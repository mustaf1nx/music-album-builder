package music.band;

public class SynthKeyboard implements Keyboard {

    @Override
    public void press() {
        System.out.println("Synth keyboard: oscillator generates a waveform electronically.");
    }
}
