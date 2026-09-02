#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Lists totem-battle opponents. No opponents exist without other players.</summary>
public sealed class TmbtlGetOpponentsHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlGetOpponents";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.TmbtlGetOpponents.Res { MyEvepotRankings = new List<EvepotRankingParticipant>() };
}
