package smartcashier.gui;

/*
 * @author Kel Uyah
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import smartcashier.model.TransaksiPembelian;
import smartcashier.model.TransaksiPenjualan;
import smartcashier.service.TransaksiPembelianService;
import smartcashier.service.TransaksiPenjualanService;
import smartcashier.utils.FormatUtil;

public class LaporanFrame extends JFrame {

    private JTable tablePenjualan;
    private JTable tablePembelian;

    private DefaultTableModel modelPenjualan;
    private DefaultTableModel modelPembelian;

    private JLabel lblTotalPenjualan;
    private JLabel lblTotalPembelian;

    private JButton btnRefresh;

    private final TransaksiPenjualanService penjualanService = new TransaksiPenjualanService();
    private final TransaksiPembelianService pembelianService = new TransaksiPembelianService();

    public LaporanFrame() {
        initComponents();
        setupEscapeKey();
        loadLaporan();
        desainTabel();
        desainTombol();

        setTitle("Laporan SmartCashier");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();

        modelPenjualan = new DefaultTableModel(
                new Object[]{"ID", "Kode", "Tanggal", "Pelanggan", "Kasir", "Total", "Bayar", "Kembalian"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelPembelian = new DefaultTableModel(
                new Object[]{"ID", "Kode", "Tanggal", "Supplier", "User", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePenjualan = new JTable(modelPenjualan);
        tablePembelian = new JTable(modelPembelian);

        JPanel panelPenjualan = new JPanel(new BorderLayout());
        lblTotalPenjualan = new JLabel("Total Penjualan: Rp0");
        panelPenjualan.add(new JScrollPane(tablePenjualan), BorderLayout.CENTER);
        panelPenjualan.add(lblTotalPenjualan, BorderLayout.SOUTH);

        JPanel panelPembelian = new JPanel(new BorderLayout());
        lblTotalPembelian = new JLabel("Total Pembelian: Rp0");
        panelPembelian.add(new JScrollPane(tablePembelian), BorderLayout.CENTER);
        panelPembelian.add(lblTotalPembelian, BorderLayout.SOUTH);

        tabbedPane.addTab("Laporan Penjualan", panelPenjualan);
        tabbedPane.addTab("Laporan Pembelian", panelPembelian);

        btnRefresh = new JButton("Refresh");

        JPanel panelTop = new JPanel(new GridLayout(1, 1));
        panelTop.add(btnRefresh);

        panelUtama.add(panelTop, BorderLayout.NORTH);
        panelUtama.add(tabbedPane, BorderLayout.CENTER);

        add(panelUtama);

        btnRefresh.addActionListener(e -> loadLaporan());
    }

    private void setupEscapeKey() {
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void desainTabel() {
        desainSatuTabel(tablePenjualan);
        desainSatuTabel(tablePembelian);

        tablePenjualan.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablePenjualan.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablePenjualan.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablePenjualan.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablePenjualan.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablePenjualan.getColumnModel().getColumn(5).setPreferredWidth(140);
        tablePenjualan.getColumnModel().getColumn(6).setPreferredWidth(140);
        tablePenjualan.getColumnModel().getColumn(7).setPreferredWidth(140);

        tablePembelian.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablePembelian.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablePembelian.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablePembelian.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablePembelian.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablePembelian.getColumnModel().getColumn(5).setPreferredWidth(150);

        lblTotalPenjualan.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        lblTotalPembelian.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        lblTotalPenjualan.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        lblTotalPembelian.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
    }

    private void desainSatuTabel(JTable table) {
        table.setRowHeight(35);

        table.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        table.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        table.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219)
        );

        table.getTableHeader().setForeground(
                java.awt.Color.BLACK
        );

        table.getTableHeader().setReorderingAllowed(false);

        table.setSelectionBackground(
                new java.awt.Color(174, 214, 241)
        );

        table.setSelectionForeground(
                java.awt.Color.BLACK
        );

        table.setShowGrid(false);

        table.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        table.setGridColor(
                new java.awt.Color(230, 230, 230)
        );
    }

    private void desainTombol() {
        btnRefresh.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnRefresh.setBackground(new java.awt.Color(52, 152, 219));
        btnRefresh.setForeground(java.awt.Color.BLACK);
    }

    private void loadLaporan() {
        loadPenjualan();
        loadPembelian();
    }

    private void loadPenjualan() {
        modelPenjualan.setRowCount(0);
        List<TransaksiPenjualan> list = penjualanService.getAllTransaksi();

        double totalSemua = 0;

        for (TransaksiPenjualan trx : list) {
            totalSemua += trx.getTotalBayar();

            modelPenjualan.addRow(new Object[]{
                trx.getIdTransaksi(),
                trx.getKodeTransaksi(),
                trx.getTanggal(),
                trx.getPelanggan() != null ? trx.getPelanggan().getNamaPelanggan() : "-",
                trx.getUser() != null ? trx.getUser().getNamaUser() : "-",
                FormatUtil.formatRupiah(trx.getTotalBayar()),
                FormatUtil.formatRupiah(trx.getBayar()),
                FormatUtil.formatRupiah(trx.getKembalian())
            });
        }

        lblTotalPenjualan.setText("Total Penjualan: " + FormatUtil.formatRupiah(totalSemua));
    }

    private void loadPembelian() {
        modelPembelian.setRowCount(0);
        List<TransaksiPembelian> list = pembelianService.getAllTransaksi();

        double totalSemua = 0;

        for (TransaksiPembelian trx : list) {
            totalSemua += trx.getTotalBayar();

            modelPembelian.addRow(new Object[]{
                trx.getIdTransaksi(),
                trx.getKodeTransaksi(),
                trx.getTanggal(),
                trx.getSupplier() != null ? trx.getSupplier().getNamaSupplier() : "-",
                trx.getUser() != null ? trx.getUser().getNamaUser() : "-",
                FormatUtil.formatRupiah(trx.getTotalBayar())
            });
        }

        lblTotalPembelian.setText("Total Pembelian: " + FormatUtil.formatRupiah(totalSemua));
    }
}