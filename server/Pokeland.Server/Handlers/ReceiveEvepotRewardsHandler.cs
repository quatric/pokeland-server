#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Claims event-pot rewards. No events run here yet.</summary>
public sealed class ReceiveEvepotRewardsHandler : IEndpointHandler
{
    public string Endpoint => "ReceiveEvepotRewards";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ReceiveEvepotRewards.Res
        {
            ReceivedEvepotRewardIDs = new List<EvepotRewardID>(),
            MyEvepotRankings = new List<EvepotRankingParticipant>(),
        };
}
