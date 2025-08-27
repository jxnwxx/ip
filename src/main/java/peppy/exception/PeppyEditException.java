package peppy.exception;

/**
 * Thrown when there is a problem editing tasks.
 */
public class PeppyEditException extends PeppyException {
    public PeppyEditException(String message) {
        super("EditException: " + message);
    }
}
