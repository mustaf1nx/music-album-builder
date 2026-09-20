package music.player;

/**
 * Concrete Product: decodes and plays compressed MP3 audio.
 */
public class Mp3Player implements AudioPlayer {

    private static final int DEFAULT_SAMPLE_RATE_HZ = 44_100;

    @Override
    public void play(String trackTitle) {
        System.out.printf("Decoding MP3 stream at %d Hz and playing: %s%n",
                DEFAULT_SAMPLE_RATE_HZ, trackTitle);
    }
}
