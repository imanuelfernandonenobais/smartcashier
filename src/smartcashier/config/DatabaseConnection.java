package smartcashier.config;
/*
 * @author Kel Uyah
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/smartcashier";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi database berhasil.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver JDBC tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Koneksi database gagal: " + e.getMessage());
        }

        return conn;
    }
}
