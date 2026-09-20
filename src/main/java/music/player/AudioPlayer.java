package music.player;

/**
 * Product interface for the Factory Method example.
 * Every concrete player must be able to play a track; how it decodes
 * and outputs audio is an implementation detail hidden from the client.
 */
public interface AudioPlayer {

    void play(String trackTitle);
}
