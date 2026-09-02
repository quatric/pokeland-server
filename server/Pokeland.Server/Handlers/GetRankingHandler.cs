#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Event ranking board. Now that every device has its own save
/// (PlayerStoreManager), "other players" are real accounts on this server
/// rather than a single-player fiction - so this ranks every known account by
/// distinct Pokémon caught, the one stat every save actually tracks.
///
/// There is no per-run "battle points" formula recovered yet (no BP field is
/// persisted anywhere), so MaxPlainBPs ships as a zeroed placeholder the same
/// length as PokedexIDs - enough for the client's ranking-row renderer to not
/// choke on a null/short array, but not a real score.
/// </summary>
public sealed class GetRankingHandler : IEndpointHandler
{
    public string Endpoint => "GetRanking";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var self = RankingParticipantFor(session.BaaSUserId, ctx.Players.Current);
        var others = ctx.PlayerManager.AllOthers(session.BaaSUserId)
            .Select(o => RankingParticipantFor(o.Id, o.Player));

        var ranked = others.Append(self)
            .OrderByDescending(p => p.PokedexIDs.Count)
            .ThenBy(p => p.BaaSUserId, StringComparer.Ordinal)
            .ToList();

        for (var i = 0; i < ranked.Count; i++)
        {
            ranked[i].Rank = i + 1;
            ranked[i].TieRank = ranked[i].Rank;
        }

        return new Pokeland.Protocol.GetRanking.Res
        {
            RankingParticipants = ranked,
            TimestampUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
    }

    private static RankingParticipant RankingParticipantFor(string baasUserId, Player player)
    {
        var dex = player.OwnedPPEs.Select(p => (PokedexID)p.MonsNo).Distinct().ToList();
        return new RankingParticipant
        {
            BaaSUserId = baasUserId,
            Nickname = player.Nickname ?? "Trainer",
            MiiCoreData = Array.Empty<byte>(),
            PokedexIDs = dex,
            MaxPlainBPs = Enumerable.Repeat(0, dex.Count).ToList(),
            LastUpdatedUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
    }
}
