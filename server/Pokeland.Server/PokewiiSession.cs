#nullable disable
using System.Security.Cryptography;

namespace Pokeland.Server;

/// <summary>
/// The shared "logged in at auth.pokewii.net" session that <see cref="AuthLogin"/>
/// establishes and <see cref="NaPortal"/>'s Authorize handler (running on
/// baas.pokewii.net) reads back.
///
/// This only works because the cookie is issued for the shared parent domain
/// ".pokewii.net" rather than either subdomain specifically - auth.pokewii.net
/// and baas.pokewii.net are both subdomains of it, so a browser attaches the
/// cookie to requests on either host once it's set. That is the actual
/// mechanism a "full navigation back to the redirect URL" relies on: the
/// redirect crosses from auth.pokewii.net to baas.pokewii.net, and the
/// cookie rides along because they share a registrable domain.
/// </summary>
public static class PokewiiSession
{
    public const string CookieName = "pokewii_session";
    public const string CookieDomain = ".pokewii.net";
    private static readonly TimeSpan Ttl = TimeSpan.FromDays(30);

    private sealed record Entry(string AccountId, DateTime ExpiresAtUtc);

    private static readonly object Gate = new();
    private static readonly Dictionary<string, Entry> Sessions = new();

    public static string AccountIdFor(HttpContext http)
    {
        var token = http.Request.Cookies[CookieName];
        if (string.IsNullOrEmpty(token)) return null;
        lock (Gate)
        {
            if (Sessions.TryGetValue(token, out var entry) && entry.ExpiresAtUtc > DateTime.UtcNow)
                return entry.AccountId;
            return null;
        }
    }

    public static void Establish(HttpContext http, string accountId)
    {
        var token = Convert.ToBase64String(RandomNumberGenerator.GetBytes(32))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
        lock (Gate)
        {
            Prune();
            Sessions[token] = new Entry(accountId, DateTime.UtcNow.Add(Ttl));
        }
        http.Response.Cookies.Append(CookieName, token, new CookieOptions
        {
            Domain = CookieDomain,
            HttpOnly = true,
            Secure = true,
            SameSite = SameSiteMode.Lax,
            Expires = DateTimeOffset.UtcNow.Add(Ttl),
        });
    }

    private static void Prune()
    {
        var now = DateTime.UtcNow;
        foreach (var k in Sessions.Where(kv => kv.Value.ExpiresAtUtc < now).Select(kv => kv.Key).ToList())
            Sessions.Remove(k);
    }
}
