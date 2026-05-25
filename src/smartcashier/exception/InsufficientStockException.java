package smartcashier.exception;
/*
 * @author Kel Uyah
 */

public class InsufficientStockException extends AppException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}