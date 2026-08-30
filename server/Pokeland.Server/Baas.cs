#nullable disable
using System.Security.Cryptography;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// Stand-in for Nintendo's BaaS (Baked-as-a-Service) account backend.
///
/// The client cannot reach <c>Login</c> until the NPF SDK has authenticated a
/// device account and obtained a bearer token, so a revival deployment needs
/// something answering the BaaS gateway. The shapes here were recovered by
/// decompiling <c>com.nintendo.npf.sdk</c> out of classes.dex - specifically
/// BaaSAuth, CoreHttpClient and BaasUserMapper.
///
/// The SDK normally forces https, but honours <c>"useHttp": true</c> in
/// assets/npf.json, which is what lets this run as plain HTTP alongside the
/// game API. Point <c>baasHost</c> at this server and it takes over.
/// </summary>
public static class Baas
{
    public static void MapBaas(this WebApplication app, ILogger log)
    {
        // POST /core/v1/gateway/sdk/login       - device-account auth
        // POST /core/v1/gateway/sdk/federation  - the same, with a linked NA
        foreach (var kind in new[] { "login", "federation" })
            app.MapPost($"/core/v1/gateway/sdk/{kind}", Handler(kind, log));
        MapRest(app);
    }

    private static Delegate Handler(string kind, ILogger log)
    {
        return async (HttpContext http) =>
        {
            using var reader = new StreamReader(http.Request.Body);
            var body = await reader.ReadToEndAsync();

            JObject req;
            try { req = JObject.Parse(body); }
            catch { req = new JObject(); }

            // The SDK sends an existing deviceAccount when it has one, and
            // nothing on a first run - in which case it expects the server to
            // mint one and hand it back as createdDeviceAccount.
            var device = req["deviceAccount"] as JObject;
            var deviceId = device?.Value<string>("id");
            var isNew = string.IsNullOrEmpty(deviceId);
            if (isNew)
                deviceId = Guid.NewGuid().ToString("N");

            // Derive a stable user id from the device id so a returning device
            // keeps its account across restarts.
            var userId = StableId(deviceId);
            log.LogInformation("baas/{Kind}: device={Device} user={User} new={New}",
                kind, deviceId, userId, isNew);

            var now = DateTimeOffset.UtcNow;
            var res = new JObject
            {
                ["user"] = new JObject
                {
                    ["id"] = userId,
                    ["nickname"] = "",
                    ["country"] = "JP",
                    ["gender"] = "male",
                    ["birthday"] = "2000-01-01",
                    ["createdAt"] = now.ToUnixTimeSeconds(),
                    ["hasUnreadCsComment"] = false,
                    ["permissions"] = new JObject
                    {
                        ["personalAnalytics"] = true,
                        ["personalAnalyticsUpdatedAt"] = now.ToUnixTimeSeconds(),
                        ["personalNotification"] = true,
                        ["personalNotificationUpdatedAt"] = now.ToUnixTimeSeconds(),
                    },
                    // BaaSAuth reads user.links unconditionally, so it must exist.
                    // Leaving out "nintendoAccount" marks the user as unlinked.
                    ["links"] = new JObject(),
                },
                ["accessToken"] = Token(userId, "access"),
                ["idToken"] = IdToken(userId, deviceId, now),
                ["expiresIn"] = 86400,
                ["sessionId"] = Guid.NewGuid().ToString("N"),
                ["market"] = "GOOGLE",
            };

            if (isNew)
            {
                res["createdDeviceAccount"] = new JObject
                {
                    ["id"] = deviceId,
                    // Persisted by the client and replayed on later logins. Nothing
                    // verifies it here; it only has to round-trip.
                    ["password"] = Guid.NewGuid().ToString("N"),
                };
            }

            return Results.Content(res.ToString(Newtonsoft.Json.Formatting.None),
                "application/json");
        };
    }

    private static void MapRest(WebApplication app)
    {
        // Profile fetch/update. The game does not depend on these, but the SDK
        // calls them, and a 404 surfaces as an NPFError.
        app.MapMethods("/core/v1/users/{id}", new[] { "GET", "POST", "PATCH", "PUT" },
            (string id) => Results.Content(
                new JObject
                {
                    ["id"] = id,
                    ["nickname"] = "",
                    ["country"] = "JP",
                    ["links"] = new JObject(),
                }.ToString(Newtonsoft.Json.Formatting.None), "application/json"));

        app.MapGet("/core/v1/users", () => Results.Content("[]", "application/json"));

        // Analytics sinks - accept and discard.
        app.MapPost("/core/v1/analytics/events", () => Results.Ok());
        app.MapGet("/core/v1/analytics/events/config",
            () => Results.Content("{}", "application/json"));
    }

    /// <summary>Deterministic 32-hex id derived from the device account id.</summary>
    private static string StableId(string deviceId)
    {
        var hash = SHA256.HashData(Encoding.UTF8.GetBytes("pokeland:" + deviceId));
        return Convert.ToHexString(hash, 0, 16).ToLowerInvariant();
    }

    /// <summary>
    /// An opaque bearer token. Only echoed back in the Authorization header,
    /// so an unforgeable-looking random string is enough for a private
    /// deployment. Unlike idToken, nothing on the client parses this one.
    /// </summary>
    private static string Token(string userId, string kind)
    {
        var raw = $"{userId}:{kind}:{Guid.NewGuid():N}";
        return Convert.ToBase64String(Encoding.UTF8.GetBytes(raw))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
    }

    /// <summary>
    /// idToken is NOT opaque - NPFBaaSUserIdToken..ctor (decompiled) splits it
    /// as a JWT and JsonUtility.FromJson's the header and payload segments
    /// into NPFBaaSUserIdTokenHeader/Payload. Those two classes' field sets
    /// are the contract; the signature segment is never verified client-side
    /// (no crypto call in the ctor), so it can be anything JWT-shaped.
    /// </summary>
    private static string IdToken(string userId, string deviceId, DateTimeOffset now)
    {
        var header = new JObject
        {
            ["alg"] = "none",
            ["jku"] = "",
            ["kid"] = "",
        };
        var payload = new JObject
        {
            ["exp"] = now.AddDays(1).ToUnixTimeSeconds(),
            ["sub"] = userId,
            ["aud"] = "pokeland",
            ["iss"] = "pokeland-revival",
            ["bs_did"] = deviceId,
            ["jti"] = Guid.NewGuid().ToString("N"),
            ["typ"] = "id",
            ["iat"] = now.ToUnixTimeSeconds(),
        };
        return $"{B64(header)}.{B64(payload)}.";
    }

    private static string B64(JObject obj) =>
        Convert.ToBase64String(Encoding.UTF8.GetBytes(obj.ToString(Newtonsoft.Json.Formatting.None)))
            .TrimEnd('=').Replace('+', '-').Replace('/', '_');
}
