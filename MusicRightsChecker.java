import java.util.List;

/** Prevents tracks without commercial clearance from entering commercial sets. */
public final class MusicRightsChecker {
    public List<Song> filterAllowed(List<Song> songs, boolean commercialUse) {
        if (!commercialUse) {
            return List.copyOf(songs);
        }
        return songs.stream().filter(Song::commercialLicense).toList();
    }
}
