package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.time.LocalDateTime;
import java.util.List;
import smartcashier.dao.TransaksiPembelianDAO;
import smartcashier.model.DetailTransaksi;
import smartcashier.model.Kain;
import smartcashier.model.Supplier;
import smartcashier.model.TransaksiPembelian;
import smartcashier.model.User;
import smartcashier.utils.KodeGenerator;

public class TransaksiPembelianService {

    private final TransaksiPembelianDAO transaksiDAO = new TransaksiPembelianDAO();

    public TransaksiPembelian buatTransaksiBaru(User user, Supplier supplier) {
        if (user == null || user.getIdUser() <= 0) {
            throw new IllegalArgumentException("User transaksi tidak valid.");
        }

        TransaksiPembelian transaksi = new TransaksiPembelian();
        transaksi.setKodeTransaksi(generateKodeBaru());
        transaksi.setTanggal(LocalDateTime.now());
        transaksi.setUser(user);
        transaksi.setSupplier(supplier);

        return transaksi;
    }

    public void tambahItem(TransaksiPembelian transaksi, Kain kain, String satuan, double jumlah, double hargaBeli) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }

        validateItemPembelian(kain, satuan, jumlah, hargaBeli);

        DetailTransaksi detail = new DetailTransaksi();
        detail.setKain(kain);
        detail.setSatuan(satuan);
        detail.setJumlah(jumlah);
        detail.setHarga(hargaBeli);

        transaksi.tambahDetail(detail);
    }

    public void hapusItem(TransaksiPembelian transaksi, int index) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        transaksi.hapusDetail(index);
    }

    public double hitungTotal(TransaksiPembelian transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        return transaksi.hitungTotal();
    }

    public boolean simpanTransaksi(TransaksiPembelian transaksi) {
        validateTransaksiPembelian(transaksi);
        transaksi.hitungTotal();
        return transaksiDAO.insert(transaksi);
    }

    public TransaksiPembelian getTransaksiById(int idPembelian) {
        if (idPembelian <= 0) {
            throw new IllegalArgumentException("ID transaksi tidak valid.");
        }
        return transaksiDAO.getById(idPembelian);
    }

    public List<TransaksiPembelian> getAllTransaksi() {
        return transaksiDAO.getAll();
    }

    private String generateKodeBaru() {
        int nomorUrut = transaksiDAO.getAll().size() + 1;
        return KodeGenerator.generateKodePembelian(nomorUrut);
    }

    private void validateItemPembelian(Kain kain, String satuan, double jumlah, double hargaBeli) {
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

        if (hargaBeli < 0) {
            throw new IllegalArgumentException("Harga beli tidak boleh negatif.");
        }
    }

    private void validateTransaksiPembelian(TransaksiPembelian transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }

        if (transaksi.getUser() == null || transaksi.getUser().getIdUser() <= 0) {
            throw new IllegalArgumentException("User transaksi tidak valid.");
        }

        if (transaksi.getDaftarDetail() == null || transaksi.getDaftarDetail().isEmpty()) {
            throw new IllegalArgumentException("Detail transaksi pembelian masih kosong.");
        }
    }
}