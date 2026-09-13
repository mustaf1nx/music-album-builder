package music.builder;

/**
 * Knows the recipe for a few standard, reusable album configurations.
 * It depends only on the AlbumBuilder interface, so it can drive any
 * concrete builder without knowing how a step is actually implemented.
 */
public class AlbumDirector {

    private static final String RADIO_GENRE = "Pop";
    private static final String DELUXE_GENRE = "Pop";

    public void constructRadioSingleEdition(AlbumBuilder builder, String title, String artist, int year) {
        builder.title(title)
               .artist(artist)
               .releaseYear(year)
               .genre(RADIO_GENRE)
               .addTrack(title + " (Radio Edit)")
               .addTrack(title + " (Instrumental)");
    }

    public void constructDeluxeEdition(AlbumBuilder builder,
                                        String title,
                                        String artist,
                                        int year,
                                        String producer) {
        builder.title(title + " (Deluxe Edition)")
               .artist(artist)
               .releaseYear(year)
               .producer(producer)
               .genre(DELUXE_GENRE)
               .addTrack("Intro")
               .addTrack(title)
               .addTrack(title + " (Acoustic Version)")
               .addTrack("Bonus Track");
    }
}
