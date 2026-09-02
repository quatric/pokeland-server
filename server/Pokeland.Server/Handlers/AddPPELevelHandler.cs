#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Levels up a PPE by spending AddPPELevel utensils - see
/// PlayerStore.AddPPELevel for the persistence and PPE.X[17] for how it
/// reaches the wire.
/// </summary>
public sealed class AddPPELevelHandler : IEndpointHandler
{
    public string Endpoint => "AddPPELevel";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.AddPPELevel.Req)request;
        ctx.Players.AddPPELevel(req.PPEId, req.AddLevel);
        return new Pokeland.Protocol.AddPPELevel.Res();
    }
}
