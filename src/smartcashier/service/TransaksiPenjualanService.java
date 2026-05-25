package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.time.LocalDateTime;
import java.util.List;
import smartcashier.dao.TransaksiPenjualanDAO;
import smartcashier.model.DetailTransaksi;
import smartcashier.model.Kain;
import smartcashier.model.Pelanggan;
import smartcashier.model.TransaksiPenjualan;
import smartcashier.model.User;
import smartcashier.utils.KodeGenerator;

public class TransaksiPenjualanService {

    private final TransaksiPenjualanDAO transaksiDAO = new TransaksiPenjualanDAO();

    public TransaksiPenjualan buatTransaksiBaru(User user, Pelanggan pelanggan) {
        if (user == null || user.getIdUser() <= 0) {
            throw new IllegalArgumentException("User transaksi tidak valid.");
        }

        TransaksiPenjualan transaksi = new TransaksiPenjualan();
        transaksi.setKodeTransaksi(generateKodeBaru());
        transaksi.setTanggal(LocalDateTime.now());
        transaksi.setUser(user);
        transaksi.setPelanggan(pelanggan);

        return transaksi;
    }

    public void tambahItem(TransaksiPenjualan transaksi, Kain kain, String satuan, double jumlah) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }

        validateItemPenjualan(kain, satuan, jumlah);

        double stokTersedia = kain.getStokBySatuan(satuan);
        if (jumlah > stokTersedia) {
            throw new IllegalArgumentException("Stok " + satuan + " tidak mencukupi.");
        }

        DetailTransaksi detail = new DetailTransaksi(0, kain, satuan, jumlah);
        transaksi.tambahDetail(detail);
    }

    public void hapusItem(TransaksiPenjualan transaksi, int index) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        transaksi.hapusDetail(index);
    }

    public double hitungTotal(TransaksiPenjualan transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        return transaksi.hitungTotal();
    }

    public double hitungKembalian(TransaksiPenjualan transaksi, double bayar) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }

        if (bayar < 0) {
            throw new IllegalArgumentException("Nominal bayar tidak boleh negatif.");
        }

        transaksi.setBayar(bayar);
        return transaksi.getKembalian();
    }

    public boolean simpanTransaksi(TransaksiPenjualan transaksi, double bayar) {
        validateTransaksiPenjualan(transaksi);

        transaksi.hitungTotal();
        transaksi.setBayar(bayar);

        if (!transaksi.isPembayaranCukup()) {
            throw new IllegalArgumentException("Pembayaran kurang dari total transaksi.");
        }

        return transaksiDAO.insert(transaksi);
    }

    public TransaksiPenjualan getTransaksiById(int idPenjualan) {
        if (idPenjualan <= 0) {
            throw new IllegalArgumentException("ID transaksi tidak valid.");
        }
        return transaksiDAO.getById(idPenjualan);
    }

    public List<TransaksiPenjualan> getAllTransaksi() {
        return transaksiDAO.getAll();
    }

    private String generateKodeBaru() {
        int nomorUrut = transaksiDAO.getAll().size() + 1;
        return KodeGenerator.generateKodePenjualan(nomorUrut);
    }

    private void validateItemPenjualan(Kain kain, String satuan, double jumlah) {
        if (kain == null || kain.getIdKain() <= 0) {
            throw new IllegalArgumentException("Data kain tidak valid.");
        }

        if (satuan == null || satuan.trim().isEmpty()) {
            throw new IllegalArgumentException("Satuan tidak boleh kosong.");
        }

        String s = satuan.trim().toLowerCase();
        if (!s.equals("meter") && !s.equals("roll") && !s.equals("yard") && !s.equals("kg")) {
            throw new IllegalArgumentException("Satuan harus meter, roll, yard, atau kg.");
        }

        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }
    }

    private void validateTransaksiPenjualan(TransaksiPenjualan transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }

        if (transaksi.getUser() == null || transaksi.getUser().getIdUser() <= 0) {
            throw new IllegalArgumentException("User transaksi tidak valid.");
        }

        if (transaksi.getDaftarDetail() == null || transaksi.getDaftarDetail().isEmpty()) {
            throw new IllegalArgumentException("Detail transaksi penjualan masih kosong.");
        }
    }
}