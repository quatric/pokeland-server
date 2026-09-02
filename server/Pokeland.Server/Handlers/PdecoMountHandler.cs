#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Mounts a park decoration onto a PPE. Decoration system unimplemented; ack.</summary>
public sealed class PdecoMountHandler : IEndpointHandler
{
    public string Endpoint => "PdecoMount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PdecoMount.Res();
}
