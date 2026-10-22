import java.util.List;

/** Computes performance and duration metrics without file I/O. */
public final class PlaylistStatistics {
    public PlaylistSummary calculate(List<Song> songs) {
        if (songs.isEmpty()) {
            throw new IllegalArgumentException("Cannot calculate statistics for an empty playlist.");
        }
        int seconds = songs.stream().mapToInt(Song::durationSeconds).sum();
        double averageBpm = songs.stream().mapToInt(Song::bpm).average().orElse(0);
        double totalJump = 0;
        for (int i = 1; i < songs.size(); i++) {
            totalJump += Math.abs(songs.get(i).bpm() - songs.get(i - 1).bpm());
        }
        double averageBpmJump = songs.size() == 1 ? 0 : totalJump / (songs.size() - 1);
        return new PlaylistSummary(songs.size(), seconds, averageBpm, averageBpmJump);
    }
}
