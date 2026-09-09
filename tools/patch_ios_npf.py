#!/usr/bin/env python3
"""Redirect the iOS client's embedded NPF configuration to the revival."""

from __future__ import annotations

import json
import plistlib
import sys
from pathlib import Path
from urllib.parse import urlsplit

import UnityPy


def main() -> None:
    if len(sys.argv) not in {4, 5}:
        raise SystemExit(
            f"usage: {Path(sys.argv[0]).name} <in resources.assets> "
            "<out resources.assets> <base-url> [Info.plist]"
        )

    source, output, base = Path(sys.argv[1]), Path(sys.argv[2]), sys.argv[3]
    info_plist = Path(sys.argv[4]) if len(sys.argv) == 5 else None
    parsed = urlsplit(base)
    if parsed.scheme not in {"http", "https"} or not parsed.netloc or parsed.path.rstrip("/"):
        raise SystemExit("base URL must be an http(s) origin without a path")

    environment = UnityPy.load(str(source))
    matches = []
    for obj in environment.objects:
        if obj.type.name != "TextAsset":
            continue
        tree = obj.read_typetree()
        if tree.get("m_Name") == "npf":
            matches.append((obj, tree))
    if len(matches) != 1:
        raise SystemExit(f"expected one npf TextAsset, found {len(matches)}")

    obj, tree = matches[0]
    config = json.loads(tree["m_Script"])
    config["baasHost"] = parsed.netloc
    config["useHttp"] = parsed.scheme == "http"
    tree["m_Script"] = json.dumps(config, indent=4) + "\n"
    obj.save_typetree(tree)
    output.write_bytes(environment.file.save())

    # The iOS NPF wrapper has two configuration sources. Unity loads the npf
    # TextAsset, while the native SDK can fall back to NPFSettings in the app's
    # Info.plist before Unity has initialized. Keep both copies synchronized;
    # otherwise the country request reaches the revival but native BaaS login
    # still targets Nintendo's retired host and surfaces as N-000000.
    if info_plist is not None:
        plist_bytes = info_plist.read_bytes()
        plist = plistlib.loads(plist_bytes)
        settings = dict(plist.get("NPFSettings", {}))
        for key in (
            "baasHost",
            "basicAuthPass",
            "basicAuthUser",
            "clientId",
            "debugLog",
            "printLog",
            "purchaseMock",
            "useHttp",
        ):
            if key in config:
                settings[key] = config[key]
        plist["NPFSettings"] = settings
        plist_format = (
            plistlib.FMT_BINARY if plist_bytes.startswith(b"bplist")
            else plistlib.FMT_XML
        )
        with info_plist.open("wb") as stream:
            plistlib.dump(plist, stream, fmt=plist_format, sort_keys=False)

    check_environment = UnityPy.load(str(output))
    check = []
    for item in check_environment.objects:
        if item.type.name != "TextAsset":
            continue
        check_tree = item.read_typetree()
        if check_tree.get("m_Name") == "npf":
            check.append(json.loads(check_tree["m_Script"]))
    if len(check) != 1 or check[0].get("baasHost") != parsed.netloc \
            or check[0].get("useHttp") != (parsed.scheme == "http"):
        raise SystemExit("saved NPF configuration did not verify")

    if info_plist is not None:
        saved_settings = plistlib.loads(info_plist.read_bytes()).get("NPFSettings", {})
        for key in ("baasHost", "basicAuthPass", "basicAuthUser", "clientId", "useHttp"):
            if saved_settings.get(key) != config.get(key):
                raise SystemExit(f"saved Info.plist NPF setting did not verify: {key}")

    print(f"  baasHost -> {parsed.netloc}")
    print(f"  useHttp  -> {config['useHttp']}")
    if info_plist is not None:
        print("  Info.plist NPFSettings synchronized")


if __name__ == "__main__":
    main()
