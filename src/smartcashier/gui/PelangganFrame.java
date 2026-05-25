package smartcashier.gui;

/*
 * @author Kel Uyah
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import smartcashier.model.Pelanggan;
import smartcashier.service.PelangganService;
import smartcashier.utils.DialogUtil;

public class PelangganFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtNoHp;

    private JButton btnSimpan;
    private JButton btnReset;
    private JButton btnHapus;

    private JTable tablePelanggan;
    private DefaultTableModel tableModel;

    private final PelangganService pelangganService = new PelangganService();

    public PelangganFrame() {
        initComponents();
        setupEscapeKey();
        loadData();
        desainTabel();
        desainTombol();

        setTitle("Data Pelanggan");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Pelanggan"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtId.setEditable(false);

        txtNama = new JTextField();
        txtAlamat = new JTextField();
        txtNoHp = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnHapus = new JButton("Hapus");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelForm.add(new JLabel("ID Pelanggan"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelForm.add(txtId, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Nama"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelForm.add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Alamat"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelForm.add(txtAlamat, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("No HP"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelForm.add(txtNoHp, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelForm.add(btnReset, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelForm.add(btnSimpan, gbc);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Alamat", "No HP"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePelanggan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablePelanggan);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(btnHapus, BorderLayout.EAST);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        panelUtama.add(panelBottom, BorderLayout.SOUTH);

        add(panelUtama);

        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnHapus.addActionListener(e -> hapusData());

        tablePelanggan.getSelectionModel().addListSelectionListener(e -> {
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
        tablePelanggan.setRowHeight(35);

        tablePelanggan.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        tablePelanggan.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        tablePelanggan.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219)
        );

        tablePelanggan.getTableHeader().setForeground(
                java.awt.Color.BLACK
        );

        tablePelanggan.getTableHeader().setReorderingAllowed(false);

        tablePelanggan.setSelectionBackground(
                new java.awt.Color(174, 214, 241)
        );

        tablePelanggan.setSelectionForeground(
                java.awt.Color.BLACK
        );

        tablePelanggan.setShowGrid(false);

        tablePelanggan.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        tablePelanggan.setGridColor(
                new java.awt.Color(230, 230, 230)
        );

        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

        center.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        tablePelanggan.getColumnModel().getColumn(0).setCellRenderer(center);
        tablePelanggan.getColumnModel().getColumn(3).setCellRenderer(center);

        tablePelanggan.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablePelanggan.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablePelanggan.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablePelanggan.getColumnModel().getColumn(3).setPreferredWidth(150);
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

    private void loadData() {
        tableModel.setRowCount(0);
        List<Pelanggan> list = pelangganService.getAllPelanggan();

        for (Pelanggan pelanggan : list) {
            tableModel.addRow(new Object[]{
                pelanggan.getIdPelanggan(),
                pelanggan.getNamaPelanggan(),
                pelanggan.getAlamat(),
                pelanggan.getNoHp()
            });
        }
    }

    private void simpanData() {
        try {
            Pelanggan pelanggan = new Pelanggan();

            if (!txtId.getText().trim().isEmpty()) {
                pelanggan.setIdPelanggan(Integer.parseInt(txtId.getText()));
            }

            pelanggan.setNamaPelanggan(txtNama.getText());
            pelanggan.setAlamat(txtAlamat.getText());
            pelanggan.setNoHp(txtNoHp.getText());

            boolean hasil = pelangganService.simpanPelanggan(pelanggan);

            if (hasil) {
                DialogUtil.showInfo(this, "Data pelanggan berhasil disimpan.");
                loadData();
                resetForm();
            } else {
                DialogUtil.showError(this, "Data pelanggan gagal disimpan.");
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hapusData() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                DialogUtil.showWarning(this, "Pilih data pelanggan yang akan dihapus.");
                return;
            }

            if (DialogUtil.confirm(this, "Yakin ingin menghapus data pelanggan?")) {
                boolean hasil = pelangganService.hapusPelanggan(
                        Integer.parseInt(txtId.getText())
                );

                if (hasil) {
                    DialogUtil.showInfo(this, "Data pelanggan berhasil dihapus.");
                    loadData();
                    resetForm();
                } else {
                    DialogUtil.showError(this, "Data pelanggan gagal dihapus.");
                }
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void isiFormDariTabel() {
        int row = tablePelanggan.getSelectedRow();

        if (row >= 0) {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtNama.setText(tableModel.getValueAt(row, 1).toString());
            txtAlamat.setText(tableModel.getValueAt(row, 2).toString());
            txtNoHp.setText(tableModel.getValueAt(row, 3).toString());
        }
    }

    private void resetForm() {
        txtId.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");

        tablePelanggan.clearSelection();
        txtNama.requestFocus();
    }
}