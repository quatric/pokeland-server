#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Fired when the client enters or leaves an event/island (the Globe's
/// per-mysland "you are here" marker). The CommitUpdatedEqunit/
/// CommitUpdatedPPE payloads that ride along describe equipment/PPE systems
/// this server doesn't implement yet, so there's nothing to persist here
/// beyond the ack the empty-envelope stub already gave - this just answers
/// without the "no handler" warning.
/// </summary>
public sealed class SetCurrentEventHandler : IEndpointHandler
{
    public string Endpoint => "SetCurrentEvent";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetCurrentEvent.Res();
}
