#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Mounts an equnit into a PPE socket - see PlayerStore.MountEqunit for the
/// persistence. PPEId 0 unmounts (the request shape has no separate
/// unmount endpoint).
/// </summary>
public sealed class EqunitMountHandler : IEndpointHandler
{
    public string Endpoint => "EqunitMount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.EqunitMount.Req)request;
        ctx.Players.MountEqunit(req.EqunitId, req.PPEId, req.SocketNo);
        return new Pokeland.Protocol.EqunitMount.Res();
    }
}
