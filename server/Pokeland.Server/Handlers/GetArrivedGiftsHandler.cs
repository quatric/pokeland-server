#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The mailbox's gift-list screen (opened from Camp's gift-box icon). There's
/// no gift-granting system yet (no friend gifts, no login-bonus gifts), so an
/// empty list is the correct answer here, not a stub - the empty-envelope
/// fallback answered with a null Gifts field instead of an empty one, which
/// is the difference between "no handler" and "no gifts".
/// </summary>
public sealed class GetArrivedGiftsHandler : IEndpointHandler
{
    public string Endpoint => "GetArrivedGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetArrivedGifts.Res
        {
            Gifts = new List<Pokeland.Protocol.Gift>(),
        };
    }
}
