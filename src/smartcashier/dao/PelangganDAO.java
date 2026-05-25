package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.Pelanggan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAO {

    public boolean insert(Pelanggan pelanggan) {
        String sql = "INSERT INTO pelanggan (nama_pelanggan, alamat, no_hp) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, pelanggan.getNamaPelanggan());
            pst.setString(2, pelanggan.getAlamat());
            pst.setString(3, pelanggan.getNoHp());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert pelanggan: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Pelanggan pelanggan) {
        String sql = "UPDATE pelanggan SET nama_pelanggan = ?, alamat = ?, no_hp = ? WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, pelanggan.getNamaPelanggan());
            pst.setString(2, pelanggan.getAlamat());
            pst.setString(3, pelanggan.getNoHp());
            pst.setInt(4, pelanggan.getIdPelanggan());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update pelanggan: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idPelanggan) {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idPelanggan);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete pelanggan: " + e.getMessage());
            return false;
        }
    }

    public Pelanggan getById(int idPelanggan) {
        Pelanggan pelanggan = null;
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idPelanggan);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
                pelanggan.setNamaPelanggan(rs.getString("nama_pelanggan"));
                pelanggan.setAlamat(rs.getString("alamat"));
                pelanggan.setNoHp(rs.getString("no_hp"));
            }

        } catch (SQLException e) {
            System.out.println("Error get pelanggan by id: " + e.getMessage());
        }

        return pelanggan;
    }

    public List<Pelanggan> getAll() {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY id_pelanggan ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
                pelanggan.setNamaPelanggan(rs.getString("nama_pelanggan"));
                pelanggan.setAlamat(rs.getString("alamat"));
                pelanggan.setNoHp(rs.getString("no_hp"));
                list.add(pelanggan);
            }

        } catch (SQLException e) {
            System.out.println("Error get all pelanggan: " + e.getMessage());
        }

        return list;
    }
}