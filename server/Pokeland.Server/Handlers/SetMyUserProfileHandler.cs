#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// Fired whenever the trainer card is edited (Profile screen's pencil icon)
/// and once automatically right after every Login. The empty-envelope stub
/// acked the write but never stored it, so a renamed trainer reverted to
/// "Trainer" on the next GetMyUserProfile refresh or app restart.
/// </summary>
public sealed class SetMyUserProfileHandler : IEndpointHandler
{
    public string Endpoint => "SetMyUserProfile";

    public object Handle(object request, GameSession session, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.SetMyUserProfile.Req)request;
        ctx.Players.ApplyProfile(req.MyUserProfile);
        return new Pokeland.Protocol.SetMyUserProfile.Res();
    }
}
