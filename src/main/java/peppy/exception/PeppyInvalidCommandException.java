package peppy.exception;

/**
 * Thrown when a command with valid action but invalid arguments is inputted by the user.
 */
public class PeppyInvalidCommandException extends PeppyException {
    public PeppyInvalidCommandException(String message) {
        super("InvalidCommand: " + message);
    }
}
