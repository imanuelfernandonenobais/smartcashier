package smartcashier.dao;

import smartcashier.config.DatabaseConnection;
import smartcashier.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaksiPembelianDAO {

    private final KainDAO kainDAO = new KainDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean insert(TransaksiPembelian transaksi) {
        String sqlHeader = "INSERT INTO transaksi_pembelian (kode_pembelian, tanggal, id_supplier, id_user, total_bayar) VALUES (?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_pembelian (id_pembelian, id_kain, satuan, jumlah, harga, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
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

            if (transaksi.getSupplier() != null) {
                pstHeader.setInt(3, transaksi.getSupplier().getIdSupplier());
            } else {
                pstHeader.setNull(3, Types.INTEGER);
            }

            pstHeader.setInt(4, transaksi.getUser().getIdUser());
            pstHeader.setDouble(5, transaksi.getTotalBayar());
            pstHeader.executeUpdate();

            generatedKeys = pstHeader.getGeneratedKeys();
            int idPembelian = 0;
            if (generatedKeys.next()) {
                idPembelian = generatedKeys.getInt(1);
            }

            transaksi.prosesStok();

            pstDetail = conn.prepareStatement(sqlDetail);
            pstStok = conn.prepareStatement(sqlUpdateStok);

            for (DetailTransaksi detail : transaksi.getDaftarDetail()) {
                pstDetail.setInt(1, idPembelian);
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
            System.out.println("Error insert transaksi pembelian: " + e.getMessage());
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

    public TransaksiPembelian getById(int idPembelian) {
        TransaksiPembelian transaksi = null;

        String sqlHeader = "SELECT * FROM transaksi_pembelian WHERE id_pembelian = ?";
        String sqlDetail = "SELECT * FROM detail_pembelian WHERE id_pembelian = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstHeader = conn.prepareStatement(sqlHeader);
             PreparedStatement pstDetail = conn.prepareStatement(sqlDetail)) {

            pstHeader.setInt(1, idPembelian);
            ResultSet rsHeader = pstHeader.executeQuery();

            if (rsHeader.next()) {
                transaksi = new TransaksiPembelian();

                transaksi.setIdTransaksi(rsHeader.getInt("id_pembelian"));
                transaksi.setKodeTransaksi(rsHeader.getString("kode_pembelian"));

                Timestamp ts = rsHeader.getTimestamp("tanggal");
                if (ts != null) {
                    transaksi.setTanggal(ts.toLocalDateTime());
                } else {
                    transaksi.setTanggal(LocalDateTime.now());
                }

                int idSupplier = rsHeader.getInt("id_supplier");
                if (!rsHeader.wasNull()) {
                    transaksi.setSupplier(supplierDAO.getById(idSupplier));
                }

                transaksi.setUser(userDAO.getById(rsHeader.getInt("id_user")));

                pstDetail.setInt(1, idPembelian);
                ResultSet rsDetail = pstDetail.executeQuery();

                while (rsDetail.next()) {
                    Kain kain = kainDAO.getById(rsDetail.getInt("id_kain"));

                    DetailTransaksi detail = new DetailTransaksi();
                    detail.setIdDetail(rsDetail.getInt("id_detail_pembelian"));
                    detail.setKain(kain);
                    detail.setSatuan(rsDetail.getString("satuan"));
                    detail.setJumlah(rsDetail.getDouble("jumlah"));
                    detail.setHarga(rsDetail.getDouble("harga"));

                    transaksi.tambahDetail(detail);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error get transaksi pembelian by id: " + e.getMessage());
        }

        return transaksi;
    }

    public List<TransaksiPembelian> getAll() {
        List<TransaksiPembelian> list = new ArrayList<>();
        String sql = "SELECT id_pembelian FROM transaksi_pembelian ORDER BY id_pembelian DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TransaksiPembelian transaksi = getById(rs.getInt("id_pembelian"));
                if (transaksi != null) {
                    list.add(transaksi);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error get all transaksi pembelian: " + e.getMessage());
        }

        return list;
    }
}