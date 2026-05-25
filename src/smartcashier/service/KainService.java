package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.util.List;
import smartcashier.dao.KainDAO;
import smartcashier.model.Kain;
import smartcashier.utils.ValidationUtil;

public class KainService {

    private final KainDAO kainDAO = new KainDAO();

    public boolean simpanKain(Kain kain) {
        validateKain(kain);

        if (kain.getIdKain() <= 0) {
            return kainDAO.insert(kain);
        } else {
            return kainDAO.update(kain);
        }
    }

    public boolean hapusKain(int idKain) {
        if (idKain <= 0) {
            throw new IllegalArgumentException("ID kain tidak valid.");
        }
        return kainDAO.delete(idKain);
    }

    public Kain getKainById(int idKain) {
        if (idKain <= 0) {
            throw new IllegalArgumentException("ID kain tidak valid.");
        }
        return kainDAO.getById(idKain);
    }

    public List<Kain> getAllKain() {
        return kainDAO.getAll();
    }

    public boolean updateStok(Kain kain) {
        if (kain == null || kain.getIdKain() <= 0) {
            throw new IllegalArgumentException("Data kain tidak valid.");
        }
        return kainDAO.updateStok(kain);
    }

    private void validateKain(Kain kain) {
        if (kain == null) {
            throw new IllegalArgumentException("Data kain tidak boleh null.");
        }

        if (ValidationUtil.isEmpty(kain.getNamaKain())) {
            throw new IllegalArgumentException("Nama kain tidak boleh kosong.");
        }

        if (kain.getKategori() == null || kain.getKategori().getIdKategori() <= 0) {
            throw new IllegalArgumentException("Kategori kain harus dipilih.");
        }

        if (kain.getHargaPerMeter() < 0 || kain.getHargaPerRoll() < 0
                || kain.getHargaPerYard() < 0 || kain.getHargaPerKg() < 0) {
            throw new IllegalArgumentException("Harga kain tidak boleh negatif.");
        }

        if (kain.getStokMeter() < 0 || kain.getStokRoll() < 0
                || kain.getStokYard() < 0 || kain.getStokKg() < 0) {
            throw new IllegalArgumentException("Stok kain tidak boleh negatif.");
        }
    }
}