#nullable disable
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The mailbox's gift-list screen (opened from Camp's gift-box icon). See
/// PlayerStore.GrantGift/GrantWelcomeGiftIfNeeded for how a mailbox entry is
/// created; this just reports the ones not yet claimed.
/// </summary>
public sealed class GetArrivedGiftsHandler : IEndpointHandler
{
    public string Endpoint => "GetArrivedGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetArrivedGifts.Res
        {
            Gifts = ctx.Players.ArrivedGifts().Select(World.Gift).ToList(),
        };
    }
}
