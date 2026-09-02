#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Event ranking board. No other players to rank against.</summary>
public sealed class GetRankingHandler : IEndpointHandler
{
    public string Endpoint => "GetRanking";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetRanking.Res
        {
            RankingParticipants = new List<RankingParticipant>(),
            TimestampUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
}
