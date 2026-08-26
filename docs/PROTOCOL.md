# Pokéland (Pokémon Scramble SP) — `Uskumru` server protocol

Recovered from `jp.pokemon.pokemonscrambleSP` v1.6.0 / IL2CPP metadata v24.1.

## Transport

| | |
|---|---|
| API URL | `https://prd.app.pokeland.jp/1.600/game` — a **single** POST endpoint |
| Asset CDN | `https://dl.app.pokeland.jp/pokeland/` |
| Protocol version | `1.600` (`Uskumru.Proto.Endpoint.Version`) |
| Deployment abbrev | `AWS_PRD` |
| Encoding | JSON (Newtonsoft `[JsonObject]`), UTF-8, TLS only — no payload crypto |
| Auth | Nintendo BaaS `89feb806a5d5eb69fc3ef4a83b921c45.baas.nintendo.com`, client id `943a6bf00ff9f3e5`, `Authorization: Bearer <idToken>` |
| Bad-word / geo | `https://pokemon-webapi.appspot.com/api/badword/v1/check_word`, `/api/location/v1/estimate_country` |

The RPC is selected by the `Endpoint` field **inside the body**, not by URL path.

### Request envelope — `Uskumru.Proto.Base.Req`

```jsonc
{ "Endpoint": "<name>", "Rev": 0, "SessionID": "...", "DCI": [ ... ] }
```

`Rev` is a monotonic revision counter; a mismatch raises `UskumruRevMismatch` client-side.

### Response envelope — `Uskumru.Proto.Base.BaseRes`

```jsonc
{ "Rev": 0, "UTCStr": "<server UTC>", "A": [ /* AutoRes piggyback deltas */ ] }
```

`AutoRes` piggybacks state deltas (currency, chests, missions, pokédex, …) onto every
response, so most endpoints do not need dedicated sync calls.

## Endpoints (63)

### AddNormalSocketCount

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `long` | `PPEId` |
| `int` | `Count` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### AddPPELevel

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `long` | `PPEId` |
| `int` | `AddLevel` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### Base

**Req**

| type | field |
|---|---|
| `int` | `Rev` |
| `string` | `SessionID` |
| `int[]` | `DCI` |

**BaseRes**

| type | field |
|---|---|
| `int` | `Rev` |
| `string` | `UTCStr` |
| `AutoRes[]` | `A` |

**Res** : `BaseRes`

_(no additional fields)_

### BaseDF

**Req** : `Req, IAutoReq, IAutoReq`

| type | field |
|---|---|
| `AutoReq` | `SetDoneFlag` |
| `AutoReq` | `RecordMissions` |

**Res** : `Res, IAutoRes, IAutoRes`

_(no additional fields)_

### BuyStoreSize

**Req** : `Req`

| type | field |
|---|---|
| `int` | `PPEStoreSizeBuyUnit` |
| `int` | `NormalEqunitStoreSizeBuyUnit` |
| `int` | `SpEqunitStoreSizeBuyUnit` |

**Res** : `Res`

_(no additional fields)_

### BuyUtensil

**Req** : `Req`

| type | field |
|---|---|
| `UtensilID` | `UtensilID` |
| `int` | `Count` |

**Res** : `Res`

_(no additional fields)_

### ChestStartUnlock

**Req** : `Req`

| type | field |
|---|---|
| `long` | `ChestId` |

**Res** : `Res`

| type | field |
|---|---|
| `Bool` | `Success` |

### ChestUseJitanTicket

**Req** : `Req`

| type | field |
|---|---|
| `long` | `ChestId` |
| `int` | `Count` |

**Res** : `Res`

_(no additional fields)_

### ChstgGetStage

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `Bool` | `Refresh` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `StageCodeX[]` | `StageCode` |

### CommitHome

**BaseAutoReq** : `Req, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq`

| type | field |
|---|---|
| `AutoReq` | `CommitKpi` |
| `AutoReq` | `CommitNonActiveSec` |
| `AutoReq` | `CommitUpdatedEqunit` |
| `AutoReq` | `CommitUpdatedEvedef` |
| `AutoReq` | `CommitUpdatedPPE` |
| `AutoReq` | `CommitUpdatedIsland` |

**BaseAutoRes** : `Res, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

| type | field |
|---|---|
| `AutoRes` | `CommitNonActiveSec` |

**Req** : `BaseAutoReq`

_(no additional fields)_

**Res** : `BaseAutoRes`

_(no additional fields)_

**IAutoReq** : `IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq`

_(no additional fields)_

**IAutoRes** : `IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

_(no additional fields)_

### CommitUpdatedGuestUserProfile

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<string>` | `UpdatedGUPFavidstrValues` |
| `List<GuestUserProfileMemo>` | `UpdatedGUPMemos` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### DoDailyProcess

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

### EndStage

**Req** : `Req, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq`

| type | field |
|---|---|
| `BattleResult` | `BattleResult` |
| `int` | `GotMoney` |
| `int` | `GotPierreCount` |
| `long` | `PlayerPPEDropId` |
| `List<PPEDropGot>` | `GotPPEDrops` |
| `List<EqunitDropGot>` | `GotEqunitDrops` |
| `Bool` | `GotChest` |
| `PokedexDiffs` | `PokedexDiffs` |
| `PokedexDiffs` | `StagePokedexDiffs` |
| `int` | `ChangeCount` |
| `float` | `DPS` |
| `int` | `JissionResults` |
| `AutoReq` | `CommitUpdatedEqunit` |
| `AutoReq` | `CommitUpdatedPPE` |

**Res** : `Res, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

| type | field |
|---|---|
| `List<PPEUpdate>` | `PPEUpdates` |
| `List<int>` | `JissionDiamonds` |

### EqunitMount

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `SocketNo` | `SocketNo` |
| `long` | `EqunitId` |
| `long` | `PPEId` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### EqunitUpgrade

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `long` | `TargetEqunitId` |
| `Bool` | `UseMulti` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### FindMysland

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `float` | `X` |
| `float` | `Y` |
| `FindMyslandBy` | `By` |
| `IslandCodeX[]` | `PriorityIslandCode` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `IslandCodeX[]` | `Found` |

### GetAnnouncements

**Req** : `Req, IAutoReq`

| type | field |
|---|---|
| `int` | `Skip` |
| `int` | `Take` |
| `int` | `MinAnnouncementId` |
| `int` | `MaxAnnouncementId` |
| `List<int>` | `AnnouncementIds` |

**Res** : `Res, IAutoRes`

_(no additional fields)_

### GetArrivedGifts

**Req** : `Req, IAutoReq`

_(no additional fields)_

**Res** : `Res, IAutoRes`

| type | field |
|---|---|
| `List<Gift>` | `Gifts` |

### GetChestContentRatio

**Req** : `Req`

| type | field |
|---|---|
| `long` | `ChestId` |

**Res** : `Res`

| type | field |
|---|---|
| `ChestContentRatio` | `ChestContentRatio` |

### GetEndOfServiceInfos

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `string` | `CountryCode` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `int` | `EndOfServiceAnnouncementId` |
| `int` | `EndOfShopAnnouncementId` |
| `string` | `RefundCode` |

### GetEvepotRanking

**Req** : `Req`

| type | field |
|---|---|
| `EvepotID` | `EvepotID` |
| `int` | `Count` |
| `string` | `BaaSUserId` |

**Res** : `Res`

| type | field |
|---|---|
| `List<EvepotRankingParticipant>` | `EvepotRankingParticipants` |
| `string` | `TimestampUTCStr` |

### GetGuestMyslands

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<IslandCodeX>` | `GuestIslandCodes` |
| `Bool` | `InEventOnly` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### GetJourneyWinners

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<JourneyWinner>` | `JWs` |

### GetMyUserProfile

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

### GetMyslandDiscoverers

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `int` | `Count` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<MyslandDiscoverer>` | `MyslandDiscoverers` |

### GetMyslands

**Req** : `BaseAutoReq`

_(no additional fields)_

**Res** : `BaseAutoRes`

_(no additional fields)_

### GetRanking

**Req** : `Req`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `int` | `Count` |
| `string` | `BaaSUserId` |

**Res** : `Res`

| type | field |
|---|---|
| `List<RankingParticipant>` | `RankingParticipants` |
| `string` | `TimestampUTCStr` |

### GetReceivedGifts

**Req** : `Req`

| type | field |
|---|---|
| `int` | `Skip` |
| `int` | `Take` |

**Res** : `Res`

| type | field |
|---|---|
| `List<Gift>` | `Gifts` |

### GetSeldomInfo

**Req** : `BaseAutoReq`

_(no additional fields)_

**Res** : `BaseAutoRes`

_(no additional fields)_

### Goodbye

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<long>` | `GoodbyePPEIds` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### GoodbyeChests

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<long>` | `GoodbyeChestIds` |
| `List<long>` | `GetChestIds` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### GoodbyeEqunits

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<long>` | `GoodbyeEqunitIds` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `Bool` | `HaveCStopGifts` |

### GreetGuestUsers

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `int` | `Count` |
| `List<string>` | `BaaSUserIds` |
| `List<string>` | `BaaSUserMacs` |
| `List<GuestUserProfileSerialized>` | `GUPs` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<string>` | `GreetedBaaSUserIds` |

### LinkNA

**Req** : `Req`

| type | field |
|---|---|
| `string` | `NintendoAccountId` |

**Res** : `Res`

_(no additional fields)_

### ListEvepotRewards

**Req** : `BaseAutoReq, IAutoReqTZOffsetMin`

| type | field |
|---|---|
| `int` | `TZOffsetMin` |
| `List<EvepotID>` | `EvepotIDs` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<EvepotRankingParticipant>` | `MyEvepotRankings` |

### ListMissions

**Req** : `BaseAutoReq`

_(no additional fields)_

**Res** : `BaseAutoRes`

_(no additional fields)_

### ListStageChestContent

**Req** : `Req`

| type | field |
|---|---|
| `StageCodeX[]` | `StageCode` |

**Res** : `Res`

| type | field |
|---|---|
| `Bool` | `HaveChest` |
| `List<UnitPrefix>` | `NotableSpEqunits` |
| `List<UnitPrefix>` | `StageEqunits` |

### Login

**Req** : `Req, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReqTZOffsetMin`

| type | field |
|---|---|
| `string` | `Market` |
| `string` | `AppVer` |
| `string` | `AssetVer` |
| `int` | `TZOffsetMin` |

**Res** : `Res, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

| type | field |
|---|---|
| `Reset[]` | `Reset` |
| `string` | `SessionID` |

### OpenChest

**Req** : `Req`

| type | field |
|---|---|
| `long` | `ChestId` |
| `OpenChestBy` | `By` |
| `int` | `Quantity` |
| `string` | `NintendoAccountId` |

**Res** : `Res`

| type | field |
|---|---|
| `OpenChestResult` | `Result` |
| `Bool` | `HaveCStopGifts` |

### PdecoMount

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `PdecoID` | `PdecoID` |
| `long` | `PPEId` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### PurchaseActivate

**Req** : `Req`

| type | field |
|---|---|
| `SKUID` | `SKUID` |
| `long` | `Magic` |

**Res** : `Res`

_(no additional fields)_

### PurchaseBegin

**Req** : `Req`

| type | field |
|---|---|
| `SKUID` | `SKUID` |
| `long` | `Magic` |

**Res** : `Res`

_(no additional fields)_

### PurchaseEnd

**Req** : `Req`

| type | field |
|---|---|
| `long` | `Magic` |
| `PurchaseResult` | `Success` |
| `int` | `ErrorType` |
| `int` | `ErrorCode` |

**Res** : `Res`

| type | field |
|---|---|
| `List<SKUID>` | `ProcessedSKUIDs` |

### PurchaseRecovered

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `PurchaseResult` | `Success` |
| `int` | `ErrorType` |
| `int` | `ErrorCode` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<SKUID>` | `ProcessedSKUIDs` |

### ReceiveEvepotRewards

**Req** : `BaseAutoReq, IAutoReqTZOffsetMin`

| type | field |
|---|---|
| `int` | `TZOffsetMin` |
| `List<EvepotRewardID>` | `EvepotRewardIDs` |
| `List<EvepotID>` | `EvepotIDs` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<EvepotRewardID>` | `ReceivedEvepotRewardIDs` |
| `List<EvepotRankingParticipant>` | `MyEvepotRankings` |

### ReceiveGifts

**Req** : `Req, IAutoReq`

| type | field |
|---|---|
| `Bool` | `ReceiveAll` |
| `List<long>` | `GiftIds` |

**Res** : `Res, IAutoRes`

| type | field |
|---|---|
| `List<long>` | `ReceivedGiftIds` |

### ReceiveMissionRewards

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `List<MissionID>` | `MissionIDs` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### RecordMissions

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

**AutoReq**

| type | field |
|---|---|
| `MissionCommit` | `MissionCommit` |

### RedeemWelcal

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `WelcalStepID` | `WelcalStepID` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `Bool` | `Success` |
| `Bool` | `HaveGifts` |

### SetAnnouncementState

**Req** : `Req, IAutoReq`

| type | field |
|---|---|
| `int` | `HeadMarkedAsReadAnnouncementId` |

**Res** : `Res, IAutoRes`

_(no additional fields)_

### SetCurrentEvent

**Req** : `Req, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq, IAutoReq`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `Bool` | `SetVisited` |
| `AutoReq` | `CommitUpdatedEqunit` |
| `AutoReq` | `CommitUpdatedPPE` |

**Res** : `Res, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

_(no additional fields)_

### SetDoneFlag

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

**AutoReq**

| type | field |
|---|---|
| `DoneFlagDiff` | `DoneFlagDiff` |

### SetFavoriteMysland

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### SetLoginBonusState

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

### SetMyUserProfile

**Req** : `Req`

| type | field |
|---|---|
| `MyUserProfile` | `MyUserProfile` |

**Res** : `Res`

_(no additional fields)_

### SetMyslandName

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |
| `string` | `Name` |

**Res** : `BaseAutoRes`

_(no additional fields)_

### SetStageCollectionCompleted

**Req** : `Req`

| type | field |
|---|---|
| `StageCodeX[]` | `StageCode` |

**Res** : `Res`

_(no additional fields)_

### StartStage

**Req** : `BaseAutoReq, IAutoReqTZOffsetMin`

| type | field |
|---|---|
| `StartStageMode` | `Mode` |
| `StageCodeX[]` | `StageCode` |
| `GuestUserProfileSerialized` | `GUP` |
| `int` | `TZOffsetMin` |
| `long` | `MaxBPPPEId` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `StartStageResult` | `Result` |
| `MyHabitatMap` | `MHM` |
| `List<PPEDrop>` | `PPEDrops` |
| `List<EqunitDrop>` | `EqunitDrops` |
| `string` | `MLABaaSUserId` |

### TmbtlEnd

**Req** : `Req, IAutoReq, IAutoReq, IAutoReq, IAutoReq`

| type | field |
|---|---|
| `TmbtlResult` | `TmbtlResult` |
| `int` | `DefeatCount` |
| `int` | `DrawCount` |

**Res** : `Res, IAutoRes, IAutoRes, IAutoRes, IAutoRes`

| type | field |
|---|---|
| `TmbtlEndEvepotResult` | `EvepotResult` |
| `List<EvepotRankingParticipant>` | `MyEvepotRankings` |

### TmbtlGetOpponents

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `TmbtlCodeX[]` | `TmbtlCode` |
| `Bool` | `Refresh` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `List<EvepotRankingParticipant>` | `MyEvepotRankings` |

### TmbtlStart

**Req** : `BaseAutoReq`

| type | field |
|---|---|
| `TmbtlStartBy` | `By` |
| `TmbtlCodeX[]` | `TmbtlCode` |

**Res** : `BaseAutoRes`

| type | field |
|---|---|
| `TmbtlStartResult` | `Result` |
| `TmbtlCodeX[]` | `TmbtlCode` |
| `Bool` | `IsPickedUp` |

### Unregister

**Req** : `Req`

_(no additional fields)_

**Res** : `Res`

_(no additional fields)_

### UpdateEvent

**Req** : `Req, IAutoReq`

_(no additional fields)_

**Res** : `Res, IAutoRes`

_(no additional fields)_

## Shared entities & enums (14 namespaces)

### (root)

**Endpoint**

| type | field |
|---|---|
| `string` | `Default` |
| `string` | `Base` |

**AutoRes**

| type | field |
|---|---|
| `AutoResValidField` | `ValidFields` |
| `AnnouncementState` | `AnnouncementState` |
| `int[]` | `DiamondFreePaid` |
| `ChestsDiff` | `ChestsDiff` |
| `ChestSummary` | `ChestSummary` |
| `int[]` | `Exp` |
| `EvedefSummary` | `EvedefSummary` |
| `int[]` | `GuestTotalVisitCount` |
| `EqunitsDiff` | `EqunitsDiff` |
| `EventScheduleSet` | `EventScheduleSet` |
| `Bool[]` | `IsGiftArrived` |
| `LoginBonusInfo` | `LoginBonusInfo` |
| `MissionSummary` | `MissionSummary` |
| `int[]` | `Money` |
| `int[]` | `PaidNormalSpEqunitStoreSize` |
| `int[]` | `PaidPPEStoreSize` |
| `PokedexSummary` | `PokedexSummary` |
| `PPEsDiff` | `PPEsDiff` |
| `Bool[]` | `PurchaseProcessing` |
| `SeldomInfoUser` | `SeldomInfo` |
| `Subscription` | `Subscription` |
| `TmbtlOpponentsDiff` | `TmbtlOpponentsDiff` |
| `List<Announcement>` | `UpdatedAnnouncements` |
| `List<Eqbit>` | `UpdatedEqbits` |
| `List<Evedef>` | `UpdatedEvedefs` |
| `List<Evepot>` | `UpdatedEvepots` |
| `List<EvepotReward>` | `UpdatedEvepotRewards` |
| `List<GuestUserProfileSerialized>` | `UpdatedGUPs` |
| `Honors` | `UpdatedHonors` |
| `List<Island>` | `UpdatedIslands` |
| `MyslandSummary` | `MyslandSummary` |
| `List<Mysland>` | `UpdatedMyslands` |
| `MyUserProfile` | `UpdatedMyUserProfile` |
| `List<Pdeco>` | `UpdatedPdecos` |
| `PokedexDetails` | `UpdatedPokedexDetails` |
| `List<Stage>` | `UpdatedStages` |
| `List<Utensil>` | `UpdatedUtensils` |
| `List<Welcal>` | `UpdatedWelcals` |

`enum AutoResValidField` — `EqunitsDiff=1`, `EventScheduleSet=2`, `AnnouncementState=4`, `EvedefSummary=8`, `MissionSummary=16`, `LoginBonusInfo=32`, `Subscription=64`, `MyslandSummary=128`, `UpdatedMyUserProfile=256`, `UpdatedPokedexDetails=512`, `ChestsDiff=1024`, `ChestSummary=2048`, `SeldomInfo=4096`, `PokedexSummary=8192`, `PPEsDiff=16384`, `TmbtlOpponentsDiff=32768`, `UpdatedHonors=65536`

**GuardedAutoRes** : `AutoRes`

_(no additional fields)_

`enum BattleResult` — `Lose=0`, `Clear=1`, `Cancel=2`, `MinValue=0`, `MaxValue=2`

**PokedexDiffs**

| type | field |
|---|---|
| `List<PokedexID>` | `DefeatedPokedexIDs` |
| `List<int>` | `DefeatedCounts` |
| `List<PokedexID>` | `DiscoveredPokedexIDs` |

**DoneFlagDiff**

| type | field |
|---|---|
| `List<DoneFlag>` | `Ons` |
| `List<DoneFlag>` | `Offs` |

**EvedefDiff**

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `IslandRankID` | `MarkedAsReadEPDIRID` |

**EqunitMountInfo**

| type | field |
|---|---|
| `SocketNo` | `SocketNo` |
| `long` | `EqunitId` |
| `long` | `PPEId` |

**Favid**

| type | field |
|---|---|
| `long` | `Value` |

**KpiDiff**

| type | field |
|---|---|
| `List<KpiID>` | `IDs` |
| `List<int>` | `Diffs` |

**Favidstr**

| type | field |
|---|---|
| `string` | `Value` |

**MissionCommit** : `BaseMissionSummary`

_(no additional fields)_

`enum PurchaseResult` — `Failed=0`, `Success=1`, `SuccessButNoWallets=2`

`enum TmbtlResult` — `Lose=0`, `Win=1`, `Cancel=2`, `Draw=3`, `MinValue=0`, `MaxValue=3`

`enum DiamondConsumeInfo` — `E=3`

**Announcement** : `BaseAnnouncement, IProtoEntity`

| type | field |
|---|---|
| `int` | `AnnouncementId` |
| `List<string>` | `Image` |

**AnnouncementState**

| type | field |
|---|---|
| `int` | `HeadMarkedAsReadAnnouncementId` |
| `int` | `HeadAnnouncementId` |
| `int` | `HeadValidAnnouncementId` |
| `int[]` | `EmergencyInfoAnnouncementIds` |
| `int[]` | `PopupAnnouncementIds` |

`enum AnnouncementType` — `Superseded=0`, `Info=1`, `EmergencyInfo=2`, `Popup=3`, `CommandDelete=4`, `EndOfService=5`, `_Missing=-1`

**AppManifest**

| type | field |
|---|---|
| `AppManifestItem[]` | `AppManifestItems` |
| `string` | `Version` |

`enum AppManifestItem` — `AppVerMaxLength=32`, `AssetVerMaxLength=64`

**SizeManifest**

| type | field |
|---|---|
| `AssetSizeInfo[]` | `AssetSizeInfos` |

**AssetSizeInfo**

| type | field |
|---|---|
| `AssetSizeItem[]` | `AssetSizeItems` |
| `string` | `AssetVer` |

**AssetSizeItem**

| type | field |
|---|---|
| `string` | `Name` |
| `int` | `Size` |

`enum BanFlag` — `Deleted=1`, `Banned=2`

**BaseAnnouncement**

| type | field |
|---|---|
| `int` | `SupersedingAnnouncementId` |
| `AnnouncementType` | `Type` |
| `List<string>` | `Title` |
| `string` | `CreatedUTCStr` |
| `string` | `PopupEndUTCStr` |
| `List<string>` | `Message0` |
| `List<string>` | `Message1` |
| `string` | `CCmFilter` |

`enum BaseEqunit` — `E=4`

**BaseGuestUserProfile** : `BaseUserProfile`

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `LastUpdatedUTCStr` |
| `short` | `LastUpdatedOffset` |
| `BasePPE` | `BasePPE` |
| `List<BaseEqunit>` | `BaseEqunits` |
| `IslandCodeX[]` | `MyslandFavorite` |
| `int` | `Exp` |

`enum BaseGift` — `ParamsCount=4`

**BaseMissionSummary**

| type | field |
|---|---|
| `string` | `DailyUTCStr` |
| `List<MissionID>` | `IDs` |
| `List<int>` | `Progresses` |

`enum BasePPE` — `E=12`

`enum BasePPEAndEqunits` — `E=24`

**BaseTmbtlOpponent**

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `Nickname` |
| `byte[]` | `MiiCoreData` |
| `HonorID` | `HonorID` |
| `int` | `HonorParam` |
| `List<BasePPEAndEqunits>` | `BPAEs` |

**BaseUserProfile**

| type | field |
|---|---|
| `string` | `Nickname` |
| `byte[]` | `MiiCoreData` |
| `LangID` | `LangID` |
| `GreetingMessageID` | `Greeting0` |
| `GreetingMessageID` | `Greeting1` |
| `short` | `GreetingParam0` |
| `short` | `GreetingParam1` |
| `float[]` | `LatLng` |
| `GenerationID` | `Generation` |
| `HonorID` | `HonorID` |
| `int` | `HonorParam` |
| `Bool` | `SealedLatLng` |
| `EvedefID` | `LatestRankingEvedefID` |
| `int` | `LatestRankingRank` |
| `int` | `LatestRankingTieRank` |
| `int` | `LatestRankingScore` |
| `EvedefID` | `BestRankingEvedefID` |
| `int` | `BestRankingRank` |
| `int` | `BestRankingTieRank` |
| `int` | `BestRankingScore` |

`enum Bool` — `False=0`, `True=1`

**DoneFlagSummary**

| type | field |
|---|---|
| `byte[]` | `DoneFlagVec` |

**Chest** : `IProtoEntity`

| type | field |
|---|---|
| `long` | `ChestId` |
| `ChestState` | `State` |
| `StageCodeX[]` | `StageCode` |
| `IslandRankID` | `IslandRankID` |
| `ChestTypeID` | `ChestTypeID` |
| `int` | `UsedJitanTicketCount` |
| `string` | `UnlockUTCStr` |
| `ChestContent` | `Content` |

**ChestContent**

| type | field |
|---|---|
| `float` | `SpEqunitCount` |
| `int` | `NormalEqunitCount` |
| `int` | `PierreCount` |
| `int` | `Money` |
| `List<UnitPrefix>` | `NotableEqunits` |
| `List<UnitPrefix>` | `FixedEqunits` |
| `List<UnitPrefix>` | `StageEqunits` |

**ChestContentRatio**

| type | field |
|---|---|
| `List<UnitPrefix>` | `SpEqunits` |
| `List<float>` | `SpEqunitRatios` |
| `List<UnitPrefix>` | `NormalEqunits` |
| `List<float>` | `NormalEqunitRatios` |

**ChestsDiff**

| type | field |
|---|---|
| `List<Chest>` | `UpdatedChests` |
| `List<long>` | `RemovedChestIds` |

`enum ChestState` — `Temporary=0`, `Locked=1`, `Unlocking=2`, `Unlocked=3`, `Removed=4`

**ChestSummary**

| type | field |
|---|---|
| `int` | `StoreSize` |
| `int` | `DailyGoodbyeCount` |

**Evedef** : `IProtoEntity`

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `PokedexSummary` | `PokedexSummary` |
| `EvedefPokedexFlag` | `PokedexFlags` |
| `int` | `JourneyCondMaxBP` |
| `short` | `Progress` |
| `Bool` | `IsVisited` |
| `string` | `LastBattleUTCStr` |
| `IslandRankID` | `MarkedAsReadEPDIRID` |
| `int` | `DiamondConsumed` |
| `int` | `DiamondConsumedExtra` |
| `int` | `PierreCount` |
| `Bool` | `PierreCountAdded` |
| `IslandRankID` | `JourneyIslandRankID` |
| `StageCodeX[]` | `ChstgStageCode` |

**EvedefPokedexDistribution**

| type | field |
|---|---|
| `List<PokeWazaType>` | `AttrPWTs` |
| `List<int>` | `Counts` |
| `List<PokedexID>` | `PokedexIDs` |
| `List<IslandRankID>` | `MinIslandRankIDs` |

`enum EvedefPokedexFlag` — `CompleteRewardDone=1`, `BondCompleteRewardDone=2`

**EvedefSchedule**

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `string` | `EndUTCStr` |
| `string` | `RedeemEndUTCStr` |
| `string` | `PickUpBeginUTCStr` |
| `string` | `PickUpEndUTCStr` |
| `string` | `RankingFixedEndUTCStr` |
| `EvedefPokedexDistribution` | `PokedexDist` |

**EventScheduleSet**

| type | field |
|---|---|
| `List<EvedefSchedule>` | `Evedefs` |
| `string` | `ExpireUTCStr` |

**Eqbit** : `IProtoEntity`

| type | field |
|---|---|
| `PrefixMaterial` | `PrefixMaterial` |
| `int` | `Count` |

`enum Equnit` — `E=12`

**EqunitsDiff**

| type | field |
|---|---|
| `List<Equnit>` | `UpdatedEqunits` |
| `List<long>` | `RemovedEqunitIds` |

`enum EqunitDrop` — `E=6`

**EqunitDropGot**

| type | field |
|---|---|
| `long` | `EqunitDropId` |
| `string` | `GotUTCStr` |

**EvedefSummary**

| type | field |
|---|---|
| `EvedefID` | `CurrentEvedefID` |
| `int` | `NrushDailyPlayCountLeft` |
| `int` | `TmbtlDailyPlayCountLeft` |
| `int` | `ChstgDailyPlayCountLeft` |

**Evepot** : `IProtoEntity`

| type | field |
|---|---|
| `EvepotID` | `EvepotID` |
| `int` | `Count` |
| `int` | `TotalCount` |

**TmbtlEndEvepotResult**

| type | field |
|---|---|
| `int` | `TbpParticipationPoint` |
| `int` | `TbpWinningPoint` |
| `int` | `TbpBonusPoint` |
| `int` | `DrawCount` |
| `int` | `DefeatCount` |
| `Bool` | `IsPickedUp` |
| `int` | `WinningPoint` |
| `int` | `Coeff` |

**EvepotRankingParticipant**

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `Nickname` |
| `byte[]` | `MiiCoreData` |
| `EvepotID` | `EvepotID` |
| `int` | `EvepotTotalCount` |
| `string` | `LastUpdatedUTCStr` |
| `int` | `Rank` |
| `int` | `TieRank` |

**EvepotReward** : `IProtoEntity`

| type | field |
|---|---|
| `EvepotRewardID` | `EvepotRewardID` |
| `int` | `Count` |
| `string` | `LastRedeemedUTCStr` |
| `int` | `LastRedeemedTzOffsetMin` |

`enum FindMyslandBy` — `Pierre=0`, `Diamond=1`

**Gift** : `BaseGift`

| type | field |
|---|---|
| `long` | `GiftId` |
| `Bool` | `IsReceived` |
| `string` | `LastUpdatedUTCStr` |

`enum GiftStatus` — `NotArrived=0`, `Arrived=1`, `Received=2`, `Expired=3`

**GuestUserProfile** : `BaseGuestUserProfile`

| type | field |
|---|---|
| `string` | `LastVisitUTCStr` |
| `short` | `LastVisitOffset` |
| `string` | `PreviousVisitUTCStr` |
| `short` | `PreviousVisitOffset` |
| `int` | `VisitCount` |
| `Bool` | `IsFavorite` |
| `string` | `Memo` |
| `Bool` | `StageInvitationAccepted` |

**GuestUserProfileSerialized**

| type | field |
|---|---|
| `string` | `J` |
| `string` | `Mac` |

**GuestUserProfileMemo**

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `Memo` |

**Honor**

| type | field |
|---|---|
| `HonorID` | `HonorID` |
| `int` | `Param` |

**Honors**

| type | field |
|---|---|
| `List<HonorID>` | `IDs` |
| `List<int>` | `Params` |

**InitializeTag**

| type | field |
|---|---|
| `int` | `value__` |

**Island** : `IProtoEntity`

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |
| `IslandState` | `State` |
| `IslandAnimState` | `AnimState` |
| `string` | `CreatedUTCStr` |
| `float` | `X` |
| `float` | `Y` |

`enum IslandAnimState` — `Future=0`, `NextNotReady=1`, `NextReady=2`, `Done=3`

**IslandDiff**

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |
| `IslandAnimState` | `AnimState` |

**IslandCodeX**

| type | field |
|---|---|
| `int` | `value__` |

`enum IslandState` — `Ready=1`, `Expired=2`, `DailyReady=3`, `DailyExpired=4`, `DailyReadyFree=5`

`enum JissionState` — `NotAchieved=0`, `Achieved=1`

**JourneyWinner** : `BaseGuestUserProfile`

| type | field |
|---|---|
| `int` | `ChangeCount` |
| `float` | `DPS` |

`enum LoginBonusCStopResult` — `GiftDiamond=4`, `GiftMoney=2`, `DiscardTicket=8`

**LoginBonusInfo**

| type | field |
|---|---|
| `string` | `LastDailyProcessUTCStr` |
| `int` | `TotalLoginDays` |
| `StampCardDays` | `StampCardDays` |
| `StampCardDays` | `LastResult_StampCardDays` |
| `int` | `LastResult_DiffDiamond` |
| `int` | `LastResult_DiffMoney` |
| `int` | `LastResult_DiffTicket` |
| `LoginBonusCStopResult` | `LastResult_CStop` |

**MaintenanceHerald**

| type | field |
|---|---|
| `string` | `MaintenanceBeginUTCStr` |
| `string` | `MaintenanceEndUTCStr` |
| `string` | `PopupBeginUTCStr` |
| `string` | `PopupEndUTCStr` |
| `int` | `AnnouncementId` |

**MaintenanceState** : `ILocalizedMessage`

| type | field |
|---|---|
| `string` | `BeginUTCStr` |
| `string` | `EndUTCStr` |
| `Bool` | `EndAutomatically` |
| `List<string>` | `LTMessages` |

`enum MissionState` — `InProgress=0`, `CanRedeem=1`, `Redeemed=2`

**MissionSummary** : `BaseMissionSummary`

| type | field |
|---|---|
| `List<MissionState>` | `States` |

**MyHabitatMap**

| type | field |
|---|---|
| `StageCodeX[]` | `StageCode` |
| `IslandRankID` | `IslandRankID` |
| `List<EnemyDesc>` | `EnemyDescs` |
| `List<EnemyBossDesc>` | `EnemyBossDescs` |
| `int` | `MaxDropMoney` |
| `int` | `MaxDropPierreCount` |
| `ChestTypeID` | `DropChestTypeID` |
| `Bool` | `IsSubscriptionDropActive` |
| `Bool` | `IsSubscriptionUnlockActive` |
| `Bool` | `IsFeverStage` |

**Mysland** : `BaseUserProfile, IProtoEntity`

| type | field |
|---|---|
| `IslandCodeX[]` | `IslandCode` |
| `string` | `Name` |
| `string` | `DiscovererBaaSUserId` |
| `float` | `X` |
| `float` | `Y` |
| `List<PokedexID>` | `Capturables` |
| `PokedexID` | `Boss` |

**MyslandDiscoverer**

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `BaaSUserMac` |
| `string` | `Nickname` |
| `byte[]` | `MiiCoreData` |
| `IslandCodeX[]` | `IslandCode` |
| `PokeWazaType` | `ColorHint` |
| `string` | `LastUpdatedUTCStr` |
| `float` | `X` |
| `float` | `Y` |
| `PokedexID` | `Boss` |

**MyslandSummary**

| type | field |
|---|---|
| `IslandCodeX[]` | `Favorite` |
| `IslandCodeX[]` | `LastVisited` |

**MyUserProfile** : `BaseUserProfile`

_(no additional fields)_

`enum OpenChestBy` — `None=0`, `Diamond=1`, `JitanTicket=2`

`enum OpenChestResult` — `Success=0`, `ErrorNotYetUnlocked=1`, `ErrorInconsistentQuantity=2`

`enum PartyMember` — `NONE=0`, `IsPlayer=1`, `MemberIndex0=2`, `MemberIndex1=4`, `MemberIndex2=6`, `_MemberIndex_shift=1`

**Pdeco** : `IProtoEntity`

| type | field |
|---|---|
| `PdecoID` | `PdecoID` |
| `int` | `Count` |

**PokedexDetail**

| type | field |
|---|---|
| `EvedefID` | `EvedefID` |
| `PokedexID` | `PokedexID` |
| `int` | `MaxPlainBP` |
| `int` | `DefeatedCount` |
| `int` | `CapturedCount` |
| `int` | `CapturedSexVec` |

**PokedexDetails**

| type | field |
|---|---|
| `List<EvedefID>` | `EvedefIDs` |
| `List<PokedexID>` | `PokedexIDs` |
| `List<int>` | `MaxPlainBPs` |
| `List<int>` | `DefeatedCounts` |
| `List<int>` | `CapturedCounts` |
| `List<int>` | `CapturedSexVecs` |

**PokedexSummary**

| type | field |
|---|---|
| `byte[]` | `DiscoveredVec` |
| `byte[]` | `CapturedVec` |

`enum PPE` — `E=21`

**PPEsDiff**

| type | field |
|---|---|
| `List<PPE>` | `UpdatedPPEs` |
| `List<long>` | `RemovedPPEIds` |

`enum PPEDrop` — `E=26`

**PPEDropGot**

| type | field |
|---|---|
| `long` | `PPEDropFavidValue` |
| `string` | `GotUTCStr` |
| `string` | `Nickname` |

**PPENickname**

| type | field |
|---|---|
| `long` | `PPEId` |
| `string` | `Nickname` |

**PPESummary**

| type | field |
|---|---|
| `List<long>` | `PartyPPEIds` |

**PPEUpdate**

| type | field |
|---|---|
| `long` | `PPEDropId` |
| `long` | `PPEId` |
| `List<long>` | `EqunitIds` |

**RankingParticipant**

| type | field |
|---|---|
| `string` | `BaaSUserId` |
| `string` | `Nickname` |
| `byte[]` | `MiiCoreData` |
| `List<PokedexID>` | `PokedexIDs` |
| `List<int>` | `MaxPlainBPs` |
| `string` | `LastUpdatedUTCStr` |
| `int` | `Rank` |
| `int` | `TieRank` |

**Reset**

| type | field |
|---|---|
| `long` | `SupportNumber` |
| `int` | `Money` |
| `int` | `DiamondFree` |
| `int` | `DiamondPaid` |
| `int` | `DiamondConsumed` |
| `int` | `DiamondConsumedExtra` |
| `Bool` | `IsGiftArrived` |
| `int` | `Exp` |
| `int` | `GuestTotalVisitCount` |
| `AnnouncementState` | `AnnouncementState` |
| `ChestSummary` | `ChestSummary` |
| `DoneFlagSummary` | `DoneFlagSummary` |
| `EvedefSummary` | `EvedefSummary` |
| `List<Eqbit>` | `Eqbits` |
| `List<Equnit>` | `Equnits` |
| `int` | `PaidNormalEqunitStoreSize` |
| `int` | `PaidSpEqunitStoreSize` |
| `List<Evedef>` | `Evedefs` |
| `EventScheduleSet` | `EventScheduleSet` |
| `List<Evepot>` | `Evepots` |
| `List<GuestUserProfileSerialized>` | `GUPs` |
| `Honors` | `Honors` |
| `List<Island>` | `Islands` |
| `LoginBonusInfo` | `LoginBonusInfo` |
| `MissionSummary` | `MissionSummary` |
| `MyslandSummary` | `MyslandSummary` |
| `List<Mysland>` | `Myslands` |
| `MyUserProfile` | `MyUserProfile` |
| `PokedexDetails` | `PokedexDetails` |
| `PokedexSummary` | `PokedexSummary` |
| `List<PPE>` | `PPEs` |
| `int` | `PaidPPEStoreSize` |
| `List<Stage>` | `Stages` |
| `int` | `TotalSec` |
| `List<Chest>` | `Chests` |
| `List<Pdeco>` | `Pdecos` |
| `List<Utensil>` | `Utensils` |
| `List<Welcal>` | `Welcals` |
| `Subscription` | `Subscription` |
| `Bool` | `PurchaseProcessing` |
| `SeldomInfoUser` | `SeldomInfo` |

**SeldomInfo**

| type | field |
|---|---|
| `int` | `EulaRev` |
| `string` | `EndOfServiceCCmFilter` |
| `string` | `EndOfShopCCmFilter` |
| `MaintenanceHerald` | `MaintenanceHerald` |

**SeldomInfoUser** : `SeldomInfo`

| type | field |
|---|---|
| `BanFlag` | `BanFlag` |

**ServerError**

| type | field |
|---|---|
| `string` | `Message` |
| `string` | `ExceptionMessage` |
| `string` | `ExceptionType` |
| `string` | `StackTrace` |
| `string` | `ExceptionId` |
| `string` | `BadRequestId` |

**ServerError1** : `ServerError`

| type | field |
|---|---|
| `ServerError2` | `InnerException` |

**ServerError2** : `ServerError`

| type | field |
|---|---|
| `ServerError3` | `InnerException` |

**ServerError3** : `ServerError`

| type | field |
|---|---|
| `ServerError4` | `InnerException` |

**ServerError4** : `ServerError`

| type | field |
|---|---|
| `ServerError5` | `InnerException` |

**ServerError5** : `ServerError`

| type | field |
|---|---|
| `ServerError6` | `InnerException` |

**ServerError6** : `ServerError`

_(no additional fields)_

`enum ServiceUnavailableReason` — `None=0`, `TryAgainSomeTimeLater=1`, `Maintenance0=2`, `Maintenance1=3`, `Maintenance2=4`

**Stage** : `IProtoEntity`

| type | field |
|---|---|
| `StageCodeX[]` | `StageCode` |
| `StageState` | `State` |
| `PokedexSummary` | `PokedexSummary` |
| `Bool` | `CollectionRewarded` |
| `int` | `ClearCount` |
| `List<JissionState>` | `JissionStates` |
| `List<PokedexID>` | `Bosses` |
| `List<PokedexID>` | `Capturables` |
| `List<PokedexID>` | `Prizes` |

**StageCodeX**

| type | field |
|---|---|
| `int` | `value__` |

`enum StageState` — `Ready=0`, `Visited=1`, `Cleared=2`

`enum StartStageMode` — `StageCode=0`, `GUP=1`, `NrushOrChstgByDiamond=2`

`enum StartStageResult` — `Success=0`, `ErrorNotInEvent=1`, `ErrorGUPExpired=2`

**TmbtlCodeX**

| type | field |
|---|---|
| `int` | `value__` |

**TmbtlOpponent** : `BaseTmbtlOpponent, IProtoEntity`

| type | field |
|---|---|
| `long` | `TmbtlOpponentId` |
| `TmbtlCodeX[]` | `TmbtlCode` |

**TmbtlOpponentsDiff**

| type | field |
|---|---|
| `List<TmbtlOpponent>` | `UpdatedTmbtlOpponents` |
| `List<long>` | `RemovedTmbtlOpponentIds` |

`enum TmbtlStartBy` — `DailyPlayCount=0`, `Diamond=1`

`enum TmbtlStartResult` — `Success=0`, `ErrorNotInEvent=1`

**TimeSpanSecond**

| type | field |
|---|---|
| `int` | `Low` |
| `int` | `High` |

`enum UnauthorizedReason` — `None=0`, `NewUserButNotLoggedIn=2`, `NotLoggedIn=3`, `InvalidSession=4`, `ExpiredClient=5`, `InvalidAppVer=6`, `InvalidAssetVer=7`

**Utensil** : `IProtoEntity`

| type | field |
|---|---|
| `UtensilID` | `UtensilID` |
| `int` | `Count` |

**Welcal** : `IProtoEntity`

| type | field |
|---|---|
| `WelcalID` | `WelcalID` |
| `int` | `DayDone` |
| `string` | `LastRedeemedUTCStr` |

**Subscription**

| type | field |
|---|---|
| `List<SKUID>` | `UnlockSKUIDs` |
| `List<string>` | `UnlockExpireUTCStr` |
| `SKUID` | `DropSKUID` |
| `string` | `DropExpireUTCStr` |
| `SKUID` | `LoginBonusSKUID` |
| `string` | `LoginBonusExpireUTCStr` |
| `List<SKUID>` | `WaitingSKUIDs` |
| `Bool` | `IsBonusSetSoldOut` |

`enum Util` — `Ver4CoreDataLength=48`

**IslandStateStr** : `EnumString<IslandState>`

_(no additional fields)_

### CommitKpi

**AutoReq**

| type | field |
|---|---|
| `KpiDiff` | `KpiDiff` |

### CommitNonActiveSec

**AutoReq**

| type | field |
|---|---|
| `int` | `NonActiveSec` |

**AutoRes**

| type | field |
|---|---|
| `int` | `TotalSec` |

### CommitUpdatedEqunit

**AutoReq**

| type | field |
|---|---|
| `List<long>` | `UpdatedEqunitFavidValues` |
| `List<long>` | `EMIs` |

### CommitUpdatedEvedef

**AutoReq**

| type | field |
|---|---|
| `List<EvedefDiff>` | `EvedefDiffs` |

### CommitUpdatedIsland

**AutoReq**

| type | field |
|---|---|
| `List<IslandDiff>` | `IslandDiffs` |

### CommitUpdatedPPE

**AutoReq**

| type | field |
|---|---|
| `long` | `PlayerPPEId` |
| `List<long>` | `PartyPPEIds` |
| `List<long>` | `UpdatedPPEFavidValues` |
| `List<PPENickname>` | `UpdatedPPENicknames` |

### EnterHome

**IAutoReq** : `IAutoReq, IAutoReq, IAutoReq`

_(no additional fields)_

**IAutoRes** : `IAutoRes, IAutoRes, IAutoRes`

_(no additional fields)_

### Ex

### IsGiftArrived

### ServiceUnavailable

**Res**

| type | field |
|---|---|
| `ServiceUnavailableReason` | `Reason` |
| `MaintenanceState` | `MaintenanceState` |
| `MaintenanceHerald` | `MaintenanceHerald` |

### Unauthorized

**Res**

| type | field |
|---|---|
| `UnauthorizedReason` | `Reason` |

### UpdateAnnouncement

### Validations

**LocalizedMessagesAttribute** : `ValidationAttribute`

_(no additional fields)_

**EnumRangeAttribute** : `ValidationAttribute`

_(no additional fields)_

**EnumRange0Attribute** : `EnumRangeAttribute`

_(no additional fields)_

**EnumRange1Attribute** : `EnumRangeAttribute`

_(no additional fields)_

**IslandCodeAttribute** : `ValidationAttribute`

_(no additional fields)_

**StageCodeAttribute** : `ValidationAttribute`

_(no additional fields)_

**TmbtlCodeAttribute** : `ValidationAttribute`

_(no additional fields)_

**MiiCoreDataAttribute** : `ValidationAttribute`

_(no additional fields)_
