package smartcashier.model;
/*
 * @author Kel Uyah
 */

import java.time.LocalDateTime;

public class TransaksiPenjualan extends Transaksi {
    private Pelanggan pelanggan;
    private double bayar;
    private double kembalian;

    public TransaksiPenjualan() {
        super();
    }

    public TransaksiPenjualan(int idTransaksi, String kodeTransaksi, LocalDateTime tanggal,
                              User user, Pelanggan pelanggan) {
        super(idTransaksi, kodeTransaksi, tanggal, user);
        this.pelanggan = pelanggan;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
        this.kembalian = hitungKembalian();
    }

    public double getKembalian() {
        return kembalian;
    }

    @Override
    public double hitungTotal() {
        totalBayar = hitungTotalDetail();
        return totalBayar;
    }

    public double hitungKembalian() {
        return bayar - hitungTotal();
    }

    public boolean isPembayaranCukup() {
        return bayar >= hitungTotal();
    }

    @Override
    public void prosesStok() {
        for (DetailTransaksi detail : getDaftarDetail()) {
            detail.getKain().kurangiStok(detail.getSatuan(), detail.getJumlah());
        }
    }
}