package http.protocol;

public class BadHttpVersionException extends Exception {
    BadHttpVersionException() { }

    BadHttpVersionException(String message) {
        super(message);
    }

    BadHttpVersionException(String message, Exception innerException) {
        super(message, innerException);
    }
}
