#nullable disable
using System.Collections.Generic;
using System.Linq;
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
    /// Trainer name, set via SetMyUserProfile. Login and GetMyUserProfile both
    /// read it back through UserProfiles.Current() so a renamed trainer stays
    /// renamed after a restart.
    /// </summary>
    [JsonProperty("Nickname")]
    public string Nickname { get; set; } = "Trainer";

    /// <summary>
    /// Times each stage has been cleared, keyed by the stage's wire code
    /// joined with commas. The client keeps no progress of its own - it takes
    /// ClearCount straight from the Stage record Login and EndStage hand it -
    /// so this is the only copy.
    /// </summary>
    [JsonProperty("StageClears")]
    public Dictionary<string, int> StageClears { get; set; } = new();

    /// <summary>
    /// Free (non-purchased) diamonds. Mission rewards land here; there is no
    /// purchase flow, so the paid balance stays zero.
    /// </summary>
    [JsonProperty("DiamondFree")]
    public int DiamondFree { get; set; }

    /// <summary>
    /// Per-mission progress as last reported by the client's RecordMissions
    /// commit, keyed by MissionID. The client counts progress locally and
    /// hands over the running totals; the server keeps them so a mission is
    /// not back to zero after a restart.
    /// </summary>
    [JsonProperty("MissionProgress")]
    public Dictionary<int, int> MissionProgress { get; set; } = new();

    /// <summary>Missions already paid out. Redeeming is the one step the
    /// client must not be trusted with, so this is what stops a reward being
    /// collected twice.</summary>
    [JsonProperty("RedeemedMissions")]
    public HashSet<int> RedeemedMissions { get; set; } = new();

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

    /// <summary>Applies one <c>SetMyUserProfile</c> update and persists it.</summary>
    public bool ApplyProfile(Pokeland.Protocol.MyUserProfile profile)
    {
        if (profile?.Nickname is null || profile.Nickname == _player.Nickname) return false;
        lock (_gate) _player.Nickname = profile.Nickname;
        Save();
        return true;
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

    /// <summary>
    /// Merges a RecordMissions commit. Progress is taken as the high-water
    /// mark rather than assigned: the commits are cumulative counters the
    /// client resends, and an in-flight request that lost a race must not walk
    /// a finished mission backwards. Returns true if anything moved.
    /// </summary>
    public bool ApplyMissions(IReadOnlyList<int> ids, IReadOnlyList<int> progresses)
    {
        if (ids is null || progresses is null) return false;
        bool changed = false;
        lock (_gate)
        {
            for (int i = 0; i < ids.Count && i < progresses.Count; i++)
            {
                _player.MissionProgress.TryGetValue(ids[i], out var have);
                if (progresses[i] <= have) continue;
                _player.MissionProgress[ids[i]] = progresses[i];
                changed = true;
            }
        }
        if (changed) Save();
        return changed;
    }

    /// <summary>
    /// Pays out the named missions, skipping any that are unfinished or
    /// already redeemed. Returns the ids actually paid.
    /// </summary>
    public List<int> RedeemMissions(IEnumerable<int> ids)
    {
        var paid = new List<int>();
        lock (_gate)
        {
            foreach (var id in ids ?? Enumerable.Empty<int>())
            {
                if (_player.RedeemedMissions.Contains(id)) continue;
                _player.MissionProgress.TryGetValue(id, out var progress);
                if (!Missions.IsComplete(id, progress)) continue;
                if (!Missions.TryGet(id, out var desc)) continue;
                _player.RedeemedMissions.Add(id);
                _player.DiamondFree += desc.Diamonds;
                paid.Add(id);
            }
        }
        if (paid.Count > 0) Save();
        return paid;
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
