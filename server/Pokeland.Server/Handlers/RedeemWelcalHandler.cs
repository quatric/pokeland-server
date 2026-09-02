#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Welcome-calendar (login streak) reward redemption. Not tracked server-side yet.</summary>
public sealed class RedeemWelcalHandler : IEndpointHandler
{
    public string Endpoint => "RedeemWelcal";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.RedeemWelcal.Res
        {
            Success = Pokeland.Protocol.Bool.False,
            HaveGifts = Pokeland.Protocol.Bool.False,
        };
}
