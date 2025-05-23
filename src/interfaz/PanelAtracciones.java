package interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class PanelAtracciones extends JPanel {
    private JTable tblAtracciones;
    private DefaultTableModel modelo;
    private JButton btnRegresar;
    private String panelAnterior;
    
    public PanelAtracciones() {
        setLayout(new BorderLayout());
        
        String[] columnas = {"Nombre", "Capacidad", "Estado", "Tiempo de Espera"};
        modelo = new DefaultTableModel(columnas, 0);
        tblAtracciones = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblAtracciones);
        
        JPanel panelInfo = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel lblTitulo = new JLabel("Atracciones Disponibles");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelInfo.add(lblTitulo, gbc);
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            if (panelAnterior != null) {
                VentanaPrincipal.mostrarPanel(panelAnterior);
            }
        });
        
        add(panelInfo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);
        
        cargarDatosEjemplo();
    }
    
    public void setPanelAnterior(String panel) {
        this.panelAnterior = panel;
    }
    
    private void cargarDatosEjemplo() {
        Object[][] datos = {
            {"Montaña Rusa", "30 personas", "Operativa", "15 min"},
            {"Carrusel", "20 personas", "Operativa", "5 min"},
            {"Rueda de la Fortuna", "40 personas", "Mantenimiento", "N/A"},
            {"Tobogán", "10 personas", "Operativa", "10 min"}
        };
        
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }
} 