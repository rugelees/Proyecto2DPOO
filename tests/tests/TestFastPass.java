package tests;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import modelo.tiquetes.FastPass;
import modelo.tiquetes.TiqueteBasico;
import modelo.atracciones.AtraccionMecanica;

public class TestFastPass {
    
    private FastPass fastPass;
    private TiqueteBasico tiqueteBase;
    private AtraccionMecanica atraccion;
    private Date fechaActual;
    private Date fechaFutura;
    private Date fechaPasada;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        fechaFutura = new Date();
        fechaFutura.setTime(fechaActual.getTime() + 24L * 60 * 60 * 1000);
        
        fechaPasada = new Date();
        fechaPasada.setTime(fechaActual.getTime() - 24L * 60 * 60 * 1000);
        
        atraccion = new AtraccionMecanica(
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
        
        tiqueteBase = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
        
        fastPass = new FastPass(tiqueteBase, fechaActual);
    }
    
    @Test
    public void testPropiedadesFastPass() {
        assertEquals(tiqueteBase, fastPass.getTiqueteAsociado());
        assertEquals(fechaActual, fastPass.getFechaValida());
        assertFalse(fastPass.isUsado());
    }
    
    @Test
    public void testEsValido() {
        assertTrue(fastPass.esValido(fechaActual));
        assertFalse(fastPass.esValido(fechaFutura));
        assertFalse(fastPass.esValido(fechaPasada));
        
        fastPass.marcarComoUsado();
        assertFalse(fastPass.esValido(fechaActual));
    }
    
    @Test
    public void testModificarEstado() {
        assertFalse(fastPass.isUsado());
        
        fastPass.setUsado(true);
        assertTrue(fastPass.isUsado());
        
        fastPass.setUsado(false);
        assertFalse(fastPass.isUsado());
    }
    
    @Test
    public void testModificarTiqueteAsociado() {
        TiqueteBasico nuevoTiquete = new TiqueteBasico(2, "Tiquete Oro", 1, "Oro", fechaActual, "Activo", "Online", "Adulto", false);
        fastPass.setTiqueteAsociado(nuevoTiquete);
        assertEquals(nuevoTiquete, fastPass.getTiqueteAsociado());
    }
    
    @Test
    public void testModificarFechaValida() {
        fastPass.setFechaValida(fechaFutura);
        assertEquals(fechaFutura, fastPass.getFechaValida());
    }
} 