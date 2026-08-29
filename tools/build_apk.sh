#!/bin/bash
# Build a patched Pokeland APK that talks to a revival server.
#
# Two edits, both in place so no offsets move:
#   * global-metadata.dat  - the hard-coded prd/dl.app.pokeland.jp URLs are
#     rewritten to the server base, so no DNS interception is needed.
#   * AndroidManifest.xml  - targetSdkVersion 28 -> 27, which restores the
#     permissive cleartext-HTTP default. (Adding usesCleartextTraffic would mean
#     inserting an AXML attribute and resizing every enclosing chunk.)
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
BASE="${1:?usage: build_apk.sh <base-url> [out.apk]}"
OUT="${2:-$ROOT/build/pokeland-1.6.0-patched.apk}"

# macOS ships an applet JRE with keytool but no jarsigner; prefer a real JDK.
JAVA_BIN=""
for j in "${JAVA_HOME:-}" /opt/homebrew/opt/openjdk /opt/homebrew/opt/openjdk@21 \
         /opt/homebrew/opt/openjdk@17 /usr; do
  [ -n "$j" ] && [ -x "$j/bin/jarsigner" ] && { JAVA_BIN="$j/bin"; break; }
done
[ -n "$JAVA_BIN" ] || { echo "no JDK with jarsigner found (brew install openjdk)" >&2; exit 1; }

KEYSTORE=$ROOT/build/debug.keystore
STORE_PASS=pokeland
STAGE=$ROOT/build/stage

if [ ! -f "$SRC_APK" ]; then
  echo "source APK not found: $SRC_APK" >&2
  echo "set POKELAND_APK to an unmodified jp.pokemon.pokemonscrambleSP 1.6.0 APK" >&2
  exit 1
fi
mkdir -p "$ROOT/build" "$STAGE/assets/bin/Data/Managed/Metadata"

echo "==> patching metadata URLs -> $BASE"
unzip -oq "$SRC_APK" assets/bin/Data/Managed/Metadata/global-metadata.dat -d "$STAGE.orig"
python3 "$ROOT/tools/patch_metadata.py" \
    "$STAGE.orig/assets/bin/Data/Managed/Metadata/global-metadata.dat" \
    "$STAGE/assets/bin/Data/Managed/Metadata/global-metadata.dat" "$BASE"

echo "==> patching npf.json (BaaS host + useHttp)"
mkdir -p "$STAGE/assets"
unzip -oq "$SRC_APK" assets/npf.json -d "$STAGE.orig"
python3 "$ROOT/tools/patch_npf.py" \
    "$STAGE.orig/assets/npf.json" "$STAGE/assets/npf.json" "$BASE"

echo "==> patching manifest targetSdkVersion"
unzip -oq "$SRC_APK" AndroidManifest.xml -d "$STAGE.orig"
python3 "$ROOT/tools/patch_manifest.py" \
    "$STAGE.orig/AndroidManifest.xml" "$STAGE/AndroidManifest.xml" 27

echo "==> assembling APK"
cp "$SRC_APK" "$OUT"
# The old signature covers the files we are about to replace.
zip -qd "$OUT" 'META-INF/*.SF' 'META-INF/*.RSA' 'META-INF/*.DSA' 'META-INF/MANIFEST.MF' || true
( cd "$STAGE" && zip -qX "$OUT" AndroidManifest.xml assets/npf.json \
      assets/bin/Data/Managed/Metadata/global-metadata.dat )

if [ ! -f "$KEYSTORE" ]; then
  echo "==> generating debug keystore"
  "$JAVA_BIN/keytool" -genkeypair -v -keystore "$KEYSTORE" -alias pokeland \
      -keyalg RSA -keysize 2048 -validity 10950 \
      -storepass "$STORE_PASS" -keypass "$STORE_PASS" \
      -storetype PKCS12 \
      -dname "CN=Pokeland Revival, OU=Preservation, O=Pokeland, C=US" >/dev/null
fi

echo "==> signing"
"$JAVA_BIN/jarsigner" -keystore "$KEYSTORE" -storepass "$STORE_PASS" -keypass "$STORE_PASS" \
    -sigalg SHA256withRSA -digestalg SHA-256 "$OUT" pokeland >/dev/null
"$JAVA_BIN/jarsigner" -verify "$OUT" >/dev/null && echo "    signature OK"

rm -rf "$STAGE" "$STAGE.orig"
echo
echo "built: $OUT ($(du -h "$OUT" | cut -f1))"
echo "server base baked in: $BASE"
echo
echo "install with:  adb install -r \"$OUT\""
