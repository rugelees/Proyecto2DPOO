package VistaAdministrador;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import modelo.usuarios.Administrador;
import modelo.atracciones.*;

public class VentanaGestionAtracciones extends JDialog {
    private Administrador administrador;

    private JTextField txtNombre;
    private JTextField txtCapacidad;
    private JButton btnGuardar;

    public VentanaGestionAtracciones(Frame parent, Administrador administrador) {
        super(parent, "Gestionar Atracciones", true);
        this.administrador = administrador;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panel.add(txtCapacidad);

        btnGuardar = new JButton("Guardar");
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarAtraccion());

        add(panel);
    }

    private void guardarAtraccion() {
        try {
            String nombre = txtNombre.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());

            Atraccion nueva = new AtraccionMecanica(
                    nombre, "Sin restricciones", false, null, null,
                    "DIAMANTE", 3, "Zona Norte", capacidad, 120.0f, 200.0f, 40.0f, 120.0f,
                    "Sin restricciones", "alto");

            administrador.crearAtraccion(nueva);

            JOptionPane.showMessageDialog(this, "Atracción creada correctamente.");
            dispose();  
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Administrador admin = new Administrador("AdminPrueba", 1, "admin@parque.com", "1234", null);

        SwingUtilities.invokeLater(() -> {
            VentanaGestionAtracciones ventana = new VentanaGestionAtracciones(null, admin);
            ventana.setVisible(true);
        });
    }
}
