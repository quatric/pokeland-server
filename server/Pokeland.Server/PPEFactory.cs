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
/// Equipment (BasePPEAndEqunits.Equnits, X[12..23]) is always zeroed - that
/// layout is still un-reverse-engineered and no drop this server makes
/// carries equipment yet.
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
    public static PPE BuildPPE(long ppeId, int monsNo, int level, int grade, int waza0, int waza1, string nickname)
    {
        var x = new int[21];
        BaseSlots(monsNo, level, grade, waza0, waza1).CopyTo(x, 0);
        x[12] = (int)(ppeId & 0xFFFFFFFF);        // PPEId_Low
        x[13] = (int)(ppeId >> 32);                // PPEId_High
        x[14] = 0;                                  // PartyMember
        x[15] = 0;                                  // EvedefID
        x[16] = 0;                                  // IsFavorite
        x[17] = 0;                                  // AddLevelCount
        x[18] = 0;                                  // AddNormalSocketCount
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
        // x[12..23] left zero: Equnits, un-RE'd, no drop carries equipment yet.
        x[24] = (int)(dropId & 0xFFFFFFFF);
        x[25] = (int)(dropId >> 32);
        return new PPEDrop { X = x };
    }
}
