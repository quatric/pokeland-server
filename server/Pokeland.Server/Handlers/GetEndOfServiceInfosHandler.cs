#nullable disable
using Pokeland.Server;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Boot-time check the client uses to decide whether to show its "service has
/// ended" screen. Leaving this endpoint unimplemented meant the dispatcher's
/// default empty-envelope response (see GameDispatcher's "no handler" path)
/// came back with AnnouncementId fields defaulted to 0 but with none of the
/// BaseAutoRes envelope machinery a real handler fills in - the client reads
/// that as a malformed/untrusted response and falls back to the generic
/// "Unable to connect to the server" dialog instead of proceeding. Answering
/// with explicit zero IDs (no announcement, i.e. not end of service/shop) is
/// exactly what live retail returned before EOS.
/// </summary>
public sealed class GetEndOfServiceInfosHandler : IEndpointHandler
{
    public string Endpoint => "GetEndOfServiceInfos";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        return new Pokeland.Protocol.GetEndOfServiceInfos.Res
        {
            EndOfServiceAnnouncementId = 0,
            EndOfShopAnnouncementId = 0,
            RefundCode = "",
        };
    }
}
