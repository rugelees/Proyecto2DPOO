package interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class PanelCompraBoletos extends JPanel {
    private JComboBox<String> cmbTipoBoleto;
    private JSpinner spnCantidad;
    private JTable tblBoletos;
    private DefaultTableModel modelo;
    private JButton btnComprar;
    private JButton btnImprimir;
    private JButton btnRegresar;
    private String panelAnterior;
    
    public PanelCompraBoletos() {
        setLayout(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel lblTipoBoleto = new JLabel("Tipo de Boleto:");
        cmbTipoBoleto = new JComboBox<>(new String[]{"Adulto", "Niño", "Adulto Mayor"});
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        spnCantidad = new JSpinner(spinnerModel);
        
        btnComprar = new JButton("Comprar");
        btnImprimir = new JButton("Imprimir Boleto");
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(lblTipoBoleto, gbc);
        gbc.gridx = 1;
        panelFormulario.add(cmbTipoBoleto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(lblCantidad, gbc);
        gbc.gridx = 1;
        panelFormulario.add(spnCantidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelFormulario.add(btnComprar, gbc);
        
        gbc.gridy = 3;
        panelFormulario.add(btnImprimir, gbc);
        
        String[] columnas = {"ID", "Tipo", "Fecha", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tblBoletos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblBoletos);
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            if (panelAnterior != null) {
                VentanaPrincipal.mostrarPanel(panelAnterior);
            }
        });
        
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);
        
        btnComprar.addActionListener(e -> comprarBoleto());
        btnImprimir.addActionListener(e -> imprimirBoleto());
    }
    
    public void setPanelAnterior(String panel) {
        this.panelAnterior = panel;
    }
    
    private void comprarBoleto() {
        String tipo = (String) cmbTipoBoleto.getSelectedItem();
        int cantidad = (Integer) spnCantidad.getValue();
        
        for (int i = 0; i < cantidad; i++) {
            Object[] fila = {
                "B" + System.currentTimeMillis(),
                tipo,
                new java.util.Date(),
                "No impreso"
            };
            modelo.addRow(fila);
        }
    }
    
    private void imprimirBoleto() {
        int filaSeleccionada = tblBoletos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un boleto para imprimir",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        modelo.setValueAt("Impreso", filaSeleccionada, 3);
    }
} 