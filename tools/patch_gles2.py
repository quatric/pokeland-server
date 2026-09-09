#!/usr/bin/env python3
"""Make the 1.6.0 Android player use a complete GLES2 shader set.

The archived CDN bundles contain GLES2 and Metal shader programs, while the
retail Android player contains GLES3 and Vulkan programs. Switching renderers
alone therefore leaves one side or the other magenta. A donor APK made with the
same Unity 2018.4.11f1 editor supplies the missing GLES2 player shaders.

This tool copies only compiled Shader objects (matched by asset filename and
shader name), replaces Unity's built-in shader archive, and changes the player
graphics API list to OpenGLES2. It does not copy game state or managed code.

Usage: patch_gles2.py <donor Data directory> <target Data directory>
"""

from __future__ import annotations

import os
import shutil
import sys
from pathlib import Path

import UnityPy


GLES2_SHADER_PLATFORM = 5
OPEN_GLES2_GRAPHICS_API = 8

# These player shaders are identical by name and path ID in the donor and 1.6.0
# APKs, but their compiled programs target different graphics APIs.
SHADERS_BY_ASSET = {
    "globalgamemanagers.assets": ("Sprites/Default",),
    "sharedassets0.assets": ("Skybox/Procedural", "CustomParticle"),
    "128e987d567d4e2c824d754223b3f3b0": ("TextMeshPro/Bitmap",),
    "1e3b057af24249748ff873be7fafee47": ("TextMeshPro/Mobile/Bitmap",),
    "48bb5f55d8670e349b6e614913f9d910": (
        "TextMeshPro/Bitmap Custom Atlas",
    ),
    "68e6db2ebdc24f95958faec2be5558d6": ("TextMeshPro/Distance Field",),
    "85187c2149c549c5b33f0cdb02836b17": (
        "TextMeshPro/Mobile/Distance Field (Surface)",
    ),
    "a02a7d8c237544f1962732b55a9aebf1": (
        "TextMeshPro/Mobile/Distance Field Overlay",
    ),
    "bc1ede39bf3643ee8e493720e4259791": (
        "TextMeshPro/Mobile/Distance Field - Masking",
    ),
    "cf81c85f95fe47e1a27f6ae460cf182c": ("TextMeshPro/Sprite",),
    "dd89cf5b9246416f84610a006f916af7": (
        "TextMeshPro/Distance Field Overlay",
    ),
    "f7ada0af4f174f0694ca6a487b8f543d": (
        "TextMeshPro/Distance Field (Surface)",
    ),
    "fe393ace9b354375a9cb14cdbbc28be4": (
        "TextMeshPro/Mobile/Distance Field",
    ),
}

# The donor has no GLES2 programs for these Nintendo-account preview shaders.
# Neither shader is loaded by the device-account Camp, Globe, or battle flows.
ALLOWED_NON_GLES2 = {
    ("de82410dac80d5e4d9dac8a6fb2d2cc5", "Mii/TextureShader"),
    ("f2028d61e53b31b409fdba275046e450", "Mii/SampleShader"),
}


def find_serialized_file(root: Path, asset_name: str) -> Path:
    direct = root / asset_name
    if direct.is_file():
        return direct

    split = root / f"{asset_name}.split0"
    if split.is_file():
        return split

    matches = [path for path in root.rglob(asset_name) if path.is_file()]
    if len(matches) == 1:
        return matches[0]
    raise RuntimeError(
        f"expected one {asset_name!r} below {root}, found {len(matches)}"
    )


def shader_objects(environment, names: tuple[str, ...]):
    wanted = set(names)
    matches = {}
    for obj in environment.objects:
        if obj.type.name != "Shader":
            continue
        tree = obj.read_typetree()
        name = tree["m_ParsedForm"]["m_Name"]
        if name in wanted:
            if name in matches:
                raise RuntimeError(f"duplicate shader {name!r}")
            matches[name] = (obj, tree)

    missing = wanted - matches.keys()
    if missing:
        raise RuntimeError(f"missing shader(s): {', '.join(sorted(missing))}")
    return matches


def atomic_write(path: Path, data: bytes) -> None:
    temporary = path.with_name(f"{path.name}.gles2tmp")
    temporary.write_bytes(data)
    os.replace(temporary, path)


def save_serialized_file(path: Path, data: bytes) -> None:
    if not path.name.endswith(".split0"):
        atomic_write(path, data)
        return

    prefix = path.name[:-1]
    parts = sorted(
        path.parent.glob(f"{prefix}*"),
        key=lambda item: int(item.name.removeprefix(prefix)),
    )
    if not parts:
        raise RuntimeError(f"no split parts found for {path}")

    chunk_size = parts[0].stat().st_size
    chunks = [data[start : start + chunk_size] for start in range(0, len(data), chunk_size)]
    if len(chunks) != len(parts):
        raise RuntimeError(
            f"patched {path.name} needs {len(chunks)} split parts; target has "
            f"{len(parts)}"
        )

    for part, chunk in zip(parts, chunks, strict=True):
        atomic_write(part, chunk)


def transplant_shaders(donor_root: Path, target_root: Path) -> int:
    patched = 0
    for asset_name, names in SHADERS_BY_ASSET.items():
        donor_path = find_serialized_file(donor_root, asset_name)
        target_path = find_serialized_file(target_root, asset_name)
        donor = UnityPy.load(str(donor_path))
        target = UnityPy.load(str(target_path))
        donor_shaders = shader_objects(donor, names)
        target_shaders = shader_objects(target, names)

        for name in names:
            donor_tree = donor_shaders[name][1]
            donor_platforms = list(donor_tree["platforms"])
            if GLES2_SHADER_PLATFORM not in donor_platforms:
                raise RuntimeError(
                    f"donor shader {name!r} has platforms {donor_platforms}, "
                    "not GLES2"
                )
            target_shaders[name][0].save_typetree(donor_tree)
            patched += 1

        save_serialized_file(target_path, target.file.save())
        print(f"  {asset_name}: {', '.join(names)}")
    return patched


def replace_builtin_shaders(donor_root: Path, target_root: Path) -> int:
    relative_path = Path("Resources") / "unity_builtin_extra"
    donor_path = donor_root / relative_path
    target_path = target_root / relative_path
    if not donor_path.is_file() or not target_path.is_file():
        raise RuntimeError(f"missing {relative_path} in donor or target")

    environment = UnityPy.load(str(donor_path))
    shaders = [obj.read_typetree() for obj in environment.objects if obj.type.name == "Shader"]
    bad = {
        tree["m_ParsedForm"]["m_Name"]: list(tree["platforms"])
        for tree in shaders
        if GLES2_SHADER_PLATFORM not in tree["platforms"]
    }
    if not shaders or bad:
        raise RuntimeError(f"donor unity_builtin_extra is not GLES2-complete: {bad}")

    shutil.copyfile(donor_path, target_path)
    print(f"  unity_builtin_extra: {len(shaders)} GLES2 shaders")
    return len(shaders)


def force_gles2_renderer(target_root: Path) -> None:
    path = find_serialized_file(target_root, "globalgamemanagers")
    environment = UnityPy.load(str(path))
    settings = [obj for obj in environment.objects if obj.type.name == "BuildSettings"]
    if len(settings) != 1:
        raise RuntimeError(f"expected one BuildSettings object, found {len(settings)}")

    tree = settings[0].read_typetree()
    old_apis = list(tree["m_GraphicsAPIs"])
    tree["m_GraphicsAPIs"] = [OPEN_GLES2_GRAPHICS_API]
    settings[0].save_typetree(tree)
    save_serialized_file(path, environment.file.save())
    print(f"  graphics APIs: {old_apis} -> [{OPEN_GLES2_GRAPHICS_API}] (OpenGLES2)")


def verify(target_root: Path) -> None:
    environment = UnityPy.load(str(target_root))
    unsupported = set()
    shader_count = 0
    for obj in environment.objects:
        if obj.type.name != "Shader":
            continue
        shader_count += 1
        tree = obj.read_typetree()
        platforms = list(tree["platforms"])
        if GLES2_SHADER_PLATFORM in platforms:
            continue
        key = (Path(obj.assets_file.name).name, tree["m_ParsedForm"]["m_Name"])
        unsupported.add(key)

    unexpected = unsupported - ALLOWED_NON_GLES2
    if unexpected:
        details = ", ".join(f"{asset}:{name}" for asset, name in sorted(unexpected))
        raise RuntimeError(f"non-GLES2 player shaders remain: {details}")

    settings_path = find_serialized_file(target_root, "globalgamemanagers")
    settings_environment = UnityPy.load(str(settings_path))
    apis = [
        obj.read_typetree()["m_GraphicsAPIs"]
        for obj in settings_environment.objects
        if obj.type.name == "BuildSettings"
    ]
    if apis != [[OPEN_GLES2_GRAPHICS_API]]:
        raise RuntimeError(f"unexpected graphics API list: {apis}")

    print(f"verified {shader_count} player shaders; only dormant Mii previews lack GLES2")


def main() -> int:
    if len(sys.argv) != 3:
        print(__doc__)
        return 2

    donor_root = Path(sys.argv[1]).resolve()
    target_root = Path(sys.argv[2]).resolve()
    if not donor_root.is_dir() or not target_root.is_dir():
        print("donor and target Data directories must both exist", file=sys.stderr)
        return 2

    try:
        print("transplanting GLES2 shader programs")
        patched = transplant_shaders(donor_root, target_root)
        builtin_count = replace_builtin_shaders(donor_root, target_root)
        force_gles2_renderer(target_root)
        verify(target_root)
    except Exception as error:
        print(f"GLES2 patch failed: {error}", file=sys.stderr)
        return 1

    print(f"patched {patched} embedded and {builtin_count} built-in shaders")
    return 0


if __name__ == "__main__":
    sys.exit(main())
