#nullable disable
using System.Security.Cryptography;
using System.Text;
using Microsoft.AspNetCore.Http.Extensions;
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// Serves the NPF SDK's Nintendo Account link/switch flow - the endpoints
/// assets/npf.json's accountHost and accountApiHost point at once
/// tools/patch_npf.py --account-host is used. Recovered by
/// decompiling this exact APK build's com.nintendo.npf.sdk - none of this is
/// public API documentation, it is the literal request/response shape this
/// SDK build sends and expects. Three calls, in order:
///
///   1. GET {accountHost}/connect/1.0.0/authorize?state=...&amp;redirect_uri=
///      npf{clientId}://auth&amp;client_id={clientId}&amp;...&amp;
///      response_type=session_token_code&amp;session_token_code_challenge=...&amp;
///      session_token_code_challenge_method=S256
///
///      Opened in an EXTERNAL BROWSER, which is what makes bouncing through
///      Authentik's own login page (auth.pokewii.net) possible: a WebView
///      would not carry Authentik's session cookie into that request, an
///      external browser does. redirect_uri is a custom URI scheme
///      (npf&lt;clientId&gt;://auth) - Android intercepts navigation to it and
///      hands control back to the app; this server never receives that
///      final redirect, only ever issues it.
///
///   2. POST {accountHost}/connect/1.0.0/api/session_token
///      Content-Type: application/x-www-form-urlencoded
///      Body: client_id={clientId}&amp;session_token_code={code}&amp;
///            session_token_code_verifier={verifier}
///      -&gt; {"session_token": "..."}
///
///   3. POST {accountApiHost}/1.0.0/gateway/sdk/token
///      Content-Type: application/json
///      Body: {"client_id": "...", "session_token": "..."}
///      -&gt; {"accessToken": "...", "idToken": "...", "expiresIn": N,
///          "user": {"id": "...", "nickname": "...", ...}}
///
///      This idToken is what Baas.cs's federation handler reads "sub" out
///      of to resolve which Authentik account just linked.
///
/// tools/patch_npf.py points both accountHost and accountApiHost at
/// baas.pokewii.net, so all three calls land in this one place.
///
/// Between step 1 and step 2 there is now a fourth, internal hop: this
/// server's own authorize handler redirects the external browser on to
/// Authentik (<see cref="AuthentikClient"/>) instead of trusting a cookie of
/// its own, and only mints the session_token_code once Authentik's callback
/// hands back a verified account id. See <see cref="OidcCallback"/>.
///
/// State for the in-flight exchange steps is process-memory only (a dict,
/// not a file) - a server restart mid-link just means the player retries the
/// browser round-trip; nothing about an already-completed link (which lives
/// in AccountLinkStore, durable) is at risk.
/// </summary>
public static class NaPortal
{
    /// <summary>How long a session_token_code (or an in-flight Authentik
    /// hop) survives - generous for a human doing a browser round-trip,
    /// tight enough that a leaked/logged code isn't useful for long.</summary>
    private const int CodeTtlSeconds = 300;

    private sealed record PendingCode(string AccountId, string CodeChallenge, DateTime ExpiresAtUtc);

    /// <summary>What the SDK's own authorize request looked like, kept
    /// around while the browser is off at Authentik so <see cref="OidcCallback"/>
    /// can resume exactly where <see cref="Authorize"/> left off.</summary>
    private sealed record PendingAuthentikState(string SdkState, string RedirectUri, string CodeChallenge, DateTime ExpiresAtUtc);

    private static readonly object Gate = new();

    /// <summary>session_token_code -&gt; pending exchange state. Single-use -
    /// popped at the session_token exchange.</summary>
    private static readonly Dictionary<string, PendingCode> PendingCodes = new();

    /// <summary>Our own opaque state value (passed as Authentik's "state")
    /// -&gt; the SDK request it stands in for. Single-use - popped at the
    /// OIDC callback.</summary>
    private static readonly Dictionary<string, PendingAuthentikState> PendingAuthentikStates = new();

    public static void MapNaPortal(this WebApplication app, AuthentikClient authentik, ILogger log)
    {
        app.MapGet("/connect/1.0.0/authorize",
            (Func<HttpContext, Task<IResult>>)(http => Authorize(http, authentik, log)));
        app.MapGet("/na-portal/oidc-callback",
            (Func<HttpContext, Task<IResult>>)(http => OidcCallback(http, authentik, log)));
        app.MapPost("/connect/1.0.0/api/session_token",
            (Func<HttpContext, Task<IResult>>)(http => SessionTokenEndpoint(http, log)));
        app.MapPost("/1.0.0/gateway/sdk/token",
            (Func<HttpContext, Task<IResult>>)(http => GatewayToken(http, log)));
    }

    private static Task<IResult> Authorize(HttpContext http, AuthentikClient authentik, ILogger log)
    {
        var query = http.Request.Query;
        var state = query["state"].ToString();
        var redirectUri = query["redirect_uri"].ToString();
        var clientId = query["client_id"].ToString();
        var codeChallenge = query["session_token_code_challenge"].ToString();
        var codeChallengeMethod = query["session_token_code_challenge_method"].ToString();
        var responseType = query["response_type"].ToString();

        var expectedRedirect = $"npf{clientId}://auth";
        if (string.IsNullOrEmpty(clientId) || string.IsNullOrEmpty(codeChallenge)
            || redirectUri != expectedRedirect || responseType != "session_token_code"
            || codeChallengeMethod != "S256")
        {
            log.LogWarning("na_portal/authorize: malformed request (client_id={ClientId} redirect_uri={RedirectUri})",
                clientId, redirectUri);
            return Task.FromResult(Results.StatusCode(StatusCodes.Status400BadRequest));
        }

        // Stash the SDK's own request behind an opaque state value and hand
        // the browser off to Authentik. OidcCallback picks this back up once
        // Authentik reports who logged in.
        var akState = Convert.ToBase64String(RandomNumberGenerator.GetBytes(24))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
        lock (Gate)
        {
            PruneExpired();
            PendingAuthentikStates[akState] = new PendingAuthentikState(
                state, redirectUri, codeChallenge, DateTime.UtcNow.AddSeconds(CodeTtlSeconds));
        }
        log.LogInformation("na_portal/authorize: bouncing to Authentik for client {ClientId}", clientId);

        return Task.FromResult(Results.Redirect(authentik.BuildAuthorizeUrl(akState), permanent: false));
    }

    private static async Task<IResult> OidcCallback(HttpContext http, AuthentikClient authentik, ILogger log)
    {
        var query = http.Request.Query;
        var akState = query["state"].ToString();
        var code = query["code"].ToString();

        PendingAuthentikState pending;
        lock (Gate)
        {
            PendingAuthentikStates.Remove(akState, out pending);
        }

        if (pending is null || pending.ExpiresAtUtc < DateTime.UtcNow)
        {
            log.LogWarning("na_portal/oidc-callback: unknown or expired state");
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        if (string.IsNullOrEmpty(code))
        {
            log.LogWarning("na_portal/oidc-callback: Authentik returned no code (error={Error})",
                query["error"].ToString());
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        var accountId = await authentik.ExchangeCodeForAccountIdAsync(code);
        if (string.IsNullOrEmpty(accountId))
        {
            log.LogWarning("na_portal/oidc-callback: Authentik code exchange failed");
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        var sdkCode = Convert.ToBase64String(RandomNumberGenerator.GetBytes(24))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
        lock (Gate)
        {
            PruneExpired();
            PendingCodes[sdkCode] = new PendingCode(accountId, pending.CodeChallenge, DateTime.UtcNow.AddSeconds(CodeTtlSeconds));
        }
        log.LogInformation("na_portal/oidc-callback: Authentik account {AccountId} authorized, code minted", accountId);

        // redirectUri is the custom URI scheme (npf{clientId}://auth) -
        // Android intercepts this navigation and hands control back to the
        // app; this server never receives it.
        var fragment = $"state={Uri.EscapeDataString(pending.SdkState)}&session_token_code={Uri.EscapeDataString(sdkCode)}";
        return Results.Redirect($"{pending.RedirectUri}#{fragment}", permanent: false);
    }

    private static async Task<IResult> SessionTokenEndpoint(HttpContext http, ILogger log)
    {
        var form = await http.Request.ReadFormAsync();
        var code = form["session_token_code"].ToString();
        var verifier = form["session_token_code_verifier"].ToString();

        PendingCode pending;
        lock (Gate)
        {
            PendingCodes.Remove(code, out pending);
        }

        if (pending is null || pending.ExpiresAtUtc < DateTime.UtcNow)
        {
            log.LogWarning("na_portal/session_token: unknown or expired code");
            return InvalidGrant();
        }

        // PKCE S256: the verifier the client held onto since authorize must
        // hash to the challenge it sent then.
        var challenge = Base64UrlNoPad(SHA256.HashData(Encoding.UTF8.GetBytes(verifier)));
        if (!CryptographicOperations.FixedTimeEquals(
                Encoding.UTF8.GetBytes(challenge), Encoding.UTF8.GetBytes(pending.CodeChallenge)))
        {
            log.LogWarning("na_portal/session_token: PKCE verifier mismatch");
            return InvalidGrant();
        }

        // Stateless on purpose - see PendingCodes above. Nothing but
        // GatewayToken below ever reads this, and it only needs the
        // account id back, so the token carries it rather than indexing a
        // dict that a restart would empty.
        var token = FakeJwt.IdToken(pending.AccountId, pending.AccountId, DateTimeOffset.UtcNow);
        return JsonResult(new JObject { ["session_token"] = token });
    }

    private static async Task<IResult> GatewayToken(HttpContext http, ILogger log)
    {
        using var reader = new StreamReader(http.Request.Body);
        JObject body;
        try { body = JObject.Parse(await reader.ReadToEndAsync()); }
        catch { body = new JObject(); }

        var sessionToken = body.Value<string>("session_token");
        var accountId = string.IsNullOrEmpty(sessionToken) ? null : FakeJwt.DecodeSub(sessionToken);
        if (string.IsNullOrEmpty(accountId))
        {
            log.LogWarning("na_portal/gateway_token: unreadable session_token");
            return JsonResult(new JObject
            {
                ["error"] = new JObject { ["errorCode"] = 401, ["errorMessage"] = new JObject { ["detail"] = "invalid_token" } },
            }, StatusCodes.Status401Unauthorized);
        }

        var now = DateTimeOffset.UtcNow;
        // Read back by Baas.cs's federation handler via FakeJwt.DecodeSub -
        // "sub" here is the Authentik account id, not a real Nintendo
        // Account id. Nothing else (real or otherwise) ever verifies this
        // token.
        var idToken = FakeJwt.IdToken(accountId, accountId, now);

        return JsonResult(new JObject
        {
            ["accessToken"] = FakeJwt.OpaqueToken(accountId, "na-access"),
            ["idToken"] = idToken,
            ["expiresIn"] = 86400,
            ["user"] = new JObject { ["id"] = accountId, ["nickname"] = "" },
        });
    }

    private static void PruneExpired()
    {
        var now = DateTime.UtcNow;
        foreach (var key in PendingCodes.Where(kv => kv.Value.ExpiresAtUtc < now).Select(kv => kv.Key).ToList())
            PendingCodes.Remove(key);
        foreach (var key in PendingAuthentikStates.Where(kv => kv.Value.ExpiresAtUtc < now).Select(kv => kv.Key).ToList())
            PendingAuthentikStates.Remove(key);
    }

    private static IResult InvalidGrant() => JsonResult(new JObject
    {
        ["error"] = new JObject { ["errorCode"] = 400, ["errorMessage"] = new JObject { ["detail"] = "invalid_grant" } },
    }, StatusCodes.Status400BadRequest);

    private static IResult JsonResult(JObject obj, int status = StatusCodes.Status200OK) =>
        Results.Content(obj.ToString(Newtonsoft.Json.Formatting.None), "application/json", statusCode: status);

    private static string Base64UrlNoPad(byte[] raw) =>
        Convert.ToBase64String(raw).TrimEnd('=').Replace('+', '-').Replace('/', '_');
}
