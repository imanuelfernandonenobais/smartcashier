package smartcashier.utils;
/*
 * @author Kel Uyah
 */

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isNumeric(String text) {
        if (isEmpty(text)) {
            return false;
        }

        try {
            Double.parseDouble(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveNumber(String text) {
        if (!isNumeric(text)) {
            return false;
        }
        return Double.parseDouble(text.trim()) > 0;
    }

    public static boolean isNonNegativeNumber(String text) {
        if (!isNumeric(text)) {
            return false;
        }
        return Double.parseDouble(text.trim()) >= 0;
    }

    public static boolean isPhoneNumber(String text) {
        if (isEmpty(text)) {
            return false;
        }
        return text.matches("[0-9+\\- ]{10,20}");
    }
}