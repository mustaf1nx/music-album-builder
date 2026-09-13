package music.model;

import java.util.Collections;
import java.util.List;

/**
 * The complex object produced by the Builder pattern.
 * An Album is immutable once constructed: all fields are set through the
 * constructor called by a builder's build() method, never modified after.
 */
public final class Album {

    private final String title;
    private final String artist;
    private final String genre;
    private final int releaseYear;
    private final List<String> trackTitles;
    private final String producer;
    private final boolean explicit;

    public Album(String title,
                 String artist,
                 String genre,
                 int releaseYear,
                 List<String> trackTitles,
                 String producer,
                 boolean explicit) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.trackTitles = Collections.unmodifiableList(trackTitles);
        this.producer = producer;
        this.explicit = explicit;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<String> getTrackTitles() {
        return trackTitles;
    }

    public String getProducer() {
        return producer;
    }

    public boolean isExplicit() {
        return explicit;
    }

    @Override
    public String toString() {
        return formatHeader() + formatTrackList();
    }

    private String formatHeader() {
        String explicitTag = explicit ? " [Explicit]" : "";
        return String.format("%s by %s (%d, %s) - Producer: %s%s%n",
                title, artist, releaseYear, genre, producer, explicitTag);
    }

    private String formatTrackList() {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < trackTitles.size(); index++) {
            builder.append(formatTrackLine(index + 1, trackTitles.get(index)));
        }
        return builder.toString();
    }

    private String formatTrackLine(int trackNumber, String trackTitle) {
        return String.format("  %d. %s%n", trackNumber, trackTitle);
    }
}
