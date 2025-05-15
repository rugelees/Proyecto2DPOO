package tests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.AtraccionCultural;
import modelo.usuarios.Cliente;

public class TestAtraccion {
    
    private AtraccionMecanica atraccionMecanica;
    private AtraccionCultural atraccionCultural;
    private Cliente clienteValido;
    private Cliente clienteInvalido;
    private Date fechaActual;
    private Date fechaFutura;
    private Date fechaPasada;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        fechaFutura = new Date();
        fechaFutura.setTime(fechaActual.getTime() + 365L * 24 * 60 * 60 * 1000);
        
        fechaPasada = new Date();
        fechaPasada.setTime(fechaActual.getTime() - 365L * 24 * 60 * 60 * 1000);
        
        atraccionMecanica = new AtraccionMecanica(
            "Montaña Rusa",
            "No operar en caso de tormenta eléctrica",
            false,
            null,
            null,
            "Oro",
            2,
            "Zona Norte",
            50,
            120.0f,  
            200.0f,  
            40.0f,   
            120.0f,  
            "vértigo, problemas cardíacos",
            "alto"
        );
        
        atraccionCultural = new AtraccionCultural(
            "Casa del Terror",
            "Ninguna",
            false,
            null,
            null,
            "Familiar",
            1,
            "Zona Sur",
            30,
            12    
        );
        
        
        clienteValido = new Cliente("Juan Pérez", 1, "juan@ejemplo.com", "clave123", 170.0f, 70.0f, 25);
        
        clienteInvalido = new Cliente("Niño Pequeño", 2, "niño@ejemplo.com", "clave123", 110.0f, 30.0f, 8);
        clienteInvalido.agregarCondicionSalud("vértigo");
    }
    
    @Test
    public void testVerificarRestriccionesFisicas() {
        assertTrue(atraccionMecanica.verificarRestriccionesFisicas(clienteValido));
        
        assertFalse(atraccionMecanica.verificarRestriccionesFisicas(clienteInvalido));
    }
    
    @Test
    public void testVerificarContraindicaciones() {
        assertTrue(atraccionMecanica.verificarContraindicaciones(clienteValido));
        
        assertFalse(atraccionMecanica.verificarContraindicaciones(clienteInvalido));
    }
    
    @Test
    public void testVerificarRestriccionEdad() {
        assertTrue(atraccionCultural.verificarRestriccionEdad(clienteValido));
        
        assertFalse(atraccionCultural.verificarRestriccionEdad(clienteInvalido));
    }
    
    @Test
    public void testEstaDisponible() {
        assertTrue(atraccionMecanica.estaDisponible(fechaActual));
        
        atraccionMecanica.programarMantenimiento(fechaActual, fechaActual);
        
        assertFalse(atraccionMecanica.estaDisponible(fechaActual));
        
        Date mañana = new Date(fechaActual.getTime() + 24 * 60 * 60 * 1000);
        assertTrue(atraccionMecanica.estaDisponible(mañana));
        
        AtraccionMecanica atraccionTemporada = new AtraccionMecanica(
            "Montaña Acuática",
            "No operar en caso de tormenta eléctrica",
            true,  
            fechaActual,
            fechaFutura,
            "Familiar",
            2,
            "Zona Acuática",
            100,
            120.0f,
            200.0f,
            40.0f,
            120.0f,
            "",
            "medio"
        );
        
        assertTrue(atraccionTemporada.estaDisponible(fechaActual));
        assertTrue(atraccionTemporada.estaDisponible(new Date(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000)));
        
        assertFalse(atraccionTemporada.estaDisponible(fechaPasada));
        assertFalse(atraccionTemporada.estaDisponible(new Date(fechaFutura.getTime() + 24 * 60 * 60 * 1000)));
    }
    
    @Test
    public void testConsultarInformacion() {
        String infoMecanica = atraccionMecanica.consultarInformacion();
        assertTrue(infoMecanica.contains("Montaña Rusa"));
        assertTrue(infoMecanica.contains("Zona Norte"));
        assertTrue(infoMecanica.contains("Oro"));
        assertTrue(infoMecanica.contains("120.0 cm - 200.0 cm"));
        assertTrue(infoMecanica.contains("40.0 kg - 120.0 kg"));
        assertTrue(infoMecanica.contains("alto"));
        
        String infoCultural = atraccionCultural.consultarInformacion();
        assertTrue(infoCultural.contains("Casa del Terror"));
        assertTrue(infoCultural.contains("Zona Sur"));
        assertTrue(infoCultural.contains("Familiar"));
        assertTrue(infoCultural.contains("12 años"));
    }
    
    @Test
    public void testEsRiesgoAltoMedio() {
        assertTrue(atraccionMecanica.esRiesgoAlto());
        assertFalse(atraccionMecanica.esRiesgoMedio());
        
        AtraccionMecanica atraccionRiesgoMedio = new AtraccionMecanica(
            "Carrusel",
            "Ninguna",
            false,
            null,
            null,
            "Familiar",
            1,
            "Zona Infantil",
            30,
            90.0f,
            200.0f,
            0.0f,
            100.0f,
            "",
            "medio"
        );
        
        assertTrue(atraccionRiesgoMedio.esRiesgoMedio());
        assertFalse(atraccionRiesgoMedio.esRiesgoAlto());
    }
}
