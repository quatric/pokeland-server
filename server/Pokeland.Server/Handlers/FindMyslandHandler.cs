#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Answers the Globe's mysland finder - the "search screen".
///
/// This is not an optional social feature: the tutorial routes the first stage
/// through it. Once the Camp gate opens, tapping the globe puts the player on
/// the finder with "Tap the map to find a stage.", and until that tap produces
/// a result there is no way to reach a stage at all.
///
/// The request carries the tapped point (X/Y in map space) plus how the search
/// was paid for (<see cref="FindMyslandBy"/>), and the response is just the
/// code of what turned up. Returning a code is only half the job, though: the
/// finder resolves it into a <c>Mysland</c> out of the *client-side* cache
/// (<c>GlobeMyslandFinder.iFind</c> case 5 -> <c>MyslandBox.Get</c>) and, when
/// that lookup misses, <c>Globe.iMyslandFinder</c> state 5 sees a null
/// ResultMysland and ends its coroutine without a word - a pin drops on the map
/// and the result panel stays blank, no exception anywhere.
///
/// So the mysland has to already be in that cache. <see cref="World"/> puts it
/// there at Login, via <c>Reset</c>, rather than trying to push it down here in
/// the <c>A</c> delta envelope. It is also copied into <c>A</c> below so a
/// search still works in a session where the cache was built before the mysland
/// existed.
/// </summary>
public sealed class FindMyslandHandler : IEndpointHandler
{
    public string Endpoint => "FindMysland";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.FindMysland.Req)request;

        ctx.Log.LogInformation(
            "FindMysland: evedef={Evedef} at ({X},{Y}) by={By}",
            req.EvedefID, req.X, req.Y, req.By);

        return new Pokeland.Protocol.FindMysland.Res
        {
            Found = World.IslandCode(req.EvedefID),
            A = new[]
            {
                new AutoRes
                {
                    UpdatedMyslands = new List<Mysland> { World.Mysland(req.EvedefID) },
                    UpdatedStages = new List<Stage> { World.Stage(req.EvedefID) },
                },
            },
        };
    }
}
