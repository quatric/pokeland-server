#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Spends a Jitan ticket to finish a chest's unlock timer immediately (see
/// PlayerStore.UseJitanTicket). Does not open the chest - the client still
/// calls OpenChest separately once the wait reads as satisfied.
/// </summary>
public sealed class ChestUseJitanTicketHandler : IEndpointHandler
{
    public string Endpoint => "ChestUseJitanTicket";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.ChestUseJitanTicket.Req)request;
        ctx.Players.UseJitanTicket(req.ChestId);
        return new Pokeland.Protocol.ChestUseJitanTicket.Res();
    }
}
