/* concrete implementor that represents playback through headphones */

public class HeadphonesOutput implements AudioOutput {
    @Override
    public void playSound(String trackName) {
        System.out.println("[Headphones] Playing privately: " + trackName);
    }

    @Override
    public void stopSound() {
        System.out.println("[Headphones] Playback stopped.");
    }
}
