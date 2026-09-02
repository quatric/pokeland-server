#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Releases PPEs (GoodbyePPEIds) - see PlayerStore.RemovePPEs, which also
/// unmounts any equnit that was mounted to a released PPE.
/// </summary>
public sealed class GoodbyeHandler : IEndpointHandler
{
    public string Endpoint => "Goodbye";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.Goodbye.Req)request;
        ctx.Players.RemovePPEs(req.GoodbyePPEIds);
        return new Pokeland.Protocol.Goodbye.Res();
    }
}
