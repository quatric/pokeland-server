#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Marks announcements read. No announcements are ever served; ack.</summary>
public sealed class SetAnnouncementStateHandler : IEndpointHandler
{
    public string Endpoint => "SetAnnouncementState";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetAnnouncementState.Res();
}
