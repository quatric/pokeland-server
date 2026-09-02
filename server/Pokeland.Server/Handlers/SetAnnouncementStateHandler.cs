#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Marks announcements read up through the given id - persisted so
/// the welcome announcement in Announcements.cs stops popping up.</summary>
public sealed class SetAnnouncementStateHandler : IEndpointHandler
{
    public string Endpoint => "SetAnnouncementState";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.SetAnnouncementState.Req)request;
        ctx.Players.SetHeadMarkedAsReadAnnouncementId(req.HeadMarkedAsReadAnnouncementId);
        return new Pokeland.Protocol.SetAnnouncementState.Res();
    }
}
