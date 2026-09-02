#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Marks a stage's collection (all-equnit-drops) complete. Not tracked yet; ack.</summary>
public sealed class SetStageCollectionCompletedHandler : IEndpointHandler
{
    public string Endpoint => "SetStageCollectionCompleted";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetStageCollectionCompleted.Res();
}
