/* concrete implementor that represents playback through speakers */
public class SpeakersOutput implements AudioOutput {
    @Override
    public void playSound(String trackName) {
        System.out.println("[Speakers] Playing: " + trackName);
    }

    @Override
    public void stopSound() {
        System.out.println("[Speakers] Playback stopped.");
    }
}
