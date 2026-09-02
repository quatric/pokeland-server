#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Stage-clear chest/equnit preview. Chests and drop tables unimplemented.</summary>
public sealed class ListStageChestContentHandler : IEndpointHandler
{
    public string Endpoint => "ListStageChestContent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ListStageChestContent.Res
        {
            HaveChest = Pokeland.Protocol.Bool.False,
            NotableSpEqunits = new List<UnitPrefix>(),
            StageEqunits = new List<UnitPrefix>(),
        };
}
