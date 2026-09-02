#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Expands inventory store size. Inventory sizing is unimplemented; ack.</summary>
public sealed class BuyStoreSizeHandler : IEndpointHandler
{
    public string Endpoint => "BuyStoreSize";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.BuyStoreSize.Res();
}
