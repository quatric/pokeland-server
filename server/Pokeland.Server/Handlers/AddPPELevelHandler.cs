#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Levels up a PPE. PPE system unimplemented; ack.</summary>
public sealed class AddPPELevelHandler : IEndpointHandler
{
    public string Endpoint => "AddPPELevel";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.AddPPELevel.Res();
}
