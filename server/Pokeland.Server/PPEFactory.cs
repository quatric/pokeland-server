#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// Packs the wire PPE/PPEDrop int[] layouts (BasePPE.Index/PPE.Index/
/// PPEDrop.Index in out/dump/dump.cs) from plain fields. Proto.g.cs's
/// generated classes carry no named properties beyond the inherited int[] X,
/// so every caller that builds a PPE by hand needs this same packing -
/// LoginHandler's hardcoded starter, StartStageHandler's offered drop, and
/// LoginHandler's replay of previously-granted PPEs all go through here.
/// PPEDrop's embedded BasePPEAndEqunits.Equnits (X[12..23]) is always
/// zeroed: that field turned out to be a stage-offered drop's own inline
/// stat-roll preview, not where owned equipment lives - see BuildEqunit
/// below for the real, separate Equnit entity this server does implement.
/// </summary>
public static class PPEFactory
{
    private static int[] BaseSlots(int monsNo, int level, int grade, int waza0, int waza1) => new[]
    {
        /* 0  Rnd                 */ 0,
        /* 1  PokedexID           */ monsNo,
        /* 2  IsRareColor         */ 0,
        /* 3  ParaSex             */ 0,
        /* 4  Level               */ level,
        /* 5  ApOffset            */ 0,
        /* 6  PdecoID             */ 0,
        /* 7  PiiGrade            */ grade,
        /* 8  SocketCount         */ 0,
        /* 9  SpSocketCount       */ 0,
        /* 10 Waza0               */ waza0,
        /* 11 Waza1               */ waza1,
    };

    /// <summary>Builds an owned PPE (21 ints: base 12 + PPEId/PartyMember/
    /// EvedefID/IsFavorite/AddLevelCount/AddNormalSocketCount/GotUTC).</summary>
    public static PPE BuildPPE(long ppeId, int monsNo, int level, int grade, int waza0, int waza1, string nickname,
        int pdecoId = 0, int addLevel = 0, int addNormalSocket = 0, bool isPartyMember = false, bool isFavorite = false)
    {
        var x = new int[21];
        BaseSlots(monsNo, level, grade, waza0, waza1).CopyTo(x, 0);
        x[6] = pdecoId;                            // PdecoID, from Player.PdecoMounts
        x[12] = (int)(ppeId & 0xFFFFFFFF);        // PPEId_Low
        x[13] = (int)(ppeId >> 32);                // PPEId_High
        x[14] = isPartyMember ? 1 : 0;               // PartyMember, from Player.OwnedPPE.IsPartyMember
        x[15] = 0;                                  // EvedefID
        x[16] = isFavorite ? 1 : 0;                  // IsFavorite, from Player.OwnedPPE.IsFavorite
        x[17] = addLevel;                           // AddLevelCount, from Player.PPEAddLevels
        x[18] = addNormalSocket;                    // AddNormalSocketCount, from Player.PPEAddNormalSockets
        x[19] = 0;                                  // GotUTC_Low
        x[20] = 0;                                  // GotUTC_High
        return new PPE { X = x, N = nickname };
    }

    /// <summary>Builds a stage-offered PPEDrop (26 ints: base 12 + 12 zeroed
    /// equipment slots + PPEDropId low/high).</summary>
    public static PPEDrop BuildDrop(long dropId, int monsNo, int level, int grade, int waza0, int waza1)
    {
        var x = new int[26];
        BaseSlots(monsNo, level, grade, waza0, waza1).CopyTo(x, 0);
        // x[12..23] left zero: PPEDrop's own inline stat-roll preview
        // (BaseEqunit x3 sockets, see BuildEqunit) - no drop this server
        // makes pre-rolls one.
        x[24] = (int)(dropId & 0xFFFFFFFF);
        x[25] = (int)(dropId >> 32);
        return new PPEDrop { X = x };
    }

    /// <summary>Builds an owned Equnit (BaseEqunit.Index + Equnit.Index in
    /// out/dump/dump.cs: 4 base ints + EqunitId/PPEId/SocketNo/IsFavorite/
    /// GotUTC, 12 total) from a PlayerStore.OwnedEqunit.</summary>
    public static Equnit BuildEqunit(OwnedEqunit e)
    {
        var x = new int[12];
        x[0] = e.UnitPrefix;
        x[1] = e.PrefixGrade;
        x[2] = e.PrefixAddition0;
        x[3] = e.PrefixAddition1;
        x[4] = (int)(e.Id & 0xFFFFFFFF);          // EqunitId_Low
        x[5] = (int)(e.Id >> 32);                  // EqunitId_High
        x[6] = (int)(e.MountedPPEId & 0xFFFFFFFF); // PPEId_Low
        x[7] = (int)(e.MountedPPEId >> 32);        // PPEId_High
        x[8] = e.MountedSocketNo;                  // SocketNo
        x[9] = e.IsFavorite ? 1 : 0;                // IsFavorite
        x[10] = 0;                                  // GotUTC_Low
        x[11] = 0;                                  // GotUTC_High
        return new Equnit { X = x };
    }
}
