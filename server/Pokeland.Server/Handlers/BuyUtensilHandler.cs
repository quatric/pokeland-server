#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Buys utensils (JitanTicket and friends) - free, matching
/// docs/tables/UtensilDesc.json's real m_priceDiamond of 0 (see
/// PlayerStore.UtensilPriceDiamond). Reports the new diamond balance and utensil counts
/// back through the AutoRes side-channel on failure or success alike, so the
/// client's own balance always matches what actually happened.
/// </summary>
public sealed class BuyUtensilHandler : IEndpointHandler
{
    public string Endpoint => "BuyUtensil";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.BuyUtensil.Req)request;
        ctx.Players.BuyUtensil(req.UtensilID, req.Count);
        var player = ctx.Players.Current;

        return new Pokeland.Protocol.BuyUtensil.Res
        {
            A = new[]
            {
                new AutoRes
                {
                    DiamondFreePaid = new[] { player.DiamondFree, player.DiamondPaid },
                    UpdatedUtensils = player.Utensils
                        .Select(kv => new Utensil { UtensilID = (UtensilID)kv.Key, Count = kv.Value })
                        .ToList(),
                },
            },
        };
    }
}
