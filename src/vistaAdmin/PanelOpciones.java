package vistaAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import modelo.usuarios.Administrador;
import persistencia.PersistenciaUsuarios;
import Principal.Parque;
import excepciones.UsuarioException;

@SuppressWarnings("serial")
public class PanelOpciones extends JPanel {

    private JRadioButton rbCliente;
    private JRadioButton rbEmpleado;
    private JRadioButton rbAdministrador;
    private JButton btnInicioSesion;
    private JButton btnRegistrarse;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;

    private PersistenciaUsuarios persistenciaUsuarios;
    private Parque parque;

    public PanelOpciones(PersistenciaUsuarios persistenciaUsuarios, Parque parque) {
        this.persistenciaUsuarios = persistenciaUsuarios;
        this.parque = parque;

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelRadios = new JPanel();
        panelRadios.setOpaque(false);
        panelRadios.setBorder(BorderFactory.createTitledBorder("Tipo de usuario"));

        rbCliente = new JRadioButton("Cliente");
        rbEmpleado = new JRadioButton("Empleado");
        rbAdministrador = new JRadioButton("Administrador");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCliente);
        grupo.add(rbEmpleado);
        grupo.add(rbAdministrador);

        panelRadios.add(rbCliente);
        panelRadios.add(rbEmpleado);
        panelRadios.add(rbAdministrador);

        add(panelRadios);

        JPanel panelCampos = new JPanel(new GridLayout(4, 1, 10, 10));
        panelCampos.setOpaque(false);

        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField(20);

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField(20);

        panelCampos.add(lblCorreo);
        panelCampos.add(txtCorreo);
        panelCampos.add(lblContrasena);
        panelCampos.add(txtContrasena);

        add(Box.createVerticalStrut(10));
        add(panelCampos);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        btnInicioSesion = new JButton("Inicio de sesión");
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setVisible(false);

        panelBotones.add(btnInicioSesion);
        panelBotones.add(btnRegistrarse);

        add(Box.createVerticalStrut(10));
        add(panelBotones);

        ActionListener actualizar = e -> {
            btnRegistrarse.setVisible(rbCliente.isSelected());
            revalidate();
            repaint();
        };

        rbCliente.addActionListener(actualizar);
        rbEmpleado.addActionListener(actualizar);
        rbAdministrador.addActionListener(actualizar);

        rbCliente.setSelected(true);
        btnRegistrarse.setVisible(true);
        
        btnRegistrarse.addActionListener(e -> {
            if (rbCliente.isSelected()) {
                new VentanaRegistroCliente(persistenciaUsuarios);
            } else {
                JOptionPane.showMessageDialog(this, "Solo se puede registrar clientes por ahora.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnInicioSesion.addActionListener(e -> {
            String usuario = txtCorreo.getText().trim();
            String password = new String(txtContrasena.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el correo y la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (rbAdministrador.isSelected()) {
                    Administrador admin = (Administrador) persistenciaUsuarios.autenticarUsuario(usuario, password);
                    admin.setParque(parque);

                    VentanaIncioSesion ventana = new VentanaIncioSesion(admin);
                    ventana.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Solo la autenticación de administradores está implementada.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (UsuarioException ex) {
                JOptionPane.showMessageDialog(this, "Error de autenticación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Prueba PanelOpciones");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);

                PersistenciaUsuarios persistencia = new PersistenciaUsuarios();
                Parque parque = new Parque(); 

                PanelOpciones panel = new PanelOpciones(persistencia, parque);
                frame.add(panel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error cargando datos del parque: " + e.getMessage(),
                        "Error de carga", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
