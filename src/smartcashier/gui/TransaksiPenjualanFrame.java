package smartcashier.gui;

/*
 * @author Kel Uyah
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import smartcashier.model.DetailTransaksi;
import smartcashier.model.Kain;
import smartcashier.model.Pelanggan;
import smartcashier.model.TransaksiPenjualan;
import smartcashier.model.User;
import smartcashier.service.KainService;
import smartcashier.service.PelangganService;
import smartcashier.service.TransaksiPenjualanService;
import smartcashier.utils.DialogUtil;
import smartcashier.utils.FormatUtil;

public class TransaksiPenjualanFrame extends JFrame {

    private final User userLogin;
    private TransaksiPenjualan transaksi;

    private JTextField txtKode;
    private JComboBox<Pelanggan> cmbPelanggan;
    private JComboBox<Kain> cmbKain;
    private JComboBox<String> cmbSatuan;
    private JTextField txtJumlah;
    private JTextField txtBayar;
    private JLabel lblTotal;
    private JLabel lblKembalian;

    private JButton btnTambahItem;
    private JButton btnHapusItem;
    private JButton btnHitung;
    private JButton btnSimpan;
    private JButton btnBaru;

    private JTable tableDetail;
    private DefaultTableModel tableModel;

    private final PelangganService pelangganService = new PelangganService();
    private final KainService kainService = new KainService();
    private final TransaksiPenjualanService transaksiService = new TransaksiPenjualanService();

    public TransaksiPenjualanFrame(User userLogin) {
        this.userLogin = userLogin;

        initComponents();
        setupEscapeKey();
        loadPelanggan();
        loadKain();
        buatTransaksiBaru();
        desainTabel();
        desainTombol();

        setTitle("Transaksi Penjualan");
        setSize(1000, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Transaksi Penjualan"));

        txtKode = new JTextField();
        txtKode.setEditable(false);

        cmbPelanggan = new JComboBox<>();
        cmbKain = new JComboBox<>();
        cmbSatuan = new JComboBox<>(new String[]{"meter", "roll", "yard", "kg"});

        txtJumlah = new JTextField();
        txtBayar = new JTextField();

        lblTotal = new JLabel("Rp0");
        lblKembalian = new JLabel("Rp0");

        lblTotal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        lblKembalian.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        btnTambahItem = new JButton("Tambah Item");
        btnHapusItem = new JButton("Hapus Item");
        btnHitung = new JButton("Hitung Kembalian");
        btnSimpan = new JButton("Simpan Transaksi");
        btnBaru = new JButton("Transaksi Baru");

        panelForm.add(new JLabel("Kode Transaksi"));
        panelForm.add(txtKode);
        panelForm.add(new JLabel("Pelanggan"));
        panelForm.add(cmbPelanggan);

        panelForm.add(new JLabel("Kain"));
        panelForm.add(cmbKain);
        panelForm.add(new JLabel("Satuan"));
        panelForm.add(cmbSatuan);

        panelForm.add(new JLabel("Jumlah"));
        panelForm.add(txtJumlah);
        panelForm.add(new JLabel("Bayar"));
        panelForm.add(txtBayar);

        panelForm.add(new JLabel("Total"));
        panelForm.add(lblTotal);
        panelForm.add(new JLabel("Kembalian"));
        panelForm.add(lblKembalian);

        panelForm.add(btnTambahItem);
        panelForm.add(btnHapusItem);
        panelForm.add(btnHitung);
        panelForm.add(btnSimpan);

        tableModel = new DefaultTableModel(
                new Object[]{"No", "Nama Kain", "Satuan", "Jumlah", "Harga", "Subtotal"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableDetail = new JTable(tableModel);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(btnBaru, BorderLayout.EAST);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(new JScrollPane(tableDetail), BorderLayout.CENTER);
        panelUtama.add(panelBottom, BorderLayout.SOUTH);

        add(panelUtama);

        btnTambahItem.addActionListener(e -> tambahItem());
        btnHapusItem.addActionListener(e -> hapusItem());
        btnHitung.addActionListener(e -> hitungKembalian());
        btnSimpan.addActionListener(e -> simpanTransaksi());
        btnBaru.addActionListener(e -> buatTransaksiBaru());
    }

    private void setupEscapeKey() {
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void desainTabel() {
        tableDetail.setRowHeight(35);

        tableDetail.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        tableDetail.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        tableDetail.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219)
        );

        tableDetail.getTableHeader().setForeground(
                java.awt.Color.BLACK
        );

        tableDetail.getTableHeader().setReorderingAllowed(false);

        tableDetail.setSelectionBackground(
                new java.awt.Color(174, 214, 241)
        );

        tableDetail.setSelectionForeground(
                java.awt.Color.BLACK
        );

        tableDetail.setShowGrid(false);

        tableDetail.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        tableDetail.setGridColor(
                new java.awt.Color(230, 230, 230)
        );

        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

        center.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        tableDetail.getColumnModel().getColumn(0).setCellRenderer(center);
        tableDetail.getColumnModel().getColumn(2).setCellRenderer(center);
        tableDetail.getColumnModel().getColumn(3).setCellRenderer(center);

        tableDetail.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableDetail.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableDetail.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableDetail.getColumnModel().getColumn(4).setPreferredWidth(150);
        tableDetail.getColumnModel().getColumn(5).setPreferredWidth(150);
    }

    private void desainTombol() {
        JButton[] tombol = {
            btnTambahItem, btnHapusItem, btnHitung, btnSimpan, btnBaru
        };

        for (JButton btn : tombol) {
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            btn.setForeground(java.awt.Color.BLACK);
        }

        btnTambahItem.setBackground(new java.awt.Color(46, 204, 113));
        btnHapusItem.setBackground(new java.awt.Color(220, 53, 69));
        btnHitung.setBackground(new java.awt.Color(255, 193, 7));
        btnSimpan.setBackground(new java.awt.Color(52, 152, 219));
        btnBaru.setBackground(new java.awt.Color(108, 117, 125));
    }

    private void loadPelanggan() {
        cmbPelanggan.removeAllItems();

        for (Pelanggan pelanggan : pelangganService.getAllPelanggan()) {
            cmbPelanggan.addItem(pelanggan);
        }
    }

    private void loadKain() {
        cmbKain.removeAllItems();

        for (Kain kain : kainService.getAllKain()) {
            cmbKain.addItem(kain);
        }
    }

    private void buatTransaksiBaru() {
        try {
            transaksi = transaksiService.buatTransaksiBaru(userLogin, null);

            txtKode.setText(transaksi.getKodeTransaksi());
            txtJumlah.setText("");
            txtBayar.setText("");
            lblTotal.setText("Rp0");
            lblKembalian.setText("Rp0");
            tableModel.setRowCount(0);

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void tambahItem() {
        try {
            Kain kain = (Kain) cmbKain.getSelectedItem();
            String satuan = cmbSatuan.getSelectedItem().toString();
            double jumlah = Double.parseDouble(txtJumlah.getText().trim());

            transaksiService.tambahItem(transaksi, kain, satuan, jumlah);
            refreshTable();

            txtJumlah.setText("");
            txtJumlah.requestFocus();

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hapusItem() {
        try {
            int row = tableDetail.getSelectedRow();

            if (row < 0) {
                DialogUtil.showWarning(this, "Pilih item yang akan dihapus.");
                return;
            }

            transaksiService.hapusItem(transaksi, row);
            refreshTable();

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hitungKembalian() {
        try {
            double bayar = Double.parseDouble(txtBayar.getText().trim());
            double kembalian = transaksiService.hitungKembalian(transaksi, bayar);

            lblKembalian.setText(FormatUtil.formatRupiah(kembalian));

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void simpanTransaksi() {
        try {
            Pelanggan pelanggan = (Pelanggan) cmbPelanggan.getSelectedItem();
            transaksi.setPelanggan(pelanggan);

            double bayar = Double.parseDouble(txtBayar.getText().trim());

            boolean hasil = transaksiService.simpanTransaksi(transaksi, bayar);

            if (hasil) {
                DialogUtil.showInfo(this, "Transaksi penjualan berhasil disimpan.");
                loadKain();
                buatTransaksiBaru();
            } else {
                DialogUtil.showError(this, "Transaksi penjualan gagal disimpan.");
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int no = 1;

        for (DetailTransaksi detail : transaksi.getDaftarDetail()) {
            tableModel.addRow(new Object[]{
                no++,
                detail.getKain().getNamaKain(),
                detail.getSatuan(),
                detail.getJumlah(),
                FormatUtil.formatRupiah(detail.getHarga()),
                FormatUtil.formatRupiah(detail.getSubtotal())
            });
        }

        lblTotal.setText(FormatUtil.formatRupiah(transaksi.hitungTotal()));
    }
}