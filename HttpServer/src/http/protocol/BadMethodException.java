package http.protocol;

public class BadMethodException extends  Exception {
    BadMethodException() { }

    BadMethodException(String message) {
        super(message);
    }

    BadMethodException(String message, Exception innerException) {
        super(message, innerException);
    }
}
