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
import smartcashier.model.Supplier;
import smartcashier.model.TransaksiPembelian;
import smartcashier.model.User;
import smartcashier.service.KainService;
import smartcashier.service.SupplierService;
import smartcashier.service.TransaksiPembelianService;
import smartcashier.utils.DialogUtil;
import smartcashier.utils.FormatUtil;

public class TransaksiPembelianFrame extends JFrame {

    private final User userLogin;
    private TransaksiPembelian transaksi;

    private JTextField txtKode;
    private JComboBox<Supplier> cmbSupplier;
    private JComboBox<Kain> cmbKain;
    private JComboBox<String> cmbSatuan;
    private JTextField txtJumlah;
    private JTextField txtHargaBeli;
    private JLabel lblTotal;

    private JButton btnTambahItem;
    private JButton btnHapusItem;
    private JButton btnSimpan;
    private JButton btnBaru;

    private JTable tableDetail;
    private DefaultTableModel tableModel;

    private final SupplierService supplierService = new SupplierService();
    private final KainService kainService = new KainService();
    private final TransaksiPembelianService transaksiService = new TransaksiPembelianService();

    public TransaksiPembelianFrame(User userLogin) {
        this.userLogin = userLogin;

        initComponents();
        setupEscapeKey();
        loadSupplier();
        loadKain();
        buatTransaksiBaru();
        desainTabel();
        desainTombol();

        setTitle("Transaksi Pembelian");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Transaksi Pembelian"));

        txtKode = new JTextField();
        txtKode.setEditable(false);

        cmbSupplier = new JComboBox<>();
        cmbKain = new JComboBox<>();
        cmbSatuan = new JComboBox<>(new String[]{"meter", "roll", "yard", "kg"});

        txtJumlah = new JTextField();
        txtHargaBeli = new JTextField();

        lblTotal = new JLabel("Rp0");
        lblTotal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        btnTambahItem = new JButton("Tambah Item");
        btnHapusItem = new JButton("Hapus Item");
        btnSimpan = new JButton("Simpan Transaksi");
        btnBaru = new JButton("Transaksi Baru");

        panelForm.add(new JLabel("Kode Transaksi"));
        panelForm.add(txtKode);
        panelForm.add(new JLabel("Supplier"));
        panelForm.add(cmbSupplier);

        panelForm.add(new JLabel("Kain"));
        panelForm.add(cmbKain);
        panelForm.add(new JLabel("Satuan"));
        panelForm.add(cmbSatuan);

        panelForm.add(new JLabel("Jumlah"));
        panelForm.add(txtJumlah);
        panelForm.add(new JLabel("Harga Beli"));
        panelForm.add(txtHargaBeli);

        panelForm.add(new JLabel("Total"));
        panelForm.add(lblTotal);
        panelForm.add(new JLabel(""));
        panelForm.add(new JLabel(""));

        panelForm.add(btnTambahItem);
        panelForm.add(btnHapusItem);
        panelForm.add(btnSimpan);
        panelForm.add(btnBaru);

        tableModel = new DefaultTableModel(
                new Object[]{"No", "Nama Kain", "Satuan", "Jumlah", "Harga Beli", "Subtotal"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableDetail = new JTable(tableModel);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(new JScrollPane(tableDetail), BorderLayout.CENTER);

        add(panelUtama);

        btnTambahItem.addActionListener(e -> tambahItem());
        btnHapusItem.addActionListener(e -> hapusItem());
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
            btnTambahItem, btnHapusItem, btnSimpan, btnBaru
        };

        for (JButton btn : tombol) {
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            btn.setForeground(java.awt.Color.BLACK);
        }

        btnTambahItem.setBackground(new java.awt.Color(46, 204, 113));
        btnHapusItem.setBackground(new java.awt.Color(220, 53, 69));
        btnSimpan.setBackground(new java.awt.Color(52, 152, 219));
        btnBaru.setBackground(new java.awt.Color(108, 117, 125));
    }

    private void loadSupplier() {
        cmbSupplier.removeAllItems();

        for (Supplier supplier : supplierService.getAllSupplier()) {
            cmbSupplier.addItem(supplier);
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
            txtHargaBeli.setText("");
            lblTotal.setText("Rp0");
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
            double hargaBeli = Double.parseDouble(txtHargaBeli.getText().trim());

            transaksiService.tambahItem(transaksi, kain, satuan, jumlah, hargaBeli);
            refreshTable();

            txtJumlah.setText("");
            txtHargaBeli.setText("");
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

    private void simpanTransaksi() {
        try {
            Supplier supplier = (Supplier) cmbSupplier.getSelectedItem();
            transaksi.setSupplier(supplier);

            boolean hasil = transaksiService.simpanTransaksi(transaksi);

            if (hasil) {
                DialogUtil.showInfo(this, "Transaksi pembelian berhasil disimpan.");
                loadKain();
                buatTransaksiBaru();
            } else {
                DialogUtil.showError(this, "Transaksi pembelian gagal disimpan.");
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