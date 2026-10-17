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

        // Offer one drop of the island's own species on every run, scaled
        // with the same tier as the encounter, so a clear actually grows the
        // roster instead of only paying money - previously PPEDrops/
        // EqunitDrops were always empty and nothing was ever won. EndStage
        // reads these session fields back to grant the drop for real on a
        // clear; a fresh id per StartStage means an abandoned run's offer is
        // just discarded rather than granted twice.
        int dropLevel = 5 * tier;
        int dropGrade = 10 * tier;
        session.CurrentIslandID = islandID;
        session.CurrentStageCode = req.StageCode;

        short minionMonsNo = (short)(bossMonsNo == 19 ? 16 : 19);
        var bossMoves = PPEFactory.GetDefaultMoves(bossMonsNo);
        var minionMoves = PPEFactory.GetDefaultMoves(minionMonsNo);

        // The set02 ZakuZaku purchase's temporary Drop-subscription buff
        // (see PlayerStore.ActivatePurchase/DropBonusExpiresUtc) triples the
        // offered drop count for its duration - "2 more PPE slots on stage
        // clear" per its SKUDesc.json blurb, on top of the usual one.
        int dropCount = ctx.Players.Current.DropBonusExpiresUtc is DateTime bonusUntil
            && PokelandClock.UtcNow < bonusUntil ? 3 : 1;
        session.OfferedDrops = Enumerable.Range(0, dropCount)
            .Select(_ => (DropId: DateTime.UtcNow.Ticks + Random.Shared.Next(1000), MonsNo: (int)bossMonsNo, Level: dropLevel, Grade: dropGrade, Waza0: bossMoves.Waza0, Waza1: bossMoves.Waza1))
            .ToList();

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
                    .SelectMany(type => AdventTypes.Select(advent =>
                    {
                        short[] monsRaw;
                        byte[] popRaw;
                        short[] wazaRaw;
                        short waza2;

                        if (type == EnemyType.BOSS)
                        {
                            // Slot 0: Minion escort (4 spawns)
                            // Slot 1: Boss (STRICTLY 1 spawn so no duplicate boss model)
                            // Slot 2: Minion escort
                            // Slot 3: Minion escort
                            monsRaw = new[] { minionMonsNo, bossMonsNo, minionMonsNo, minionMonsNo };
                            popRaw = new byte[] { 4, 1, 4, 4 };
                            wazaRaw = new short[] { (short)minionMoves.Waza0, (short)bossMoves.Waza0, (short)minionMoves.Waza0, (short)minionMoves.Waza0 };
                            waza2 = (short)bossMoves.Waza1;
                        }
                        else
                        {
                            monsRaw = new[] { minionMonsNo, minionMonsNo, minionMonsNo, minionMonsNo };
                            popRaw = new byte[] { 1, 1, 1, 1 };
                            wazaRaw = new short[] { (short)minionMoves.Waza0, (short)minionMoves.Waza0, (short)minionMoves.Waza0, (short)minionMoves.Waza0 };
                            waza2 = 0;
                        }

                        return new EnemyDesc
                        {
                            EnemyType = type,
                            MonsNoRaw = monsRaw,
                            FormNoRaw = new short[] { 0, 0, 0, 0 },
                            PopNumRaw = popRaw,
                            HpScaleRaw = new[] { hpScale, hpScale, hpScale, hpScale },
                            ApScaleRaw = new[] { apScale, apScale, apScale, apScale },
                            DpScaleRaw = new float[] { 1f, 1f, 1f, 1f },
                            WazaIDRaw = wazaRaw,
                            WazaID2 = (WazaID)waza2,
                            Prize = false,
                            Ratio = 1f,
                            AdventType = advent,
                        };
                    }))
                    .ToList(),
                // Boss behaviour profiles, paired to EnemyDescs by index, so
                // this list has to be the same length as that one.
                // ActionPattern 3210 activates:
                // Normal (0) -> Guard shield (1) -> Minions (2) -> Charge (3).
                EnemyBossDescs = EnemyTypes
                    .SelectMany(type => AdventTypes.Select(advent => new EnemyBossDesc
                    {
                        TapAttackDamageRatio = 1f,
                        OffenseBreakTapAttackNum = (byte)(type == EnemyType.BOSS ? 6 : 1),
                        WazaPattern = 0,
                        OffenseWaza1BeforeTime = 1f,
                        OffenseWaza2BeforeTime = 1f,
                        DefenseTime = type == EnemyType.BOSS ? 6.0f : 0f,
                        DefenseBreakHpRatio = type == EnemyType.BOSS ? 0.15f : 1f,
                        DefenseBreakRecoverSpeedCoef = 1f,
                        DefenseAttackDamageScale = 1f,
                        SummonGuardNum = (byte)(type == EnemyType.BOSS ? 3 : 0),
                        AfterSummonTime = 1f,
                        TossinDamageScale = type == EnemyType.BOSS ? 1.5f : 1f,
                        TossinAimPlayerAfterCamera = type == EnemyType.BOSS,
                        HukitobasiDamageScale = 1f,
                        LeaveTime = 10f,
                        ForwardSpeedCoef = 1f,
                        ActionPattern = type == EnemyType.BOSS ? 3210UL : 0UL,
                        EscortFormationType = 0,
                        EscortFormationAngle = 0f,
                    }))
                    .ToList(),
                MaxDropMoney = 100,
                MaxDropPierreCount = 0,
                // TutorialCopper1 is inserted into the client's temporary
                // mineral slot by EndStage. It must remain Temporary in that
                // response; sending it as Locked makes ChestBox reject the new
                // item and leaves BattleResult with a Mineral model but no
                // corresponding cached chest.
                DropChestTypeID = ChestTypeID.TutorialCopper1,
                IsSubscriptionDropActive = Bool.False,
                IsSubscriptionUnlockActive = Bool.False,
                IsFeverStage = Bool.False,
            },
            PPEDrops = session.OfferedDrops
                .Select(d => PPEFactory.BuildDrop(d.DropId, d.MonsNo, d.Level, d.Grade, d.Waza0, d.Waza1))
                .ToList(),
            EqunitDrops = new List<EqunitDrop>(),
            MLABaaSUserId = "",
        };
    }
}
