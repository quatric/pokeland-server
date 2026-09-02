#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Totem Battle points leaderboard - the Evepot-scoped counterpart to
/// GetRankingHandler's Pokedex-count ranking, built the same way (real
/// cross-account data via PlayerStoreManager.AllOthers, see Tmbtl.Rankings).
/// </summary>
public sealed class GetEvepotRankingHandler : IEndpointHandler
{
    public string Endpoint => "GetEvepotRanking";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var rankings = Tmbtl.Rankings(session.BaaSUserId, ctx.Players.Current, ctx.PlayerManager);

        return new Pokeland.Protocol.GetEvepotRanking.Res
        {
            EvepotRankingParticipants = rankings,
            TimestampUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
    }
}
