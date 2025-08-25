package peppy.exception;

public class PeppyUnknownCommandException extends PeppyException {
    public PeppyUnknownCommandException(String message) {
        super("UnknownCommand: " + message);
    }
}
