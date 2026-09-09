#nullable disable
using System.Globalization;

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
///
/// FOUND (2026-08-31): the original version anchored the offset to
/// `DateTime.UtcNow` read at static-init, i.e. whenever the server process
/// happened to start. The device's clock keeps ticking forward in real time
/// from whatever moment it was last `adb shell date`-set, completely
/// independent of the server process's lifetime - so every server restart
/// silently re-zeroed the offset while the device clock had already drifted
/// minutes ahead, reintroducing the exact clock-mismatch this class exists
/// to prevent. Anchoring to values captured when the device clock is set keeps
/// the offset stable across restarts. tools/bringup.sh obtains those values
/// from tools/sync_device_clock.sh and exports them for this process.
/// </summary>
public static class PokelandClock
{
    // 2020-06-20T20:00:00Z matches the `adb shell date 062020002020.00` used
    // to set the emulator's clock - comfortably before the 2020-07-22 EOS cutoff.
    private static readonly DateTime DeviceEpoch = ReadUtc(
        "POKELAND_DEVICE_EPOCH",
        new DateTime(2020, 6, 20, 20, 0, 0, DateTimeKind.Utc));

    // Fallback matches the most recent checked-in emulator sync. Normal local
    // operation uses the environment value emitted by sync_device_clock.sh.
    private static readonly DateTime RealAnchor = ReadUtc(
        "POKELAND_REAL_ANCHOR",
        new DateTime(2026, 9, 8, 23, 1, 51, DateTimeKind.Utc));

    public static DateTime UtcNow => DeviceEpoch + (DateTime.UtcNow - RealAnchor);
    public static DateTimeOffset UtcNowOffset => new(UtcNow, TimeSpan.Zero);

    private static DateTime ReadUtc(string variable, DateTime fallback)
    {
        var value = Environment.GetEnvironmentVariable(variable);
        if (string.IsNullOrWhiteSpace(value))
            return fallback;

        if (DateTime.TryParseExact(
                value,
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                CultureInfo.InvariantCulture,
                DateTimeStyles.AssumeUniversal | DateTimeStyles.AdjustToUniversal,
                out var parsed))
            return DateTime.SpecifyKind(parsed, DateTimeKind.Utc);

        throw new InvalidOperationException(
            $"{variable} must use UTC format yyyy-MM-ddTHH:mm:ssZ; got {value}");
    }
}
