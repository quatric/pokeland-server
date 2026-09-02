#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Upgrades (evolves/fuses) an equnit. Equipment system unimplemented; ack.</summary>
public sealed class EqunitUpgradeHandler : IEndpointHandler
{
    public string Endpoint => "EqunitUpgrade";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.EqunitUpgrade.Res();
}
