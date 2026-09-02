#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// PPE (equipment) goodbye - the equipment system doesn't exist on this
/// server yet, so there's nothing to remove; just ack.
/// </summary>
public sealed class GoodbyeHandler : IEndpointHandler
{
    public string Endpoint => "Goodbye";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.Goodbye.Res();
}
