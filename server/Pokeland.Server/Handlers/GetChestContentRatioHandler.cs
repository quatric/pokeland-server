#nullable disable
using System;
using System.Linq;
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Chest drop-rate breakdown. PlayerStore.OpenChest grants one equnit per
/// chest, an even roll across every non-NONE UnitPrefix - report that same
/// uniform table here rather than an empty one, so the client's odds
/// display matches what OpenChest actually pays out.
/// </summary>
public sealed class GetChestContentRatioHandler : IEndpointHandler
{
    public string Endpoint => "GetChestContentRatio";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var prefixes = Enum.GetValues<UnitPrefix>().Where(p => p != UnitPrefix.NONE).ToList();
        var ratio = 1f / prefixes.Count;
        return new Pokeland.Protocol.GetChestContentRatio.Res
        {
            ChestContentRatio = new ChestContentRatio
            {
                SpEqunits = new List<UnitPrefix>(),
                SpEqunitRatios = new List<float>(),
                NormalEqunits = prefixes,
                NormalEqunitRatios = prefixes.Select(_ => ratio).ToList(),
            },
        };
    }
}
