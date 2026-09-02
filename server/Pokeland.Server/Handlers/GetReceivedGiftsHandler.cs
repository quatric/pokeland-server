#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Gift inbox. Gifts aren't generated server-side yet.</summary>
public sealed class GetReceivedGiftsHandler : IEndpointHandler
{
    public string Endpoint => "GetReceivedGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetReceivedGifts.Res { Gifts = new List<Gift>() };
}
