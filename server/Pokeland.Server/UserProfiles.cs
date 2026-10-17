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
        MiiCoreData = player.MiiCoreData ?? System.Array.Empty<byte>(),
        LangID = (LangID)player.LangID,
        Greeting0 = (GreetingMessageID)player.Greeting0,
        Greeting1 = (GreetingMessageID)player.Greeting1,
        GreetingParam0 = (short)player.GreetingParam0,
        GreetingParam1 = (short)player.GreetingParam1,
        LatLng = player.LatLng ?? new float[] { 0, 0 },
        Generation = (GenerationID)player.Generation,
        HonorID = (HonorID)player.HonorID,
        HonorParam = player.HonorParam,
        SealedLatLng = player.SealedLatLng ? Bool.True : Bool.False,
    };
}
