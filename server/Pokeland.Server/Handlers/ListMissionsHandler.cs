#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The challenge board's own refresh call. Login ships an initial
/// MissionSummary so the board isn't empty on first open, but the board
/// re-fetches through this endpoint whenever it's opened later - the
/// empty-envelope stub answered with a null MissionSummary AutoRes, so the
/// board showed nothing (and the tournament-rank-gate dialog in front of it)
/// on every visit after the first.
/// </summary>
public sealed class ListMissionsHandler : IEndpointHandler
{
    public string Endpoint => "ListMissions";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var player = ctx.Players.Current;
        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");

        return new Pokeland.Protocol.ListMissions.Res
        {
            A = new[] { new AutoRes
            {
                MissionSummary = Missions.Summary(utcNow, player),
            } },
        };
    }
}
