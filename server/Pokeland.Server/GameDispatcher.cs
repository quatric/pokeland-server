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
            FillEmptyCollections(res);
        }

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
        // timed system (stamina, chests, events), so it must be a real UTC stamp.
        b.UTCStr = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
        b.A ??= Array.Empty<AutoRes>();
    }
}

public sealed class BadRequestException(string message) : Exception(message);

public static class Json
{
    public static readonly JsonSerializerSettings Settings = new()
    {
        NullValueHandling = NullValueHandling.Ignore,
        MissingMemberHandling = MissingMemberHandling.Ignore,
        DateParseHandling = DateParseHandling.None,
    };

    public static readonly JsonSerializer Serializer = JsonSerializer.Create(Settings);

    public static string Serialize(object o) => JsonConvert.SerializeObject(o, Settings);
}
