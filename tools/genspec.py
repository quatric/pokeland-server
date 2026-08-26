import re, json, os
BASE='/Volumes/SSD/larsen/pokeland'
src=open(BASE+'/out/dump/game.cs').read()
blocks=re.split(r'(?m)^// Namespace: ', src)
types={}
for b in blocks[1:]:
    lines=b.split('\n'); ns=lines[0].strip()
    if not ns.startswith('Uskumru.Proto'): continue
    rest='\n'.join(lines[1:])
    m=re.search(r'(?m)^((?:public|internal|private|protected).*?(?:class|struct|enum|interface)\s+[A-Za-z0-9_.<>]+.*?)(?:\s*//\s*TypeDefIndex.*)?$', rest)
    if not m: continue
    decl=re.sub(r'\s*//.*$','',m.group(1)).strip()
    body=rest[m.end():]
    end=body.find('\n}')
    types.setdefault(ns,[]).append((decl, body[:end+2] if end>=0 else body))

def fields(body):
    out=[]
    for fm in re.finditer(r'(?m)^\t(?:public|internal|private|protected)\s+(?:static\s+|readonly\s+|const\s+)*([A-Za-z0-9_.<>,\[\]\? ]+?)\s+([A-Za-z0-9_<>]+)\s*;\s*//\s*(0x[0-9A-Fa-f]+)', body):
        n=fm.group(2)
        if n.startswith(('<','m_','s_')): continue
        out.append((fm.group(1).strip(), n, fm.group(3)))
    return out

def enumvals(body):
    return re.findall(r'(?m)^\tpublic const [A-Za-z0-9_.]+ ([A-Za-z0-9_]+) = (-?\d+);', body)

endpoints=[]; entities=[]
for ns in sorted(types):
    short=ns[len('Uskumru.Proto'):].lstrip('.')
    has_req=any(re.search(r'\bclass Req\b',d) for d,_ in types[ns])
    (endpoints if has_req and short not in ('Validations','Ex') else entities).append(ns)

md=[]
A=md.append
A("# Pokéland (Pokémon Scramble SP) — `Uskumru` server protocol")
A("")
A("Recovered from `jp.pokemon.pokemonscrambleSP` v1.6.0 / IL2CPP metadata v24.1.")
A("")
A("## Transport")
A("")
A("| | |")
A("|---|---|")
A("| API URL | `https://prd.app.pokeland.jp/1.600/game` — a **single** POST endpoint |")
A("| Asset CDN | `https://dl.app.pokeland.jp/pokeland/` |")
A("| Protocol version | `1.600` (`Uskumru.Proto.Endpoint.Version`) |")
A("| Deployment abbrev | `AWS_PRD` |")
A("| Encoding | JSON (Newtonsoft `[JsonObject]`), UTF-8, TLS only — no payload crypto |")
A("| Auth | Nintendo BaaS `89feb806a5d5eb69fc3ef4a83b921c45.baas.nintendo.com`, client id `943a6bf00ff9f3e5`, `Authorization: Bearer <idToken>` |")
A("| Bad-word / geo | `https://pokemon-webapi.appspot.com/api/badword/v1/check_word`, `/api/location/v1/estimate_country` |")
A("")
A("The RPC is selected by the `Endpoint` field **inside the body**, not by URL path.")
A("")
A("### Request envelope — `Uskumru.Proto.Base.Req`")
A("")
A("```jsonc")
A('{ "Endpoint": "<name>", "Rev": 0, "SessionID": "...", "DCI": [ ... ] }')
A("```")
A("")
A("`Rev` is a monotonic revision counter; a mismatch raises `UskumruRevMismatch` client-side.")
A("")
A("### Response envelope — `Uskumru.Proto.Base.BaseRes`")
A("")
A("```jsonc")
A('{ "Rev": 0, "UTCStr": "<server UTC>", "A": [ /* AutoRes piggyback deltas */ ] }')
A("```")
A("")
A("`AutoRes` piggybacks state deltas (currency, chests, missions, pokédex, …) onto every")
A("response, so most endpoints do not need dedicated sync calls.")
A("")
A("## Endpoints (%d)" % len(endpoints))
A("")

def render(ns_list, lvl=3):
    o=[]
    for ns in ns_list:
        short=ns[len('Uskumru.Proto'):].lstrip('.') or '(root)'
        o.append(f"{'#'*lvl} {short}")
        o.append("")
        for decl,body in types[ns]:
            nm=re.search(r'(?:class|struct|enum|interface)\s+([A-Za-z0-9_.<>]+)',decl)
            nm=nm.group(1) if nm else decl
            base=decl.split(':',1)[1].strip() if ':' in decl else ''
            ev=enumvals(body)
            if ev:
                o.append(f"`enum {nm}` — "+", ".join(f"`{k}={v}`" for k,v in ev)); o.append("")
                continue
            fl=fields(body)
            if not fl and not base: continue
            o.append(f"**{nm}**"+(f" : `{base}`" if base else ""))
            o.append("")
            if fl:
                o.append("| type | field |"); o.append("|---|---|")
                for t,f,_ in fl: o.append(f"| `{t}` | `{f}` |")
            else:
                o.append("_(no additional fields)_")
            o.append("")
    return o

md+=render(endpoints)
A("## Shared entities & enums (%d namespaces)" % len(entities))
A("")
md+=render(entities)

os.makedirs(BASE+'/out/spec', exist_ok=True)
open(BASE+'/out/spec/PROTOCOL.md','w').write('\n'.join(md))
j={}
for ns in sorted(types):
    j[ns]={}
    for decl,body in types[ns]:
        nm=re.search(r'(?:class|struct|enum|interface)\s+([A-Za-z0-9_.<>]+)',decl)
        nm=nm.group(1) if nm else decl
        j[ns][nm]={'decl':decl,
                   'fields':[{'type':t,'name':f,'offset':o} for t,f,o in fields(body)],
                   'enum':dict(enumvals(body))}
json.dump(j,open(BASE+'/out/spec/protocol.json','w'),indent=1)
open(BASE+'/out/spec/endpoints.txt','w').write('\n'.join(
    n[len('Uskumru.Proto'):].lstrip('.') for n in endpoints)+'\n')
print("endpoints:",len(endpoints),"entity ns:",len(entities))
print("PROTOCOL.md:",os.path.getsize(BASE+'/out/spec/PROTOCOL.md'),"bytes")
