#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Event-pot reward tiers. No events run here yet.</summary>
public sealed class ListEvepotRewardsHandler : IEndpointHandler
{
    public string Endpoint => "ListEvepotRewards";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ListEvepotRewards.Res { MyEvepotRankings = new List<EvepotRankingParticipant>() };
}
