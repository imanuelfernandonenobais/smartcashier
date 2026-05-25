package smartcashier.model;
/*
 * @author Kel Uyah
 */

import java.time.LocalDateTime;

public class TransaksiPembelian extends Transaksi {
    private Supplier supplier;

    public TransaksiPembelian() {
        super();
    }

    public TransaksiPembelian(int idTransaksi, String kodeTransaksi, LocalDateTime tanggal,
                              User user, Supplier supplier) {
        super(idTransaksi, kodeTransaksi, tanggal, user);
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public double hitungTotal() {
        totalBayar = hitungTotalDetail();
        return totalBayar;
    }

    @Override
    public void prosesStok() {
        for (DetailTransaksi detail : getDaftarDetail()) {
            detail.getKain().tambahStok(detail.getSatuan(), detail.getJumlah());
        }
    }
}