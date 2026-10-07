import java.util.List;
import java.util.Objects;

/* refined Abstraction that plays a sequence of tracks */
public class PlaylistPlayer extends MusicPlayer {
    private final String playlistName;
    private final List<String> tracks;

    public PlaylistPlayer(AudioOutput audioOutput, String playlistName, List<String> tracks) {
        super(audioOutput);
        this.playlistName = Objects.requireNonNull(playlistName, "Playlist name must not be null");
        this.tracks = List.copyOf(Objects.requireNonNull(tracks, "Tracks must not be null"));
    }

    @Override
    public void play() {
        System.out.println("Playlist mode: " + playlistName);
        for (String track : tracks) {
            audioOutput.playSound(track);
        }
    }
}
