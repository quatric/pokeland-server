#nullable disable
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Clears chest entries the client is done with - see
/// PlayerStore.RemoveChests for why GoodbyeChestIds (discarded unopened) and
/// GetChestIds (opened and collected) both just drop the entry.
/// </summary>
public sealed class GoodbyeChestsHandler : IEndpointHandler
{
    public string Endpoint => "GoodbyeChests";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GoodbyeChests.Req)request;
        ctx.Players.RemoveChests((req.GoodbyeChestIds ?? Enumerable.Empty<long>())
            .Concat(req.GetChestIds ?? Enumerable.Empty<long>()));
        return new Pokeland.Protocol.GoodbyeChests.Res();
    }
}
