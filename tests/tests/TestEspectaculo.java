package tests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.atracciones.Espectaculo;

public class TestEspectaculo {
    
    private Espectaculo espectaculo;
    private Date fechaActual;
    private Date fechaFutura;
    private Date fechaPasada;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        fechaFutura = new Date();
        fechaFutura.setTime(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000);
        
        fechaPasada = new Date();
        fechaPasada.setTime(fechaActual.getTime() - 30L * 24 * 60 * 60 * 1000);
        
        espectaculo = new Espectaculo(
            "Show de Delfines",
            "No realizar en caso de lluvia",
            true,  
            fechaActual,
            fechaFutura,
            "45 minutos",
            "15:00",
            100
        );
    }
    
    @Test
    public void testAgregarFuncion() {
        assertTrue(espectaculo.getFunciones().isEmpty());
        
        Date fechaFuncion = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000); 
        espectaculo.agregarFuncion(fechaFuncion);
        
        assertEquals(1, espectaculo.getFunciones().size());
        
        espectaculo.agregarFuncion(fechaFuncion);
        assertEquals(1, espectaculo.getFunciones().size());
        
        Date otraFechaFuncion = new Date(fechaActual.getTime() + 14L * 24 * 60 * 60 * 1000);
        espectaculo.agregarFuncion(otraFechaFuncion);
        assertEquals(2, espectaculo.getFunciones().size());
    }
    
    @Test
    public void testCancelarFuncion() {
        Date fechaFuncion1 = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000);
        Date fechaFuncion2 = new Date(fechaActual.getTime() + 14L * 24 * 60 * 60 * 1000);
        espectaculo.agregarFuncion(fechaFuncion1);
        espectaculo.agregarFuncion(fechaFuncion2);
        
        assertEquals(2, espectaculo.getFunciones().size());
        
        boolean resultado = espectaculo.cancelarFuncion(fechaFuncion1);
        assertTrue(resultado);
        assertEquals(1, espectaculo.getFunciones().size());
        
        Date fechaNoExistente = new Date(fechaActual.getTime() + 21L * 24 * 60 * 60 * 1000);
        resultado = espectaculo.cancelarFuncion(fechaNoExistente);
        assertFalse(resultado);
        assertEquals(1, espectaculo.getFunciones().size());
    }
    
    @Test
    public void testEstaDisponible() {
        assertFalse(espectaculo.estaDisponible(fechaActual));
        
        Date fechaFuncion = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000);
        espectaculo.agregarFuncion(fechaFuncion);
        
        assertTrue(espectaculo.estaDisponible(fechaFuncion));
        
        assertFalse(espectaculo.estaDisponible(fechaActual));
        
        Date fechaFueraDeLaTemporada = new Date(fechaFutura.getTime() + 24 * 60 * 60 * 1000);
        espectaculo.agregarFuncion(fechaFueraDeLaTemporada);
        assertFalse(espectaculo.estaDisponible(fechaFueraDeLaTemporada));
    }
    
    @Test
    public void testConsultarInformacion() {
        String info = espectaculo.consultarInformacion();
        assertTrue(info.contains("Show de Delfines"));
        assertTrue(info.contains("45 minutos"));
        assertTrue(info.contains("15:00"));
        assertTrue(info.contains("100 personas"));
        assertTrue(info.contains("No realizar en caso de lluvia"));
        assertTrue(info.contains("Espectáculo de Temporada"));
    }
    
    @Test
    public void testEspectaculoNoDeTemporada() {
        Espectaculo espectaculoNoTemporada = new Espectaculo(
            "Circo",
            "Ninguna",
            false,  
            null,
            null,
            "2 horas",
            "19:00",
            200
        );
        
        Date fechaFuncion = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000);
        espectaculoNoTemporada.agregarFuncion(fechaFuncion);
        
        assertTrue(espectaculoNoTemporada.estaDisponible(fechaFuncion));
        
        assertFalse(espectaculoNoTemporada.estaDisponible(fechaActual));
    }
}
