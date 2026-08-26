# pokeland-server

A server reimplementation for **Pokémon Scramble SP / Pokéland**
(`jp.pokemon.pokemonscrambleSP`), the Pokémon spin-off whose servers were shut
down in 2020, plus the tooling to point an original client at it.

The game is a Unity 2018.4.11f1 / IL2CPP title. Its networking layer is a
library internally codenamed **Uskumru**, and the wire protocol is plain
Newtonsoft JSON over HTTPS with no payload encryption.

## Protocol

| | |
|---|---|
| API | `https://prd.app.pokeland.jp/1.600/game` — one POST endpoint for everything |
| Bootstrap | `https://prd.app.pokeland.jp/pre/AppManifest?market=GOOGLE&magic=…` |
| Asset CDN | `https://dl.app.pokeland.jp/pokeland/<AssetVer>/<Platform>/<bundle>` |
| Accounts | Nintendo BaaS, via the NPF SDK bundled in the client |
| Endpoints | **61**, selected by an `Endpoint` field *inside the body*, not by URL path |

`docs/PROTOCOL.md` documents every endpoint's request and response fields;
`docs/protocol.json` is the same thing machine-readable, and `docs/endpoints.txt`
is the bare list.

### Envelope

```jsonc
// request  - Uskumru.Proto.Base.Req
{ "Endpoint": "StartStage", "Rev": 0, "SessionID": "…", "DCI": [ … ] }

// response - Uskumru.Proto.Base.BaseRes
{ "Rev": 0, "UTCStr": "2020-07-21T22:12:11Z", "A": [ /* AutoRes deltas */ ] }
```

`A` is the interesting part: **every** response can piggyback state deltas —
currency, chest timers, mission progress, pokédex, event schedules — so the game
has almost no dedicated sync calls. A server gets most of its behaviour from
populating `AutoRes` correctly.

## Layout

```
server/
  Pokeland.Protocol/   299 generated C# types - the whole wire protocol
  Pokeland.Server/     ASP.NET Core host: bootstrap, dispatcher, BaaS, CDN
tools/
  genspec.py           IL2CPP dump  -> PROTOCOL.md / protocol.json
  gen_csharp.py        protocol.json -> C# DTOs
  fetch_cdn.sh         Wayback -> local CDN mirror (SHA1-verified)
  verify_cdn.py        audit a mirror against the CDX digests
  repack_android.py    iOS asset bundles -> Android
  extract_tables.py    pull the master data tables out of the `tables` bundle
  patch_metadata.py    rewrite the client's hard-coded server URLs
  patch_npf.py         repoint the NPF SDK's account backend
  patch_manifest.py    lower targetSdkVersion so cleartext HTTP works
  build_apk.sh         all three patches + re-sign
docs/                  protocol specification
```

## Running

```bash
cd server/Pokeland.Server
ASPNETCORE_URLS=http://0.0.0.0:5199 dotnet run
```

`GET /_status` reports which of the 61 endpoints have handlers.

The dispatcher discovers endpoints by reflection over the generated namespaces,
so implementing one means adding an `IEndpointHandler` — see
`Handlers/LoginHandler.cs`. Everything without a handler answers with a valid
empty envelope rather than an error, which keeps the client moving while the
surface is filled in.

## Pointing a client at it

```bash
POKELAND_APK=/path/to/original.apk tools/build_apk.sh http://192.168.1.50:5199
adb install -r build/pokeland-1.6.0-patched.apk
```

Three in-place patches, so no DNS interception and no certificate on the device:

| file | change | why |
|---|---|---|
| `global-metadata.dat` | the two hard-coded hosts rewritten to the server base | they are IL2CPP string literals, stored as (length, dataIndex) pairs over a flat blob — a shorter replacement only needs its length field updated, so nothing else in the file moves |
| `assets/npf.json` | `baasHost` -> server, `useHttp` -> `true` | the NPF SDK hard-codes `https` for the account backend *unless* this flag is set, which would otherwise force a TLS stand-in and a device-installed CA |
| `AndroidManifest.xml` | `targetSdkVersion` 28 -> 27 | restores the permissive cleartext-HTTP default; adding `usesCleartextTraffic` would mean inserting an AXML attribute and resizing every enclosing chunk, where the SDK level is a single in-place 4-byte edit |

The APK is re-signed with a locally generated debug key (signature scheme v1,
which Android accepts below targetSdk 30).

## Assets

Only an iOS asset tree survives in public web archives, and Unity refuses to load
an AssetBundle built for a different platform. `repack_android.py` converts one:

- `target_platform` iOS (9) -> Android (13). Note that UnityPy's
  `SerializedFile.save()` writes the raw `_m_target_platform` int rather than the
  `BuildTarget` property, so setting only the latter looks like it worked and
  silently changes nothing.
- PVRTC textures re-encoded to ETC2_RGBA8. PVRTC is PowerVR-only; Adreno and Mali
  cannot sample it. Most textures are already RGB24/RGBA32 and are left alone.

Repacking with `packer='original'` keeps the source's LZMA, so the tree grows
about 1.9x (ETC2 costs 1 byte/px against PVRTC's 0.5) rather than the ~5x that
writing LZ4 would cost.

`fetch_cdn.sh` mirrors from the Wayback Machine. Two things bite there and both
are handled: replay silently truncates large bodies behind a clean HTTP 200, and
resuming a truncated file with a Range request can splice a bad prefix onto a
good tail — producing a file of exactly the right length whose contents are
wrong. Correctness is therefore decided by the SHA1 the CDX index records for
each stored payload, never by size. `verify_cdn.py` audits an existing mirror the
same way.

## Accounts

The client will not call `Login` until the NPF SDK has authenticated a device
account, so `Baas.cs` stands in for that gateway:

- `POST /core/v1/gateway/sdk/login` — mints a device account on first run and
  returns it as `createdDeviceAccount`; later logins replay it and get a stable
  user id back
- `POST /core/v1/gateway/sdk/federation` — the same, for a linked account
- `/core/v1/users/{id}`, `/core/v1/analytics/*` — the SDK calls these and treats
  a 404 as an error

## Status

Working: the bootstrap handshake, account authentication, asset delivery, and
`Login`. The remaining 60 endpoints are described in `docs/PROTOCOL.md` and
answer with valid empty envelopes, but are not implemented, so this does not yet
produce a playable game.

The boot chain is verified at the protocol level — every request a client makes
receives a correctly shaped response — but has not been confirmed by booting the
game on a physical device.

## What is not here

No game code, assets, binaries, or extracted data are distributed with this
repository, and none will be. The tools operate on a copy you supply yourself:

- `build_apk.sh` needs an original APK via `POKELAND_APK`
- `fetch_cdn.sh` retrieves assets from public web archives at run time
- `extract_tables.py` reads the game's own data tables out of an asset bundle you
  already have

Pokémon and Pokémon Scramble SP are trademarks of Nintendo, Creatures Inc. and
GAME FREAK Inc. This project is unaffiliated with, and unendorsed by, any of
them. It exists to document a protocol that is no longer served anywhere.

## Credits

The protocol was recovered with [Il2CppDumper](https://github.com/Perfare/Il2CppDumper);
asset handling uses [UnityPy](https://github.com/K0lb3/UnityPy) and
[etcpak](https://github.com/wolfpld/etcpak); the account SDK was read with
[jadx](https://github.com/skylot/jadx). Asset archives come from the Internet
Archive's Wayback Machine and ArchiveTeam's ArchiveBot.

## Contact

quatricsoftware@gmail.com

No support will be provided for this tool.

## License

Copyright (c) 2026 quatric. Released under the MIT License; see `LICENSE`.
