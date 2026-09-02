#nullable disable
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Pokeland.Server;

/// <summary>
/// The part of an account that has to outlive a session.
///
/// A GameSession is created fresh by every Login, so anything kept only there
/// is lost the moment the app restarts - which is why the tutorial replayed
/// from the top on every launch, birthday gate and all. The client itself
/// keeps no progress: it asks for the DoneFlag bit vector at Login and trusts
/// whatever comes back, so this is the authoritative copy.
/// </summary>
public sealed class Player
{
    /// <summary>
    /// Set members of <c>Pokeland.Protocol.DoneFlag</c>. Stored as a set of
    /// ints rather than the wire's bit vector because the diffs the client
    /// sends are per-flag; packing happens once, on the way out.
    /// </summary>
    [JsonProperty("DoneFlags")]
    public HashSet<int> DoneFlags { get; set; } = new();

    /// <summary>
    /// Wallet. Login seeds the Cache from here rather than from a constant, so
    /// money a run actually earned is still there after a restart.
    /// </summary>
    [JsonProperty("Money")]
    public int Money { get; set; } = 1000;

    /// <summary>
    /// Times each stage has been cleared, keyed by the stage's wire code
    /// joined with commas. The client keeps no progress of its own - it takes
    /// ClearCount straight from the Stage record Login and EndStage hand it -
    /// so this is the only copy.
    /// </summary>
    [JsonProperty("StageClears")]
    public Dictionary<string, int> StageClears { get; set; } = new();

    /// <summary>
    /// Flags the client can only earn through a flow this server does not
    /// implement yet, and which gate progress until they are set.
    ///
    /// FirstChallengeDone (3): CampPageMain.iMessageTryBtnChallenge (RVA
    /// 0xF21C08) is a hard gate - while flag 3 is clear it shows "I would
    /// like you to check your challenges before you go on an adventure" and
    /// refuses the Globe transition, so tapping the globe at Camp just
    /// replays the message. The client only sets flag 3 after a mission
    /// reward is actually redeemed (MissionSelector.iMessageFirstChallengeDone),
    /// which needs the ReceiveMissionRewards endpoint. Seed it until that
    /// exists, or the game cannot leave Camp at all.
    /// </summary>
    private static readonly int[] SeedFlags = { 3 };

    /// <summary>Adds any missing <see cref="SeedFlags"/>; true if it changed.</summary>
    public bool ApplySeedFlags()
    {
        bool changed = false;
        foreach (var f in SeedFlags) changed |= DoneFlags.Add(f);
        return changed;
    }

    /// <summary>
    /// Packs <see cref="DoneFlags"/> into the little-endian bit vector
    /// <c>DoneFlagBox</c> indexes by DoneFlag value. Sized for the largest
    /// member of the enum (163) so a read cannot run off the end even for
    /// flags that are never set.
    /// </summary>
    public byte[] ToDoneFlagVec()
    {
        var vec = new byte[(163 / 8) + 1];
        foreach (var f in DoneFlags)
        {
            if (f < 0 || f > 163) continue;
            vec[f / 8] |= (byte)(1 << (f % 8));
        }
        return vec;
    }
}

/// <summary>
/// Persists <see cref="Player"/> to a JSON file beside the server. One account
/// only - this is a single-player revival, and Login does not carry a real
/// BaaS user id to key on.
/// </summary>
public sealed class PlayerStore
{
    private readonly string _path;
    private readonly object _gate = new();
    private Player _player;

    public PlayerStore(string path)
    {
        _path = path;
        try
        {
            if (File.Exists(_path))
                _player = JsonConvert.DeserializeObject<Player>(File.ReadAllText(_path));
        }
        catch (Exception)
        {
            // A corrupt save must not stop the server booting: a fresh account
            // is strictly better than refusing to start.
            _player = null;
        }
        _player ??= new Player();
        if (_player.ApplySeedFlags()) Save();
    }

    public Player Current { get { lock (_gate) return _player; } }

    public void Save()
    {
        lock (_gate)
        {
            var tmp = _path + ".tmp";
            File.WriteAllText(tmp, JsonConvert.SerializeObject(_player, Formatting.Indented));
            File.Move(tmp, _path, overwrite: true);
        }
    }

    /// <summary>Applies one <c>SetDoneFlag</c> diff and persists it.</summary>
    public bool Apply(Pokeland.Protocol.DoneFlagDiff diff)
    {
        if (diff is null) return false;
        bool changed = false;
        lock (_gate)
        {
            foreach (var f in diff.Ons ?? new List<Pokeland.Protocol.DoneFlag>())
                changed |= _player.DoneFlags.Add((int)f);
            foreach (var f in diff.Offs ?? new List<Pokeland.Protocol.DoneFlag>())
                changed |= _player.DoneFlags.Remove((int)f);
        }
        if (changed) Save();
        return changed;
    }

    /// <summary>Records a stage clear and any money it paid out; returns the
    /// stage's new total clear count.</summary>
    public int RecordClear(string stageKey, int money)
    {
        int count;
        lock (_gate)
        {
            _player.StageClears.TryGetValue(stageKey, out count);
            _player.StageClears[stageKey] = ++count;
            // Money is clamped at zero rather than trusted outright: GotMoney
            // is a client-reported figure.
            _player.Money += money > 0 ? money : 0;
        }
        Save();
        return count;
    }
}
