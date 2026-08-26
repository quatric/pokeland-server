#!/usr/bin/env python3
"""Verify mirrored CDN files against the Wayback CDX SHA1 digests.

Size alone is not enough. Wayback truncates large bodies behind a clean HTTP
200, and resuming with Range can splice a bad prefix onto a good tail, giving a
file of exactly the right length whose contents are wrong (bgm/common did this).
The CDX index carries a base32 SHA1 of each stored payload, so that is the real
check.

Usage: verify_cdn.py [--delete-bad] [assetver]
"""
import base64
import hashlib
import os
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CDX = f'{ROOT}/out/cdn_cdx_digest.txt'


def digests(ver):
    out = {}
    for line in open(CDX):
        f = line.split()
        if len(f) < 5 or f[2] != '200':
            continue
        url = f[1]
        if f'/pokeland/{ver}/' not in url:
            continue
        rel = url.split('dl.app.pokeland.jp/', 1)[1]
        out[rel] = f[3]
    return out


def main():
    delete_bad = '--delete-bad' in sys.argv
    args = [a for a in sys.argv[1:] if not a.startswith('--')]
    ver = args[0] if args else '1.6.0'

    want = digests(ver)
    good = bad = missing = 0
    for rel, digest in sorted(want.items()):
        path = os.path.join(ROOT, 'cdn', rel)
        if not os.path.exists(path):
            missing += 1
            continue
        got = base64.b32encode(hashlib.sha1(open(path, 'rb').read()).digest()).decode()
        name = rel.split('/', 3)[-1]
        if got == digest:
            good += 1
        else:
            bad += 1
            print(f'BAD  {name}  {got} != {digest}')
            if delete_bad:
                os.remove(path)
                print(f'     removed {path}')

    print(f'\nverified={good} bad={bad} missing={missing} of {len(want)} archived')
    return 1 if bad else 0


if __name__ == '__main__':
    sys.exit(main())
