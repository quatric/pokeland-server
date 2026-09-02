#nullable disable
using System;
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
/// end. This ships one modest spawn group per advent type, keyed off the
/// island the request's StageCode actually names (via World.IslandBossMonsNo,
/// the same table Login already advertises on the Globe) rather than the
/// single hardcoded Rattata encounter every stage used to get - so the six
/// journey stages are six different, progressively tougher fights instead of
/// one stage replayed under six different island skins. Still not the retail
/// spawn tables (those live in the unextracted stage data and are a separate
/// job), just enough variety and difficulty curve for the journey run to be a
/// journey rather than a loop.
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

        // req.StageCode is packed by Codes.Stage as [evedefID, myslandId,
        // islandID, stageID], so index 2 says which of the six journey
        // islands (or the World mysland, islandID 46) this run is against.
        // Before this, every stage played the identical fixed Rattata
        // encounter regardless of StageCode - there was exactly one stage's
        // worth of real content behind six different Globe entries. Look the
        // boss up the same way Login already advertises it on the Globe
        // (World.IslandBossMonsNo) so a run actually fights the island's own
        // Pokemon, and scale it up per island so later islands are a real
        // (if coarse) step up rather than window dressing.
        int islandID = req.StageCode != null && req.StageCode.Length > 2
            ? (int)req.StageCode[2]
            : 0;
        short bossMonsNo = (short)(islandID >= 1 && islandID < World.IslandBossMonsNo.Length
            ? World.IslandBossMonsNo[islandID]
            : 19); // Rattata fallback for the tutorial/mysland stage.
        int tier = Math.Clamp(islandID, 1, 6);
        float hpScale = 0.01f * tier;
        float apScale = 0.2f + 0.05f * tier;

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
                        // One spawn per slot, at reduced attack and health:
                        // every advent type is populated, so a retail-sized
                        // PopNum stacks a dozen groups onto one short route
                        // and the tutorial Pokemon is overrun before it
                        // reaches the arena.
                        // Four identical slots rather than one: iBossProc
                        // walks these arrays past index 0, so a single-slot
                        // roster runs off the end of MonsNoAccessor.
                        MonsNoRaw = new[] { bossMonsNo, bossMonsNo, bossMonsNo, bossMonsNo },
                        FormNoRaw = new short[] { 0, 0, 0, 0 },
                        PopNumRaw = new byte[] { 1, 1, 1, 1 },
                        // Enemy HP is scaled right down. This is not
                        // cosmetic tuning: at the retail-ish 0.5 the starter
                        // could not kill a single Rattata, so every locator's
                        // spawn group stayed alive, the route stayed blocked
                        // (the Pokemon only walks toward *nearby* opponents,
                        // so a live crowd pins it in place) and the run always
                        // timed out. With this the roster actually thins and
                        // the route can be walked to the arena. Real HP curves
                        // live in the unextracted stage data - a separate job.
                        // hpScale/apScale climb with the island tier (1..6) so
                        // the six journey stages are not identical encounters.
                        HpScaleRaw = new[] { hpScale, hpScale, hpScale, hpScale },
                        ApScaleRaw = new[] { apScale, apScale, apScale, apScale },
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
                        OffenseBreakTapAttackNum = 1,
                        WazaPattern = 0,
                        OffenseWaza1BeforeTime = 1f,
                        OffenseWaza2BeforeTime = 1f,
                        DefenseTime = 0f,
                        DefenseBreakHpRatio = 1f,
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
