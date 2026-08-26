#!/usr/bin/env python3
"""Generate the C# protocol library for the Pokeland revival server.

Source of truth is the Il2CppDumper output of jp.pokemon.pokemonscrambleSP 1.6.0.
Base classes are resolved by *field offset* rather than by the dumper's flattened
declaration text, which loses the namespace of same-named bases (`Req : Req`).
"""
import json, os, re, keyword

BASE = '/Volumes/SSD/larsen/pokeland'
SRC  = json.load(open(BASE + '/out/spec/protocol.json'))
GAME = open(BASE + '/out/dump/game.cs').read()
OUT  = BASE + '/server/Pokeland.Protocol/Generated'
ROOT = 'Pokeland.Protocol'
PIGGYBACK = {'AutoReq', 'AutoRes'}

short = lambda ns: ns[len('Uskumru.Proto'):].lstrip('.')
cs_ns = lambda ns: ROOT + ('.' + short(ns) if short(ns) else '')

ENDPOINTS = [l.strip() for l in open(BASE + '/out/spec/endpoints.txt')
             if l.strip() and l.strip() not in ('Base', 'BaseDF')]
SKIP_NS = {'Ex', 'Validations'}

# ------------------------------------------------------------ game type index
# Types in the game assembly's global namespace that the protocol references
# directly (EvedefID, DoneFlag, EnemyDesc, ...). Indexed so they can be pulled
# in transitively.
GAME_ENUMS, GAME_CLASSES = {}, {}
for m in re.finditer(r'(?m)^public (enum|class|struct) ([A-Za-z0-9_]+)[^\n]*\n\{\n(.*?)(?=\n\})',
                     GAME, re.S):
    kind, name, body = m.group(1), m.group(2), m.group(3)
    if '.' in name or '<' in name:
        continue
    if kind == 'enum':
        GAME_ENUMS.setdefault(name, re.findall(
            r'(?m)^\tpublic const [A-Za-z0-9_.]+ ([A-Za-z0-9_]+) = (-?\d+);', body))
    else:
        flds = []
        for fm in re.finditer(
                r'(?m)^\tpublic (?:static\s+|readonly\s+)*([A-Za-z0-9_.<>,\[\]\? ]+?)\s+'
                r'([A-Za-z0-9_]+)\s*;\s*//\s*(0x[0-9A-Fa-f]+)', body):
            fn = fm.group(2)
            if fn.startswith(('<', 'm_', 's_')) or fn == 'value__':
                continue
            flds.append((fm.group(1).strip(), fn))
        GAME_CLASSES.setdefault(name, flds)

# ---------------------------------------------------------------- declarations
declared = {}
for ns, ts in SRC.items():
    if short(ns) in SKIP_NS:
        continue
    for nm in ts:
        declared.setdefault(nm, set()).add(ns)

def is_iface(nm, decl):
    return 'interface' in decl

# ------------------------------------------------------- base type resolution
def end_offset(ns, nm):
    """Byte offset just past this type's own last field (0 == inherits nothing new)."""
    t = SRC[ns][nm]
    if not t['fields']:
        return None
    last = t['fields'][-1]
    return int(last['offset'], 16) + 8       # 64-bit slots; good enough to match starts

def first_offset(ns, nm):
    f = SRC[ns][nm]['fields']
    return int(f[0]['offset'], 16) if f else None

# candidate base classes, keyed by their simple name
CANDIDATES = {}
for ns, ts in SRC.items():
    if short(ns) in SKIP_NS:
        continue
    for nm, t in ts.items():
        if 'class' in t['decl'] and not is_iface(nm, t['decl']):
            CANDIDATES.setdefault(nm, []).append(ns)

# explicit chain roots we know from the offsets
CHAIN = {
    ('Uskumru.Proto.BaseDF', 'Req'):          f'{ROOT}.Base.Req',
    ('Uskumru.Proto.BaseDF', 'Res'):          f'{ROOT}.Base.Res',
    ('Uskumru.Proto.CommitHome', 'BaseAutoReq'): f'{ROOT}.BaseDF.Req',
    ('Uskumru.Proto.CommitHome', 'BaseAutoRes'): f'{ROOT}.BaseDF.Res',
    ('Uskumru.Proto.Base', 'Res'):            f'{ROOT}.Base.BaseRes',
}

def resolve_base(ns, nm, bname):
    if (ns, nm) in CHAIN:
        return CHAIN[(ns, nm)]
    if bname in ('Req', 'Res'):
        # An endpoint's `Req : Req` extends the envelope base, never itself.
        start = first_offset(ns, nm)
        for cand_ns, cand in ((f'Uskumru.Proto.Base', bname),
                              (f'Uskumru.Proto.BaseDF', bname)):
            if start is not None and end_offset(cand_ns, cand) == start:
                return f'{ROOT}.{short(cand_ns)}.{bname}'
        return f'{ROOT}.Base.{bname}'
    if bname in ('BaseAutoReq', 'BaseAutoRes'):
        return f'{ROOT}.CommitHome.{bname}'
    owners = CANDIDATES.get(bname)
    if owners:
        if ns in owners:
            return bname
        if 'Uskumru.Proto' in owners:
            return f'{ROOT}.{bname}'
        return f'{cs_ns(sorted(owners)[0])}.{bname}'
    return bname

def base_of(decl):
    if ':' not in decl:
        return None
    for p in (x.strip() for x in decl.split(':', 1)[1].split(',')):
        if not re.match(r'^I[A-Z]', p):
            return p
    return None

# ------------------------------------------------------------- type mapping
needed_global = set()

def map_type(t, field_name, ns):
    t = t.strip()
    m = re.fullmatch(r'(?:List|IList|IEnumerable)<(.+)>', t)
    if m:
        return f'List<{map_type(m.group(1), field_name, ns)}>'
    if t.endswith('[]'):
        return map_type(t[:-2], field_name, ns) + '[]'
    if t.startswith('Dictionary<'):
        return t
    if t in PIGGYBACK:
        target = 'Uskumru.Proto.' + field_name
        if target in SRC and t in SRC[target]:
            return f'{ROOT}.{field_name}.{t}'
        return f'{ROOT}.{t}'
    owners = declared.get(t)
    if owners:
        if ns in owners:
            return t
        if 'Uskumru.Proto' in owners:
            return f'{ROOT}.{t}'
        return f'{cs_ns(sorted(owners)[0])}.{t}'
    if t in GAME_ENUMS or t in GAME_CLASSES:
        needed_global.add(t)
        return f'{ROOT}.{t}'
    return t

sanitize = lambda n: '@' + n if keyword.iskeyword(n) else n

# ------------------------------------------------------------------- emit
os.makedirs(OUT, exist_ok=True)
for f in os.listdir(OUT):
    os.remove(os.path.join(OUT, f))

HEADER = ['// <auto-generated>',
          '//   Generated by tools/gen_csharp.py from the IL2CPP dump of',
          '//   jp.pokemon.pokemonscrambleSP 1.6.0. Do not edit by hand.',
          '// </auto-generated>',
          '#nullable disable',
          'using System.Collections.Generic;',
          'using Newtonsoft.Json;',
          '']

count = 0
for ns in sorted(SRC):
    if short(ns) in SKIP_NS:
        continue
    lines = HEADER + [f'namespace {cs_ns(ns)};', '']
    emitted = False
    for nm, t in SRC[ns].items():
        decl, fields, enum = t['decl'], t['fields'], t['enum']
        if is_iface(nm, decl):
            continue
        if 'enum' in decl.split('{')[0]:
            lines.append(f'public enum {nm}')
            lines.append('{')
            for k, v in enum.items():
                lines.append(f'    {sanitize(k)} = {v},')
            lines.append('}')
            lines.append('')
            emitted = True; count += 1
            continue
        if 'class' not in decl and 'struct' not in decl:
            continue
        kind = 'struct' if 'struct' in decl.split('{')[0] else 'class'
        abstract = 'abstract ' if 'abstract' in decl else ''
        if kind == 'struct':
            abstract = ''
        b = base_of(decl)
        inherit = f' : {resolve_base(ns, nm, b)}' if (b and kind == 'class') else ''
        lines.append('[JsonObject(MemberSerialization.OptIn)]')
        lines.append(f'public {abstract}{kind} {nm}{inherit}')
        lines.append('{')
        if nm == 'Req' and short(ns) == 'Base':
            lines.append('    [JsonProperty("Endpoint")]')
            lines.append('    public abstract string Endpoint { get; }')
            lines.append('')
        elif nm == 'Req' and short(ns) in ENDPOINTS:
            lines.append('    [JsonProperty("Endpoint")]')
            lines.append(f'    public override string Endpoint => "{short(ns)}";')
            lines.append('')
        for f in fields:
            ft = map_type(f['type'], f['name'], ns)
            lines.append(f'    [JsonProperty("{f["name"]}")]')
            lines.append(f'    public {ft} {sanitize(f["name"])} {{ get; set; }}')
        lines.append('}')
        lines.append('')
        emitted = True; count += 1
    if emitted:
        open(os.path.join(OUT, (short(ns) or 'Proto') + '.g.cs'), 'w').write('\n'.join(lines))

# game types pulled in by reference, closed transitively
done = set()
lines = HEADER + [f'namespace {ROOT};', '',
                  "// Types declared in the game assembly's global namespace that the",
                  '// Uskumru protocol references directly or transitively.', '']
while needed_global - done:
    nm = sorted(needed_global - done)[0]
    done.add(nm)
    if nm in GAME_ENUMS:
        lines.append(f'public enum {nm}')
        lines.append('{')
        for k, v in GAME_ENUMS[nm]:
            lines.append(f'    {sanitize(k)} = {v},')
        lines.append('}')
        lines.append('')
        count += 1
    elif nm in GAME_CLASSES:
        lines.append('[JsonObject(MemberSerialization.OptIn)]')
        lines.append(f'public class {nm}')
        lines.append('{')
        for ft, fn in GAME_CLASSES[nm]:
            lines.append(f'    [JsonProperty("{fn}")]')
            lines.append(f'    public {map_type(ft, fn, None)} {sanitize(fn)} {{ get; set; }}')
        lines.append('}')
        lines.append('')
        count += 1
if len(lines) > len(HEADER) + 5:
    open(os.path.join(OUT, 'GameTypes.g.cs'), 'w').write('\n'.join(lines))

print(f'generated {count} types in {len(os.listdir(OUT))} files')
print(f'game types pulled in ({len(needed_global)}): {sorted(needed_global)}')
