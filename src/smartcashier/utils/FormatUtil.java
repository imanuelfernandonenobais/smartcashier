package smartcashier.utils;
/*
 * @author Kel Uyah
 */

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    private static final Locale LOCALE_ID = new Locale("id", "ID");
    private static final NumberFormat RUPIAH_FORMAT = NumberFormat.getCurrencyInstance(LOCALE_ID);
    private static final DecimalFormat ANGKA_FORMAT = new DecimalFormat("#,##0.##");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private FormatUtil() {
    }

    public static String formatRupiah(double nilai) {
        return RUPIAH_FORMAT.format(nilai);
    }

    public static String formatAngka(double nilai) {
        return ANGKA_FORMAT.format(nilai);
    }

    public static String formatTanggal(LocalDate tanggal) {
        if (tanggal == null) {
            return "";
        }
        return tanggal.format(DATE_FORMAT);
    }

    public static String formatDateTime(LocalDateTime tanggalWaktu) {
        if (tanggalWaktu == null) {
            return "";
        }
        return tanggalWaktu.format(DATETIME_FORMAT);
    }
}