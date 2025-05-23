package interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import persistencia.PersistenciaUsuarios;
import modelo.usuarios.Usuario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.empleados.Empleado;

public class PanelAdministracion extends JPanel {
    private JTabbedPane tabbedPane;
    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloAtracciones;
    private DefaultTableModel modeloUsuarios;
    private PersistenciaUsuarios persistenciaUsuarios;
    
    public PanelAdministracion() {
        setLayout(new BorderLayout());
        persistenciaUsuarios = new PersistenciaUsuarios();
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Ventas", crearPanelVentas());
        tabbedPane.addTab("Atracciones", crearPanelAtracciones());
        tabbedPane.addTab("Usuarios", crearPanelUsuarios());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        cargarDatosVentas();
        cargarDatosAtracciones();
        cargarDatosUsuarios();
    }
    
    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"ID", "Fecha", "Cliente", "Monto", "Estado"};
        modeloVentas = new DefaultTableModel(columnas, 0);
        JTable tablaVentas = new JTable(modeloVentas);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        
        JPanel panelBotones = new JPanel();
        JButton btnNuevaVenta = new JButton("Nueva Venta");
        JButton btnRegresar = new JButton("Regresar");
        
        btnNuevaVenta.addActionListener(e -> {
            PanelCompraBoletos panelCompra = new PanelCompraBoletos();
            panelCompra.setPanelAnterior("ADMIN");
            VentanaPrincipal.mostrarPanel("COMPRA");
        });
        
        btnRegresar.addActionListener(e -> VentanaPrincipal.mostrarPanel("LOGIN"));
        
        panelBotones.add(btnNuevaVenta);
        panelBotones.add(btnRegresar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelAtracciones() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Nombre", "Capacidad", "Estado", "Tiempo de Espera"};
        modeloAtracciones = new DefaultTableModel(columnas, 0);
        JTable tablaAtracciones = new JTable(modeloAtracciones);
        JScrollPane scrollPane = new JScrollPane(tablaAtracciones);
        
        JPanel panelBotones = new JPanel();
        JButton btnNuevaAtraccion = new JButton("Nueva Atracción");
        JButton btnRegresar = new JButton("Regresar");
        
        btnNuevaAtraccion.addActionListener(e -> {
            PanelAtracciones panelAtracciones = new PanelAtracciones();
            panelAtracciones.setPanelAnterior("ADMIN");
            VentanaPrincipal.mostrarPanel("ATRACCIONES");
        });
        
        btnRegresar.addActionListener(e -> VentanaPrincipal.mostrarPanel("LOGIN"));
        
        panelBotones.add(btnNuevaAtraccion);
        panelBotones.add(btnRegresar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"ID", "Nombre", "Email", "Tipo"};
        modeloUsuarios = new DefaultTableModel(columnas, 0);
        JTable tablaUsuarios = new JTable(modeloUsuarios);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        
        JPanel panelBotones = new JPanel();
        JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
        JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
        JButton btnRegresar = new JButton("Regresar");
        
        btnNuevoUsuario.addActionListener(e -> {
        });
        
        btnEliminarUsuario.addActionListener(e -> {
            int filaSeleccionada = tablaUsuarios.getSelectedRow();
            if (filaSeleccionada != -1) {
                modeloUsuarios.removeRow(filaSeleccionada);
            }
        });
        
        btnRegresar.addActionListener(e -> VentanaPrincipal.mostrarPanel("LOGIN"));
        
        panelBotones.add(btnNuevoUsuario);
        panelBotones.add(btnEliminarUsuario);
        panelBotones.add(btnRegresar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void cargarDatosVentas() {
        Object[][] datos = {
            {"V001", "2024-03-20", "cliente1@email.com", "$50.00", "Completada"},
            {"V002", "2024-03-20", "cliente2@email.com", "$75.00", "Pendiente"}
        };
        
        for (Object[] fila : datos) {
            modeloVentas.addRow(fila);
        }
    }
    
    private void cargarDatosAtracciones() {
        Object[][] datos = {
            {"Montaña Rusa", "30 personas", "Operativa", "15 min"},
            {"Carrusel", "20 personas", "Operativa", "5 min"},
            {"Rueda de la Fortuna", "40 personas", "Mantenimiento", "N/A"}
        };
        
        for (Object[] fila : datos) {
            modeloAtracciones.addRow(fila);
        }
    }
    
    private void cargarDatosUsuarios() {
        try {
            for (Usuario usuario : persistenciaUsuarios.cargarTodosUsuarios()) {
                String tipo = usuario instanceof Administrador ? "Administrador" :
                             usuario instanceof Cliente ? "Cliente" :
                             usuario instanceof Empleado ? "Empleado" : "Desconocido";
                
                String id = "";
                String nombre = "";
                
                if (usuario instanceof Administrador) {
                    id = String.valueOf(((Administrador) usuario).getId());
                    nombre = ((Administrador) usuario).getNombre();
                } else if (usuario instanceof Cliente) {
                    id = String.valueOf(((Cliente) usuario).getId());
                    nombre = ((Cliente) usuario).getNombre();
                } else if (usuario instanceof Empleado) {
                    id = String.valueOf(((Empleado) usuario).getId());
                    nombre = ((Empleado) usuario).getNombre();
                }
                
                Object[] fila = {
                    id,
                    nombre,
                    usuario.getEmail(),
                    tipo
                };
                modeloUsuarios.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar usuarios: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 