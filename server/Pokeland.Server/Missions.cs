#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// The mission rows this server offers, as much of MissionDesc.json as the
/// server actually has to know.
///
/// The client owns the descriptions, icons and progress counting - it reads
/// MissionDesc itself and reports progress up through the RecordMissions
/// commit that rides on every request. The server only has to know when a
/// mission is finished (<c>Num</c>) and what redeeming it pays out, because
/// the wallet is the one thing the client is not allowed to decide.
///
/// Rows 1/6/7/8/12/14/17 are the whole of MissionGroup.TUTORIAL (1) and 71..75
/// are MissionGroup.DAILY (2); all twelve pay MissionBonus.DIAMOND.
/// </summary>
public static class Missions
{
    public readonly record struct Desc(int Num, int Diamonds);

    private static readonly Dictionary<int, Desc> Table = new()
    {
        [1] = new(1, 3),
        [6] = new(1, 3),
        [7] = new(1, 3),
        [8] = new(1, 3),
        [12] = new(1, 3),
        [14] = new(1, 3),
        [17] = new(1, 3),
        [71] = new(3, 6),
        [72] = new(1, 6),
        [73] = new(1, 3),
        [74] = new(1, 6),
        [75] = new(1, 6),
    };

    public static IEnumerable<int> IDs => Table.Keys.OrderBy(id => id);

    public static bool TryGet(int id, out Desc desc) => Table.TryGetValue(id, out desc);

    /// <summary>True once the client has reported enough progress for the
    /// mission to be redeemable. Unknown ids are never complete rather than
    /// always complete: a mission this server does not describe must not pay.
    /// </summary>
    public static bool IsComplete(int id, int progress) =>
        Table.TryGetValue(id, out var d) && progress >= d.Num;

    /// <summary>
    /// The mission list as the account currently stands. Progress is whatever
    /// the client last reported through RecordMissions, and the state follows
    /// from it: paid missions are Redeemed, finished-but-unpaid ones are
    /// CanRedeem (which is what puts the badge on the challenge board), the
    /// rest are still InProgress. The three lists are index-paired.
    /// </summary>
    public static MissionSummary Summary(string utcNow, Player player)
    {
        var ids = IDs.ToList();
        var progresses = ids
            .Select(id => player.MissionProgress.TryGetValue(id, out var p) ? p : 0)
            .ToList();
        return new MissionSummary
        {
            DailyUTCStr = utcNow,
            IDs = ids.Select(id => (MissionID)id).ToList(),
            Progresses = progresses,
            States = ids.Zip(progresses, (id, p) =>
                player.RedeemedMissions.Contains(id) ? MissionState.Redeemed
                : IsComplete(id, p) ? MissionState.CanRedeem
                : MissionState.InProgress).ToList(),
        };
    }
}
