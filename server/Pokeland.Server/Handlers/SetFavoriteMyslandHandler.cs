#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Favorites a mysland. No favorites list is surfaced anywhere yet; ack.</summary>
public sealed class SetFavoriteMyslandHandler : IEndpointHandler
{
    public string Endpoint => "SetFavoriteMysland";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetFavoriteMysland.Res();
}
