#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// Totem Battle helpers shared by TmbtlGetOpponentsHandler, TmbtlEndHandler
/// and GetEvepotRankingHandler - building EvepotRankingParticipant/
/// TmbtlOpponent from a Player the same way GetRankingHandler builds
/// RankingParticipant from one (see RankingParticipantFor there).
/// </summary>
public static class Tmbtl
{
    /// <summary>A few always-available CPU trainers, used only when no other
    /// real account exists yet to fill the opponent list - a fresh
    /// per-device save (PlayerStoreManager) otherwise has nobody to fight.</summary>
    private static readonly (string Name, int MonsNo, int Level)[] CpuRoster =
    {
        ("Youngster Joey", 25, 12),   // Pikachu
        ("Lass Ren", 1, 12),          // Bulbasaur
        ("Bug Catcher Rick", 10, 10), // Caterpie
    };

    public static EvepotRankingParticipant RankingParticipantFor(string baasUserId, Player player)
    {
        var points = player.EvepotPoints.GetValueOrDefault((int)Events.TmbtlEvepotID);
        return new EvepotRankingParticipant
        {
            BaaSUserId = baasUserId,
            Nickname = player.Nickname ?? "Trainer",
            MiiCoreData = Array.Empty<byte>(),
            EvepotID = Events.TmbtlEvepotID,
            EvepotTotalCount = points,
            LastUpdatedUTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ"),
        };
    }

    public static List<EvepotRankingParticipant> Rankings(string selfBaasUserId, Player self, PlayerStoreManager players)
    {
        var ranked = players.AllOthers(selfBaasUserId)
            .Select(o => RankingParticipantFor(o.Id, o.Player))
            .Append(RankingParticipantFor(selfBaasUserId, self))
            .OrderByDescending(p => p.EvepotTotalCount)
            .ThenBy(p => p.BaaSUserId, StringComparer.Ordinal)
            .ToList();

        for (var i = 0; i < ranked.Count; i++)
        {
            ranked[i].Rank = i + 1;
            ranked[i].TieRank = ranked[i].Rank;
        }
        return ranked;
    }

    private static TmbtlOpponent OpponentFor(long id, string baasUserId, string nickname, IEnumerable<OwnedPPE> roster)
    {
        var bpaes = roster.Take(3)
            .Select(p => PPEFactory.BuildBPAE(p.MonsNo, p.Level, p.Grade, p.Waza0, p.Waza1))
            .ToList();
        if (bpaes.Count == 0)
            bpaes.Add(PPEFactory.BuildBPAE(monsNo: 1, level: 5, grade: 0, waza0: 0, waza1: 0));

        return new TmbtlOpponent
        {
            TmbtlOpponentId = id,
            TmbtlCode = Array.Empty<TmbtlCodeX>(),
            BaaSUserId = baasUserId,
            Nickname = nickname,
            MiiCoreData = Array.Empty<byte>(),
            HonorID = HonorID.NONE,
            HonorParam = 0,
            BPAEs = bpaes,
        };
    }

    /// <summary>Real other accounts (per-device saves) first, padded out with
    /// fixed CPU trainers so a lone player still has someone to fight.</summary>
    public static List<TmbtlOpponent> Opponents(string selfBaasUserId, PlayerStoreManager players)
    {
        var others = players.AllOthers(selfBaasUserId).ToList();
        var opponents = others
            .Select((o, i) => OpponentFor(i + 1, o.Id, o.Player.Nickname ?? "Trainer", o.Player.OwnedPPEs))
            .ToList();

        var need = Math.Max(0, 3 - opponents.Count);
        for (var i = 0; i < need; i++)
        {
            var cpu = CpuRoster[i % CpuRoster.Length];
            opponents.Add(OpponentFor(1000 + i, $"cpu-{i}", cpu.Name,
                new[] { new OwnedPPE { MonsNo = cpu.MonsNo, Level = cpu.Level, Grade = 0, Waza0 = 0, Waza1 = 0 } }));
        }
        return opponents;
    }
}
