package smartcashier.exception;
/*
 * @author Kel Uyah
 */

public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}