import java.util.List;

/** Stores the available songs; it does not depend on the facade. */
public final class SongCatalog {
    private final List<Song> songs = List.of(
            new Song("Midnight Circuit", "Pixel Echo", "Electronic", 225, 122, 7, 8, true),
            new Song("City Lights", "Luna Waves", "Electronic", 205, 118, 6, 8, true),
            new Song("Neon Horizon", "Nova Static", "Electronic", 245, 128, 9, 9, true),
            new Song("Blue Frequency", "Pixel Echo", "Electronic", 198, 116, 4, 7, true),
            new Song("Soft Satellites", "Luna Waves", "Electronic", 215, 110, 3, 7, true),
            new Song("Solar Rush", "Nova Static", "Electronic", 234, 132, 9, 10, true),
            new Song("Digital Sunrise", "Pixel Echo", "Electronic", 252, 125, 8, 9, true),
            new Song("Cloud Terminal", "Luna Waves", "Electronic", 210, 108, 3, 6, true),
            new Song("Unreleased Demo", "Studio Guest", "Electronic", 230, 124, 8, 8, false),
            new Song("Afterglow, Again", "Nova Static", "Electronic", 221, 120, 7, 8, true),
            new Song("Quiet Autumn", "Willow Park", "Jazz", 242, 85, 2, 4, true),
            new Song("Late Train", "Blue Avenue", "Jazz", 198, 94, 4, 5, true),
            new Song("Sunday Standard", "Willow Park", "Jazz", 216, 100, 5, 5, true),
            new Song("Cafe Night", "Blue Avenue", "Jazz", 207, 89, 3, 4, true),
            new Song("Open Road", "Northbound", "Rock", 255, 132, 8, 6, true),
            new Song("Mountain Signal", "Wild Current", "Rock", 230, 126, 7, 6, true),
            new Song("Runaway Sky", "Northbound", "Rock", 246, 138, 9, 7, true),
            new Song("Home Again", "Wild Current", "Rock", 205, 104, 4, 5, true)
    );

    public List<Song> findAll() {
        return songs;
    }
}
