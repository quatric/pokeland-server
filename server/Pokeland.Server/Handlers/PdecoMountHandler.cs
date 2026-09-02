#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Mounts a park decoration onto a PPE. Real persistence via
/// PlayerStore.MountPdeco - see there for why every PdecoID counts as owned
/// and PPE.X[6] carries the result back out through Login.
/// </summary>
public sealed class PdecoMountHandler : IEndpointHandler
{
    public string Endpoint => "PdecoMount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.PdecoMount.Req)request;
        ctx.Players.MountPdeco(req.PPEId, req.PdecoID);
        return new Pokeland.Protocol.PdecoMount.Res();
    }
}
