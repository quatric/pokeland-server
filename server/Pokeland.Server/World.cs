#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// The one mysland this server has to offer, and the Island/Stage records that
/// make it enterable.
///
/// Retail generated myslands per player and served whichever ones sat near the
/// point the searcher tapped. There are none here, so the finder always turns up
/// this same one - which means it is not really "found" at all, it is a fixed
/// part of the world. Login ships it in <c>Reset</c> alongside the journey
/// islands, and FindMysland just hands back its code.
///
/// Shipping it through Login rather than only in the FindMysland response also
/// keeps it out of the incremental <c>AutoRes</c> path: <c>MyslandBox.Update</c>
/// (RVA 0x115F3DC) does not merge a list into the cache, it rebuilds the whole
/// per-event dictionary by walking the cached EventScheduleBox, and anything the
/// walk does not reach is dropped without a word. <c>Cache..ctor(Reset)</c> has
/// no such filter.
/// </summary>
public static class World
{
    /// <summary>Nonzero myslandId is the entire definition of "this is a mysland":
    /// <c>EnumEmisCode.IsMysland</c> (RVA 0x129751C) is just
    /// <c>(emisCode &amp; 0x3FFFFFC0000000) != 0</c>.</summary>
    public const int MyslandId = 1;

    /// <summary>Visual/terrain identity, an IslandDesc.json row. It has to be a
    /// row with m_islandType 2 - an actual mysland - and not a journey island:
    /// GlobeMyslandFinder.iFind reads the row's m_star to pick which of its
    /// AnimHash_Star states to play, then waits for the animator to reach
    /// AnimHash_Done. Journey rows carry m_star 0, there is no star-0 state, so
    /// the found animation never starts and the finder hangs forever with a
    /// blank result card. Row 46 ("Mysland_1_1") is the first ordinary mysland:
    /// m_star 2, m_myslandChartType 10, m_groundType 3 - the same ground the
    /// stage below is built on.</summary>
    public const int IslandID = 46;

    /// <summary>Mysland rows carry no m_islandMonsNo of their own - a mysland
    /// names its boss in the Mysland record instead.</summary>
    private const int BossMonsNo = 20;

    /// <summary>IslandDesc.json rows 1..6 ("_Tut", "_1".."_5") m_islandMonsNo,
    /// index 0 unused so journey island IDs (1-based) index straight in.
    /// Shared with StartStageHandler so a stage's actual spawn roster matches
    /// the boss the Globe already advertises for that island, instead of every
    /// stage playing the same fixed tutorial encounter.</summary>
    public static readonly int[] IslandBossMonsNo = { 0, 20, 20, 2, 8, 5, 26 };

    /// <summary>Where it sits on the globe. Fixed, because Login has to be able
    /// to describe it without knowing where anyone tapped.</summary>
    private const float X = 0.0f;
    private const float Y = 0.25f;

    public static IslandCodeX[] IslandCode(EvedefID evedefID) =>
        Codes.Island(evedefID, IslandID, MyslandId);

    /// <summary>The mysland's one stage. A Stage's StageType is not carried on
    /// the wire - the client reads it out of the StageDesc row named by the
    /// StageID. GlobeIslandView.RefreshAll (RVA 0xED44C0) does
    /// <c>StageBox.GetStages(islandCode).GetStages(StageType.Campaign)
    /// .FirstOrDefault()</c> for any island where IslandCodeEx.IsMysland is true
    /// (journey islands take StageType.Totem instead) and then dereferences the
    /// result, so a mysland whose only stage is not a Campaign stage black-screens
    /// the Globe scene on entry.
    ///
    /// StageDesc row 1 - the obvious tutorial row - is Totem. Row 9 is the first
    /// Campaign row whose m_groundType is 3, matching IslandDesc row 46.</summary>
    private const int StageID = 9;

    public static StageCodeX[] StageCode(EvedefID evedefID) =>
        Codes.Stage(evedefID, IslandID, stageID: StageID, myslandId: MyslandId);

    public static Mysland Mysland(EvedefID evedefID) => new()
    {
        IslandCode = IslandCode(evedefID),
        Name = "Sunny Isle",
        DiscovererBaaSUserId = "",
        X = X,
        Y = Y,
        Capturables = new List<PokedexID>(),
        Boss = (PokedexID)BossMonsNo,
        Nickname = "Trainer",
        MiiCoreData = Array.Empty<byte>(),
        LatLng = new float[] { 0, 0 },
    };

    /// <summary>The mysland needs an Island record of its own, keyed by the same
    /// island code as the Mysland. Globe.iMyslandFinderFound (RVA 0xF7BD50) does
    /// <c>Cache.IslandBox.Get(mysland.IslandCode)</c> and dereferences the result
    /// with no null guard; the NullReferenceException is swallowed by the
    /// coroutine's catch handler, so a missing record shows up as the finder
    /// simply doing nothing after the found animation - no StartStage, no error.
    /// </summary>
    public static Island Island(EvedefID evedefID, string createdUTC) => new()
    {
        IslandCode = IslandCode(evedefID),
        State = IslandState.Ready,
        AnimState = IslandAnimState.Done,
        CreatedUTCStr = createdUTC,
        X = X,
        Y = Y,
    };

    public static Stage Stage(EvedefID evedefID) => new()
    {
        StageCode = StageCode(evedefID),
        State = StageState.Ready,
        PokedexSummary = new PokedexSummary
        {
            DiscoveredVec = Array.Empty<byte>(),
            CapturedVec = Array.Empty<byte>(),
        },
        CollectionRewarded = Bool.False,
        ClearCount = 0,
        // IslandDesc.m_jissionID is a fixed 3-slot array the client zips with
        // this list by index, so it always has three entries.
        JissionStates = Enumerable.Repeat(JissionState.NotAchieved, 3).ToList(),
        // Stage.get_Boss is m_bosses[0] with no emptiness guard.
        Bosses = new List<PokedexID> { (PokedexID)BossMonsNo },
        Capturables = new List<PokedexID>(),
        Prizes = new List<PokedexID>(),
    };
}
