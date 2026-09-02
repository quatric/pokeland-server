#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Opens a chest. Money-only reward (see PendingChest) - a real item drop
/// needs the equnit wire layout this server still can't pack (PPEFactory's
/// equipment-layout note), so this pays out the chest's cash content via the
/// same AutoRes.Money channel EndStage already uses rather than fabricating
/// equipment.
/// </summary>
public sealed class OpenChestHandler : IEndpointHandler
{
    public string Endpoint => "OpenChest";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.OpenChest.Req)request;
        var granted = ctx.Players.OpenChest(req.ChestId, req.By);

        if (granted is not int money)
        {
            return new Pokeland.Protocol.OpenChest.Res
            {
                Result = OpenChestResult.ErrorNotYetUnlocked,
                HaveCStopGifts = Bool.False,
            };
        }

        return new Pokeland.Protocol.OpenChest.Res
        {
            Result = OpenChestResult.Success,
            HaveCStopGifts = Bool.False,
            A = money > 0
                ? new[] { new AutoRes { Money = new[] { ctx.Players.Current.Money } } }
                : null,
        };
    }
}
