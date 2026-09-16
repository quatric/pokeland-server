#nullable disable
using Newtonsoft.Json.Linq;

namespace Pokeland.Server;

/// <summary>
/// Resolves an Authentik account ("sub" from auth.pokewii.net's OIDC
/// userinfo, see <see cref="AuthentikClient"/>) to this game's BaaS user id
/// (the key <see cref="PlayerStoreManager"/> saves under), so linking a
/// Nintendo Account makes a save follow the player across devices.
///
/// Two maps, and the distinction between them is the whole mechanism.
/// <c>links</c> is account -&gt; the *canonical* save that account owns.
/// <c>claims</c> is device-derived user id -&gt; account, recording every
/// device that has ever linked to it. The second map exists because a plain
/// gateway/sdk/login has no Nintendo Account in the request at all - only a
/// deviceAccount, from which Baas.cs derives a user id. Without
/// <c>claims</c> there is no way back from "this device" to "this account",
/// so a reinstall (which mints a fresh deviceAccount, hence a fresh user id)
/// would silently start a new save and the link would be decorative. With
/// it, login can resolve the device to the account and hand back the
/// canonical save instead.
///
/// Persisted next to the per-device saves PlayerStoreManager uses, as
/// ".account_links.json" (a leading dot so PlayerStoreManager.AllOthers()'s
/// "*.json" glob never mistakes it for a save).
/// </summary>
public sealed class AccountLinkStore
{
    private readonly string _path;
    private readonly object _gate = new();
    private readonly Dictionary<string, string> _links = new();
    private readonly Dictionary<string, string> _claims = new();

    public AccountLinkStore(string directory)
    {
        _path = Path.Combine(directory, ".account_links.json");
        Load();
    }

    private void Load()
    {
        JObject raw;
        try
        {
            raw = JObject.Parse(File.ReadAllText(_path));
        }
        catch (FileNotFoundException) { return; }
        catch (DirectoryNotFoundException) { return; }
        catch (Exception)
        {
            // A corrupt link file must not stop the server booting - same
            // "fresh state beats refusing to start" call PlayerStore makes
            // for a corrupt save.
            return;
        }

        if (raw["links"] is JObject linksObj)
        {
            foreach (var p in linksObj.Properties()) _links[p.Name] = p.Value.Value<string>();
            if (raw["claims"] is JObject claimsObj)
                foreach (var p in claimsObj.Properties()) _claims[p.Name] = p.Value.Value<string>();
        }
        else
        {
            foreach (var p in raw.Properties()) _links[p.Name] = p.Value.Value<string>();
        }
    }

    private void Save()
    {
        var obj = new JObject
        {
            ["links"] = JObject.FromObject(_links),
            ["claims"] = JObject.FromObject(_claims),
        };
        var tmp = _path + ".tmp";
        Directory.CreateDirectory(Path.GetDirectoryName(_path)!);
        File.WriteAllText(tmp, obj.ToString(Newtonsoft.Json.Formatting.Indented));
        File.Move(tmp, _path, overwrite: true);
    }

    public string BaasUserIdFor(string accountId)
    {
        lock (_gate)
            return _links.TryGetValue(accountId, out var id) ? id : null;
    }

    /// <summary>
    /// Reverse lookup, so a plain login (no idpAccount on this particular
    /// request) can still report a linked-account status for a save that
    /// was linked in some earlier session - the number of linked accounts
    /// on a hobby revival server is small enough that a linear scan is not
    /// worth a second index.
    /// </summary>
    public string AccountIdFor(string baasUserId)
    {
        lock (_gate)
        {
            if (_claims.TryGetValue(baasUserId, out var claimed)) return claimed;
            foreach (var (account, uid) in _links)
                if (uid == baasUserId) return account;
            return null;
        }
    }

    public void Link(string accountId, string baasUserId)
    {
        lock (_gate)
        {
            if (_links.TryGetValue(accountId, out var existing) && existing == baasUserId) return;
            _links[accountId] = baasUserId;
            Save();
        }
    }

    /// <summary>Records that the device behind <paramref name="baasUserId"/>
    /// belongs to this account. Called at link time; read at every later
    /// login.</summary>
    public void Claim(string baasUserId, string accountId)
    {
        lock (_gate)
        {
            if (_claims.TryGetValue(baasUserId, out var existing) && existing == accountId) return;
            _claims[baasUserId] = accountId;
            Save();
        }
    }

    /// <summary>
    /// The save a device should actually load: if this user id belongs to a
    /// linked account, that account's canonical save, else null. This is
    /// what makes a save follow the account rather than the device.
    /// </summary>
    public string CanonicalForUser(string baasUserId)
    {
        var accountId = AccountIdFor(baasUserId);
        if (accountId is null) return null;
        lock (_gate)
            return _links.TryGetValue(accountId, out var canonical) ? canonical : null;
    }
}
