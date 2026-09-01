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
///
/// FOUND (2026-08-31): the original version anchored the offset to
/// `DateTime.UtcNow` read at static-init, i.e. whenever the server process
/// happened to start. The device's clock keeps ticking forward in real time
/// from whatever moment it was last `adb shell date`-set, completely
/// independent of the server process's lifetime - so every server restart
/// silently re-zeroed the offset while the device clock had already drifted
/// minutes ahead, reintroducing the exact clock-mismatch this class exists
/// to prevent. Anchoring to a fixed constant instead of a runtime value
/// keeps the offset stable across restarts, as long as the device clock was
/// set (via `adb shell date`) to DeviceEpoch at a moment when the real clock
/// read RealAnchor - see tools/sync_device_clock.sh.
/// </summary>
public static class PokelandClock
{
    // 2020-06-20T20:00:00Z matches the `adb shell date 062020002020.00` used
    // to set the emulator's clock - comfortably before the 2020-07-22 EOS cutoff.
    private static readonly DateTime DeviceEpoch = new(2020, 6, 20, 20, 0, 0, DateTimeKind.Utc);

    // The real wall-clock instant the device was set to DeviceEpoch. Must be
    // updated (and the device re-synced) together - see
    // tools/sync_device_clock.sh, which prints the exact `adb shell date`
    // invocation and the RealAnchor line to paste in here.
    private static readonly DateTime RealAnchor = new(2026, 9, 1, 13, 35, 2, DateTimeKind.Utc);

    public static DateTime UtcNow => DeviceEpoch + (DateTime.UtcNow - RealAnchor);
    public static DateTimeOffset UtcNowOffset => new(UtcNow, TimeSpan.Zero);
}
