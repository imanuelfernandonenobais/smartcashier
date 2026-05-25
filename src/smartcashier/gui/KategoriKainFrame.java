package smartcashier.gui;

/*
 * @author Kel Uyah
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
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
import smartcashier.model.KategoriKain;
import smartcashier.service.KategoriKainService;
import smartcashier.utils.DialogUtil;

public class KategoriKainFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNamaKategori;

    private JButton btnSimpan;
    private JButton btnReset;
    private JButton btnHapus;

    private JTable tableKategori;
    private DefaultTableModel tableModel;

    private final KategoriKainService kategoriService = new KategoriKainService();

    public KategoriKainFrame() {
        initComponents();
        setupEscapeKey();
        setupKeyboardNavigation();
        loadData();
        desainTabel();

        setTitle("Data Kategori Kain");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnSimpan.setBackground(new java.awt.Color(34, 139, 34));
        btnSimpan.setForeground(java.awt.Color.BLACK);

        btnReset.setBackground(new java.awt.Color(255, 193, 7));
        btnReset.setForeground(java.awt.Color.BLACK);

        btnHapus.setBackground(new java.awt.Color(220, 53, 69));
        btnHapus.setForeground(java.awt.Color.BLACK);
    }

    private void initComponents() {

        JPanel panelUtama = new JPanel(new BorderLayout(15, 15));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Kategori"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtNamaKategori = new JTextField();

        txtId.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtNamaKategori.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        panelForm.add(new JLabel("ID Kategori"));
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nama Kategori"));
        panelForm.add(txtNamaKategori);

        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnHapus = new JButton("Hapus");

        panelForm.add(btnSimpan);
        panelForm.add(btnReset);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama Kategori"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKategori = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableKategori);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(btnHapus, BorderLayout.EAST);

        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        panelUtama.add(panelBawah, BorderLayout.SOUTH);

        add(panelUtama);

        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnHapus.addActionListener(e -> hapusData());

        tableKategori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });
    }

    private void setupEscapeKey() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");

        getRootPane().getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private void setupKeyboardNavigation() {
        JComponent[] komponen = {
            txtNamaKategori,
            btnSimpan,
            btnReset,
            tableKategori,
            btnHapus
        };

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

        getRootPane().getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahFokus(komponen, 1);
            }
        });

        getRootPane().getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahFokus(komponen, -1);
            }
        });

        getRootPane().getActionMap().put("tab", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahFokus(komponen, 1);
            }
        });

        getRootPane().getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (getFocusOwner() instanceof JButton) {
                    ((JButton) getFocusOwner()).doClick();
                } else if (txtNamaKategori.hasFocus()) {
                    btnSimpan.requestFocusInWindow();
                }
            }
        });

        txtNamaKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "textDown");

        txtNamaKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "textUp");

        txtNamaKategori.getActionMap().put("textDown", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnSimpan.requestFocusInWindow();
            }
        });

        txtNamaKategori.getActionMap().put("textUp", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnHapus.requestFocusInWindow();
            }
        });

        btnSimpan.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "simpanDown");

        btnSimpan.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "simpanUp");

        btnSimpan.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "simpanRight");

        btnSimpan.getActionMap().put("simpanDown", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tableKategori.requestFocusInWindow();
                pilihBarisPertamaJikaAda();
            }
        });

        btnSimpan.getActionMap().put("simpanUp", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtNamaKategori.requestFocusInWindow();
            }
        });

        btnSimpan.getActionMap().put("simpanRight", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnReset.requestFocusInWindow();
            }
        });

        btnReset.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "resetDown");

        btnReset.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "resetUp");

        btnReset.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "resetLeft");

        btnReset.getActionMap().put("resetDown", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tableKategori.requestFocusInWindow();
                pilihBarisPertamaJikaAda();
            }
        });

        btnReset.getActionMap().put("resetUp", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtNamaKategori.requestFocusInWindow();
            }
        });

        btnReset.getActionMap().put("resetLeft", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnSimpan.requestFocusInWindow();
            }
        });

        tableKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "tableRight");

        tableKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "tableLeft");

        tableKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tableTab");

        tableKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), "tableToHapus");

        tableKategori.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "tableToForm");

        tableKategori.getActionMap().put("tableRight", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnHapus.requestFocusInWindow();
            }
        });

        tableKategori.getActionMap().put("tableLeft", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnReset.requestFocusInWindow();
            }
        });

        tableKategori.getActionMap().put("tableTab", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnHapus.requestFocusInWindow();
            }
        });

        tableKategori.getActionMap().put("tableToHapus", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnHapus.requestFocusInWindow();
            }
        });

        tableKategori.getActionMap().put("tableToForm", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtNamaKategori.requestFocusInWindow();
            }
        });

        btnHapus.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "hapusUp");

        btnHapus.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "hapusLeft");

        btnHapus.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "hapusDown");

        btnHapus.getActionMap().put("hapusUp", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tableKategori.requestFocusInWindow();
                pilihBarisPertamaJikaAda();
            }
        });

        btnHapus.getActionMap().put("hapusLeft", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tableKategori.requestFocusInWindow();
                pilihBarisPertamaJikaAda();
            }
        });

        btnHapus.getActionMap().put("hapusDown", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtNamaKategori.requestFocusInWindow();
            }
        });

        java.awt.EventQueue.invokeLater(() -> txtNamaKategori.requestFocusInWindow());
    }

    private void pindahFokus(JComponent[] komponen, int langkah) {
        for (int i = 0; i < komponen.length; i++) {
            if (komponen[i].hasFocus()) {
                int indexBaru = i + langkah;

                if (indexBaru < 0) {
                    indexBaru = komponen.length - 1;
                }

                if (indexBaru >= komponen.length) {
                    indexBaru = 0;
                }

                komponen[indexBaru].requestFocusInWindow();
                return;
            }
        }

        komponen[0].requestFocusInWindow();
    }

    private void pilihBarisPertamaJikaAda() {
        if (tableKategori.getRowCount() > 0 && tableKategori.getSelectedRow() < 0) {
            tableKategori.setRowSelectionInterval(0, 0);
        }
    }

    private void desainTabel() {

        tableKategori.setRowHeight(35);

        tableKategori.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        tableKategori.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        tableKategori.getTableHeader().setBackground(
                new java.awt.Color(52, 152, 219));

        tableKategori.getTableHeader().setForeground(
                java.awt.Color.BLACK);

        tableKategori.setSelectionBackground(
                new java.awt.Color(174, 214, 241));

        tableKategori.setSelectionForeground(
                java.awt.Color.BLACK);

        tableKategori.setShowGrid(false);

        tableKategori.setIntercellSpacing(
                new java.awt.Dimension(0, 0));

        tableKategori.setGridColor(
                new java.awt.Color(230, 230, 230));

        javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

        center.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        tableKategori.getColumnModel().getColumn(0)
                .setCellRenderer(center);

        tableKategori.getColumnModel().getColumn(0)
                .setPreferredWidth(80);

        tableKategori.getColumnModel().getColumn(1)
                .setPreferredWidth(300);
    }

    private void loadData() {

        tableModel.setRowCount(0);

        List<KategoriKain> list = kategoriService.getAllKategori();

        for (KategoriKain kategori : list) {

            tableModel.addRow(new Object[]{
                kategori.getIdKategori(),
                kategori.getNamaKategori()
            });
        }
    }

    private void simpanData() {

        try {

            KategoriKain kategori = new KategoriKain();

            if (!txtId.getText().trim().isEmpty()) {
                kategori.setIdKategori(
                        Integer.parseInt(txtId.getText()));
            }

            kategori.setNamaKategori(
                    txtNamaKategori.getText());

            boolean hasil =
                    kategoriService.simpanKategori(kategori);

            if (hasil) {

                DialogUtil.showInfo(this,
                        "Data kategori berhasil disimpan.");

                loadData();
                resetForm();

            } else {

                DialogUtil.showError(this,
                        "Data kategori gagal disimpan.");
            }

        } catch (Exception e) {

            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void hapusData() {

        try {

            if (txtId.getText().trim().isEmpty()) {

                DialogUtil.showWarning(this,
                        "Pilih data yang akan dihapus.");

                return;
            }

            if (DialogUtil.confirm(this,
                    "Yakin ingin menghapus data kategori?")) {

                boolean hasil =
                        kategoriService.hapusKategori(
                                Integer.parseInt(txtId.getText()));

                if (hasil) {

                    DialogUtil.showInfo(this,
                            "Data kategori berhasil dihapus.");

                    loadData();
                    resetForm();

                } else {

                    DialogUtil.showError(this,
                            "Data kategori gagal dihapus.");
                }
            }

        } catch (Exception e) {

            DialogUtil.showError(this, e.getMessage());
        }
    }

    private void isiFormDariTabel() {

        int row = tableKategori.getSelectedRow();

        if (row >= 0) {

            txtId.setText(
                    tableModel.getValueAt(row, 0).toString());

            txtNamaKategori.setText(
                    tableModel.getValueAt(row, 1).toString());
        }
    }

    private void resetForm() {

        txtId.setText("");
        txtNamaKategori.setText("");

        tableKategori.clearSelection();

        txtNamaKategori.requestFocusInWindow();
    }
}