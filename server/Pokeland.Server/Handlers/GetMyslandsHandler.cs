#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The Globe's mysland-list refresh. Login ships the one mysland (see
/// World.Mysland/World for why it rides along with the rest of the world
/// rather than only through this AutoRes route) at boot; this is what
/// re-fetches it - the empty-envelope stub answered with a null
/// UpdatedMyslands, leaving the list empty on any refresh after the first.
/// </summary>
public sealed class GetMyslandsHandler : IEndpointHandler
{
    public string Endpoint => "GetMyslands";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetMyslands.Res
        {
            A = new[] { new AutoRes
            {
                UpdatedMyslands = new List<Mysland> { World.Mysland(ctx.Config.CurrentEvedefID, ctx.Players.Current.MyslandName ?? "Sunny Isle") },
            } },
        };
    }
}
