#nullable disable
using System;
using System.Linq;
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Stage-clear chest/equnit preview. Every stage offers TutorialCopper1 (see
/// StartStageHandler), so HaveChest is honestly true; StageEqunits mirrors
/// the uniform UnitPrefix pool PlayerStore.OpenChest actually draws from
/// (see GetChestContentRatioHandler) rather than reporting an empty list.
/// </summary>
public sealed class ListStageChestContentHandler : IEndpointHandler
{
    public string Endpoint => "ListStageChestContent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ListStageChestContent.Res
        {
            HaveChest = Pokeland.Protocol.Bool.True,
            NotableSpEqunits = new List<UnitPrefix>(),
            StageEqunits = Enum.GetValues<UnitPrefix>().Where(p => p != UnitPrefix.NONE).ToList(),
        };
}
