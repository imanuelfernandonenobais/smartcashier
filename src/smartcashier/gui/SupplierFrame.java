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
import smartcashier.model.Supplier;
import smartcashier.service.SupplierService;
import smartcashier.utils.DialogUtil;

public class SupplierFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtNoHp;

    private JButton btnSimpan;
    private JButton btnReset;
    private JButton btnHapus;

    private JTable tableSupplier;
    private DefaultTableModel tableModel;

    private final SupplierService supplierService = new SupplierService();

    public SupplierFrame() {
        initComponents();
        setupEscapeKey();
        loadData();
        desainTabel();
        desainTombol();

        setTitle("Data Supplier");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Supplier"));

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
        panelForm.add(new JLabel("ID Supplier"), gbc);

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

        tableSupplier = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableSupplier);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(btnHapus, BorderLayout.EAST);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        panelUtama.add(panelBottom, BorderLayout.SOUTH);

        add(panelUtama);

        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnHapus.addActionListener(e -> hapusData());

        tableSupplier.getSelectionModel().addListSelectionListener(e -> {
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
        tableSupplier.setRowHeight(35);

        tableSupplier.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        tableSupplier.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        tableSupplier.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219)
        );

        tableSupplier.getTableHeader().setForeground(
                java.awt.Color.BLACK
        );

        tableSupplier.getTableHeader().setReorderingAllowed(false);

        tableSupplier.setSelectionBackground(
                new java.awt.Color(174, 214, 241)
        );

        tableSupplier.setSelectionForeground(
                java.awt.Color.BLACK
        );

        tableSupplier.setShowGrid(false);

        tableSupplier.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        tableSupplier.setGridColor(
                new java.awt.Color(230, 230, 230)
        );

        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

        center.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        tableSupplier.getColumnModel().getColumn(0).setCellRenderer(center);
        tableSupplier.getColumnModel().getColumn(3).setCellRenderer(center);

        tableSupplier.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableSupplier.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableSupplier.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableSupplier.getColumnModel().getColumn(3).setPreferredWidth(150);
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
        List<Supplier> list = supplierService.getAllSupplier();

        for (Supplier supplier : list) {
            tableModel.addRow(new Object[]{
                supplier.getIdSupplier(),
                supplier.getNamaSupplier(),
                supplier.getAlamat(),
                supplier.getNoHp()
            });
        }
    }

    private void simpanData() {
        try {
            Supplier supplier = new Supplier();

            if (!txtId.getText().trim().isEmpty()) {
                supplier.setIdSupplier(Integer.parseInt(txtId.getText()));
            }

            supplier.setNamaSupplier(txtNama.getText());
            supplier.setAlamat(txtAlamat.getText());
            supplier.setNoHp(txtNoHp.getText());

            boolean hasil = supplierService.simpanSupplier(supplier);

            if (hasil) {
                DialogUtil.showInfo(this, "Data supplier berhasil disimpan.");
                loadData();
                resetForm();
            } else {
                DialogUtil.showError(this, "Data supplier gagal disimpan.");
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hapusData() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                DialogUtil.showWarning(this, "Pilih data supplier yang akan dihapus.");
                return;
            }

            if (DialogUtil.confirm(this, "Yakin ingin menghapus data supplier?")) {
                boolean hasil = supplierService.hapusSupplier(
                        Integer.parseInt(txtId.getText())
                );

                if (hasil) {
                    DialogUtil.showInfo(this, "Data supplier berhasil dihapus.");
                    loadData();
                    resetForm();
                } else {
                    DialogUtil.showError(this, "Data supplier gagal dihapus.");
                }
            }

        } catch (Exception e) {
            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void isiFormDariTabel() {
        int row = tableSupplier.getSelectedRow();

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

        tableSupplier.clearSelection();
        txtNama.requestFocus();
    }
}