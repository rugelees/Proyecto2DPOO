package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelEmpleado extends JPanel {
    private JButton btnVerAtracciones;
    private JButton btnCerrarSesion;
    
    public PanelEmpleado() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        btnVerAtracciones = new JButton("Ver Atracciones");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        
        btnVerAtracciones.addActionListener(e -> {
            PanelAtracciones panelAtracciones = new PanelAtracciones();
            panelAtracciones.setPanelAnterior("EMPLEADO");
            VentanaPrincipal.mostrarPanel("ATRACCIONES");
        });
        
        btnCerrarSesion.addActionListener(e -> VentanaPrincipal.mostrarPanel("LOGIN"));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(btnVerAtracciones, gbc);
        
        gbc.gridy = 1;
        add(btnCerrarSesion, gbc);
    }
} 