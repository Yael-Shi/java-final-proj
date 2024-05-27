package utils;

public class ItemAlreadyExistsException extends Exception {

    public ItemAlreadyExistsException() {
        super("This item already exists.");
    }

    public ItemAlreadyExistsException(String message) {
        super(message);
    }

    public ItemAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ItemAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}