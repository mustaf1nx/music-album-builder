/**
 * implementor in the Bridge pattern
 * defines low-level audio output operations used by MusicPlayer abstractions */

public interface AudioOutput {
    void playSound(String trackName);

    void stopSound();
}
