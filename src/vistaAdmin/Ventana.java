package vistaAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ventana extends JFrame {
    private JTextArea salida;
    private JButton btnAgregarEmpleado;
    private JButton btnListarAtracciones;
    private JButton btnSalir;

    public Ventana() {
        setTitle("Panel Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        salida = new JTextArea();
        salida.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(salida);

        btnAgregarEmpleado = new JButton("Agregar empleado");
        btnListarAtracciones = new JButton("Listar atracciones");
        btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarEmpleado);
        panelBotones.add(btnListarAtracciones);
        panelBotones.add(btnSalir);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregarEmpleado.addActionListener(e -> agregarEmpleado());
        btnListarAtracciones.addActionListener(e -> listarAtracciones());
        btnSalir.addActionListener(e -> salir());

        setVisible(true);
    }

    private void agregarEmpleado() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre del empleado:");
        if (nombre != null && !nombre.isEmpty()) {
            salida.append("Empleado '" + nombre + "' agregado.\n");
        } else {
            salida.append("No se agregó empleado.\n");
        }
    }

    private void listarAtracciones() {
        salida.append("Atracciones disponibles:\n - Montaña Rusa\n - Museo de Historia\n");
    }

    private void salir() {
        JOptionPane.showMessageDialog(this, "Adiós!");
        System.exit(0);
    }

    public static void main(String[] args) {
    	
    	Ventana ventana = new Ventana();
    }
}
