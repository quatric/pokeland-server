#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Speeds up a chest unlock with a ticket. Chests unimplemented; ack.</summary>
public sealed class ChestUseJitanTicketHandler : IEndpointHandler
{
    public string Endpoint => "ChestUseJitanTicket";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ChestUseJitanTicket.Res();
}
