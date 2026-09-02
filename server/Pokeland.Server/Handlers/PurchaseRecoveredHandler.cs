#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Recovers a previously-interrupted IAP. No real store backend exists.</summary>
public sealed class PurchaseRecoveredHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseRecovered";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PurchaseRecovered.Res { ProcessedSKUIDs = new List<SKUID>() };
}
