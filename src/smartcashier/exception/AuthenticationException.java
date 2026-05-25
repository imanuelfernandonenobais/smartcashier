package smartcashier.exception;
/*
 * @author Kel Uyah
 */

public class AuthenticationException extends AppException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}