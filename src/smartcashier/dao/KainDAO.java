package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.Kain;
import smartcashier.model.KategoriKain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KainDAO {

    public boolean insert(Kain kain) {
        String sql = "INSERT INTO kain (nama_kain, id_kategori, harga_per_meter, harga_per_roll, harga_per_yard, harga_per_kg, stok_meter, stok_roll, stok_yard, stok_kg) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, kain.getNamaKain());
            pst.setInt(2, kain.getKategori().getIdKategori());
            pst.setDouble(3, kain.getHargaPerMeter());
            pst.setDouble(4, kain.getHargaPerRoll());
            pst.setDouble(5, kain.getHargaPerYard());
            pst.setDouble(6, kain.getHargaPerKg());
            pst.setDouble(7, kain.getStokMeter());
            pst.setDouble(8, kain.getStokRoll());
            pst.setDouble(9, kain.getStokYard());
            pst.setDouble(10, kain.getStokKg());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert kain: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Kain kain) {
        String sql = "UPDATE kain SET nama_kain = ?, id_kategori = ?, harga_per_meter = ?, harga_per_roll = ?, harga_per_yard = ?, harga_per_kg = ?, " +
                     "stok_meter = ?, stok_roll = ?, stok_yard = ?, stok_kg = ? WHERE id_kain = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, kain.getNamaKain());
            pst.setInt(2, kain.getKategori().getIdKategori());
            pst.setDouble(3, kain.getHargaPerMeter());
            pst.setDouble(4, kain.getHargaPerRoll());
            pst.setDouble(5, kain.getHargaPerYard());
            pst.setDouble(6, kain.getHargaPerKg());
            pst.setDouble(7, kain.getStokMeter());
            pst.setDouble(8, kain.getStokRoll());
            pst.setDouble(9, kain.getStokYard());
            pst.setDouble(10, kain.getStokKg());
            pst.setInt(11, kain.getIdKain());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update kain: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idKain) {
        String sql = "DELETE FROM kain WHERE id_kain = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idKain);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete kain: " + e.getMessage());
            return false;
        }
    }

    public Kain getById(int idKain) {
        Kain kain = null;
        String sql = "SELECT k.*, kk.nama_kategori " +
                     "FROM kain k JOIN kategori_kain kk ON k.id_kategori = kk.id_kategori " +
                     "WHERE k.id_kain = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idKain);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                kain = mapResultSetToKain(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error get kain by id: " + e.getMessage());
        }

        return kain;
    }

    public List<Kain> getAll() {
        List<Kain> list = new ArrayList<>();
        String sql = "SELECT k.*, kk.nama_kategori " +
                     "FROM kain k JOIN kategori_kain kk ON k.id_kategori = kk.id_kategori " +
                     "ORDER BY k.id_kain ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToKain(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error get all kain: " + e.getMessage());
        }

        return list;
    }

    public boolean updateStok(Kain kain) {
        String sql = "UPDATE kain SET stok_meter = ?, stok_roll = ?, stok_yard = ?, stok_kg = ? WHERE id_kain = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setDouble(1, kain.getStokMeter());
            pst.setDouble(2, kain.getStokRoll());
            pst.setDouble(3, kain.getStokYard());
            pst.setDouble(4, kain.getStokKg());
            pst.setInt(5, kain.getIdKain());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update stok kain: " + e.getMessage());
            return false;
        }
    }

    private Kain mapResultSetToKain(ResultSet rs) throws SQLException {
        KategoriKain kategori = new KategoriKain();
        kategori.setIdKategori(rs.getInt("id_kategori"));
        kategori.setNamaKategori(rs.getString("nama_kategori"));

        Kain kain = new Kain();
        kain.setIdKain(rs.getInt("id_kain"));
        kain.setNamaKain(rs.getString("nama_kain"));
        kain.setKategori(kategori);
        kain.setHargaPerMeter(rs.getDouble("harga_per_meter"));
        kain.setHargaPerRoll(rs.getDouble("harga_per_roll"));
        kain.setHargaPerYard(rs.getDouble("harga_per_yard"));
        kain.setHargaPerKg(rs.getDouble("harga_per_kg"));
        kain.setStokMeter(rs.getDouble("stok_meter"));
        kain.setStokRoll(rs.getDouble("stok_roll"));
        kain.setStokYard(rs.getDouble("stok_yard"));
        kain.setStokKg(rs.getDouble("stok_kg"));

        return kain;
    }
}