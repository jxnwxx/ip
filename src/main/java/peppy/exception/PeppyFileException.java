package peppy.exception;

public class PeppyFileException extends PeppyException {
    public PeppyFileException(String message) {
        super("FileException: " + message);
    }
}
