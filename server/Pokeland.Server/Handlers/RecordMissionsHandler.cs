#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// RecordMissions as its own endpoint call (rather than piggybacked on
/// another request). GameDispatcher already applies the MissionCommit
/// generically before routing - reading it here too would double-apply the
/// same commit - so this handler exists purely to answer with a plain ack
/// instead of falling through to the "no handler" stub warning.
/// </summary>
public sealed class RecordMissionsHandler : IEndpointHandler
{
    public string Endpoint => "RecordMissions";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.RecordMissions.Res();
}
