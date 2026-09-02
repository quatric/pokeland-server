#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Recovers a previously-interrupted IAP. Every purchase now
/// completes synchronously in PurchaseActivate (see PlayerStore.
/// ActivatePurchase), so there is never anything left mid-flight to
/// recover - an empty list is correct, not a stub.</summary>
public sealed class PurchaseRecoveredHandler : IEndpointHandler
{
    public string Endpoint => "PurchaseRecovered";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.PurchaseRecovered.Res { ProcessedSKUIDs = new List<SKUID>() };
}
