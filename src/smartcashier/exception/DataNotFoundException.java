package smartcashier.exception;
/*
 * @author Kel Uyah
 */

public class DataNotFoundException extends AppException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}