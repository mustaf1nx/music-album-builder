package music.player;

/**
 * Concrete Creator: the only class that knows Mp3Player exists.
 */
public class Mp3PlayerFactory extends AudioPlayerFactory {

    @Override
    public AudioPlayer createPlayer() {
        return new Mp3Player();
    }
}
