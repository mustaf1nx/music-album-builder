import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/** Ranks tasks by importance per minute (a greedy heuristic, not knapsack optimization). */
public final class HighImpactStrategy implements PlanningStrategy {

    // Cross-multiplication avoids floating point errors in equal-ratio comparisons.
    private static final Comparator<StudyTask> IMPACT_PER_MINUTE = (left, right) -> {
        long rightScore = (long) right.importance() * left.durationMinutes();
        long leftScore = (long) left.importance() * right.durationMinutes();
        int comparison = Long.compare(rightScore, leftScore);
        if (comparison != 0) {
            return comparison;
        }
        comparison = left.dueDate().compareTo(right.dueDate());
        if (comparison != 0) {
            return comparison;
        }
        return left.id().compareTo(right.id());
    };

    @Override
    public List<StudyTask> selectTasks(List<StudyTask> tasks, int availableMinutes) {
        List<StudyTask> orderedTasks = new ArrayList<>(tasks);
        orderedTasks.sort(IMPACT_PER_MINUTE);
        return StrategySupport.selectFittingTasks(orderedTasks, availableMinutes);
    }
}
