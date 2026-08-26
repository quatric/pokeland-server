#!/bin/bash
# Mirror archived Pokeland CDN assets out of the Wayback Machine.
#
# Three things bite here, and all three are handled:
#   * web.archive.org rate-limits aggressive clients into outright connection
#     failures, so requests go one at a time with exponential backoff.
#   * Wayback replay silently truncates large binaries - bgm/common comes back
#     at 3 MB or 9 MB instead of 12 MB, with a clean HTTP 200 either way.
#   * It does honour Range requests (verified: HTTP 206), so a truncated file is
#     resumed with `curl -C -` rather than restarted, which is the only way the
#     20 MB font bundles finish at all.
#
# Every file is checked against the authoritative size in the game's own
# size_manifest.json, so a partial download can never be mistaken for a good one.
#
# Safe to re-run: verified files are skipped, partial ones resume.
#
# Usage: fetch_cdn.sh [assetver] [delay-seconds]
set -u
ROOT=/Volumes/SSD/larsen/pokeland
CDX=$ROOT/out/cdn_cdx.txt
DEST=$ROOT/cdn
VER="${1:-1.6.0}"
DELAY="${2:-3}"

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
  part="$out.part"

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
  for try in 1 2 3 4 5 6 7 8; do
    # -C - resumes whatever the previous attempt managed to get.
    curl -sL -C - --compressed --max-time 900 --speed-time 60 --speed-limit 1024 \
         -o "$part" "https://web.archive.org/web/${ts}id_/$url" >/dev/null 2>&1
    got=$(stat -f%z "$part" 2>/dev/null || echo 0)

    if [ "$got" -gt 0 ] && { [ -z "$want" ] || [ "$got" = "$want" ]; }; then
      mv "$part" "$out"; good=1
      echo "ok   $name ($got B)"; break
    fi
    if [ -n "$want" ] && [ "$got" -gt "$want" ]; then
      # Overshoot means the resume offset desynced; start this one over.
      echo "reset $name (got=$got > want=$want)"
      rm -f "$part"
    fi
    echo "retry$try ($got/${want:-?}) $name"
    sleep $((try * try * 15))
  done

  if [ "$good" = 1 ]; then
    ok=$((ok+1))
  else
    bad=$((bad+1))
    # Leave the .part in place - a later run resumes instead of restarting.
    echo "FAIL $name (partial kept)"
  fi
  sleep "$DELAY"
done < "$CDX"

echo "DONE $VER verified=$ok failed=$bad"
