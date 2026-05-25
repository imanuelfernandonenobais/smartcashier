package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.KategoriKain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriKainDAO {

    public boolean insert(KategoriKain kategori) {
        String sql = "INSERT INTO kategori_kain (nama_kategori) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, kategori.getNamaKategori());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert kategori: " + e.getMessage());
            return false;
        }
    }

    public boolean update(KategoriKain kategori) {
        String sql = "UPDATE kategori_kain SET nama_kategori = ? WHERE id_kategori = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, kategori.getNamaKategori());
            pst.setInt(2, kategori.getIdKategori());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update kategori: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idKategori) {
        String sql = "DELETE FROM kategori_kain WHERE id_kategori = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idKategori);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete kategori: " + e.getMessage());
            return false;
        }
    }

    public KategoriKain getById(int idKategori) {
        KategoriKain kategori = null;
        String sql = "SELECT * FROM kategori_kain WHERE id_kategori = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idKategori);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                kategori = new KategoriKain();
                kategori.setIdKategori(rs.getInt("id_kategori"));
                kategori.setNamaKategori(rs.getString("nama_kategori"));
            }

        } catch (SQLException e) {
            System.out.println("Error get kategori by id: " + e.getMessage());
        }

        return kategori;
    }

    public List<KategoriKain> getAll() {
        List<KategoriKain> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori_kain ORDER BY id_kategori ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                KategoriKain kategori = new KategoriKain();
                kategori.setIdKategori(rs.getInt("id_kategori"));
                kategori.setNamaKategori(rs.getString("nama_kategori"));
                list.add(kategori);
            }

        } catch (SQLException e) {
            System.out.println("Error get all kategori: " + e.getMessage());
        }

        return list;
    }
}