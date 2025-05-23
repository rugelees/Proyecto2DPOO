package vistaAdmin;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class BannerParque extends JPanel {

    private Image fondo;

    public BannerParque(String rutaImagen) {
        this.fondo = new ImageIcon(rutaImagen).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
