package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Empleado;
import modelo.lugares.Cafeteria;
import modelo.lugares.LugarServicio;
import modelo.lugares.LugarTrabajo;
import modelo.lugares.Taquilla;
import modelo.lugares.Tienda;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Cliente;
import excepciones.TiqueteException;

public class TestLugarTrabajo {
    
    private LugarTrabajo lugarTrabajo;
    private Cafeteria cafeteria;
    private Taquilla taquilla;
    private Tienda tienda;
    
    private Cajero cajero;
    private Cocinero cocinero;
    
    private Date fechaActual;
    private String turnoApertura;
    private String turnoCierre;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        turnoApertura = "Apertura";
        turnoCierre = "Cierre";
        
        lugarTrabajo = new LugarTrabajo("LUG1", "Lugar de Trabajo Genérico", "Centro del parque");
        
        List<String> menu = new ArrayList<>();
        menu.add("Hamburguesas");
        menu.add("Papas fritas");
        menu.add("Refrescos");
        cafeteria = new Cafeteria("CAF1", "Cafetería Central", "Centro del parque", menu, 50);
        
        taquilla = new Taquilla("TAQ1", "Taquilla Principal", "Entrada del parque", "Efectivo y tarjeta");
        
        Map<String, Integer> inventario = new HashMap<>();
        inventario.put("Camisetas", 50);
        inventario.put("Llaveros", 100);
        inventario.put("Muñecos", 30);
        tienda = new Tienda("TIE1", "Tienda de Recuerdos", "Salida del parque", "Souvenirs", inventario);
        
        cajero = new Cajero(
            "Cajero",
            "Pedro Gómez",
            3,
            false,
            "pedro@ejemplo.com",
            "clave123",
            false
        );
        
        cocinero = new Cocinero(
            true,  
            "Cocinero",
            "Ana Martínez",
            4,
            false,
            "ana@ejemplo.com",
            "clave123",
            true  
        );
    }
    
    @Test
    public void testLugarTrabajoBasico() {
        assertEquals("LUG1", lugarTrabajo.getId());
        assertEquals("Lugar de Trabajo Genérico", lugarTrabajo.getNombre());
        assertEquals("Centro del parque", lugarTrabajo.getUbicacion());
        
        List<Empleado> empleados = lugarTrabajo.getEmpleadosAsignados(fechaActual, turnoApertura);
        assertTrue(empleados.isEmpty());
        
        assertTrue(lugarTrabajo.asignarEmpleado(cajero, fechaActual, turnoApertura));
        
        empleados = lugarTrabajo.getEmpleadosAsignados(fechaActual, turnoApertura);
        assertEquals(1, empleados.size());
        assertEquals(cajero, empleados.get(0));
        
        empleados = lugarTrabajo.getEmpleadosAsignados(fechaActual, turnoCierre);
        assertTrue(empleados.isEmpty());
        
        assertFalse(lugarTrabajo.requiereCapacitacion(cajero));
    }
    
    @Test
    public void testCafeteria() {
        assertEquals(50, cafeteria.getCapacidad());
        assertEquals(3, cafeteria.getMenu().size());
        assertTrue(cafeteria.getMenu().contains("Hamburguesas"));
        
        assertFalse(cafeteria.verificarPersonalMinimo(fechaActual, turnoApertura));
        
        assertTrue(cafeteria.asignarCajero(cajero, fechaActual, turnoApertura));
        assertTrue(cafeteria.asignarCocinero(cocinero, fechaActual, turnoApertura));
        
        assertTrue(cafeteria.verificarPersonalMinimo(fechaActual, turnoApertura));
        
        assertTrue(cafeteria.agregarPlato("Ensalada"));
        assertEquals(4, cafeteria.getMenu().size());
        
        assertTrue(cafeteria.removerPlato("Hamburguesas"));
        assertEquals(3, cafeteria.getMenu().size());
        assertFalse(cafeteria.getMenu().contains("Hamburguesas"));
    }
    
    @Test
    public void testTaquilla() {
        assertEquals("Efectivo y tarjeta", taquilla.getMetodoPago());
        assertTrue(taquilla.getTiquetesDisponibles().isEmpty());
        
        assertFalse(taquilla.verificarPersonalMinimo(fechaActual, turnoApertura));
        
        assertTrue(taquilla.asignarCajero(cajero, fechaActual, turnoApertura));
        assertTrue(taquilla.verificarPersonalMinimo(fechaActual, turnoApertura));
        
        TiqueteBasico tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
        assertTrue(taquilla.agregarTiquete(tiquete));
        assertEquals(1, taquilla.getTiquetesDisponibles().size());
        
        Cliente cliente = new Cliente("Cliente Prueba", 100, "cliente@ejemplo.com", "clave123");
        
        try {
            TiqueteBasico tiqueteVendido = (TiqueteBasico) taquilla.venderTiquete("Tiquete Básico", cliente);
            assertNotNull(tiqueteVendido);
            assertEquals(tiquete, tiqueteVendido);
            
            assertTrue(taquilla.getTiquetesDisponibles().isEmpty());
            
            assertEquals(1, cliente.getTiquetes().size());
            assertEquals(tiquete, cliente.getTiquetes().get(0));
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testTienda() {
        assertEquals("Souvenirs", tienda.getTipoProductos());
        assertEquals(3, tienda.getInventario().size());
        
        assertTrue(tienda.verificarDisponibilidadProducto("Camisetas"));
        assertFalse(tienda.verificarDisponibilidadProducto("Sombreros"));
        
        assertTrue(tienda.agregarProducto("Sombreros", 20));
        assertTrue(tienda.verificarDisponibilidadProducto("Sombreros"));
        assertEquals(4, tienda.getInventario().size());
        assertEquals(Integer.valueOf(20), tienda.getInventario().get("Sombreros"));
        
        assertTrue(tienda.agregarProducto("Camisetas", 30));
        assertEquals(Integer.valueOf(80), tienda.getInventario().get("Camisetas")); 
        
        assertTrue(tienda.removerProducto("Camisetas", 20));
        assertEquals(Integer.valueOf(60), tienda.getInventario().get("Camisetas"));
        
        assertTrue(tienda.removerProducto("Sombreros", 20));
        assertFalse(tienda.getInventario().containsKey("Sombreros"));
        
        assertFalse(tienda.removerProducto("Camisetas", 100));
        assertEquals(Integer.valueOf(60), tienda.getInventario().get("Camisetas"));
    }
    
    @Test
    public void testLugarServicio() {
        assertEquals("Cafeteria", cafeteria.getTipoServicio());
        assertTrue(cafeteria.isRequiereCocinero());
        
        assertEquals("Tienda", tienda.getTipoServicio());
        assertFalse(tienda.isRequiereCocinero());
        
        assertFalse(cafeteria.tieneCajeroAsignado(fechaActual, turnoApertura));
        assertFalse(cafeteria.tieneCocineroAsignado(fechaActual, turnoApertura));
        
        assertTrue(cafeteria.asignarCajero(cajero, fechaActual, turnoApertura));
        assertTrue(cafeteria.tieneCajeroAsignado(fechaActual, turnoApertura));
        
        assertTrue(cafeteria.asignarCocinero(cocinero, fechaActual, turnoApertura));
        assertTrue(cafeteria.tieneCocineroAsignado(fechaActual, turnoApertura));
        
        assertTrue(tienda.tieneCocineroAsignado(fechaActual, turnoApertura));
        
        assertFalse(tienda.asignarCocinero(cocinero, fechaActual, turnoApertura));
    }
}
