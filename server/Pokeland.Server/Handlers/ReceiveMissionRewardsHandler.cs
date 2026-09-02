#nullable disable
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Pays out finished missions.
///
/// This is the only place a mission reward can be granted, and the client does
/// not take the payout on trust: it re-reads the wallet and the mission states
/// out of the AutoRes this returns. On the empty-envelope path the challenge
/// board's redeem button therefore did nothing visible at all.
///
/// Redeeming is also what the client waits on before it sets
/// DoneFlag.FirstChallengeDone (3) - the flag that lets Camp open the Globe -
/// so with this in place that flag can be earned rather than seeded.
/// </summary>
public sealed class ReceiveMissionRewardsHandler : IEndpointHandler
{
    public string Endpoint => "ReceiveMissionRewards";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.ReceiveMissionRewards.Req)request;
        var asked = (req.MissionIDs ?? new()).Select(id => (int)id).ToList();
        var paid = ctx.Players.RedeemMissions(asked);
        var player = ctx.Players.Current;

        ctx.Log.LogInformation(
            "ReceiveMissionRewards: asked=[{Asked}] paid=[{Paid}] diamonds={Diamonds}",
            string.Join(",", asked), string.Join(",", paid), player.DiamondFree);

        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");

        return new Pokeland.Protocol.ReceiveMissionRewards.Res
        {
            A = new[] { new AutoRes
            {
                // Free and paid balances, in that order; there is no purchase
                // flow, so the paid half is always zero.
                DiamondFreePaid = new[] { player.DiamondFree, 0 },
                MissionSummary = Missions.Summary(utcNow, player),
            } },
        };
    }
}
