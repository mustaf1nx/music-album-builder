import java.time.LocalDate;
import java.util.Objects;

/** Immutable input describing one indivisible study task. */
public record StudyTask(
        String id,
        String subject,
        String title,
        LocalDate dueDate,
        int durationMinutes,
        int importance) {

    public StudyTask {
        id = requireNonBlank(id, "id");
        subject = requireNonBlank(subject, "subject");
        title = requireNonBlank(title, "title");
        Objects.requireNonNull(dueDate, "dueDate");

        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("durationMinutes must be positive");
        }
        if (importance < 1 || importance > 5) {
            throw new IllegalArgumentException("importance must be between 1 and 5");
        }
    }

    private static String requireNonBlank(String value, String field) {
        Objects.requireNonNull(value, field);
        String normalized = value.strip();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return normalized;
    }
}
