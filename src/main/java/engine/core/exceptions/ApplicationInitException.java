package engine.core.exceptions;

public class ApplicationInitException extends Exception {
    public ApplicationInitException(String message) {
        super(message);
    }

    public ApplicationInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
