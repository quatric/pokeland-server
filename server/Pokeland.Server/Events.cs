#nullable disable
using System.Collections.Generic;
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
    /// <summary>The single always-open event window this server runs.</summary>
    public static EventScheduleSet Schedule(EvedefID evedefID)
    {
        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
        var farFuture = PokelandClock.UtcNow.AddYears(10).ToString("yyyy-MM-ddTHH:mm:ssZ");

        return new EventScheduleSet
        {
            Evedefs = new List<EvedefSchedule>
            {
                new EvedefSchedule
                {
                    EvedefID = evedefID,
                    // An intermission has no advertised end; park every date far
                    // enough out that nothing expires mid-session.
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
                },
            },
        };
    }
}
