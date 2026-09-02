#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>Opens a chest. Chests are never issued server-side yet, so every
/// open reports "not yet unlocked" rather than fabricating loot.</summary>
public sealed class OpenChestHandler : IEndpointHandler
{
    public string Endpoint => "OpenChest";

    public object Handle(object request, GameSession session, DispatchContext ctx)
        => new Pokeland.Protocol.OpenChest.Res
        {
            Result = OpenChestResult.ErrorNotYetUnlocked,
            HaveCStopGifts = Pokeland.Protocol.Bool.False,
        };
}
