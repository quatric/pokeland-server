#!/usr/bin/env python3
"""Repoint the client's hard-coded server URLs at a revival server.

The API and CDN hosts are baked into IL2CPP string literals, so redirecting the
client without DNS interception means editing global-metadata.dat directly.

Literals live in two parts: a table of (length, dataIndex) pairs and a flat data
blob. Rewriting one in place only needs the replacement to fit in the original's
span - the length field is updated, so a shorter URL is fine and nothing else in
the file moves. That keeps every other offset in the metadata valid.

Usage: patch_metadata.py <in.dat> <out.dat> <base-url>
   e.g. patch_metadata.py global-metadata.dat patched.dat http://10.0.2.2:5199
"""
import struct
import sys

# Retail host prefixes. Matching is by prefix, not equality: the C# compiler
# constant-folds `BaseURL + "/" + Version + "/game"` into its own literal, so
# "https://prd.app.pokeland.jp/1.600/game" exists alongside the bare host and
# both have to be redirected.
HOSTS = (
    'https://prd.app.pokeland.jp',
    'https://dl.app.pokeland.jp',
)


def literals(data):
    """Yield (index, offset_of_entry, length, data_offset, text)."""
    lit_off, lit_size = struct.unpack_from('<ii', data, 8)
    dat_off, _ = struct.unpack_from('<ii', data, 16)
    for i in range(lit_size // 8):
        entry = lit_off + i * 8
        length, idx = struct.unpack_from('<Ii', data, entry)
        start = dat_off + idx
        yield i, entry, length, start, data[start:start + length]


def main():
    if len(sys.argv) != 4:
        print(__doc__)
        return 1
    src, dst, base = sys.argv[1], sys.argv[2], sys.argv[3].rstrip('/')

    data = bytearray(open(src, 'rb').read())
    sanity, version = struct.unpack_from('<Ii', data, 0)
    if sanity != 0xFAB11BAF:
        print(f'not an il2cpp metadata file (sanity {sanity:#x})')
        return 1
    print(f'metadata v{version}, {len(data):,} bytes')

    patched = 0
    for i, entry, length, start, raw in list(literals(data)):
        text = raw.decode('utf-8', 'replace')
        for host in HOSTS:
            if not text.startswith(host):
                continue
            new = (base + text[len(host):]).encode()
            if len(new) > length:
                print(f'  SKIP "{text}" -> "{new.decode()}" '
                      f'({len(new)} > {length} bytes, will not fit)')
                continue
            # Overwrite in place and shorten the recorded length. The tail of the
            # original span becomes dead bytes no literal points at any more.
            data[start:start + len(new)] = new
            data[start + len(new):start + length] = b'\0' * (length - len(new))
            struct.pack_into('<I', data, entry, len(new))
            print(f'  [{i}] "{text}" -> "{new.decode()}"')
            patched += 1

    if not patched:
        print('nothing patched - are the URLs already rewritten?')
        return 1

    open(dst, 'wb').write(data)
    print(f'patched {patched} literals -> {dst}')
    return 0


if __name__ == '__main__':
    sys.exit(main())
