package smartcashier.gui;
/*
 * @author Kel Uyah
 */

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Gagal mengatur Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}