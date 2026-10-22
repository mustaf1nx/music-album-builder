import java.nio.file.Path;
import java.util.List;

/** Simple facade result: only strings, numbers and a file location. */
public record PlaylistResult(
        String name,
        Path csvFile,
        int trackCount,
        int totalDurationSeconds,
        double averageBpm,
        double averageBpmJump,
        List<String> songs
) {
    public PlaylistResult {
        songs = List.copyOf(songs);
    }
}
