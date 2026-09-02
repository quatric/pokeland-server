#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The "who else found this mysland" leaderboard strip. There is exactly one
/// mysland in this world (World.MyslandId/IslandID - see World.cs), shared by
/// every account, so every other real player on this server (per-device
/// saves via PlayerStoreManager) has legitimately "found" it too.
/// </summary>
public sealed class GetMyslandDiscoverersHandler : IEndpointHandler
{
    public string Endpoint => "GetMyslandDiscoverers";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var discoverers = ctx.PlayerManager.AllOthers(session.BaaSUserId)
            .Select(o => new MyslandDiscoverer
            {
                BaaSUserId = o.Id,
                BaaSUserMac = "",
                Nickname = o.Player.Nickname ?? "Trainer",
                MiiCoreData = Array.Empty<byte>(),
                IslandCode = World.IslandCode(ctx.Config.CurrentEvedefID),
                ColorHint = PokeWazaType.NONE,
                LastUpdatedUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
            })
            .ToList();

        return new Pokeland.Protocol.GetMyslandDiscoverers.Res { MyslandDiscoverers = discoverers };
    }
}
