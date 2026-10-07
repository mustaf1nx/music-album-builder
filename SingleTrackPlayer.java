import java.util.Objects;

/* refined Abstraction that plays exactly one track */
public class SingleTrackPlayer extends MusicPlayer {
    private final String trackName;

    public SingleTrackPlayer(AudioOutput audioOutput, String trackName) {
        super(audioOutput);
        this.trackName = Objects.requireNonNull(trackName, "Track name must not be null");
    }

    @Override
    public void play() {
        System.out.println("Single track mode");
        audioOutput.playSound(trackName);
    }
}
