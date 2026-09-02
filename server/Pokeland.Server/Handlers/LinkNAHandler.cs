#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Links a Nintendo Account to the save. No account backend here; ack.</summary>
public sealed class LinkNAHandler : IEndpointHandler
{
    public string Endpoint => "LinkNA";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.LinkNA.Res();
}
