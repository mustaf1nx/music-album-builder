import java.util.Objects;

/** abstraction in the Bridge pattern
 *  keeps a reference to an AudioOutput implementor
*/
public abstract class MusicPlayer {
    protected AudioOutput audioOutput;

    protected MusicPlayer(AudioOutput audioOutput) {
        this.audioOutput = Objects.requireNonNull(audioOutput, "Audio output must not be null");
    }

    public void setAudioOutput(AudioOutput audioOutput) {
        this.audioOutput = Objects.requireNonNull(audioOutput, "Audio output must not be null");
    }

    public abstract void play();

    public void stop() {
        audioOutput.stopSound();
    }
}
