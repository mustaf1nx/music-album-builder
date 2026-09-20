package music.player;

/**
 * Concrete Product: plays uncompressed WAV audio directly from PCM data.
 */
public class WavPlayer implements AudioPlayer {

    private static final int DEFAULT_SAMPLE_RATE_HZ = 48_000;

    @Override
    public void play(String trackTitle) {
        System.out.printf("Reading raw PCM data at %d Hz and playing: %s%n",
                DEFAULT_SAMPLE_RATE_HZ, trackTitle);
    }
}
