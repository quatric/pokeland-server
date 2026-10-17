#!/usr/bin/env python3
"""Apply Android content fixes to the native iOS CDN tree.

Applies content bugfixes (text MSBTs, welcome calendars bospw2..4, GEAR sprite
in fonts/common & scene-assets/battle, cloned l10n illustration variants)
to iOS bundles, leaving textures, shaders and platforms untouched.

Updates root manifest 'iOS' hashes and regenerates 'size_manifest.json' (prefix 'iOS/').
NEVER touches any Android assets.
"""
import argparse
import hashlib
import json
import os
import shutil
import sys
import UnityPy

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
sys.path.insert(0, os.path.join(ROOT, "standalone"))
sys.path.insert(0, os.path.join(ROOT, "dokiheroes_py"))

import dokiscramble_repack_cdn as rp
from pokeland import master, protocol


def verify_ios_bundle(path):
    env = UnityPy.load(path)
    for name, sf in env.file.files.items():
        if hasattr(sf, "target_platform"):
            tp = int(sf.target_platform)
            # 9 is BuildTarget.iOS
            if tp != 9:
                raise ValueError(f"{path}: target_platform={tp}, expected 9 (iOS)")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--ios", required=True, help="Path to source iOS CDN directory")
    parser.add_argument("--out", required=True, help="Path to target iOS CDN directory")
    parser.add_argument("--in-place", action="store_true", help="Modify --ios directly")
    args = parser.parse_args()

    ios_src = os.path.abspath(args.ios)
    ios_out = os.path.abspath(args.out)

    if not os.path.isfile(os.path.join(ios_src, rp.IOS_ROOT_MANIFEST)):
        sys.exit(f"Source {ios_src} does not contain root manifest '{rp.IOS_ROOT_MANIFEST}'")

    if not args.in_place and ios_src != ios_out:
        print(f"Staging copy from {ios_src} to {ios_out}...")
        if os.path.exists(ios_out):
            shutil.rmtree(ios_out)
        shutil.copytree(ios_src, ios_out)

    print("Step 1: Clone missing l10n illustration variants (iOS native)...")
    cloned = rp.clone_variants(ios_out, manifest=rp.IOS_ROOT_MANIFEST)
    print(f"  Cloned: {', '.join(cloned) or 'none'}")

    print("Step 2: Apply content fixes (text bundles, welcal, GEAR sprite)...")
    blobs = {}
    blobs.update(rp.fix_text(ios_out, master, protocol))
    blobs.update(rp.fix_welcal(ios_out, master, protocol))
    blobs.update(rp.fix_gear(ios_out))
    print(f"  Patched bundles: {len(blobs)}")

    print(f"Step 3: Update AssetBundleHash in '{rp.IOS_ROOT_MANIFEST}' manifest...")
    rp.set_manifest_hashes(ios_out, blobs, manifest=rp.IOS_ROOT_MANIFEST)

    print("Step 4: Regenerate 'size_manifest.json' (prefix 'iOS/')...")
    n, total_bytes = rp.write_size_manifest(ios_out, prefix="iOS/")
    print(f"  size_manifest.json: {n} entries ({total_bytes / 1048576:.1f} MB)")

    print("Step 5: Verifying patched iOS tree...")
    # Verify all patched bundles are target_platform == 9
    for rel in sorted(blobs):
        verify_ios_bundle(os.path.join(ios_out, rel))
    for v in cloned:
        verify_ios_bundle(os.path.join(ios_out, v))

    # Verify size_manifest matches disk exactly
    sm = json.load(open(os.path.join(ios_out, rp.SIZE_MANIFEST)))
    for item in sm["AssetSizeInfos"][0]["AssetSizeItems"]:
        name = item["Name"]
        assert name.startswith("iOS/"), f"Bad name in size_manifest: {name}"
        rel = name[len("iOS/"):]
        real_size = os.path.getsize(os.path.join(ios_out, rel))
        assert item["Size"] == real_size, f"{name}: manifest size {item['Size']} != real {real_size}"

    # Verify GEAR sprite in Gaiji
    env = UnityPy.load(os.path.join(ios_out, "fonts", "common"))
    asset = next(o for o in env.objects if o.path_id == rp.GEAR_GAIJI)
    tree = asset.read_typetree()
    assert any(c["m_Name"] == "GEAR" for c in tree["m_SpriteCharacterTable"]), "GEAR sprite missing from Gaiji"

    print("Verification SUCCESS: All iOS bundles verified native, valid and matching manifests.")


if __name__ == "__main__":
    main()
