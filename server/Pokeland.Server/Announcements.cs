#nullable disable
using System.Collections.Generic;
using Pokeland.Protocol;

namespace Pokeland.Server;

/// <summary>
/// The revival server's own announcement feed. Retail announcements came
/// from a live-ops backend that no longer exists, so this is just a single
/// static "welcome" notice authored for this server rather than anything
/// extracted from the game's data - there is no unknown binary format
/// blocking a real one here, unlike equipment/decorations.
/// </summary>
public static class Announcements
{
    public const int WelcomeAnnouncementId = 1;

    public static Announcement Welcome => new()
    {
        AnnouncementId = WelcomeAnnouncementId,
        SupersedingAnnouncementId = 0,
        Type = AnnouncementType.Info,
        Title = new List<string>
        {
            "fr", "Bienvenue sur Pokéland !",
            "en", "Welcome to Pokeland!",
            "ja", "ポケランドへようこそ！",
        },
        Message0 = new List<string>
        {
            "fr", "Bienvenue sur le serveur de renaissance pour Pokémon Rumble Rush (Pokémon Scramble SP) !\n\n" +
                  "Certains systèmes (tournois, événements spéciaux) sont encore en cours de développement. " +
                  "Merci de jouer et bonne aventure !",
            "en", "This is a fan-run revival server for Pokemon Scramble SP, " +
                  "reconstructed after the original service shut down.\n\n" +
                  "Some systems (tournaments, live events) are still being worked on. " +
                  "Thanks for playing and enjoy your adventure!",
            "ja", "『ポケモンスクランブルSP』のファンサーバーへようこそ！\n\n" +
                  "現在も開発進行中です。冒険をお楽しみください！",
        },
        Message1 = new List<string>(),
        Image = new List<string>(),
        CreatedUTCStr = "2026-01-01T00:00:00Z",
        PopupEndUTCStr = "2099-01-01T00:00:00Z",
    };

    public static AnnouncementState StateFor(int headMarkedAsReadAnnouncementId) => new()
    {
        HeadAnnouncementId = WelcomeAnnouncementId,
        HeadValidAnnouncementId = WelcomeAnnouncementId,
        HeadMarkedAsReadAnnouncementId = headMarkedAsReadAnnouncementId,
        EmergencyInfoAnnouncementIds = System.Array.Empty<int>(),
        PopupAnnouncementIds = headMarkedAsReadAnnouncementId >= WelcomeAnnouncementId
            ? System.Array.Empty<int>()
            : new[] { WelcomeAnnouncementId },
    };
}
