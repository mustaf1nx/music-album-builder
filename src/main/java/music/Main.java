package music;

import music.builder.AlbumBuilder;
import music.builder.AlbumDirector;
import music.builder.StudioAlbumBuilder;
import music.model.Album;

/**
 * Client of the Builder pattern.
 * Shows two ways to obtain a finished Album: through a Director for a
 * known, reusable configuration, and by driving the builder directly
 * for a bespoke one-off release.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(buildDeluxeReleaseWithDirector());
        System.out.println(buildBespokeReleaseManually());
        System.out.println(demonstrateValidationFailure());
    }

    private static Album buildDeluxeReleaseWithDirector() {
        AlbumDirector director = new AlbumDirector();
        AlbumBuilder builder = new StudioAlbumBuilder();
        director.constructDeluxeEdition(builder, "Neon Skyline", "Aria Waves", 2024, "M. Torres");
        return builder.build();
    }

    private static Album buildBespokeReleaseManually() {
        return new StudioAlbumBuilder()
                .title("Midnight Static")
                .artist("The Faraday Cage")
                .genre("Alt Rock")
                .releaseYear(2023)
                .producer("J. Kessler")
                .addTrack("Wavelength")
                .addTrack("Copper Wire")
                .addTrack("Silent Circuit")
                .markExplicit()
                .build();
    }

    private static String demonstrateValidationFailure() {
        try {
            new StudioAlbumBuilder().title("Untitled").build();
            return "Validation did not trigger as expected.";
        } catch (IllegalStateException expected) {
            return "Validation works as intended -> " + expected.getMessage();
        }
    }
}
