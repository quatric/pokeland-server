# Pokéland revival server

Server reimplementation for **Pokémon Scramble SP / Pokéland**
(`jp.pokemon.pokemonscrambleSP`, JP, service ended 2020), reconstructed from the
1.6.0 Android build and the Wayback Machine's copy of the retail CDN.

## What was recovered

The client is Unity 2018.4.11f1 / IL2CPP with an **unencrypted
`global-metadata.dat`**, so a full type dump was possible — no Cpp2IL guesswork,
no obfuscation to unwind. The networking layer is a Nintendo-internal library
codenamed **Uskumru**, and the protocol is plain Newtonsoft JSON over HTTPS.

| | |
|---|---|
| API | `https://prd.app.pokeland.jp/1.600/game` — one POST endpoint for everything |
| Bootstrap | `https://prd.app.pokeland.jp/pre/AppManifest?market=GOOGLE&magic=798d799c0ec24e1f0d7ff1f5a1a74cd9` |
| Asset CDN | `https://dl.app.pokeland.jp/pokeland/<AssetVer>/<Platform>/<bundle>` |
| Auth | Nintendo BaaS (`89feb806a5d5eb69fc3ef4a83b921c45.baas.nintendo.com`), client id `943a6bf00ff9f3e5`, `Authorization: Bearer <idToken>` |
| Endpoints | **61**, selected by an `Endpoint` field *inside the body*, not by URL path |
| Payload crypto | none beyond TLS |

`docs/PROTOCOL.md` documents every endpoint's request and response fields;
`docs/protocol.json` is the same thing machine-readable.

### The envelope

```jsonc
// request  - Uskumru.Proto.Base.Req
{ "Endpoint": "StartStage", "Rev": 0, "SessionID": "...", "DCI": [ ... ] }

// response - Uskumru.Proto.Base.BaseRes
{ "Rev": 0, "UTCStr": "2020-07-21T22:12:11Z", "A": [ /* AutoRes deltas */ ] }
```

`A` is the interesting part: **every** response can piggyback state deltas —
currency, chest timers, mission progress, pokédex, event schedules — so the game
has almost no dedicated sync calls. A revival server gets most of its behaviour
from populating `AutoRes` correctly.

## What runs today

```
server/
  Pokeland.Protocol/   299 generated C# types - the whole wire protocol
  Pokeland.Server/     ASP.NET Core host: bootstrap + dispatcher + CDN
tools/
  genspec.py           dump.cs  -> PROTOCOL.md / protocol.json
  gen_csharp.py        protocol.json -> C# DTOs
  fetch_cdn.sh         Wayback -> local CDN mirror (size-verified)
docs/                  protocol spec, archived retail manifests
cdn/                   mirrored asset bundles
```

Run it:

```bash
cd server/Pokeland.Server && ASPNETCORE_URLS=http://127.0.0.1:5199 dotnet run
```

Verified working end to end against the real protocol shapes:

| flow | result |
|---|---|
| `GET /pre/AppManifest` | matches the archived retail response structure exactly |
| `POST /1.600/game` `Login` | issues a SessionID, returns a valid envelope |
| authenticated call, no handler yet | well-formed empty envelope, HTTP 200 |
| bad SessionID | HTTP 401 + `Unauthorized.Res {Reason: 4 /* InvalidSession */}` |
| wrong protocol version | HTTP 401 + `{Reason: 5 /* ExpiredClient */}` |
| unknown endpoint | HTTP 400 |
| CDN asset | serves the 8.4 MB `bgm/01` UnityFS bundle byte-exact |

`GET /_status` lists which of the 61 endpoints have handlers.

The dispatcher discovers endpoints by reflection over the generated namespaces, so
implementing one is just adding an `IEndpointHandler` — see
`Handlers/LoginHandler.cs`. Everything without a handler already answers with a
valid empty envelope rather than an error, which is what keeps the client moving
while the surface is filled in.

## Two things that constrain the plan

**1. The archived CDN is iOS-only, and iOS bundles will not load on Android.**

This is worth being blunt about because it changes the approach. The Wayback copy
covers `.../1.6.0/740e9608db30b5f19e739442a779e2e2/iOS/...` only; there is no
`Android/` tree in the archive, and the client picks its platform directory at
runtime via `GetPlatformName()` (both `"iOS"` and `"Android"` are in the binary).

Reading the bundle headers directly: the archived bundles report
`target_platform = 9`, which is Unity's `BuildTarget.iOS`. Unity refuses to load
an AssetBundle whose target platform does not match the running player, and the
textures inside are PVRTC rather than ETC2/ASTC. So these files cannot be served
to the Android APK as-is, and pointing the Android client at them will fail at
bundle-load time rather than at the network layer.

Realistic options, roughly in order of effort:

- **Use the iOS client.** The assets match it exactly. Needs the 1.6.0 IPA.
- **Repack.** Extract with UnityPy/AssetStudio and rebuild as Android bundles.
  Mechanical for meshes/audio/text; the texture recompression is lossy and
  shaders may need rebuilding. This is the actual "port", and it is real work.
- **Find an Android mirror.** Nothing in Wayback, but the asset paths are
  predictable, so other archives are worth checking.

**2. Archive coverage is 122 of 155 assets (~79%).**

Cross-checking the retail `size_manifest.json` against the CDX index, 33 bundles
were never captured — mostly past event definitions (`z-evedef/002`–`020`),
localized illustration sets, and the CJK font packs. The core game (stages,
pokémon, UI, scenes, tables, text) is fully archived. The gaps mean those
specific events cannot be restored from the archive and would have to be
reconstructed or dropped.

Also note the mirror script size-verifies every download: Wayback silently
truncates large binaries (`bgm/02` came back 3.8 MB instead of 5.68 MB on the
first attempt), so anything fetched without checking against `size_manifest.json`
is not trustworthy.

## Suggested next steps

1. **Finish the mirror** (`tools/fetch_cdn.sh 1.6.0` — resumable, size-verified).
2. **Crack open `iOS/tables`** (244 KB). It holds the stage/pokémon/drop tables the
   server needs in order to answer `StartStage`, `EndStage` and `ChstgGetStage`
   with real content instead of stubs. This is the highest-value single asset.
3. **Implement the boot path** in order: `Login` → `EnterHome` → `CommitHome` →
   `GetMyUserProfile` → `ChstgGetStage` → `StartStage` → `EndStage`. That chain is
   what gets a client from the title screen into a battle.
4. **Decide the client story** (iOS vs. repack) before investing in gameplay
   endpoints, since it determines what you can actually test against.
5. **Stub the BaaS dependency.** The client talks to `baas.nintendo.com` for its
   bearer token; a revival needs that redirected to a local stand-in or the login
   path patched out.

## Notes on the Dragalia Lost comparison

The structural similarity is real and useful: same Nintendo NPF SDK, same
BaaS-token-in-`Authorization` pattern, same "one POST endpoint, big JSON DTOs"
shape that DragaliaAPI reimplements. What does *not* carry over is the payload
format — Dragalia uses MessagePack, Pokéland uses plain Newtonsoft JSON, which is
considerably easier to work with. The dispatcher-plus-generated-DTOs layout here
mirrors DragaliaAPI's approach deliberately.

## Provenance

- APK: `jp.pokemon.pokemonscrambleSP` 1.6.0 (1060009), arm64-v8a + armeabi-v7a
- Dump: Il2CppDumper v6.7.46, metadata v24.1
- CDN + `/pre/AppManifest` captures: Wayback Machine / ArchiveTeam ArchiveBot
- Internal codenames seen in the binary: `Uskumru` (netcode), `Libamb`
  (Ambrella engine library), `caviar` (Firebase project)

This is a preservation project for a game whose servers were shut down in 2020.
