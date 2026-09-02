#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Discards owned equnits - see PlayerStore.RemoveEqunits. No gift-mail
/// system exists yet for C-Stop pity gifts, so that flag stays False.
/// </summary>
public sealed class GoodbyeEqunitsHandler : IEndpointHandler
{
    public string Endpoint => "GoodbyeEqunits";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GoodbyeEqunits.Req)request;
        ctx.Players.RemoveEqunits(req.GoodbyeEqunitIds);
        return new Pokeland.Protocol.GoodbyeEqunits.Res { HaveCStopGifts = Pokeland.Protocol.Bool.False };
    }
}
