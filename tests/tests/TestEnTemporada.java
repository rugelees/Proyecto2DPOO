package tests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.tiquetes.EnTemporada;
import modelo.atracciones.AtraccionMecanica;
import funcionesrecurrentes.Recurrente;

public class TestEnTemporada {
    
    private EnTemporada tiqueteTemporada;
    private AtraccionMecanica atraccion;
    private Date fechaActual;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaFueraDeTemporada;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        fechaInicio = new Date();
        fechaInicio.setTime(fechaActual.getTime() - 7L * 24 * 60 * 60 * 1000);
        
        fechaFin = new Date();
        fechaFin.setTime(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000);
        
        fechaFueraDeTemporada = new Date();
        fechaFueraDeTemporada.setTime(fechaFin.getTime() + 24L * 60 * 60 * 1000);
        
        atraccion = new AtraccionMecanica(
            "Montaña Rusa",
            "No operar en caso de tormenta eléctrica",
            true,
            fechaInicio,
            fechaFin,
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
        
        tiqueteTemporada = new EnTemporada(
            1,
            "Pase Mensual",
            1,
            Recurrente.ORO,
            fechaActual,
            "Activo",
            "Online",
            fechaInicio,
            fechaFin,
            "Mensual",
            "Adulto",
            false
        );
    }
    
    @Test
    public void testPropiedadesEnTemporada() {
        assertEquals(1, tiqueteTemporada.getId());
        assertEquals("Pase Mensual", tiqueteTemporada.getNombre());
        assertEquals(1, tiqueteTemporada.getNumTiquetes());
        assertEquals(Recurrente.ORO, tiqueteTemporada.getExclusividad());
        assertEquals(fechaActual, tiqueteTemporada.getFecha());
        assertEquals("Activo", tiqueteTemporada.getEstado());
        assertEquals("Online", tiqueteTemporada.getPortalCompra());
        assertEquals(fechaInicio, tiqueteTemporada.getFechaInicio());
        assertEquals(fechaFin, tiqueteTemporada.getFechaFin());
        assertEquals("Mensual", tiqueteTemporada.getTipoTemporada());
        assertEquals("Adulto", tiqueteTemporada.getCategoria());
        assertFalse(tiqueteTemporada.isUsado());
    }
    
    @Test
    public void testPuedeAccederAtraccion() {
        assertTrue(tiqueteTemporada.puedeAccederAtraccion(atraccion));
        
        tiqueteTemporada.setUsado(true);
        assertFalse(tiqueteTemporada.puedeAccederAtraccion(atraccion));
    }
    
    @Test
    public void testEstaVigente() {
        assertTrue(tiqueteTemporada.estaVigente(fechaActual));
        assertTrue(tiqueteTemporada.estaVigente(fechaInicio));
        assertTrue(tiqueteTemporada.estaVigente(fechaFin));
        assertFalse(tiqueteTemporada.estaVigente(fechaFueraDeTemporada));
    }
    
    @Test
    public void testModificarEstado() {
        assertFalse(tiqueteTemporada.isUsado());
        
        tiqueteTemporada.setUsado(true);
        assertTrue(tiqueteTemporada.isUsado());
        
        tiqueteTemporada.setUsado(false);
        assertFalse(tiqueteTemporada.isUsado());
    }
    
    @Test
    public void testModificarFechas() {
        Date nuevaFechaInicio = new Date();
        nuevaFechaInicio.setTime(fechaActual.getTime() - 14L * 24 * 60 * 60 * 1000);
        
        Date nuevaFechaFin = new Date();
        nuevaFechaFin.setTime(fechaActual.getTime() + 14L * 24 * 60 * 60 * 1000);
        
        tiqueteTemporada.setFechaInicio(nuevaFechaInicio);
        tiqueteTemporada.setFechaFin(nuevaFechaFin);
        
        assertEquals(nuevaFechaInicio, tiqueteTemporada.getFechaInicio());
        assertEquals(nuevaFechaFin, tiqueteTemporada.getFechaFin());
    }
    
    @Test
    public void testModificarTipoTemporadaYCategoria() {
        tiqueteTemporada.setTipoTemporada("Anual");
        assertEquals("Anual", tiqueteTemporada.getTipoTemporada());
        
        tiqueteTemporada.setCategoria("Niño");
        assertEquals("Niño", tiqueteTemporada.getCategoria());
    }
} 