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

    /// <summary>
    /// The event the server advertises as currently running. The client keys a
    /// lot of its Globe/Camp UI off this: <c>Intermission</c> (16) makes it show
    /// "We're getting ready for the next investigation", <c>Tutorial</c> (1)
    /// takes the tutorial branch. Whatever is set here must also appear in the
    /// Login Reset's Evedefs and EventScheduleSet lists - see LoginHandler.
    ///
    /// This is deliberately NOT Tutorial. The Globe loads the running event's
    /// main-Pokemon illustration by name, and EvedefDesc.json gives Tutorial an
    /// empty m_mainPokemonIllustAssetName (every real event has
    /// "IllustMainPokemon"); the evedef/tut bundle has no such asset either.
    /// Advertising Tutorial therefore throws "ArgumentException: The input
    /// asset name cannot be empty" on entering the Globe, and every island then
    /// NullReferences its way into an untextured magenta quad that swallows
    /// taps - which looks like missing island art and is not.
    /// </summary>
    public Pokeland.Protocol.EvedefID CurrentEvedefID { get; set; }
        = Pokeland.Protocol.EvedefID._1;
}
