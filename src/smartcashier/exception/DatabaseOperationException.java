package smartcashier.exception;
/*
 * @author Kel Uyah
 */

public class DatabaseOperationException extends AppException {

    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}