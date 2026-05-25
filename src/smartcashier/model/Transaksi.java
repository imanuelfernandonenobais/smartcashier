package smartcashier.model;
/*
 * @author Kel Uyah
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Transaksi {
    private int idTransaksi;
    private String kodeTransaksi;
    private LocalDateTime tanggal;
    private User user;
    protected List<DetailTransaksi> daftarDetail;
    protected double totalBayar;

    public Transaksi() {
        this.tanggal = LocalDateTime.now();
        this.daftarDetail = new ArrayList<>();
    }

    public Transaksi(int idTransaksi, String kodeTransaksi, LocalDateTime tanggal, User user) {
        this.idTransaksi = idTransaksi;
        this.kodeTransaksi = kodeTransaksi;
        this.tanggal = tanggal;
        this.user = user;
        this.daftarDetail = new ArrayList<>();
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DetailTransaksi> getDaftarDetail() {
        return daftarDetail;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void tambahDetail(DetailTransaksi detail) {
        if (detail == null) {
            throw new IllegalArgumentException("Detail transaksi tidak boleh null.");
        }
        daftarDetail.add(detail);
        hitungTotal();
    }

    public void hapusDetail(int index) {
        if (index >= 0 && index < daftarDetail.size()) {
            daftarDetail.remove(index);
            hitungTotal();
        }
    }

    public int getJumlahItem() {
        return daftarDetail.size();
    }

    protected double hitungTotalDetail() {
        double total = 0;
        for (DetailTransaksi detail : daftarDetail) {
            total += detail.getSubtotal();
        }
        return total;
    }

    public abstract double hitungTotal();

    public abstract void prosesStok();
}