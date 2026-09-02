#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The Home screen's periodic AutoReq/AutoRes sync (KPI, non-active-seconds,
/// equipment/PPE/island deltas). CommitUpdatedPPE/CommitUpdatedEqunit are
/// applied generically by GameDispatcher before this runs; CommitKpi/
/// CommitNonActiveSec/CommitUpdatedEvedef/CommitUpdatedIsland have no
/// server-side tracking behind them, so there's nothing left to apply here -
/// ack with the shared empty AutoRes envelope other CommitHome.BaseAutoReq-
/// derived endpoints use.
/// </summary>
public sealed class CommitHomeHandler : IEndpointHandler
{
    public string Endpoint => "CommitHome";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.CommitHome.Res();
}
