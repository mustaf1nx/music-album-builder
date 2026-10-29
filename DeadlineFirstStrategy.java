import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/** Prioritizes the nearest deadlines; importance breaks deadline ties. */
public final class DeadlineFirstStrategy implements PlanningStrategy {

    private static final Comparator<StudyTask> DEADLINE_ORDER =
            Comparator.comparing(StudyTask::dueDate)
                    .thenComparing(Comparator.comparingInt(StudyTask::importance).reversed())
                    .thenComparingInt(StudyTask::durationMinutes)
                    .thenComparing(StudyTask::id);

    @Override
    public List<StudyTask> selectTasks(List<StudyTask> tasks, int availableMinutes) {
        List<StudyTask> orderedTasks = new ArrayList<>(tasks);
        orderedTasks.sort(DEADLINE_ORDER);
        return StrategySupport.selectFittingTasks(orderedTasks, availableMinutes);
    }
}
