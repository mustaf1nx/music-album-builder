import java.util.Locale;
import java.util.Set;

/** Validates user settings before processing starts. */
public final class PlaylistRequestValidator {
    private static final Set<String> MOODS = Set.of("energetic", "relaxed", "balanced");

    public PlaylistRequest validate(PlaylistRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Playlist request cannot be null.");
        }
        if (request.playlistName() == null || !request.playlistName().trim().matches("[\\p{L}\\p{N} _-]{1,60}")) {
            throw new IllegalArgumentException("Playlist name must contain 1-60 letters, digits, spaces, - or _.");
        }
        if (request.genre() == null || request.genre().isBlank()) {
            throw new IllegalArgumentException("Genre must not be empty.");
        }
        if (request.mood() == null || !MOODS.contains(request.mood().trim().toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("Mood must be energetic, relaxed or balanced.");
        }
        if (request.targetMinutes() < 5 || request.targetMinutes() > 60) {
            throw new IllegalArgumentException("Target length must be between 5 and 60 minutes.");
        }
        return new PlaylistRequest(
                request.playlistName().trim(), request.genre().trim(),
                request.mood().trim().toLowerCase(Locale.ROOT),
                request.targetMinutes(), request.commercialUse()
        );
    }
}
