#nullable disable
using System.Collections.Concurrent;
using System.Collections.Generic;

namespace Pokeland.Server;

/// <summary>One logged-in client. Handed to every endpoint handler.</summary>
public sealed class GameSession
{
    public string SessionId { get; init; }
    public string BaaSUserId { get; init; }
    public string Market { get; set; }
    public string AppVer { get; set; }
    public string AssetVer { get; set; }
    public int TimeZoneOffsetMinutes { get; set; }

    /// <summary>
    /// Mirrors <c>Uskumru.Proto.Base.Req.Rev</c>. The client sends the revision it
    /// last saw and refuses the response as an <c>UskumruRevMismatch</c> if the
    /// server's view has moved on unexpectedly, so it has to be tracked per session.
    ///
    /// It also gates whether the client processes the response body at all: the
    /// decompiled <c>ClientTask.&lt;iWait&gt;d__12.MoveNext</c> only runs the
    /// Cache-update path when <c>Res.Rev != 0</c> - a value of 0 is read as "no
    /// change, nothing to apply" and the entire Reset/AutoRes payload is silently
    /// dropped. Starts at 1, not 0, so the very first Login response is not mistaken
    /// for a no-op.
    /// </summary>
    public int Rev = 1;

    public DateTime LastSeenUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// The island a StartStage most recently sent an <c>MHM</c> for, so
    /// EndStage (whose Req carries no StageCode at all - see
    /// EndStage.g.cs) knows which stage's ClearCount to bump instead of
    /// always crediting the fixed mysland stage.
    /// </summary>
    public int? CurrentIslandID { get; set; }

    /// <summary>
    /// The PPEDrop(s) StartStage most recently offered for this run, paired
    /// with the species/level each one described, so EndStage can convert
    /// them into real owned PPEs on a clear without re-deriving what was
    /// offered. Normally just one entry; the set02 ZakuZaku purchase's
    /// temporary Drop-subscription buff (see PlayerStore.ActivatePurchase/
    /// DropBonusExpiresUtc) doubles this to three for its duration.
    /// </summary>
    public List<(long DropId, int MonsNo, int Level, int Grade)> OfferedDrops { get; set; } = new();
}

public sealed class SessionStore
{
    private readonly ConcurrentDictionary<string, GameSession> _sessions = new();

    public GameSession Create(string baasUserId)
    {
        var s = new GameSession
        {
            SessionId = Guid.NewGuid().ToString("N"),
            BaaSUserId = baasUserId ?? "anonymous",
        };
        _sessions[s.SessionId] = s;
        return s;
    }

    public GameSession Get(string sessionId)
    {
        if (string.IsNullOrEmpty(sessionId)) return null;
        if (!_sessions.TryGetValue(sessionId, out var s)) return null;
        s.LastSeenUtc = DateTime.UtcNow;
        return s;
    }

    /// <summary>
    /// Re-binds a session id this store no longer knows - a server restart
    /// wipes in-memory sessions, so any run in flight at deploy time comes
    /// back with a dead id - to a fresh transient session for the same
    /// device. Binding under the requested id (rather than minting a new
    /// one) makes the client's retries converge on one session instead of
    /// minting one per attempt. Run-scoped state (CurrentIslandID,
    /// OfferedDrops) is gone - handlers fall back to the mysland stage and
    /// empty drops - but wallet/chest rewards still bank instead of the
    /// client looping on 401 forever.
    /// </summary>
    public GameSession Reattach(string sessionId, string baasUserId)
    {
        var s = new GameSession
        {
            SessionId = string.IsNullOrEmpty(sessionId) ? Guid.NewGuid().ToString("N") : sessionId,
            BaaSUserId = baasUserId ?? "anonymous",
        };
        _sessions[s.SessionId] = s;
        return s;
    }
}
