#!/bin/bash
# Build an unsigned iOS 1.6.1 IPA ready for a sideloading tool to sign.
#
# Usage: build_ipa.sh <server-url> [out.ipa]
#   POKELAND_IPA must point at the decrypted (cryptid=0) 1.6.1 IPA.
# The server's /api, /cdn and /baas paths carry the game, CDN and Nintendo BaaS
# hosts; see patch_ipa.py.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_IPA="${POKELAND_IPA:-$ROOT/ipa/pokeland-1.6.1-decrypted.ipa}"
BASE="${1:?usage: build_ipa.sh <server-url> [out.ipa]}"
OUT="${2:-$ROOT/build/pokeland-1.6.1-ios-patched-unsigned.ipa}"
PYTHON_BIN="${POKELAND_PYTHON:-$ROOT/.venv/bin/python}"
[ -x "$PYTHON_BIN" ] || { echo "Python environment not found: $PYTHON_BIN" >&2; exit 1; }
[ -f "$SRC_IPA" ] || { echo "decrypted source IPA not found: $SRC_IPA" >&2; exit 1; }

mkdir -p "$(dirname "$OUT")"
exec "$PYTHON_BIN" "$ROOT/tools/patch_ipa.py" "$SRC_IPA" -o "$OUT" --server "$BASE"
