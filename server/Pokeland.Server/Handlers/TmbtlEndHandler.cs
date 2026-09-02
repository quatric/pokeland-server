#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Records a finished Totem Battle. Req derives from BaseDF.Req, so the
/// piggybacked DoneFlag diff (if any) is already applied by GameDispatcher
/// before this runs - see the class doc there. Points/tally persistence goes
/// through PlayerStore.ApplyTmbtlResult, the same Apply*-and-Save pattern
/// every other stateful handler in this codebase uses.
/// </summary>
public sealed class TmbtlEndHandler : IEndpointHandler
{
    public string Endpoint => "TmbtlEnd";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.TmbtlEnd.Req)request;
        var evepotResult = ctx.Players.ApplyTmbtlResult(req.TmbtlResult, req.DefeatCount, req.DrawCount);
        var rankings = Tmbtl.Rankings(session.BaaSUserId, ctx.Players.Current, ctx.PlayerManager);

        return new Pokeland.Protocol.TmbtlEnd.Res
        {
            EvepotResult = evepotResult,
            MyEvepotRankings = rankings,
        };
    }
}
