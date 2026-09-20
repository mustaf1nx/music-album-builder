package music.band;

/**
 * Abstract Factory: declares one creation method per product type in the
 * family (Guitar, Keyboard). A concrete factory guarantees that whatever
 * it produces belongs to the same, mutually compatible family.
 */
public interface BandInstrumentFactory {

    Guitar createGuitar();

    Keyboard createKeyboard();
}
