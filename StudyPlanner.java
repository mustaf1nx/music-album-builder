import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
/** Context: stores an interchangeable Strategy and delegates the planning work. */
public final class StudyPlanner {

    private PlanningStrategy strategy;

    public StudyPlanner(PlanningStrategy strategy) {
        setStrategy(strategy);
    }

    /** Replace the algorithm while keeping the very same context object. */
    public void setStrategy(PlanningStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public StudyPlan createPlan(List<StudyTask> tasks, int availableMinutes) {
        Objects.requireNonNull(tasks, "tasks");
        if (availableMinutes < 0) {
            throw new IllegalArgumentException("availableMinutes must not be negative");
        }

        List<StudyTask> safeTasks = List.copyOf(tasks);
        ensureUniqueTaskIds(safeTasks);
        return new StudyPlan(strategy.selectTasks(safeTasks, availableMinutes), availableMinutes);
    }

    private static void ensureUniqueTaskIds(List<StudyTask> tasks) {
        Set<String> ids = new HashSet<>();
        for (StudyTask task : tasks) {
            if (!ids.add(task.id())) {
                throw new IllegalArgumentException("Duplicate input task ID: " + task.id());
            }
        }
    }
}
