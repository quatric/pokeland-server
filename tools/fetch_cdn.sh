#!/bin/bash
# Mirror archived Pokeland CDN assets out of the Wayback Machine.
#
# Three things bite here, and all three are handled:
#
#   * web.archive.org rate-limits aggressive clients into outright connection
#     failures, so requests go one at a time with exponential backoff.
#   * Wayback replay silently truncates large binaries - bgm/common comes back
#     at 3 MB or 9 MB instead of 12 MB, with a clean HTTP 200 either way.
#   * Resuming a truncated file with Range can splice a bad prefix onto a good
#     tail, producing a file of exactly the right length whose contents are
#     wrong. bgm/common did this: correct size, corrupt LZMA.
#
# So correctness is decided by the SHA1 the CDX index records for each stored
# payload, never by size. A fresh download is tried first; a resume is only
# attempted when the body came back short, and its result is checksummed like
# any other. A digest mismatch throws the file away and starts over clean.
#
# Safe to re-run: verified files are skipped.
#
# Usage: fetch_cdn.sh [assetver] [delay-seconds]
set -u
ROOT=/Volumes/SSD/larsen/pokeland
CDX=$ROOT/out/cdn_cdx_digest.txt
DEST=$ROOT/cdn
VER="${1:-1.6.0}"
DELAY="${2:-3}"

EXPECT=$(mktemp)
trap 'rm -f "$EXPECT"' EXIT

# name <TAB> size <TAB> sha1-hex, joining the CDX digests to the game's own
# size manifest (CDX base32 -> hex so shasum output can be compared directly).
python3 - "$CDX" "$ROOT/out/size_manifest_$VER.json" "$VER" > "$EXPECT" <<'PY'
import base64, json, sys
cdx, manifest, ver = sys.argv[1], sys.argv[2], sys.argv[3]
sizes = {}
for info in json.load(open(manifest))['AssetSizeInfos']:
    for i in info['AssetSizeItems']:
        sizes[i['Name']] = i['Size']
for line in open(cdx):
    f = line.split()
    if len(f) < 5 or f[2] != '200' or f'/pokeland/{ver}/' not in f[1]:
        continue
    rel = f[1].split('dl.app.pokeland.jp/', 1)[1]
    name = rel.split('/', 3)[-1]
    sha1 = base64.b32decode(f[3]).hex()
    print(f"{name}\t{sizes.get(name, 0)}\t{sha1}")
PY

lookup() { awk -F'\t' -v n="$1" '$1==n {print $2"\t"$3; exit}' "$EXPECT"; }
sha1of() { shasum -a 1 "$1" 2>/dev/null | cut -d' ' -f1; }

ok=0; bad=0
while read -r ts url status digest len; do
  case "$url" in */pokeland/"$VER"/*) ;; *) continue ;; esac
  [ "$status" = 200 ] || continue

  rel="${url#https://dl.app.pokeland.jp/}"
  name=$(printf '%s' "$rel" | sed -E "s|^pokeland/[^/]+/[0-9a-f]{32}/||")
  IFS=$'\t' read -r want_size want_sha < <(lookup "$name")
  out="$DEST/$rel"
  part="$out.part"

  if [ -s "$out" ]; then
    if [ "$(sha1of "$out")" = "$want_sha" ]; then
      echo "have $name"; ok=$((ok+1)); continue
    fi
    echo "bad  $name (digest mismatch) - refetching"
    rm -f "$out" "$part"
  fi

  mkdir -p "$(dirname "$out")"
  good=0
  for try in 1 2 3 4 5 6 7 8; do
    # Resume only makes sense against a short body from the previous attempt;
    # anything else starts clean so a bad prefix cannot survive.
    got=$(stat -f%z "$part" 2>/dev/null || echo 0)
    if [ "$got" -gt 0 ] && [ -n "$want_size" ] && [ "$got" -lt "$want_size" ]; then
      resume=(-C -)
    else
      rm -f "$part"; resume=()
    fi

    curl -sL "${resume[@]+"${resume[@]}"}" --compressed --max-time 900 \
         --speed-time 60 --speed-limit 1024 \
         -o "$part" "https://web.archive.org/web/${ts}id_/$url" >/dev/null 2>&1

    got=$(stat -f%z "$part" 2>/dev/null || echo 0)
    if [ "$got" -gt 0 ] && [ "$(sha1of "$part")" = "$want_sha" ]; then
      mv "$part" "$out"; good=1
      echo "ok   $name ($got B)"; break
    fi
    if [ -n "$want_size" ] && [ "$got" -ge "$want_size" ]; then
      # Full length but wrong content - a spliced resume. Start over.
      echo "reset $name (len ok, digest bad)"
      rm -f "$part"
    fi
    echo "retry$try ($got/${want_size:-?}) $name"
    sleep $((try * try * 15))
  done

  if [ "$good" = 1 ]; then ok=$((ok+1)); else bad=$((bad+1)); echo "FAIL $name"; fi
  sleep "$DELAY"
done < "$CDX"

echo "DONE $VER verified=$ok failed=$bad"
