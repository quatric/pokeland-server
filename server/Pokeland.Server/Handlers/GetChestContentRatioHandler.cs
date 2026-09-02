#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Chest drop-rate breakdown. Chests unimplemented; report empty odds tables.</summary>
public sealed class GetChestContentRatioHandler : IEndpointHandler
{
    public string Endpoint => "GetChestContentRatio";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetChestContentRatio.Res
        {
            ChestContentRatio = new ChestContentRatio
            {
                SpEqunits = new List<UnitPrefix>(),
                SpEqunitRatios = new List<float>(),
                NormalEqunits = new List<UnitPrefix>(),
                NormalEqunitRatios = new List<float>(),
            },
        };
}
