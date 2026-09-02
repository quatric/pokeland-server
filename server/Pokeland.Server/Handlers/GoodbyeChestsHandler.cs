#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Chest goodbye/get - chests aren't tracked server-side yet; ack.</summary>
public sealed class GoodbyeChestsHandler : IEndpointHandler
{
    public string Endpoint => "GoodbyeChests";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GoodbyeChests.Res();
}
