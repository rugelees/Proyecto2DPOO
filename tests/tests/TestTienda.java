package tests;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import modelo.lugares.Tienda;
import modelo.empleados.Cajero;

public class TestTienda {
    
    private Tienda tienda;
    private Cajero cajero;
    private Map<String, Integer> inventario;
    private Date fechaActual;
    private String turno;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        turno = "Mañana";
        
        inventario = new HashMap<>();
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
    }
    
    @Test
    public void testPropiedadesTienda() {
        assertEquals("TIE1", tienda.getId());
        assertEquals("Tienda de Recuerdos", tienda.getNombre());
        assertEquals("Salida del parque", tienda.getUbicacion());
        assertEquals("Souvenirs", tienda.getTipoProductos());
        assertEquals(inventario, tienda.getInventario());
    }
    
    @Test
    public void testAsignarEmpleado() {
        assertTrue(tienda.asignarEmpleado(cajero, fechaActual, turno));
        assertTrue(tienda.tieneCajeroAsignado(fechaActual, turno));
    }
    
    @Test
    public void testGestionarInventario() {
        assertTrue(tienda.verificarDisponibilidadProducto("Camisetas"));
        assertTrue(tienda.verificarDisponibilidadProducto("Llaveros"));
        assertTrue(tienda.verificarDisponibilidadProducto("Muñecos"));
        
        assertTrue(tienda.agregarProducto("Gorras", 20));
        assertTrue(tienda.verificarDisponibilidadProducto("Gorras"));
        
        assertTrue(tienda.removerProducto("Camisetas", 10));
        assertTrue(tienda.verificarDisponibilidadProducto("Camisetas"));
        
        assertTrue(tienda.removerProducto("Llaveros", 100));
        assertFalse(tienda.verificarDisponibilidadProducto("Llaveros"));
    }
    
    @Test
    public void testVerificarPersonalMinimo() {
        assertFalse(tienda.verificarPersonalMinimo(fechaActual, turno));
        
        tienda.asignarEmpleado(cajero, fechaActual, turno);
        assertTrue(tienda.verificarPersonalMinimo(fechaActual, turno));
    }
    
    @Test
    public void testModificarTipoProductos() {
        tienda.setTipoProductos("Ropa");
        assertEquals("Ropa", tienda.getTipoProductos());
    }
    
    @Test
    public void testModificarInventario() {
        Map<String, Integer> nuevoInventario = new HashMap<>();
        nuevoInventario.put("Gorras", 30);
        nuevoInventario.put("Tazas", 20);
        
        tienda.setInventario(nuevoInventario);
        assertEquals(nuevoInventario, tienda.getInventario());
    }
} 