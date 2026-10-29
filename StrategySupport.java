import java.util.ArrayList;
import java.util.List;
/** Small shared mechanism, not a strategy or a decision about which one to use. */
final class StrategySupport {

    private StrategySupport() {
    }

    static List<StudyTask> selectFittingTasks(List<StudyTask> orderedTasks, int budgetMinutes) {
        List<StudyTask> selected = new ArrayList<>();
        int remaining = budgetMinutes;

        for (StudyTask task : orderedTasks) {
            if (task.durationMinutes() <= remaining) {
                selected.add(task);
                remaining -= task.durationMinutes();
            }
        }
        return List.copyOf(selected);
    }
}
