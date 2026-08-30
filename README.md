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

**1. The archived CDN is iOS-only.** (Solved - see "Android" below.)


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

This was solved by repacking rather than by finding Android assets; see below.

**2. Archive coverage is 122 of 155 assets (~79%).** (All 122 are now mirrored and
converted; the 33 that follow were never captured at all.)

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
2. ~~Crack open `iOS/tables`.~~ **Done** — see "Game data" below.
3. **Implement the boot path** in order: `Login` → `EnterHome` → `CommitHome` →
   `GetMyUserProfile` → `ChstgGetStage` → `StartStage` → `EndStage`. That chain is
   what gets a client from the title screen into a battle.
4. **Decide the client story** (iOS vs. repack) before investing in gameplay
   endpoints, since it determines what you can actually test against.
5. **Stub the BaaS dependency.** The client talks to `baas.nintendo.com` for its
   bearer token; a revival needs that redirected to a local stand-in or the login
   path patched out.

## Android

The Android client boots against this server with no DNS interception and no
device-side certificate work. Three patches to the APK plus an asset conversion.

```bash
tools/build_apk.sh http://10.0.2.2:5199        # emulator -> host
tools/build_apk.sh http://192.168.1.50:5199    # real device on the LAN
adb install -r build/pokeland-1.6.0-patched.apk
```

### The three APK patches

| file | change | why |
|---|---|---|
| `global-metadata.dat` | `https://prd.app.pokeland.jp` and `https://dl.app.pokeland.jp` rewritten to the server base | the hosts are IL2CPP string literals; rewriting them in place avoids DNS interception entirely |
| `assets/npf.json` | `baasHost` -> server, `useHttp` -> `true` | the NPF SDK hard-codes `https` for the Nintendo account backend *unless* this flag is set, which would otherwise force a TLS stand-in and a device-installed CA |
| `AndroidManifest.xml` | `targetSdkVersion` 28 -> 27 | restores the permissive cleartext-HTTP default. Adding `usesCleartextTraffic` would mean inserting an AXML attribute and resizing every enclosing chunk; the SDK level is a single in-place 4-byte edit |

String literals live as (length, dataIndex) pairs over a flat blob, so a shorter
replacement only needs its length field updated - nothing else in the file moves
and every other metadata offset stays valid. The APK is re-signed with a local
debug key (v1, which Android accepts below targetSdk 30).

Two `https://…pokeland.jp` strings survive in `fieldAndParameterDefaultValueData`.
Those are `const string` default-value blobs kept for reflection; the compiler
already inlined those consts into the literals that were patched, so no traffic
uses them.

### Asset conversion

`tools/repack_android.py` turns the archived iOS bundles into Android ones:

- `target_platform` iOS (9) -> Android (13). Note `SerializedFile.save()` writes
  the raw `_m_target_platform` int, not the `BuildTarget` property - setting only
  the latter looks like it worked and silently changes nothing.
- PVRTC textures re-encoded to ETC2_RGBA8. PVRTC is PowerVR-only; Adreno and Mali
  cannot sample it. Most textures are already RGB24/RGBA32 and are left alone.

Repacking with `packer='original'` keeps the source's LZMA, so the tree grows
about 1.9x (ETC2 costs 1 byte/px against PVRTC's 0.5) rather than the ~5x that
writing LZ4 would cost.

### The BaaS stand-in

The client will not call `Login` until the NPF SDK has authenticated a device
account against Nintendo's BaaS. `Baas.cs` implements that gateway; the shapes
came from decompiling `com.nintendo.npf.sdk` out of `classes.dex` (BaaSAuth,
CoreHttpClient, BaasUserMapper):

- `POST /core/v1/gateway/sdk/login` — mints a device account on first run and
  returns it as `createdDeviceAccount`; later logins replay it and get a stable
  user id back
- `POST /core/v1/gateway/sdk/federation` — same, for a linked Nintendo Account
- `/core/v1/users/{id}`, `/core/v1/analytics/*` — the SDK calls these and treats
  a 404 as an `NPFError`

### Verified boot chain

Every step exercised against the running server with the real request shapes:

| step | result |
|---|---|
| `GET /pre/AppManifest?market=GOOGLE&magic=…` | AppVer -> AssetVer mapping |
| `POST /core/v1/gateway/sdk/login` | device account + `idToken` |
| `GET /pokeland/<AssetVer>/Android/size_manifest.json` | regenerated for the converted tree |
| `POST /1.600/game` `Login` + `Authorization: Bearer` | SessionID + valid envelope |
| `GET /pokeland/<AssetVer>/Android/equnit-icons` | UnityFS bundle, Android-targeted |

The asset tree is complete: all 123 archived files SHA1-verified against the CDX
digests, all 122 bundles converted (202.8 MB), every serialized file reporting
`target_platform` 13, no PVRTC left anywhere, and all 122 served over HTTP at the
exact sizes the regenerated manifest advertises.

**Not yet done:** no Android device or emulator was available here, so this is
verified at the protocol level - every request the client makes gets a correct
response - not by booting the game and rendering a frame.

## iOS client

`jp.pokemon.pokemonscrambleSP` **1.6.1** (build 1060101, arm64, `MinimumOSVersion`
9.0) — the global "Pokémon Rumble Rush" build, which kept the JP bundle id.

Its `global-metadata.dat` is not encrypted, so the protocol could be verified
against it directly, and it is **identical to Android 1.6.0**:

- same API version — the client still posts to `/1.600/game`
- same hosts — `prd.app.pokeland.jp`, `dl.app.pokeland.jp`
- all 63 `Uskumru.Proto` namespace names present, and 491 of 498 protocol field and
  enum names (the 7 misses are 1–2 character names that the ≥3-char scan could not
  test, not real differences)
- the only iOS-exclusive identifiers are Unity GameCenter / local-notification APIs

So the server speaks to both clients unmodified. The one client-visible difference
is the AppManifest gate, which is per-store:

| market | magic |
|---|---|
| `GOOGLE` | `798d799c0ec24e1f0d7ff1f5a1a74cd9` |
| `APPLE` | `f388d2d02c48702efacde9ca0d977b45` |

Both are configured, and AppVer `1.6.1` is mapped onto the archived 1.6.0 asset set.

**The blocker: the IPA is still FairPlay-encrypted** (`LC_ENCRYPTION_INFO_64`,
`cryptid = 1`, 39.8 MB of `__TEXT` encrypted). That has two consequences:

1. Il2CppDumper cannot dump it — `CodeRegistration` lives in the encrypted range,
   so the search finds plausible-looking addresses and then walks off the end of
   the array. Only the metadata is readable.
2. More importantly, it **cannot be sideloaded**. Re-signing requires a decrypted
   binary, and FairPlay keys are per-Apple-ID for an app that is now delisted.

A decrypted dump is needed — the usual route is `frida-ios-dump` or `flexdecrypt`
on a jailbroken device that has the app installed, or finding an already-decrypted
copy. Everything else on the iOS path is ready and waiting for that.

## Game data

`iOS/tables` (244 KB) turned out to hold the entire master database: 103
ScriptableObjects, one per table. The iOS build kept its serialized **type trees**,
so the rows deserialize directly — no need to reconstruct type info from the IL2CPP
dump. `tools/extract_tables.py` dumps all of them to `docs/tables/*.json` (26 MB),
with `docs/tables/_index.json` listing row counts and field names.

| table | rows | |
|---|---|---|
| `PresetDesc` | 7041 | |
| `StageDesc` | 6396 | 1311 populated - stage layout, target CP, weather, weak type |
| `IslandDesc` | 6220 | |
| `MissionDesc` | 2264 | |
| `PiiDesc` | 1108 | pokémon: types, HP/AP/DP, size/weight, evolution, moves |
| `WazaDesc` | 1028 | moves |
| `ChestTypeDesc` | 559 | |
| `TypeChartDesc` | 19 | full 19x19 type-effectiveness matrix |
| `ConstDesc` | 87 | global tuning constants |

Spot-checked against real Pokémon data and correct: `m_Monsno` 1 = フシギダネ
(Grass/Poison), 4 = ヒトカゲ, 25 = ピカチュウ, 150 = ミュウツー. Type ids are the
game's own ordering (12 = Grass, 10 = Fire, 11 = Water, 13 = Electric,
14 = Psychic); `TypeChartDesc` is authoritative, with `m_matchupType` 1..4 for
immune / resisted / neutral / super-effective.

This is what `StartStage`, `EndStage` and `ChstgGetStage` need to answer with real
content instead of stubs.

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
