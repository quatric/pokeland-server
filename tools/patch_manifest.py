#!/usr/bin/env python3
"""Lower targetSdkVersion in a binary AndroidManifest.xml so cleartext HTTP works.

The revival server is plain HTTP. Android blocks cleartext by default for apps
targeting API 28+, and apps targeting API 24+ do not trust user-installed CAs,
so HTTPS with a local cert is not a way out either.

The usual fix is to add android:usesCleartextTraffic="true", but inserting an
attribute into binary AXML shifts every chunk size after it. Dropping
targetSdkVersion to 27 gets the same permissive default and is a single in-place
4-byte edit, which cannot corrupt the file.

Usage: patch_manifest.py <in.xml> <out.xml> [target-sdk]
"""
import struct
import sys

CHUNK_STRING_POOL = 0x0001
CHUNK_START_TAG = 0x0102
TYPE_INT_DEC = 0x10


def read_string_pool(data, off):
    """Return the pool's strings, for resolving attribute name indices."""
    _type, header_size, size = struct.unpack_from('<HHI', data, off)
    count, _style_count, _flags, strings_start, _styles_start = \
        struct.unpack_from('<IIIII', data, off + 8)
    utf8 = bool(_flags & (1 << 8))
    out = []
    for i in range(count):
        s_off = struct.unpack_from('<I', data, off + header_size + i * 4)[0]
        p = off + strings_start + s_off
        if utf8:
            n = data[p + 1]
            out.append(data[p + 2:p + 2 + n].decode('utf-8', 'replace'))
        else:
            n = struct.unpack_from('<H', data, p)[0]
            out.append(data[p + 2:p + 2 + n * 2].decode('utf-16-le', 'replace'))
    return out


def main():
    if len(sys.argv) < 3:
        print(__doc__)
        return 1
    src, dst = sys.argv[1], sys.argv[2]
    want = int(sys.argv[3]) if len(sys.argv) > 3 else 27

    data = bytearray(open(src, 'rb').read())
    pool = None
    off = 8                                    # skip the outer XML chunk header
    patched = 0

    while off < len(data) - 8:
        ctype, header_size, size = struct.unpack_from('<HHI', data, off)
        if size <= 0 or off + size > len(data):
            break

        if ctype == CHUNK_STRING_POOL and pool is None:
            pool = read_string_pool(data, off)

        elif ctype == CHUNK_START_TAG and pool is not None:
            # ResXMLTree_node is 16 bytes (chunk header + lineNumber + comment);
            # attributeStart is an offset from the start of the attrExt that
            # follows it, not from the chunk.
            attr_start, _attr_size, attr_count = \
                struct.unpack_from('<HHH', data, off + 24)
            base = off + 16 + attr_start
            for i in range(attr_count):
                a = base + i * 20
                _ns, name_idx, _raw = struct.unpack_from('<III', data, a)
                _sz, _res0, dtype, value = struct.unpack_from('<HBBI', data, a + 12)
                if pool[name_idx] == 'targetSdkVersion' and dtype == TYPE_INT_DEC:
                    if value == want:
                        print(f'targetSdkVersion already {want}')
                    else:
                        struct.pack_into('<I', data, a + 16, want)
                        print(f'targetSdkVersion {value} -> {want}')
                        patched += 1

        off += size

    if not patched:
        print('targetSdkVersion not patched')
        return 1
    open(dst, 'wb').write(data)
    print(f'wrote {dst} ({len(data):,} bytes, size unchanged)')
    return 0


if __name__ == '__main__':
    sys.exit(main())
