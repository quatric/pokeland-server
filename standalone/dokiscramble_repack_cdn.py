#!/usr/bin/env python3
"""DokiScramble: rebuild the Android CDN asset tree of Pokemon Rumble Rush 1.6.0 from the iOS one.

Only the iOS asset tree (<AssetVer>/iOS) of 1.6.0 was archived by the Wayback Machine. This script
turns it into the Android tree (<AssetVer>/Android) the patched Android client downloads, replaying
in one run, in order, every step that produced the production tree:

  1. convert      every bundle: SerializedFile target_platform iOS (9) -> Android (13), and every
                  PVRTC texture (PowerVR only) re-encoded to ETC2_RGBA8. Saved with UnityPy's
                  packer="original" (the source's single LZMA block).        [repack_android.py]
  2. shaders      every Shader whose platforms list GLES20 is relabelled GLES3Plus in place:
                  platforms 5 -> 9, the GLES blob's m_ProgramType 5 -> 4 (re-LZ4HC'd), and
                  m_ParsedForm's m_GpuProgramType 5 -> 4. Metal is kept. Saved again with
                  packer="original".                                          [promote_gles3.py]
  3. root manifest  the converted iOS root manifest is written as both "iOS" (as converted) and
                  "Android", the name the client requests (<AssetVer>/Android/Android).
  4. variants     language variants of illustrations/l10n that the archive never captured are
                  filled with the en-us art: a copy of l10n.en-us whose AssetBundle m_Name says
                  the variant (illustrations/l10n.de-de, ...). Same CAB, same objects. This was a
                  manual step done once before the first CDN push.
  5. content fixes (only with --fixes, they need the server's game tables):
                  a. text: the MSBT text bundles text/<lang> of 8 languages, for content the global
                     client never named (zones, cups, species, gear, kits, honors, ...).
                                                                         [patch_text_bundles.py]
                  b. welcal: welcal/bospw2..4, never archived, rebuilt from welcal/bosp with their
                     own CAB, bundle name, prefabs and day layouts.   [build_welcal_bundles.py]
                  c. gear: a GEAR sprite in the "Gaiji" TMP sprite asset of fonts/common, and the
                     field gear drop label of scene-assets/battle pointed at it.
                                                                           [patch_gear_sprite.py]
                  then the AssetBundleHash of every bundle these fixes wrote is set to the MD5 of
                  the bundle file in the "Android" root manifest (so clients download them again),
                  which is saved with packer="original". The "iOS" copy is left as converted.
                                         [the hash rewrites of patch_text_bundles.py and
                                          /tmp/scr/publish_{welcal,bundles}.py]
  6. size_manifest.json  {"AssetSizeInfos":[{"AssetSizeItems":[{Name:"Android/<path>",Size}],
                  "AssetVer":""}]}, every file of the output tree, sorted by name, compact JSON.
  7. verify       reload every bundle and check: target_platform Android, no PVRTC texture left,
                  every shader has GLES3Plus with GLES3-class blob subprograms and no GLES
                  m_GpuProgramType left, the root manifests and size_manifest.json exist, and
                  (added) size_manifest.json matches the tree.                [verify_android.py]

Without --fixes the result is the converted tree (steps 1-4, 6, 7): it loads and renders on
Android, but text/*, fonts/common, scene-assets/battle and the root manifest are the archived
versions and welcal/bospw2..4 do not exist. With --fixes it is the production tree, byte for byte.

Usage:
  dokiscramble_repack_cdn.py --ios <.../iOS> --out <.../Android> [--fixes <repo>/dokiheroes_py]
                             [--jobs N] [--overwrite] [--no-verify]

  --ios       the archived iOS tree (the directory holding the "iOS" root manifest).
  --out       where to write the Android tree. Must be empty or missing unless --overwrite.
  --fixes     the server package directory (the one containing "pokeland/"); enables step 5.
              Only pokeland.master (tables from pokeland/resources/master.json.gz) and
              pokeland.protocol (enums) are imported, both pure stdlib.
  --jobs      worker processes for steps 1-2 and 7 (default: CPU count).

Dependencies:
  core (steps 1-4, 6, 7): Python 3 (3.11 tested) with UnityPy 1.25.4, which pulls in what it
  needs here: etcpak (ETC2 encoder), texture2ddecoder (PVRTC decoder), Pillow, lz4 (python-lz4,
  also used directly for the shader blobs). The stock lzma module (liblzma) does the LZMA.
  Byte identity with the production tree was checked with exactly: UnityPy 1.25.4, etcpak
  0.9.15, texture2ddecoder 1.0.6, lz4 4.4.5, Pillow 12.3.0, CPython 3.11.2 / Debian 12 liblzma
  (the /tmp/scr/upy venv on the VPS). Other versions of the encoders may change bytes.
  --fixes: additionally the pokeland package directory (no extra pip package).

Exit status: 0 on success, 1 when a bundle fails to convert, a fix fails or verification fails.
"""
import argparse
import collections
import copy
import hashlib
import json
import multiprocessing
import os
import random
import re
import shutil
import struct
import sys
import time

import UnityPy
import lz4.block
from UnityPy.enums import BuildTarget, TextureFormat

IOS_ROOT_MANIFEST = "iOS"          # Unity names the root manifest after its build folder
ANDROID_ROOT_MANIFEST = "Android"  # what the Android client requests: <AssetVer>/Android/Android
SIZE_MANIFEST = "size_manifest.json"
SIZE_PREFIX = "Android/"           # names in size_manifest.json are CDN paths under the AssetVer

PVRTC = {
    int(TextureFormat.PVRTC_RGB2), int(TextureFormat.PVRTC_RGBA2),
    int(TextureFormat.PVRTC_RGB4), int(TextureFormat.PVRTC_RGBA4),
}
GLES20, GLES3PLUS, METAL = 5, 9, 14          # ShaderCompilerPlatform
GPU_GLES, GPU_GLES3 = 5, 4                   # ShaderGpuProgramType
GPU_OK = {2, 3, 4}                           # GLES31AEP, GLES31, GLES3: what GLES3Plus accepts
PROG_STAGES = ("progVertex", "progFragment", "progGeometry", "progHull", "progDomain")

#: Variant bundles whose missing variants are filled from one archived variant (step 4).
#: The CJK font variants (fonts/sourcehansans.fonts{kr,sc,tc}) are deliberately not cloned:
#: a Japanese font cannot stand in for them, and production does not carry them either.
CLONE_VARIANTS = {"illustrations/l10n": "en-us"}


def log(msg):
    print(msg, flush=True)


def tree_files(root, skip=()):
    """Relative '/'-separated paths of every file under root, sorted."""
    out = []
    for dirpath, _, files in os.walk(root):
        for f in files:
            rel = os.path.relpath(os.path.join(dirpath, f), root).replace(os.sep, "/")
            if not rel.endswith(skip):
                out.append(rel)
    return sorted(out)


def write_bytes(path, blob):
    os.makedirs(os.path.dirname(path) or ".", exist_ok=True)
    with open(path, "wb") as fh:
        fh.write(blob)


# =============================================================== steps 1-2: convert + shaders
def convert(src):
    """Step 1 (repack_android.convert): returns (bundle bytes, PVRTC textures re-encoded)."""
    env = UnityPy.load(src)
    converted = 0
    for obj in env.objects:
        if obj.type.name != "Texture2D":
            continue
        data = obj.read()
        if int(data.m_TextureFormat) not in PVRTC:
            continue
        # Round-trip through RGBA; ETC2_RGBA8 keeps the alpha PVRTC_RGBA* had.
        data.set_image(data.image, target_format=TextureFormat.ETC2_RGBA8)
        data.save()
        converted += 1
    # SerializedFile.save() writes the raw _m_target_platform, not the BuildTarget property.
    for bundle in env.files.values():
        for sf in getattr(bundle, "files", {}).values():
            if hasattr(sf, "_m_target_platform"):
                sf._m_target_platform = int(BuildTarget.Android)
                sf.target_platform = BuildTarget.Android
    return env.file.save(packer="original"), converted


def _patch_blob(dec, name):
    """GLES blob: uint32 count, count x (uint32 offset, uint32 length); at each offset int32
    version, uint32 m_ProgramType. Rewrites every m_ProgramType GLES (5) -> GLES3 (4)."""
    buf = bytearray(dec)
    count, = struct.unpack_from("<I", buf, 0)
    for k in range(count):
        off, _len = struct.unpack_from("<II", buf, 4 + 8 * k)
        ptype, = struct.unpack_from("<I", buf, off + 4)
        if ptype != GPU_GLES:
            raise ValueError(f"{name}: blob sub{k} has m_ProgramType {ptype}, expected {GPU_GLES}")
        struct.pack_into("<I", buf, off + 4, GPU_GLES3)
    return bytes(buf), count


def _patch_parsed_form(parsed):
    patched = 0
    for sub in parsed.get("m_SubShaders") or []:
        for pas in sub.get("m_Passes") or []:
            for stage in PROG_STAGES:
                for sp in ((pas.get(stage) or {}).get("m_SubPrograms") or []):
                    if sp["m_GpuProgramType"] == GPU_GLES:
                        sp["m_GpuProgramType"] = GPU_GLES3
                        patched += 1
    return patched


def promote_shader(obj):
    """Step 2 (promote_gles3.promote, without --only/--strip-metal). Returns (name, blob_subs)
    or None when the shader has no GLES20 platform."""
    tt = obj.read_typetree()
    name = tt["m_ParsedForm"]["m_Name"]
    platforms = list(tt["platforms"])
    if GLES20 not in platforms:
        return None
    blob = bytes(tt["compressedBlob"])
    offsets, clens = list(tt["offsets"]), list(tt["compressedLengths"])
    dlens = list(tt["decompressedLengths"])
    segments, blob_subs = [], 0
    for i, plat in enumerate(platforms):
        raw = blob[offsets[i]:offsets[i] + clens[i]]
        if plat != GLES20:
            segments.append((plat, raw, dlens[i]))        # other platforms verbatim
            continue
        dec = lz4.block.decompress(raw, uncompressed_size=dlens[i])
        dec, blob_subs = _patch_blob(dec, name)
        segments.append((GLES3PLUS, lz4.block.compress(dec, mode="high_compression",
                                                       store_size=False), len(dec)))
    parsed_subs = _patch_parsed_form(tt["m_ParsedForm"])
    # A build-stripped shader has an empty blob and an empty table: relabel it all the same.
    if bool(blob_subs) != bool(parsed_subs):
        raise ValueError(f"{name}: blob has {blob_subs} GLES subprogram(s) but m_ParsedForm "
                         f"has {parsed_subs}")
    new_blob, plats, offs, cls, dls = b"", [], [], [], []
    for plat, raw, dlen in segments:
        plats.append(plat)
        offs.append(len(new_blob))
        cls.append(len(raw))
        dls.append(dlen)
        new_blob += raw
    tt["platforms"], tt["offsets"] = plats, offs
    tt["compressedLengths"], tt["decompressedLengths"] = cls, dls
    tt["compressedBlob"] = new_blob
    obj.save_typetree(tt)
    return name, blob_subs


def promote(blob):
    """Step 2 on one bundle, as a separate load/save of the step 1 output (as on the CDN).
    Returns (new bytes or None when no shader needed it, promoted, stubs)."""
    env = UnityPy.load(blob)
    done = []
    for obj in env.objects:
        if obj.type.name == "Shader":
            res = promote_shader(obj)
            if res:
                done.append(res)
    if not done:
        return None, 0, 0
    stubs = sum(1 for _, subs in done if not subs)
    return env.file.save(packer="original"), len(done) - stubs, stubs


def convert_worker(job):
    rel, src, dst = job
    t0 = time.time()
    try:
        blob, textures = convert(src)
        promoted, shaders, stubs = promote(blob)
        if promoted is not None:
            blob = promoted
        write_bytes(dst, blob)
        return rel, None, textures, shaders, stubs, len(blob), time.time() - t0
    except Exception as e:  # reported, fails the run
        return rel, f"{type(e).__name__}: {e}", 0, 0, 0, 0, time.time() - t0


# =============================================================== steps 3-4: manifests, variants
def manifest_tree(env):
    for obj in env.objects:
        if obj.type.name == "AssetBundleManifest":
            return obj, obj.read_typetree()
    raise ValueError("no AssetBundleManifest in the root manifest")


def clone_variants(out, manifest=ANDROID_ROOT_MANIFEST):
    """Step 4: missing variants of CLONE_VARIANTS bundles, as copies of the archived one."""
    _, tree = manifest_tree(UnityPy.load(os.path.join(out, manifest)))
    names = dict(tree["AssetBundleNames"])
    variants = sorted(names[k] for k in tree["AssetBundlesWithVariant"])
    made = []
    for base, source_variant in CLONE_VARIANTS.items():
        source = os.path.join(out, f"{base}.{source_variant}")
        if not os.path.exists(source):
            log(f"  {base}.{source_variant} missing from the archive, no variant cloned")
            continue
        for name in variants:
            if not name.startswith(base + ".") or os.path.exists(os.path.join(out, name)):
                continue
            env = UnityPy.load(source)
            for obj in env.objects:
                if obj.type.name == "AssetBundle":
                    t = obj.read_typetree()
                    t["m_Name"] = name           # m_AssetBundleName stays the variant-less base
                    obj.save_typetree(t)
            write_bytes(os.path.join(out, name), env.file.save(packer="original"))
            made.append(name)
    return made


def set_manifest_hashes(out, blobs, manifest=ANDROID_ROOT_MANIFEST):
    """AssetBundleHash := MD5 of the bundle file, for every bundle in blobs, in the root
    manifest (the hash the client compares with its cache)."""
    path = os.path.join(out, manifest)
    env = UnityPy.load(path)
    obj, tree = manifest_tree(env)
    ids = dict(tree["AssetBundleNames"])
    seen = set()
    for key, info in tree["AssetBundleInfos"]:
        if ids[key] in blobs:
            digest = hashlib.md5(blobs[ids[key]]).digest()
            info["AssetBundleHash"] = {f"bytes[{i}]": b for i, b in enumerate(digest)}
            seen.add(ids[key])
    if seen != set(blobs):
        raise ValueError(f"bundles missing from the manifest: {sorted(set(blobs) - seen)}")
    obj.save_typetree(tree)
    write_bytes(path, env.file.save(packer="original"))


def write_size_manifest(out, prefix=SIZE_PREFIX):
    sizes = {prefix + rel: os.path.getsize(os.path.join(out, rel))
             for rel in tree_files(out) if rel != SIZE_MANIFEST}
    manifest = {"AssetSizeInfos": [{
        "AssetSizeItems": [{"Name": k, "Size": v} for k, v in sorted(sizes.items())],
        "AssetVer": "",
    }]}
    with open(os.path.join(out, SIZE_MANIFEST), "w") as fh:
        json.dump(manifest, fh, separators=(",", ":"))
    return len(sizes), sum(sizes.values())


# =============================================================== step 5a: text bundles
LANGS = ["fr", "en-us", "de", "it", "es", "ko", "zh-tw", "ja"]


def msbt_label_hash(label, slots):
    h = 0
    for c in label:
        h = (h * 0x492 + ord(c)) & 0xFFFFFFFF
    return h % slots


class Msbt:
    """MsgStdBn with LBL1 + TXT2 sections, UTF-16LE."""

    def __init__(self, b):
        assert b[:8] == b"MsgStdBn" and b[8:10] == b"\xff\xfe", b[:10]
        self.header = bytearray(b[:0x20])
        nsec = struct.unpack("<H", b[0x0E:0x10])[0]
        pos = 0x20
        self.slots = None
        self.messages = []
        for _ in range(nsec):
            magic = b[pos:pos + 4]
            size = struct.unpack("<I", b[pos + 4:pos + 8])[0]
            data = b[pos + 16:pos + 16 + size]
            if magic == b"LBL1":
                n = struct.unpack("<I", data[:4])[0]
                self.slots = []
                for i in range(n):
                    cnt, off = struct.unpack("<II", data[4 + 8 * i:12 + 8 * i])
                    p = off
                    entries = []
                    for _ in range(cnt):
                        ln = data[p]
                        name = data[p + 1:p + 1 + ln].decode("ascii")
                        idx = struct.unpack("<I", data[p + 1 + ln:p + 5 + ln])[0]
                        entries.append([name, idx])
                        p += 5 + ln
                    self.slots.append(entries)
            elif magic == b"TXT2":
                n = struct.unpack("<I", data[:4])[0]
                offs = [struct.unpack("<I", data[4 + 4 * i:8 + 4 * i])[0] for i in range(n)] + [size]
                self.messages = [bytes(data[offs[i]:offs[i + 1]]) for i in range(n)]
            else:
                raise ValueError(f"unexpected section {magic}")
            pos = (pos + 16 + size + 15) & ~15
        self.index = {name: idx for slot in self.slots for name, idx in slot}

    def get(self, label):
        i = self.index.get(label)
        return None if i is None else self.messages[i]

    def set(self, label, raw):
        if label in self.index:
            self.messages[self.index[label]] = raw
        else:
            self.messages.append(raw)
            idx = len(self.messages) - 1
            self.index[label] = idx
            self.slots[msbt_label_hash(label, len(self.slots))].append([label, idx])

    def build(self):
        def section(magic, data):
            body = struct.pack("<4sI8x", magic, len(data)) + data
            return body + b"\xab" * ((-len(body)) % 16)
        n = len(self.slots)
        table = b""
        names = b""
        base = 4 + 8 * n
        for entries in self.slots:
            table += struct.pack("<II", len(entries), base + len(names))
            for name, idx in entries:
                nb = name.encode("ascii")
                names += bytes([len(nb)]) + nb + struct.pack("<I", idx)
        lbl = struct.pack("<I", n) + table + names
        offs = []
        blob = b""
        start = 4 + 4 * len(self.messages)
        for m in self.messages:
            offs.append(start + len(blob))
            blob += m
        txt = struct.pack("<I", len(self.messages)) + b"".join(struct.pack("<I", o) for o in offs) + blob
        out = bytearray(self.header) + section(b"LBL1", lbl) + section(b"TXT2", txt)
        struct.pack_into("<H", out, 0x0E, 2)
        struct.pack_into("<I", out, 0x12, len(out))
        return bytes(out)


def msbt_tokens(raw):
    """Split a raw message into ('t', str) text and ('g', bytes) tag tokens (terminator dropped)."""
    out, j = [], 0
    while j + 1 < len(raw):
        c = struct.unpack("<H", raw[j:j + 2])[0]
        if c == 0x0E:
            sz = struct.unpack("<H", raw[j + 6:j + 8])[0]
            out.append(("g", raw[j:j + 8 + sz]))
            j += 8 + sz
        elif c == 0x0F:
            out.append(("g", raw[j:j + 6]))
            j += 6
        elif c == 0:
            break
        else:
            out.append(("t", chr(c)))
            j += 2
    return out


def msbt_text(raw):
    return "".join(v if k == "t" else "{tag}" for k, v in msbt_tokens(raw))


def msbt_tag_runs(raw):
    runs, cur = [], b""
    for k, v in msbt_tokens(raw):
        if k == "g":
            cur += v
        elif cur:
            runs.append(cur)
            cur = b""
    if cur:
        runs.append(cur)
    return runs


def msbt_render(template, runs=()):
    """Template text with {T1}.. placeholders for the source message's tag runs -> raw message."""
    out = b""
    for part in re.split(r"(\{T\d\})", template):
        m = re.fullmatch(r"\{T(\d)\}", part)
        if m:
            out += runs[int(m.group(1)) - 1]
        elif part:
            out += part.encode("utf-16-le")
    return out + b"\x00\x00"


# Santa Rowlet form.
TEXT_SANTA = {"fr": "Brindibou Père Noël", "en-us": "Santa Rowlet", "de": "Weihnachts-Bauz",
              "it": "Rowlet Babbo Natale", "es": "Rowlet Papá Noel", "ko": "산타 나몰빼미",
              "zh-tw": "聖誕老人木木梟", "ja": "サンタモクロー"}
# Zone names of the last six Japanese events (NAME__29.._34), built around localized species.
TEXT_ZONES = {
    "_29": ({"fr": "{A} contre {B}", "en-us": "{A} vs. {B}", "de": "{A} gegen {B}", "it": "{A} contro {B}",
             "es": "{A} contra {B}", "ko": "{A} VS {B}", "zh-tw": "{A} V.S. {B}"}, ["シルヴァディ", "マッシブーン"]),
    "_30": ({"fr": "{A} et le ciel étoilé", "en-us": "{A} and the Starry Sky", "de": "{A} und der Sternenhimmel",
             "it": "{A} e il cielo stellato", "es": "{A} y el cielo estrellado", "ko": "{A}와 별하늘",
             "zh-tw": "{A}與星空"}, ["ジラーチ"]),
    "_31": ({"fr": "La fête d'Halloween de {A}", "en-us": "{A}'s Halloween Party", "de": "{A}s Halloween-Party",
             "it": "La festa di Halloween di {A}", "es": "La fiesta de Halloween de {A}", "ko": "{A}의 핼러윈 파티",
             "zh-tw": "{A}的萬聖節派對"}, ["ミミッキュ"]),
    "_32": ({"fr": "{A} et le gardien Z", "en-us": "{A} and the Watcher Z", "de": "{A} und der Wächter Z",
             "it": "{A} e il guardiano Z", "es": "{A} y el vigilante Z", "ko": "{A}와 감시자 Z",
             "zh-tw": "{A}與監視者Z"}, ["ニンフィア"]),
    "_33": ({"fr": "{A} et la grotte génétique", "en-us": "{A} and the Genetic Cave", "de": "{A} und die Genhöhle",
             "it": "{A} e la grotta genetica", "es": "{A} y la cueva genética", "ko": "{A}와 초유전의 동굴",
             "zh-tw": "{A}與超遺傳洞窟"}, ["グソクムシャ"]),
    "_34": ({"fr": "Le Noël alolien de {A}", "en-us": "{A}'s Alolan Christmas", "de": "Alola-Weihnachten mit {A}",
             "it": "Il Natale di Alola di {A}", "es": "La Navidad alolana de {A}", "ko": "{A}와 알로라의 크리스마스",
             "zh-tw": "{A}與阿羅拉的聖誕節"}, ["モクロー"]),
}
# Team battle cups and the P rush.
TEXT_CUP = {"fr": "Coupe {A}", "en-us": "{A} Cup", "de": "{A}-Cup", "it": "Coppa {A}", "es": "Copa {A}",
            "ko": "{A}배", "zh-tw": "{A}杯"}
TEXT_PRUSH = {"fr": "Ruée aux P", "en-us": "P Rush", "de": "P-Rausch", "it": "Corsa ai P", "es": "Fiebre de P",
              "ko": "P 러시", "zh-tw": "P衝衝衝", "ja": "Pラッシュ"}
# Totem rush tutorial islands.
TEXT_TOTEM = {
    "TotemRush_Tutorial_Lv1": {"fr": "Qualifications : 1er tour", "en-us": "Prelims: Round 1", "de": "Vorrunde: 1. Runde",
                               "it": "Eliminatorie: 1º turno", "es": "Eliminatorias: 1.ª ronda", "ko": "예선 1회전",
                               "zh-tw": "預選第一回合"},
    "TotemRush_Tutorial_Lv2": {"fr": "Qualifications : finale", "en-us": "Prelims: Final", "de": "Vorrunde: Finale",
                               "it": "Eliminatorie: finale", "es": "Eliminatorias: final", "ko": "예선 결승",
                               "zh-tw": "預選決賽"},
    "TotemRush_Tutorial_Lv3": {"fr": "Classe Normale", "en-us": "Normal Class", "de": "Normale Klasse",
                               "it": "Classe Normale", "es": "Clase Normal", "ko": "노멀 클래스", "zh-tw": "普通級"},
    "TotemRush_Tutorial_Lv4": {"fr": "Classe Super", "en-us": "Super Class", "de": "Super-Klasse", "it": "Classe Super",
                               "es": "Clase Súper", "ko": "슈퍼 클래스", "zh-tw": "超級級"},
}
# Pokemon+ gear and their kits.
TEXT_GEAR = {"fr": "{A} +", "en-us": "{A} Boost", "de": "{A}-Boost", "it": "{A} Plus", "es": "{A} +",
             "ko": "{A}플러스", "zh-tw": "{A}加強"}
TEXT_KIT = {"fr": "Kit de Renforcement {G}", "en-us": "Upgrade Kit for {G}", "de": "Kit für {G}",
            "it": "Set upgrade {G}", "es": "Kit Refuerzo {G}", "ko": "{G} 강화키트", "zh-tw": "{G}強化組件"}
# Strings left untranslated. Applied only where the current value still equals the untranslated
# source (Japanese, or English for the European languages).
TEXT_FIX = {
    ("GenerationDesc", "REGION_SWORD_SHIELD"): {"fr": "Galar", "en-us": "Galar", "de": "Galar", "it": "Galar",
                                                 "es": "Galar", "ko": "가라르", "zh-tw": "伽勒爾"},
    ("HonorDesc", "TITLE__1"): {"fr": "Aventurier débutant", "en-us": "Novice Adventurer", "de": "Abenteuer-Neuling",
                                "it": "Avventuriero principiante", "es": "Aventurero novato", "ko": "신참 모험가",
                                "zh-tw": "新手冒險家"},
    ("HonorDesc", "TITLE__2"): {"fr": "Enquêteur ({T1})", "en-us": "{T1} Researcher", "de": "{T1}-Forscher",
                                "it": "Ricercatore {T1}", "es": "Investigador del {T1}", "ko": "{T1} 조사원",
                                "zh-tw": "{T1}調查員"},
    ("HonorDesc", "TITLE__3"): {"fr": "Enquêteur ultime", "en-us": "Ultimate Researcher", "de": "Ultimativer Forscher",
                                "it": "Ricercatore eccezionale", "es": "Investigador máximo", "ko": "궁극의 조사원",
                                "zh-tw": "究極的調查員"},
    ("HonorDesc", "TITLE__4"): {"fr": "Collectionneur ({T1})", "en-us": "{T1} Collector", "de": "{T1}-Sammler",
                                "it": "Collezionista {T1}", "es": "Coleccionista del {T1}", "ko": "{T1} 컬렉터",
                                "zh-tw": "{T1}收藏家"},
    ("HonorDesc", "TITLE__5"): {"fr": "Collectionneur absolu", "en-us": "Ultimate Collector", "de": "Ultimativer Sammler",
                                "it": "Collezionista supremo", "es": "Coleccionista supremo", "ko": "궁극의 컬렉터",
                                "zh-tw": "究極的收藏家"},
    ("HonorDesc", "TITLE__6"): {"fr": "Maître ({T1})", "en-us": "{T1} Master", "de": "{T1}-Meister",
                                "it": "Maestro {T1}", "es": "Maestro del {T1}", "ko": "{T1} 마스터", "zh-tw": "{T1}大師"},
    ("HonorDesc", "TITLE__7"): {"fr": "Maître ultime", "en-us": "Ultimate Master", "de": "Ultimativer Meister",
                                "it": "Maestro supremo", "es": "Maestro supremo", "ko": "궁극의 마스터",
                                "zh-tw": "究極的大師"},
    ("HonorDesc", "CATEGORY__2"): {"fr": "Enquêteur Pokémon", "en-us": "Pokémon Researcher", "de": "Pokémon-Forscher",
                                   "it": "Ricercatore di Pokémon", "es": "Investigador Pokémon", "ko": "포켓몬 조사원",
                                   "zh-tw": "寶可夢調查員"},
    ("HonorDesc", "CATEGORY__4"): {"fr": "Collectionneur Pokémon", "en-us": "Pokémon Collector", "de": "Pokémon-Sammler",
                                   "it": "Collezionista di Pokémon", "es": "Coleccionista Pokémon",
                                   "ko": "포켓몬 컬렉터", "zh-tw": "寶可夢收藏家"},
    ("HonorDesc", "CATEGORY__6"): {"fr": "Maître Pokémon", "en-us": "Pokémon Master", "de": "Pokémon-Meister",
                                   "it": "Maestro di Pokémon", "es": "Maestro Pokémon", "ko": "포켓몬 마스터",
                                   "zh-tw": "寶可夢大師"},
    ("MissionBonusDesc", "EQUNIT"): {"fr": "Rouages", "en-us": "Power Gears", "de": "Bauteile", "it": "Power kit",
                                     "es": "Engranajes", "ko": "파워기어", "zh-tw": "力量齒輪"},
    ("MissionBonusDesc", "EQBIT"): {"fr": "Kits de Renforcement", "en-us": "Upgrade Kits", "de": "Kits",
                                    "it": "Set upgrade", "es": "Kits de Refuerzo", "ko": "강화키트", "zh-tw": "強化組件"},
    ("MissionBonusDesc", "UTENSIL"): {"fr": "Objets", "en-us": "Items", "de": "Gegenstände", "it": "Strumenti",
                                      "es": "Objetos", "ko": "도구"},
    ("MissionBonusDesc", "Pdeco"): {"fr": "Poké Déco", "en-us": "Poké Glam", "de": "Pokémon-Deko", "it": "Decorazioni",
                                    "es": "Pokéadornos", "ko": "포켓데코", "zh-tw": "寶可彩飾"},
    ("MissionMessageDesc", "MMD_TARGET_DIAMOND_CONSUMPTION"): {
        "fr": "Utiliser {T1} au total", "en-us": "Use a total of {T1}", "de": "Setze insgesamt {T1} ein",
        "it": "Usa un totale di {T1}", "es": "Usa un total de {T1}", "ko": "누계 {T1} 사용하자", "zh-tw": "累計使用{T1}"},
    ("MissionMessageDesc", "MMD_HINT_DIAMOND_CONSUMPTION"): {
        "fr": "Programme de fidélité", "en-us": "Mileage", "de": "Bonusprogramm", "it": "Programma fedeltà",
        "es": "Programa de fidelidad", "ko": "마일리지", "zh-tw": "累積里程"},
    ("PdecoDesc", "NONE"): {"fr": "Aucune", "en-us": "None", "de": "Keine", "it": "Nessuna", "es": "Ninguna",
                            "ko": "없음", "zh-tw": "無"},
    ("main", "Shop_0129"): {"fr": "Veuillez réessayer\nplus tard.", "en-us": "Please try again\nlater.",
                            "de": "Bitte versuche es\nspäter erneut.", "it": "Riprova\npiù tardi.",
                            "es": "Vuelve a intentarlo\nmás tarde.", "ko": "잠시 후에\n다시 시도해 주세요.",
                            "zh-tw": "請稍後\n再試一次。"},
    ("main", "HomeMenu_0003"): {"fr": "Partager la photo", "en-us": "Share photo", "de": "Foto teilen",
                                "it": "Condividi la foto", "es": "Compartir foto", "ko": "사진을 공유합니다",
                                "zh-tw": "分享照片"},
    ("main", "CampPageMain_CompleteInfo_0001"): {"fr": "Complet !", "en-us": "Complete!", "de": "Komplett!",
                                                 "it": "Completato!", "es": "¡Completo!", "ko": "컴플리트",
                                                 "zh-tw": "完成"},
    ("main", "BuyStoreSize_0001"): {"fr": "Stockage", "en-us": "Storage", "de": "Lagerplatz", "it": "Spazio",
                                    "es": "Almacén", "ko": "보관장소", "zh-tw": "存放處"},
    ("main", "EvedefSelector_0001"): {"fr": "Mer actuelle", "en-us": "Current sea", "de": "Aktuelles Seegebiet",
                                      "it": "Mare attuale", "es": "Mar actual", "ko": "현재 해역입니다",
                                      "zh-tw": "目前的海域"},
    ("scenes", "AnnouncementSelector_0002"): {"fr": "Tout marquer comme lu", "en-us": "Mark all as read",
                                              "de": "Alle als gelesen markieren", "it": "Segna tutto come letto",
                                              "es": "Marcar todo como leído", "ko": "모두 읽음", "zh-tw": "全部已讀"},
    ("main", "StageChestContent_0101"): {"fr": "<Rouages Invoc>", "it": "<Power kit specchio>",
                                         "es": "<Engranajes de aliado>", "ko": "＜미러기어＞"},
    ("main", "StageChestContent_0102"): {"fr": "<Rouages standard>", "it": "<Power kit normali>",
                                         "es": "<Engranajes normales>", "ko": "＜일반 파워기어＞"},
    ("main", "StageChestContent_0103"): {"fr": "<Autres Rouages standard>", "it": "<Altri Power kit normali>",
                                         "es": "<Otros engranajes normales>", "ko": "＜기타 일반 파워기어＞",
                                         "zh-tw": "＜其他普通力量齒輪＞"},
    ("main", "CutsceneRankUp_0001"): {"fr": "Félicitations !\n\nVotre rang de zone est passé à {T1} !",
                                      "de": "Glückwunsch!\n\nDein Gebietsrang ist jetzt {T1}!",
                                      "ko": "축하합니다!\n\n에리어 랭크가 {T1}이(가) 되었습니다!",
                                      "zh-tw": "恭喜！\n\n區域等級提升至{T1}了！"},
    ("main", "WorldAtlas_0105"): {"fr": "L'application de cartes va s'ouvrir.\nLa position est approximative\n(rayon de {T1}).",
                                  "de": "Die Karten-App wird gestartet.\nDie Position ist ungefähr\n(Umkreis von {T1}).",
                                  "it": "Verrà avviata l'app delle mappe.\nLa posizione è approssimativa\n(raggio di {T1}).",
                                  "es": "Se abrirá la aplicación de mapas.\nLa ubicación es aproximada\n(radio de {T1}).",
                                  "ko": "지도 앱을 실행합니다.\n대략적인 위치이며\n주변 {T1} 범위를 나타냅니다.",
                                  "zh-tw": "將啟動地圖應用程式。\n此為大致位置，\n顯示周圍{T1}的範圍。"},
    ("main", "SubdivisionDesc_0001"): {"fr": "{T1}, {T2}", "de": "{T1}, {T2}", "it": "{T1}, {T2}", "es": "{T1}, {T2}",
                                       "ko": "{T1}의 {T2}", "zh-tw": "{T1}的{T2}"},
    ("scenes", "OptionNotification_0001"): {"fr": "Ouverture d'un coffre mystère",
                                            "de": "Öffnen einer Geheimnistruhe",
                                            "it": "Apertura di uno scrigno misterioso",
                                            "es": "Apertura de un cofre misterioso", "ko": "신비한 상자 개봉",
                                            "zh-tw": "開啟神秘箱子"},
    ("scenes", "OptionNotification_0002"): {"fr": "Paramètres des notifications",
                                            "de": "Benachrichtigungseinstellungen", "it": "Impostazioni notifiche",
                                            "es": "Ajustes de notificaciones", "ko": "알림 설정"},
    ("scenes", "StampCard_0003"): {"fr": "Carte à tampons d'événement", "it": "Tessera timbri evento",
                                   "es": "Tarjeta de sellos de evento", "zh-tw": "活動集章卡"},
}
# zh-tw strings whose tag layout is taken from the English message.
TEXT_FIX_EN_TAGS = {
    ("main", "ChestSelectorAdd_0001"): {"zh-tw": "{T1}不足。\n需要{T2}。"},
    ("main", "ChestSelectorAdd_0002"): {"zh-tw": "要增加存放處嗎？\n需要{T1}。"},
    ("main", "ChestSelectorAdd_0003"): {"zh-tw": "存放處已增加至{T1}個。"},
    ("main", "GiftSelector_0005"): {"zh-tw": "島嶼 ×{T1}"},
}


def fix_text(out, master, protocol):
    """Step 5a (patch_text_bundles.py): patch text/<lang> in place. Returns {bundle: bytes}."""
    envs, files = {}, {}
    for lang in LANGS:
        env = UnityPy.load(os.path.join(out, "text", lang))
        f = {}
        for obj in env.objects:
            if obj.type.name == "TextAsset":
                d = obj.read()
                s = d.m_Script
                raw = s.encode("utf-8", "surrogateescape") if isinstance(s, str) else bytes(s)
                f[d.m_Name] = (obj, d, Msbt(raw))
        envs[lang], files[lang] = env, f

    def msg(lang, f):
        return files[lang][f + ".msbt"][2]

    def plain(lang, f, label):
        raw = msg(lang, f).get(label)
        return None if raw is None else msbt_text(raw)

    changes = {lang: [] for lang in LANGS}

    def put(lang, f, label, raw):
        if msg(lang, f).get(label) != raw:
            msg(lang, f).set(label, raw)
            changes[lang].append(f"{f}:{label}")

    pii_rows = master.rows("Pii")

    def poke(lang, jp_name):
        """Localized species name, from the PiiDesc label of the species' table row."""
        for r in pii_rows:
            if r["m_MonsName"] == jp_name:
                for key in (r["m_basename"], r["m_basename"] + "_NORMAL", r["m_basename"].rsplit("_", 1)[0]):
                    v = plain(lang, "PiiDesc", key)
                    if v:
                        return v
        raise KeyError(jp_name)

    # 1. Species whose JP table renamed the label: copy the global label's text (every language).
    for new, old in {"GEKKOUGA_NORMAL": "GEKKOUGA", "ZIGARUDE_50P": "ZIGARUDE", "METENO": "METENO_NORMAL",
                     "METENO_CORE": "METENO_CORE0", "MIMIKKYU": "MIMIKKYU_NORMAL",
                     "NEKUROZUMA": "NEKUROZUMA_NORMAL", "MAGIANA": "MAGIANA_NORMAL"}.items():
        for lang in LANGS:
            src = msg(lang, "PiiDesc").get(old)
            assert src is not None, (lang, old)
            if msg(lang, "PiiDesc").get(new) is None:
                put(lang, "PiiDesc", new, src)

    # 2. Santa Rowlet form.
    for lang in LANGS:
        put(lang, "formfull", "FORM_MOKUROO_09", msbt_render(TEXT_SANTA[lang]))

    # 3. Zone names of the last six Japanese events.
    evedef = master.rows("Evedef")
    for idstr, (templates, species) in TEXT_ZONES.items():
        row = next(r for r in evedef if r["m_idStr"] == idstr)
        for lang in LANGS:
            if lang == "ja":
                text = row["m_name"]
            else:
                names = [poke(lang, s) for s in species]
                text = templates[lang].format(A=names[0], B=names[1] if len(names) > 1 else "")
            put(lang, "EvedefDesc", "NAME_" + idstr, msbt_render(text))

    # 4. Team battle cups and the P rush.
    for r in evedef:
        if re.fullmatch(r"TeamBattle_\d{3}", r["m_idStr"]):
            for lang in LANGS:
                text = r["m_name"] if lang == "ja" else TEXT_CUP[lang].format(A=poke(lang, r["m_name"].rstrip("杯")))
                put(lang, "EvedefDesc", "NAME_" + r["m_idStr"], msbt_render(text))
    for lang in LANGS:
        put(lang, "EvedefDesc", "NAME_P_Rush", msbt_render(TEXT_PRUSH[lang]))

    # 5. Totem rush tutorial islands.
    islands = master.rows("Island")
    for idstr, names in TEXT_TOTEM.items():
        row = next(r for r in islands if r["m_idStr"] == idstr)
        for lang in LANGS:
            put(lang, "IslandDesc", idstr, msbt_render(row["m_name"] if lang == "ja" else names[lang]))

    # 6. Pokemon+ gear and their kits.
    up_rows = master.rows("UnitPrefix")
    mat_rows = master.rows("PrefixMaterial")
    for i, r in enumerate(up_rows):
        name = protocol.UnitPrefix(i).name
        if not name.startswith("POKE_PLUS_"):
            continue
        species = r["m_name"][:-len("プラス")]
        mat = r["m_prefixMaterial"]
        for lang in LANGS:
            gear = r["m_name"] if lang == "ja" else TEXT_GEAR[lang].format(A=poke(lang, species))
            put(lang, "PrefixDesc", name, msbt_render(gear))
            if mat:
                kit = mat_rows[mat]["m_name"] if lang == "ja" else TEXT_KIT[lang].format(G=gear)
                put(lang, "PrefixMaterialDesc", protocol.PrefixMaterial(mat).name, msbt_render(kit))

    # 7. de/it type kits left in English: rebuild from the gear's own localized name.
    for lang in ("de", "it"):
        for label in list(msg(lang, "PrefixMaterialDesc").index):
            cur, en = plain(lang, "PrefixMaterialDesc", label), plain("en-us", "PrefixMaterialDesc", label)
            gear = plain(lang, "PrefixDesc", label)
            if cur and cur == en and cur.startswith("Upgrade Kit for") and gear:
                put(lang, "PrefixMaterialDesc", label, msbt_render(TEXT_KIT[lang].format(G=gear)))

    # 8. Strings left untranslated.
    def untranslated(lang, f, label):
        cur = plain(lang, f, label)
        return cur is not None and (cur == plain("ja", f, label)
                                    or (lang != "en-us" and cur == plain("en-us", f, label)))

    for table, src in ((TEXT_FIX, None), (TEXT_FIX_EN_TAGS, "en-us")):
        for (f, label), per_lang in table.items():
            for lang, template in per_lang.items():
                if not untranslated(lang, f, label):
                    continue
                runs = msbt_tag_runs(msg(src or lang, f).get(label))
                need = len(re.findall(r"\{T\d\}", template))
                assert need <= len(runs), (lang, f, label, need, len(runs))
                put(lang, f, label, msbt_render(template, runs))

    # Every TextAsset is rebuilt (unchanged ones round-trip to the same MSBT), then saved.
    blobs = {}
    for lang in LANGS:
        for name, (obj, data, m) in files[lang].items():
            new = m.build()
            assert Msbt(new).index.keys() == m.index.keys()
            if hasattr(data, "script") and isinstance(getattr(data, "script"), (bytes, bytearray)):
                data.script = new
            else:
                data.m_Script = new.decode("utf-8", "surrogateescape")
            data.save()
        blob = envs[lang].file.save(packer="original")
        write_bytes(os.path.join(out, "text", lang), blob)
        blobs["text/" + lang] = blob
        log(f"  text/{lang}: {len(changes[lang])} changes, {len(blob):,} B")
    return blobs


# =============================================================== step 5b: welcome calendars
WELCAL_SHEETS = {"bospw2": 2, "bospw3": 3, "bospw4": 4}
WELCAL_STATES = ("Waiting", "CanRedeem", "Redeemed")
# Layouts (anchored position, size) by day content, after BOSP's own days 1, 3 and 4.
WELCAL_POKEMON_ONLY = {"pokemon": ((22, 112), 400), "ore": ((153, -45), 150)}
WELCAL_ORE_ONLY = {"ore": ((5, 0), 200)}
WELCAL_POKEMON_GEAR = {"pokemon": ((63, 46), 250), "ore": ((-71, -12), 150),
                       "gear": [((-75, 114), 150), ((-140, 175), 120), ((-150, 40), 110)]}
WELCAL_GEAR_ONLY = {1: {"gear": [((-60, 95), 170)], "ore": ((60, -45), 170)},
                    2: {"gear": [((-80, 110), 150), ((60, 150), 150)], "ore": ((0, -70), 170)},
                    3: {"gear": [((-100, 120), 130), ((0, 175), 130), ((100, 120), 130)], "ore": ((0, -70), 170)}}


def build_welcal(out, sheet, master, protocol):
    """Step 5b (build_welcal_bundles.build): welcal/<sheet> from welcal/bosp. Returns bytes.

    welcal/bosp holds the art of all four calendars but only BOSP's WelcalSheet and WelcalButton
    prefabs. Each rebuilt bundle is a copy of welcal/bosp with its own CAB and bundle name, its
    prefabs pointing at that calendar's textures, and the seven days laid out from WelcalStepDesc
    (Pokemon art on Pokemon days, gear icons cloned from day 3's on gear days)."""
    env = UnityPy.load(os.path.join(out, "welcal", "bosp"))
    bf = env.file
    old_cab = next(k for k in bf.files if not k.endswith(".resS"))
    new_cab = "CAB-" + hashlib.md5(("welcal/" + sheet).encode()).hexdigest()
    sf = bf.files[old_cab]
    objs = sf.objects
    rng = random.Random(sheet)

    # Art by calendar folder: name -> (texture id, sprite id)
    art = {}
    bundle_obj = next(o for o in objs.values() if o.type.name == "AssetBundle")
    btree = bundle_obj.read_typetree()
    for path, info in btree["m_Container"]:
        if path.startswith("assets/scenes/welcalsheet/") and path.endswith(".psd"):
            folder, name = path.split("/")[3], path.rsplit("/", 1)[1][:-4]
            obj = objs[info["asset"]["m_PathID"]]
            slot = art.setdefault(folder, {}).setdefault(name, [None, None])
            slot[0 if obj.type.name == "Texture2D" else 1] = obj.path_id
    remap = {}
    for name, (tex, spr) in art["bosp"].items():
        if name in art[sheet]:
            remap[tex], remap[spr] = art[sheet][name]

    def repoint(node):
        changed = False
        if isinstance(node, dict):
            if set(node) == {"m_FileID", "m_PathID"}:
                if node["m_FileID"] == 0 and node["m_PathID"] in remap:
                    node["m_PathID"] = remap[node["m_PathID"]]
                    return True
                return False
            for v in node.values():
                changed |= repoint(v)
        elif isinstance(node, list):
            for v in node:
                changed |= repoint(v)
        return changed

    trees = {}
    for pid, obj in objs.items():
        if obj.type.name in ("AssetBundle", "Texture2D", "Sprite", "MonoScript"):
            continue
        try:
            tree = obj.read_typetree()
        except Exception:
            continue
        trees[pid] = tree
        if repoint(tree):
            obj.save_typetree(tree)

    # Hierarchy helpers.
    go_tree = {pid: t for pid, t in trees.items() if objs[pid].type.name == "GameObject"}
    tr_of_go, go_of_tr = {}, {}
    for pid, t in trees.items():
        if objs[pid].type.name in ("Transform", "RectTransform"):
            tr_of_go[t["m_GameObject"]["m_PathID"]] = pid
            go_of_tr[pid] = t["m_GameObject"]["m_PathID"]

    def kids(go):
        return [go_of_tr[c["m_PathID"]] for c in trees[tr_of_go[go]]["m_Children"]]

    def named(go, name):
        return next((k for k in kids(go) if go_tree[k]["m_Name"] == name), None)

    def image_of(go):
        for comp in go_tree[go]["m_Component"]:
            pid = comp["component"]["m_PathID"]
            if pid in trees and "m_Sprite" in trees[pid]:
                return pid
        return None

    def save(pid):
        objs[pid].save_typetree(trees[pid])

    def place(go, pos, size, active=True):
        go_tree[go]["m_IsActive"] = active
        save(go)
        rt = tr_of_go[go]
        trees[rt]["m_AnchoredPosition"] = {"x": float(pos[0]), "y": float(pos[1])}
        trees[rt]["m_SizeDelta"] = {"x": float(size), "y": float(size)}
        save(rt)

    def set_sprite(go, sprite):
        img = image_of(go)
        trees[img]["m_Sprite"] = {"m_FileID": 0, "m_PathID": sprite}
        save(img)

    new_ids = []

    def clone(go, parent_tr):
        """Copy a GameObject with its components (and children) under parent_tr."""
        ids = {}

        def collect(g):
            ids[g] = None
            for comp in go_tree[g]["m_Component"]:
                ids[comp["component"]["m_PathID"]] = None
            for k in kids(g):
                collect(k)
        collect(go)
        for old in ids:
            new = rng.getrandbits(62)
            while new in objs:
                new = rng.getrandbits(62)
            ids[old] = new
        for old, new in ids.items():
            tree = copy.deepcopy(trees[old])

            def swap(node):
                if isinstance(node, dict):
                    if set(node) == {"m_FileID", "m_PathID"}:
                        if node["m_FileID"] == 0 and node["m_PathID"] in ids:
                            node["m_PathID"] = ids[node["m_PathID"]]
                        return
                    for v in node.values():
                        swap(v)
                elif isinstance(node, list):
                    for v in node:
                        swap(v)
            swap(tree)
            if old == tr_of_go[go]:
                tree["m_Father"] = {"m_FileID": 0, "m_PathID": parent_tr}
            obj = copy.copy(objs[old])
            obj.path_id = new
            objs[new] = obj
            trees[new] = tree
            obj.save_typetree(tree)
            new_ids.append(new)
            if objs[old].type.name == "GameObject":
                go_tree[new] = tree
            if objs[old].type.name in ("Transform", "RectTransform"):
                tr_of_go[ids[go_of_tr[old]]] = new
                go_of_tr[new] = ids[go_of_tr[old]]
        trees[parent_tr]["m_Children"].append({"m_FileID": 0, "m_PathID": ids[tr_of_go[go]]})
        save(parent_tr)
        return ids[go]

    # Days from the table.
    calendar = WELCAL_SHEETS[sheet]
    steps = sorted((r["m_day"], r) for i, r in enumerate(master.rows("WelcalStep"))
                   if i and r["m_WelcalID"] == calendar)
    pokemon_days = [d for d, r in steps if r["m_monsNo"]]
    presents = sorted(n for n in art[sheet] if n.startswith("present0"))
    present_of = dict(zip(pokemon_days, presents))
    types = {int(t): t.name.lower() for t in protocol.PokeWazaType}
    up = master.rows("UnitPrefix")

    def gear_sprite(prefix):
        r = up[prefix]
        candidates = []
        if r["m_equnitCategory"] == 3:
            candidates.append("presentgear_type_" + types.get(r["m_wazaType"], ""))
        if r["m_prefixType"] == 2:
            candidates.append("presentgear sr")
        candidates.append("presentgear")
        for name in candidates:
            for folder in (sheet, "bosp"):
                if name in art.get(folder, {}):
                    return art[folder][name][1]
        raise KeyError(prefix)

    day3 = next(g for g, t in go_tree.items() if t["m_Name"] == "Day (3)")
    gear_template = {s: next(k for k in kids(named(day3, s)) if go_tree[k]["m_Name"].startswith("ギア"))
                     for s in WELCAL_STATES}
    for day, row in steps:
        day_go = next(g for g, t in go_tree.items() if t["m_Name"] == f"Day ({day})")
        gears = [p for q, p in zip(row["m_equnitQuantity"], row["m_equnitUnitPrefix"]) if q and p]
        has_pokemon = day in present_of
        layout = (WELCAL_POKEMON_GEAR if has_pokemon and gears else WELCAL_POKEMON_ONLY if has_pokemon
                  else WELCAL_GEAR_ONLY[len(gears)] if gears else WELCAL_ORE_ONLY)
        for state in WELCAL_STATES:
            box = named(day_go, state)
            pokemon = named(box, "プレゼント")
            ore = named(box, "鉱石")
            place(ore, *layout["ore"])
            if has_pokemon:
                place(pokemon, *layout["pokemon"])
                set_sprite(pokemon, art[sheet][present_of[day]][1])
            else:
                go_tree[pokemon]["m_IsActive"] = False
                save(pokemon)
            existing = [k for k in kids(box) if go_tree[k]["m_Name"].startswith("ギア")]
            for n, prefix in enumerate(gears):
                go = existing[n] if n < len(existing) else clone(gear_template[state], tr_of_go[box])
                pos, size = layout["gear"][n]
                place(go, pos, size)
                set_sprite(go, gear_sprite(prefix))
            for extra in existing[len(gears):]:
                go_tree[extra]["m_IsActive"] = False
                save(extra)

    # The prefabs carry the calendar and step ids the client looks up and redeems
    # (WelcalButton.m_welcalID, WelcalSheet.m_items[].WelcalStepID).
    step_ids = [protocol.WelcalStepID(i).name
                for d, i in sorted((r["m_day"], i) for i, r in enumerate(master.rows("WelcalStep"))
                                   if i and r["m_WelcalID"] == calendar)]
    for pid, tree in trees.items():
        if isinstance(tree.get("m_welcalID"), dict):
            tree["m_welcalID"]["m_value"] = protocol.WelcalID(calendar).name
            save(pid)
        if isinstance(tree.get("m_items"), list) and tree["m_items"] and "WelcalStepID" in tree["m_items"][0]:
            assert len(tree["m_items"]) == len(step_ids)
            for item, name in zip(tree["m_items"], step_ids):
                item["WelcalStepID"]["m_value"] = name
            save(pid)

    # Bundle identity: its own CAB, name and prefab paths; the new objects preload with the sheet.
    for pid, obj in objs.items():
        if obj.type.name == "Texture2D":
            t = obj.read_typetree()
            if t["m_StreamData"]["path"]:
                t["m_StreamData"]["path"] = t["m_StreamData"]["path"].replace(old_cab, new_cab)
                obj.save_typetree(t)
    btree["m_Name"] = btree["m_AssetBundleName"] = "welcal/" + sheet
    sheet_entry = None
    container = []
    for path, info in btree["m_Container"]:
        if path.startswith("assets/scenes/welcalsheet/bosp/sceneassets/") and path.endswith(".prefab"):
            path = path.replace("/bosp/", "/" + sheet + "/")
            if path.endswith("welcalsheet.prefab"):
                sheet_entry = info
        container.append((path, info))
    btree["m_Container"] = container
    if new_ids and sheet_entry is not None:
        at = sheet_entry["preloadIndex"] + sheet_entry["preloadSize"]
        btree["m_PreloadTable"][at:at] = [{"m_FileID": 0, "m_PathID": i} for i in new_ids]
        for path, info in btree["m_Container"]:
            if info is sheet_entry:
                continue
            if info["preloadIndex"] >= at:
                info["preloadIndex"] += len(new_ids)
        sheet_entry["preloadSize"] += len(new_ids)
    bundle_obj.save_typetree(btree)
    files = {}
    for name, f in bf.files.items():
        new = name.replace(old_cab, new_cab)
        if hasattr(f, "name"):
            f.name = new
        files[new] = f
    bf.files = files
    blob = bf.save(packer="original")
    write_bytes(os.path.join(out, "welcal", sheet), blob)
    log(f"  welcal/{sheet}: {len(blob):,} B, {len(new_ids)} objects added, "
        f"Pokemon days {sorted(present_of)}")
    return blob


def fix_welcal(out, master, protocol):
    return {"welcal/" + s: build_welcal(out, s, master, protocol) for s in WELCAL_SHEETS}


# =============================================================== step 5c: GEAR sprite
GEAR_GAIJI = 6235669756444918039          # TMP_SpriteAsset "Gaiji" in fonts/common
GEAR_LABEL = 6351954468126782168          # WOSTextDropPii AnimRoot TextMeshProUGUI, scene-assets/battle
GEAR_FONTS_CAB = "cab-164ecad3cde4639a3f9388d750378f74"


def tmp_hash(name):
    value = 0
    for c in name:
        value = (((value << 5) + value) ^ ord(c)) & 0xFFFFFFFF
    return value - (1 << 32) if value >= 1 << 31 else value


def fix_gear(out):
    """Step 5c (patch_gear_sprite.py): the field gear drop label shows <sprite name="GEAR">, which
    no sprite asset had. Adds GEAR (the OL_NORMAL_EQUNIT cog glyph) to "Gaiji" in fonts/common and
    gives the label of scene-assets/battle that sprite asset. Returns {bundle: bytes}."""
    blobs = {}
    env = UnityPy.load(os.path.join(out, "fonts", "common"))
    asset = next(o for o in env.objects if o.path_id == GEAR_GAIJI)
    tree = asset.read_typetree()
    assert tree["m_Name"] == "Gaiji"
    chars = tree["m_SpriteCharacterTable"]
    cog = next(c for c in chars if c["m_Name"] == "OL_NORMAL_EQUNIT")
    if not any(c["m_Name"] == "GEAR" for c in chars):
        chars.append(dict(cog, m_Name="GEAR", m_HashCode=tmp_hash("GEAR")))
        asset.save_typetree(tree)
    blobs["fonts/common"] = env.file.save(packer="original")

    env = UnityPy.load(os.path.join(out, "scene-assets", "battle"))
    sf = next(f for n, f in env.file.files.items() if not n.endswith(".resS"))
    external = next(i + 1 for i, e in enumerate(sf.externals) if GEAR_FONTS_CAB in e.path.lower())
    label = next(o for o in env.objects if o.path_id == GEAR_LABEL)
    tree = label.read_typetree()
    assert tree["m_isRichText"] == 1 and tree["m_fontAsset"]["m_FileID"] == external
    tree["m_spriteAsset"] = {"m_FileID": external, "m_PathID": GEAR_GAIJI}
    label.save_typetree(tree)
    blobs["scene-assets/battle"] = env.file.save(packer="original")
    for rel, blob in blobs.items():
        write_bytes(os.path.join(out, rel), blob)
        log(f"  {rel}: {len(blob):,} B")
    return blobs


# =============================================================== step 7: verification
def _check_shader(tt, where, problems):
    name = tt["m_ParsedForm"]["m_Name"]
    platforms = list(tt["platforms"])
    blob = bytes(tt["compressedBlob"])
    stub = True
    for i, plat in enumerate(platforms):
        if plat != GLES3PLUS:
            continue
        dlen = tt["decompressedLengths"][i]
        off, clen = tt["offsets"][i], tt["compressedLengths"][i]
        dec = lz4.block.decompress(blob[off:off + clen], uncompressed_size=dlen)
        count, = struct.unpack_from("<I", dec, 0)
        for k in range(count):
            so, _ = struct.unpack_from("<II", dec, 4 + 8 * k)
            ptype, = struct.unpack_from("<I", dec, so + 4)
            stub = False
            if ptype not in GPU_OK:
                problems["blob_program_type"].append(f"{where}: {name} blob sub{k} m_ProgramType={ptype}")
    for sub in tt["m_ParsedForm"].get("m_SubShaders") or []:
        for pas in sub.get("m_Passes") or []:
            for stage in PROG_STAGES:
                for sp in ((pas.get(stage) or {}).get("m_SubPrograms") or []):
                    if sp["m_GpuProgramType"] == GPU_GLES:
                        problems["parsed_form_gles"].append(f"{where}: {name} {stage} m_GpuProgramType=5")
    if GLES3PLUS not in platforms:
        problems["no_gles3"].append(f"{where}: {name} platforms={platforms}")
    return stub


def verify_worker(job):
    rel, path = job
    problems = collections.defaultdict(list)
    shaders = stubs = textures = 0
    try:
        env = UnityPy.load(path)
        for bundle in env.files.values():
            for sf in getattr(bundle, "files", {}).values():
                tp = getattr(sf, "target_platform", None)
                if tp is not None and int(tp) != int(BuildTarget.Android):
                    problems["target_platform"].append(f"{rel}: {int(tp)}")
        for obj in env.objects:
            if obj.type.name == "Shader":
                shaders += 1
                stubs += _check_shader(obj.read_typetree(), rel, problems)
            elif obj.type.name == "Texture2D":
                textures += 1
                fmt = obj.read_typetree()["m_TextureFormat"]
                if fmt in PVRTC:
                    problems["pvrtc"].append(f"{rel}: format {fmt}")
    except Exception as e:
        problems["unreadable"].append(f"{rel}: {type(e).__name__}: {e}")
    return dict(problems), shaders, stubs, textures


def verify(out, pool):
    names = tree_files(out, skip=(".json",))
    problems = collections.defaultdict(list)
    shaders = stubs = textures = 0
    for probs, s, st, t in pool.imap_unordered(verify_worker, [(n, os.path.join(out, n)) for n in names]):
        shaders, stubs, textures = shaders + s, stubs + st, textures + t
        for k, v in probs.items():
            problems[k].extend(v)
    for manifest in (ANDROID_ROOT_MANIFEST, IOS_ROOT_MANIFEST, SIZE_MANIFEST):
        if not os.path.exists(os.path.join(out, manifest)):
            problems["missing_manifest"].append(manifest)
    # Added check: size_manifest.json describes exactly this tree.
    try:
        with open(os.path.join(out, SIZE_MANIFEST)) as fh:
            items = json.load(fh)["AssetSizeInfos"][0]["AssetSizeItems"]
        listed = {i["Name"]: i["Size"] for i in items}
        actual = {SIZE_PREFIX + r: os.path.getsize(os.path.join(out, r))
                  for r in tree_files(out) if r != SIZE_MANIFEST}
        for n in sorted(set(listed) | set(actual)):
            if listed.get(n) != actual.get(n):
                problems["size_manifest"].append(f"{n}: listed {listed.get(n)} actual {actual.get(n)}")
    except (OSError, ValueError, KeyError) as e:
        problems["size_manifest"].append(f"unreadable: {e}")
    log(f"  files={len(names)} shaders={shaders} (stubs={stubs}) textures={textures}")
    for kind, hits in sorted(problems.items()):
        log(f"  {kind}: {len(hits)}")
        for h in sorted(hits)[:10]:
            log(f"     {h}")
        if len(hits) > 10:
            log(f"     ... and {len(hits) - 10} more")
    return not problems


# =============================================================== driver
def main():
    ap = argparse.ArgumentParser(description=__doc__.split("\n\n")[0],
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--ios", required=True, help="archived iOS asset tree (holds the 'iOS' root manifest)")
    ap.add_argument("--out", required=True, help="Android asset tree to write")
    ap.add_argument("--fixes", help="server package dir (contains pokeland/): enables the content fixes")
    ap.add_argument("--jobs", type=int, default=os.cpu_count() or 1, help="worker processes")
    ap.add_argument("--overwrite", action="store_true", help="allow a non-empty --out (files are replaced)")
    ap.add_argument("--no-verify", action="store_true", help="skip the final verification")
    args = ap.parse_args()

    ios, out = os.path.abspath(args.ios), os.path.abspath(args.out)
    if not os.path.isfile(os.path.join(ios, IOS_ROOT_MANIFEST)):
        sys.exit(f"{ios} has no '{IOS_ROOT_MANIFEST}' root manifest - is it the iOS asset tree?")
    if out == ios or out.startswith(ios + os.sep):
        sys.exit("--out must not be inside --ios")
    if os.path.isdir(out) and os.listdir(out) and not args.overwrite:
        sys.exit(f"{out} is not empty (pass --overwrite to write into it anyway)")
    master = protocol = None
    if args.fixes:
        sys.path.insert(0, os.path.abspath(args.fixes))
        from pokeland import master, protocol  # noqa: F811  (tables + enums for the content fixes)
    os.makedirs(out, exist_ok=True)

    t_start = time.time()
    timings = []
    ok = True
    ctx = multiprocessing.get_context("fork" if hasattr(os, "fork") else "spawn")
    with ctx.Pool(max(1, args.jobs)) as pool:
        # 1-2. convert + shaders
        t0 = time.time()
        names = tree_files(ios, skip=(".part", ".json"))
        log(f"[1-2] convert {len(names)} bundles (platform, PVRTC->ETC2, GLES20->GLES3Plus), "
            f"{args.jobs} jobs")
        jobs = sorted(((n, os.path.join(ios, n), os.path.join(out, n)) for n in names),
                      key=lambda j: -os.path.getsize(j[1]))
        failed, n_tex, n_sh, n_stub = [], 0, 0, 0
        for rel, err, tex, sh, stub, size, dt in pool.imap_unordered(convert_worker, jobs):
            if err:
                failed.append(rel)
                log(f"  FAIL {rel}: {err}")
                continue
            n_tex, n_sh, n_stub = n_tex + tex, n_sh + sh, n_stub + stub
            if tex or sh or stub:
                log(f"  {rel}: {tex} PVRTC texture(s), {sh} shader(s) promoted"
                    f"{f', {stub} stub(s)' if stub else ''}, {size:,} B, {dt:.1f}s")
        log(f"  {len(names) - len(failed)} converted, {n_tex} textures re-encoded, {n_sh} shaders "
            f"promoted (+{n_stub} stubs), {len(failed)} failed")
        ok &= not failed
        timings.append(("convert+shaders", time.time() - t0))

        # 3. root manifest
        t0 = time.time()
        log(f"[3] root manifest: {IOS_ROOT_MANIFEST} -> {ANDROID_ROOT_MANIFEST}")
        shutil.copyfile(os.path.join(out, IOS_ROOT_MANIFEST), os.path.join(out, ANDROID_ROOT_MANIFEST))

        # 4. language variants
        made = clone_variants(out)
        log(f"[4] variants cloned: {', '.join(made) or 'none'}")
        timings.append(("manifest+variants", time.time() - t0))

        # 5. content fixes
        if args.fixes:
            t0 = time.time()
            blobs = {}
            for label, fn in (("text bundles", lambda: fix_text(out, master, protocol)),
                              ("welcome calendars", lambda: fix_welcal(out, master, protocol)),
                              ("GEAR sprite", lambda: fix_gear(out))):
                log(f"[5] {label}")
                try:
                    blobs.update(fn())
                except Exception as e:
                    ok = False
                    log(f"  FAIL {label}: {type(e).__name__}: {e}")
            log(f"[5] manifest hashes of {len(blobs)} bundle(s) -> {ANDROID_ROOT_MANIFEST}")
            set_manifest_hashes(out, blobs)
            timings.append(("content fixes", time.time() - t0))
        else:
            log("[5] content fixes skipped (no --fixes)")

        # 6. size_manifest.json
        n, total = write_size_manifest(out)
        log(f"[6] {SIZE_MANIFEST}: {n} entries, {total / 1048576:.1f} MB")

        # 7. verify
        if not args.no_verify:
            t0 = time.time()
            log("[7] verify")
            good = verify(out, pool)
            log("  OK" if good else "  FAIL")
            ok &= good
            timings.append(("verify", time.time() - t0))

    log("timings: " + ", ".join(f"{k} {v:.0f}s" for k, v in timings)
        + f", total {time.time() - t_start:.0f}s")
    log("DONE" if ok else "FAILED")
    return 0 if ok else 1


if __name__ == "__main__":
    sys.exit(main())
