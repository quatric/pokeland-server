#!/bin/bash
# Build an unsigned iOS 1.6.1 IPA ready for a sideloading tool to sign.
#
# Usage: build_ipa.sh <base-url> [out.ipa]
#   POKELAND_IPA must point at the decrypted (cryptid=0) 1.6.1 IPA.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_IPA="${POKELAND_IPA:-$ROOT/ipa/pokeland-1.6.1-decrypted.ipa}"
BASE="${1:?usage: build_ipa.sh <base-url> [out.ipa]}"
OUT="${2:-$ROOT/build/pokeland-1.6.1-ios-patched-unsigned.ipa}"
case "$OUT" in
  /*) ;;
  *) OUT="$(pwd)/$OUT" ;;
esac

PYTHON_BIN="${POKELAND_PYTHON:-$ROOT/.venv/bin/python}"
[ -x "$PYTHON_BIN" ] || { echo "Python environment not found: $PYTHON_BIN" >&2; exit 1; }
[ -f "$SRC_IPA" ] || { echo "decrypted source IPA not found: $SRC_IPA" >&2; exit 1; }

mkdir -p "$ROOT/build" "$(dirname "$OUT")"
WORK="$(mktemp -d "$ROOT/build/ipa-build.XXXXXX")"
STAGE="$WORK/stage"
trap 'rm -rf "$WORK"' EXIT
mkdir -p "$STAGE"
unzip -oq "$SRC_IPA" -d "$STAGE"

APP_CANDIDATES=("$STAGE"/Payload/*.app)
if [ "${#APP_CANDIDATES[@]}" -ne 1 ] || [ ! -d "${APP_CANDIDATES[0]}" ]; then
  echo "expected exactly one app below Payload" >&2
  exit 1
fi
APP="${APP_CANDIDATES[0]}"
EXECUTABLE_NAME="$(plutil -extract CFBundleExecutable raw "$APP/Info.plist")"
EXECUTABLE="$APP/$EXECUTABLE_NAME"
METADATA="$APP/Data/Managed/Metadata/global-metadata.dat"
RESOURCES="$APP/Data/resources.assets"

echo "==> patching game, CDN, and pokemon-webapi URLs -> $BASE"
"$PYTHON_BIN" "$ROOT/tools/patch_metadata.py" "$METADATA" "$WORK/global-metadata.dat" "$BASE"
mv "$WORK/global-metadata.dat" "$METADATA"

echo "==> patching embedded Nintendo BaaS configuration"
"$PYTHON_BIN" "$ROOT/tools/patch_ios_npf.py" \
  "$RESOURCES" "$WORK/resources.assets" "$BASE" "$APP/Info.plist"
mv "$WORK/resources.assets" "$RESOURCES"

echo "==> patching native iOS client behavior"
"$PYTHON_BIN" "$ROOT/tools/patch_ios_binary.py" "$EXECUTABLE"

# The source App Store signatures no longer cover the modified files. A stock
# device sideloader will replace these with the user's development signature;
# TrollStore/jailbreak tooling can apply its own ad-hoc signature instead.
rm -rf "$APP/_CodeSignature" "$APP/Frameworks/GTLR.framework/_CodeSignature"
rm -f "$APP/embedded.mobileprovision"

echo "==> assembling unsigned IPA"
TEMP_IPA="$WORK/pokeland-patched.ipa"
( cd "$STAGE" && zip -qry "$TEMP_IPA" Payload )
mv "$TEMP_IPA" "$OUT"
unzip -tq "$OUT"

echo
echo "built: $OUT ($(du -h "$OUT" | cut -f1))"
echo "server base baked in: $BASE"
echo "source: decrypted iOS 1.6.1 (arm64 iphoneos)"
echo "signing: required before installation"
