import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Exports a real, UTF-8 CSV file; quoted fields are safely escaped. */
public final class CsvPlaylistExporter {
    private final Path outputDirectory;

    public CsvPlaylistExporter(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public Path export(String playlistName, List<Song> songs) {
        String fileName = playlistName.replace(' ', '_') + ".csv";
        Path output = outputDirectory.resolve(fileName);
        List<String> lines = new ArrayList<>();
        lines.add("Position,Title,Artist,Genre,Duration Seconds,BPM,Energy,Commercial License");
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            lines.add(String.join(",",
                    String.valueOf(i + 1), quote(song.title()), quote(song.artist()), quote(song.genre()),
                    String.valueOf(song.durationSeconds()), String.valueOf(song.bpm()),
                    String.valueOf(song.energy()), String.valueOf(song.commercialLicense())));
        }
        try {
            Files.createDirectories(outputDirectory);
            Files.write(output, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not export playlist CSV: " + output, exception);
        }
        return output.toAbsolutePath().normalize();
    }

    private String quote(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
