package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import modelo.lugares.Cafeteria;
import modelo.empleados.Cocinero;
import modelo.empleados.Cajero;

public class TestCafeteria {
    
    private Cafeteria cafeteria;
    private Cocinero cocinero;
    private Cajero cajero;
    private List<String> menu;
    private Date fechaActual;
    private String turno;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        turno = "Mañana";
        
        menu = new ArrayList<>();
        menu.add("Hamburguesas");
        menu.add("Papas fritas");
        menu.add("Refrescos");
        
        cafeteria = new Cafeteria("CAF1", "Cafetería Central", "Centro del parque", menu, 50);
        
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
    public void testPropiedadesCafeteria() {
        assertEquals("CAF1", cafeteria.getId());
        assertEquals("Cafetería Central", cafeteria.getNombre());
        assertEquals("Centro del parque", cafeteria.getUbicacion());
        assertEquals(menu, cafeteria.getMenu());
        assertEquals(50, cafeteria.getCapacidad());
    }
    
    @Test
    public void testAsignarEmpleados() {
        assertTrue(cafeteria.asignarEmpleado(cocinero, fechaActual, turno));
        assertTrue(cafeteria.tieneCocineroAsignado(fechaActual, turno));
        
        assertTrue(cafeteria.asignarEmpleado(cajero, fechaActual, turno));
        assertTrue(cafeteria.tieneCajeroAsignado(fechaActual, turno));
    }
    
    @Test
    public void testGestionarMenu() {
        assertTrue(cafeteria.agregarPlato("Ensalada"));
        assertTrue(cafeteria.getMenu().contains("Ensalada"));
        
        assertTrue(cafeteria.removerPlato("Papas fritas"));
        assertFalse(cafeteria.getMenu().contains("Papas fritas"));
    }
    
    @Test
    public void testVerificarPersonalMinimo() {
        assertFalse(cafeteria.verificarPersonalMinimo(fechaActual, turno));
        
        cafeteria.asignarEmpleado(cocinero, fechaActual, turno);
        assertFalse(cafeteria.verificarPersonalMinimo(fechaActual, turno));
        
        cafeteria.asignarEmpleado(cajero, fechaActual, turno);
        assertTrue(cafeteria.verificarPersonalMinimo(fechaActual, turno));
    }
    
    @Test
    public void testModificarCapacidad() {
        cafeteria.setCapacidad(75);
        assertEquals(75, cafeteria.getCapacidad());
    }
    
    @Test
    public void testModificarMenu() {
        List<String> nuevoMenu = new ArrayList<>();
        nuevoMenu.add("Pizza");
        nuevoMenu.add("Pasta");
        
        cafeteria.setMenu(nuevoMenu);
        assertEquals(nuevoMenu, cafeteria.getMenu());
    }
} 