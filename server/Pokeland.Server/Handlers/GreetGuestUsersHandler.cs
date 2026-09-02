#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Multiplayer guest greeting. The client sends candidate BaaSUserIds it
/// picked up from myslands/rankings it has cached locally; this echoes back
/// whichever of those are actual accounts on this server (per-device saves
/// via PlayerStoreManager), rather than either inventing greetings or always
/// answering empty the way a single-player revival would have to.
/// </summary>
public sealed class GreetGuestUsersHandler : IEndpointHandler
{
    public string Endpoint => "GreetGuestUsers";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GreetGuestUsers.Req)request;
        var real = new HashSet<string>(
            ctx.PlayerManager.AllOthers(session.BaaSUserId).Select(o => o.Id),
            System.StringComparer.Ordinal);

        var greeted = (req.BaaSUserIds ?? new List<string>())
            .Where(id => real.Contains(id))
            .ToList();

        return new Pokeland.Protocol.GreetGuestUsers.Res { GreetedBaaSUserIds = greeted };
    }
}
