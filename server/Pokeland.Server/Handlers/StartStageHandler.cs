#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Hands the client the contents of a stage it is about to enter.
///
/// The client generates none of this locally: the whole enemy roster for a run
/// comes down in <c>MyHabitatMap</c>, which is why leaving StartStage on the
/// dispatcher's empty-envelope path leaves the tutorial hanging on the title
/// screen with no error - it is waiting on a habitat map it will never get.
/// (This is the first endpoint past Login that has to produce real *content*
/// rather than just an acknowledgement.)
///
/// The <c>*Raw</c> arrays on EnemyDesc are parallel per-spawn-slot arrays -
/// index N of MonsNoRaw/FormNoRaw/PopNumRaw/HpScaleRaw/... all describe the
/// same spawn - so they must be the same length or the client indexes off the
/// end. This deliberately ships one modest spawn group per advent type: enough
/// for the tutorial stage to be playable and for EndStage to have something to
/// report, without pretending to reproduce the retail spawn tables (those live
/// in the unextracted stage data and are a separate job).
/// </summary>
public sealed class StartStageHandler : IEndpointHandler
{
    private static readonly EnemyType[] EnemyTypes =
    {
        EnemyType.NORMAL, EnemyType.BOSS,
    };

    private static readonly AdventType[] AdventTypes =
    {
        AdventType.FIXED, AdventType.POP, AdventType.POP_GROUND,
        AdventType.POP_SKY, AdventType.POP_WARP, AdventType.YARARE,
        AdventType.FALL, AdventType.SURROUND, AdventType.POP_FOREST,
        AdventType.POP_CAVE, AdventType.POP_GRASSLAND, AdventType.POP_BEACH,
    };

    public string Endpoint => "StartStage";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.StartStage.Req)request;

        ctx.Log.LogInformation(
            "StartStage: mode={Mode} stageCode=[{Code}]",
            req.Mode,
            req.StageCode == null ? "" : string.Join(",", req.StageCode));

        return new Pokeland.Protocol.StartStage.Res
        {
            Result = StartStageResult.Success,
            MHM = new MyHabitatMap
            {
                StageCode = req.StageCode,
                IslandRankID = IslandRankID._1,
                // One entry per (enemy type, advent type) pair. A Locator
                // filters the roster down to its own advent type and then
                // picks one at random; when nothing matches it indexes an
                // empty array and Locator.iEnemyProc throws
                // IndexOutOfRangeException every frame. BOSS is here as well
                // as NORMAL because the arena at the end of a route is a boss
                // locator and goes through iBossProc.
                EnemyDescs = EnemyTypes
                    .SelectMany(type => AdventTypes.Select(advent => new EnemyDesc
                    {
                        EnemyType = type,
                        // Rattata - a plain early-route Pokemon, no form variants.
                        // Four identical slots rather than one: iBossProc
                        // walks these arrays past index 0, so a single-slot
                        // roster runs off the end of MonsNoAccessor.
                        MonsNoRaw = new short[] { 19, 19, 19, 19 },
                        FormNoRaw = new short[] { 0, 0, 0, 0 },
                        PopNumRaw = new byte[] { 3, 3, 3, 3 },
                        HpScaleRaw = new float[] { 1f, 1f, 1f, 1f },
                        ApScaleRaw = new float[] { 1f, 1f, 1f, 1f },
                        DpScaleRaw = new float[] { 1f, 1f, 1f, 1f },
                        WazaIDRaw = new short[] { 0, 0, 0, 0 },
                        WazaID2 = 0,
                        Prize = false,
                        Ratio = 1f,
                        AdventType = advent,
                    }))
                    .ToList(),
                // Boss behaviour profiles, paired to EnemyDescs by index, so
                // this list has to be the same length as that one.
                EnemyBossDescs = Enumerable
                    .Range(0, EnemyTypes.Length * AdventTypes.Length)
                    .Select(_ => new EnemyBossDesc
                    {
                        TapAttackDamageRatio = 1f,
                        OffenseBreakTapAttackNum = 3,
                        WazaPattern = 0,
                        OffenseWaza1BeforeTime = 1f,
                        OffenseWaza2BeforeTime = 1f,
                        DefenseTime = 3f,
                        DefenseBreakHpRatio = 0.5f,
                        DefenseBreakRecoverSpeedCoef = 1f,
                        DefenseAttackDamageScale = 1f,
                        SummonGuardNum = 0,
                        AfterSummonTime = 1f,
                        TossinDamageScale = 1f,
                        TossinAimPlayerAfterCamera = false,
                        HukitobasiDamageScale = 1f,
                        LeaveTime = 10f,
                        ForwardSpeedCoef = 1f,
                        ActionPattern = 0,
                        EscortFormationType = 0,
                        EscortFormationAngle = 0f,
                    })
                    .ToList(),
                MaxDropMoney = 100,
                MaxDropPierreCount = 0,
                DropChestTypeID = 0,
                IsSubscriptionDropActive = Bool.False,
                IsSubscriptionUnlockActive = Bool.False,
                IsFeverStage = Bool.False,
            },
            PPEDrops = new List<PPEDrop>(),
            EqunitDrops = new List<EqunitDrop>(),
            MLABaaSUserId = "",
        };
    }
}
