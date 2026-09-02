#nullable disable
using System.Collections.Concurrent;
using System.Linq;

namespace Pokeland.Server;

/// <summary>
/// One <see cref="PlayerStore"/> per device/account, keyed by
/// <see cref="GameSession.BaaSUserId"/> - the stable per-device id the fake
/// BaaS login already hands out, so multiple devices/emulators get
/// independent saves without any real login/credential system.
/// </summary>
public sealed class PlayerStoreManager
{
    private readonly string _dir;
    private readonly ConcurrentDictionary<string, PlayerStore> _stores = new();

    public PlayerStoreManager(string dir)
    {
        _dir = dir;
        Directory.CreateDirectory(_dir);
    }

    public PlayerStore Get(string baasUserId)
    {
        var id = string.IsNullOrEmpty(baasUserId) ? "anonymous" : baasUserId;
        return _stores.GetOrAdd(id, CreateStore);
    }

    private PlayerStore CreateStore(string id)
    {
        var path = Path.Combine(_dir, $"{id}.json");

        // One-time migration: the original single-save build wrote one shared
        // player.json next to the server. The first ever per-user save this
        // process creates inherits it, so whoever was already playing keeps
        // their progress instead of being reset to a fresh account.
        if (!File.Exists(path))
        {
            var legacy = Environment.GetEnvironmentVariable("POKELAND_SAVE") ?? "player.json";
            var migratedMarker = Path.Combine(_dir, ".legacy-migrated");
            if (File.Exists(legacy) && !File.Exists(migratedMarker) && !Directory.EnumerateFiles(_dir, "*.json").Any())
            {
                File.Copy(legacy, path);
                File.WriteAllText(migratedMarker, id);
            }
        }

        return new PlayerStore(path);
    }
}
