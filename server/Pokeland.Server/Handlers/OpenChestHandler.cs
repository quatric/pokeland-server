#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Opens a chest, paying out its money plus one freshly-minted equnit (see
/// PlayerStore.OpenChest) via the EqunitsDiff channel other endpoints use
/// for equipment updates.
/// </summary>
public sealed class OpenChestHandler : IEndpointHandler
{
    public string Endpoint => "OpenChest";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.OpenChest.Req)request;
        var granted = ctx.Players.OpenChest(req.ChestId, req.By);

        if (granted is not (int money, var equnit))
        {
            return new Pokeland.Protocol.OpenChest.Res
            {
                Result = OpenChestResult.ErrorNotYetUnlocked,
                HaveCStopGifts = Bool.False,
            };
        }

        AutoRes autoRes = null;
        if (money > 0 || equnit != null)
        {
            autoRes = new AutoRes();
            if (money > 0) autoRes.Money = new[] { ctx.Players.Current.Money };
            if (equnit != null)
                autoRes.EqunitsDiff = new EqunitsDiff
                {
                    UpdatedEqunits = new() { PPEFactory.BuildEqunit(equnit) },
                };
        }

        return new Pokeland.Protocol.OpenChest.Res
        {
            Result = OpenChestResult.Success,
            HaveCStopGifts = Bool.False,
            A = autoRes != null ? new[] { autoRes } : null,
        };
    }
}
