#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Totem Battle opponent list. Real other accounts (per-device saves via
/// PlayerStoreManager, same primitive GetRankingHandler etc. already use)
/// come first, padded out with a few fixed CPU trainers (Tmbtl.CpuRoster) so
/// a lone player still has someone to fight. Delivered through the AutoRes
/// channel like every other diff-shaped payload (see GetGuestMyslandsHandler)
/// rather than a dedicated field on Res.
/// </summary>
public sealed class TmbtlGetOpponentsHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlGetOpponents";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var opponents = Tmbtl.Opponents(session.BaaSUserId, ctx.PlayerManager);
        var rankings = Tmbtl.Rankings(session.BaaSUserId, ctx.Players.Current, ctx.PlayerManager);

        return new Pokeland.Protocol.TmbtlGetOpponents.Res
        {
            MyEvepotRankings = rankings,
            A = new[] { new AutoRes { TmbtlOpponentsDiff = new TmbtlOpponentsDiff { UpdatedTmbtlOpponents = opponents } } },
        };
    }
}
