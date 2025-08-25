package peppy.exception;

public class PeppyInvalidCommandException extends PeppyException {
    public PeppyInvalidCommandException(String message) {
        super("InvalidCommand: " + message);
    }
}
