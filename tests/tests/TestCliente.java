package tests;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import excepciones.TiqueteException;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Cliente;

public class TestCliente {
    
    private Cliente cliente;
    private TiqueteBasico tiquete;
    private Date fechaActual;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        cliente = new Cliente("Juan Pérez", 1, "juan@ejemplo.com", "clave123", 170.0f, 70.0f, 25);
        
        tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
    }
    
    @Test
    public void testPropiedadesCliente() {
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals(1, cliente.getId());
        assertEquals("juan@ejemplo.com", cliente.getEmail());
        assertEquals("clave123", cliente.getPassword());
        
        assertEquals(170.0f, cliente.getAltura(), 0.01);
        assertEquals(70.0f, cliente.getPeso(), 0.01);
        assertEquals(25, cliente.getEdad());
        
        assertTrue(cliente.getCondicionesSalud().isEmpty());
        
        cliente.agregarCondicionSalud("vértigo");
        assertEquals(1, cliente.getCondicionesSalud().size());
        assertTrue(cliente.tieneCondicionSalud("vértigo"));
        assertFalse(cliente.tieneCondicionSalud("problemas cardíacos"));
    }
    
    @Test
    public void testComprarTiquete() {
        assertTrue(cliente.getTiquetes().isEmpty());
        
        try {
            assertTrue(cliente.comprarTiquete(tiquete));
            
            List<TiqueteBasico> tiquetes = (List) cliente.getTiquetes();
            assertEquals(1, tiquetes.size());
            assertEquals(tiquete, tiquetes.get(0));
            
            TiqueteBasico otroTiquete = new TiqueteBasico(2, "Tiquete Oro", 1, "Oro", fechaActual, "Activo", "Online", "Adulto", false);
            assertTrue(cliente.comprarTiquete(otroTiquete));
            
            assertEquals(2, cliente.getTiquetes().size());
            assertTrue(cliente.getTiquetes().contains(tiquete));
            assertTrue(cliente.getTiquetes().contains(otroTiquete));
            
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test(expected = TiqueteException.class)
    public void testComprarTiqueteNulo() throws TiqueteException {
        cliente.comprarTiquete(null);
    }
    
    @Test
    public void testVerificarCredenciales() {
        assertTrue(cliente.verificarCredenciales("juan@ejemplo.com", "clave123"));
        
        assertFalse(cliente.verificarCredenciales("otro@ejemplo.com", "clave123"));
        
        assertFalse(cliente.verificarCredenciales("juan@ejemplo.com", "otraClave"));
        
        cliente.setEmail("nuevo@ejemplo.com");
        assertTrue(cliente.verificarCredenciales("nuevo@ejemplo.com", "clave123"));
        assertFalse(cliente.verificarCredenciales("juan@ejemplo.com", "clave123"));
        
        cliente.setPassword("nuevaClave");
        assertTrue(cliente.verificarCredenciales("nuevo@ejemplo.com", "nuevaClave"));
        assertFalse(cliente.verificarCredenciales("nuevo@ejemplo.com", "clave123"));
    }
}