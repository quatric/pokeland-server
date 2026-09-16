#nullable disable
using System.Security.Cryptography;

namespace Pokeland.Server;

/// <summary>
/// The shared login page at auth.pokewii.net/login?game=...&amp;redirect=... -
/// what NaPortal's Authorize bounces the browser to when there's no
/// established session yet, matching the exact URL shape the NPF SDK's own
/// dokihan-auth deployments use elsewhere, just served from pokewii.net
/// instead of dokihan.dev.
///
/// Backed by Authentik underneath (same as NaPortal used to do directly),
/// but the SDK-facing contract only ever sees this page and a plain
/// redirect back - it has no idea Authentik exists.
/// </summary>
public static class AuthLogin
{
    private const int StateTtlSeconds = 600;

    private sealed record Pending(string Redirect, DateTime ExpiresAtUtc);

    private static readonly object Gate = new();
    private static readonly Dictionary<string, Pending> PendingLogins = new();

    public static void MapAuthLogin(this WebApplication app, AuthentikClient authentik, ILogger log)
    {
        app.MapGet("/login", (HttpContext http) => Login(http, authentik, log));
        app.MapGet("/login/callback", (Func<HttpContext, Task<IResult>>)(http => Callback(http, authentik, log)));
    }

    private static IResult Login(HttpContext http, AuthentikClient authentik, ILogger log)
    {
        var redirect = http.Request.Query["redirect"].ToString();
        if (string.IsNullOrEmpty(redirect))
        {
            log.LogWarning("login: missing redirect param");
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        // Already logged in this browser (from an earlier link, possibly for
        // a different game under pokewii.net) - bounce straight back, no
        // Authentik round trip needed.
        var existing = PokewiiSession.AccountIdFor(http);
        if (existing is not null)
            return Results.Redirect(redirect, permanent: false);

        var state = Convert.ToBase64String(RandomNumberGenerator.GetBytes(24))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
        lock (Gate)
        {
            Prune();
            PendingLogins[state] = new Pending(redirect, DateTime.UtcNow.AddSeconds(StateTtlSeconds));
        }
        log.LogInformation("login: no session yet, bouncing to Authentik (game={Game})",
            http.Request.Query["game"].ToString());
        return Results.Redirect(authentik.BuildAuthorizeUrl(state), permanent: false);
    }

    private static async Task<IResult> Callback(HttpContext http, AuthentikClient authentik, ILogger log)
    {
        var state = http.Request.Query["state"].ToString();
        Pending pending;
        lock (Gate)
        {
            Prune();
            PendingLogins.Remove(state, out pending);
        }
        if (pending is null)
        {
            log.LogWarning("login/callback: unknown or expired state");
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        var code = http.Request.Query["code"].ToString();
        var accountId = string.IsNullOrEmpty(code) ? null : await authentik.ExchangeCodeForAccountIdAsync(code);
        if (string.IsNullOrEmpty(accountId))
        {
            log.LogWarning("login/callback: Authentik code exchange failed (error={Error})",
                http.Request.Query["error"].ToString());
            return Results.StatusCode(StatusCodes.Status400BadRequest);
        }

        PokewiiSession.Establish(http, accountId);
        log.LogInformation("login/callback: established session for {AccountId}", accountId);
        return Results.Redirect(pending.Redirect, permanent: false);
    }

    private static void Prune()
    {
        var now = DateTime.UtcNow;
        foreach (var k in PendingLogins.Where(kv => kv.Value.ExpiresAtUtc < now).Select(kv => kv.Key).ToList())
            PendingLogins.Remove(k);
    }
}
