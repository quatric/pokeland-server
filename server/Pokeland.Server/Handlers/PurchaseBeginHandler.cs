#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Opens an IAP flow - see PlayerStore.BeginPurchase/ActivatePurchase
/// for why this is a free-diamond grant rather than a real store.</summary>
public sealed class PurchaseBeginHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseBegin";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.PurchaseBegin.Req)request;
        ctx.Players.BeginPurchase(req.Magic, req.SKUID);
        return new Pokeland.Protocol.PurchaseBegin.Res();
    }
}
