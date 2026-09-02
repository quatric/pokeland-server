#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Starts a chest's unlock timer. The client mints its own ChestId when it
/// picks one up in-stage (StartStage's MHM.DropChestTypeID makes that
/// possible - see StartStageHandler), so the server only learns a chest
/// exists the first time this or OpenChest is called with it; see
/// PlayerStore.StartChestUnlock/PendingChest.
/// </summary>
public sealed class ChestStartUnlockHandler : IEndpointHandler
{
    public string Endpoint => "ChestStartUnlock";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.ChestStartUnlock.Req)request;
        var ok = ctx.Players.StartChestUnlock(req.ChestId);
        return new Pokeland.Protocol.ChestStartUnlock.Res { Success = ok ? Bool.True : Bool.False };
    }
}
