import java.util.List;

/** Matches genre and energy range to the listener's mood. */
public final class MoodFilter {
    public List<Song> findMatching(List<Song> songs, String genre, String mood) {
        return songs.stream()
                .filter(song -> song.genre().equalsIgnoreCase(genre))
                .filter(song -> switch (mood) {
                    case "energetic" -> song.energy() >= 6;
                    case "relaxed" -> song.energy() <= 5;
                    default -> true;
                })
                .toList();
    }
}
