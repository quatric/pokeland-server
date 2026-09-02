#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Other players' myslands for the Globe. No other accounts exist here.</summary>
public sealed class GetGuestMyslandsHandler : IEndpointHandler
{
    public string Endpoint => "GetGuestMyslands";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GetGuestMyslands.Res();
}
