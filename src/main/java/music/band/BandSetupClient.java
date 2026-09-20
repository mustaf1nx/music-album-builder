package music.band;

/**
 * Client: rehearses a band using a matched instrument family.
 * It only ever talks to BandInstrumentFactory, Guitar and Keyboard -
 * it never references AcousticGuitar, SynthKeyboard, etc. directly,
 * so it cannot accidentally mix incompatible families.
 */
public class BandSetupClient {

    private final Guitar guitar;
    private final Keyboard keyboard;

    public BandSetupClient(BandInstrumentFactory factory) {
        requireFactory(factory);
        this.guitar = factory.createGuitar();
        this.keyboard = factory.createKeyboard();
    }

    public void rehearse() {
        guitar.strum();
        keyboard.press();
    }

    private void requireFactory(BandInstrumentFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("A BandInstrumentFactory is required to set up a band.");
        }
    }
}
