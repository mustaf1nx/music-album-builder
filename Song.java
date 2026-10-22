/** Immutable track metadata stored in the music catalog. */
public record Song(
        String title,
        String artist,
        String genre,
        int durationSeconds,
        int bpm,
        int energy,
        int harmonicKey,
        boolean commercialLicense
) {
    public Song {
        if (title == null || title.isBlank() || artist == null || artist.isBlank()
                || genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Song title, artist and genre are required.");
        }
        if (durationSeconds <= 0 || bpm <= 0 || energy < 1 || energy > 10
                || harmonicKey < 1 || harmonicKey > 12) {
            throw new IllegalArgumentException("Invalid track metadata for " + title);
        }
    }
}
