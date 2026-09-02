#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Login bonus not implemented; ack.</summary>
public sealed class SetLoginBonusStateHandler : IEndpointHandler
{
    public string Endpoint => "SetLoginBonusState";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetLoginBonusState.Res();
}
