package sample;

public class BadFileNameException extends Exception {
    BadFileNameException() {}

    BadFileNameException(String message) {
        super(message);
    }
}
