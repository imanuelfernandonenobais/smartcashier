package smartcashier.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    private final Image backgroundImage;

    public BackgroundPanel(String path) {
        URL location = getClass().getResource(path);

        if (location == null) {
            throw new IllegalArgumentException("Gambar tidak ditemukan: " + path);
        }

        ImageIcon icon = new ImageIcon(location);
        backgroundImage = icon.getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}