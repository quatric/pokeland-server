#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// SetDoneFlag as its own endpoint call (rather than piggybacked on another
/// request). GameDispatcher already applies the DoneFlagDiff generically
/// before routing - reading it here too would double-apply the same diff -
/// so this handler exists purely to answer with a plain ack instead of
/// falling through to the "no handler" stub warning.
/// </summary>
public sealed class SetDoneFlagHandler : IEndpointHandler
{
    public string Endpoint => "SetDoneFlag";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.SetDoneFlag.Res();
}
