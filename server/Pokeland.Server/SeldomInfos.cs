#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// SeldomInfoBox.IsEndOfService/IsEndOfShop test whether the client's
/// country/market code appears in these filter strings - leaving them null
/// (the default) made every CCmCode match, which is what kept showing the
/// "End of Service Info" dialog on every screen. Empty strings mean "nothing
/// is filtered". Shared between Login (seeds it at boot) and GetSeldomInfo
/// (the client's own periodic re-check of the same box).
/// </summary>
public static class SeldomInfos
{
    public static SeldomInfoUser Current() => new()
    {
        EulaRev = 1,
        EndOfServiceCCmFilter = "",
        EndOfShopCCmFilter = "",
    };
}
