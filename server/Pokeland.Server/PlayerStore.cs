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
    /// PPEs earned past the fixed Login starter - one per cleared-stage
    /// PPEUpdate. Login appends these to the wire PPEs list so a caught
    /// Pokemon is still in the roster after a restart, since the client
    /// keeps no roster of its own (same reasoning as StageClears above).
    /// </summary>
    [JsonProperty("OwnedPPEs")]
    public List<OwnedPPE> OwnedPPEs { get; set; } = new();

    /// <summary>Next id to hand out via GrantPPE. Starts past the Login
    /// starter's hardcoded PPEId of 1.</summary>
    [JsonProperty("NextPPEId")]
    public long NextPPEId { get; set; } = 2;

    /// <summary>
    /// Owned equipment (see OwnedEqunit). There is no drop/grant flow that
    /// hands out equipment yet - EnsureStarterEqunits below grants a small
    /// fixed set the first time a session touches this, the same bootstrap
    /// role the hardcoded Login starter PPE plays for the roster - so
    /// EqunitMount/EqunitUpgrade/GoodbyeEqunits have real inventory to act on
    /// instead of always operating on an empty list.
    /// </summary>
    [JsonProperty("Equnits")]
    public List<OwnedEqunit> Equnits { get; set; } = new();

    [JsonProperty("NextEqunitId")]
    public long NextEqunitId { get; set; } = 1;

    [JsonProperty("StarterEqunitsGranted")]
    public bool StarterEqunitsGranted { get; set; }

    /// <summary>
    /// Chests picked up during a run (StartStage's MHM.DropChestTypeID makes
    /// this possible; the client sets EndStage.Req.GotChest and shows its own
    /// "you got a chest!" screen off locally-shipped chest content - the
    /// server never invents a ChestId, it only ever hears about one the first
    /// time the client calls ChestStartUnlock/OpenChest with an id it already
    /// decided on, so entries here are created lazily on first contact rather
    /// than at EndStage time.
    /// </summary>
    [JsonProperty("Chests")]
    public Dictionary<long, PendingChest> Chests { get; set; } = new();

    /// <summary>
    /// Free (non-purchased) diamonds. Mission rewards land here; there is no
    /// purchase flow, so the paid balance stays zero.
    /// </summary>
    [JsonProperty("DiamondFree")]
    public int DiamondFree { get; set; }

    /// <summary>Purchased diamonds. There is no real-money purchase flow, so
    /// this only ever moves via BuyUtensil/BuyStoreSize spending it down
    /// after free diamonds run out - it stays zero otherwise.</summary>
    [JsonProperty("DiamondPaid")]
    public int DiamondPaid { get; set; }

    /// <summary>
    /// Consumable utensils bought via BuyUtensil (JitanTicket and friends),
    /// keyed by UtensilID. Login reports these back and ChestUseJitanTicket
    /// spends one - real inventory, not an ack-only stub.
    /// </summary>
    [JsonProperty("Utensils")]
    public Dictionary<int, int> Utensils { get; set; } = new();

    /// <summary>
    /// Park decoration currently mounted on each owned PPE, keyed by PPEId.
    /// Set via PdecoMount (Scenes.Camp lets the player pick a decoration per
    /// PPE from the Pdecos every account owns - see PdecoOwned below); wire
    /// PPE.X[6] is PdecoID (see LoginHandler's PPE.Index layout comment), so
    /// this dictionary is what makes a mount survive a restart instead of the
    /// handler being a pure ack.
    /// </summary>
    [JsonProperty("PdecoMounts")]
    public Dictionary<long, int> PdecoMounts { get; set; } = new();

    /// <summary>Extra levels bought onto a PPE via AddPPELevel, keyed by
    /// PPEId - wire PPE.X[17] AddLevelCount (see LoginHandler's PPE.Index
    /// layout comment). Spends one UtensilID.AddPPELevel ticket per level.</summary>
    [JsonProperty("PPEAddLevels")]
    public Dictionary<long, int> PPEAddLevels { get; set; } = new();

    /// <summary>Extra equipment sockets bought onto a PPE via
    /// AddNormalSocketCount, keyed by PPEId - wire PPE.X[18]
    /// AddNormalSocketCount. Spends one UtensilID.AddNormalEqunitSocket
    /// ticket per socket.</summary>
    [JsonProperty("PPEAddNormalSockets")]
    public Dictionary<long, int> PPEAddNormalSockets { get; set; } = new();

    /// <summary>Extra inventory slots bought via BuyStoreSize, on top of the
    /// base size Login already advertises.</summary>
    [JsonProperty("PaidPPEStoreSize")]
    public int PaidPPEStoreSize { get; set; }
    [JsonProperty("PaidNormalEqunitStoreSize")]
    public int PaidNormalEqunitStoreSize { get; set; }
    [JsonProperty("PaidSpEqunitStoreSize")]
    public int PaidSpEqunitStoreSize { get; set; }

    /// <summary>Highest Announcement.AnnouncementId the client has told us
    /// (via SetAnnouncementState) it has already marked read, so the welcome
    /// announcement in Announcements.cs doesn't keep popping up.</summary>
    [JsonProperty("HeadMarkedAsReadAnnouncementId")]
    public int HeadMarkedAsReadAnnouncementId { get; set; }

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
/// A PPE earned during a run, in the minimal form Login needs to rebuild the
/// wire <c>PPE</c> record (see BasePPE.Index/PPE.Index in out/dump/dump.cs).
/// No equipment/socket fields - StartStage's drops never carry equipment, so
/// there is nothing to persist there for an earned PPE itself. Equipment
/// ownership lives separately in Player.Equnits (see OwnedEqunit below):
/// out/dump/dump.cs's BasePPEAndEqunits.Equnits (X[12..23], 12 ints) turned
/// out to be PPEDrop's own inline stat-roll preview (3 sockets x BaseEqunit's
/// 4-int UnitPrefix/PrefixGrade/PrefixAddition0/1), not where an *owned*
/// PPE's equipment lives - Equnit (out/dump/dump.cs TypeDefIndex 6305) is its
/// own top-level entity carrying PPEId+SocketNo, mirroring how Pdecos/
/// Utensils ride in Login's top-level lists rather than inside PPE.X.
/// </summary>
public sealed class OwnedPPE
{
    [JsonProperty("Id")]
    public long Id { get; set; }
    [JsonProperty("MonsNo")]
    public int MonsNo { get; set; }
    [JsonProperty("Level")]
    public int Level { get; set; }
    [JsonProperty("Grade")]
    public int Grade { get; set; }
    [JsonProperty("Waza0")]
    public int Waza0 { get; set; }
    [JsonProperty("Waza1")]
    public int Waza1 { get; set; }
    [JsonProperty("Nickname")]
    public string Nickname { get; set; }
}

/// <summary>
/// An owned piece of equipment (Equnit.Index in out/dump/dump.cs: EqunitId
/// Low/High, PPEId Low/High, SocketNo, IsFavorite, GotUTC Low/High, riding on
/// top of BaseEqunit.Index's UnitPrefix/PrefixGrade/PrefixAddition0/1). Mount
/// is PPEId==0 (no PPEId 0 is ever handed out - NextPPEId starts at 2 past
/// the starter's fixed 1) meaning unmounted, tracked here rather than in any
/// PPE's own fields - see the OwnedPPE doc above for why.
/// </summary>
public sealed class OwnedEqunit
{
    [JsonProperty("Id")]
    public long Id { get; set; }
    [JsonProperty("UnitPrefix")]
    public int UnitPrefix { get; set; }
    [JsonProperty("PrefixGrade")]
    public int PrefixGrade { get; set; }
    [JsonProperty("PrefixAddition0")]
    public int PrefixAddition0 { get; set; }
    [JsonProperty("PrefixAddition1")]
    public int PrefixAddition1 { get; set; }
    [JsonProperty("MountedPPEId")]
    public long MountedPPEId { get; set; }
    [JsonProperty("MountedSocketNo")]
    public int MountedSocketNo { get; set; }
    [JsonProperty("IsFavorite")]
    public bool IsFavorite { get; set; }
}

/// <summary>
/// One chest, keyed by the client's own ChestId. Money-only reward - the
/// wire OpenChest.Res carries no equnit payload of its own (see
/// PPEFactory's equipment-layout note), so a real per-chest item drop is
/// blocked on the same un-RE'd BasePPEAndEqunits.Equnits layout.
/// </summary>
public sealed class PendingChest
{
    [JsonProperty("StartUnlockUtc")]
    public DateTime? StartUnlockUtc { get; set; }
    [JsonProperty("Opened")]
    public bool Opened { get; set; }
    [JsonProperty("Money")]
    public int Money { get; set; } = 100;
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

    /// <summary>Converts a run's offered drop into a persisted, owned PPE and
    /// returns its new id - the id an EndStage PPEUpdate reports back to the
    /// client as the drop's real PPEId.</summary>
    public OwnedPPE GrantPPE(int monsNo, int level, int grade, int waza0, int waza1, string nickname)
    {
        OwnedPPE ppe;
        lock (_gate)
        {
            ppe = new OwnedPPE
            {
                Id = _player.NextPPEId++,
                MonsNo = monsNo,
                Level = level,
                Grade = grade,
                Waza0 = waza0,
                Waza1 = waza1,
                Nickname = nickname,
            };
            _player.OwnedPPEs.Add(ppe);
        }
        Save();
        return ppe;
    }

    /// <summary>
    /// Diamond price per BuyUnit. docs/tables/UtensilDesc.json (extracted
    /// from the real asset bundle) shows m_priceDiamond is 0 for every
    /// utensil - they're IAP/event-granted, not diamond-purchasable in
    /// retail - so this flat placeholder only exists to keep the BuyUtensil
    /// endpoint exercising something non-trivial in absence of a real SKU
    /// flow. m_maxCount, in contrast, is real retail data and is enforced
    /// below.
    /// </summary>
    private const int UtensilPriceDiamond = 10;

    /// <summary>Real retail per-utensil stack caps (UtensilDesc.m_maxCount),
    /// indexed by UtensilID - see docs/tables/UtensilDesc.json.</summary>
    private static readonly int[] UtensilMaxCount = { 0, 10, 999, 999 };

    /// <summary>
    /// Buys utensils by spending diamonds (free balance first, then paid).
    /// Returns false if the player can't afford it, or if the purchase
    /// would exceed the utensil's retail stack cap; nothing is charged or
    /// granted on failure.
    /// </summary>
    public bool BuyUtensil(Pokeland.Protocol.UtensilID id, int count)
    {
        if (count <= 0) return false;
        bool ok;
        lock (_gate)
        {
            int cost = UtensilPriceDiamond * count;
            int have = _player.DiamondFree + _player.DiamondPaid;
            _player.Utensils.TryGetValue((int)id, out var have2);
            int max = (int)id >= 0 && (int)id < UtensilMaxCount.Length ? UtensilMaxCount[(int)id] : int.MaxValue;
            ok = have >= cost && have2 + count <= max;
            if (ok)
            {
                int fromFree = Math.Min(_player.DiamondFree, cost);
                _player.DiamondFree -= fromFree;
                _player.DiamondPaid -= cost - fromFree;
                _player.Utensils[(int)id] = have2 + count;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>
    /// Mounts (or, with PdecoID.NONE, clears) a park decoration on a PPE the
    /// player actually owns - the starter's fixed Id=1 or one of OwnedPPEs.
    /// Every PdecoID the enum defines counts as owned (there is no grant/shop
    /// flow for decorations in retail's own data - PdecoDesc carries no price
    /// fields - so gating this against an inventory would just be inventing a
    /// restriction retail doesn't have). Returns false for an unknown PPEId or
    /// out-of-range PdecoID; nothing is persisted on failure.
    /// </summary>
    public bool MountPdeco(long ppeId, Pokeland.Protocol.PdecoID pdecoId)
    {
        if (!Enum.IsDefined(typeof(Pokeland.Protocol.PdecoID), pdecoId)) return false;
        bool ok;
        lock (_gate)
        {
            ok = ppeId == 1 || _player.OwnedPPEs.Any(p => p.Id == ppeId);
            if (ok)
            {
                if (pdecoId == Pokeland.Protocol.PdecoID.NONE) _player.PdecoMounts.Remove(ppeId);
                else _player.PdecoMounts[ppeId] = (int)pdecoId;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>
    /// Spends `addLevel` AddPPELevel utensils to add that many levels to a
    /// PPE the player owns. Returns false for an unknown PPEId, a
    /// non-positive addLevel, or insufficient utensils in stock; nothing is
    /// spent or persisted on failure.
    /// </summary>
    public bool AddPPELevel(long ppeId, int addLevel)
    {
        if (addLevel <= 0) return false;
        bool ok;
        lock (_gate)
        {
            _player.Utensils.TryGetValue((int)Pokeland.Protocol.UtensilID.AddPPELevel, out var have);
            ok = (ppeId == 1 || _player.OwnedPPEs.Any(p => p.Id == ppeId)) && have >= addLevel;
            if (ok)
            {
                _player.Utensils[(int)Pokeland.Protocol.UtensilID.AddPPELevel] = have - addLevel;
                _player.PPEAddLevels[ppeId] = _player.PPEAddLevels.GetValueOrDefault(ppeId) + addLevel;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>
    /// Spends `count` AddNormalEqunitSocket utensils to add that many
    /// equipment sockets to a PPE the player owns. Same failure modes as
    /// AddPPELevel above.
    /// </summary>
    public bool AddNormalSocketCount(long ppeId, int count)
    {
        if (count <= 0) return false;
        bool ok;
        lock (_gate)
        {
            _player.Utensils.TryGetValue((int)Pokeland.Protocol.UtensilID.AddNormalEqunitSocket, out var have);
            ok = (ppeId == 1 || _player.OwnedPPEs.Any(p => p.Id == ppeId)) && have >= count;
            if (ok)
            {
                _player.Utensils[(int)Pokeland.Protocol.UtensilID.AddNormalEqunitSocket] = have - count;
                _player.PPEAddNormalSockets[ppeId] = _player.PPEAddNormalSockets.GetValueOrDefault(ppeId) + count;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>Highest SocketNo the wire enum defines (0/1/2 - see
    /// out/dump/dump.cs's SocketNo), i.e. at most 3 equipment sockets per
    /// PPE regardless of how many AddNormalSocketCount has bought.</summary>
    private const int MaxSocketNo = 2;

    /// <summary>
    /// Grants a small fixed starter equipment set (one-time, gated by
    /// StarterEqunitsGranted) so EqunitMount/EqunitUpgrade have real
    /// inventory to act on - see Player.Equnits for why this bootstrap is
    /// needed. Two basic, unmounted, grade-0 pieces, mirroring the fixed
    /// Bulbasaur starter PPE Login already hardcodes.
    /// </summary>
    public void EnsureStarterEqunits()
    {
        lock (_gate)
        {
            if (_player.StarterEqunitsGranted) return;
            _player.Equnits.Add(new OwnedEqunit { Id = _player.NextEqunitId++, UnitPrefix = (int)Pokeland.Protocol.UnitPrefix.HP_PLUS });
            _player.Equnits.Add(new OwnedEqunit { Id = _player.NextEqunitId++, UnitPrefix = (int)Pokeland.Protocol.UnitPrefix.ARMOR_PLUS });
            _player.StarterEqunitsGranted = true;
        }
        Save();
    }

    /// <summary>
    /// Mounts an owned equnit onto one of a PPE's sockets, or unmounts it
    /// (PPEId 0) - see OwnedEqunit for why mount state lives here rather
    /// than on any PPE. Bumps whatever else already occupied that exact
    /// PPEId+SocketNo back to unmounted, since the wire has no notion of two
    /// equnits sharing a socket. Returns false for an unowned EqunitId, an
    /// unowned target PPEId, or an out-of-range SocketNo.
    /// </summary>
    public bool MountEqunit(long equnitId, long ppeId, Pokeland.Protocol.SocketNo socketNo)
    {
        if ((int)socketNo < 0 || (int)socketNo > MaxSocketNo) return false;
        bool ok;
        lock (_gate)
        {
            var equnit = _player.Equnits.FirstOrDefault(e => e.Id == equnitId);
            ok = equnit != null && (ppeId == 0 || ppeId == 1 || _player.OwnedPPEs.Any(p => p.Id == ppeId));
            if (ok)
            {
                if (ppeId != 0)
                {
                    foreach (var other in _player.Equnits)
                        if (other.Id != equnitId && other.MountedPPEId == ppeId && other.MountedSocketNo == (int)socketNo)
                            other.MountedPPEId = 0;
                }
                equnit.MountedPPEId = ppeId;
                equnit.MountedSocketNo = ppeId == 0 ? 0 : (int)socketNo;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>Flat diamond price per PrefixGrade step - no EqunitUpgrade
    /// cost table was extracted (docs/tables has Category/FilterType/
    /// SortType but no per-grade cost desc), so this is a placeholder like
    /// UtensilPriceDiamond above. UseMulti spends x10 for a x10 grade jump,
    /// mirroring the wire field's own naming.</summary>
    private const int EqunitUpgradePriceDiamond = 50;

    /// <summary>Soft cap on PrefixGrade - PrefixGrade's wire enum runs to
    /// 254 but that is almost certainly the raw byte range rather than a
    /// real retail ceiling, so this stays conservative pending an extracted
    /// cost/cap table.</summary>
    private const int MaxPrefixGrade = 99;

    /// <summary>
    /// Upgrades an owned equnit's PrefixGrade by spending diamonds (free
    /// balance first, then paid). Returns false for an unowned EqunitId,
    /// insufficient diamonds, or a grade already at MaxPrefixGrade.
    /// </summary>
    public bool UpgradeEqunit(long equnitId, bool useMulti)
    {
        int steps = useMulti ? 10 : 1;
        bool ok;
        lock (_gate)
        {
            var equnit = _player.Equnits.FirstOrDefault(e => e.Id == equnitId);
            int cost = EqunitUpgradePriceDiamond * steps;
            int have = _player.DiamondFree + _player.DiamondPaid;
            ok = equnit != null && have >= cost && equnit.PrefixGrade + steps <= MaxPrefixGrade;
            if (ok)
            {
                int fromFree = Math.Min(_player.DiamondFree, cost);
                _player.DiamondFree -= fromFree;
                _player.DiamondPaid -= cost - fromFree;
                equnit.PrefixGrade += steps;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>Discards owned equnits by id (silently ignoring any id not
    /// actually owned) - GoodbyeEqunits' "sell for nothing" flow, same
    /// shape as GoodbyeChests.</summary>
    public void RemoveEqunits(IEnumerable<long> equnitIds)
    {
        var ids = new HashSet<long>(equnitIds);
        lock (_gate)
        {
            _player.Equnits.RemoveAll(e => ids.Contains(e.Id));
        }
        Save();
    }

    /// <summary>Spends one utensil of the given kind. Returns false if none
    /// are in stock.</summary>
    public bool SpendUtensil(Pokeland.Protocol.UtensilID id)
    {
        bool ok;
        lock (_gate)
        {
            ok = _player.Utensils.TryGetValue((int)id, out var have) && have > 0;
            if (ok) _player.Utensils[(int)id] = have - 1;
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>Diamond price per store-size BuyUnit - see UtensilPriceDiamond
    /// for why this is a placeholder rather than extracted retail data.</summary>
    private const int StoreSizePriceDiamond = 20;

    /// <summary>
    /// Buys inventory expansion in up to three categories at once (PPE,
    /// normal equnit, sp equnit), charging diamonds per BuyUnit requested.
    /// All-or-nothing: returns false and charges nothing if the combined cost
    /// can't be covered.
    /// </summary>
    public bool BuyStoreSize(int ppeUnits, int normalEqunitUnits, int spEqunitUnits)
    {
        ppeUnits = Math.Max(0, ppeUnits);
        normalEqunitUnits = Math.Max(0, normalEqunitUnits);
        spEqunitUnits = Math.Max(0, spEqunitUnits);
        bool ok;
        lock (_gate)
        {
            int cost = StoreSizePriceDiamond * (ppeUnits + normalEqunitUnits + spEqunitUnits);
            int have = _player.DiamondFree + _player.DiamondPaid;
            ok = cost > 0 && have >= cost;
            if (ok)
            {
                int fromFree = Math.Min(_player.DiamondFree, cost);
                _player.DiamondFree -= fromFree;
                _player.DiamondPaid -= cost - fromFree;
                _player.PaidPPEStoreSize += ppeUnits;
                _player.PaidNormalEqunitStoreSize += normalEqunitUnits;
                _player.PaidSpEqunitStoreSize += spEqunitUnits;
            }
        }
        if (ok) Save();
        return ok;
    }

    /// <summary>The real unlock wait - short, since TutorialCopper1 (the only
    /// chest content this server currently offers) is a low-tier chest in
    /// retail too.</summary>
    private static readonly TimeSpan ChestUnlockDuration = TimeSpan.FromMinutes(1);

    private PendingChest GetOrAddChest(long chestId)
    {
        if (!_player.Chests.TryGetValue(chestId, out var chest))
        {
            chest = new PendingChest();
            _player.Chests[chestId] = chest;
        }
        return chest;
    }

    /// <summary>Starts a chest's unlock timer if it isn't running yet.
    /// Returns false only if the chest was already opened.</summary>
    public bool StartChestUnlock(long chestId)
    {
        bool ok;
        lock (_gate)
        {
            var chest = GetOrAddChest(chestId);
            ok = !chest.Opened;
            chest.StartUnlockUtc ??= PokelandClock.UtcNow;
        }
        Save();
        return ok;
    }

    /// <summary>
    /// Spends a Jitan ticket to finish a chest's unlock timer immediately,
    /// without opening it - OpenChest is still a separate call. Backdates
    /// StartUnlockUtc rather than adding a second "forced" flag, so the
    /// existing OpenChest elapsed-time check just sees the wait as already
    /// satisfied. Returns false only if the chest was already opened.
    /// </summary>
    public bool UseJitanTicket(long chestId)
    {
        bool ok;
        lock (_gate)
        {
            var chest = GetOrAddChest(chestId);
            ok = !chest.Opened;
            if (ok) chest.StartUnlockUtc = PokelandClock.UtcNow - ChestUnlockDuration;
        }
        Save();
        return ok;
    }

    /// <summary>Persists how far the client has scrolled through the
    /// announcement feed (SetAnnouncementState), so the welcome announcement
    /// stops being reported as unread on future logins.</summary>
    public void SetHeadMarkedAsReadAnnouncementId(int id)
    {
        lock (_gate)
        {
            if (id <= _player.HeadMarkedAsReadAnnouncementId) return;
            _player.HeadMarkedAsReadAnnouncementId = id;
        }
        Save();
    }

    /// <summary>
    /// Opens a chest. Diamond/JitanTicket both skip the wait outright (this
    /// server has no real currency cost to charge for them, so treat either
    /// as a free skip rather than reject it); the plain case needs the timer
    /// actually elapsed. Returns the money granted, or null if not unlocked
    /// yet. Re-opening an already-opened chest reports success with 0 so a
    /// retried request cannot mint money twice.
    /// </summary>
    public int? OpenChest(long chestId, Pokeland.Protocol.OpenChestBy by)
    {
        int? granted;
        lock (_gate)
        {
            var chest = GetOrAddChest(chestId);
            if (chest.Opened) { granted = 0; }
            else
            {
                bool unlocked = by != Pokeland.Protocol.OpenChestBy.@None
                    || (chest.StartUnlockUtc is DateTime start
                        && PokelandClock.UtcNow - start >= ChestUnlockDuration);
                if (!unlocked) { granted = null; }
                else
                {
                    chest.Opened = true;
                    _player.Money += chest.Money;
                    granted = chest.Money;
                }
            }
        }
        Save();
        return granted;
    }
}
