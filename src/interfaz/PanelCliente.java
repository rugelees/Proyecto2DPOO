package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCliente extends JPanel {
    private JButton btnComprarBoletos;
    private JButton btnVerAtracciones;
    private JButton btnCerrarSesion;
    
    public PanelCliente() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("Panel de Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);
        
        btnComprarBoletos = new JButton("Comprar Boletos");
        btnVerAtracciones = new JButton("Ver Atracciones");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(btnComprarBoletos, gbc);
        
        gbc.gridx = 1;
        add(btnVerAtracciones, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnCerrarSesion, gbc);
        
        btnComprarBoletos.addActionListener(e -> {
            PanelCompraBoletos panelCompra = new PanelCompraBoletos();
            panelCompra.setPanelAnterior("CLIENTE");
            VentanaPrincipal.mostrarPanel("COMPRA");
        });
        
        btnVerAtracciones.addActionListener(e -> {
            PanelAtracciones panelAtracciones = new PanelAtracciones();
            panelAtracciones.setPanelAnterior("CLIENTE");
            VentanaPrincipal.mostrarPanel("ATRACCIONES");
        });
        
        btnCerrarSesion.addActionListener(e -> {
            VentanaPrincipal.mostrarPanel("LOGIN");
        });
    }
} 