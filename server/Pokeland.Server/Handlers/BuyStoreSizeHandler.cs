#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Buys inventory expansion (PPE / normal equnit / sp equnit slots) with
/// diamonds - see PlayerStore.BuyStoreSize for why the price is improvised
/// rather than extracted retail data (there is no real cost curve to use).
/// Reports the new balance and sizes back through AutoRes either way, same
/// pattern as BuyUtensil.
/// </summary>
public sealed class BuyStoreSizeHandler : IEndpointHandler
{
    public string Endpoint => "BuyStoreSize";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.BuyStoreSize.Req)request;
        ctx.Players.BuyStoreSize(req.PPEStoreSizeBuyUnit, req.NormalEqunitStoreSizeBuyUnit, req.SpEqunitStoreSizeBuyUnit);
        var player = ctx.Players.Current;

        return new Pokeland.Protocol.BuyStoreSize.Res
        {
            A = new[]
            {
                new AutoRes
                {
                    DiamondFreePaid = new[] { player.DiamondFree, player.DiamondPaid },
                    PaidPPEStoreSize = new[] { player.PaidPPEStoreSize },
                    PaidNormalSpEqunitStoreSize = new[]
                    {
                        player.PaidNormalEqunitStoreSize,
                        player.PaidSpEqunitStoreSize,
                    },
                },
            },
        };
    }
}
