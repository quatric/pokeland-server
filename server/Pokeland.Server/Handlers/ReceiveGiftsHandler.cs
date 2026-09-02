#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Claims gifts from the mailbox - PlayerStore.ReceiveGifts applies
/// each one's payload (Diamond/Money land in the wallet immediately) and
/// marks it received.</summary>
public sealed class ReceiveGiftsHandler : IEndpointHandler
{
    public string Endpoint => "ReceiveGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.ReceiveGifts.Req)request;
        var claimed = ctx.Players.ReceiveGifts(req.ReceiveAll == Bool.True, req.GiftIds);
        var player = ctx.Players.Current;
        return new Pokeland.Protocol.ReceiveGifts.Res
        {
            ReceivedGiftIds = claimed,
            A = new[]
            {
                new AutoRes
                {
                    Money = new[] { player.Money },
                    DiamondFreePaid = new[] { player.DiamondFree, player.DiamondPaid },
                },
            },
        };
    }
}
