#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Finishes an IAP purchase. No real store backend exists; nothing processed.</summary>
public sealed class PurchaseEndHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseEnd";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PurchaseEnd.Res { ProcessedSKUIDs = new List<SKUID>() };
}
