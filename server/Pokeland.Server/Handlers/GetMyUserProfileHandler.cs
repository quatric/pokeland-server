#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The profile screen's own refresh call (opened from Camp's single-smiley
/// icon). Login ships the trainer card once at boot; this is what re-fetches
/// it - the empty-envelope stub answered with a null MyUserProfile AutoRes,
/// which is why the card's Bulbasaur/map/name fields never reappeared after
/// a scene reload.
/// </summary>
public sealed class GetMyUserProfileHandler : IEndpointHandler
{
    public string Endpoint => "GetMyUserProfile";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var utcNow = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");

        return new Pokeland.Protocol.GetMyUserProfile.Res
        {
            UTCStr = utcNow,
            A = new[] { new AutoRes { UpdatedMyUserProfile = UserProfiles.Current() } },
        };
    }
}
