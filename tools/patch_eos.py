#!/usr/bin/env python3
"""Patch native client behavior needed by the revival build."""

from __future__ import annotations

import sys
from pathlib import Path


# The client has two independent IL2CPP gates: a persisted service-shutdown flag
# and a real-clock customer-support cutoff. Returning false from their accessors
# also neutralizes an m_isEndOfService=true value already saved in PlayerPrefs,
# so existing installs do not need their app data cleared. The third patch keeps
# Camp's timer-only badge hidden because this revival's journey has no end date.
PATCHES = {
    "arm64-v8a": (
        (
            "service-shutdown flag",
            0x1342808,
            bytes.fromhex("60 ee 41 39"),  # ldrb w0, [x19, #0x7b]
            bytes.fromhex("00 00 80 52"),  # mov w0, #0
        ),
        (
            "support-date cutoff",
            0x1699FC8,
            bytes.fromhex("f3 0f 1e f8 fd 7b 01 a9"),
            bytes.fromhex("00 00 80 52 c0 03 5f d6"),
        ),
        (
            "open-ended journey deadline badge",
            0xF1D918,
            bytes.fromhex("e1 03 00 32"),  # mov w1, #1
            bytes.fromhex("e1 03 1f 2a"),  # mov w1, wzr
        ),
    ),
    "armeabi-v7a": (
        (
            "service-shutdown flag",
            0xDC723C,
            bytes.fromhex("57 00 d4 e5"),  # ldrb r0, [r4, #0x57]
            bytes.fromhex("00 00 a0 e3"),  # mov r0, #0
        ),
        (
            "support-date cutoff",
            0x11942C0,
            bytes.fromhex("70 4c 2d e9 10 b0 8d e2"),
            bytes.fromhex("00 00 a0 e3 1e ff 2f e1"),
        ),
        (
            "open-ended journey deadline badge",
            0x90C2E8,
            bytes.fromhex("01 10 a0 e3"),  # mov r1, #1
            bytes.fromhex("00 10 a0 e3"),  # mov r1, #0
        ),
    ),
}


def patch_library(
    path: Path, label: str, offset: int, expected: bytes, replacement: bytes
) -> None:
    data = bytearray(path.read_bytes())
    actual = bytes(data[offset : offset + len(expected)])
    if actual == replacement:
        print(f"    {path.parent.name}: {label} already patched at 0x{offset:x}")
        return
    if actual != expected:
        raise SystemExit(
            f"unexpected {path.parent.name} libil2cpp.so bytes at 0x{offset:x}: "
            f"expected {expected.hex(' ')}, got {actual.hex(' ')}"
        )

    data[offset : offset + len(replacement)] = replacement
    path.write_bytes(data)
    print(f"    {path.parent.name}: patched {label} at 0x{offset:x}")


def main() -> None:
    if len(sys.argv) != 2:
        raise SystemExit(f"usage: {Path(sys.argv[0]).name} <apk-lib-directory>")

    lib_root = Path(sys.argv[1])
    for abi, patches in PATCHES.items():
        path = lib_root / abi / "libil2cpp.so"
        if not path.is_file():
            raise SystemExit(f"missing native library: {path}")
        for label, offset, expected, replacement in patches:
            patch_library(path, label, offset, expected, replacement)


if __name__ == "__main__":
    main()
