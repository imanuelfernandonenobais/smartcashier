package smartcashier.utils;
/*
 * @author Kel Uyah
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KodeGenerator {

    private static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("yyyyMMdd");

    private KodeGenerator() {
    }

    public static String generateKodePenjualan(int nomorUrut) {
        return "PJ-" + LocalDate.now().format(FORMAT_TANGGAL) + "-" + String.format("%03d", nomorUrut);
    }

    public static String generateKodePembelian(int nomorUrut) {
        return "PB-" + LocalDate.now().format(FORMAT_TANGGAL) + "-" + String.format("%03d", nomorUrut);
    }

    public static String generateKodeCustom(String prefix, int nomorUrut) {
        return prefix.toUpperCase() + "-" + LocalDate.now().format(FORMAT_TANGGAL) + "-" + String.format("%03d", nomorUrut);
    }
}