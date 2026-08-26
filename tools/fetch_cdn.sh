#!/bin/bash
# Mirror archived Pokeland CDN assets out of the Wayback Machine.
#
# Two things bite here, and both are handled:
#   * web.archive.org rate-limits aggressive clients into outright connection
#     failures, so requests go one at a time with exponential backoff.
#   * Wayback replay silently truncates large binaries, so every download is
#     checked against the authoritative size in the game's size_manifest.json
#     and re-fetched until it matches.
#
# Usage: fetch_cdn.sh [assetver] [delay-seconds]
set -u
ROOT=/Volumes/SSD/larsen/pokeland
CDX=$ROOT/out/cdn_cdx.txt
DEST=$ROOT/cdn
VER="${1:-1.6.0}"
DELAY="${2:-4}"

SIZES=$(mktemp)
trap 'rm -f "$SIZES"' EXIT
python3 - "$ROOT/out/size_manifest_$VER.json" > "$SIZES" <<'PY'
import json, sys
for info in json.load(open(sys.argv[1]))['AssetSizeInfos']:
    for i in info['AssetSizeItems']:
        print(i['Name'], i['Size'])
PY

expected() { awk -v n="$1" '$1==n {print $2; exit}' "$SIZES"; }

ok=0; bad=0
while read -r ts url mime status len; do
  case "$url" in */pokeland/"$VER"/*) ;; *) continue ;; esac
  [ "$status" = 200 ] || continue

  rel="${url#https://dl.app.pokeland.jp/}"
  name=$(printf '%s' "$rel" | sed -E "s|^pokeland/[^/]+/[0-9a-f]{32}/||")
  want=$(expected "$name")
  out="$DEST/$rel"

  if [ -s "$out" ]; then
    got=$(stat -f%z "$out")
    if [ -z "$want" ] || [ "$got" = "$want" ]; then
      echo "have $name"; ok=$((ok+1)); continue
    fi
    echo "bad  $name ($got != $want) - refetching"
    rm -f "$out"
  fi

  mkdir -p "$(dirname "$out")"
  good=0
  for try in 1 2 3 4 5 6; do
    code=$(curl -sL --compressed --max-time 900 --speed-time 60 --speed-limit 1024 \
             -w '%{http_code}' -o "$out.part" "https://web.archive.org/web/${ts}id_/$url")
    got=$(stat -f%z "$out.part" 2>/dev/null || echo 0)
    if [ "$code" = 200 ] && [ "$got" -gt 0 ] && { [ -z "$want" ] || [ "$got" = "$want" ]; }; then
      mv "$out.part" "$out"; good=1
      echo "ok   $name ($got B)"; break
    fi
    echo "retry$try (http=$code got=$got want=${want:-?}) $name"
    sleep $((try * try * 20))
  done
  rm -f "$out.part"

  if [ "$good" = 1 ]; then ok=$((ok+1)); else bad=$((bad+1)); echo "FAIL $name"; fi
  sleep "$DELAY"
done < "$CDX"

echo "DONE $VER verified=$ok failed=$bad"
