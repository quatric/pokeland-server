#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Daily rollover (stamp card, daily counters). None of that state exists yet; ack.</summary>
public sealed class DoDailyProcessHandler : IEndpointHandler
{
    public string Endpoint => "DoDailyProcess";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.DoDailyProcess.Res();
}
