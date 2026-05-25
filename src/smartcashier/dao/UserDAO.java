package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.Admin;
import smartcashier.model.Kasir;
import smartcashier.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role)) {
                    user = new Admin();
                } else if ("kasir".equalsIgnoreCase(role)) {
                    user = new Kasir();
                } else {
                    user = new User();
                }

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaUser(rs.getString("nama_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }

        return user;
    }

    public boolean insert(User user) {
        String sql = "INSERT INTO users (nama_user, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getNamaUser());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insert user: " + e.getMessage());
            return false;
        }
    }

    public boolean update(User user) {
        String sql = "UPDATE users SET nama_user = ?, username = ?, password = ?, role = ? WHERE id_user = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getNamaUser());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());
            pst.setInt(5, user.getIdUser());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update user: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idUser) {
        String sql = "DELETE FROM users WHERE id_user = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idUser);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error delete user: " + e.getMessage());
            return false;
        }
    }

    public User getById(int idUser) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id_user = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idUser);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role)) {
                    user = new Admin();
                } else if ("kasir".equalsIgnoreCase(role)) {
                    user = new Kasir();
                } else {
                    user = new User();
                }

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaUser(rs.getString("nama_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            System.out.println("Error get user by id: " + e.getMessage());
        }

        return user;
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id_user ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user;
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role)) {
                    user = new Admin();
                } else if ("kasir".equalsIgnoreCase(role)) {
                    user = new Kasir();
                } else {
                    user = new User();
                }

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaUser(rs.getString("nama_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));

                list.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error get all users: " + e.getMessage());
        }

        return list;
    }
}