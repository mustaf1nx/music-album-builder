import java.util.List;
/** Strategy: one interchangeable algorithm for selecting study tasks. */
public interface PlanningStrategy {

    /**
     * Returns selected tasks in execution order within the time budget.
     * The caller owns the input; implementations must not mutate it.
     */
    List<StudyTask> selectTasks(List<StudyTask> tasks, int availableMinutes);
}
