import java.nio.file.Path;
import java.util.List;

/** Facade: one simple operation orchestrates eight independent subsystems. */
public final class MusicPlaylistFacade {
    private final SongCatalog catalog;
    private final PlaylistRequestValidator validator;
    private final MoodFilter moodFilter;
    private final MusicRightsChecker rightsChecker;
    private final DurationOptimizer durationOptimizer;
    private final TransitionPlanner transitionPlanner;
    private final PlaylistStatistics statistics;
    private final CsvPlaylistExporter exporter;

    public MusicPlaylistFacade() {
        this(Path.of("."));
    }

    /** Alternative output location supports safe isolated use in automated tests. */
    public MusicPlaylistFacade(Path outputDirectory) {
        if (outputDirectory == null) {
            throw new IllegalArgumentException("Output directory must not be null.");
        }
        catalog = new SongCatalog();
        validator = new PlaylistRequestValidator();
        moodFilter = new MoodFilter();
        rightsChecker = new MusicRightsChecker();
        durationOptimizer = new DurationOptimizer();
        transitionPlanner = new TransitionPlanner();
        statistics = new PlaylistStatistics();
        exporter = new CsvPlaylistExporter(outputDirectory);
    }

    public PlaylistResult createPlaylist(PlaylistRequest request) {
        PlaylistRequest valid = validator.validate(request);
        List<Song> moodMatches = moodFilter.findMatching(catalog.findAll(), valid.genre(), valid.mood());
        List<Song> licensed = rightsChecker.filterAllowed(moodMatches, valid.commercialUse());
        List<Song> selected = durationOptimizer.selectTracks(licensed, valid.targetMinutes() * 60);
        List<Song> ordered = transitionPlanner.orderForSmoothTransitions(selected, valid.mood());
        PlaylistSummary summary = statistics.calculate(ordered);
        Path file = exporter.export(valid.playlistName(), ordered);
        return new PlaylistResult(
                valid.playlistName(), file, summary.songCount(), summary.durationSeconds(),
                summary.averageBpm(), summary.averageBpmJump(),
                ordered.stream().map(s -> s.artist() + " - " + s.title()).toList());
    }
}
