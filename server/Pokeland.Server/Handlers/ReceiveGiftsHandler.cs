#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Claims gifts from the inbox. Inbox is always empty; nothing to claim.</summary>
public sealed class ReceiveGiftsHandler : IEndpointHandler
{
    public string Endpoint => "ReceiveGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.ReceiveGifts.Res { ReceivedGiftIds = new List<long>() };
}
