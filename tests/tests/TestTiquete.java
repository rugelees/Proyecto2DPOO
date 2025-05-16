package tests;


import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.atracciones.AtraccionCultural;
import modelo.atracciones.AtraccionMecanica;
import modelo.tiquetes.EnTemporada;
import modelo.tiquetes.FastPass;
import modelo.tiquetes.Individual;
import modelo.tiquetes.TiqueteBasico;
import funcionesrecurrentes.Recurrente;

public class TestTiquete {
    
    private TiqueteBasico tiqueteBasicoFamiliar;
    private TiqueteBasico tiqueteBasicoOro;
    private TiqueteBasico tiqueteBasicoDiamante;
    private EnTemporada tiqueteTemporada;
    private Individual tiqueteIndividual;
    private FastPass fastPass;
    
    private AtraccionMecanica atraccionFamiliar;
    private AtraccionCultural atraccionOro;
    private AtraccionMecanica atraccionDiamante;
    
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
        
        atraccionFamiliar = new AtraccionMecanica(
            "Carrusel",
            "Ninguna",
            false,
            null,
            null,
            Recurrente.FAMILIAR,
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
        
        atraccionOro = new AtraccionCultural(
            "Teatro 4D",
            "Ninguna",
            false,
            null,
            null,
            Recurrente.ORO,
            1,
            "Zona Central",
            50,
            8
        );
        
        atraccionDiamante = new AtraccionMecanica(
            "Montaña Rusa Extrema",
            "No operar en caso de tormenta eléctrica",
            false,
            null,
            null,
            Recurrente.DIAMANTE,
            2,
            "Zona Extrema",
            50,
            120.0f,
            200.0f,
            40.0f,
            120.0f,
            "vértigo, problemas cardíacos",
            "alto"
        );
        
        tiqueteBasicoFamiliar = new TiqueteBasico(1, "Tiquete Familiar", 1, Recurrente.FAMILIAR, fechaActual, "Activo", "Taquilla", "Adulto", false);
        tiqueteBasicoOro = new TiqueteBasico(2, "Tiquete Oro", 1, Recurrente.ORO, fechaActual, "Activo", "Taquilla", "Adulto", false);
        tiqueteBasicoDiamante = new TiqueteBasico(3, "Tiquete Diamante", 1, Recurrente.DIAMANTE, fechaActual, "Activo", "Taquilla", "Adulto", false);
        
        tiqueteTemporada = new EnTemporada(4, "Pase Mensual", 1, Recurrente.ORO, fechaActual, "Activo", "Online", fechaActual, fechaFutura, "Mensual", "Adulto", false);
        
        tiqueteIndividual = new Individual(atraccionDiamante, 5, "Entrada Montaña Rusa", 1, Recurrente.FAMILIAR, fechaActual, "Activo", "Taquilla", false);
        
        fastPass = new FastPass(tiqueteBasicoFamiliar, fechaActual);
    }
    
    @Test
    public void testPuedeAccederAtraccionTiqueteBasico() {
        assertTrue(tiqueteBasicoFamiliar.puedeAccederAtraccion(atraccionFamiliar));
        assertFalse(tiqueteBasicoFamiliar.puedeAccederAtraccion(atraccionOro));
        assertFalse(tiqueteBasicoFamiliar.puedeAccederAtraccion(atraccionDiamante));
        
        assertTrue(tiqueteBasicoOro.puedeAccederAtraccion(atraccionFamiliar));
        assertTrue(tiqueteBasicoOro.puedeAccederAtraccion(atraccionOro));
        assertFalse(tiqueteBasicoOro.puedeAccederAtraccion(atraccionDiamante));
        
        assertTrue(tiqueteBasicoDiamante.puedeAccederAtraccion(atraccionFamiliar));
        assertTrue(tiqueteBasicoDiamante.puedeAccederAtraccion(atraccionOro));
        assertTrue(tiqueteBasicoDiamante.puedeAccederAtraccion(atraccionDiamante));
    }
    
    @Test
    public void testPuedeAccederAtraccionTiqueteTemporada() {
        assertTrue(tiqueteTemporada.puedeAccederAtraccion(atraccionFamiliar));
        assertTrue(tiqueteTemporada.puedeAccederAtraccion(atraccionOro));
        assertFalse(tiqueteTemporada.puedeAccederAtraccion(atraccionDiamante));
        
        assertTrue(tiqueteTemporada.estaVigente(fechaActual));
        assertTrue(tiqueteTemporada.estaVigente(new Date(fechaActual.getTime() + 15L * 24 * 60 * 60 * 1000))); 
        
        assertFalse(tiqueteTemporada.estaVigente(fechaPasada));
        assertFalse(tiqueteTemporada.estaVigente(new Date(fechaFutura.getTime() + 24 * 60 * 60 * 1000))); 
        
        tiqueteTemporada.setFechaFin(fechaPasada); 
        assertFalse(tiqueteTemporada.puedeAccederAtraccion(atraccionFamiliar));
        assertFalse(tiqueteTemporada.puedeAccederAtraccion(atraccionOro));
    }
    
    @Test
    public void testPuedeAccederAtraccionTiqueteIndividual() {
        assertFalse(tiqueteIndividual.puedeAccederAtraccion(atraccionFamiliar));
        assertFalse(tiqueteIndividual.puedeAccederAtraccion(atraccionOro));
        assertTrue(tiqueteIndividual.puedeAccederAtraccion(atraccionDiamante));
        
        tiqueteIndividual.marcarComoUsado();
        assertFalse(tiqueteIndividual.puedeAccederAtraccion(atraccionDiamante));
    }
    
    @Test
    public void testFastPass() {
        assertTrue(fastPass.esValido(fechaActual));
        
        assertFalse(fastPass.esValido(fechaPasada));
        assertFalse(fastPass.esValido(fechaFutura));
        
        fastPass.marcarComoUsado();
        assertFalse(fastPass.esValido(fechaActual));
        
        assertEquals(tiqueteBasicoFamiliar, fastPass.getTiqueteAsociado());
    }
    
    @Test
    public void testNivelExclusividad() {
        assertTrue(Recurrente.tieneAcceso(Recurrente.FAMILIAR, Recurrente.FAMILIAR));
        assertFalse(Recurrente.tieneAcceso(Recurrente.FAMILIAR, Recurrente.ORO));
        assertFalse(Recurrente.tieneAcceso(Recurrente.FAMILIAR, Recurrente.DIAMANTE));
        
        assertTrue(Recurrente.tieneAcceso(Recurrente.ORO, Recurrente.FAMILIAR));
        assertTrue(Recurrente.tieneAcceso(Recurrente.ORO, Recurrente.ORO));
        assertFalse(Recurrente.tieneAcceso(Recurrente.ORO, Recurrente.DIAMANTE));
        
        assertTrue(Recurrente.tieneAcceso(Recurrente.DIAMANTE, Recurrente.FAMILIAR));
        assertTrue(Recurrente.tieneAcceso(Recurrente.DIAMANTE, Recurrente.ORO));
        assertTrue(Recurrente.tieneAcceso(Recurrente.DIAMANTE, Recurrente.DIAMANTE));
    }
    
    @Test
    public void testDescuentoEmpleado() {
        assertFalse(tiqueteBasicoFamiliar.isDctoEmpleado());
        
        tiqueteBasicoFamiliar.setDctoEmpleado(true);
        assertTrue(tiqueteBasicoFamiliar.isDctoEmpleado());
    }
}
