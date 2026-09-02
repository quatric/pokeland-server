#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Mounts an equnit into a PPE socket. Equipment system unimplemented; ack.</summary>
public sealed class EqunitMountHandler : IEndpointHandler
{
    public string Endpoint => "EqunitMount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.EqunitMount.Res();
}
