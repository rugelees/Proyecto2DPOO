package vistaAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import modelo.usuarios.Cliente;
import persistencia.PersistenciaUsuarios;
import excepciones.UsuarioException;

public class VentanaRegistroCliente extends JFrame {

    private JTextField txtNombre;
    private JTextField txtId;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private PersistenciaUsuarios persistencia;

    public VentanaRegistroCliente(PersistenciaUsuarios persistencia) {
        this.persistencia = persistencia;

        setTitle("Registro de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnRegistrar = new JButton("Registrar");
        add(new JLabel()); 
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarCliente());

        setVisible(true);
    }

    private void registrarCliente() {
        String nombre = txtNombre.getText().trim();
        String idStr = txtId.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (nombre.isEmpty() || idStr.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Cliente cliente = new Cliente(nombre, id, email, password);
            List<Cliente> clientes = persistencia.cargarClientes();
            clientes.add(cliente);
            persistencia.guardarClientes(clientes);
            JOptionPane.showMessageDialog(this, "Cliente registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
