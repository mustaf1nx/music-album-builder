import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Dependency-free regression tests; run with: java MusicPlaylistTests */
public final class MusicPlaylistTests {
    private static int passed = 0;

    public static void main(String[] args) throws IOException {
        Path temp = Files.createTempDirectory("music-facade-tests-");
        try {
            MusicPlaylistFacade facade = new MusicPlaylistFacade(temp);
            PlaylistResult electronic = facade.createPlaylist(
                    new PlaylistRequest("Electronic Demo", "Electronic", "energetic", 18, true));
            check(electronic.trackCount() > 0, "creates a non-empty playlist");
            check(Files.isRegularFile(electronic.csvFile()), "exports CSV file");
            check(Math.abs(electronic.totalDurationSeconds() - 1080) <= 180,
                    "duration optimized to within 3 minutes");
            check(electronic.songs().stream().noneMatch(s -> s.contains("Unreleased Demo")),
                    "copyright restriction enforced");
            List<String> csvLines = Files.readAllLines(electronic.csvFile(), StandardCharsets.UTF_8);
            check(csvLines.size() == electronic.trackCount() + 1, "CSV header and song count");
            check(csvLines.get(0).startsWith("Position,Title"), "CSV has readable headings");
            check(csvLines.stream().noneMatch(s -> s.contains("Unreleased Demo")), "no unlicensed CSV entries");
            check(electronic.songs().size() == electronic.trackCount(), "result song count consistent");
            check(electronic.averageBpm() > 100, "BPM statistics calculated");
            check(facade.createPlaylist(new PlaylistRequest("Another Mix", "Electronic", "energetic", 18, true))
                    .songs().equals(electronic.songs()), "selection and order are deterministic");
            expectError(() -> facade.createPlaylist(
                    new PlaylistRequest("../escape", "Electronic", "balanced", 15, true)),
                    "rejects traversal in playlist name");
            expectError(() -> facade.createPlaylist(
                    new PlaylistRequest("Oops", "Electronic", "sleepy", 15, false)),
                    "rejects invalid mood");
            expectError(() -> facade.createPlaylist(
                    new PlaylistRequest("Oops", "Electronic", "balanced", 0, false)),
                    "rejects invalid target length");
            expectError(() -> facade.createPlaylist(
                    new PlaylistRequest("Oops", "Classical", "balanced", 15, false)),
                    "reports missing genre");
            PlaylistResult relaxed = facade.createPlaylist(
                    new PlaylistRequest("Quiet Mix", "Jazz", "relaxed", 12, true));
            check(relaxed.trackCount() > 0, "independent jazz workflow runs");
            PlaylistResult quoted = facade.createPlaylist(
                    new PlaylistRequest("Comma Test", "Electronic", "balanced", 40, false));
            check(Files.readString(quoted.csvFile()).contains("\"Afterglow, Again\""),
                    "CSV quotes fields containing commas");
            System.out.println("ALL " + passed + " TESTS PASSED");
        } finally {
            try (var files = Files.list(temp)) {
                for (Path file : files.toList()) Files.deleteIfExists(file);
            }
            Files.deleteIfExists(temp);
        }
    }

    private static void check(boolean condition, String description) {
        if (!condition) throw new AssertionError("FAILED: " + description);
        passed++;
        System.out.println("PASS: " + description);
    }

    private static void expectError(Runnable operation, String description) {
        try {
            operation.run();
        } catch (IllegalArgumentException | IllegalStateException expected) {
            check(true, description);
            return;
        }
        throw new AssertionError("FAILED: " + description);
    }
}
