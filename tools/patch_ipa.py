#!/usr/bin/env python3
"""Patch the decrypted Pokémon Rumble Rush / Scramble SP 1.6.1 iOS IPA, standalone.

    python3 tools/patch_ipa.py decrypted-1.6.1.ipa -o pokeland-1.6.1-ios-patched-unsigned.ipa \
        --server https://pokeland.dokihan.dev

One file, no checkout needed. It needs Python 3.9+ and UnityPy (`pip install UnityPy`). Every edit
is checked against the retail bytes it expects, so another build of the game is refused rather than
half-patched. Everything not listed here stays byte for byte as in the original IPA.

The server is one origin; the game's hosts live under it, like the Android patcher:
    https://prd.app.pokeland.jp          -> <server>/api
    https://dl.app.pokeland.jp           -> <server>/cdn
    https://pokemon-webapi.appspot.com   -> <server>
    Nintendo BaaS / account / My Nintendo -> <server>/baas (BaaS on <server>/baas/fs-sb)

What it changes:
- Data/Managed/Metadata/global-metadata.dat: the API, CDN and webapi URLs baked into IL2CPP string
  literals (a literal too long for its slot is appended at the end of the file and repointed:
  IL2CPP reads literals without a bounds check).
- Data/resources.assets (the `npf` TextAsset) and Info.plist (NPFSettings): the Nintendo SDK hosts
  and its purchase mock (a "-sb" BaaS path is what makes the SDK honour purchaseMock: the shop is
  free). The native SDK falls back to Info.plist before Unity starts, so both copies are kept equal.
- The executable: the service-shutdown flag, the support-date cutoff and the open-ended journey
  deadline badge.
- The old App Store signature, embedded.mobileprovision and every _CodeSignature are dropped; sign
  the result with AltStore, SideStore, Sideloadly or equivalent.
"""
import argparse
import io
import json
import os
import plistlib
import struct
import sys
import zipfile

METADATA_MAGIC = 0xFAB11BAF
HOST_ROLE = {"https://prd.app.pokeland.jp": "api", "https://dl.app.pokeland.jp": "cdn",
             "https://pokemon-webapi.appspot.com": "webapi"}
NPF_PLIST_KEYS = ("baasHost", "accountHost", "accountApiHost", "pointProgramHost", "basicAuthPass",
                  "basicAuthUser", "clientId", "debugLog", "printLog", "purchaseMock", "useHttp")

#: File offsets equal RVAs in this Mach-O's __TEXT segment (vmaddr 0x100000000, fileoff 0).
#: (what, offset, retail bytes, patched bytes)
EXECUTABLE_PATCHES = (
    ("service-shutdown flag", 0x10345F8, bytes.fromhex("60ee4139"), bytes.fromhex("00008052")),
    ("support-date cutoff", 0x115CBB4, bytes.fromhex("f44fbea9fd7b01a9"), bytes.fromhex("00008052c0035fd6")),
    ("open-ended journey deadline badge", 0x112F9E8, bytes.fromhex("21008052"), bytes.fromhex("01008052")),
)


def log(message):
    print(message, flush=True)


# ---------------------------------------------------------------------------------------- metadata
def patch_metadata(data, urls):
    """Rewrites the literals starting with a retail host; returns the new file."""
    data = bytearray(data)
    if struct.unpack_from("<I", data, 0)[0] != METADATA_MAGIC:
        raise SystemExit("global-metadata.dat: not an IL2CPP metadata file")
    lit_off, lit_size = struct.unpack_from("<ii", data, 8)
    dat_off = struct.unpack_from("<i", data, 16)[0]
    patched = 0
    for i in range(lit_size // 8):
        entry = lit_off + i * 8
        length, index = struct.unpack_from("<Ii", data, entry)
        start = dat_off + index
        text = bytes(data[start:start + length]).decode("utf-8", "replace")
        host = next((h for h in HOST_ROLE if text.startswith(h)), None)
        if host is None:
            continue
        new = (urls[HOST_ROLE[host]] + text[len(host):]).encode()
        if len(new) <= length:
            data[start:start + len(new)] = new
            data[start + len(new):start + length] = b"\0" * (length - len(new))
            struct.pack_into("<I", data, entry, len(new))
        else:
            struct.pack_into("<Ii", data, entry, len(new), len(data) - dat_off)
            data.extend(new)
        log(f"  metadata: {text} -> {new.decode()}")
        patched += 1
    if not patched:
        raise SystemExit("global-metadata.dat: no retail URL found (already patched?)")
    return bytes(data)


# -------------------------------------------------------------------------------------------- npf
def host_path(url):
    return url.split("://", 1)[-1].rstrip("/")


def npf_config(config, base):
    """The retail npf settings with the server's hosts. Only keys the build already has are set,
    except baasHost/useHttp/purchaseMock."""
    host = host_path(base)
    config["baasHost"] = host + "/baas/fs-sb"       # "-sb": the SDK honours purchaseMock
    for key in ("accountHost", "accountApiHost", "pointProgramHost"):
        if key in config:
            config[key] = host + "/baas"
    config["useHttp"] = base.startswith("http://")
    config["purchaseMock"] = True
    return config


def patch_npf_asset(data, base):
    """resources.assets with the npf TextAsset repointed; returns (new file, final config)."""
    import UnityPy
    env = UnityPy.load(io.BytesIO(data))
    matches = []
    for obj in env.objects:
        if obj.type.name != "TextAsset":
            continue
        tree = obj.read_typetree()
        if tree.get("m_Name") == "npf":
            matches.append((obj, tree))
    if len(matches) != 1:
        raise SystemExit(f"resources.assets: expected one npf TextAsset, found {len(matches)}")
    obj, tree = matches[0]
    config = npf_config(json.loads(tree["m_Script"]), base)
    tree["m_Script"] = json.dumps(config, indent=4) + "\n"
    obj.save_typetree(tree)
    out = env.file.save()
    log(f"  npf: baasHost {config['baasHost']}, account/My Nintendo {host_path(base)}/baas, purchaseMock")
    return out, config


def patch_info_plist(raw, config):
    plist = plistlib.loads(raw)
    settings = dict(plist.get("NPFSettings", {}))
    for key in NPF_PLIST_KEYS:
        if key in config:
            settings[key] = config[key]
    plist["NPFSettings"] = settings
    fmt = plistlib.FMT_BINARY if raw.startswith(b"bplist") else plistlib.FMT_XML
    log("  Info.plist: NPFSettings synchronized")
    return plistlib.dumps(plist, fmt=fmt, sort_keys=False)


# ------------------------------------------------------------------------------------ executable
def patch_executable(data):
    data = bytearray(data)
    if data[:4] != bytes.fromhex("cffaedfe"):
        raise SystemExit("executable: not a little-endian 64-bit Mach-O")
    for what, at, old, new in EXECUTABLE_PATCHES:
        actual = bytes(data[at:at + len(old)])
        if actual != old:
            raise SystemExit(f"executable: {what}: expected {old.hex(' ')} at {at:#x}, found "
                             f"{actual.hex(' ')}; this must be the decrypted iOS 1.6.1 build")
        data[at:at + len(new)] = new
        log(f"  executable: {what} at {at:#x}")
    return bytes(data)


# ------------------------------------------------------------------------------------------ main
def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("ipa", help="the decrypted (cryptid=0) iOS 1.6.1 IPA")
    ap.add_argument("-o", "--out", default="pokeland-1.6.1-ios-patched-unsigned.ipa")
    ap.add_argument("--server", default="https://pokeland.dokihan.dev",
                    help="server base URL; /api, /cdn and /baas are under it (default %(default)s)")
    args = ap.parse_args()

    base = args.server.rstrip("/")
    if not base.startswith(("http://", "https://")):
        raise SystemExit("--server: an http(s) URL")
    urls = {"api": base + "/api", "cdn": base + "/cdn", "webapi": base}
    log(f"Pokeland iOS patch of {args.ipa} -> {args.out}")
    src = zipfile.ZipFile(args.ipa)
    names = src.namelist()
    apps = sorted({n.split("/")[1] for n in names if n.startswith("Payload/") and n.count("/") >= 2
                   and n.split("/")[1].endswith(".app")})
    if len(apps) != 1:
        raise SystemExit(f"expected exactly one app below Payload, found {len(apps)}")
    app = f"Payload/{apps[0]}/"
    info_name = app + "Info.plist"
    metadata_name = app + "Data/Managed/Metadata/global-metadata.dat"
    resources_name = app + "Data/resources.assets"
    for needed in (info_name, metadata_name, resources_name):
        if needed not in names:
            raise SystemExit(f"{needed} missing: not the Pokémon Scramble SP IPA")
    executable_name = app + plistlib.loads(src.read(info_name))["CFBundleExecutable"]

    changes = {metadata_name: patch_metadata(src.read(metadata_name), urls)}
    changes[resources_name], config = patch_npf_asset(src.read(resources_name), base)
    changes[info_name] = patch_info_plist(src.read(info_name), config)
    changes[executable_name] = patch_executable(src.read(executable_name))

    def stale_signature(n):
        return n.endswith("embedded.mobileprovision") or "/_CodeSignature/" in n

    tmp = args.out + ".tmp"
    with zipfile.ZipFile(tmp, "w") as dst:
        for info in src.infolist():
            n = info.filename
            if stale_signature(n):
                continue                             # the App Store signature
            if info.is_dir():
                dst.writestr(info, b"")
                continue
            out = zipfile.ZipInfo(n, date_time=info.date_time)
            out.compress_type, out.external_attr = info.compress_type, info.external_attr
            dst.writestr(out, changes.get(n, src.read(n)))
    os.replace(tmp, args.out)
    with zipfile.ZipFile(args.out) as check:
        if check.testzip():
            raise SystemExit("output zip failed its integrity check")
    log("  dropped the App Store signatures: sign the IPA before installing")
    log(f"done: {args.out} ({os.path.getsize(args.out) / 1e6:.1f} MB)")


if __name__ == "__main__":
    sys.exit(main())
