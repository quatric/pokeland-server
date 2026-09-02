#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Upgrades an equnit's PrefixGrade by spending diamonds - see
/// PlayerStore.UpgradeEqunit for the persistence and cost placeholder.
/// </summary>
public sealed class EqunitUpgradeHandler : IEndpointHandler
{
    public string Endpoint => "EqunitUpgrade";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.EqunitUpgrade.Req)request;
        ctx.Players.UpgradeEqunit(req.TargetEqunitId, req.UseMulti == Pokeland.Protocol.Bool.True);
        return new Pokeland.Protocol.EqunitUpgrade.Res();
    }
}
