import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
/** Client: creates concrete strategies and selects one without type checks. */
public final class ConsoleApp {

    private static final int CHANGE_BUDGET_OPTION = 4;
    private static final int EXIT_OPTION = 0;

    private final Scanner scanner;
    private final PrintStream out;
    private final Map<Integer, StrategyOption> options;
    private final StudyPlanner planner;
    private final List<StudyTask> tasks;
    private int budgetMinutes = 95;

    public ConsoleApp(Scanner scanner, PrintStream out) {
        this.scanner = Objects.requireNonNull(scanner, "scanner");
        this.out = Objects.requireNonNull(out, "out");
        this.options = createOptions();
        this.planner = new StudyPlanner(options.get(1).strategy());
        this.tasks = DemoData.sampleTasks(LocalDate.now());
    }

    public void runInteractive() {
        printIntroduction();
        while (true) {
            out.println();
            printOptions();
            out.print("Choose an option: ");
            String input = readLineOrExit();
            Integer optionNumber = parseNumber(input);

            if (optionNumber == null) {
                out.println("Please enter a number shown in the menu.");
                continue;
            }
            if (optionNumber == EXIT_OPTION) {
                out.println("Goodbye!");
                return;
            }
            if (optionNumber == CHANGE_BUDGET_OPTION) {
                updateBudget();
                continue;
            }

            StrategyOption chosen = options.get(optionNumber);
            if (chosen == null) {
                out.println("Unknown option. Try again.");
                continue;
            }

            planner.setStrategy(chosen.strategy());
            printPlan(chosen.label(), planner.createPlan(tasks, budgetMinutes));
        }
    }

    /** Runs all three strategies on one context; useful for grading or a demo. */
    public void runDemo() {
        printIntroduction();
        for (StrategyOption option : options.values()) {
            planner.setStrategy(option.strategy());
            printPlan(option.label(), planner.createPlan(tasks, budgetMinutes));
        }
        out.println("All three algorithms used the SAME StudyPlanner context instance.");
    }

    private void printIntroduction() {
        out.println("==============================================================");
        out.println(" SMART STUDY SESSION PLANNER | Strategy Design Pattern (Java)");
        out.println("==============================================================");
        out.println("Available study time: " + budgetMinutes + " minutes");
        out.println("Tasks for today: " + tasks.size());
        out.println();
        out.printf("%-4s %-17s %-25s %-12s %5s %6s%n",
                "ID", "SUBJECT", "TASK", "DUE", "MINS", "LEVEL");
        for (StudyTask task : tasks) {
            out.printf("%-4s %-17s %-25s %-12s %5d %6d%n",
                    task.id(), task.subject(), task.title(), task.dueDate(),
                    task.durationMinutes(), task.importance());
        }
    }

    private void printOptions() {
        out.println("Strategies (change during program execution):");
        for (Map.Entry<Integer, StrategyOption> option : options.entrySet()) {
            out.println("  " + option.getKey() + ". " + option.getValue().label());
        }
        out.println("  " + CHANGE_BUDGET_OPTION + ". Change available study time");
        out.println("  " + EXIT_OPTION + ". Exit");
    }

    private void updateBudget() {
        out.print("New time budget in minutes (0 or greater): ");
        Integer proposedBudget = parseNumber(readLineOrExit());
        if (proposedBudget == null || proposedBudget < 0) {
            out.println("Invalid budget. Keeping " + budgetMinutes + " minutes.");
            return;
        }
        budgetMinutes = proposedBudget;
        out.println("Updated budget: " + budgetMinutes + " minutes.");
    }

    private void printPlan(String strategyName, StudyPlan plan) {
        out.println();
        out.println("--- " + strategyName + " ---");
        if (plan.tasks().isEmpty()) {
            out.println("No tasks fit the current time budget.");
        }
        int position = 1;
        for (StudyTask task : plan.tasks()) {
            out.printf("  %d. [%s] %s - %s (%d min; importance %d; due %s)%n",
                    position++, task.id(), task.subject(), task.title(),
                    task.durationMinutes(), task.importance(), task.dueDate());
        }
        out.printf("Selected: %d task(s) | Scheduled: %d min | Remaining: %d min%n",
                plan.tasks().size(), plan.scheduledMinutes(), plan.remainingMinutes());
    }

    private String readLineOrExit() {
        return scanner.hasNextLine() ? scanner.nextLine().strip() : "0";
    }

    private static Integer parseNumber(String text) {
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Map<Integer, StrategyOption> createOptions() {
        Map<Integer, StrategyOption> choices = new LinkedHashMap<>();
        choices.put(1, new StrategyOption("Deadline First", new DeadlineFirstStrategy()));
        choices.put(2, new StrategyOption("High Impact per Minute", new HighImpactStrategy()));
        choices.put(3, new StrategyOption("Balanced Subjects", new BalancedSubjectsStrategy()));
        return Collections.unmodifiableMap(choices);
    }

    private record StrategyOption(String label, PlanningStrategy strategy) {
    }
}
