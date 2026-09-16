#nullable disable
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// JWT-shaped-but-unsigned tokens, shared by <see cref="Baas"/> and
/// <see cref="NaPortal"/>.
///
/// Nothing here is cryptographically meaningful. The NPF SDK's idToken
/// parser (<c>NPFBaaSUserIdToken..ctor</c>, decompiled) just JSON-decodes the
/// header and payload segments with JsonUtility - it never verifies the
/// signature segment client-side - so an unforgeable-looking random string
/// for the opaque bearer tokens and a plain three-dot JWT shape for idToken
/// are exactly as much as a private deployment needs. See Baas.cs's class
/// doc for the fuller story.
/// </summary>
public static class FakeJwt
{
    /// <summary>
    /// An opaque bearer token. Only ever echoed back in the Authorization
    /// header, so an unforgeable-looking random string is enough for a
    /// private deployment. Unlike <see cref="IdToken"/>, nothing on the
    /// client parses this one.
    /// </summary>
    public static string OpaqueToken(string subject, string kind)
    {
        var raw = $"{subject}:{kind}:{Guid.NewGuid():N}";
        return Convert.ToBase64String(Encoding.UTF8.GetBytes(raw))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
    }

    /// <summary>
    /// <paramref name="subject"/> becomes the "sub" claim - the one field
    /// every reader of a token this project mints actually looks at
    /// (Program.cs's own bearer header, Baas.cs's federation handler
    /// decoding an idToken NaPortal minted). <paramref name="extraClaim"/>
    /// is carried as "bs_did" purely for parity with the real
    /// NPFBaaSUserIdTokenPayload shape; nothing reads it back.
    /// </summary>
    public static string IdToken(string subject, string extraClaim, DateTimeOffset now)
    {
        var header = new JObject { ["alg"] = "none", ["jku"] = "", ["kid"] = "" };
        var payload = new JObject
        {
            ["exp"] = now.AddDays(1).ToUnixTimeSeconds(),
            ["sub"] = subject,
            ["aud"] = "pokeland",
            ["iss"] = "pokeland-revival",
            ["bs_did"] = extraClaim,
            ["jti"] = Guid.NewGuid().ToString("N"),
            ["typ"] = "id",
            ["iat"] = now.ToUnixTimeSeconds(),
        };
        return $"{B64(header)}.{B64(payload)}.";
    }

    /// <summary>
    /// Pulls "sub" out of a bearer/idToken's payload segment - tolerant of a
    /// "Bearer " prefix (as the Authorization header carries it) or a bare
    /// token (as federation's idpAccount.idToken carries it), and of
    /// anything malformed, since a missing/invalid token just means "no
    /// identity", never an error worth failing a request over.
    /// </summary>
    public static string DecodeSub(string token)
    {
        if (string.IsNullOrEmpty(token)) return null;
        if (token.StartsWith("Bearer ", StringComparison.OrdinalIgnoreCase))
            token = token["Bearer ".Length..];
        try
        {
            var payloadSegment = token.Split('.')[1];
            var padded = payloadSegment.Replace('-', '+').Replace('_', '/');
            padded += (padded.Length % 4) switch { 2 => "==", 3 => "=", _ => "" };
            var raw = Encoding.UTF8.GetString(Convert.FromBase64String(padded));
            return JObject.Parse(raw).Value<string>("sub");
        }
        catch { return null; }
    }

    private static string B64(JObject obj) =>
        Convert.ToBase64String(Encoding.UTF8.GetBytes(obj.ToString(Formatting.None)))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
}
