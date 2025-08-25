package peppy.exception;

public class PeppyEditException extends PeppyException {
    public PeppyEditException(String message) {
        super("EditException: " + message);
    }
}
