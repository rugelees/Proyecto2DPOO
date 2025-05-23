package VistaAdministrador;

import javax.swing.*;

import modelo.usuarios.Administrador;

import java.awt.*;
import java.awt.event.*;

public class VentanaAdministrador extends JFrame {
    private Administrador administrador;

    public VentanaAdministrador(Administrador administrador) {
        this.administrador = administrador;
        setTitle("Panel de Administrador - Parque");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnGestionarAtracciones = new JButton("Gestionar Atracciones");
        JButton btnGestionarEmpleados = new JButton("Gestionar Empleados");
        JButton btnGestionarTurnos = new JButton("Gestionar Turnos");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnGestionarAtracciones);
        panel.add(btnGestionarEmpleados);
        panel.add(btnGestionarTurnos);
        panel.add(btnReportes);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        btnGestionarAtracciones.addActionListener(e -> abrirGestionAtracciones());
        btnGestionarEmpleados.addActionListener(e -> abrirGestionEmpleados());
        btnGestionarTurnos.addActionListener(e -> abrirGestionTurnos());
        btnReportes.addActionListener(e -> abrirReportes());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void abrirGestionAtracciones() {
        JOptionPane.showMessageDialog(this, "Aquí va la gestión de atracciones");
    }

    private void abrirGestionEmpleados() {
        JOptionPane.showMessageDialog(this, "Aquí va la gestión de empleados");
    }

    private void abrirGestionTurnos() {
        JOptionPane.showMessageDialog(this, "Aquí va la gestión de turnos");
    }

    private void abrirReportes() {
        JOptionPane.showMessageDialog(this, "Aquí van los reportes");
    }

    public static void main(String[] args) {
        Administrador admin = new Administrador("Admin", 1, "admin@parque.com", "1234", null);
        SwingUtilities.invokeLater(() -> {
            new VentanaAdministrador(admin).setVisible(true);
        });
    }
}
