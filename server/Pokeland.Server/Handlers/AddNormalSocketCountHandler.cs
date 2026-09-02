#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Adds equipment sockets to a PPE by spending AddNormalEqunitSocket
/// utensils - see PlayerStore.AddNormalSocketCount for the persistence and
/// PPE.X[18] for how it reaches the wire.
/// </summary>
public sealed class AddNormalSocketCountHandler : IEndpointHandler
{
    public string Endpoint => "AddNormalSocketCount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.AddNormalSocketCount.Req)request;
        ctx.Players.AddNormalSocketCount(req.PPEId, req.Count);
        return new Pokeland.Protocol.AddNormalSocketCount.Res();
    }
}
