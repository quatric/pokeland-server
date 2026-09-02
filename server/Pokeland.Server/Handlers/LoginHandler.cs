#nullable disable
using System.Linq;
using Pokeland.Protocol;
using Pokeland.Server;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The first call every client makes after fetching the AppManifest. It is the only
/// endpoint that runs without a SessionID; its response hands one back, and every
/// later request echoes it in the envelope.
/// </summary>
public sealed class LoginHandler : IEndpointHandler
{
    public string Endpoint => "Login";

    /// <summary>
    /// m_islandMonsNo from IslandDesc.json rows 0..6, indexed by IslandID.
    /// </summary>

    public object Handle(object request, GameSession _, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.Login.Req)request;

        // The retail server authenticated the Nintendo BaaS bearer token here. A
        // revival deployment has no BaaS to check against, so identity comes from
        // whatever the client presents and every login opens a fresh session.
        var session = ctx.Sessions.Create(baasUserId: null);
        session.Market = req.Market;
        session.AppVer = req.AppVer;
        session.AssetVer = req.AssetVer;
        session.TimeZoneOffsetMinutes = req.TZOffsetMin;

        ctx.Log.LogInformation(
            "login: market={Market} appVer={AppVer} assetVer={AssetVer} tz={Tz} -> session {Session}",
            req.Market, req.AppVer, req.AssetVer, req.TZOffsetMin, session.SessionId);

        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");

        // AutoRes deltas only ever *update* an existing Uskumru.Cache box - the
        // client's own decompiled Cache.ProcessRes<object> reads the current
        // Cache.MyUserProfileBox (etc.) and throws if it is null; it never
        // constructs one. Uskumru.Cache..ctor(Reset, myBaaSUserId) is the only
        // code that builds a box, from a Reset object, and its one caller is a
        // generic response-handling coroutine (Uskumru.ClientTask.<iWait>d__12).
        //
        // Confirmed via Ghidra decompile of that coroutine (Aug 30 2026): it
        // gates the whole response-processing branch on `Res.Rev != 0` and
        // silently no-ops otherwise - having a populated Reset[] was never
        // the missing piece, a zero Rev was. See GameSession.Rev.
        var reset = new Reset
        {
            // 0 tells the client no player ID has been issued yet, which pops a
            // "Player ID Registration" overlay on top of the ready-to-play dialog
            // and stalls boot - hand back a stable 12-digit ID (like the retail
            // support number) derived from the session instead.
            SupportNumber = 100000000000L + (long)((uint)session.SessionId.GetHashCode() % 900000000000L),
            // From the persisted account, not a constant: money a run
            // banked through EndStage has to still be there next launch.
            Money = ctx.Players.Current.Money,
            DiamondFree = ctx.Players.Current.DiamondFree,
            DiamondPaid = ctx.Players.Current.DiamondPaid,
            DiamondConsumed = 0,
            DiamondConsumedExtra = 0,
            IsGiftArrived = Bool.False,
            Exp = 0,
            GuestTotalVisitCount = 0,
            AnnouncementState = Announcements.StateFor(ctx.Players.Current.HeadMarkedAsReadAnnouncementId),
            ChestSummary = new ChestSummary { StoreSize = 3 },
            // DoneFlagBox indexes this bit vector by DoneFlag, whose largest
            // member is 163, so give it enough zero bytes to cover the whole
            // enum rather than an empty array a read could run off the end of.
            // Flags come from the persisted account rather than being zeroed:
            // the client keeps no progress of its own, so handing back a clear
            // vector here is what replayed the tutorial (and the birthday gate)
            // on every single launch.
            DoneFlagSummary = new DoneFlagSummary { DoneFlagVec = ctx.Players.Current.ToDoneFlagVec() },
            // FOUND (2026-09-01, headless Ghidra decompile of
            // CampPageMain.SetupAfterLogin, RVA 0xF1D7FC - the earlier decompiles
            // of it used a bare toAddr(rva) and so disassembled an unrelated
            // function; this project's image base is 0x100000, see
            // ~/ghidra_scripts/CheckBase.java). Its first block is:
            //
            //     if (m_eventBanner != null) {
            //         var eb = Cache.EvedefBox;
            //         if (!eb.IsTutorial) {
            //             if (!eb.IsIntermission) {
            //                 m_eventBanner.SetActive(true);
            //                 m_eventTimer.UTC = eb.Current.EndUTC;   // <-- NRE
            //             } else m_eventBanner.SetActive(false);
            //         } else m_eventBanner.SetActive(false);
            //     }
            //
            // IsTutorial and IsIntermission just test CurrentEvedefID against
            // EvedefID.Tutorial (1) and EvedefID.Intermission (16), and Current
            // is a dictionary lookup of that same ID in the (empty) Evedefs
            // list. Leaving CurrentEvedefID at its default of NONE (0) means
            // neither guard fires, so the client walks into the "an event is
            // running, show its countdown" branch and dereferences a null
            // Current. Intermission is the honest description of a server with
            // no event scheduled, and it takes the branch that hides the banner
            // without needing an Evedef to exist at all.
            EvedefSummary = new EvedefSummary { CurrentEvedefID = ctx.Config.CurrentEvedefID },
            Eqbits = new List<Eqbit>(),
            Equnits = new List<Equnit>(),
            PaidNormalEqunitStoreSize = ctx.Players.Current.PaidNormalEqunitStoreSize,
            PaidSpEqunitStoreSize = ctx.Players.Current.PaidSpEqunitStoreSize,
            // The Globe scene needs the current event to actually exist, not just
            // to be named: Scenes.Globe.Globe.RefreshEvedefView (RVA 0xF6BC58)
            // does `Cache.EvedefBox.Get(Param.EvedefID).EndUTC` with no null
            // guard, so an EvedefSummary.CurrentEvedefID with no matching Evedef
            // in this list NREs as soon as Camp hands off to Globe. Ship the one
            // Intermission entry to match, plus its schedule - Evedef.EndUTC
            // reads through to the EvedefSchedule with the same ID, so the two
            // lists have to agree. EvedefDesc.json confirms Intermission is a
            // real, ordinary event definition (type 1), not a sentinel.
            Evedefs = new List<Evedef>
            {
                new Evedef
                {
                    EvedefID = ctx.Config.CurrentEvedefID,
                    PokedexSummary = new PokedexSummary
                    {
                        DiscoveredVec = Array.Empty<byte>(),
                        CapturedVec = Array.Empty<byte>(),
                    },
                    ChstgStageCode = Array.Empty<StageCodeX>(),
                    // The Globe replays the whole event-change cutscene
                    // ("research finished" -> professor -> "You've arrived at
                    // Charizard Sea!") every time it is entered while
                    // Evedef.IsVisited is false, and lands the player back at
                    // Camp instead of letting any Globe button through. Mark
                    // the current event as already visited so the Globe is
                    // interactive.
                    IsVisited = Bool.True,
                    PierreCountAdded = Bool.False,
                    LastBattleUTCStr = utcNow,
                },
            },
            EventScheduleSet = Events.Schedule(ctx.Config.CurrentEvedefID),
            Evepots = new List<Evepot>(),
            GUPs = new List<GuestUserProfileSerialized>(),
            Honors = new Honors { IDs = new List<HonorID>(), Params = new List<int>() },
            // FOUND (2026-09-01, headless Ghidra decompile of
            // Camp.<iTutorial_BeforeFadeIn_AfterLogin>d__30.MoveNext, RVA
            // 0xECCD1C): once CurrentEvedefID is Tutorial the client takes the
            // tutorial branch, and if DoneFlag[2] is still clear it does
            //
            //     island = Cache.IslandBox.GetIslands(EvedefID.Tutorial)
            //                  .First(i => i.IslandID == 1);
            //     stage  = Cache.StageBox.GetStages(island.IslandCode)
            //                  .GetStages(StageType 2).First();
            //
            // with no guard for an empty sequence, so shipping no Islands/Stages
            // NREs the moment the tutorial starts. IslandDesc.json[1] is "_Tut"
            // (islandType 1) and StageDesc.json[1] is the table's only
            // m_isTutorial record - and its m_stageType is 2, exactly the type
            // the decompile filters on. So the tutorial is island 1, stage 1.
            //
            // Island 1 alone is not enough to give the Globe anything to draw.
            // IslandDesc.json[1] ("_Tut") has m_journeyDispProgressOrder -1 -
            // the tutorial island is deliberately hidden from the Globe - so
            // shipping only it renders an empty sky with the balloon floating
            // in it. The journey islands the Globe actually lays out are
            // IslandDesc.json[2..6] ("_1".."_5"), whose display orders are 1..5.
            // Ship the tutorial island (the tutorial branch still looks it up
            // by IslandID == 1) plus that run, spaced along Y so they do not
            // all stack on the origin.
            Islands = Enumerable
                .Range(1, 6)
                .Select(id => new Island
                {
                    IslandCode = Codes.Island(ctx.Config.CurrentEvedefID, islandID: id),
                    State = IslandState.Ready,
                    // The first journey island is the one to fly to; the rest
                    // are still ahead of the player.
                    AnimState = id switch
                    {
                        1 => IslandAnimState.Done,
                        2 => IslandAnimState.NextReady,
                        _ => IslandAnimState.Future,
                    },
                    CreatedUTCStr = utcNow,
                    X = 0,
                    Y = (id - 1) * 100f,
                })
                .ToList(),
            // FOUND (2026-08-31, headless Ghidra decompile of CampStampCard.
            // <iHandleLoginBonus>d__2.MoveNext, RVA 0xF25A00): that state
            // machine unconditionally dereferences Cache.LoginBonusBox.
            // LastResult ("if (*(long*)(lbb+0x20) == 0) throw" - offset 0x20
            // in LoginBonusBox is exactly m_lastResult) with no null guard.
            // LoginBonusBox is only built with a non-null m_lastResult when
            // the wire LastResult_CStop flags enum is nonzero (its only
            // defined values are GiftDiamond=4/GiftMoney=2/DiscardTicket=8 -
            // 0 reads as "no result" and the client's own ctor skips
            // allocating the LastResult box for it). Leaving CStop at its
            // Reset default of 0 is exactly what was crashing
            // CampPageMain.SetupAfterLogin right after Login. Send a token
            // GiftMoney result so a LastResult box always exists on first
            // login, matching what live retail almost certainly did (a
            // day-1 login bonus payout).
            LoginBonusInfo = new LoginBonusInfo
            {
                LastDailyProcessUTCStr = utcNow,
                TotalLoginDays = 1,
                LastResult_CStop = LoginBonusCStopResult.GiftMoney,
                LastResult_DiffMoney = 100,
            },
            // The Camp gate "I would like you to check your challenges before
            // you go on an adventure" (Camp.iMessageCheckChallenge, RVA
            // 0xECBA48) parks the player at Camp - tapping the globe replays
            // the message instead of loading the Globe - until the challenge
            // screen has actually been used, and with an empty MissionSummary
            // that screen opens as a blank card with nothing to redeem. Ship
            // the real mission set so the list has content: MissionDesc.json
            // rows 1/6/7/8/12/14/17 are the whole MissionGroup.TUTORIAL (1)
            // set and 71..75 are MissionGroup.DAILY (2). All start
            // InProgress at 0 - a fresh account has done none of them.
            MissionSummary = Missions.Summary(utcNow, ctx.Players.Current),
            MyslandSummary = new MyslandSummary
            {
                Favorite = Array.Empty<IslandCodeX>(),
                LastVisited = Array.Empty<IslandCodeX>(),
            },
            Myslands = new List<Mysland>(),
            MyUserProfile = UserProfiles.Current(ctx.Players.Current),
            // Record the starter in the dex ledger with a MaxPlainBP past the
            // island `_1` gate (TotemChallengingConditionType.BP, threshold
            // 100 per IslandDesc.m_journeyCondParam) - an empty ledger here
            // is why a maxed-out starter still couldn't clear the gate: the
            // client's journey check (Evedef.GetJourneyCondMaxBP(PiiBox))
            // reads this recorded max, not just the live PPE, and a species
            // never listed here reads as 0 no matter how strong its PPE is.
            PokedexDetails = new PokedexDetails
            {
                EvedefIDs = new List<EvedefID> { (EvedefID)0 },
                PokedexIDs = new List<PokedexID> { (PokedexID)1 },
                MaxPlainBPs = new List<int> { 999 },
                DefeatedCounts = new List<int> { 1 },
                CapturedCounts = new List<int> { 1 },
                CapturedSexVecs = new List<int> { 0 },
            },
            PokedexSummary = new PokedexSummary
            {
                DiscoveredVec = Array.Empty<byte>(),
                CapturedVec = Array.Empty<byte>(),
            },
            // Scenes.Camp.CampPiis.iMain reads Cache.PlayerPPE.PdecoID with no
            // null check, and an empty roster leaves PlayerPPE null - so this
            // NREs in Camp. FOUND (2026-08-31): the wire BasePPE.Index/PPE.Index
            // enums are literal DATA in the IL2CPP dump (out/dump/dump.cs, search
            // "private enum BasePPE.Index" and "private enum PPE.Index"), not
            // something that needs decompiling - four prior attempts missed this
            // because they were RE'ing getter *code* instead of just reading the
            // enum constants already sitting in the dump. Authoritative X[21]
            // layout: 0 Rnd, 1 PokedexID(=MonsNo), 2 IsRareColor, 3 ParaSex,
            // 4 Level, 5 ApOffset, 6 PdecoID, 7 PiiGrade, 8 SocketCount,
            // 9 SpSocketCount, 10 Waza0, 11 Waza1, 12 PPEId_Low, 13 PPEId_High
            // (PPEId packed as two ints, low then high), 14 PartyMember,
            // 15 EvedefID, 16 IsFavorite, 17 AddLevelCount,
            // 18 AddNormalSocketCount, 19 GotUTC_Low, 20 GotUTC_High. The old
            // "int[12]" claim was wrong (12 is just BasePPE's own sub-length;
            // PPE.Index continues 12-20 for a total of 21 = PPE.E).
            // PartyMember.IsPlayer=1 is almost certainly the flag
            // Cache.PlayerPPE filters the roster on (PartyMember.NONE=0 would
            // leave it unset) - set on this starter PPE. `N` maps to the
            // client's [Required] Nickname property, so it must be non-null -
            // a null/absent Nickname is a plausible cause of the previous
            // "hangs after Login with no exception" regressions (silent
            // required-field validation failure during deserialization).
            // Starter plus every PPE a stage clear has granted since - Login
            // is the only place a restarted client learns its roster again,
            // since it keeps none of its own (PlayerStore.Player.OwnedPPEs).
            PPEs = new List<PPE>
            {
                new PPE
                {
                    N = "Bulbasaur",
                    X = new[]
                    {
                        /* 0  Rnd                */ 12345,
                        /* 1  PokedexID (MonsNo)  */ 1,     // HUSIGIDANE / Bulbasaur
                        /* 2  IsRareColor         */ 0,
                        /* 3  ParaSex             */ 0,     // MALE
                        // Level 5 at the lowest grade and with no moves cannot
                        // kill a single Rattata before the stage timer runs
                        // out - the tutorial run always ended in "Try again?".
                        // Bumped to the practical caps (Level 100, PiiGrade
                        // 100/EnumPiiGrade.EndID-1) so GetJourneyCondMaxBP's
                        // client-side BP formula clears the BP>=100 gate on
                        // island `_1` - the exact CalcPlainBP/CalcBP formula
                        // lives in native code (PPEBattleUtil, RVA range
                        // 0xBB5xxx-0xBB6xxx) and hasn't been decompiled, so
                        // this maximizes every input CalcBP is known to read
                        // (Level, PiiGrade, moves) rather than proving a
                        // specific number clears the threshold.
                        /* 4  Level               */ 100,
                        /* 5  ApOffset            */ 100,
                        /* 6  PdecoID             */ ctx.Players.Current.PdecoMounts.GetValueOrDefault(1),
                        /* 7  PiiGrade            */ 100,
                        /* 8  SocketCount         */ 0,
                        /* 9  SpSocketCount       */ 0,
                        // A PPE with no moves auto-attacks for nothing, so a
                        // level-50 Bulbasaur still lost every campaign stage.
                        // PiiDesc row 1 (HUSIGIDANE) names the species' own
                        // moves: m_zakoWaza/m_rivalWaza/m_bossWaza = 27 and
                        // m_bossWaza2 = 90.
                        /* 10 Waza0               */ 27,
                        /* 11 Waza1               */ 90,
                        /* 12 PPEId_Low           */ 1,
                        /* 13 PPEId_High          */ 0,
                        /* 14 PartyMember         */ 1,     // IsPlayer
                        /* 15 EvedefID            */ 0,     // NONE
                        /* 16 IsFavorite          */ 0,
                        /* 17 AddLevelCount       */ 0,
                        /* 18 AddNormalSocketCount*/ 0,
                        /* 19 GotUTC_Low          */ 0,
                        /* 20 GotUTC_High         */ 0,
                    },
                },
            }
            .Concat(ctx.Players.Current.OwnedPPEs.Select(p =>
                PPEFactory.BuildPPE(p.Id, p.MonsNo, p.Level, p.Grade, p.Waza0, p.Waza1, p.Nickname ?? "",
                    ctx.Players.Current.PdecoMounts.GetValueOrDefault(p.Id))))
            .ToList(),
            PaidPPEStoreSize = ctx.Players.Current.PaidPPEStoreSize,
            // FOUND (2026-09-01, headless Ghidra decompile of
            // GlobeIslandView.RefreshAll, RVA 0xED44C0): it does
            //
            //     var sii = Cache.StageBox.GetStages(m_island.IslandCode);
            //     var stages = sii.GetStages(IsMysland(code) ? Mysland : Journey);
            //
            // and null-checks the StageBox but NOT the StagesInIsland that
            // GetStages returns. StageBox groups the wire Stages by the island
            // packed into each StageCode, so an island with no stages at all
            // yields null there and RefreshAll NREs - which is what was hanging
            // the Globe on its loading spinner, once per island, from
            // CategorizedScrollList.Reload -> GlobeIslandItem.UpdateData.
            //
            // So every island shipped in Islands above needs at least one
            // stage. StageDesc.json is indexed by StageID globally (not per
            // island) and rows 1..6 are exactly a journey run: m_stageType 2
            // with m_targetCP climbing 100/100/130/180/230/300, row 1 being the
            // m_isTutorial one. Line stage N up with island N.
            Stages = Enumerable
                .Range(1, 6)
                .Select(id => new Stage
                {
                    StageCode = Codes.Stage(
                        ctx.Config.CurrentEvedefID, islandID: id, stageID: id),
                    State = StageState.Ready,
                    PokedexSummary = new PokedexSummary
                    {
                        DiscoveredVec = Array.Empty<byte>(),
                        CapturedVec = Array.Empty<byte>(),
                    },
                    CollectionRewarded = Bool.False,
                    ClearCount = 0,
                    // IslandDesc.m_jissionID is a fixed 3-slot array and the
                    // client zips it with this list by index
                    // (JissionValue.IndexInIslandDesc), so a short list makes
                    // GlobeIslandView.RefreshAll throw ArgumentOutOfRange out of
                    // List<JissionState>.get_Item. Always ship all three.
                    JissionStates = Enumerable
                        .Repeat(JissionState.NotAchieved, 3)
                        .ToList(),
                    // GlobeIslandView.RefreshAll (RVA 0xED44C0) reads
                    // stage.Boss for the island's pii icon, and Stage.get_Boss
                    // (RVA 0xFA8390) is just m_bosses[0] with no emptiness
                    // guard - so an empty Bosses list throws
                    // ArgumentOutOfRangeException out of List<T>.get_Item,
                    // inlined into RefreshAll's own frame. Ship the island's
                    // own boss: PokedexID is the PiiDesc row index, which for
                    // form 0 equals m_Monsno, and IslandDesc.json rows 1..6
                    // give m_islandMonsNo 20/20/2/8/5/26.
                    Bosses = new List<PokedexID> { (PokedexID)World.IslandBossMonsNo[id] },
                    Capturables = new List<PokedexID>(),
                    Prizes = new List<PokedexID>(),
                })
                .ToList(),
            TotalSec = 0,
            Chests = new List<Chest>(),
            // PdecoDesc.json (real retail table, docs/tables/) carries no
            // price/unlock-condition fields for any decoration, so every
            // PdecoID the enum defines (bar NONE) is reported as owned -
            // PdecoMount then lets Camp mount any of them per-PPE, persisted
            // in Player.PdecoMounts.
            Pdecos = Enum.GetValues<PdecoID>()
                .Where(id => id != PdecoID.NONE)
                .Select(id => new Pdeco { PdecoID = id, Count = 1 })
                .ToList(),
            Utensils = ctx.Players.Current.Utensils
                .Select(kv => new Utensil { UtensilID = (UtensilID)kv.Key, Count = kv.Value })
                .ToList(),
            Welcals = new List<Welcal>(),
            Subscription = new Subscription
            {
                UnlockSKUIDs = new List<SKUID>(),
                UnlockExpireUTCStr = new List<string>(),
                WaitingSKUIDs = new List<SKUID>(),
            },
            PurchaseProcessing = Bool.False,
            SeldomInfo = SeldomInfos.Current(),
        };

        // The one mysland, shipped with the rest of the world rather than only
        // in the FindMysland response - see World for why the AutoRes route is
        // not trustworthy for it. Its Stage rides along so it is enterable, but
        // deliberately NOT an Island: the Globe's island scroll list is the
        // journey run, and feeding a mysland code into it throws a
        // NullReferenceException out of GlobeIslandView.RefreshAll. A mysland
        // describes itself (name, boss, position) through the Mysland record.
        reset.Myslands.Add(World.Mysland(ctx.Config.CurrentEvedefID));
        reset.Stages = reset.Stages
            .Append(World.Stage(ctx.Config.CurrentEvedefID))
            .ToList();


        return new Pokeland.Protocol.Login.Res
        {
            SessionID = session.SessionId,
            UTCStr = utcNow,

            // The client builds its whole local Cache from this on a fresh
            // install - see the comment above. One element is a full snapshot,
            // not "which subsystems to wipe". GameDispatcher.StampEnvelope fills
            // in Rev from session.Rev (starts at 1) - see GameSession.Rev for why
            // that has to be nonzero or the client drops this whole payload.
            Reset = new[] { reset },
        };
    }
}
