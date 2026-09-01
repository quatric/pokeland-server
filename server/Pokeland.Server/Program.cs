#nullable disable
using Microsoft.Extensions.FileProviders;
using Pokeland.Protocol;
using Pokeland.Server;
using Pokeland.Server.Handlers;

var builder = WebApplication.CreateBuilder(args);
builder.Logging.AddSimpleConsole(o => o.TimestampFormat = "HH:mm:ss.fff ");

var config = new ServerConfig();
builder.Configuration.GetSection("Pokeland").Bind(config);
builder.Services.AddSingleton(config);
builder.Services.AddSingleton<SessionStore>();
builder.Services.AddSingleton<IEndpointHandler, LoginHandler>();
builder.Services.AddSingleton<IEndpointHandler, GetEndOfServiceInfosHandler>();
builder.Services.AddSingleton<IEndpointHandler, StartStageHandler>();
builder.Services.AddSingleton<GameDispatcher>();

var app = builder.Build();

var log = app.Services.GetRequiredService<ILoggerFactory>().CreateLogger("Pokeland");
var sessions = app.Services.GetRequiredService<SessionStore>();
var dispatcher = app.Services.GetRequiredService<GameDispatcher>();
var ctx = new DispatchContext { Sessions = sessions, Config = config, Log = log };

app.Use(async (http, next) =>
{
    await next();
    log.LogInformation("{Method} {Path}{Query} -> {Status}",
        http.Request.Method, http.Request.Path, http.Request.QueryString, http.Response.StatusCode);
});

// ------------------------------------------------------------ pokemon-webapi
// The client has a second backend, pokemon-webapi.appspot.com, separate from
// the game API. LocationChecker hits estimate_country during boot - before
// Login - and the country it returns becomes the CCmCode that
// SeldomInfoBox.IsEndOfService filters on. Until tools/patch_metadata.py
// learned to redirect this host too, boot died here on an HTTPS request to a
// host that no longer exists, which the client reported as "Unable to connect
// to the server" (Error ID U-DA39A3) without ever reaching /pre/AppManifest.
// The client probes with GET during bootstrap but does the real lookup with
// POST, so both verbs have to answer. A 405 on the POST reads to the client as
// a failed country lookup and stalls boot before it ever asks for AppManifest.
app.MapMethods("/api/location/v1/estimate_country", new[] { "GET", "POST" }, (HttpContext http) =>
{
    log.LogInformation("estimate_country -> {Country}", config.Country);
    return Results.Json(new
    {
        country = config.Country,
        countryCode = config.Country,
        result = config.Country,
        ResultValue = config.Country,
    });
});

// The profanity filter for user-entered names. Nothing is rejected: the retail
// word list is not part of the client, and an empty rejectedBy means "clean".
app.MapGet("/api/badword/v1/check_word", () => Results.Json(new
{
    result = "OK",
    rejectedBy = "",
}));

// ---------------------------------------------------------------- bootstrap
// GET /pre/AppManifest?market=GOOGLE&magic=<constant>
// The very first request the client makes. It answers "for your AppVer, which
// AssetVer should you download?" and gates the client on a supported version.
app.MapGet("/pre/AppManifest", (HttpContext http) =>
{
    var market = http.Request.Query["market"].ToString();
    var magic = http.Request.Query["magic"].ToString();

    if (config.RequireMagic &&
        !(config.Magic.TryGetValue(market, out var want) && magic == want))
    {
        log.LogWarning("AppManifest: bad magic {Magic} from {Ip}", magic, http.Connection.RemoteIpAddress);
        return Results.StatusCode(StatusCodes.Status403Forbidden);
    }

    log.LogInformation("AppManifest: market={Market}", market);

    var manifest = new AppManifest
    {
        AppManifestItems = config.AssetVersions
            .Select(kv => new AppManifestItem { AppVer = kv.Key, AssetVer = kv.Value })
            .ToArray(),
        Version = config.VersionString,
    };

    return Results.Content(Json.Serialize(manifest), "application/json");
});

// ------------------------------------------------------------------- the API
// POST /{version}/game - every one of the 61 endpoints goes through here; which
// one is named by the "Endpoint" field inside the body.
var gameHandler = async (string version, HttpContext http) =>
{
    if (version != config.ApiVersion)
    {
        log.LogWarning("game: unsupported protocol version {Version}", version);
        return Results.Content(
            Json.Serialize(new Pokeland.Protocol.Unauthorized.Res
            {
                Reason = UnauthorizedReason.ExpiredClient,
            }),
            "application/json", statusCode: StatusCodes.Status401Unauthorized);
    }

    using var reader = new StreamReader(http.Request.Body);
    var body = await reader.ReadToEndAsync();

    try
    {
        var result = dispatcher.Dispatch(body, ctx);
        // Wire trace. The client reports nothing when it silently rejects a
        // response - no exception, no retry - so the only way to tell a bad
        // payload from a stalled coroutine is to read both halves of the
        // exchange afterwards. Off unless POKELAND_WIRE_DIR is set.
        WireTrace.Write(body, result.Body);
        return Results.Content(result.Body, "application/json", statusCode: result.StatusCode);
    }
    catch (BadRequestException e)
    {
        log.LogWarning("game: {Message}", e.Message);
        return Results.StatusCode(StatusCodes.Status400BadRequest);
    }
    catch (Exception e)
    {
        log.LogError(e, "game: unhandled failure");
        // 503 makes the client show its maintenance screen rather than a hard error.
        return Results.Content(
            Json.Serialize(new Pokeland.Protocol.ServiceUnavailable.Res
            {
                Reason = ServiceUnavailableReason.TryAgainSomeTimeLater,
            }),
            "application/json", statusCode: StatusCodes.Status503ServiceUnavailable);
    }
};

app.MapPost("/{version}/game", gameHandler);
app.MapPost("/{version}/game/{endpoint}", async (string version, string endpoint, HttpContext http) =>
{
    // Some client builds put the endpoint name in the URL instead of the
    // body's "Endpoint" field. Normalize by injecting it before dispatch.
    using var reader = new StreamReader(http.Request.Body);
    var body = await reader.ReadToEndAsync();
    var obj = Newtonsoft.Json.Linq.JObject.Parse(string.IsNullOrWhiteSpace(body) ? "{}" : body);
    obj["Endpoint"] = endpoint;
    http.Request.Body = new MemoryStream(System.Text.Encoding.UTF8.GetBytes(obj.ToString(Newtonsoft.Json.Formatting.None)));
    http.Request.Body.Position = 0;
    return await gameHandler(version, http);
});

// -------------------------------------------------------------------- BaaS
// Nintendo's account backend, which the NPF SDK must authenticate against
// before the game will call Login at all.
app.MapBaas(log);

// --------------------------------------------------------------------- CDN
// Stands in for dl.app.pokeland.jp. The client builds asset URLs as
// <cdn>/pokeland/<AssetVer>/<Platform>/<bundle>, so serving the mirror tree at
// /pokeland lets one process back both hostnames.
var cdnRoot = Path.GetFullPath(config.CdnRoot, app.Environment.ContentRootPath);
if (Directory.Exists(cdnRoot))
{
    app.UseStaticFiles(new StaticFileOptions
    {
        FileProvider = new PhysicalFileProvider(cdnRoot),
        ServeUnknownFileTypes = true,       // asset bundles are extension-less
        DefaultContentType = "binary/octet-stream",
    });
    log.LogInformation("serving CDN mirror from {Root}", cdnRoot);
}
else
{
    log.LogWarning("CDN mirror not found at {Root} - asset requests will 404", cdnRoot);
}

// ------------------------------------------------------------------ status
app.MapGet("/_status", () => Results.Json(new
{
    apiVersion = config.ApiVersion,
    endpointsKnown = dispatcher.KnownEndpoints.Count,
    endpointsImplemented = dispatcher.ImplementedEndpoints.OrderBy(x => x),
    endpointsMissing = dispatcher.KnownEndpoints
        .Except(dispatcher.ImplementedEndpoints).OrderBy(x => x),
    cdnRoot,
    cdnPresent = Directory.Exists(cdnRoot),
}));

app.Run();
