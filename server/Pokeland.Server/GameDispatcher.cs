#nullable disable
using System.Collections.Generic;
using System.Reflection;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// A handler for one Uskumru endpoint. <see cref="Endpoint"/> must match the
/// namespace short-name in <c>Uskumru.Proto.&lt;Name&gt;</c>, which is exactly the
/// string the client puts in the request's <c>Endpoint</c> field.
/// </summary>
public interface IEndpointHandler
{
    string Endpoint { get; }
    object Handle(object req, GameSession session, DispatchContext ctx);
}

public sealed class DispatchContext
{
    public SessionStore Sessions { get; init; }
    public PlayerStore Players { get; init; }
    public ServerConfig Config { get; init; }
    public ILogger Log { get; init; }
}

/// <summary>
/// Routes a request to its handler. The wire format carries the endpoint name in
/// the body rather than the URL, so dispatch is: peek at <c>Endpoint</c>, look up
/// the generated <c>Req</c> type for that namespace, deserialize into it, run the
/// handler, then stamp the shared response envelope.
/// </summary>
public sealed class GameDispatcher
{
    private readonly Dictionary<string, IEndpointHandler> _handlers = new(StringComparer.Ordinal);
    private readonly Dictionary<string, (Type Req, Type Res)> _types = new(StringComparer.Ordinal);
    private readonly ILogger<GameDispatcher> _log;

    public IReadOnlyCollection<string> KnownEndpoints => _types.Keys;
    public IReadOnlyCollection<string> ImplementedEndpoints => _handlers.Keys;

    public GameDispatcher(IEnumerable<IEndpointHandler> handlers, ILogger<GameDispatcher> log)
    {
        _log = log;

        // Every endpoint is a namespace under Pokeland.Protocol holding Req + Res.
        var asm = typeof(Pokeland.Protocol.Base.Req).Assembly;
        const string root = "Pokeland.Protocol.";
        foreach (var t in asm.GetTypes())
        {
            if (t.Name != "Req" || t.Namespace is null || !t.Namespace.StartsWith(root)) continue;
            if (t.IsAbstract) continue;
            var name = t.Namespace[root.Length..];
            if (name.Contains('.')) continue;
            var res = asm.GetType($"{root}{name}.Res");
            if (res is not null) _types[name] = (t, res);
        }

        foreach (var h in handlers) _handlers[h.Endpoint] = h;

        _log.LogInformation("dispatcher: {Known} endpoints known, {Impl} implemented",
            _types.Count, _handlers.Count);
    }

    /// <summary>Result of a dispatch: the JSON body plus the status the client should see.</summary>
    public readonly record struct Result(string Body, int StatusCode);

    public Result Dispatch(string body, DispatchContext ctx)
    {
        JObject raw;
        try { raw = JObject.Parse(body); }
        catch (JsonException e) { throw new BadRequestException($"malformed JSON: {e.Message}"); }

        var endpoint = raw.Value<string>("Endpoint");
        if (string.IsNullOrEmpty(endpoint))
            throw new BadRequestException("request has no Endpoint field");

        if (!_types.TryGetValue(endpoint, out var pair))
            throw new BadRequestException($"unknown endpoint '{endpoint}'");

        var req = raw.ToObject(pair.Req, Json.Serializer);

        // Login is the only endpoint that runs without an established session.
        var sessionId = raw.Value<string>("SessionID");
        var session = ctx.Sessions.Get(sessionId);
        if (session is null && endpoint != "Login")
        {
            _log.LogWarning("{Endpoint}: no session for {SessionId}", endpoint, sessionId);
            // 401 is how the client is told to reinterpret the body as
            // Uskumru.Proto.Unauthorized.Res and bounce back to the title screen.
            return new Result(Json.Serialize(new Pokeland.Protocol.Unauthorized.Res
            {
                Reason = UnauthorizedReason.InvalidSession,
            }), StatusCodes.Status401Unauthorized);
        }

        // SetDoneFlag arrives two ways and both have to be caught here rather
        // than in a handler: as its own endpoint, and piggybacked onto any
        // other request (every Uskumru.Proto.BaseDF.Req carries a SetDoneFlag
        // field). Read off the raw JObject rather than the typed request,
        // because the two paths do not share a base class - SetDoneFlag.Req
        // derives from Base.Req and so has no SetDoneFlag property of its own,
        // which a typed `is BaseDF.Req` check silently misses.
        // Dropping these is what replayed the tutorial on every launch: the
        // client keeps no progress and believes whatever Login hands back.
        var diff = raw["SetDoneFlag"]?["DoneFlagDiff"]?.ToObject<DoneFlagDiff>(Json.Serializer);
        if (diff is not null && ctx.Players.Apply(diff))
            _log.LogInformation("{Endpoint}: DoneFlag +[{On}] -[{Off}]", endpoint,
                string.Join(",", diff.Ons ?? new List<DoneFlag>()),
                string.Join(",", diff.Offs ?? new List<DoneFlag>()));

        // RecordMissions rides along the same way SetDoneFlag does - it is a
        // field on every BaseDF.Req as well as an endpoint of its own - and is
        // read off the raw JObject for the same reason. The client counts
        // mission progress locally and only ever reports the running totals
        // here, so dropping these means every mission sits at zero forever and
        // nothing ever becomes redeemable.
        var commit = raw["RecordMissions"]?["MissionCommit"];
        if (commit is not null)
        {
            var ids = commit["IDs"]?.ToObject<List<int>>(Json.Serializer);
            var progresses = commit["Progresses"]?.ToObject<List<int>>(Json.Serializer);
            if (ctx.Players.ApplyMissions(ids, progresses))
                _log.LogInformation("{Endpoint}: mission progress {Progress}", endpoint,
                    string.Join(",", ids.Zip(progresses, (i, p) => $"{i}:{p}")));
        }

        object res;
        if (_handlers.TryGetValue(endpoint, out var handler))
        {
            res = handler.Handle(req, session, ctx);
        }
        else
        {
            // Not yet implemented: hand back a well-formed empty response so the
            // client keeps running instead of dropping into its error flow.
            _log.LogWarning("{Endpoint}: no handler, returning empty envelope", endpoint);
            res = Activator.CreateInstance(pair.Res);
        }

        // Null-fill every response, not just the unimplemented ones. A real
        // handler is just as likely to leave an envelope field null - the
        // response types are wide and mostly optional-looking - and the client
        // is no more tolerant of it there. StartStage was the case that showed
        // this: the handler filled in everything the endpoint is *about* (a
        // habitat map full of enemies) but left the inherited `A` (AutoRes[])
        // and CommitNonActiveSec at null, and the client parked forever inside
        // iRequestTask's continuation - no exception, no error dialog, just a
        // tutorial coroutine that never resumed and a fade that never lifted.
        FillEmptyCollections(res);

        // Login runs with session == null (no SessionID to look up yet) since
        // it's the one endpoint that creates the session rather than being
        // handed an existing one. Its Res carries the fresh SessionID, so look
        // the session back up through that before stamping - otherwise every
        // Login response stamps Rev as 0 from a null session, which the client
        // reads as "nothing to apply" and silently drops the whole payload.
        session ??= ctx.Sessions.Get(res.GetType().GetProperty("SessionID")?.GetValue(res) as string);

        StampEnvelope(res, session);
        return new Result(Json.Serialize(res), StatusCodes.Status200OK);
    }

    /// <summary>
    /// The client foreaches every List/array field it reads without a null check,
    /// so an empty envelope with `null` collections crashes it the moment it opens
    /// a scene that touches one. Recursively swap nulls for empty instances instead.
    /// </summary>
    private static void FillEmptyCollections(object obj, HashSet<object> seen = null)
    {
        if (obj is null) return;
        seen ??= new HashSet<object>(ReferenceEqualityComparer.Instance);
        if (!seen.Add(obj)) return;

        foreach (var prop in obj.GetType().GetProperties(BindingFlags.Public | BindingFlags.Instance))
        {
            if (!prop.CanRead || !prop.CanWrite || prop.GetIndexParameters().Length > 0) continue;
            var type = prop.PropertyType;
            var value = prop.GetValue(obj);

            if (value is null)
            {
                if (type.IsArray)
                    prop.SetValue(obj, Array.CreateInstance(type.GetElementType(), 0));
                else if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(List<>))
                    prop.SetValue(obj, Activator.CreateInstance(type));
                continue;
            }

            if (value is string) continue;
            if (type.Namespace?.StartsWith("Pokeland.Protocol") != true) continue;

            if (value is System.Collections.IEnumerable seq)
                foreach (var item in seq) FillEmptyCollections(item, seen);
            else
                FillEmptyCollections(value, seen);
        }
    }

    /// <summary>Fills the fields every response shares (<c>Uskumru.Proto.Base.BaseRes</c>).</summary>
    private static void StampEnvelope(object res, GameSession session)
    {
        if (res is not Pokeland.Protocol.Base.BaseRes b) return;
        b.Rev = session?.Rev ?? 0;
        // The client parses UTCStr with DateTime.Parse and uses it to drive every
        // timed system (stamina, chests, events), so it must be a real UTC stamp -
        // but it must also agree with the device's own clock, which a live
        // deployment has to keep rolled back to before 2020-07-22 to dodge the
        // client's hardcoded End-of-Service gate. Handing back the real (2026)
        // wall clock here while the device thinks it's 2020 produces a clock
        // mismatch the client flags as "Unable to connect" right after Login -
        // see Server.PokelandClock.
        b.UTCStr = PokelandClock.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
        b.A ??= Array.Empty<AutoRes>();
    }
}

public sealed class BadRequestException(string message) : Exception(message);

// Unity's JsonUtility (used by the client) has no base64 special-case for byte[] -
// it expects a plain JSON array of numbers. Newtonsoft's default byte[] converter
// writes base64 strings instead, which JsonUtility rejects with "Unexpected node type."
public sealed class ByteArrayAsNumberArrayConverter : JsonConverter
{
    public override bool CanConvert(Type objectType) => objectType == typeof(byte[]);

    public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
    {
        var bytes = (byte[])value;
        if (bytes is null) { writer.WriteNull(); return; }
        writer.WriteStartArray();
        foreach (var b in bytes) writer.WriteValue(b);
        writer.WriteEndArray();
    }

    public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
    {
        if (reader.TokenType == JsonToken.Null) return null;
        var list = serializer.Deserialize<List<byte>>(reader);
        return list?.ToArray() ?? Array.Empty<byte>();
    }
}

public static class Json
{
    public static readonly JsonSerializerSettings Settings = new()
    {
        NullValueHandling = NullValueHandling.Ignore,
        MissingMemberHandling = MissingMemberHandling.Ignore,
        DateParseHandling = DateParseHandling.None,
        Converters = { new ByteArrayAsNumberArrayConverter() },
    };

    public static readonly JsonSerializer Serializer = JsonSerializer.Create(Settings);

    public static string Serialize(object o) => JsonConvert.SerializeObject(o, Settings);
}
