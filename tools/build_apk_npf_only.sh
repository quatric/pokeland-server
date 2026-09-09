#!/bin/bash
# Build a minimally modified Android APK: only assets/npf.json is changed.
# The original APK signatures are necessarily replaced after that payload edit.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_APK="${POKELAND_APK:-$ROOT/apk/pokemonscrambleSP-1.6.0.apk}"
BASE="${1:?usage: build_apk_npf_only.sh <base-url> [out.apk]}"
OUT="${2:-$ROOT/build/pokeland-1.6.0-npf-only.apk}"
case "$OUT" in
  /*) ;;
  *) OUT="$(pwd)/$OUT" ;;
esac

PYTHON_BIN="${POKELAND_PYTHON:-$ROOT/.venv/bin/python}"
[ -x "$PYTHON_BIN" ] || PYTHON_BIN="$(command -v python3)"
[ -f "$SRC_APK" ] || { echo "source APK not found: $SRC_APK" >&2; exit 1; }

APKSIGNER_BIN="${POKELAND_APKSIGNER:-$(command -v apksigner || true)}"
ZIPALIGN_BIN="${POKELAND_ZIPALIGN:-$(command -v zipalign || true)}"
if [ -z "$APKSIGNER_BIN" ]; then
  for candidate in /opt/homebrew/share/android-commandlinetools/build-tools/*/apksigner; do
    [ -x "$candidate" ] && APKSIGNER_BIN="$candidate"
  done
fi
if [ -z "$ZIPALIGN_BIN" ] && [ -n "$APKSIGNER_BIN" ] \
    && [ -x "$(dirname "$APKSIGNER_BIN")/zipalign" ]; then
  ZIPALIGN_BIN="$(dirname "$APKSIGNER_BIN")/zipalign"
fi
[ -n "$APKSIGNER_BIN" ] || { echo "apksigner not found" >&2; exit 1; }
[ -n "$ZIPALIGN_BIN" ] || { echo "zipalign not found" >&2; exit 1; }

KEYSTORE="$ROOT/build/debug.keystore"
[ -f "$KEYSTORE" ] || {
  echo "debug keystore not found: $KEYSTORE (run tools/build_apk.sh once)" >&2
  exit 1
}

mkdir -p "$ROOT/build" "$(dirname "$OUT")"
WORK="$(mktemp -d "$ROOT/build/apk-npf-only.XXXXXX")"
trap 'rm -rf "$WORK"' EXIT
mkdir -p "$WORK/stage/assets"
unzip -oq "$SRC_APK" assets/npf.json -d "$WORK/original"
"$PYTHON_BIN" "$ROOT/tools/patch_npf.py" \
  "$WORK/original/assets/npf.json" "$WORK/stage/assets/npf.json" "$BASE"

cp "$SRC_APK" "$WORK/unsigned.apk"
zip -qd "$WORK/unsigned.apk" \
  'META-INF/*.SF' 'META-INF/*.RSA' 'META-INF/*.DSA' 'META-INF/MANIFEST.MF' || true
( cd "$WORK/stage" && zip -qXD "$WORK/unsigned.apk" assets/npf.json )
"$ZIPALIGN_BIN" -f 4 "$WORK/unsigned.apk" "$WORK/aligned.apk"
"$APKSIGNER_BIN" sign --ks "$KEYSTORE" --ks-key-alias pokeland \
  --ks-pass pass:pokeland --key-pass pass:pokeland \
  --v1-signer-name POKELAND --v1-signing-enabled true \
  --v2-signing-enabled false --v3-signing-enabled false \
  --v4-signing-enabled false "$WORK/aligned.apk"
"$APKSIGNER_BIN" verify "$WORK/aligned.apk"
mv "$WORK/aligned.apk" "$OUT"

echo "built: $OUT"
echo "payload patch: assets/npf.json only"
echo "server BaaS origin: $BASE"
