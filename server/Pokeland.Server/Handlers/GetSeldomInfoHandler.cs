#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The client's own periodic re-check of the End-of-Service/End-of-Shop box
/// (SeldomInfoBox). Login seeds it once at boot; this is what re-fetches it -
/// the empty-envelope stub answered with a null SeldomInfo AutoRes, which is
/// what left later CCmCode checks reading stale/absent data after the box's
/// TTL expired client-side.
/// </summary>
public sealed class GetSeldomInfoHandler : IEndpointHandler
{
    public string Endpoint => "GetSeldomInfo";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetSeldomInfo.Res
        {
            A = new[] { new AutoRes { SeldomInfo = SeldomInfos.Current() } },
        };
    }
}
