#!/usr/bin/env python3
"""Replace deflated ZIP entries while preserving all untouched archive bytes.

The replacement is intentionally strict: the source entry must be reproducible
byte-for-byte with the requested zlib level. This keeps binary deltas small and
prevents a host's deflater implementation from silently rewriting an entire
compressed entry.

Usage:
    replace_zip_entries.py APK ENTRY REPLACEMENT LEVEL \
        [ENTRY REPLACEMENT LEVEL ...]
"""

from __future__ import annotations

import argparse
import binascii
import os
import stat
import struct
import sys
import tempfile
import zlib
from dataclasses import dataclass
from pathlib import Path
from typing import NoReturn, Sequence


LOCAL_SIGNATURE = b"PK\x03\x04"
CENTRAL_SIGNATURE = b"PK\x01\x02"
EOCD_SIGNATURE = b"PK\x05\x06"
ZIP64_LOCATOR_SIGNATURE = b"PK\x06\x07"

LOCAL_FIXED_SIZE = 30
CENTRAL_FIXED_SIZE = 46
EOCD_FIXED_SIZE = 22
MAX_ZIP_COMMENT_SIZE = 0xFFFF

FLAG_ENCRYPTED = 1 << 0
FLAG_DATA_DESCRIPTOR = 1 << 3
FLAG_UTF8 = 1 << 11
METHOD_DEFLATE = 8


class ArchiveError(RuntimeError):
    """Raised when an archive cannot be patched without ambiguity."""


@dataclass(frozen=True)
class EndOfCentralDirectory:
    offset: int
    central_offset: int
    central_size: int
    entry_count: int


@dataclass(frozen=True)
class Entry:
    index: int
    name: str
    name_bytes: bytes
    flags: int
    method: int
    crc32: int
    compressed_size: int
    uncompressed_size: int
    local_offset: int
    local_header_size: int
    data_offset: int
    central_offset: int
    central_size: int


@dataclass(frozen=True)
class Archive:
    data: bytes
    eocd: EndOfCentralDirectory
    entries: tuple[Entry, ...]


@dataclass(frozen=True)
class Request:
    entry_name: str
    replacement_path: Path
    zlib_level: int


@dataclass(frozen=True)
class Replacement:
    entry: Entry
    plain: bytes
    compressed: bytes
    crc32: int

    @property
    def size_delta(self) -> int:
        return len(self.compressed) - self.entry.compressed_size


def _fail(message: str) -> NoReturn:
    raise ArchiveError(message)


def _unpack_from(fmt: str, data: bytes | bytearray, offset: int, label: str):
    try:
        return struct.unpack_from(fmt, data, offset)
    except struct.error as exc:
        _fail(f"truncated {label} at archive offset 0x{offset:x}: {exc}")


def _find_eocd(data: bytes) -> int:
    search_start = max(0, len(data) - EOCD_FIXED_SIZE - MAX_ZIP_COMMENT_SIZE)
    candidate = data.rfind(EOCD_SIGNATURE, search_start)
    while candidate >= search_start:
        if candidate + EOCD_FIXED_SIZE <= len(data):
            (comment_size,) = _unpack_from(
                "<H", data, candidate + 20, "end-of-central-directory record"
            )
            if candidate + EOCD_FIXED_SIZE + comment_size == len(data):
                return candidate
        candidate = data.rfind(EOCD_SIGNATURE, search_start, candidate)
    _fail("end-of-central-directory record not found")


def _decode_name(name: bytes, flags: int, entry_index: int) -> str:
    encoding = "utf-8" if flags & FLAG_UTF8 else "cp437"
    try:
        return name.decode(encoding)
    except UnicodeDecodeError as exc:
        _fail(f"entry {entry_index} has an invalid {encoding} filename: {exc}")


def _parse_archive(data: bytes) -> Archive:
    eocd_offset = _find_eocd(data)
    (
        disk_number,
        central_disk,
        disk_entries,
        total_entries,
        central_size,
        central_offset,
    ) = _unpack_from("<HHHHII", data, eocd_offset + 4, "EOCD record")

    if (
        disk_number != 0
        or central_disk != 0
        or disk_entries != total_entries
    ):
        _fail("multi-disk ZIP archives are not supported")
    if (
        total_entries == 0xFFFF
        or central_size == 0xFFFFFFFF
        or central_offset == 0xFFFFFFFF
        or data[max(0, eocd_offset - 20) : eocd_offset - 16]
        == ZIP64_LOCATOR_SIGNATURE
    ):
        _fail("ZIP64 archives are not supported")
    if central_offset + central_size != eocd_offset:
        _fail(
            "central-directory bounds are inconsistent: "
            f"offset=0x{central_offset:x}, size={central_size}, "
            f"EOCD=0x{eocd_offset:x}"
        )

    entries: list[Entry] = []
    cursor = central_offset
    central_end = central_offset + central_size
    for index in range(total_entries):
        if cursor + CENTRAL_FIXED_SIZE > central_end:
            _fail(f"truncated central-directory entry {index}")
        if data[cursor : cursor + 4] != CENTRAL_SIGNATURE:
            _fail(
                f"invalid central-directory signature for entry {index} "
                f"at archive offset 0x{cursor:x}"
            )

        flags, method = _unpack_from(
            "<HH", data, cursor + 8, f"central-directory entry {index}"
        )
        crc32, compressed_size, uncompressed_size = _unpack_from(
            "<III", data, cursor + 16, f"central-directory entry {index}"
        )
        name_size, extra_size, comment_size, start_disk = _unpack_from(
            "<HHHH", data, cursor + 28, f"central-directory entry {index}"
        )
        (local_offset,) = _unpack_from(
            "<I", data, cursor + 42, f"central-directory entry {index}"
        )
        if (
            compressed_size == 0xFFFFFFFF
            or uncompressed_size == 0xFFFFFFFF
            or local_offset == 0xFFFFFFFF
        ):
            _fail(f"entry {index} uses unsupported ZIP64 fields")
        if start_disk != 0:
            _fail(f"entry {index} starts on unsupported disk {start_disk}")

        record_size = CENTRAL_FIXED_SIZE + name_size + extra_size + comment_size
        record_end = cursor + record_size
        if record_end > central_end:
            _fail(f"truncated central-directory payload for entry {index}")
        name_bytes = data[
            cursor + CENTRAL_FIXED_SIZE : cursor + CENTRAL_FIXED_SIZE + name_size
        ]
        name = _decode_name(name_bytes, flags, index)

        if local_offset + LOCAL_FIXED_SIZE > central_offset:
            _fail(f"local header for {name!r} is outside the file-data region")
        if data[local_offset : local_offset + 4] != LOCAL_SIGNATURE:
            _fail(
                f"invalid local-header signature for {name!r} "
                f"at archive offset 0x{local_offset:x}"
            )
        local_flags, local_method = _unpack_from(
            "<HH", data, local_offset + 6, f"local header for {name!r}"
        )
        local_name_size, local_extra_size = _unpack_from(
            "<HH", data, local_offset + 26, f"local header for {name!r}"
        )
        local_name_start = local_offset + LOCAL_FIXED_SIZE
        local_name_end = local_name_start + local_name_size
        local_header_size = LOCAL_FIXED_SIZE + local_name_size + local_extra_size
        data_offset = local_offset + local_header_size
        data_end = data_offset + compressed_size
        if data_end > central_offset:
            _fail(f"compressed payload for {name!r} exceeds the file-data region")
        if data[local_name_start:local_name_end] != name_bytes:
            _fail(f"local and central filenames differ for {name!r}")
        if local_flags != flags or local_method != method:
            _fail(f"local and central compression metadata differ for {name!r}")

        if not flags & FLAG_DATA_DESCRIPTOR:
            local_crc, local_compressed_size, local_uncompressed_size = _unpack_from(
                "<III", data, local_offset + 14, f"local header for {name!r}"
            )
            if (
                local_crc != crc32
                or local_compressed_size != compressed_size
                or local_uncompressed_size != uncompressed_size
            ):
                _fail(f"local and central sizes or CRC differ for {name!r}")

        entries.append(
            Entry(
                index=index,
                name=name,
                name_bytes=name_bytes,
                flags=flags,
                method=method,
                crc32=crc32,
                compressed_size=compressed_size,
                uncompressed_size=uncompressed_size,
                local_offset=local_offset,
                local_header_size=local_header_size,
                data_offset=data_offset,
                central_offset=cursor,
                central_size=record_size,
            )
        )
        cursor = record_end

    if cursor != central_end:
        _fail(
            "unsupported record or trailing data inside the central directory "
            f"at archive offset 0x{cursor:x}"
        )

    local_offsets = [entry.local_offset for entry in entries]
    if len(local_offsets) != len(set(local_offsets)):
        _fail("multiple central-directory entries reference the same local header")
    by_local_offset = sorted(entries, key=lambda entry: entry.local_offset)
    for entry, following in zip(by_local_offset, by_local_offset[1:]):
        if entry.data_offset + entry.compressed_size > following.local_offset:
            _fail(f"compressed payloads overlap at entry {entry.name!r}")

    return Archive(
        data=data,
        eocd=EndOfCentralDirectory(
            offset=eocd_offset,
            central_offset=central_offset,
            central_size=central_size,
            entry_count=total_entries,
        ),
        entries=tuple(entries),
    )


def _inflate_raw(data: bytes, label: str) -> bytes:
    inflater = zlib.decompressobj(-zlib.MAX_WBITS)
    try:
        plain = inflater.decompress(data) + inflater.flush()
    except zlib.error as exc:
        _fail(f"cannot inflate {label}: {exc}")
    if not inflater.eof:
        _fail(f"raw-deflate stream for {label} ended prematurely")
    if inflater.unused_data or inflater.unconsumed_tail:
        _fail(f"raw-deflate stream for {label} has trailing or unconsumed data")
    return plain


def _deflate_raw(data: bytes, level: int) -> bytes:
    compressor = zlib.compressobj(
        level,
        zlib.DEFLATED,
        -zlib.MAX_WBITS,
        zlib.DEF_MEM_LEVEL,
        zlib.Z_DEFAULT_STRATEGY,
    )
    return compressor.compress(data) + compressor.flush(zlib.Z_FINISH)


def _read_raw_payload(archive: Archive, entry: Entry) -> bytes:
    end = entry.data_offset + entry.compressed_size
    return archive.data[entry.data_offset:end]


def _prepare_replacements(
    archive: Archive, requests: Sequence[Request]
) -> tuple[Replacement, ...]:
    entries_by_name: dict[str, list[Entry]] = {}
    for entry in archive.entries:
        entries_by_name.setdefault(entry.name, []).append(entry)

    replacements: list[Replacement] = []
    seen_names: set[str] = set()
    for request in requests:
        if request.entry_name in seen_names:
            _fail(f"entry requested more than once: {request.entry_name!r}")
        seen_names.add(request.entry_name)

        matches = entries_by_name.get(request.entry_name, [])
        if not matches:
            _fail(f"entry not found: {request.entry_name!r}")
        if len(matches) != 1:
            _fail(f"entry name is ambiguous: {request.entry_name!r}")
        entry = matches[0]
        if entry.flags & FLAG_ENCRYPTED:
            _fail(f"encrypted entry cannot be replaced: {entry.name!r}")
        if entry.flags & FLAG_DATA_DESCRIPTOR:
            _fail(f"data-descriptor entry cannot be replaced: {entry.name!r}")
        if entry.method != METHOD_DEFLATE:
            _fail(
                f"entry {entry.name!r} uses compression method {entry.method}, "
                "not DEFLATE"
            )

        source_compressed = _read_raw_payload(archive, entry)
        source_plain = _inflate_raw(source_compressed, entry.name)
        if len(source_plain) != entry.uncompressed_size:
            _fail(
                f"uncompressed-size mismatch for {entry.name!r}: "
                f"header={entry.uncompressed_size}, actual={len(source_plain)}"
            )
        source_crc = binascii.crc32(source_plain) & 0xFFFFFFFF
        if source_crc != entry.crc32:
            _fail(
                f"CRC mismatch for {entry.name!r}: "
                f"header={entry.crc32:08x}, actual={source_crc:08x}"
            )

        reproduced = _deflate_raw(source_plain, request.zlib_level)
        if reproduced != source_compressed:
            _fail(
                f"zlib level {request.zlib_level} does not reproduce the raw "
                f"DEFLATE stream for {entry.name!r} byte-for-byte"
            )

        try:
            replacement_plain = request.replacement_path.read_bytes()
        except OSError as exc:
            _fail(f"cannot read replacement {request.replacement_path}: {exc}")
        if len(replacement_plain) > 0xFFFFFFFF:
            _fail(f"replacement is too large for non-ZIP64: {entry.name!r}")
        replacement_compressed = _deflate_raw(
            replacement_plain, request.zlib_level
        )
        if len(replacement_compressed) > 0xFFFFFFFF:
            _fail(
                f"compressed replacement is too large for non-ZIP64: "
                f"{entry.name!r}"
            )
        if (
            _inflate_raw(replacement_compressed, f"replacement for {entry.name}")
            != replacement_plain
        ):
            _fail(f"internal compression verification failed for {entry.name!r}")

        replacements.append(
            Replacement(
                entry=entry,
                plain=replacement_plain,
                compressed=replacement_compressed,
                crc32=binascii.crc32(replacement_plain) & 0xFFFFFFFF,
            )
        )

    return tuple(replacements)


def _build_archive(
    archive: Archive, replacements: Sequence[Replacement]
) -> bytes:
    output = bytearray(archive.data)

    # Update local headers before resizing any payload. Applying payload splices
    # from the end towards the beginning keeps every original offset valid.
    for replacement in replacements:
        entry = replacement.entry
        struct.pack_into(
            "<III",
            output,
            entry.local_offset + 14,
            replacement.crc32,
            len(replacement.compressed),
            len(replacement.plain),
        )
    for replacement in sorted(
        replacements, key=lambda item: item.entry.data_offset, reverse=True
    ):
        entry = replacement.entry
        old_end = entry.data_offset + entry.compressed_size
        output[entry.data_offset:old_end] = replacement.compressed

    total_delta = sum(replacement.size_delta for replacement in replacements)
    new_central_offset = archive.eocd.central_offset + total_delta
    new_eocd_offset = archive.eocd.offset + total_delta
    if not 0 <= new_central_offset <= 0xFFFFFFFF:
        _fail("rebuilt central-directory offset requires ZIP64")
    replacements_by_index = {
        replacement.entry.index: replacement for replacement in replacements
    }

    for entry in archive.entries:
        new_central_entry_offset = entry.central_offset + total_delta
        replacement = replacements_by_index.get(entry.index)
        if replacement is not None:
            struct.pack_into(
                "<III",
                output,
                new_central_entry_offset + 16,
                replacement.crc32,
                len(replacement.compressed),
                len(replacement.plain),
            )

        preceding_delta = sum(
            item.size_delta
            for item in replacements
            if item.entry.data_offset < entry.local_offset
        )
        new_local_offset = entry.local_offset + preceding_delta
        if not 0 <= new_local_offset <= 0xFFFFFFFF:
            _fail(f"rebuilt offset for {entry.name!r} requires ZIP64")
        struct.pack_into(
            "<I",
            output,
            new_central_entry_offset + 42,
            new_local_offset,
        )

    struct.pack_into("<I", output, new_eocd_offset + 16, new_central_offset)
    return bytes(output)


def _verify_archive(
    source: Archive,
    result_data: bytes,
    replacements: Sequence[Replacement],
) -> None:
    result = _parse_archive(result_data)
    if len(result.entries) != len(source.entries):
        _fail("entry count changed while rebuilding the archive")

    replacements_by_index = {
        replacement.entry.index: replacement for replacement in replacements
    }
    for old_entry, new_entry in zip(source.entries, result.entries):
        if old_entry.name_bytes != new_entry.name_bytes:
            _fail(f"entry order or filename changed at index {old_entry.index}")

        replacement = replacements_by_index.get(old_entry.index)
        new_raw = _read_raw_payload(result, new_entry)
        if replacement is None:
            old_raw = _read_raw_payload(source, old_entry)
            if old_raw != new_raw:
                _fail(f"untouched compressed payload changed for {old_entry.name!r}")

            old_central = source.data[
                old_entry.central_offset : old_entry.central_offset
                + old_entry.central_size
            ]
            new_central = bytearray(
                result.data[
                    new_entry.central_offset : new_entry.central_offset
                    + new_entry.central_size
                ]
            )
            struct.pack_into("<I", new_central, 42, old_entry.local_offset)
            if bytes(new_central) != old_central:
                _fail(f"untouched ZIP metadata changed for {old_entry.name!r}")

            old_local = source.data[
                old_entry.local_offset : old_entry.data_offset
            ]
            new_local = result.data[
                new_entry.local_offset : new_entry.data_offset
            ]
            if old_local != new_local:
                _fail(f"untouched local header changed for {old_entry.name!r}")
            continue

        plain = _inflate_raw(new_raw, f"rebuilt {new_entry.name}")
        if new_raw != replacement.compressed:
            _fail(f"rebuilt compressed stream differs for {new_entry.name!r}")
        if plain != replacement.plain:
            _fail(f"rebuilt payload differs for {new_entry.name!r}")
        if (
            new_entry.crc32 != replacement.crc32
            or new_entry.compressed_size != len(replacement.compressed)
            or new_entry.uncompressed_size != len(replacement.plain)
        ):
            _fail(f"rebuilt sizes or CRC differ for {new_entry.name!r}")

        old_central = source.data[
            old_entry.central_offset : old_entry.central_offset
            + old_entry.central_size
        ]
        new_central = bytearray(
            result.data[
                new_entry.central_offset : new_entry.central_offset
                + new_entry.central_size
            ]
        )
        new_central[16:28] = old_central[16:28]
        struct.pack_into("<I", new_central, 42, old_entry.local_offset)
        if bytes(new_central) != old_central:
            _fail(f"unrelated ZIP metadata changed for {new_entry.name!r}")

        old_local = source.data[old_entry.local_offset : old_entry.data_offset]
        new_local = bytearray(
            result.data[new_entry.local_offset : new_entry.data_offset]
        )
        new_local[14:26] = old_local[14:26]
        if bytes(new_local) != old_local:
            _fail(f"unrelated local-header metadata changed for {new_entry.name!r}")

    expected_size = len(source.data) + sum(
        replacement.size_delta for replacement in replacements
    )
    if len(result_data) != expected_size:
        _fail(
            f"rebuilt archive size mismatch: expected={expected_size}, "
            f"actual={len(result_data)}"
        )

    old_eocd = source.data[source.eocd.offset :]
    new_eocd = bytearray(result.data[result.eocd.offset :])
    struct.pack_into("<I", new_eocd, 16, source.eocd.central_offset)
    if bytes(new_eocd) != old_eocd:
        _fail("unrelated end-of-central-directory metadata changed")


def _write_atomic(path: Path, data: bytes) -> None:
    try:
        source_mode = stat.S_IMODE(path.stat().st_mode)
    except OSError as exc:
        _fail(f"cannot stat archive {path}: {exc}")

    temporary_path: Path | None = None
    try:
        with tempfile.NamedTemporaryFile(
            mode="wb",
            prefix=f".{path.name}.",
            suffix=".tmp",
            dir=path.parent,
            delete=False,
        ) as temporary:
            temporary_path = Path(temporary.name)
            temporary.write(data)
            temporary.flush()
            os.fsync(temporary.fileno())
        os.chmod(temporary_path, source_mode)
        os.replace(temporary_path, path)
        temporary_path = None
    except OSError as exc:
        _fail(f"cannot atomically replace archive {path}: {exc}")
    finally:
        if temporary_path is not None:
            try:
                temporary_path.unlink()
            except FileNotFoundError:
                pass


def _parse_arguments(argv: Sequence[str]) -> tuple[Path, tuple[Request, ...]]:
    parser = argparse.ArgumentParser(
        description=(
            "Replace raw-deflated ZIP entries without recompressing any "
            "untouched entry. The APK is updated atomically in place."
        ),
        usage=(
            "%(prog)s APK ENTRY REPLACEMENT LEVEL "
            "[ENTRY REPLACEMENT LEVEL ...]"
        ),
    )
    parser.add_argument("apk", type=Path, help="ZIP/APK file to update in place")
    parser.add_argument(
        "replacement",
        nargs="+",
        metavar="ENTRY/REPLACEMENT/LEVEL",
        help="one or more entry-name, replacement-file, zlib-level triples",
    )
    arguments = parser.parse_args(argv)

    if len(arguments.replacement) % 3:
        parser.error(
            "replacement arguments must be triples: ENTRY REPLACEMENT LEVEL"
        )
    requests: list[Request] = []
    for index in range(0, len(arguments.replacement), 3):
        entry_name, replacement_path, raw_level = arguments.replacement[
            index : index + 3
        ]
        try:
            level = int(raw_level, 10)
        except ValueError:
            parser.error(f"invalid zlib level {raw_level!r}; expected an integer 0-9")
        if not 0 <= level <= 9:
            parser.error(f"invalid zlib level {level}; expected 0-9")
        requests.append(
            Request(
                entry_name=entry_name,
                replacement_path=Path(replacement_path),
                zlib_level=level,
            )
        )
    return arguments.apk, tuple(requests)


def main(argv: Sequence[str] | None = None) -> int:
    archive_path, requests = _parse_arguments(
        sys.argv[1:] if argv is None else argv
    )
    try:
        source_data = archive_path.read_bytes()
    except OSError as exc:
        print(f"error: cannot read archive {archive_path}: {exc}", file=sys.stderr)
        return 2

    try:
        source = _parse_archive(source_data)
        replacements = _prepare_replacements(source, requests)
        result_data = _build_archive(source, replacements)
        _verify_archive(source, result_data, replacements)
        _write_atomic(archive_path, result_data)
    except ArchiveError as exc:
        print(f"error: {exc}", file=sys.stderr)
        return 2

    for replacement in replacements:
        print(
            f"replaced {replacement.entry.name}: "
            f"{replacement.entry.compressed_size} -> "
            f"{len(replacement.compressed)} compressed bytes "
            f"(zlib level verified)"
        )
    print(f"updated atomically: {archive_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
