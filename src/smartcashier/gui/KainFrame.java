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
import smartcashier.model.Kain;
import smartcashier.model.KategoriKain;
import smartcashier.service.KainService;
import smartcashier.service.KategoriKainService;
import smartcashier.utils.DialogUtil;

public class KainFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNamaKain;
    private JComboBox<KategoriKain> cmbKategori;

    private JTextField txtHargaMeter;
    private JTextField txtHargaRoll;
    private JTextField txtHargaYard;
    private JTextField txtHargaKg;

    private JTextField txtStokMeter;
    private JTextField txtStokRoll;
    private JTextField txtStokYard;
    private JTextField txtStokKg;

    private JButton btnSimpan;
    private JButton btnReset;
    private JButton btnHapus;

    private JTable tableKain;
    private DefaultTableModel tableModel;

    private final KainService kainService = new KainService();
    private final KategoriKainService kategoriService = new KategoriKainService();

    public KainFrame() {
        initComponents();
        setupEscapeKey();
        loadKategori();
        loadData();
        desainTabel();
        desainTombol();

        setTitle("Data Kain");
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(6, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Data Kain"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtNamaKain = new JTextField();
        cmbKategori = new JComboBox<>();

        txtHargaMeter = new JTextField("0");
        txtHargaRoll = new JTextField("0");
        txtHargaYard = new JTextField("0");
        txtHargaKg = new JTextField("0");

        txtStokMeter = new JTextField("0");
        txtStokRoll = new JTextField("0");
        txtStokYard = new JTextField("0");
        txtStokKg = new JTextField("0");

        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnHapus = new JButton("Hapus");

        panelForm.add(new JLabel("ID Kain"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nama Kain"));
        panelForm.add(txtNamaKain);

        panelForm.add(new JLabel("Kategori"));
        panelForm.add(cmbKategori);
        panelForm.add(new JLabel("Harga / Meter"));
        panelForm.add(txtHargaMeter);

        panelForm.add(new JLabel("Harga / Roll"));
        panelForm.add(txtHargaRoll);
        panelForm.add(new JLabel("Harga / Yard"));
        panelForm.add(txtHargaYard);

        panelForm.add(new JLabel("Harga / Kg"));
        panelForm.add(txtHargaKg);
        panelForm.add(new JLabel("Stok Meter"));
        panelForm.add(txtStokMeter);

        panelForm.add(new JLabel("Stok Roll"));
        panelForm.add(txtStokRoll);
        panelForm.add(new JLabel("Stok Yard"));
        panelForm.add(txtStokYard);

        panelForm.add(new JLabel("Stok Kg"));
        panelForm.add(txtStokKg);
        panelForm.add(btnSimpan);
        panelForm.add(btnReset);

        tableModel = new DefaultTableModel(
                new Object[]{
                    "ID", "Nama", "Kategori", "Harga Meter", "Harga Roll",
                    "Harga Yard", "Harga Kg", "Stok Meter", "Stok Roll",
                    "Stok Yard", "Stok Kg"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKain = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableKain);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(btnHapus, BorderLayout.EAST);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        panelUtama.add(panelBottom, BorderLayout.SOUTH);

        add(panelUtama);

        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnHapus.addActionListener(e -> hapusData());

        tableKain.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });
    }

    private void setupEscapeKey() {
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void desainTabel() {
        tableKain.setRowHeight(35);

        tableKain.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        tableKain.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        tableKain.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219)
        );

        tableKain.getTableHeader().setForeground(
                java.awt.Color.BLACK
        );

        tableKain.getTableHeader().setReorderingAllowed(false);

        tableKain.setSelectionBackground(
                new java.awt.Color(174, 214, 241)
        );

        tableKain.setSelectionForeground(
                java.awt.Color.BLACK
        );

        tableKain.setShowGrid(false);

        tableKain.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        tableKain.setGridColor(
                new java.awt.Color(230, 230, 230)
        );

        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

        center.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        tableKain.getColumnModel().getColumn(0).setCellRenderer(center);

        for (int i = 3; i < tableKain.getColumnCount(); i++) {
            tableKain.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        tableKain.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableKain.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableKain.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableKain.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(6).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(7).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(8).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(9).setPreferredWidth(100);
        tableKain.getColumnModel().getColumn(10).setPreferredWidth(100);
    }

    private void desainTombol() {
        btnSimpan.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnReset.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnHapus.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

        btnSimpan.setBackground(new java.awt.Color(46, 204, 113));
        btnSimpan.setForeground(java.awt.Color.BLACK);

        btnReset.setBackground(new java.awt.Color(255, 193, 7));
        btnReset.setForeground(java.awt.Color.BLACK);

        btnHapus.setBackground(new java.awt.Color(220, 53, 69));
        btnHapus.setForeground(java.awt.Color.BLACK);
    }

    private void loadKategori() {
        cmbKategori.removeAllItems();

        for (KategoriKain kategori : kategoriService.getAllKategori()) {
            cmbKategori.addItem(kategori);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Kain> list = kainService.getAllKain();

        for (Kain kain : list) {
            tableModel.addRow(new Object[]{
                kain.getIdKain(),
                kain.getNamaKain(),
                kain.getKategori() != null ? kain.getKategori().getNamaKategori() : "",
                kain.getHargaPerMeter(),
                kain.getHargaPerRoll(),
                kain.getHargaPerYard(),
                kain.getHargaPerKg(),
                kain.getStokMeter(),
                kain.getStokRoll(),
                kain.getStokYard(),
                kain.getStokKg()
            });
        }
    }

    private void simpanData() {
        try {
            Kain kain = new Kain();

            if (!txtId.getText().trim().isEmpty()) {
                kain.setIdKain(Integer.parseInt(txtId.getText()));
            }

            kain.setNamaKain(txtNamaKain.getText());
            kain.setKategori((KategoriKain) cmbKategori.getSelectedItem());
            kain.setHargaPerMeter(parseDouble(txtHargaMeter.getText()));
            kain.setHargaPerRoll(parseDouble(txtHargaRoll.getText()));
            kain.setHargaPerYard(parseDouble(txtHargaYard.getText()));
            kain.setHargaPerKg(parseDouble(txtHargaKg.getText()));
            kain.setStokMeter(parseDouble(txtStokMeter.getText()));
            kain.setStokRoll(parseDouble(txtStokRoll.getText()));
            kain.setStokYard(parseDouble(txtStokYard.getText()));
            kain.setStokKg(parseDouble(txtStokKg.getText()));

            boolean hasil = kainService.simpanKain(kain);

            if (hasil) {
                DialogUtil.showInfo(this, "Data kain berhasil disimpan.");
                loadData();
                resetForm();
            } else {
                DialogUtil.showError(this, "Data kain gagal disimpan.");
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hapusData() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                DialogUtil.showWarning(this, "Pilih data kain yang akan dihapus.");
                return;
            }

            if (DialogUtil.confirm(this, "Yakin ingin menghapus data kain?")) {
                boolean hasil = kainService.hapusKain(Integer.parseInt(txtId.getText()));

                if (hasil) {
                    DialogUtil.showInfo(this, "Data kain berhasil dihapus.");
                    loadData();
                    resetForm();
                } else {
                    DialogUtil.showError(this, "Data kain gagal dihapus.");
                }
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void isiFormDariTabel() {
        int row = tableKain.getSelectedRow();

        if (row >= 0) {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtNamaKain.setText(tableModel.getValueAt(row, 1).toString());
            pilihKategori(tableModel.getValueAt(row, 2).toString());
            txtHargaMeter.setText(tableModel.getValueAt(row, 3).toString());
            txtHargaRoll.setText(tableModel.getValueAt(row, 4).toString());
            txtHargaYard.setText(tableModel.getValueAt(row, 5).toString());
            txtHargaKg.setText(tableModel.getValueAt(row, 6).toString());
            txtStokMeter.setText(tableModel.getValueAt(row, 7).toString());
            txtStokRoll.setText(tableModel.getValueAt(row, 8).toString());
            txtStokYard.setText(tableModel.getValueAt(row, 9).toString());
            txtStokKg.setText(tableModel.getValueAt(row, 10).toString());
        }
    }

    private void pilihKategori(String namaKategori) {
        for (int i = 0; i < cmbKategori.getItemCount(); i++) {
            KategoriKain kategori = cmbKategori.getItemAt(i);

            if (kategori.getNamaKategori().equalsIgnoreCase(namaKategori)) {
                cmbKategori.setSelectedIndex(i);
                break;
            }
        }
    }

    private double parseDouble(String text) {
        return Double.parseDouble(text.trim());
    }

    private void resetForm() {
        txtId.setText("");
        txtNamaKain.setText("");
        txtHargaMeter.setText("0");
        txtHargaRoll.setText("0");
        txtHargaYard.setText("0");
        txtHargaKg.setText("0");
        txtStokMeter.setText("0");
        txtStokRoll.setText("0");
        txtStokYard.setText("0");
        txtStokKg.setText("0");

        tableKain.clearSelection();
        txtNamaKain.requestFocus();
    }
}