#!/usr/bin/env python3
"""Convert the archived iOS asset bundles into Android ones.

Only an iOS asset tree was ever archived, and Unity refuses to load an
AssetBundle whose target platform does not match the running player. Two things
have to change per bundle:

  * the SerializedFile's target_platform, iOS (9) -> Android (13);
  * any PVRTC texture, which is a PowerVR-only format that Adreno and Mali
    cannot sample, re-encoded to ETC2 (universally supported on GLES 3.0+).

Most textures are already RGB24/RGBA32 and are left alone, so the size cost is
much smaller than a blanket decompress would be.

Usage: repack_android.py [--force] [bundle ...]
       with no bundles, converts the whole mirrored iOS tree.
"""
import json
import os
import sys
import time

import UnityPy
from UnityPy.enums import BuildTarget, TextureFormat

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ASSETVER = '1.6.0/740e9608db30b5f19e739442a779e2e2'
SRC_ROOT = f'{ROOT}/cdn/pokeland/{ASSETVER}/iOS'
DST_ROOT = f'{ROOT}/cdn/pokeland/{ASSETVER}/Android'

# PowerVR-only block formats. Everything else in these bundles is either
# uncompressed or already portable.
PVRTC = {
    int(TextureFormat.PVRTC_RGB2), int(TextureFormat.PVRTC_RGBA2),
    int(TextureFormat.PVRTC_RGB4), int(TextureFormat.PVRTC_RGBA4),
}


def convert(src, dst):
    env = UnityPy.load(src)

    converted = 0
    for obj in env.objects:
        if obj.type.name != 'Texture2D':
            continue
        data = obj.read()
        if int(data.m_TextureFormat) not in PVRTC:
            continue
        # Round-trip through RGBA and re-encode. Alpha is preserved either way;
        # ETC2_RGBA8 costs 1 byte/px against PVRTC_RGBA4's 0.5.
        data.set_image(data.image, target_format=TextureFormat.ETC2_RGBA8)
        data.save()
        converted += 1

    # Retarget every serialized file in the bundle. SerializedFile.save() writes
    # the raw _m_target_platform int, not the BuildTarget property, so setting
    # only the latter looks like it worked and silently changes nothing.
    for bundle in env.files.values():
        for sf in getattr(bundle, 'files', {}).values():
            if hasattr(sf, '_m_target_platform'):
                sf._m_target_platform = int(BuildTarget.Android)
                sf.target_platform = BuildTarget.Android

    os.makedirs(os.path.dirname(dst), exist_ok=True)
    with open(dst, 'wb') as fh:
        fh.write(env.file.save(packer='original'))  # keep the source's LZMA
    return converted


def write_size_manifest():
    sizes = {}
    for dirpath, _, files in os.walk(DST_ROOT):
        for f in files:
            if f == 'size_manifest.json':
                continue
            full = os.path.join(dirpath, f)
            rel = os.path.relpath(full, DST_ROOT)
            sizes[f'Android/{rel}'] = os.path.getsize(full)
    manifest = {'AssetSizeInfos': [{
        'AssetSizeItems': [{'Name': k, 'Size': v} for k, v in sorted(sizes.items())],
        'AssetVer': '',
    }]}
    os.makedirs(DST_ROOT, exist_ok=True)
    with open(os.path.join(DST_ROOT, 'size_manifest.json'), 'w') as fh:
        json.dump(manifest, fh, separators=(',', ':'))
    print(f'size_manifest: {len(sizes)} entries, '
          f'{sum(sizes.values()) / 1048576:.1f} MB')


def main():
    args = [a for a in sys.argv[1:] if not a.startswith('--')]
    force = '--force' in sys.argv

    if args:
        names = args
    else:
        names = []
        for dirpath, _, files in os.walk(SRC_ROOT):
            for f in files:
                if f.endswith(('.part', '.json')):
                    continue
                names.append(os.path.relpath(os.path.join(dirpath, f), SRC_ROOT))
        names.sort()

    ok, failed, skipped = 0, 0, 0
    for name in names:
        src, dst = os.path.join(SRC_ROOT, name), os.path.join(DST_ROOT, name)
        if not os.path.exists(src):
            print(f'miss  {name}'); continue
        if os.path.exists(dst) and not force:
            print(f'have  {name}'); skipped += 1; continue
        try:
            t0 = time.time()
            n = convert(src, dst)
            size = os.path.getsize(dst)
            grew = size / max(os.path.getsize(src), 1)
            print(f'ok    {name}  {n} tex  {size:,} B  ({grew:.2f}x)  {time.time()-t0:.1f}s')
            ok += 1
        except Exception as e:
            print(f'FAIL  {name}: {type(e).__name__}: {e}')
            failed += 1

    # The client reads size_manifest.json to size its download, so it has to
    # describe the Android tree we just produced, not the iOS one. Always built
    # by walking the whole destination tree - deriving it from just the bundles
    # this run touched would truncate the manifest whenever the tool is given an
    # explicit bundle list.
    write_size_manifest()

    print(f'\nconverted={ok} skipped={skipped} failed={failed}')


if __name__ == '__main__':
    main()
