#nullable disable
namespace Pokeland.Server;

/// <summary>
/// A live deployment has to roll the Android device's clock back to before
/// 2020-07-22 to dodge the client's hardcoded End-of-Service check (there is
/// no dismiss button on that dialog - the check itself has to be defeated).
/// Every timestamp this server hands back (Login's UTCStr, BaaS token
/// issue/expiry times) has to agree with that rolled-back device clock, or
/// the client treats the mismatch as an untrusted/invalid server and fails
/// with a generic "Unable to connect" error right after a successful Login.
///
/// Pin server time to the same era instead of DateTime.UtcNow. The client
/// only cares that timestamps parse and move forward consistently, not that
/// they match a calendar date, so an offset from the real clock (rather than
/// a frozen instant) is enough to keep stamina/chest/event timers ticking.
/// </summary>
public static class PokelandClock
{
    // 2020-06-20T20:00:00Z matches the `adb shell date 062020002020.00` used
    // to set the emulator's clock - comfortably before the 2020-07-22 EOS cutoff.
    private static readonly DateTime DeviceEpoch = new(2020, 6, 20, 20, 0, 0, DateTimeKind.Utc);
    private static readonly DateTime ServerEpoch = DateTime.UtcNow;

    public static DateTime UtcNow => DeviceEpoch + (DateTime.UtcNow - ServerEpoch);
    public static DateTimeOffset UtcNowOffset => new(UtcNow, TimeSpan.Zero);
}
