package music.builder;

import music.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete builder for a standard studio album release.
 * Keeps the in-progress state hidden from the client and only hands over
 * a finished, validated Album through build().
 */
public class StudioAlbumBuilder implements AlbumBuilder {

    private static final int MIN_RELEASE_YEAR = 1900;
    private static final int MIN_TRACK_COUNT = 1;
    private static final String DEFAULT_GENRE = "Unspecified";
    private static final String DEFAULT_PRODUCER = "Unknown";

    private String title;
    private String artist;
    private String genre = DEFAULT_GENRE;
    private int releaseYear;
    private final List<String> trackTitles = new ArrayList<>();
    private String producer = DEFAULT_PRODUCER;
    private boolean explicit;

    @Override
    public AlbumBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public AlbumBuilder artist(String artist) {
        this.artist = artist;
        return this;
    }

    @Override
    public AlbumBuilder genre(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public AlbumBuilder releaseYear(int year) {
        this.releaseYear = year;
        return this;
    }

    @Override
    public AlbumBuilder addTrack(String trackTitle) {
        trackTitles.add(trackTitle);
        return this;
    }

    @Override
    public AlbumBuilder producer(String producer) {
        this.producer = producer;
        return this;
    }

    @Override
    public AlbumBuilder markExplicit() {
        this.explicit = true;
        return this;
    }

    @Override
    public Album build() {
        validateBeforeBuild();
        return new Album(title, artist, genre, releaseYear, trackTitles, producer, explicit);
    }

    private void validateBeforeBuild() {
        requireTitle();
        requireArtist();
        requireValidReleaseYear();
        requireAtLeastOneTrack();
    }

    private void requireTitle() {
        if (title == null || title.isBlank()) {
            throw new IllegalStateException("Album title is required before calling build().");
        }
    }

    private void requireArtist() {
        if (artist == null || artist.isBlank()) {
            throw new IllegalStateException("Artist name is required before calling build().");
        }
    }

    private void requireValidReleaseYear() {
        if (releaseYear < MIN_RELEASE_YEAR) {
            throw new IllegalStateException("Release year must be " + MIN_RELEASE_YEAR + " or later.");
        }
    }

    private void requireAtLeastOneTrack() {
        if (trackTitles.size() < MIN_TRACK_COUNT) {
            throw new IllegalStateException("An album needs at least one track.");
        }
    }
}
