package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.util.List;
import smartcashier.dao.SupplierDAO;
import smartcashier.model.Supplier;
import smartcashier.utils.ValidationUtil;

public class SupplierService {

    private final SupplierDAO supplierDAO = new SupplierDAO();

    public boolean simpanSupplier(Supplier supplier) {
        validateSupplier(supplier);

        if (supplier.getIdSupplier() <= 0) {
            return supplierDAO.insert(supplier);
        } else {
            return supplierDAO.update(supplier);
        }
    }

    public boolean hapusSupplier(int idSupplier) {
        if (idSupplier <= 0) {
            throw new IllegalArgumentException("ID supplier tidak valid.");
        }
        return supplierDAO.delete(idSupplier);
    }

    public Supplier getSupplierById(int idSupplier) {
        if (idSupplier <= 0) {
            throw new IllegalArgumentException("ID supplier tidak valid.");
        }
        return supplierDAO.getById(idSupplier);
    }

    public List<Supplier> getAllSupplier() {
        return supplierDAO.getAll();
    }

    private void validateSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Data supplier tidak boleh null.");
        }

        if (ValidationUtil.isEmpty(supplier.getNamaSupplier())) {
            throw new IllegalArgumentException("Nama supplier tidak boleh kosong.");
        }

        if (!ValidationUtil.isEmpty(supplier.getNoHp())
                && !ValidationUtil.isPhoneNumber(supplier.getNoHp())) {
            throw new IllegalArgumentException("Nomor HP supplier tidak valid.");
        }
    }
}