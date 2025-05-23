package interfaz;

import javax.swing.*;
import java.awt.*;
import modelo.usuarios.Usuario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.empleados.Empleado;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaEmpleados;
import excepciones.UsuarioException;
import excepciones.EmpleadoException;
import java.util.List;

public class PanelLogin extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaEmpleados persistenciaEmpleados;
    
    public PanelLogin() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaEmpleados = new PersistenciaEmpleados();
        
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField(20);
        
        btnLogin = new JButton("Ingresar");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblEmail, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtEmail, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtPassword, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnLogin, gbc);
        
        btnLogin.addActionListener(e -> realizarLogin());
        
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        });
    }
    
    private void realizarLogin() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        
        try {
            Usuario usuario = persistenciaUsuarios.autenticarUsuario(email, password);
            
            if (usuario != null) {
                txtEmail.setText("");
                txtPassword.setText("");
                
                if (usuario instanceof Administrador) {
                    VentanaPrincipal.mostrarPanel("ADMIN");
                } else if (usuario instanceof Cliente) {
                    VentanaPrincipal.mostrarPanel("CLIENTE");
                }
                return;
            }
            
            List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();
            for (Empleado empleado : empleados) {
                if (empleado.getEmail().equals(email) && empleado.getPassword().equals(password)) {
                    txtEmail.setText("");
                    txtPassword.setText("");
                    
                    VentanaPrincipal.mostrarPanel("EMPLEADO");
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
                
        } catch (UsuarioException | EmpleadoException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al autenticar usuario: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 