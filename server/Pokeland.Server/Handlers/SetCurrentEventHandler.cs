#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Fired when the client enters or leaves an event/island (the Globe's
/// per-mysland "you are here" marker). The CommitUpdatedEqunit/
/// CommitUpdatedPPE payloads that ride along are applied generically by
/// GameDispatcher (same pattern as SetDoneFlag/RecordMissions) before this
/// handler ever runs, so there's nothing left to do here beyond the ack.
/// </summary>
public sealed class SetCurrentEventHandler : IEndpointHandler
{
    public string Endpoint => "SetCurrentEvent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetCurrentEvent.Res();
}
