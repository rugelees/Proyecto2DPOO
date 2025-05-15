package tests;


import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import modelo.atracciones.AtraccionMecanica;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.AtraccionMedio;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Regular;
import modelo.empleados.ServicioGeneral;
import modelo.lugares.Cafeteria;
import modelo.lugares.LugarServicio;
import modelo.lugares.Taquilla;
import excepciones.TiqueteException;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Cliente;

public class TestEmpleado {
    
    private AtraccionAlto empleadoAtraccionAlto;
    private AtraccionMedio empleadoAtraccionMedio;
    private Cajero empleadoCajero;
    private Cocinero empleadoCocinero;
    private Regular empleadoRegular;
    private ServicioGeneral empleadoServicioGeneral;
    
    private AtraccionMecanica atraccionAlto;
    private AtraccionMecanica atraccionMedio;
    private Cafeteria cafeteria;
    private Taquilla taquilla;
    private LugarServicio tienda;
    
    private Date fechaActual;
    private Date fechaCapacitacion;
    private Date fechaVencimiento;
    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        fechaCapacitacion = new Date();
        fechaCapacitacion.setTime(fechaActual.getTime() - 30L * 24 * 60 * 60 * 1000);
        
        fechaVencimiento = new Date();
        fechaVencimiento.setTime(fechaActual.getTime() + 365L * 24 * 60 * 60 * 1000);
        
        atraccionAlto = new AtraccionMecanica(
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
        
        atraccionMedio = new AtraccionMecanica(
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
        
        List<String> menu = new ArrayList<>();
        menu.add("Hamburguesas");
        menu.add("Papas fritas");
        menu.add("Refrescos");
        cafeteria = new Cafeteria("CAF1", "Cafetería Central", "Centro del parque", menu, 50);
        
        taquilla = new Taquilla("TAQ1", "Taquilla Principal", "Entrada del parque", "Efectivo y tarjeta");
        
        tienda = new LugarServicio("TIE1", "Tienda de Recuerdos", "Salida del parque", "Tienda", false);
        
        empleadoAtraccionAlto = new AtraccionAlto(
            "Operador de atracción alta",
            "Juan Pérez",
            1,
            false,
            "juan@ejemplo.com",
            "clave123",
            false,
            true  
        );
        
        empleadoAtraccionMedio = new AtraccionMedio(
            fechaCapacitacion,
            fechaVencimiento,
            "Operador de atracción media",
            "María López",
            2,
            false,
            "maria@ejemplo.com",
            "clave123",
            true  
        );
        
        empleadoCajero = new Cajero(
            "Cajero",
            "Pedro Gómez",
            3,
            false,
            "pedro@ejemplo.com",
            "clave123",
            false
        );
        
        empleadoCocinero = new Cocinero(
            true,  
            "Cocinero",
            "Ana Martínez",
            4,
            false,
            "ana@ejemplo.com",
            "clave123",
            true  
        );
        
        empleadoRegular = new Regular(
            true,  
            "Regular",
            "Carlos Rodríguez",
            5,
            false,
            "carlos@ejemplo.com",
            "clave123",
            false
        );
        
        List<String> zonas = new ArrayList<>();
        zonas.add("Zona Norte");
        zonas.add("Zona Central");
        empleadoServicioGeneral = new ServicioGeneral(
            zonas,
            "Servicio General",
            "Lucía Sánchez",
            6,
            "lucia@ejemplo.com",
            "clave123",
            false
        );
    }
    
    @Test
    public void testEmpleadoAtraccionAlto() {
        assertTrue(empleadoAtraccionAlto.puedeOperarAtraccionRiesgoAlto());
        
        assertTrue(empleadoAtraccionAlto.asignarAtraccionAlta(atraccionAlto));
        
        List<AtraccionMecanica> atraccionesAsignadas = empleadoAtraccionAlto.getAtraccionesEspecificas();
        assertEquals(1, atraccionesAsignadas.size());
        assertEquals(atraccionAlto, atraccionesAsignadas.get(0));
        
        assertTrue(empleadoAtraccionAlto.estaCapacitadoParaAtraccion(atraccionAlto));
        
        assertFalse(empleadoAtraccionAlto.asignarAtraccionAlta(atraccionMedio));
        
        assertTrue(empleadoAtraccionAlto.removerAtraccionEspecifica(atraccionAlto));
        assertTrue(empleadoAtraccionAlto.getAtraccionesEspecificas().isEmpty());
    }
    
    @Test
    public void testEmpleadoAtraccionMedio() {
        assertTrue(empleadoAtraccionMedio.puedeOperarAtraccionRiesgoMedio());
        
        assertTrue(empleadoAtraccionMedio.asignarAtraccionMedia(atraccionMedio));
        
        List<AtraccionMecanica> atraccionesAsignadas = empleadoAtraccionMedio.getAtraccionesAsignadas();
        assertEquals(1, atraccionesAsignadas.size());
        assertEquals(atraccionMedio, atraccionesAsignadas.get(0));
        
        assertFalse(empleadoAtraccionMedio.asignarAtraccionMedia(atraccionAlto));
        
        Date fechaVencida = new Date();
        fechaVencida.setTime(fechaActual.getTime() - 1); 
        empleadoAtraccionMedio.setFechaVencimientoCapacitacion(fechaVencida);
        
        assertFalse(empleadoAtraccionMedio.puedeOperarAtraccionRiesgoMedio());
    }
    
    @Test
    public void testEmpleadoCajero() {
        empleadoCajero.asignarLugarServicio(taquilla);
        
        assertEquals(taquilla, empleadoCajero.getLugarAsignado());
        
        Cliente cliente = new Cliente("Cliente Prueba", 100, "cliente@ejemplo.com", "clave123");
        Date fechaCompra = new Date();
        TiqueteBasico tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaCompra, "Activo", "Taquilla", "Adulto", false);
        
        try {
            assertTrue(empleadoCajero.venderTiquete(cliente, tiquete));
            
            assertEquals(1, cliente.getTiquetes().size());
            assertEquals(tiquete, cliente.getTiquetes().get(0));
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testEmpleadoCocinero() {
        assertTrue(empleadoCocinero.isCapacitado());
        assertTrue(empleadoCocinero.puedeTrabajarCaja());
        
        assertTrue(empleadoCocinero.asignarCocina(cafeteria));
        
        assertEquals(cafeteria, empleadoCocinero.getCafeteriaAsignada());
        
        empleadoCocinero.setCapacitado(false);
        assertFalse(empleadoCocinero.asignarCocina(cafeteria));
    }
    
    @Test
    public void testEmpleadoRegular() {
        assertTrue(empleadoRegular.isPuedeSerCajero());
        
        assertTrue(empleadoRegular.asignarCajero(tienda));
        
        assertEquals(tienda, empleadoRegular.getLugarAsignado());
        
        assertFalse(empleadoRegular.esServicioGeneral());
        
        assertTrue(empleadoRegular.asignarServicioGeneral());
        
        assertTrue(empleadoRegular.esServicioGeneral());
        assertNull(empleadoRegular.getLugarAsignado());
        
        empleadoRegular.setPuedeSerCajero(false);
        assertFalse(empleadoRegular.asignarCajero(tienda));
    }
    
    @Test
    public void testEmpleadoServicioGeneral() {
        assertTrue(empleadoServicioGeneral.esServicioGeneral());
        
        List<String> zonas = empleadoServicioGeneral.getZonasAsignadas();
        assertEquals(2, zonas.size());
        assertTrue(zonas.contains("Zona Norte"));
        assertTrue(zonas.contains("Zona Central"));
        
        assertTrue(empleadoServicioGeneral.tieneZonaAsignada("Zona Norte"));
        assertFalse(empleadoServicioGeneral.tieneZonaAsignada("Zona Sur"));
        
        assertTrue(empleadoServicioGeneral.asignarZona("Zona Sur"));
        
        assertTrue(empleadoServicioGeneral.tieneZonaAsignada("Zona Sur"));
        
        assertTrue(empleadoServicioGeneral.removerZona("Zona Norte"));
        assertFalse(empleadoServicioGeneral.tieneZonaAsignada("Zona Norte"));
    }
}