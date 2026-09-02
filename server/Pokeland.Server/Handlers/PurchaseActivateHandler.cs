#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Activates an IAP - this revival has no real payment processor and no
/// intent to add one, so this is where the SKU's real
/// docs/tables/SKUDesc.json diamond amount lands immediately, for free (see
/// PlayerStore.ActivatePurchase/PurchaseSkuGrants).
/// </summary>
public sealed class PurchaseActivateHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseActivate";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.PurchaseActivate.Req)request;
        int granted = ctx.Players.ActivatePurchase(req.Magic, req.SKUID);

        var res = new Pokeland.Protocol.PurchaseActivate.Res();
        if (granted > 0)
            res.A = new[] { new AutoRes
            {
                DiamondFreePaid = new[] { ctx.Players.Current.DiamondFree, ctx.Players.Current.DiamondPaid },
            } };
        return res;
    }
}
