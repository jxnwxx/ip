package peppy.exception;

/**
 * Thrown when there is an error creating, reading or writing a file.
 */
public class PeppyFileException extends PeppyException {
    public PeppyFileException(String message) {
        super("FileException: " + message);
    }
}
