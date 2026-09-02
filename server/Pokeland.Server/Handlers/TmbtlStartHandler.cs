#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Starts a Totem Battle run. The event always exists on this server
/// (Events.TmbtlEvedefID, shipped in Login's Reset alongside the world
/// event - see LoginHandler), so this always succeeds rather than the old
/// stub's ErrorNotInEvent.
/// </summary>
public sealed class TmbtlStartHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlStart";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.TmbtlStart.Req)request;
        return new Pokeland.Protocol.TmbtlStart.Res
        {
            Result = TmbtlStartResult.Success,
            TmbtlCode = req.TmbtlCode ?? System.Array.Empty<TmbtlCodeX>(),
            IsPickedUp = Pokeland.Protocol.Bool.False,
        };
    }
}
