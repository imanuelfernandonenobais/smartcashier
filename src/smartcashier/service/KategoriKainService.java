package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.util.List;
import smartcashier.dao.KategoriKainDAO;
import smartcashier.model.KategoriKain;
import smartcashier.utils.ValidationUtil;

public class KategoriKainService {

    private final KategoriKainDAO kategoriDAO = new KategoriKainDAO();

    public boolean simpanKategori(KategoriKain kategori) {
        validateKategori(kategori);

        if (kategori.getIdKategori() <= 0) {
            return kategoriDAO.insert(kategori);
        } else {
            return kategoriDAO.update(kategori);
        }
    }

    public boolean hapusKategori(int idKategori) {
        if (idKategori <= 0) {
            throw new IllegalArgumentException("ID kategori tidak valid.");
        }
        return kategoriDAO.delete(idKategori);
    }

    public KategoriKain getKategoriById(int idKategori) {
        if (idKategori <= 0) {
            throw new IllegalArgumentException("ID kategori tidak valid.");
        }
        return kategoriDAO.getById(idKategori);
    }

    public List<KategoriKain> getAllKategori() {
        return kategoriDAO.getAll();
    }

    private void validateKategori(KategoriKain kategori) {
        if (kategori == null) {
            throw new IllegalArgumentException("Data kategori tidak boleh null.");
        }

        if (ValidationUtil.isEmpty(kategori.getNamaKategori())) {
            throw new IllegalArgumentException("Nama kategori tidak boleh kosong.");
        }
    }
}