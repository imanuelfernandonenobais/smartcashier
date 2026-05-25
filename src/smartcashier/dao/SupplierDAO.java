package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO supplier (nama_supplier, alamat, no_hp) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getNamaSupplier());
            pst.setString(2, supplier.getAlamat());
            pst.setString(3, supplier.getNoHp());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Supplier supplier) {
        String sql = "UPDATE supplier SET nama_supplier = ?, alamat = ?, no_hp = ? WHERE id_supplier = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getNamaSupplier());
            pst.setString(2, supplier.getAlamat());
            pst.setString(3, supplier.getNoHp());
            pst.setInt(4, supplier.getIdSupplier());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idSupplier) {
        String sql = "DELETE FROM supplier WHERE id_supplier = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idSupplier);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete supplier: " + e.getMessage());
            return false;
        }
    }

    public Supplier getById(int idSupplier) {
        Supplier supplier = null;
        String sql = "SELECT * FROM supplier WHERE id_supplier = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idSupplier);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                supplier = new Supplier();
                supplier.setIdSupplier(rs.getInt("id_supplier"));
                supplier.setNamaSupplier(rs.getString("nama_supplier"));
                supplier.setAlamat(rs.getString("alamat"));
                supplier.setNoHp(rs.getString("no_hp"));
            }

        } catch (SQLException e) {
            System.out.println("Error get supplier by id: " + e.getMessage());
        }

        return supplier;
    }

    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier ORDER BY id_supplier ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setIdSupplier(rs.getInt("id_supplier"));
                supplier.setNamaSupplier(rs.getString("nama_supplier"));
                supplier.setAlamat(rs.getString("alamat"));
                supplier.setNoHp(rs.getString("no_hp"));
                list.add(supplier);
            }

        } catch (SQLException e) {
            System.out.println("Error get all supplier: " + e.getMessage());
        }

        return list;
    }
}