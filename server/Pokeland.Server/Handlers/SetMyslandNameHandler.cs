#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Renames the player's mysland - see PlayerStore.SetMyslandName.
/// The request carries no id to check (a single-mysland revival always
/// means "this one"), so IslandCode is read only for logging.</summary>
public sealed class SetMyslandNameHandler : IEndpointHandler
{
    public string Endpoint => "SetMyslandName";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.SetMyslandName.Req)request;
        ctx.Players.SetMyslandName(req.Name);
        return new Pokeland.Protocol.SetMyslandName.Res();
    }
}
