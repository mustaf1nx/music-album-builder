import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 0/1 subset-sum optimization: chooses each track at most once, near the time limit. */
public final class DurationOptimizer {
    private static final int MAX_OVER_TARGET_SECONDS = 180;

    public List<Song> selectTracks(List<Song> candidates, int targetSeconds) {
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No tracks match the requested genre, mood and license.");
        }
        int maximumSeconds = targetSeconds + MAX_OVER_TARGET_SECONDS;
        // Each reachable duration has a selected list. Reverse iteration prevents reusing a song.
        Map<Integer, List<Song>> possible = new HashMap<>();
        possible.put(0, List.of());
        for (Song song : candidates) {
            List<Integer> existingDurations = new ArrayList<>(possible.keySet());
            existingDurations.sort((a, b) -> Integer.compare(b, a));
            for (int duration : existingDurations) {
                int next = duration + song.durationSeconds();
                if (next <= maximumSeconds && !possible.containsKey(next)) {
                    List<Song> selected = new ArrayList<>(possible.get(duration));
                    selected.add(song);
                    possible.put(next, List.copyOf(selected));
                }
            }
        }
        int best = possible.keySet().stream()
                .filter(seconds -> seconds > 0)
                .min((a, b) -> {
                    int byDifference = Integer.compare(Math.abs(a - targetSeconds), Math.abs(b - targetSeconds));
                    if (byDifference != 0) return byDifference;
                    // Prefer staying within the planned time in case of a tie.
                    return Integer.compare(a, b);
                })
                .orElseThrow(() -> new IllegalStateException("No track fits the requested duration."));
        if (best < targetSeconds / 2) {
            throw new IllegalStateException("Not enough matching music to build a useful playlist.");
        }
        return possible.get(best);
    }
}
