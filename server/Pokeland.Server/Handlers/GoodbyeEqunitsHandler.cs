#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Equnit goodbye - equipment system unimplemented; no C-Stop gifts pending.</summary>
public sealed class GoodbyeEqunitsHandler : IEndpointHandler
{
    public string Endpoint => "GoodbyeEqunits";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GoodbyeEqunits.Res { HaveCStopGifts = Pokeland.Protocol.Bool.False };
}
