#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Buys camp utensils. Utensil economy is unimplemented; ack.</summary>
public sealed class BuyUtensilHandler : IEndpointHandler
{
    public string Endpoint => "BuyUtensil";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.BuyUtensil.Res();
}
