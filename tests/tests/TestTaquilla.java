package tests;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import modelo.lugares.Taquilla;
import modelo.empleados.Cajero;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Cliente;
import excepciones.TiqueteException;

public class TestTaquilla {
    
    private Taquilla taquilla;
    private Cajero cajero;
    private Cliente cliente;
    private TiqueteBasico tiquete;
    private Date fechaActual;
    private String turno;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        turno = "Mañana";
        
        taquilla = new Taquilla("TAQ1", "Taquilla Principal", "Entrada del parque", "Efectivo y tarjeta");
        
        cajero = new Cajero(
            "Cajero",
            "Pedro Gómez",
            3,
            false,
            "pedro@ejemplo.com",
            "clave123",
            false
        );
        
        cliente = new Cliente("Juan Pérez", 1, "juan@ejemplo.com", "clave123", 170.0f, 70.0f, 25);
        
        tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
    }
    
    @Test
    public void testPropiedadesTaquilla() {
        assertEquals("TAQ1", taquilla.getId());
        assertEquals("Taquilla Principal", taquilla.getNombre());
        assertEquals("Entrada del parque", taquilla.getUbicacion());
        assertEquals("Efectivo y tarjeta", taquilla.getMetodoPago());
    }
    
    @Test
    public void testAsignarEmpleado() {
        assertTrue(taquilla.asignarEmpleado(cajero, fechaActual, turno));
        assertTrue(taquilla.tieneCajeroAsignado(fechaActual, turno));
    }
    
    @Test
    public void testVerificarPersonalMinimo() {
        assertFalse(taquilla.verificarPersonalMinimo(fechaActual, turno));
        
        taquilla.asignarEmpleado(cajero, fechaActual, turno);
        assertTrue(taquilla.verificarPersonalMinimo(fechaActual, turno));
    }
    
    @Test
    public void testGestionarTiquetes() {
        assertTrue(taquilla.agregarTiquete(tiquete));
        List<TiqueteBasico> tiquetes = (List) taquilla.getTiquetesDisponibles();
        assertEquals(1, tiquetes.size());
        assertEquals(tiquete, tiquetes.get(0));
        
        try {
            TiqueteBasico tiqueteVendido = (TiqueteBasico) taquilla.venderTiquete("Tiquete Básico", cliente);
            assertEquals(tiquete, tiqueteVendido);
            assertEquals(1, cliente.getTiquetes().size());
            assertEquals(tiquete, cliente.getTiquetes().get(0));
            assertEquals(0, taquilla.getTiquetesDisponibles().size());
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test(expected = TiqueteException.class)
    public void testVenderTiqueteInexistente() throws TiqueteException {
        taquilla.venderTiquete("Tiquete Inexistente", cliente);
    }
    
    @Test
    public void testModificarMetodoPago() {
        taquilla.setMetodoPago("Solo efectivo");
        assertEquals("Solo efectivo", taquilla.getMetodoPago());
    }
} 