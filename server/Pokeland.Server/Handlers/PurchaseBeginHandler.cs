#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Begins an IAP purchase. No real store backend exists; ack.</summary>
public sealed class PurchaseBeginHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseBegin";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PurchaseBegin.Res();
}
