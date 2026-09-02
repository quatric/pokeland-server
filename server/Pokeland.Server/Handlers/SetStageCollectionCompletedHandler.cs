#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Marks a stage's all-equnit-drop collection reward claimed - see
/// PlayerStore.MarkCollectionRewarded. Persisted so World.Stage/JourneyStage
/// report Stage.CollectionRewarded=True from then on instead of the client's
/// claim being silently discarded on every relaunch.</summary>
public sealed class SetStageCollectionCompletedHandler : IEndpointHandler
{
    public string Endpoint => "SetStageCollectionCompleted";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.SetStageCollectionCompleted.Req)request;
        ctx.Players.MarkCollectionRewarded(World.StageKey(req.StageCode ?? System.Array.Empty<StageCodeX>()));
        return new Pokeland.Protocol.SetStageCollectionCompleted.Res();
    }
}
