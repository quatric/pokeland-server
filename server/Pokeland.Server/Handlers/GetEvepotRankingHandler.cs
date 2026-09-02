#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Event-pot ranking board. No events run here yet.</summary>
public sealed class GetEvepotRankingHandler : IEndpointHandler
{
    public string Endpoint => "GetEvepotRanking";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetEvepotRanking.Res
        {
            EvepotRankingParticipants = new List<EvepotRankingParticipant>(),
            TimestampUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
}
