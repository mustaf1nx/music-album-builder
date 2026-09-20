package music.player;

/**
 * Creator: declares the factory method createPlayer() and leaves the
 * decision of which concrete AudioPlayer to instantiate to its subclasses.
 * playTrack() is a small template method that depends only on the
 * AudioPlayer abstraction, never on a concrete player class.
 */
public abstract class AudioPlayerFactory {

    public abstract AudioPlayer createPlayer();

    public final void playTrack(String trackTitle) {
        requireNonBlankTitle(trackTitle);
        AudioPlayer player = createPlayer();
        player.play(trackTitle);
    }

    private void requireNonBlankTitle(String trackTitle) {
        if (trackTitle == null || trackTitle.isBlank()) {
            throw new IllegalArgumentException("Track title must not be blank.");
        }
    }
}
