#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Multiplayer guest greeting - no other accounts on a single-player revival.</summary>
public sealed class GreetGuestUsersHandler : IEndpointHandler
{
    public string Endpoint => "GreetGuestUsers";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.GreetGuestUsers.Res { GreetedBaaSUserIds = new List<string>() };
}
