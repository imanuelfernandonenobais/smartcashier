package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.util.List;
import smartcashier.dao.PelangganDAO;
import smartcashier.model.Pelanggan;
import smartcashier.utils.ValidationUtil;

public class PelangganService {

    private final PelangganDAO pelangganDAO = new PelangganDAO();

    public boolean simpanPelanggan(Pelanggan pelanggan) {
        validatePelanggan(pelanggan);

        if (pelanggan.getIdPelanggan() <= 0) {
            return pelangganDAO.insert(pelanggan);
        } else {
            return pelangganDAO.update(pelanggan);
        }
    }

    public boolean hapusPelanggan(int idPelanggan) {
        if (idPelanggan <= 0) {
            throw new IllegalArgumentException("ID pelanggan tidak valid.");
        }
        return pelangganDAO.delete(idPelanggan);
    }

    public Pelanggan getPelangganById(int idPelanggan) {
        if (idPelanggan <= 0) {
            throw new IllegalArgumentException("ID pelanggan tidak valid.");
        }
        return pelangganDAO.getById(idPelanggan);
    }

    public List<Pelanggan> getAllPelanggan() {
        return pelangganDAO.getAll();
    }

    private void validatePelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Data pelanggan tidak boleh null.");
        }

        if (ValidationUtil.isEmpty(pelanggan.getNamaPelanggan())) {
            throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong.");
        }

        if (!ValidationUtil.isEmpty(pelanggan.getNoHp())
                && !ValidationUtil.isPhoneNumber(pelanggan.getNoHp())) {
            throw new IllegalArgumentException("Nomor HP pelanggan tidak valid.");
        }
    }
}