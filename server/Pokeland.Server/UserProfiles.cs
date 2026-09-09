#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// The trainer-card fields the client actually reads (MyUserProfileBox), shared
/// between Login (which has to seed the box before the client will accept any
/// later delta) and GetMyUserProfile (the profile screen's own refresh call).
/// </summary>
public static class UserProfiles
{
    public static MyUserProfile Current(Player player) => new()
    {
        Nickname = player.Nickname,
        // Surface the persisted Mii blob so the trainer card and multiplayer
        // guest profiles show the player's actual avatar. Falls back to an
        // empty array for accounts that have never visited the auth endpoint,
        // which is identical to the original behaviour.
        MiiCoreData = player.MiiCoreData ?? System.Array.Empty<byte>(),
        LatLng = new float[] { 0, 0 },
    };
}
