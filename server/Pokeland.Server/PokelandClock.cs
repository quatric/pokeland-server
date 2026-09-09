#nullable disable
namespace Pokeland.Server;

/// <summary>
/// Central UTC source for timestamps sent to the client and used by persistent
/// timers. Patched clients keep Android's normal clock, so these values are real
/// wall-clock time and BaaS token validation agrees with the device.
/// </summary>
public static class PokelandClock
{
    public static DateTime UtcNow => DateTime.UtcNow;
    public static DateTimeOffset UtcNowOffset => DateTimeOffset.UtcNow;
}
