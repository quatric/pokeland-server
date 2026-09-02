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
        var stage = World.Stage(EvedefID._1);
        var cleared = req.BattleResult == BattleResult.Clear;

        int clearCount = 0;
        if (cleared)
        {
            var key = string.Join(",", stage.StageCode.Select(c => (long)c));
            clearCount = ctx.Players.RecordClear(key, req.GotMoney);
        }

        ctx.Log.LogInformation(
            "EndStage: result={Result} money=+{Money} pierres={Pierres} dps={Dps} " +
            "clearCount={Count} wallet={Wallet}",
            req.BattleResult, req.GotMoney, req.GotPierreCount, req.DPS,
            clearCount, ctx.Players.Current.Money);

        if (cleared)
        {
            stage.State = StageState.Cleared;
            stage.ClearCount = clearCount;
        }

        return new Pokeland.Protocol.EndStage.Res
        {
            // The run's drops. Nothing is granted yet: a PPEUpdate has to name
            // a PPEDropId the StartStage response actually offered, and that
            // response ships an empty PPEDrops list, so anything here would
            // reference a drop the client never saw.
            PPEUpdates = new List<PPEUpdate>(),
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
