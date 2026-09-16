#nullable disable
using System.Text;
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// Stand-in for Nintendo's BaaS (Baked-as-a-Service) account backend,
/// extended with real Nintendo Account federation backed by the Authentik
/// instance at auth.pokewii.net (<see cref="AuthentikClient"/>, <see
/// cref="NaPortal"/>).
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
///
/// Federation (<c>POST .../gateway/sdk/federation</c>) is the *other* thing
/// baasHost answers, called by the SDK right after NaPortal's token grant
/// completes - its body carries the same shape login's does, plus an
/// <c>idpAccount: {idp: "nintendoAccount", idToken: "..."}</c> the SDK just
/// obtained. That idToken's "sub" is the Authentik account id NaPortal
/// minted it for (NaPortal is the only other reader), which <see
/// cref="AccountLinkStore"/> resolves to a stable BaaS user id - the
/// mechanism that makes a save follow an Authentik account across devices
/// instead of staying pinned to whichever device linked it first.
/// </summary>
public static class Baas
{
    public static void MapBaas(this WebApplication app, AccountLinkStore links, ILogger log)
    {
        // POST /core/v1/gateway/sdk/login       - device-account auth
        // POST /core/v1/gateway/sdk/federation  - the same, with a linked NA
        foreach (var kind in new[] { "login", "federation" })
            app.MapPost($"/core/v1/gateway/sdk/{kind}", Handler(kind, links, log));
        MapUsersLink(app, links, log);
        MapRest(app, links);
    }

    private static Delegate Handler(string kind, AccountLinkStore links, ILogger log)
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

            // Federation additionally carries the Nintendo Account link the
            // device just completed via NaPortal. Its idToken's "sub" is the
            // Authentik account id (we minted the token; nothing else ever
            // reads it), which AccountLinkStore resolves to a stable BaaS
            // user id.
            var idpAccount = req["idpAccount"] as JObject;
            var naIdToken = idpAccount?.Value<string>("idToken");
            var accountId = string.IsNullOrEmpty(naIdToken) ? null : FakeJwt.DecodeSub(naIdToken);

            string userId;
            if (!string.IsNullOrEmpty(accountId))
            {
                userId = links.BaasUserIdFor(accountId);
                if (userId is null)
                {
                    // This account owns no save. Creating one here is wrong:
                    // the client has two distinct flows and they land on two
                    // different endpoints.
                    //
                    //   "link a Nintendo Account"    (Camp)      -> /core/v1/users/{id}/link
                    //   "restore an existing account" (settings) -> this, federation
                    //
                    // Federation is the *restore* half, so "no save for this
                    // account" is the answer it exists to give. Minting one
                    // instead means asking to restore an account you never
                    // had would silently create a brand new one, which is
                    // indistinguishable from the restore having worked.
                    // Linking belongs to /link and nowhere else.
                    if (kind == "federation")
                    {
                        log.LogInformation("baas/federation: Authentik account {AccountId} owns no save, " +
                            "reporting no account rather than creating one", accountId);
                        // BaaSAuth.b() parses this in-band error out of a 2xx
                        // body; errorMessage has to be a JSON *object*.
                        return Results.Content(new JObject
                        {
                            ["error"] = new JObject
                            {
                                ["errorCode"] = 404,
                                ["errorMessage"] = new JObject
                                {
                                    ["message"] = "no game account is linked to this Nintendo Account",
                                },
                            },
                        }.ToString(Newtonsoft.Json.Formatting.None), "application/json");
                    }
                    // A plain login that merely carries an idpAccount falls
                    // back to the device's own save without linking anything.
                    userId = StableId(deviceId);
                    accountId = null;
                }
                else
                {
                    // Restoring onto this device: remember the device's own
                    // save id as belonging to the account, so later plain
                    // logins (which carry no idpAccount at all) resolve here
                    // too.
                    links.Claim(StableId(deviceId), accountId);
                }
            }
            else
            {
                // A plain login carries no Nintendo Account, only the
                // deviceAccount, so the save starts out device-derived...
                userId = StableId(deviceId);
                // ...but if this device has ever linked, the account's
                // canonical save wins. Without this the link is decorative:
                // every login would keep loading whatever save the current
                // device maps to, so a reinstall (fresh deviceAccount ->
                // fresh user id) would silently strand the player on a new
                // save while their linked one sat there untouched. This is
                // the step that actually makes a save follow the account.
                accountId = links.AccountIdFor(userId);
                if (accountId is not null)
                {
                    var canonical = links.CanonicalForUser(userId);
                    if (canonical is not null && canonical != userId)
                    {
                        log.LogInformation("baas/{Kind}: device save {UserId} belongs to Authentik account {AccountId}, " +
                            "serving canonical save {Canonical}", kind, userId, accountId, canonical);
                        userId = canonical;
                    }
                }
            }

            log.LogInformation("baas/{Kind}: device={Device} user={User} new={New} linkedAccount={Account}",
                kind, deviceId, userId, isNew, accountId);

            // Must agree with the device clock or the SDK's timestamp/expiry
            // validation flags this token as untrusted.
            var now = PokelandClock.UtcNowOffset;
            var res = UserResponse(userId, accountId, deviceId, now);

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

            return Results.Content(res.ToString(Newtonsoft.Json.Formatting.None), "application/json");
        };
    }

    /// <summary>The full BaaS login/federation response envelope (user +
    /// tokens + session), as opposed to <see cref="UserObject"/> which is
    /// just the "user" sub-object reused by the REST routes.</summary>
    private static JObject UserResponse(string userId, string accountId, string deviceId, DateTimeOffset now)
    {
        return new JObject
        {
            // BaaSAuth reads user.links unconditionally, so it must exist.
            // Leaving out "nintendoAccount" marks the user as unlinked.
            ["user"] = UserObject(userId, accountId, now),
            ["accessToken"] = FakeJwt.OpaqueToken(userId, "access"),
            // bs_did carries the device id (parity with the real
            // NPFBaaSUserIdTokenPayload shape), not the Authentik account id -
            // "sub" (userId) is the only claim anything actually reads back.
            ["idToken"] = FakeJwt.IdToken(userId, deviceId, now),
            ["expiresIn"] = 86400,
            ["sessionId"] = Guid.NewGuid().ToString("N"),
            ["market"] = "GOOGLE",
        };
    }

    /// <summary>The BaaS user as BaasUserMapper.b() wants it. "id" and
    /// "createdAt" are read with getString/getLong and throw when absent;
    /// everything else is guarded by has() &amp;&amp; !isNull(). "permissions"
    /// is all-or-nothing - if the key is present, all four of its members are
    /// then read unguarded.</summary>
    private static JObject UserObject(string userId, string accountId, DateTimeOffset now)
    {
        var stamp = now.ToUnixTimeSeconds();
        return new JObject
        {
            ["id"] = userId,
            ["nickname"] = "",
            ["country"] = "JP",
            ["gender"] = "male",
            ["birthday"] = "2000-01-01",
            ["createdAt"] = stamp,
            ["hasUnreadCsComment"] = false,
            ["permissions"] = new JObject
            {
                ["personalAnalytics"] = true,
                ["personalAnalyticsUpdatedAt"] = stamp,
                ["personalNotification"] = true,
                ["personalNotificationUpdatedAt"] = stamp,
            },
            ["links"] = string.IsNullOrEmpty(accountId)
                ? new JObject()
                : new JObject { ["nintendoAccount"] = new JObject { ["id"] = accountId } },
        };
    }

    /// <summary>
    /// POST /core/v1/users/{id}/link - attach a Nintendo Account to a BaaS
    /// user. This, not federation, is how the client links an account: the
    /// SDK never calls gateway/sdk/federation for *initiating* a link.
    /// BaaSUser.linkNintendoAccount -&gt; BaasUserImpl -&gt; CoreHttpClient issues
    /// this once NaPortal's token grant has handed it an idToken.
    ///
    /// Two things differ from every other BaaS call, and both matter: the
    /// body is application/x-www-form-urlencoded ("idp=nintendoAccount&amp;
    /// idToken=&lt;jwt&gt;"), spliced in raw by CoreHttpClient
    /// (String.format, never percent-encoded) rather than percent-encoded -
    /// so it is parsed by hand below rather than through the framework's
    /// form parser, which would mangle a token containing '+' or '='; and
    /// the response is the *bare* user object, with no "user" wrapper.
    /// </summary>
    private static void MapUsersLink(WebApplication app, AccountLinkStore links, ILogger log)
    {
        app.MapPost("/core/v1/users/{userId}/link", async (string userId, HttpContext http) =>
        {
            using var reader = new StreamReader(http.Request.Body);
            var body = await reader.ReadToEndAsync();

            // Everything after the first "idToken=" is the token verbatim,
            // '&' included - a JWT has none, but the value is spliced
            // rather than encoded, so don't assume it.
            var marker = "idToken=";
            var markerIndex = body.IndexOf(marker, StringComparison.Ordinal);
            var head = markerIndex < 0 ? body : body[..markerIndex];
            var idToken = markerIndex < 0 ? "" : body[(markerIndex + marker.Length)..];

            var idp = "";
            foreach (var part in head.Split('&', StringSplitOptions.RemoveEmptyEntries))
            {
                var kv = part.Split('=', 2);
                if (kv.Length == 2 && kv[0] == "idp") idp = kv[1];
            }

            if (idp != "nintendoAccount")
                return Error(StatusCodes.Status400BadRequest, $"unsupported idp '{idp}'");
            if (string.IsNullOrEmpty(idToken))
                return Error(StatusCodes.Status400BadRequest, "idToken is required");

            var accountId = FakeJwt.DecodeSub(idToken);
            if (string.IsNullOrEmpty(accountId))
                return Error(StatusCodes.Status400BadRequest, "idToken has no sub");

            // Linking is "attach *this* save to an account that has none".
            // Switching to a save the account already owns is the other
            // flow entirely (BaaSUser.switchByNintendoAccount, through
            // federation). So an account that already owns a *different*
            // save is a conflict, not something to absorb: accepting it
            // told the player "linked!" while nothing changed, and quietly
            // orphaned the save they were holding. 409 is the code the SDK
            // already has a recovery path for. Refuse before touching any
            // state, so a rejected attempt leaves no trace.
            var existing = links.BaasUserIdFor(accountId);
            if (existing is not null && existing != userId)
            {
                log.LogWarning("baas/link: refusing - Authentik account {AccountId} already owns save {Existing}, " +
                    "device asked to link {UserId} (use switch/restore instead)", accountId, existing, userId);
                return Error(StatusCodes.Status409Conflict,
                    "This Nintendo Account already has a game account. " +
                    "Use 'restore account' to switch to it instead of linking.");
            }

            // Record the device behind this user id as belonging to the
            // account. This is what lets a *later* plain login resolve the
            // device back to the account without an idpAccount in the
            // request.
            links.Claim(userId, accountId);
            if (existing is null)
                // First save this account has ever owned - it becomes canonical.
                links.Link(accountId, userId);

            log.LogInformation("baas/link: user={UserId} linked to Authentik account {AccountId}", userId, accountId);
            return Results.Content(
                UserObject(userId, accountId, PokelandClock.UtcNowOffset).ToString(Newtonsoft.Json.Formatting.None),
                "application/json");
        });
    }

    /// <summary>The error envelope BaseHttpClient parses on a non-2xx. Both
    /// keys are required: NPFErrorImpl only lifts code/message out of the
    /// body when *both* are present, otherwise it falls back to the HTTP
    /// status plus the raw body text.</summary>
    private static IResult Error(int status, string message) =>
        Results.Content(new JObject { ["code"] = status, ["message"] = message }
            .ToString(Newtonsoft.Json.Formatting.None), "application/json", statusCode: status);

    private static void MapRest(WebApplication app, AccountLinkStore links)
    {
        // Profile fetch/update. The game does not depend on these, but the SDK
        // calls them, and a 404 surfaces as an NPFError. Resolves accountId so
        // a linked status shows up even without an idToken on this particular
        // request.
        app.MapMethods("/core/v1/users/{id}", new[] { "GET", "POST", "PATCH", "PUT" },
            (string id) => Results.Content(
                UserObject(id, links.AccountIdFor(id), PokelandClock.UtcNowOffset)
                    .ToString(Newtonsoft.Json.Formatting.None), "application/json"));

        app.MapGet("/core/v1/users", () => Results.Content("[]", "application/json"));

        // Analytics sinks - accept and discard. The client hits both a
        // "core" and a "bigdata" prefix for the same events/config calls
        // (observed live: repeated GET /bigdata/v1/analytics/events/config
        // 404-looping forever post-Terms-of-Use, apparently blocking the
        // startup sequence from proceeding since it never got an
        // acceptable response) - register both.
        app.MapPost("/core/v1/analytics/events", () => Results.Ok());
        app.MapGet("/core/v1/analytics/events/config",
            () => Results.Content("{}", "application/json"));
        app.MapPost("/bigdata/v1/analytics/events", () => Results.Ok());
        app.MapGet("/bigdata/v1/analytics/events/config",
            () => Results.Content("{}", "application/json"));

        // The NPF SDK's general profanity service is separate from the
        // pokemon-webapi nickname endpoint. It expects the submitted array
        // back with a validity result attached to every word.
        app.MapPost("/audit/v1/profanity_inspect", async (HttpContext http) =>
        {
            using var reader = new StreamReader(http.Request.Body);
            Newtonsoft.Json.Linq.JArray words;
            try { words = Newtonsoft.Json.Linq.JArray.Parse(await reader.ReadToEndAsync()); }
            catch { return Results.BadRequest(); }

            foreach (var word in words.OfType<JObject>())
                word["valid"] = true;

            return Results.Content(words.ToString(Newtonsoft.Json.Formatting.None),
                "application/json");
        });

        // No restored customer-support inbox exists, so there cannot be an
        // unread support reply. The field is optional in InquiryStatusMapper,
        // but returning it explicitly avoids stale client-side state.
        app.MapGet("/inquiry/v1/users/{userId}", () => Results.Json(new
        {
            hasUnreadCsComment = false,
        }));

        // Push registration is nonessential for an offline revival. Accept a
        // device token so registration cannot block startup; a later read gets
        // a valid empty token instead of a 404 or an unparseable object.
        app.MapPut("/notification/v1/push_channels/{userId}/{deviceId}",
            () => Results.Content("{}", "application/json"));
        app.MapGet("/notification/v1/push_channels/{userId}/{deviceId}",
            () => Results.Json(new { deviceToken = "" }));

        // Empty read models are valid according to the SDK's JSONArray
        // mappers. They disable commerce without letting a missing retired
        // service strand the game in a loading coroutine.
        app.MapGet("/vcm/v1/users/{userId}/markets/{market}/wallets",
            () => Results.Json(Array.Empty<object>()));
        app.MapGet("/vcm/v1/users/{userId}/markets/{market}/transactions",
            () => Results.Json(Array.Empty<object>()));
        app.MapGet("/vcm/v1/users/{userId}/markets/{market}/transaction_histories_cached",
            () => Results.Json(Array.Empty<object>()));
        app.MapGet("/vcm/v1/users/{userId}/ability",
            () => Results.Json(new { purchasable = false }));
    }

    /// <summary>Deterministic 32-hex id derived from the device account id.</summary>
    private static string StableId(string deviceId)
    {
        var hash = System.Security.Cryptography.SHA256.HashData(Encoding.UTF8.GetBytes("pokeland:" + deviceId));
        return Convert.ToHexString(hash, 0, 16).ToLowerInvariant();
    }
}
