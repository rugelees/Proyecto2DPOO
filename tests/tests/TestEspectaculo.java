package tests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.atracciones.Espectaculo;
import modelo.usuarios.Cliente;

public class TestEspectaculo {
    
    private Espectaculo espectaculo;
    private Cliente clienteValido;
    private Cliente clienteInvalido;
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
        
        clienteValido = new Cliente("Juan Pérez", 1, "juan@ejemplo.com", "clave123", 170.0f, 70.0f, 25);
        clienteInvalido = new Cliente("Niño Pequeño", 2, "niño@ejemplo.com", "clave123", 110.0f, 30.0f, 8);
    }
    
    @Test
    public void testPropiedadesEspectaculo() {
        assertEquals("Show de Delfines", espectaculo.getNombre());
        assertEquals("No realizar en caso de lluvia", espectaculo.getRestriccionClima());
        
        espectaculo.agregarFuncion(fechaActual);
        assertTrue(espectaculo.estaDisponible(fechaActual));
        
        assertEquals(fechaActual, espectaculo.getFechaInicio());
        assertEquals(fechaFutura, espectaculo.getFechaFin());
        assertEquals("45 minutos", espectaculo.getDuracion());
        assertEquals("15:00", espectaculo.getHorario());
        assertEquals(100, espectaculo.getCapacidad());
    }
    
    @Test
    public void testEstaDisponible() {
        espectaculo.agregarFuncion(fechaActual);
        assertTrue(espectaculo.estaDisponible(fechaActual));
        
        Date fechaFueraDeRango = new Date(fechaFutura.getTime() + 24L * 60 * 60 * 1000);
        assertFalse(espectaculo.estaDisponible(fechaFueraDeRango));
    }
    
    @Test
    public void testModificarHorario() {
        espectaculo.setHorario("16:00");
        assertEquals("16:00", espectaculo.getHorario());
    }
    
    @Test
    public void testModificarDuracion() {
        espectaculo.setDuracion("60 minutos");
        assertEquals("60 minutos", espectaculo.getDuracion());
    }
    
    @Test
    public void testModificarCapacidad() {
        espectaculo.setCapacidad(150);
        assertEquals(150, espectaculo.getCapacidad());
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
