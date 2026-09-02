#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>News/announcements feed - see Announcements.cs for the single
/// static welcome notice this server serves.</summary>
public sealed class GetAnnouncementsHandler : IEndpointHandler
{
    public string Endpoint => "GetAnnouncements";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var player = ctx.Players.Current;
        return new Pokeland.Protocol.GetAnnouncements.Res
        {
            A = new[]
            {
                new AutoRes
                {
                    AnnouncementState = Announcements.StateFor(player.HeadMarkedAsReadAnnouncementId),
                    UpdatedAnnouncements = new List<Announcement> { Announcements.Welcome },
                },
            },
        };
    }
}
