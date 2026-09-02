#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Adds equipment sockets to a PPE. PPE/equipment system unimplemented; ack.</summary>
public sealed class AddNormalSocketCountHandler : IEndpointHandler
{
    public string Endpoint => "AddNormalSocketCount";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.AddNormalSocketCount.Res();
}
