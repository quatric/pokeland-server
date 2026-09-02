#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// The event schedule the whole world hangs off.
///
/// Every island, stage and mysland belongs to an EvedefID, and the client keeps
/// them in per-event buckets that it filters by that event's schedule window.
/// So the schedule is not just Login boilerplate: any later response that adds
/// world content has to carry it too, or the client files the new content under
/// an event it has no live window for and drops it on the floor.
/// </summary>
public static class Events
{
    /// <summary>
    /// The Totem Battle event this server always advertises as running,
    /// alongside the main world's ctx.Config.CurrentEvedefID - it's a
    /// separate EvedefType (3, "team battle") from the Globe/journey content,
    /// so it ships as an additional Evedef/EvedefSchedule entry rather than
    /// replacing CurrentEvedefID (see docs/tables/EvedefDesc.json row 25:
    /// TeamBattle_001, m_evepotIDs=[1,0] - matches EvedefID.TeamBattle_001=25
    /// and EvepotID.TeamBattle_001=1 in GameTypes.g.cs).
    /// </summary>
    public const EvedefID TmbtlEvedefID = EvedefID.TeamBattle_001;
    public const EvepotID TmbtlEvepotID = EvepotID.TeamBattle_001;

    /// <summary>One always-open event window per given EvedefID.</summary>
    public static EventScheduleSet Schedule(params EvedefID[] evedefIDs)
    {
        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
        var farFuture = PokelandClock.UtcNow.AddYears(10).ToString("yyyy-MM-ddTHH:mm:ssZ");

        return new EventScheduleSet
        {
            Evedefs = evedefIDs.Select(evedefID => new EvedefSchedule
            {
                EvedefID = evedefID,
                // No advertised end; park every date far enough out that
                // nothing expires mid-session.
                EndUTCStr = farFuture,
                RedeemEndUTCStr = farFuture,
                PickUpBeginUTCStr = utcNow,
                PickUpEndUTCStr = farFuture,
                RankingFixedEndUTCStr = farFuture,
                PokedexDist = new EvedefPokedexDistribution
                {
                    AttrPWTs = new List<PokeWazaType>(),
                    Counts = new List<int>(),
                    PokedexIDs = new List<PokedexID>(),
                    MinIslandRankIDs = new List<IslandRankID>(),
                },
            }).ToList(),
        };
    }
}
