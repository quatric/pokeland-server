#nullable disable
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
            Money = 1000,
            DiamondFree = 0,
            DiamondPaid = 0,
            DiamondConsumed = 0,
            DiamondConsumedExtra = 0,
            IsGiftArrived = Bool.False,
            Exp = 0,
            GuestTotalVisitCount = 0,
            AnnouncementState = new AnnouncementState
            {
                EmergencyInfoAnnouncementIds = Array.Empty<int>(),
                PopupAnnouncementIds = Array.Empty<int>(),
            },
            ChestSummary = new ChestSummary { StoreSize = 3 },
            DoneFlagSummary = new DoneFlagSummary { DoneFlagVec = Array.Empty<byte>() },
            EvedefSummary = new EvedefSummary(),
            Eqbits = new List<Eqbit>(),
            Equnits = new List<Equnit>(),
            PaidNormalEqunitStoreSize = 0,
            PaidSpEqunitStoreSize = 0,
            Evedefs = new List<Evedef>(),
            EventScheduleSet = new EventScheduleSet { Evedefs = new List<EvedefSchedule>() },
            Evepots = new List<Evepot>(),
            GUPs = new List<GuestUserProfileSerialized>(),
            Honors = new Honors { IDs = new List<HonorID>(), Params = new List<int>() },
            Islands = new List<Island>(),
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
            MissionSummary = new MissionSummary
            {
                DailyUTCStr = utcNow,
                IDs = new List<MissionID>(),
                Progresses = new List<int>(),
                States = new List<MissionState>(),
            },
            MyslandSummary = new MyslandSummary
            {
                Favorite = Array.Empty<IslandCodeX>(),
                LastVisited = Array.Empty<IslandCodeX>(),
            },
            Myslands = new List<Mysland>(),
            MyUserProfile = new MyUserProfile
            {
                Nickname = "Trainer",
                MiiCoreData = Array.Empty<byte>(),
                LatLng = new float[] { 0, 0 },
            },
            PokedexDetails = new PokedexDetails
            {
                EvedefIDs = new List<EvedefID>(),
                PokedexIDs = new List<PokedexID>(),
                MaxPlainBPs = new List<int>(),
                DefeatedCounts = new List<int>(),
                CapturedCounts = new List<int>(),
                CapturedSexVecs = new List<int>(),
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
                        /* 4  Level               */ 5,
                        /* 5  ApOffset            */ 0,
                        /* 6  PdecoID             */ 0,     // NONE
                        /* 7  PiiGrade            */ 1,
                        /* 8  SocketCount         */ 0,
                        /* 9  SpSocketCount       */ 0,
                        /* 10 Waza0               */ 0,     // NONE
                        /* 11 Waza1               */ 0,     // NONE
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
            },
            PaidPPEStoreSize = 0,
            Stages = new List<Stage>(),
            TotalSec = 0,
            Chests = new List<Chest>(),
            Pdecos = new List<Pdeco>(),
            Utensils = new List<Utensil>(),
            Welcals = new List<Welcal>(),
            Subscription = new Subscription
            {
                UnlockSKUIDs = new List<SKUID>(),
                UnlockExpireUTCStr = new List<string>(),
                WaitingSKUIDs = new List<SKUID>(),
            },
            PurchaseProcessing = Bool.False,
            // SeldomInfoBox.IsEndOfService/IsEndOfShop test whether the client's
            // country/market code appears in these filter strings - leaving them
            // null (the default) made every CCmCode match, which is what kept
            // showing the "End of Service Info" dialog on every screen
            // regardless of a live-code Frida hook forcing the check methods to
            // return false (a second, unhooked inlined call site was reading
            // this data directly). Empty strings mean "nothing is filtered".
            SeldomInfo = new SeldomInfoUser
            {
                EulaRev = 1,
                EndOfServiceCCmFilter = "",
                EndOfShopCCmFilter = "",
            },
        };

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
