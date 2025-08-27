package peppy.exception;

/**
 * Thrown when user inputs a command action that is not recognised.
 */
public class PeppyUnknownCommandException extends PeppyException {
    public PeppyUnknownCommandException(String message) {
        super("UnknownCommand: " + message);
    }
}
