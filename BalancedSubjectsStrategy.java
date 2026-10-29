import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/** Takes at most one fitting task from each subject per round. */
public final class BalancedSubjectsStrategy implements PlanningStrategy {

    private static final Comparator<StudyTask> WITHIN_SUBJECT_ORDER =
            Comparator.comparing(StudyTask::dueDate)
                    .thenComparing(Comparator.comparingInt(StudyTask::importance).reversed())
                    .thenComparing(StudyTask::id);

    @Override
    public List<StudyTask> selectTasks(List<StudyTask> tasks, int availableMinutes) {
        Map<String, List<StudyTask>> grouped = new TreeMap<>();
        for (StudyTask task : tasks) {
            grouped.computeIfAbsent(task.subject(), unused -> new ArrayList<>()).add(task);
        }

        Map<String, Deque<StudyTask>> queues = new TreeMap<>();
        for (Map.Entry<String, List<StudyTask>> entry : grouped.entrySet()) {
            entry.getValue().sort(WITHIN_SUBJECT_ORDER);
            queues.put(entry.getKey(), new ArrayDeque<>(entry.getValue()));
        }

        List<StudyTask> selected = new ArrayList<>();
        int remaining = availableMinutes;
        boolean taskSelectedInRound;
        do {
            taskSelectedInRound = false;
            for (Deque<StudyTask> subjectQueue : queues.values()) {
                while (!subjectQueue.isEmpty()) {
                    StudyTask candidate = subjectQueue.removeFirst();
                    if (candidate.durationMinutes() <= remaining) {
                        selected.add(candidate);
                        remaining -= candidate.durationMinutes();
                        taskSelectedInRound = true;
                        break; // Next subject: fair, round-robin choice.
                    }
                    // This task cannot fit now or later, so continue within this subject.
                }
            }
        } while (taskSelectedInRound && remaining > 0);

        return List.copyOf(selected);
    }
}
