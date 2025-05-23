package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private CardLayout cardLayout;
    private static VentanaPrincipal instancia;
    
    public VentanaPrincipal() {
        instancia = this;
        setTitle("Sistema de Parque de Atracciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        
        panelPrincipal.add(new PanelLogin(), "LOGIN");
        panelPrincipal.add(new PanelCliente(), "CLIENTE");
        panelPrincipal.add(new PanelEmpleado(), "EMPLEADO");
        panelPrincipal.add(new PanelAdministracion(), "ADMIN");
        panelPrincipal.add(new PanelCompraBoletos(), "COMPRA");
        panelPrincipal.add(new PanelAtracciones(), "ATRACCIONES");
        
        add(panelPrincipal);
        
        cardLayout.show(panelPrincipal, "LOGIN");
    }
    
    public static void mostrarPanel(String nombrePanel) {
        if (instancia != null) {
            instancia.cardLayout.show(instancia.panelPrincipal, nombrePanel);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
} 