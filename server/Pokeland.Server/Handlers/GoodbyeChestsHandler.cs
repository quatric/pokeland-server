#nullable disable
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Clears chest entries the client is done with - see
/// PlayerStore.RemoveChests for why GoodbyeChestIds (discarded unopened)
/// drops the entry while GetChestIds (confirmed into the refinery, or an
/// opened chest's reward collected) only drops it once opened.
/// </summary>
public sealed class GoodbyeChestsHandler : IEndpointHandler
{
    public string Endpoint => "GoodbyeChests";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GoodbyeChests.Req)request;
        var goodbye = req.GoodbyeChestIds ?? Enumerable.Empty<long>();
        var got = req.GetChestIds ?? Enumerable.Empty<long>();
        var removed = ctx.Players.RemoveChests(goodbye, got);
        ctx.Log.LogInformation(
            "GoodbyeChests: goodbye=[{Goodbye}] got=[{Got}] removed=[{Removed}] remaining=[{Remaining}]",
            string.Join(",", goodbye), string.Join(",", got),
            string.Join(",", removed), string.Join(",", ctx.Players.ChestIds()));
        return new Pokeland.Protocol.GoodbyeChests.Res();
    }
}
