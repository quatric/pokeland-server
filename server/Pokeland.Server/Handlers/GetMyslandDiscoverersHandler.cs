#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The "who else found this mysland" leaderboard strip. There are no other
/// accounts on a single-player revival, so an empty list is the correct
/// answer, not a stub - the empty-envelope fallback answered with a null
/// MyslandDiscoverers field instead of an empty one.
/// </summary>
public sealed class GetMyslandDiscoverersHandler : IEndpointHandler
{
    public string Endpoint => "GetMyslandDiscoverers";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetMyslandDiscoverers.Res
        {
            MyslandDiscoverers = new List<MyslandDiscoverer>(),
        };
    }
}
