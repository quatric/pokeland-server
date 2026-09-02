#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Finishes an IAP flow. The diamonds already landed in PurchaseActivate
/// (see PlayerStore.ActivatePurchase) - this just reports back which SKU
/// that Magic token was for, since PurchaseEnd.Req carries no SKUID itself.
/// </summary>
public sealed class PurchaseEndHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseEnd";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.PurchaseEnd.Req)request;
        var sku = ctx.Players.EndPurchase(req.Magic);
        return new Pokeland.Protocol.PurchaseEnd.Res
        {
            ProcessedSKUIDs = sku is SKUID s ? new List<SKUID> { s } : new List<SKUID>(),
        };
    }
}
