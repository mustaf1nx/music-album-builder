package music.band;

public class AcousticBandFactory implements BandInstrumentFactory {

    @Override
    public Guitar createGuitar() {
        return new AcousticGuitar();
    }

    @Override
    public Keyboard createKeyboard() {
        return new AcousticPiano();
    }
}
