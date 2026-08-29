#nullable disable
using Pokeland.Protocol;

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

        var utcNow = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");

        // AutoRes deltas only ever *update* an existing Uskumru.Cache box - the
        // client's own decompiled Cache.ProcessRes<object> reads the current
        // Cache.MyUserProfileBox (etc.) and throws if it is null; it never
        // constructs one. Uskumru.Cache..ctor(Reset, myBaaSUserId) is the only
        // code that builds a box, from a Reset object, and its one caller is a
        // generic response-handling coroutine (Uskumru.ClientTask.<iWait>d__12).
        // Sending a fully populated Reset here is what retail's shape implies
        // and is a real improvement regardless - but on-device testing (Aug 29
        // 2026) still shows Cache.MyUserProfileBox null and the Camp scene
        // NRE'ing on first login, so whatever actually decides "call the
        // Reset-taking ctor vs. just read the existing Cache" has NOT been
        // confirmed to be "this response has a Reset[]". Needs live
        // instrumentation (Frida) against the running client to pin down,
        // not more static reading of the generic-shared IL2CPP bytes.
        var reset = new Reset
        {
            SupportNumber = 0,
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
            LoginBonusInfo = new LoginBonusInfo
            {
                LastDailyProcessUTCStr = utcNow,
                TotalLoginDays = 1,
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
            PPEs = new List<PPE>(),
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
            SeldomInfo = new SeldomInfoUser(),
        };

        return new Pokeland.Protocol.Login.Res
        {
            SessionID = session.SessionId,

            // The client builds its whole local Cache from this on a fresh
            // install - see the comment above. One element is a full snapshot,
            // not "which subsystems to wipe".
            Reset = new[] { reset },
        };
    }
}
