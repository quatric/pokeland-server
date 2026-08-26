#!/usr/bin/env python3
"""Extract the game's master data tables out of the `tables` asset bundle.

The bundle holds one ScriptableObject per table (gen_StageDesc, gen_PiiDesc,
gen_WazaDesc, ...), each wrapping a single `m_table` array of rows. The iOS
build kept its serialized type trees, so the rows deserialize without needing
type info reconstructed from the IL2CPP dump.

Usage: extract_tables.py [bundle] [outdir]
"""
import json
import os
import sys

import UnityPy

ROOT = '/Volumes/SSD/larsen/pokeland'
BUNDLE = sys.argv[1] if len(sys.argv) > 1 else (
    ROOT + '/cdn/pokeland/1.6.0/740e9608db30b5f19e739442a779e2e2/iOS/tables')
OUTDIR = sys.argv[2] if len(sys.argv) > 2 else ROOT + '/docs/tables'


def main():
    os.makedirs(OUTDIR, exist_ok=True)
    env = UnityPy.load(BUNDLE)

    index = {}
    for obj in env.objects:
        if obj.type.name != 'MonoBehaviour':
            continue
        tree = obj.read_typetree()
        name = tree.get('m_Name')
        if not name:
            continue

        # Every table object is a thin wrapper around one `m_table` array.
        rows = tree.get('m_table')
        if rows is None:
            # A few descriptors carry their fields inline instead of as a table.
            rows = {k: v for k, v in tree.items()
                    if k not in ('m_GameObject', 'm_Enabled', 'm_Script', 'm_Name')}

        short = name[4:] if name.startswith('gen_') else name
        path = os.path.join(OUTDIR, short + '.json')
        with open(path, 'w') as fh:
            json.dump(rows, fh, indent=1, ensure_ascii=False)

        count = len(rows) if isinstance(rows, list) else 1
        fields = sorted(rows[0]) if isinstance(rows, list) and rows and isinstance(rows[0], dict) \
            else sorted(rows) if isinstance(rows, dict) else []
        index[short] = {'rows': count, 'fields': fields, 'file': short + '.json'}

    with open(os.path.join(OUTDIR, '_index.json'), 'w') as fh:
        json.dump(index, fh, indent=1, ensure_ascii=False)

    print(f'extracted {len(index)} tables -> {OUTDIR}')
    for name, meta in sorted(index.items(), key=lambda kv: -kv[1]['rows'])[:25]:
        print(f'  {meta["rows"]:>6}  {name}')


if __name__ == '__main__':
    main()
