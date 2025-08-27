package peppy.exception;

/**
 * Parent class to all other forms of Peppy exceptions.
 */
public class PeppyException extends Exception {
    public PeppyException(String message) {
        super(message);
    }
}
