#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>News/announcements feed. Nothing to announce on a revival server.</summary>
public sealed class GetAnnouncementsHandler : IEndpointHandler
{
    public string Endpoint => "GetAnnouncements";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetAnnouncements.Res();
}
