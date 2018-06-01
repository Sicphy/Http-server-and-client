package http.protocol;

public class BadRequestException extends  Exception {
    BadRequestException() { }

    BadRequestException(String message) {
        super(message);
    }

    BadRequestException(String message, Exception innerException) {
        super(message, innerException);
    }
}
