package vistaAdmin;

import javax.swing.*;
import java.awt.*;
import modelo.usuarios.Administrador;

@SuppressWarnings("serial")
public class VentanaIncioSesion extends JFrame {

    private Administrador admin;

    public VentanaIncioSesion(Administrador admin) {
        this.admin = admin;

        setTitle("Sesión Administrador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelBienvenida = new JLabel("¡Bienvenido, " + admin.getNombre() + "!", SwingConstants.CENTER);
        labelBienvenida.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(labelBienvenida, BorderLayout.CENTER);

        getContentPane().add(panel);
    }
}
