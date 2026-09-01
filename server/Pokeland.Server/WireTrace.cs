namespace Pokeland.Server;

/// <summary>
/// Dumps each /1.600/game request and its response to POKELAND_WIRE_DIR, one
/// numbered file per exchange. Diagnostic only - the client gives no feedback
/// when it quietly discards a response, so having the exact bytes on disk is
/// the difference between reading the bug and guessing at it.
/// </summary>
public static class WireTrace
{
    private static readonly string Dir = Environment.GetEnvironmentVariable("POKELAND_WIRE_DIR");
    private static int _seq;

    public static void Write(string req, string res)
    {
        if (string.IsNullOrEmpty(Dir)) return;
        Directory.CreateDirectory(Dir);
        var n = Interlocked.Increment(ref _seq);
        var ep = "unknown";
        try { ep = Newtonsoft.Json.Linq.JObject.Parse(req).Value<string>("Endpoint") ?? ep; }
        catch { /* keep the dump even when the request is unparseable */ }
        File.WriteAllText(Path.Combine(Dir, $"{n:D3}-{ep}.req.json"), req);
        File.WriteAllText(Path.Combine(Dir, $"{n:D3}-{ep}.res.json"), res);
    }
}
