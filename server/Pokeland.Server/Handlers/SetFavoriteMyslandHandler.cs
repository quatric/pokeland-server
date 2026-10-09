using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Persist the single favourite island supported by the native client.
/// Handles locking (favoriting) and unlocking (where client sends [0, 0, 0] or empty).
/// Preserves unlocked islands in LastVisited without evicting them.
/// </summary>
public sealed class SetFavoriteMyslandHandler : IEndpointHandler
{
    public string Endpoint => "SetFavoriteMysland";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.SetFavoriteMysland.Req)request;
        bool isFav = req.IslandCode != null && req.IslandCode.Length > 0 && req.IslandCode.Any(x => (long)x != 0);

        if (isFav)
        {
            var key = string.Join(",", req.IslandCode.Select(c => (long)c));
            ctx.Players.SetFavorite(key);
        }
        else
        {
            ctx.Players.ClearFavorite();
        }

        var fav = ctx.Players.Current.Favorites.FirstOrDefault();
        var favCode = string.IsNullOrEmpty(fav)
            ? System.Array.Empty<IslandCodeX>()
            : fav.Split(',').Select(x => (IslandCodeX)long.Parse(x)).ToArray();

        var last = ctx.Players.Current.LastVisited.FirstOrDefault();
        var lastCode = string.IsNullOrEmpty(last)
            ? System.Array.Empty<IslandCodeX>()
            : last.Split(',').Select(x => (IslandCodeX)long.Parse(x)).ToArray();

        return new Pokeland.Protocol.SetFavoriteMysland.Res
        {
            A = new[]
            {
                new AutoRes
                {
                    MyslandSummary = new MyslandSummary
                    {
                        Favorite = favCode,
                        LastVisited = lastCode,
                    }
                }
            }
        };
    }
}
