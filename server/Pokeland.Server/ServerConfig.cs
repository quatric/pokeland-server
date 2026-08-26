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
    /// Constant the 1.6.0 Android client appends to its AppManifest request
    /// (<c>/pre/AppManifest?market=GOOGLE&amp;magic=...</c>). Purely a gate; the
    /// value is hard-coded in the binary.
    /// </summary>
    public string Magic { get; set; } = "798d799c0ec24e1f0d7ff1f5a1a74cd9";

    /// <summary>Reject AppManifest requests whose magic does not match.</summary>
    public bool RequireMagic { get; set; } = false;

    /// <summary>
    /// AppVer -> AssetVer. AssetVer is a path prefix under the asset CDN root, so
    /// "1.6.0/740e9608db30b5f19e739442a779e2e2" resolves to
    /// <c>/pokeland/1.6.0/740e9608db30b5f19e739442a779e2e2/&lt;platform&gt;/&lt;bundle&gt;</c>.
    /// </summary>
    public Dictionary<string, string> AssetVersions { get; set; } = new()
    {
        ["1.6.0"] = "1.6.0/740e9608db30b5f19e739442a779e2e2",
    };

    /// <summary>
    /// Free-form "branch,revision,config" triple parsed by the client's VersionInfo.
    /// Retail shipped an opaque branch token; anything matching the shape works.
    /// </summary>
    public string VersionString { get; set; } = "revival,1,Release";

    /// <summary>Local directory mirroring the CDN tree (contains a "pokeland" folder).</summary>
    public string CdnRoot { get; set; } = "../../cdn";
}
