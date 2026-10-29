# Assignment 5 — Strategy Pattern (Java)

**Topic:** Smart Study Session Planner  
**Requirements:** Java 17 or newer; no external libraries.

## Run

Extract all files into one folder, open a terminal in that folder and run:

```bash
javac *.java
java Main --demo
```

For interactive strategy selection:

```bash
java Main
```

## Strategy pattern

- **Strategy:** `PlanningStrategy` defines `selectTasks()`.
- **Concrete Strategies:** `DeadlineFirstStrategy`, `HighImpactStrategy`, `BalancedSubjectsStrategy` implement three different task-selection algorithms.
- **Context:** `StudyPlanner` keeps a `PlanningStrategy` reference, delegates execution through `createPlan()`, and permits runtime replacement via `setStrategy()`.
- **Client:** `ConsoleApp` creates and selects concrete strategies at runtime; `Main` starts the program.

## Clean Code principles (five examples)

1. **Single Responsibility:** each concrete strategy implements one scheduling algorithm; `ConsoleApp` handles input/output separately.
2. **Open/Closed Principle:** a fourth scheduling algorithm can be added by implementing `PlanningStrategy`, without modifying `StudyPlanner`.
3. **Polymorphism instead of conditional selection:** `StudyPlanner` calls `strategy.selectTasks()`; it does not use `if` or `switch` statements to detect strategy types.
4. **Meaningful names:** classes such as `DeadlineFirstStrategy` and methods such as `setStrategy` clearly describe their purpose.
5. **Encapsulation and immutability:** `StudyTask` and `StudyPlan` use immutable records and defensive copies of task lists to protect data.

The context does not depend on concrete strategy implementations. Confirm the topic is unique within the group and not a lecture example before submitting.
