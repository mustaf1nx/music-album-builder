import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
/** Small offline dataset: no APIs, database, or internet required. */
public final class DemoData {

    private DemoData() {
    }

    public static List<StudyTask> sampleTasks(LocalDate today) {
        Objects.requireNonNull(today, "today");
        return List.of(
                task("T01", "Mathematics", "Algebra practice", today, 1, 35, 5),
                task("T02", "History", "Memorize key dates", today, 3, 20, 3),
                task("T03", "Physics", "Kinematics problems", today, 2, 45, 5),
                task("T04", "Mathematics", "Trigonometry review", today, 5, 25, 4),
                task("T05", "English", "Essay outline", today, 4, 30, 4),
                task("T06", "History", "Create a timeline", today, 2, 25, 2),
                task("T07", "Physics", "Review lab theory", today, 6, 30, 3),
                task("T08", "English", "Vocabulary flashcards", today, 1, 15, 2),
                task("T09", "Computer Science", "Python functions", today, 7, 40, 5),
                task("T10", "Computer Science", "Array exercises", today, 3, 25, 3));
    }

    private static StudyTask task(String id, String subject, String title,
                                  LocalDate today, int dueInDays, int minutes, int importance) {
        return new StudyTask(id, subject, title, today.plusDays(dueInDays), minutes, importance);
    }
}
