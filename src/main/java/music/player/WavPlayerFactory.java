package music.player;

/**
 * Concrete Creator: the only class that knows WavPlayer exists.
 */
public class WavPlayerFactory extends AudioPlayerFactory {

    @Override
    public AudioPlayer createPlayer() {
        return new WavPlayer();
    }
}
