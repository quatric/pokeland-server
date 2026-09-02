#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Starts a chest's unlock timer. Chests aren't tracked server-side yet.</summary>
public sealed class ChestStartUnlockHandler : IEndpointHandler
{
    public string Endpoint => "ChestStartUnlock";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ChestStartUnlock.Res { Success = Pokeland.Protocol.Bool.False };
}
