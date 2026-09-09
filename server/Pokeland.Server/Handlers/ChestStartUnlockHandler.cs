#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Starts a chest's unlock timer. The server mints the ChestId in EndStage
/// (StartStage's MHM.DropChestTypeID makes the in-stage pickup possible -
/// see StartStageHandler) and persists it via PlayerStore.GrantChest, so by
/// the time this runs the entry already exists; see
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
