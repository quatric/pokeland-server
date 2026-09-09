#nullable disable
using System.Text;
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// Stand-in for <c>https://auth.pokewii.net/</c>.
///
/// <para>
/// Serves a minimal account profile that includes the player's Mii avatar
/// encoded as the standard 96-byte <c>MiiCoreData</c> blob (identical layout
/// to <c>RFLCharData</c> / <c>CFLCharModelSource</c>). The player store is
/// consulted so the blob round-trips through <c>SetMyUserProfile</c> ↔
/// <c>GetMyUserProfile</c> and the Login reset without requiring a separate
/// account system.
/// </para>
///
/// <para>Routes registered here mirror the pokewii.net auth surface:</para>
/// <list type="bullet">
///   <item><term>GET  /auth/v1/users/{userId}</term><description>Profile + Mii avatar blob</description></item>
///   <item><term>PATCH /auth/v1/users/{userId}</term><description>Persist updated Mii blob</description></item>
///   <item><term>GET  /auth/v1/users/{userId}/mii</term><description>Raw Mii blob as JSON number-array</description></item>
/// </list>
/// </summary>
public static class PokewiiAuth
{
    // ── Default Mii ───────────────────────────────────────────────────────────
    // 96-byte RFLCharData with a neutral appearance. The first two bytes are
    // the big-endian CRC16-CCITT of bytes [2..95]; they are left as 0x0000
    // here because the game never verifies the CRC — it feeds the blob
    // directly into the Mii SDK renderer. All other fields are set to the
    // safe mid-range defaults used by the Wii system menu's "blank" Mii.
    private static readonly byte[] DefaultMiiCoreData = BuildDefaultMii();

    public static void MapPokewiiAuth(this WebApplication app, PlayerStoreManager stores, ILogger log)
    {
        // ------------------------------------------------------------------ GET profile
        app.MapGet("/auth/v1/users/{userId}", (string userId) =>
        {
            var store = stores.Get(userId);
            return Results.Content(
                BuildProfile(userId, store.Current).ToString(Newtonsoft.Json.Formatting.None),
                "application/json");
        });

        // ------------------------------------------------------------------ PATCH / PUT profile (nickname / Mii blob)
        app.MapMethods("/auth/v1/users/{userId}", new[] { "PATCH", "PUT" },
            async (string userId, HttpContext http) =>
        {
            using var reader = new StreamReader(http.Request.Body);
            var body = await reader.ReadToEndAsync();

            JObject req;
            try { req = JObject.Parse(body); }
            catch { return Results.BadRequest(); }

            var store = stores.Get(userId);
            var player = store.Current;
            bool changed = false;

            // Accept nickname update
            if (req["nickname"]?.Value<string>() is { Length: > 0 } nick && nick != player.Nickname)
            {
                player.Nickname = nick;
                changed = true;
                log.LogInformation("pokewii-auth/{UserId}: nickname -> {Nick}", userId, nick);
            }

            // Accept Mii blob update (number-array or base64 string)
            var miiToken = req["miiData"];
            if (miiToken is not null)
            {
                var blob = ParseMiiBlob(miiToken);
                if (blob is { Length: 96 })
                {
                    player.MiiCoreData = blob;
                    changed = true;
                    log.LogInformation("pokewii-auth/{UserId}: miiData updated", userId);
                }
            }

            if (changed) store.Save();

            return Results.Content(
                BuildProfile(userId, store.Current).ToString(Newtonsoft.Json.Formatting.None),
                "application/json");
        });

        // ------------------------------------------------------------------ GET raw Mii blob
        // Returns the 96-byte blob as a JSON array of integers, matching
        // Unity's JsonUtility byte[] wire format so the client can feed it
        // straight into the Mii renderer without further decoding.
        app.MapGet("/auth/v1/users/{userId}/mii", (string userId) =>
        {
            var store = stores.Get(userId);
            var blob = store.Current.MiiCoreData is { Length: 96 } b ? b : DefaultMiiCoreData;
            return Results.Content(BytesToNumberArray(blob), "application/json");
        });
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static JObject BuildProfile(string userId, Player player)
    {
        var blob = player.MiiCoreData is { Length: 96 } b ? b : DefaultMiiCoreData;
        return new JObject
        {
            ["id"]         = userId,
            ["nickname"]   = player.Nickname ?? "Trainer",
            // miiData as a number-array for Unity client parity
            ["miiData"]    = JArray.Parse(BytesToNumberArray(blob)),
            // Convenience base64 for non-Unity consumers (web dashboard etc.)
            ["miiDataB64"] = Convert.ToBase64String(blob),
        };
    }

    /// <summary>
    /// Accepts the Mii blob either as a JSON number-array (Unity wire format)
    /// or as a base64 string.
    /// </summary>
    private static byte[] ParseMiiBlob(JToken token)
    {
        if (token is JArray arr)
        {
            var bytes = new byte[arr.Count];
            for (int i = 0; i < arr.Count; i++)
                bytes[i] = (byte)(int)arr[i];
            return bytes;
        }
        if (token.Type == JTokenType.String)
        {
            try { return Convert.FromBase64String(token.Value<string>()); }
            catch { return null; }
        }
        return null;
    }

    private static string BytesToNumberArray(byte[] bytes)
    {
        var sb = new StringBuilder("[");
        for (int i = 0; i < bytes.Length; i++)
        {
            if (i > 0) sb.Append(',');
            sb.Append(bytes[i]);
        }
        sb.Append(']');
        return sb.ToString();
    }

    // ── Default Mii blob ─────────────────────────────────────────────────────
    // 96-byte RFLCharData layout (big-endian, Wii-era):
    //   [0x00-0x01]  CRC16 (skipped — renderer does not verify)
    //   [0x02]       Flags (copy bit etc.)
    //   [0x03]       Unknown / padding
    //   [0x04-0x17]  Mii name in UTF-16BE (10 chars max, NUL-terminated)
    //   [0x18]       Height  (0–127)
    //   [0x19]       Weight  (0–127)
    //   ...          Appearance fields packed as described in RFL headers
    //
    // The defaults below produce a short, neutral-featured Mii named "Trainer".
    private static byte[] BuildDefaultMii()
    {
        var data = new byte[96];

        // Mii name "Trainer" in UTF-16BE at offset 0x04, 10-char field (20 bytes)
        const string name = "Trainer";
        for (int i = 0; i < Math.Min(name.Length, 10); i++)
        {
            data[0x04 + i * 2]     = (byte)(name[i] >> 8);
            data[0x04 + i * 2 + 1] = (byte)(name[i] & 0xFF);
        }

        // Height / weight: midpoint (64 = roughly average)
        data[0x18] = 64;
        data[0x19] = 64;

        // Skin colour 0 (fair) packed into face byte; face type 0 (round)
        data[0x20] = 0;

        // Eye colour 4 (brown)
        data[0x24] = (byte)((4 << 3) | 0);

        // Hair type 33 (short, parted)
        data[0x28] = 33;

        // Eyebrow type 6
        data[0x2C] = 6;

        // Nose type 1
        data[0x30] = 1;

        // Mouth type 23 (friendly smile)
        data[0x34] = 23;

        return data;
    }
}
