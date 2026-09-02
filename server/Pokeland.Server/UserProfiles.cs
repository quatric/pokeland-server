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
        MiiCoreData = System.Array.Empty<byte>(),
        LatLng = new float[] { 0, 0 },
    };
}
