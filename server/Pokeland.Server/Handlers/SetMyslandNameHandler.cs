#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Renames the player's mysland. The single mysland always reports
/// World's fixed name today; ack without persisting until Mysland naming is wired
/// into World/PlayerStore.</summary>
public sealed class SetMyslandNameHandler : IEndpointHandler
{
    public string Endpoint => "SetMyslandName";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetMyslandName.Res();
}
