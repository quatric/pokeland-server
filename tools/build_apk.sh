#!/bin/bash
# Build a patched Pokeland APK that talks to a revival server and renders the
# archived asset bundles without magenta fallback shaders.
#
# Network edits:
#   * global-metadata.dat  - the hard-coded prd/dl.app.pokeland.jp URLs are
#     rewritten to the server base, so no DNS interception is needed.
#   * npf.json             - Nintendo BaaS is redirected to the same server.
#   * AndroidManifest.xml  - targetSdkVersion 28 -> 27, which restores the
#     permissive cleartext-HTTP default. (Adding usesCleartextTraffic would mean
#     inserting an AXML attribute and resizing every enclosing chunk.)
#
# Shader compatibility edits (unless POKELAND_SKIP_GLES2=1):
#   * transplant the donor's GLES2 variants of 14 embedded player shaders;
#   * replace Resources/unity_builtin_extra with its GLES2 build;
#   * set Unity's graphics API list to OpenGLES2.
#
# The GLES2 donor is not distributed here. By default it is read from
# apk/pokeland-gles2-donor.apk; override that with POKELAND_GLES2_APK.
#
# The APK is then re-signed with a local debug key. Signature scheme v1 only,
# which Android accepts because the app targets below API 30.
#
# Usage: build_apk.sh <base-url> [out.apk]
#   e.g. build_apk.sh http://10.0.2.2:5199          # Android emulator -> host
#        build_apk.sh http://192.168.1.50:5199      # real device on the LAN
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
# Path to an unmodified 1.6.0 APK. Not distributed with this repository.
SRC_APK="${POKELAND_APK:-$ROOT/apk/pokemonscrambleSP-1.6.0.apk}"
GLES2_APK="${POKELAND_GLES2_APK:-$ROOT/apk/pokeland-gles2-donor.apk}"
SKIP_GLES2="${POKELAND_SKIP_GLES2:-0}"
BASE="${1:?usage: build_apk.sh <base-url> [out.apk]}"
OUT="${2:-$ROOT/build/pokeland-1.6.0-patched.apk}"
# Parts of the build run from a staging directory, so resolve a relative output
# path before changing directories.
case "$OUT" in
  /*) ;;
  *) OUT="$(pwd)/$OUT" ;;
esac

PYTHON_BIN="${POKELAND_PYTHON:-}"
if [ -z "$PYTHON_BIN" ]; then
  if [ -x "$ROOT/.venv/bin/python" ]; then
    PYTHON_BIN="$ROOT/.venv/bin/python"
  else
    PYTHON_BIN="$(command -v python3 || true)"
  fi
fi
[ -n "$PYTHON_BIN" ] || { echo "python3 not found" >&2; exit 1; }

# macOS ships an applet JRE with keytool but no jarsigner; prefer a real JDK.
JAVA_BIN=""
for j in "${JAVA_HOME:-}" /opt/homebrew/opt/openjdk /opt/homebrew/opt/openjdk@21 \
         /opt/homebrew/opt/openjdk@17 /usr; do
  [ -n "$j" ] && [ -x "$j/bin/jarsigner" ] && { JAVA_BIN="$j/bin"; break; }
done
[ -n "$JAVA_BIN" ] || { echo "no JDK with jarsigner found (brew install openjdk)" >&2; exit 1; }

# apksigner preserves the compressed bytes of unchanged ZIP entries, unlike
# jarsigner on this APK. That makes binary-delta distribution practical.
APKSIGNER_BIN="${POKELAND_APKSIGNER:-$(command -v apksigner || true)}"
if [ -z "$APKSIGNER_BIN" ] && [ -n "${ANDROID_HOME:-}" ]; then
  for candidate in "$ANDROID_HOME"/build-tools/*/apksigner; do
    [ -x "$candidate" ] && APKSIGNER_BIN="$candidate"
  done
fi
if [ -z "$APKSIGNER_BIN" ]; then
  for candidate in /opt/homebrew/share/android-commandlinetools/build-tools/*/apksigner; do
    [ -x "$candidate" ] && APKSIGNER_BIN="$candidate"
  done
fi

KEYSTORE=$ROOT/build/debug.keystore
STORE_PASS=pokeland

if [ ! -f "$SRC_APK" ]; then
  echo "source APK not found: $SRC_APK" >&2
  echo "set POKELAND_APK to an unmodified jp.pokemon.pokemonscrambleSP 1.6.0 APK" >&2
  exit 1
fi
if [ "$SKIP_GLES2" != 1 ] && [ ! -f "$GLES2_APK" ]; then
  echo "GLES2 donor APK not found: $GLES2_APK" >&2
  echo "set POKELAND_GLES2_APK or place it at apk/pokeland-gles2-donor.apk" >&2
  exit 1
fi
if [ "$SKIP_GLES2" != 1 ] && ! "$PYTHON_BIN" -c 'import UnityPy' >/dev/null 2>&1; then
  echo "UnityPy is required for the shader fix" >&2
  echo "install it with: $PYTHON_BIN -m pip install -r $ROOT/requirements.txt" >&2
  exit 1
fi

mkdir -p "$ROOT/build" "$(dirname "$OUT")"
WORK="$(mktemp -d "$ROOT/build/apk-build.XXXXXX")"
STAGE="$WORK/stage"
ORIGINAL="$WORK/original"
DONOR="$WORK/donor"
trap 'rm -rf "$WORK"' EXIT
mkdir -p "$STAGE/assets" "$ORIGINAL" "$DONOR"

if [ "$SKIP_GLES2" != 1 ]; then
  echo "==> extracting Unity player data"
  unzip -oq "$SRC_APK" 'assets/bin/Data/*' -d "$ORIGINAL"
  mkdir -p "$STAGE/assets/bin"
  cp -R "$ORIGINAL/assets/bin/Data" "$STAGE/assets/bin/"
  unzip -oq "$GLES2_APK" 'assets/bin/Data/*' -d "$DONOR"
  "$PYTHON_BIN" "$ROOT/tools/patch_gles2.py" \
      "$DONOR/assets/bin/Data" "$STAGE/assets/bin/Data"
fi

echo "==> patching metadata URLs -> $BASE"
unzip -oq "$SRC_APK" assets/bin/Data/Managed/Metadata/global-metadata.dat -d "$ORIGINAL"
"$PYTHON_BIN" "$ROOT/tools/patch_metadata.py" \
    "$ORIGINAL/assets/bin/Data/Managed/Metadata/global-metadata.dat" \
    "$STAGE/assets/bin/Data/Managed/Metadata/global-metadata.dat" "$BASE"

echo "==> patching npf.json (BaaS host + useHttp)"
mkdir -p "$STAGE/assets"
unzip -oq "$SRC_APK" assets/npf.json -d "$ORIGINAL"
"$PYTHON_BIN" "$ROOT/tools/patch_npf.py" \
    "$ORIGINAL/assets/npf.json" "$STAGE/assets/npf.json" "$BASE"

echo "==> patching manifest targetSdkVersion"
unzip -oq "$SRC_APK" AndroidManifest.xml -d "$ORIGINAL"
"$PYTHON_BIN" "$ROOT/tools/patch_manifest.py" \
    "$ORIGINAL/AndroidManifest.xml" "$STAGE/AndroidManifest.xml" 27

echo "==> assembling APK"
cp "$SRC_APK" "$OUT"
# The old signature covers the files we are about to replace.
zip -qd "$OUT" 'META-INF/*.SF' 'META-INF/*.RSA' 'META-INF/*.DSA' 'META-INF/MANIFEST.MF' || true
if [ "$SKIP_GLES2" = 1 ]; then
  ( cd "$STAGE" && zip -qX "$OUT" AndroidManifest.xml assets/npf.json \
        assets/bin/Data/Managed/Metadata/global-metadata.dat )
else
  replacements=(AndroidManifest.xml assets/npf.json)
  while IFS= read -r -d '' staged_file; do
    relative_path="${staged_file#"$STAGE/"}"
    if ! cmp -s "$staged_file" "$ORIGINAL/$relative_path"; then
      replacements+=("$relative_path")
    fi
  done < <(find "$STAGE/assets/bin/Data" -type f -print0)

  echo "    updating ${#replacements[@]} changed APK entries"
  # Preserve the source APK's compressed bytes for every unchanged entry. This
  # keeps binary-delta patches small. -D also prevents ZIP directory records,
  # which Unity's IL2CPP resource extractor incorrectly treats as files.
  ( cd "$STAGE" && zip -qXD "$OUT" "${replacements[@]}" )
fi

if [ ! -f "$KEYSTORE" ]; then
  echo "==> generating debug keystore"
  "$JAVA_BIN/keytool" -genkeypair -v -keystore "$KEYSTORE" -alias pokeland \
      -keyalg RSA -keysize 2048 -validity 10950 \
      -storepass "$STORE_PASS" -keypass "$STORE_PASS" \
      -storetype PKCS12 \
      -dname "CN=Pokeland Revival, OU=Preservation, O=Pokeland, C=US" >/dev/null
fi

echo "==> signing"
if [ -n "$APKSIGNER_BIN" ]; then
  "$APKSIGNER_BIN" sign --ks "$KEYSTORE" --ks-key-alias pokeland \
      --ks-pass "pass:$STORE_PASS" --key-pass "pass:$STORE_PASS" \
      --v1-signer-name POKELAND --v1-signing-enabled true \
      --v2-signing-enabled false --v3-signing-enabled false \
      --v4-signing-enabled false "$OUT"
  "$APKSIGNER_BIN" verify "$OUT" && echo "    signature OK (apksigner, v1)"
else
  "$JAVA_BIN/jarsigner" -keystore "$KEYSTORE" -storepass "$STORE_PASS" -keypass "$STORE_PASS" \
      -sigalg SHA256withRSA -digestalg SHA-256 "$OUT" pokeland >/dev/null
  "$JAVA_BIN/jarsigner" -verify "$OUT" >/dev/null && echo "    signature OK (jarsigner, v1)"
fi

echo
echo "built: $OUT ($(du -h "$OUT" | cut -f1))"
echo "server base baked in: $BASE"
[ "$SKIP_GLES2" = 1 ] || echo "renderer: OpenGLES2 with complete player shader set"
echo
echo "install with:  adb install -r \"$OUT\""
