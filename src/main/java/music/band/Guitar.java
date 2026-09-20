package music.band;

/**
 * Abstract Product #1 of the family: every guitar variant must be able
 * to be strummed. Concrete guitars never leak their type to the client.
 */
public interface Guitar {

    void strum();
}
