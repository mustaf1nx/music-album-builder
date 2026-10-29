import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Immutable, validated result of applying a planning algorithm. */
public record StudyPlan(List<StudyTask> tasks, int budgetMinutes) {

    public StudyPlan {
        Objects.requireNonNull(tasks, "tasks");
        tasks = List.copyOf(tasks);
        if (budgetMinutes < 0) {
            throw new IllegalArgumentException("budgetMinutes must not be negative");
        }

        Set<String> scheduledTaskIds = new HashSet<>();
        int remaining = budgetMinutes;
        for (StudyTask task : tasks) {
            if (!scheduledTaskIds.add(task.id())) {
                throw new IllegalArgumentException("Duplicate scheduled task ID: " + task.id());
            }
            if (task.durationMinutes() > remaining) {
                throw new IllegalArgumentException("Scheduled tasks exceed time budget");
            }
            remaining -= task.durationMinutes();
        }
    }

    public int scheduledMinutes() {
        return tasks.stream().mapToInt(StudyTask::durationMinutes).sum();
    }

    public int remainingMinutes() {
        return budgetMinutes - scheduledMinutes();
    }
}
