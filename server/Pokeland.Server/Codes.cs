#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// Island/stage identity on the wire.
///
/// FOUND (2026-09-01, headless Ghidra decompile of the EnumEmisCode helpers -
/// _MakeMem at RVA 0x129740C/0x1297414/0x129741C/0x1297434, _Create at
/// 0x1297448/0x129747C, and ToProto at 0x1297690/0x1297764): every island,
/// stage, mysland and team-battle identifier in this game is one packed 64-bit
/// "emisCode":
///
///     emisCode = (EvedefID  &amp; 0x1FF)      &lt;&lt; 54
///              | (myslandId &amp; 0xFFFFFF)   &lt;&lt; 30
///              | (IslandID  &amp; 0x7FFF)     &lt;&lt; 15
///              | (StageID   &amp; 0x7FFF);
///
/// and <c>ToProto</c> is just that unpacked back out into a small int array -
/// three elements for an IslandCode, four for a StageCode, in that same order.
/// So the wire arrays are plain field tuples, not an opaque blob, and the
/// server can mint them directly. <c>myslandId</c> is 0 for ordinary
/// (non-player-generated) islands.
/// </summary>
public static class Codes
{
    public static IslandCodeX[] Island(EvedefID evedefID, int islandID, int myslandId = 0)
        => new[] { (IslandCodeX)(int)evedefID, (IslandCodeX)myslandId, (IslandCodeX)islandID };

    public static StageCodeX[] Stage(EvedefID evedefID, int islandID, int stageID, int myslandId = 0)
        => new[] { (StageCodeX)(int)evedefID, (StageCodeX)myslandId,
                   (StageCodeX)islandID, (StageCodeX)stageID };
}
