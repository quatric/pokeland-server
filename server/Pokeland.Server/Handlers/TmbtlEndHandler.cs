#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Ends a totem battle. Totem battles aren't implemented; report a zeroed result.</summary>
public sealed class TmbtlEndHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlEnd";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.TmbtlEnd.Res
        {
            EvepotResult = new TmbtlEndEvepotResult(),
            MyEvepotRankings = new List<EvepotRankingParticipant>(),
        };
}
