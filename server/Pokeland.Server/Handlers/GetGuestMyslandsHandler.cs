#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Other players' myslands for the Globe. There is exactly one mysland in
/// this world (World.MyslandId/IslandID), shared by every account, so a
/// "guest mysland" is just another real player's own copy of it - their
/// chosen name and nickname, same IslandCode. GuestIslandCodes filters to
/// that shared code when the client bothers to ask for a specific one;
/// otherwise every other known account is offered.
/// </summary>
public sealed class GetGuestMyslandsHandler : IEndpointHandler
{
    public string Endpoint => "GetGuestMyslands";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GetGuestMyslands.Req)request;
        var sharedCode = World.IslandCode(ctx.Config.CurrentEvedefID);
        var wantsShared = req.GuestIslandCodes is null || req.GuestIslandCodes.Count == 0
            || req.GuestIslandCodes.Any(sharedCode.Contains);

        var guestMyslands = wantsShared
            ? ctx.PlayerManager.AllOthers(session.BaaSUserId)
                .Select(o =>
                {
                    var m = World.Mysland(ctx.Config.CurrentEvedefID, o.Player.MyslandName ?? "Sunny Isle");
                    m.DiscovererBaaSUserId = o.Id;
                    m.Nickname = o.Player.Nickname ?? "Trainer";
                    return m;
                })
                .ToList()
            : new List<Mysland>();

        return new Pokeland.Protocol.GetGuestMyslands.Res
        {
            A = new[] { new AutoRes { UpdatedMyslands = guestMyslands } },
        };
    }
}
