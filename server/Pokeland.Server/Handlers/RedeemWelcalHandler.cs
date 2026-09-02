#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Redeems one day of the Welcome Calendar login-streak reward - see
/// PlayerStore.RedeemWelcal/WelcalCalendar for the reward table and why
/// only WelcalID.BOSP is treated as running.
/// </summary>
public sealed class RedeemWelcalHandler : IEndpointHandler
{
    public string Endpoint => "RedeemWelcal";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.RedeemWelcal.Req)request;
        var granted = ctx.Players.RedeemWelcal(req.WelcalStepID);

        if (granted is not (int diamond, int money, var ppe, var equnit))
        {
            return new Pokeland.Protocol.RedeemWelcal.Res
            {
                Success = Pokeland.Protocol.Bool.False,
                HaveGifts = Pokeland.Protocol.Bool.False,
            };
        }

        var autoRes = new AutoRes();
        if (diamond > 0) autoRes.DiamondFreePaid = new[] { ctx.Players.Current.DiamondFree, ctx.Players.Current.DiamondPaid };
        if (money > 0) autoRes.Money = new[] { ctx.Players.Current.Money };
        if (ppe != null)
            autoRes.PPEsDiff = new PPEsDiff
            {
                UpdatedPPEs = new() { PPEFactory.BuildPPE(ppe.Id, ppe.MonsNo, ppe.Level, ppe.Grade, ppe.Waza0, ppe.Waza1, ppe.Nickname ?? "") },
                RemovedPPEIds = new(),
            };
        if (equnit != null)
            autoRes.EqunitsDiff = new EqunitsDiff { UpdatedEqunits = new() { PPEFactory.BuildEqunit(equnit) } };

        return new Pokeland.Protocol.RedeemWelcal.Res
        {
            Success = Pokeland.Protocol.Bool.True,
            HaveGifts = Pokeland.Protocol.Bool.False,
            A = new[] { autoRes },
        };
    }
}
