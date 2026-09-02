#nullable disable
using System.Linq;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Gift claim history - PlayerStore.ReceivedGifts, paged the same way the Req asks for.</summary>
public sealed class GetReceivedGiftsHandler : IEndpointHandler
{
    public string Endpoint => "GetReceivedGifts";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.GetReceivedGifts.Req)request;
        return new Pokeland.Protocol.GetReceivedGifts.Res
        {
            Gifts = ctx.Players.ReceivedGifts(req.Skip, req.Take).Select(World.Gift).ToList(),
        };
    }
}
