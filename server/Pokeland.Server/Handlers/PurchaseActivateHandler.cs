#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Activates a store purchase flow. No real store backend exists; ack.</summary>
public sealed class PurchaseActivateHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseActivate";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PurchaseActivate.Res();
}
