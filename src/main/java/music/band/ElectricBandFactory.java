package music.band;

public class ElectricBandFactory implements BandInstrumentFactory {

    @Override
    public Guitar createGuitar() {
        return new ElectricGuitar();
    }

    @Override
    public Keyboard createKeyboard() {
        return new SynthKeyboard();
    }
}
