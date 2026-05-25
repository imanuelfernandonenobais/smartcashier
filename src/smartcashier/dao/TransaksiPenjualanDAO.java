package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaksiPenjualanDAO {

    private final KainDAO kainDAO = new KainDAO();
    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean insert(TransaksiPenjualan transaksi) {
        String sqlHeader = "INSERT INTO transaksi_penjualan (kode_penjualan, tanggal, id_pelanggan, id_user, total_bayar, bayar, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_penjualan (id_penjualan, id_kain, satuan, jumlah, harga, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE kain SET stok_meter = ?, stok_roll = ?, stok_yard = ?, stok_kg = ? WHERE id_kain = ?";

        Connection conn = null;
        PreparedStatement pstHeader = null;
        PreparedStatement pstDetail = null;
        PreparedStatement pstStok = null;
        ResultSet generatedKeys = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            pstHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS);
            pstHeader.setString(1, transaksi.getKodeTransaksi());
            pstHeader.setTimestamp(2, Timestamp.valueOf(transaksi.getTanggal()));

            if (transaksi.getPelanggan() != null) {
                pstHeader.setInt(3, transaksi.getPelanggan().getIdPelanggan());
            } else {
                pstHeader.setNull(3, Types.INTEGER);
            }

            pstHeader.setInt(4, transaksi.getUser().getIdUser());
            pstHeader.setDouble(5, transaksi.getTotalBayar());
            pstHeader.setDouble(6, transaksi.getBayar());
            pstHeader.setDouble(7, transaksi.getKembalian());

            pstHeader.executeUpdate();

            generatedKeys = pstHeader.getGeneratedKeys();
            int idPenjualan = 0;
            if (generatedKeys.next()) {
                idPenjualan = generatedKeys.getInt(1);
            }

            transaksi.prosesStok();

            pstDetail = conn.prepareStatement(sqlDetail);
            pstStok = conn.prepareStatement(sqlUpdateStok);

            for (DetailTransaksi detail : transaksi.getDaftarDetail()) {
                pstDetail.setInt(1, idPenjualan);
                pstDetail.setInt(2, detail.getKain().getIdKain());
                pstDetail.setString(3, detail.getSatuan());
                pstDetail.setDouble(4, detail.getJumlah());
                pstDetail.setDouble(5, detail.getHarga());
                pstDetail.setDouble(6, detail.getSubtotal());
                pstDetail.executeUpdate();

                Kain kain = detail.getKain();
                pstStok.setDouble(1, kain.getStokMeter());
                pstStok.setDouble(2, kain.getStokRoll());
                pstStok.setDouble(3, kain.getStokYard());
                pstStok.setDouble(4, kain.getStokKg());
                pstStok.setInt(5, kain.getIdKain());
                pstStok.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback gagal: " + ex.getMessage());
            }
            System.out.println("Error insert transaksi penjualan: " + e.getMessage());
            return false;

        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pstHeader != null) pstHeader.close();
                if (pstDetail != null) pstDetail.close();
                if (pstStok != null) pstStok.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resource: " + e.getMessage());
            }
        }
    }

    public TransaksiPenjualan getById(int idPenjualan) {
        TransaksiPenjualan transaksi = null;

        String sqlHeader = "SELECT * FROM transaksi_penjualan WHERE id_penjualan = ?";
        String sqlDetail = "SELECT * FROM detail_penjualan WHERE id_penjualan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstHeader = conn.prepareStatement(sqlHeader);
             PreparedStatement pstDetail = conn.prepareStatement(sqlDetail)) {

            pstHeader.setInt(1, idPenjualan);
            ResultSet rsHeader = pstHeader.executeQuery();

            if (rsHeader.next()) {
                transaksi = new TransaksiPenjualan();

                transaksi.setIdTransaksi(rsHeader.getInt("id_penjualan"));
                transaksi.setKodeTransaksi(rsHeader.getString("kode_penjualan"));

                Timestamp ts = rsHeader.getTimestamp("tanggal");
                if (ts != null) {
                    transaksi.setTanggal(ts.toLocalDateTime());
                } else {
                    transaksi.setTanggal(LocalDateTime.now());
                }

                int idPelanggan = rsHeader.getInt("id_pelanggan");
                if (!rsHeader.wasNull()) {
                    transaksi.setPelanggan(pelangganDAO.getById(idPelanggan));
                }

                transaksi.setUser(userDAO.getById(rsHeader.getInt("id_user")));
                transaksi.setBayar(rsHeader.getDouble("bayar"));

                pstDetail.setInt(1, idPenjualan);
                ResultSet rsDetail = pstDetail.executeQuery();

                while (rsDetail.next()) {
                    Kain kain = kainDAO.getById(rsDetail.getInt("id_kain"));

                    DetailTransaksi detail = new DetailTransaksi();
                    detail.setIdDetail(rsDetail.getInt("id_detail_penjualan"));
                    detail.setKain(kain);
                    detail.setSatuan(rsDetail.getString("satuan"));
                    detail.setJumlah(rsDetail.getDouble("jumlah"));
                    detail.setHarga(rsDetail.getDouble("harga"));

                    transaksi.tambahDetail(detail);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error get transaksi penjualan by id: " + e.getMessage());
        }

        return transaksi;
    }

    public List<TransaksiPenjualan> getAll() {
        List<TransaksiPenjualan> list = new ArrayList<>();
        String sql = "SELECT id_penjualan FROM transaksi_penjualan ORDER BY id_penjualan DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TransaksiPenjualan transaksi = getById(rs.getInt("id_penjualan"));
                if (transaksi != null) {
                    list.add(transaksi);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error get all transaksi penjualan: " + e.getMessage());
        }

        return list;
    }
}