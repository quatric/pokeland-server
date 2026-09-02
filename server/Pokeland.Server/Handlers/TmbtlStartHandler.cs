#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Starts a totem battle. There's no event running to battle in yet,
/// so this reports the same error the client would get from a real server
/// outside an event window rather than fabricating a battle.</summary>
public sealed class TmbtlStartHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlStart";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.TmbtlStart.Res
        {
            Result = TmbtlStartResult.ErrorNotInEvent,
            TmbtlCode = System.Array.Empty<TmbtlCodeX>(),
            IsPickedUp = Pokeland.Protocol.Bool.False,
        };
}
