#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Dismisses the CampStampCard popup after the client has read
/// LoginBonusInfo off Login. The Req carries no fields - the actual streak
/// advance/payout lives in PlayerStore.ClaimLoginBonus, run on Login - so
/// there's nothing left for this endpoint to do but ack.</summary>
public sealed class SetLoginBonusStateHandler : IEndpointHandler
{
    public string Endpoint => "SetLoginBonusState";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetLoginBonusState.Res();
}
