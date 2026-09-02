#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The Home screen's periodic AutoReq/AutoRes sync (KPI, non-active-seconds,
/// equipment/PPE/island deltas). Every one of those subsystems is
/// unimplemented, so there's nothing to apply - ack with the shared empty
/// AutoRes envelope other CommitHome.BaseAutoReq-derived endpoints use.
/// </summary>
public sealed class CommitHomeHandler : IEndpointHandler
{
    public string Endpoint => "CommitHome";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.CommitHome.Res();
}
