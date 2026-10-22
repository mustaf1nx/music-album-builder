# Music Playlist Facade — SDP Assignment #4

A **Java 17+** project showing the structural **Facade Design Pattern** through an automated DJ playlist creator. Every project file is in this same folder. No packages, `src/`, Maven dependencies, or external music services are required.

## Run

From the folder with the `.java` files:

```bash
javac *.java
java Main
```

`Main` creates `Night_Drive_Mix.csv` in the current directory and prints a summary. The export is a real CSV playlist/setlist of **fictional tracks**, not downloadable or playable audio files. To try another playlist, change the arguments in `Main.java`.

## Run tests

```bash
javac *.java
java MusicPlaylistTests
```

The test runner requires no JUnit installation. It checks generation, CSV output, duration matching, licenses, deterministic selection, validation, and CSV quoting. It creates test files in a temporary directory and deletes them.

## Facade

The client **`Main` only calls `MusicPlaylistFacade`** and exchanges two simple data records: `PlaylistRequest` and `PlaylistResult`. A single public business operation simplifies eight subsystem calls:

```java
MusicPlaylistFacade facade = new MusicPlaylistFacade();
PlaylistResult result = facade.createPlaylist(
    new PlaylistRequest("Night Drive Mix", "Electronic", "energetic", 18, true));
System.out.println(result.csvFile());
```

### Subsystem responsibilities and collaboration

1. `PlaylistRequestValidator` checks request values and safe filenames.
2. `SongCatalog` provides a catalog of 18 fictional tracks.
3. `MoodFilter` filters the library by genre and energy.
4. `MusicRightsChecker` excludes music without commercial clearance when requested.
5. `DurationOptimizer` uses a 0/1 subset-sum approach to approach the requested playtime without song duplicates.
6. `TransitionPlanner` reorders the selection based on BPM, harmonic wheel key, and energy differences.
7. `PlaylistStatistics` calculates the duration, average BPM, and average BPM transition gap.
8. `CsvPlaylistExporter` escapes text and writes the UTF-8 playlist file.

**Order:** validate → load → filter by mood → check rights → optimize duration → order transitions → calculate statistics → export CSV → return receipt.

Each subsystem can be used independently by an advanced client and has no reference back to the facade. `Main`, the ordinary client, never creates or invokes them directly.

## Why Facade is suitable

Without the facade, a client would have to construct eight services, know their contracts, pass intermediate collections between each one, enforce rights before exporting, and handle I/O exceptions. With a facade, the client performs one call and receives a simple result.

### At least five Clean Code principles

1. **Meaningful names:** `createPlaylist`, `filterAllowed`, and `orderForSmoothTransitions` describe intent.
2. **Single Responsibility Principle:** validation, library data, copyright, optimization, ordering, statistics, and I/O are separate.
3. **Encapsulation:** callers work with `PlaylistRequest` and `PlaylistResult`, not `Song` or any subsystem service.
4. **Small focused methods:** each class performs one stage with clear inputs and outputs.
5. **Exception handling:** invalid input gives informative `IllegalArgumentException`; empty selection or failed CSV writes give `IllegalStateException`.
6. **Immutability:** `Song`, request/result, and output song lists are immutable records or copies.
7. **Avoid duplication:** a shared transition-cost function is used inside the transition planner.
8. **Testability:** output directory injection enables isolated file tests.

## For the paper defense

- **Pattern type:** structural (Facade).
- **Facade component:** `MusicPlaylistFacade`.
- **Subsystem components:** the eight classes described above.
- **Client component:** `Main`.
- **Simplified API:** `createPlaylist(PlaylistRequest)`.
- **What it does not do:** the facade does not implement track filtering or CSV writing itself; it delegates them.
- **Difference from Adapter:** Adapter changes an interface to make incompatible interfaces fit; Facade offers one simple entry point into multiple existing subsystem interfaces.
- **Downside:** the facade can grow too large if unrelated features keep being added; changes in the subsystem workflow may require updating orchestration.
- **Advanced users:** subsystem classes remain public and independently callable.

## File structure

All `.java` files plus this `README.md` are at one level. Compiled `.class` files and generated `.csv` are output files and are not included in the submission ZIP.

**Submission:** upload all source `.java` files and the README into the root of a GitHub repository. Run on Java 17 or later.
