#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Client-side event-cache refresh trigger. Nothing to recompute server-side; ack.</summary>
public sealed class UpdateEventHandler : IEndpointHandler
{
    public string Endpoint => "UpdateEvent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.UpdateEvent.Res();
}
