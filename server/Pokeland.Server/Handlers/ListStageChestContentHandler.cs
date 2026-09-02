#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Stage-clear chest/equnit preview. Every stage offers TutorialCopper1 (see
/// StartStageHandler), so HaveChest is honestly true; the equnit lists stay
/// empty since this server has no item chest content (PPEFactory's
/// equipment-layout note).
/// </summary>
public sealed class ListStageChestContentHandler : IEndpointHandler
{
    public string Endpoint => "ListStageChestContent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ListStageChestContent.Res
        {
            HaveChest = Pokeland.Protocol.Bool.True,
            NotableSpEqunits = new List<UnitPrefix>(),
            StageEqunits = new List<UnitPrefix>(),
        };
}
