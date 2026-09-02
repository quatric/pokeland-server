#nullable disable
using System.Collections.Generic;
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Banks the result of a run.
///
/// The client is the authority on what happened inside the stage - it reports
/// the battle result, the money and Pierres it picked up and which Pokemon it
/// defeated or captured - and the server is the authority on what that is
/// worth afterwards. On the empty-envelope path the run "worked": the results
/// screen shows and the client returns to Camp, but nothing is kept, so the
/// stage's ClearCount stays 0 and the wallet is back to its Login value on the
/// next launch.
///
/// Only a <c>BattleResult.Clear</c> counts. Lose and Cancel still come through
/// here (the failure exit is the same call) and must be acknowledged, but they
/// bank nothing.
/// </summary>
public sealed class EndStageHandler : IEndpointHandler
{
    public string Endpoint => "EndStage";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.EndStage.Req)request;

        // session.CurrentIslandID is set by the StartStage that opened this
        // run (see GameSession.CurrentIslandID) - EndStage.Req itself carries
        // no StageCode at all (EndStage.g.cs), so without this every clear
        // used to get attributed to the one fixed mysland stage regardless of
        // which journey island was actually played, silently losing progress
        // on the other five.
        var islandID = session.CurrentIslandID;
        var stage = islandID is >= 1 and <= 6
            ? World.JourneyStage(ctx.Config.CurrentEvedefID, islandID.Value)
            : World.Stage(EvedefID._1);
        var cleared = req.BattleResult == BattleResult.Clear;

        int clearCount = 0;
        if (cleared)
        {
            var key = string.Join(",", stage.StageCode.Select(c => (long)c));
            clearCount = ctx.Players.RecordClear(key, req.GotMoney);
        }

        // Convert the drop StartStage offered into a real, persisted PPE.
        // OfferedPPEDropId is cleared either way so an abandoned/lost run
        // cannot be replayed to grant the same drop twice.
        var ppeUpdates = new List<PPEUpdate>();
        if (cleared && session.OfferedPPEDropId is long dropId)
        {
            var granted = ctx.Players.GrantPPE(
                session.OfferedMonsNo, session.OfferedLevel,
                grade: session.OfferedGrade, waza0: 0, waza1: 0, nickname: null);
            ppeUpdates.Add(new PPEUpdate
            {
                PPEDropId = dropId,
                PPEId = granted.Id,
                EqunitIds = new List<long>(),
            });
        }
        session.OfferedPPEDropId = null;

        ctx.Log.LogInformation(
            "EndStage: result={Result} island={Island} money=+{Money} pierres={Pierres} dps={Dps} " +
            "clearCount={Count} wallet={Wallet} grantedPPE={Granted}",
            req.BattleResult, islandID, req.GotMoney, req.GotPierreCount, req.DPS,
            clearCount, ctx.Players.Current.Money, ppeUpdates.Count > 0);

        if (cleared)
        {
            stage.State = StageState.Cleared;
            stage.ClearCount = clearCount;
        }

        return new Pokeland.Protocol.EndStage.Res
        {
            PPEUpdates = ppeUpdates,
            // Diamonds per mission slot, index-paired with Stage.JissionStates,
            // which is a fixed three.
            JissionDiamonds = new List<int> { 0, 0, 0 },
            A = cleared
                ? new[] { new AutoRes
                    {
                        Money = new[] { ctx.Players.Current.Money },
                        UpdatedStages = new List<Stage> { stage },
                    } }
                : null,
        };
    }
}
