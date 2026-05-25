package smartcashier.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import smartcashier.model.User;
import smartcashier.utils.DialogUtil;

public class DashboardFrame extends JFrame {

    private JLabel lblTitle;
    private JLabel lblWelcome;
    private JLabel lblRole;

    private JButton btnKategori;
    private JButton btnKain;
    private JButton btnPelanggan;
    private JButton btnSupplier;
    private JButton btnPenjualan;
    private JButton btnPembelian;
    private JButton btnLaporan;
    private JButton btnLogout;

    private JPanel panelMain;
    private JPanel panelWrapper;
    private JPanel panelHeader;
    private JPanel panelCenter;
    private JPanel panelInfo;
    private JPanel panelMenu;

    private final User userLogin;

    public DashboardFrame(User userLogin) {
        this.userLogin = userLogin;

        setContentPane(new BackgroundPanel("/smartcashier/assets/background.jpg"));

        initComponents();
        setupEscapeKey();
        setupKeyboardNavigation();

        setTitle("SmartCashier - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 36);
        Font welcomeFont = new Font("Segoe UI", Font.BOLD, 24);
        Font roleFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 17);

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setOpaque(false);

        panelWrapper = new JPanel(new BorderLayout(25, 25));
        panelWrapper.setOpaque(true);
        panelWrapper.setBackground(new Color(255, 255, 255, 145));
        panelWrapper.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelWrapper.setPreferredSize(new Dimension(1100, 620));

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(true);
        panelHeader.setBackground(new Color(52, 152, 219, 210));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelHeader.setPreferredSize(new Dimension(100, 110));

        lblTitle = new JLabel("DASHBOARD SMARTCASHIER", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(titleFont);
        panelHeader.add(lblTitle, BorderLayout.CENTER);

        panelCenter = new JPanel(new GridLayout(1, 2, 25, 25));
        panelCenter.setOpaque(false);

        panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setOpaque(true);
        panelInfo.setBackground(new Color(255, 255, 255, 190));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Informasi User"),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        JPanel isiInfo = new JPanel(new GridLayout(3, 1, 10, 10));
        isiInfo.setOpaque(false);

        JLabel lblIcon = new JLabel("👤", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));

        lblWelcome = new JLabel("Selamat datang,", JLabel.CENTER);
        lblWelcome.setFont(roleFont);
        lblWelcome.setForeground(Color.DARK_GRAY);

        lblRole = new JLabel(userLogin.getNamaUser() + "  |  Role: " + userLogin.getRole(), JLabel.CENTER);
        lblRole.setFont(welcomeFont);
        lblRole.setForeground(Color.BLACK);

        isiInfo.add(lblIcon);
        isiInfo.add(lblWelcome);
        isiInfo.add(lblRole);

        panelInfo.add(isiInfo);

        panelMenu = new JPanel(new GridLayout(4, 2, 18, 18));
        panelMenu.setOpaque(true);
        panelMenu.setBackground(new Color(255, 255, 255, 190));
        panelMenu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Menu Utama"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        btnKategori = new JButton("Data Kategori");
        btnKain = new JButton("Data Kain");
        btnPelanggan = new JButton("Data Pelanggan");
        btnSupplier = new JButton("Data Supplier");
        btnPenjualan = new JButton("Transaksi Penjualan");
        btnPembelian = new JButton("Transaksi Pembelian");
        btnLaporan = new JButton("Laporan");
        btnLogout = new JButton("Logout");

        desainTombol(btnKategori, buttonFont, new Color(52, 152, 219));
        desainTombol(btnKain, buttonFont, new Color(52, 152, 219));
        desainTombol(btnPelanggan, buttonFont, new Color(46, 204, 113));
        desainTombol(btnSupplier, buttonFont, new Color(46, 204, 113));
        desainTombol(btnPenjualan, buttonFont, new Color(155, 89, 182));
        desainTombol(btnPembelian, buttonFont, new Color(155, 89, 182));
        desainTombol(btnLaporan, buttonFont, new Color(241, 196, 15));
        desainTombol(btnLogout, buttonFont, new Color(231, 76, 60));

        panelMenu.add(btnKategori);
        panelMenu.add(btnKain);
        panelMenu.add(btnPelanggan);
        panelMenu.add(btnSupplier);
        panelMenu.add(btnPenjualan);
        panelMenu.add(btnPembelian);
        panelMenu.add(btnLaporan);
        panelMenu.add(btnLogout);

        panelCenter.add(panelInfo);
        panelCenter.add(panelMenu);

        panelWrapper.add(panelHeader, BorderLayout.NORTH);
        panelWrapper.add(panelCenter, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        panelMain.add(panelWrapper, gbc);

        add(panelMain);

        btnKategori.addActionListener(e -> new KategoriKainFrame().setVisible(true));
        btnKain.addActionListener(e -> new KainFrame().setVisible(true));
        btnPelanggan.addActionListener(e -> new PelangganFrame().setVisible(true));
        btnSupplier.addActionListener(e -> new SupplierFrame().setVisible(true));
        btnPenjualan.addActionListener(e -> new TransaksiPenjualanFrame(userLogin).setVisible(true));
        btnPembelian.addActionListener(e -> new TransaksiPembelianFrame(userLogin).setVisible(true));
        btnLaporan.addActionListener(e -> new LaporanFrame().setVisible(true));
        btnLogout.addActionListener(e -> logout());

        aturHakAkses();
    }

    private void desainTombol(JButton button, Font font, Color warna) {
        button.setFont(font);
        button.setBackground(warna);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(true);
        button.setFocusable(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Color warnaAsli = warna;
        Color warnaHover = warna.brighter();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaAsli);
            }
        });
    }

    private void setupKeyboardNavigation() {
        JButton[][] grid = {
            {btnKategori, btnKain},
            {btnPelanggan, btnSupplier},
            {btnPenjualan, btnPembelian},
            {btnLaporan, btnLogout}
        };

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

        getRootPane().getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahGrid(grid, -1, 0);
            }
        });

        getRootPane().getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahGrid(grid, 1, 0);
            }
        });

        getRootPane().getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahGrid(grid, 0, -1);
            }
        });

        getRootPane().getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahGrid(grid, 0, 1);
            }
        });

        getRootPane().getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (getFocusOwner() instanceof JButton) {
                    ((JButton) getFocusOwner()).doClick();
                }
            }
        });

        java.awt.EventQueue.invokeLater(() -> btnKategori.requestFocusInWindow());
    }

    private void pindahGrid(JButton[][] grid, int arahBaris, int arahKolom) {
        int barisAktif = -1;
        int kolomAktif = -1;

        for (int baris = 0; baris < grid.length; baris++) {
            for (int kolom = 0; kolom < grid[baris].length; kolom++) {
                if (grid[baris][kolom].hasFocus()) {
                    barisAktif = baris;
                    kolomAktif = kolom;
                    break;
                }
            }
        }

        if (barisAktif == -1 || kolomAktif == -1) {
            grid[0][0].requestFocusInWindow();
            return;
        }

        int barisBaru = barisAktif + arahBaris;
        int kolomBaru = kolomAktif + arahKolom;

        if (barisBaru < 0) {
            barisBaru = grid.length - 1;
        }

        if (barisBaru >= grid.length) {
            barisBaru = 0;
        }

        if (kolomBaru < 0) {
            kolomBaru = grid[barisBaru].length - 1;
        }

        if (kolomBaru >= grid[barisBaru].length) {
            kolomBaru = 0;
        }

        grid[barisBaru][kolomBaru].requestFocusInWindow();
    }

    private void setupEscapeKey() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");

        getRootPane().getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                logout();
            }
        });
    }

    private void aturHakAkses() {
        if ("kasir".equalsIgnoreCase(userLogin.getRole())) {
            btnKategori.setEnabled(false);
            btnKain.setEnabled(false);
            btnSupplier.setEnabled(false);
        }
    }

    private void logout() {
        Object[] options = {"Yes", "No"};

        JOptionPane optionPane = new JOptionPane(
                "Yakin ingin logout?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]
        );

        JDialog dialog = optionPane.createDialog(this, "Konfirmasi Logout");

        JButton btnYes = new JButton("Yes");
        JButton btnNo = new JButton("No");

        btnYes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        btnYes.setPreferredSize(new Dimension(120, 40));
        btnNo.setPreferredSize(new Dimension(120, 40));

        optionPane.setOptions(new Object[]{btnYes, btnNo});
        optionPane.setInitialValue(btnYes);

        btnYes.addActionListener(e -> {
            optionPane.setValue("Yes");
            dialog.dispose();
        });

        btnNo.addActionListener(e -> {
            optionPane.setValue("No");
            dialog.dispose();
        });

        btnYes.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightToNo");

        btnYes.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftStayYes");

        btnYes.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterYes");

        btnNo.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftToYes");

        btnNo.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightStayNo");

        btnNo.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterNo");

        btnYes.getActionMap().put("rightToNo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnNo.requestFocusInWindow();
            }
        });

        btnYes.getActionMap().put("leftStayYes", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnYes.requestFocusInWindow();
            }
        });

        btnYes.getActionMap().put("enterYes", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                optionPane.setValue("Yes");
                dialog.dispose();
            }
        });

        btnNo.getActionMap().put("leftToYes", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnYes.requestFocusInWindow();
            }
        });

        btnNo.getActionMap().put("rightStayNo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnNo.requestFocusInWindow();
            }
        });

        btnNo.getActionMap().put("enterNo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                optionPane.setValue("No");
                dialog.dispose();
            }
        });

        dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapeDialog");

        dialog.getRootPane().getActionMap().put("escapeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                optionPane.setValue("No");
                dialog.dispose();
            }
        });

        java.awt.EventQueue.invokeLater(() -> btnYes.requestFocusInWindow());

        dialog.setVisible(true);

        Object value = optionPane.getValue();

        if ("Yes".equals(value)) {
            DialogUtil.showInfo(this, "Anda berhasil logout.");
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}