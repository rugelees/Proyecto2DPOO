package interfaz;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {
    private JButton btnCompraBoletos;
    private JButton btnAtracciones;
    private JButton btnAdministracion;
    
    public PanelInicio() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("Bienvenido al Parque de Atracciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);
        
        btnCompraBoletos = new JButton("Comprar Boletos");
        btnAtracciones = new JButton("Ver Atracciones");
        btnAdministracion = new JButton("Administración");
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(btnCompraBoletos, gbc);
        
        gbc.gridx = 1;
        add(btnAtracciones, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnAdministracion, gbc);
        
        btnCompraBoletos.addActionListener(e -> VentanaPrincipal.mostrarPanel("COMPRA"));
        btnAtracciones.addActionListener(e -> VentanaPrincipal.mostrarPanel("ATRACCIONES"));
        btnAdministracion.addActionListener(e -> VentanaPrincipal.mostrarPanel("ADMIN"));
    }
} 