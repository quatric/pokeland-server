#nullable disable
namespace Pokeland.Server;

public sealed class ServerConfig
{
    /// <summary>
    /// Protocol version. The client posts to <c>/{ApiVersion}/game</c>, baked in as
    /// <c>Uskumru.Proto.Endpoint.Version</c>, so it must match the client build.
    /// </summary>
    public string ApiVersion { get; set; } = "1.600";

    /// <summary>
    /// Constants the clients append to their AppManifest request
    /// (<c>/pre/AppManifest?market=GOOGLE&amp;magic=...</c>). Purely a gate; each
    /// store build has its own value hard-coded in the binary.
    /// </summary>
    public Dictionary<string, string> Magic { get; set; } = new()
    {
        ["GOOGLE"] = "798d799c0ec24e1f0d7ff1f5a1a74cd9",   // Android 1.6.0
        ["APPLE"]  = "f388d2d02c48702efacde9ca0d977b45",   // iOS 1.6.1
    };

    /// <summary>Reject AppManifest requests whose magic does not match.</summary>
    public bool RequireMagic { get; set; } = false;

    /// <summary>
    /// AppVer -> AssetVer. AssetVer is a path prefix under the asset CDN root, so
    /// "1.6.0/740e9608db30b5f19e739442a779e2e2" resolves to
    /// <c>/pokeland/1.6.0/740e9608db30b5f19e739442a779e2e2/&lt;platform&gt;/&lt;bundle&gt;</c>.
    /// </summary>
    /// <remarks>
    /// iOS 1.6.1 is mapped onto the 1.6.0 asset set: no 1.6.1 tree was ever
    /// archived, and 1.6.0 -> 1.6.1 was a patch bump. AssetVer is just a path
    /// prefix, so the client will happily fetch it.
    /// </remarks>
    public Dictionary<string, string> AssetVersions { get; set; } = new()
    {
        ["1.6.0"] = "1.6.0/740e9608db30b5f19e739442a779e2e2",
        ["1.6.1"] = "1.6.0/740e9608db30b5f19e739442a779e2e2",
    };

    /// <summary>
    /// Free-form "branch,revision,config" triple parsed by the client's VersionInfo.
    /// Retail shipped an opaque branch token; anything matching the shape works.
    /// </summary>
    public string VersionString { get; set; } = "revival,1,Release";

    /// <summary>Local directory mirroring the CDN tree (contains a "pokeland" folder).</summary>
    public string CdnRoot { get; set; } = "../../cdn";

    /// <summary>
    /// Two-letter country handed back by pokemon-webapi's estimate_country. The
    /// client turns this into its CCmCode, which drives region gating - see the
    /// EndOfServiceCCmFilter note in LoginHandler.
    /// </summary>
    public string Country { get; set; } = "US";
}
