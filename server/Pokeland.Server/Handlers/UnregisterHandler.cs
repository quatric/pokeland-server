#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Account deletion. Single-player revival - ack without touching the save.</summary>
public sealed class UnregisterHandler : IEndpointHandler
{
    public string Endpoint => "Unregister";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.Unregister.Res();
}
