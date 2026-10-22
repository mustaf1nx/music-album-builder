import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Orders songs to minimize abrupt BPM/key shifts while creating a mood-appropriate opening. */
public final class TransitionPlanner {
    public List<Song> orderForSmoothTransitions(List<Song> selected, String mood) {
        if (selected.isEmpty()) return List.of();
        List<Song> remaining = new ArrayList<>(selected);
        Comparator<Song> opening = Comparator.comparingInt(Song::energy)
                .thenComparingInt(Song::bpm).thenComparing(Song::title);
        if (mood.equals("relaxed")) opening = opening.reversed();
        Song current = remaining.stream().min(opening).orElseThrow();
        remaining.remove(current);
        List<Song> ordered = new ArrayList<>();
        ordered.add(current);
        while (!remaining.isEmpty()) {
            Song previous = current;
            current = remaining.stream()
                    .min(Comparator.comparingInt((Song next) -> transitionCost(previous, next))
                            .thenComparing(Song::title))
                    .orElseThrow();
            remaining.remove(current);
            ordered.add(current);
        }
        return List.copyOf(ordered);
    }

    public int transitionCost(Song first, Song second) {
        int keyDistance = Math.abs(first.harmonicKey() - second.harmonicKey());
        keyDistance = Math.min(keyDistance, 12 - keyDistance);
        return Math.abs(first.bpm() - second.bpm()) + 8 * keyDistance
                + 2 * Math.abs(first.energy() - second.energy());
    }
}
