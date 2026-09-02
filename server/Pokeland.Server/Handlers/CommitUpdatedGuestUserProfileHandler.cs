#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Syncs cached guest-profile memos/favorites. No guest profiles exist here.</summary>
public sealed class CommitUpdatedGuestUserProfileHandler : IEndpointHandler
{
    public string Endpoint => "CommitUpdatedGuestUserProfile";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.CommitUpdatedGuestUserProfile.Res();
}
