#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Challenge-stage (event stage rotation) list. No event stages exist yet.</summary>
public sealed class ChstgGetStageHandler : IEndpointHandler
{
    public string Endpoint => "ChstgGetStage";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ChstgGetStage.Res { StageCode = System.Array.Empty<StageCodeX>() };
}
