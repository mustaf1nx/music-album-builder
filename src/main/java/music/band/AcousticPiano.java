package music.band;

public class AcousticPiano implements Keyboard {

    @Override
    public void press() {
        System.out.println("Acoustic piano: hammer strikes the string directly.");
    }
}
