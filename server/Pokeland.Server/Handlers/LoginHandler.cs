#nullable disable
using Pokeland.Protocol;

namespace Pokeland.Server.Handlers;

/// <summary>
/// The first call every client makes after fetching the AppManifest. It is the only
/// endpoint that runs without a SessionID; its response hands one back, and every
/// later request echoes it in the envelope.
/// </summary>
public sealed class LoginHandler : IEndpointHandler
{
    public string Endpoint => "Login";

    public object Handle(object request, GameSession _, DispatchContext ctx)
    {
        var req = (Pokeland.Protocol.Login.Req)request;

        // The retail server authenticated the Nintendo BaaS bearer token here. A
        // revival deployment has no BaaS to check against, so identity comes from
        // whatever the client presents and every login opens a fresh session.
        var session = ctx.Sessions.Create(baasUserId: null);
        session.Market = req.Market;
        session.AppVer = req.AppVer;
        session.AssetVer = req.AssetVer;
        session.TimeZoneOffsetMinutes = req.TZOffsetMin;

        ctx.Log.LogInformation(
            "login: market={Market} appVer={AppVer} assetVer={AssetVer} tz={Tz} -> session {Session}",
            req.Market, req.AppVer, req.AssetVer, req.TZOffsetMin, session.SessionId);

        return new Pokeland.Protocol.Login.Res
        {
            SessionID = session.SessionId,

            // Reset[] tells the client which cached subsystems to drop. Empty means
            // "keep everything you have"; a fresh save is driven by the AutoRes
            // deltas on this and subsequent responses instead.
            Reset = Array.Empty<Reset>(),
        };
    }
}
