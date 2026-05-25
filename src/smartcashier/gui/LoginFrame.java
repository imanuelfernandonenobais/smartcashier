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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import smartcashier.exception.AuthenticationException;
import smartcashier.exception.ValidationException;
import smartcashier.model.User;
import smartcashier.service.AuthService;
import smartcashier.utils.DialogUtil;

public class LoginFrame extends JFrame {

    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblUsername;
    private JLabel lblPassword;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnReset;

    private JPanel panelMain;
    private JPanel panelWrapper;
    private JPanel panelHeader;
    private JPanel panelForm;
    private JPanel panelButton;

    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setContentPane(new BackgroundPanel("/smartcashier/assets/background.jpg"));

        initComponents();
        setupKeyboardNavigation();

        setTitle("SmartCashier - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {

        Font titleFont = new Font("Segoe UI", Font.BOLD, 40);
        Font subtitleFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setOpaque(false);

        panelWrapper = new JPanel(new BorderLayout(22, 22));
        panelWrapper.setOpaque(true);
        panelWrapper.setBackground(new Color(255, 255, 255, 150));
        panelWrapper.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));
        panelWrapper.setPreferredSize(new Dimension(650, 470));

        panelHeader = new JPanel(new GridLayout(2, 1, 8, 8));
        panelHeader.setOpaque(true);
        panelHeader.setBackground(new Color(52, 152, 219, 210));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(28, 20, 28, 20));
        panelHeader.setPreferredSize(new Dimension(600, 160));

        lblTitle = new JLabel("SMARTCASHIER", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);

        lblSubtitle = new JLabel("Sistem Kasir Toko Kain", SwingConstants.CENTER);
        lblSubtitle.setFont(subtitleFont);
        lblSubtitle.setForeground(Color.WHITE);

        panelHeader.add(lblTitle);
        panelHeader.add(lblSubtitle);

        panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(true);
        panelForm.setBackground(new Color(255, 255, 255, 200));
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Login Sistem"),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblUsername = new JLabel("Username");
        lblUsername.setFont(labelFont);
        lblUsername.setForeground(Color.BLACK);

        lblPassword = new JLabel("Password");
        lblPassword.setFont(labelFont);
        lblPassword.setForeground(Color.BLACK);

        txtUsername = new JTextField();
        txtUsername.setFont(fieldFont);
        txtUsername.setPreferredSize(new Dimension(300, 42));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        txtPassword = new JPasswordField();
        txtPassword.setFont(fieldFont);
        txtPassword.setPreferredSize(new Dimension(300, 42));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelForm.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelForm.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelForm.add(txtPassword, gbc);

        panelButton = new JPanel(new GridLayout(1, 2, 20, 20));
        panelButton.setOpaque(false);
        panelButton.setPreferredSize(new Dimension(600, 55));

        btnLogin = new JButton("Login");
        btnReset = new JButton("Reset");

        desainTombol(btnLogin, buttonFont, new Color(0, 123, 255));
        desainTombol(btnReset, buttonFont, new Color(52, 58, 64));

        panelButton.add(btnLogin);
        panelButton.add(btnReset);

        panelWrapper.add(panelHeader, BorderLayout.NORTH);
        panelWrapper.add(panelForm, BorderLayout.CENTER);
        panelWrapper.add(panelButton, BorderLayout.SOUTH);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;

        panelMain.add(panelWrapper, mainGbc);

        add(panelMain);

        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        txtPassword.addActionListener(e -> prosesLogin());

        btnLogin.addActionListener(e -> prosesLogin());
        btnReset.addActionListener(e -> resetForm());

        java.awt.EventQueue.invokeLater(() -> txtUsername.requestFocusInWindow());
    }

    private void setupKeyboardNavigation() {
        JComponent[] komponen = {
            txtUsername,
            txtPassword,
            btnLogin,
            btnReset
        };

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

        getRootPane().getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahFokus(komponen, 1);
            }
        });

        getRootPane().getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pindahFokus(komponen, -1);
            }
        });

        getRootPane().getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (btnLogin.hasFocus()) {
                    btnReset.requestFocusInWindow();
                } else if (btnReset.hasFocus()) {
                    btnLogin.requestFocusInWindow();
                } else {
                    pindahFokus(komponen, 1);
                }
            }
        });

        getRootPane().getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (btnReset.hasFocus()) {
                    btnLogin.requestFocusInWindow();
                } else if (btnLogin.hasFocus()) {
                    btnReset.requestFocusInWindow();
                } else {
                    pindahFokus(komponen, -1);
                }
            }
        });

        getRootPane().getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (btnLogin.hasFocus()) {
                    btnLogin.doClick();
                } else if (btnReset.hasFocus()) {
                    btnReset.doClick();
                } else if (txtUsername.hasFocus()) {
                    txtPassword.requestFocusInWindow();
                } else if (txtPassword.hasFocus()) {
                    prosesLogin();
                }
            }
        });
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

    private void desainTombol(JButton button, Font font, Color warna) {
        button.setFont(font);
        button.setBackground(warna);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        Color warnaAsli = warna;
        Color warnaHover = warna.brighter();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaHover);
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaAsli);
                button.setForeground(Color.WHITE);
            }
        });
    }

    private void prosesLogin() {
        try {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User user = authService.login(username, password);

            DialogUtil.showInfo(this,
                    "Login berhasil. Selamat datang, "
                    + user.getNamaUser());

            DashboardFrame dashboard = new DashboardFrame(user);
            dashboard.setVisible(true);

            dispose();

        } catch (ValidationException e) {

            DialogUtil.showWarning(this, e.getMessage());

        } catch (AuthenticationException e) {

            DialogUtil.showError(this, e.getMessage());

        } catch (Exception e) {

            DialogUtil.showError(this,
                    "Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void resetForm() {
        txtUsername.setText("");
        txtPassword.setText("");

        txtUsername.requestFocusInWindow();
    }
}