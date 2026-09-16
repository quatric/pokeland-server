#nullable disable
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// OIDC client for the Authentik instance at auth.pokewii.net that
/// <see cref="NaPortal"/> bounces the player's external browser to for the
/// Nintendo Account link/restore flow. Plain Authorization Code exchange -
/// no PKCE on this leg, since it is a direct server-to-server hop over TLS
/// to Authentik itself (the SDK's own PKCE handshake, which this class knows
/// nothing about, is handled entirely inside NaPortal).
///
/// The only thing anything downstream reads back is the Authentik user's
/// stable "sub", which becomes the account id <see cref="AccountLinkStore"/>
/// keys saves under. Fetched from Authentik's userinfo endpoint with the
/// freshly-exchanged access token rather than by verifying the id_token's
/// RS256 signature - the round trip is already an authenticated HTTPS
/// connection to Authentik, so there is nothing a signature check would add.
/// </summary>
public sealed class AuthentikClient
{
    private readonly HttpClient _http = new();
    private readonly string _issuer;
    private readonly string _clientId;
    private readonly string _clientSecret;
    private readonly string _redirectUri;

    public AuthentikClient(string issuer, string clientId, string clientSecret, string redirectUri)
    {
        _issuer = issuer.TrimEnd('/');
        _clientId = clientId;
        _clientSecret = clientSecret;
        _redirectUri = redirectUri;
    }

    public string BuildAuthorizeUrl(string state) =>
        $"{_issuer}/application/o/authorize/?client_id={Uri.EscapeDataString(_clientId)}" +
        $"&redirect_uri={Uri.EscapeDataString(_redirectUri)}" +
        "&response_type=code&scope=openid" +
        $"&state={Uri.EscapeDataString(state)}";

    /// <summary>Exchanges an authorization code Authentik just issued for
    /// the account id ("sub") behind it. Returns null on any failure - an
    /// expired/replayed code or a misconfigured client secret is reported to
    /// the SDK the same way NaPortal already reports "no dokihan session":
    /// as a failed link attempt, not a crash.</summary>
    public async Task<string> ExchangeCodeForAccountIdAsync(string code)
    {
        using var tokenReq = new HttpRequestMessage(HttpMethod.Post, $"{_issuer}/application/o/token/")
        {
            Content = new FormUrlEncodedContent(new Dictionary<string, string>
            {
                ["grant_type"] = "authorization_code",
                ["code"] = code,
                ["redirect_uri"] = _redirectUri,
                ["client_id"] = _clientId,
                ["client_secret"] = _clientSecret,
            }),
        };

        using var tokenRes = await _http.SendAsync(tokenReq);
        if (!tokenRes.IsSuccessStatusCode) return null;

        var tokenBody = JObject.Parse(await tokenRes.Content.ReadAsStringAsync());
        var accessToken = tokenBody.Value<string>("access_token");
        if (string.IsNullOrEmpty(accessToken)) return null;

        using var infoReq = new HttpRequestMessage(HttpMethod.Get, $"{_issuer}/application/o/userinfo/");
        infoReq.Headers.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", accessToken);

        using var infoRes = await _http.SendAsync(infoReq);
        if (!infoRes.IsSuccessStatusCode) return null;

        var info = JObject.Parse(await infoRes.Content.ReadAsStringAsync());
        return info.Value<string>("sub");
    }
}
