#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Journey-completion leaderboard for an island. No other players exist.</summary>
public sealed class GetJourneyWinnersHandler : IEndpointHandler
{
    public string Endpoint => "GetJourneyWinners";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetJourneyWinners.Res { JWs = new List<JourneyWinner>() };
}
