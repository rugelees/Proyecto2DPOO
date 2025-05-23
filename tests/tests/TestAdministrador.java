package tests;



import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import Principal.Parque;
import excepciones.AtraccionException;
import excepciones.EmpleadoException;
import modelo.atracciones.Atraccion;
import modelo.atracciones.AtraccionCultural;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.AtraccionMedio;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Empleado;
import modelo.lugares.Cafeteria;
import modelo.lugares.Taquilla;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Administrador;
import funcionesrecurrentes.Recurrente;

public class TestAdministrador {
    
    private Administrador admin;
    
    private AtraccionMecanica atraccionMecanica;
    private AtraccionCultural atraccionCultural;
    private Espectaculo espectaculo;
    
    private AtraccionAlto empleadoAtraccionAlto;
    private AtraccionMedio empleadoAtraccionMedio;
    private Cajero empleadoCajero;
    private Cocinero empleadoCocinero;
    
    private Cafeteria cafeteria;
    private Taquilla taquilla;
    
    private Date fechaActual;
    private Parque parque;
    
    @Before
    public void setUp() throws IOException {
        fechaActual = new Date();
        parque = new Parque();

        admin = new Administrador("Admin Test", 999, "admin@test.com", "admin123", parque);
        
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
        
        espectaculo = new Espectaculo(
            "Show de Delfines",
            "No realizar en caso de lluvia",
            true,
            fechaActual,
            new Date(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000),
            "45 minutos",
            "15:00",
            100
        );
        
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
            new Date(fechaActual.getTime() - 30L * 24 * 60 * 60 * 1000),
            new Date(fechaActual.getTime() + 365L * 24 * 60 * 60 * 1000),
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
        
        List<String> menu = new ArrayList<>();
        menu.add("Hamburguesas");
        menu.add("Papas fritas");
        menu.add("Refrescos");
        cafeteria = new Cafeteria("CAF1", "Cafetería Central", "Centro del parque", menu, 50);
        
        taquilla = new Taquilla("TAQ1", "Taquilla Principal", "Entrada del parque", "Efectivo y tarjeta");
    }
    
    @Test
    public void testGestionAtracciones() {
        assertTrue(admin.getAtracciones().isEmpty());
        
        try {
            admin.crearAtraccion(atraccionMecanica);
            admin.crearAtraccion(atraccionCultural);
            
            List<Atraccion> atracciones = admin.getAtracciones();
            assertEquals(2, atracciones.size());
            assertTrue(atracciones.contains(atraccionMecanica));
            assertTrue(atracciones.contains(atraccionCultural));
            
            atraccionMecanica.setNivelExclusividad("Diamante");
            admin.actualizarAtraccion(atraccionMecanica);
            
            assertEquals("Diamante", admin.getAtracciones().get(0).getNivelExclusividad());
            
            admin.eliminarAtraccion(atraccionCultural);
            
            atracciones = admin.getAtracciones();
            assertEquals(1, atracciones.size());
            assertFalse(atracciones.contains(atraccionCultural));
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
        
        try {
            admin.crearAtraccion(null);
            fail("Debería lanzar excepción");
        } catch (AtraccionException e) {
        }
    }
    
    @Test
    public void testGestionEmpleados() {
        assertTrue(admin.getEmpleados().isEmpty());
        
        try {
            admin.agregarEmpleado(empleadoAtraccionAlto);
            admin.agregarEmpleado(empleadoAtraccionMedio);
            admin.agregarEmpleado(empleadoCajero);
            admin.agregarEmpleado(empleadoCocinero);
            
            List<Empleado> empleados = admin.getEmpleados();
            assertEquals(4, empleados.size());
            assertTrue(empleados.contains(empleadoAtraccionAlto));
            assertTrue(empleados.contains(empleadoCocinero));
            
            empleadoCajero.setHorasExtras(true);
            admin.actualizarEmpleado(empleadoAtraccionAlto);
            
            for (Empleado empleado : admin.getEmpleados()) {
                if (empleado.equals(empleadoCajero)) {
                    assertTrue(empleado.isHorasExtras());
                }
            }
            
            admin.eliminarEmpleado(empleadoCocinero);
            
            empleados = admin.getEmpleados();
            assertEquals(3, empleados.size());
            assertFalse(empleados.contains(empleadoCocinero));
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
        
        try {
            admin.agregarEmpleado(null);
            fail("Debería lanzar excepción");
        } catch (EmpleadoException e) {
        }
    }
    
    @Test
    public void testGestionEspectaculos() {
        assertTrue(admin.getEspectaculos().isEmpty());
        
        try {
            admin.crearEspectaculo(espectaculo);
            
            List<Espectaculo> espectaculos = admin.getEspectaculos();
            assertEquals(1, espectaculos.size());
            assertEquals(espectaculo, espectaculos.get(0));
            
            espectaculo.setCapacidad(150);
            admin.modificarEspectaculo(espectaculo);
            
            assertEquals(150, admin.getEspectaculos().get(0).getCapacidad());
            
            admin.eliminarEspectaculo(espectaculo);
            
            assertTrue(admin.getEspectaculos().isEmpty());
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testAsignacionEmpleados() {
        try {
            admin.agregarEmpleado(empleadoAtraccionAlto);
            admin.agregarEmpleado(empleadoAtraccionMedio);
            admin.agregarEmpleado(empleadoCajero);
            admin.agregarEmpleado(empleadoCocinero);
            
            admin.crearAtraccion(atraccionMecanica);
            
            admin.asignarEmpleadoAtraccion(empleadoAtraccionAlto, atraccionMecanica, fechaActual, Recurrente.APERTURA);
            
            assertTrue(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
            assertEquals(atraccionMecanica, admin.obtenerLugarAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
            
            List<Empleado> empleadosAsignados = admin.obtenerEmpleadosAsignadosTurno(fechaActual, Recurrente.APERTURA);
            assertEquals(1, empleadosAsignados.size());
            assertTrue(empleadosAsignados.contains(empleadoAtraccionAlto));
            
            admin.asignarEmpleadoAtraccion(empleadoAtraccionMedio, atraccionMecanica, fechaActual, Recurrente.APERTURA);
            
            assertTrue(admin.verificarPersonalMinimoEnAtraccion(atraccionMecanica, fechaActual, Recurrente.APERTURA));
            
            admin.liberarAsignacion(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA);
            
            assertFalse(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
            
            assertFalse(admin.verificarPersonalMinimoEnAtraccion(atraccionMecanica, fechaActual, Recurrente.APERTURA));
            
        } catch (EmpleadoException | AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testGestionCocinerosCajeros() {
        try {
            admin.agregarEmpleado(empleadoCocinero);
            admin.agregarEmpleado(empleadoCajero);
            
            admin.asignarCocineroCafeteria(empleadoCocinero, cafeteria, fechaActual, Recurrente.APERTURA);
            
            assertTrue(admin.estaEmpleadoAsignado(empleadoCocinero, fechaActual, Recurrente.APERTURA));
            assertEquals(cafeteria, admin.obtenerLugarAsignado(empleadoCocinero, fechaActual, Recurrente.APERTURA));
            
            admin.asignarCajeroLugarServicio(empleadoCajero, taquilla, fechaActual, Recurrente.APERTURA);
            
            assertTrue(admin.estaEmpleadoAsignado(empleadoCajero, fechaActual, Recurrente.APERTURA));
            assertEquals(taquilla, admin.obtenerLugarAsignado(empleadoCajero, fechaActual, Recurrente.APERTURA));
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testGestionServicioGeneral() {
        try {
            admin.agregarEmpleado(empleadoAtraccionAlto);
            
            String[] zonas = {"Zona Norte", "Zona Central"};
            admin.asignarEmpleadoServicioGeneral(empleadoAtraccionAlto, zonas, fechaActual, Recurrente.APERTURA);
            
            assertTrue(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
            Object asignacion = admin.obtenerLugarAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA);
            assertTrue(asignacion instanceof String[]);
            String[] zonasAsignadas = (String[]) asignacion;
            assertEquals(2, zonasAsignadas.length);
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testGestionAtraccionesTemporada() {
        try {
            System.out.println("Antes de crear atracción");
            admin.crearAtraccion(atraccionMecanica);
            System.out.println("Atracción creada: deTemporada = " + atraccionMecanica.isDeTemporada());

            assertFalse("La atracción debería iniciar NO siendo de temporada", atraccionMecanica.isDeTemporada());

            Date fechaInicio = fechaActual;
            Date fechaFin = new Date(fechaActual.getTime() + 90L * 24 * 60 * 60 * 1000);
            
            System.out.println("Antes de gestionar atracciones de temporada");
            admin.gestionarAtraccionesTemporada(atraccionMecanica, true, fechaInicio, fechaFin);
            System.out.println("Después de gestionar temporada: deTemporada = " + atraccionMecanica.isDeTemporada() +
                               ", fechaInicio = " + atraccionMecanica.getFechaInicio() +
                               ", fechaFin = " + atraccionMecanica.getFechaFin());

            assertTrue("La atracción debería ser de temporada después de gestionarla", atraccionMecanica.isDeTemporada());
            assertEquals("Fecha inicio incorrecta", fechaInicio, atraccionMecanica.getFechaInicio());
            assertEquals("Fecha fin incorrecta", fechaFin, atraccionMecanica.getFechaFin());

            System.out.println("Verificando disponibilidad en fecha actual");
            assertTrue("La atracción debería estar disponible en la fecha actual",
                admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));

            System.out.println("Verificando disponibilidad 30 días después");
            assertTrue("La atracción debería estar disponible 30 días después",
                admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000)));

            System.out.println("Verificando disponibilidad después de fecha fin");
            assertFalse("La atracción NO debería estar disponible después de la fecha fin",
                admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaFin.getTime() + 24 * 60 * 60 * 1000)));

            System.out.println("Test finalizado exitosamente");

        } catch (AtraccionException e) {
            e.printStackTrace();
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    
    @Test
    public void testGestionMantenimiento() {
        try {
            admin.crearAtraccion(atraccionMecanica);
            
            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));
            
            Date fechaInicio = fechaActual;
            Date fechaFin = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000); 
            
            admin.gestionarMantenimientoAtracciones(atraccionMecanica, fechaInicio, fechaFin);
            
            assertFalse(admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));
            assertFalse(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaActual.getTime() + 3L * 24 * 60 * 60 * 1000)));
            
            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaFin.getTime() + 24 * 60 * 60 * 1000)));
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testCambiarNivelExclusividad() {
        try {
            admin.crearAtraccion(atraccionMecanica);
            
            assertEquals("Oro", atraccionMecanica.getNivelExclusividad());
            
            admin.cambiarNivelExclusividadAtraccion(atraccionMecanica, "Diamante");
            
            assertEquals("Diamante", atraccionMecanica.getNivelExclusividad());
            
            try {
                admin.cambiarNivelExclusividadAtraccion(atraccionMecanica, "SuperDiamante");
                fail("Debería lanzar excepción");
            } catch (AtraccionException e) {
            }
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testDescuentoEmpleado() {
        try {
            admin.agregarEmpleado(empleadoCajero);
            
            TiqueteBasico tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
            
            assertFalse(tiquete.isDctoEmpleado());
            
            admin.gestionarDescuentoEmpleado(tiquete, empleadoCajero);
            
            assertTrue(tiquete.isDctoEmpleado());
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
