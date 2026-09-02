#nullable disable
using System.Collections.Concurrent;

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
    /// The PPEDrop id StartStage most recently offered for this run, paired
    /// with the species/level it described, so EndStage can convert it into
    /// a real owned PPE on a clear without re-deriving what was offered.
    /// </summary>
    public long? OfferedPPEDropId { get; set; }
    public int OfferedMonsNo { get; set; }
    public int OfferedLevel { get; set; }
    public int OfferedGrade { get; set; }
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
}
