#!/usr/bin/env python3
"""Repoint the NPF SDK's Nintendo BaaS backend at a revival server.

assets/npf.json is plain JSON in the APK, so this needs no binary surgery. Two
keys matter:

  baasHost  the account backend's host[:port] - no scheme, that is added by the
            SDK
  useHttp   the SDK otherwise hard-codes https for every BaaS call. Setting this
            lets the account stack run over plain HTTP, so no CA has to be
            installed on the device (which apps targeting API 24+ would not
            trust anyway).

Usage: patch_npf.py <in.json> <out.json> <base-url>
"""
import json
import sys


def main():
    if len(sys.argv) != 4:
        print(__doc__)
        return 1
    src, dst, base = sys.argv[1], sys.argv[2], sys.argv[3]

    # The retail file is UTF-8 with a BOM.
    cfg = json.loads(open(src, encoding='utf-8-sig').read())
    host = base.split('://', 1)[-1].rstrip('/')

    cfg['baasHost'] = host
    cfg['useHttp'] = base.startswith('http://')

    with open(dst, 'w') as fh:
        json.dump(cfg, fh, indent=4)

    print(f"  baasHost -> {host}")
    print(f"  useHttp  -> {cfg['useHttp']}")
    return 0


if __name__ == '__main__':
    sys.exit(main())
