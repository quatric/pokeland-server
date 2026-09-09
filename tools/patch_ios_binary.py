#!/usr/bin/env python3
"""Patch native gates and the open-ended event timer in iOS 1.6.1."""

from __future__ import annotations

import sys
from pathlib import Path


# File offsets equal RVAs in this Mach-O's __TEXT segment (vmaddr 0x100000000,
# fileoff 0). These are guarded against the decrypted 1.6.1 instructions so an
# encrypted or different client build fails closed instead of being corrupted.
PATCHES = (
    (
        "service-shutdown flag",
        0x10345F8,
        bytes.fromhex("60 ee 41 39"),  # ldrb w0, [x19, #0x7b]
        bytes.fromhex("00 00 80 52"),  # mov w0, #0
    ),
    (
        "support-date cutoff",
        0x115CBB4,
        bytes.fromhex("f4 4f be a9 fd 7b 01 a9"),
        bytes.fromhex("00 00 80 52 c0 03 5f d6"),  # mov w0, #0; ret
    ),
    (
        "open-ended journey deadline badge",
        0x112F9E8,
        bytes.fromhex("21 00 80 52"),  # mov w1, #1
        bytes.fromhex("01 00 80 52"),  # mov w1, #0
    ),
)


def main() -> None:
    if len(sys.argv) != 2:
        raise SystemExit(f"usage: {Path(sys.argv[0]).name} <iOS executable>")

    path = Path(sys.argv[1])
    data = bytearray(path.read_bytes())
    if data[:4] != bytes.fromhex("cf fa ed fe"):
        raise SystemExit(f"not a little-endian 64-bit Mach-O: {path}")

    for label, offset, expected, replacement in PATCHES:
        actual = bytes(data[offset : offset + len(expected)])
        if actual == replacement:
            print(f"    {label} already patched at 0x{offset:x}")
            continue
        if actual != expected:
            raise SystemExit(
                f"unexpected executable bytes at 0x{offset:x}: expected "
                f"{expected.hex(' ')}, got {actual.hex(' ')}; this must be the "
                "decrypted iOS 1.6.1 build"
            )
        data[offset : offset + len(replacement)] = replacement
        print(f"    patched {label} at 0x{offset:x}")

    path.write_bytes(data)


if __name__ == "__main__":
    main()
