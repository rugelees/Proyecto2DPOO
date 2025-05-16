package tests;



import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
import modelo.lugares.LugarServicio;
import modelo.lugares.Taquilla;
import modelo.tiquetes.Tiquete;
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
    
//    Para el manejo de atracciones
    List<Atraccion> atracciones;

    
    @Before
    public void setUp() {
        fechaActual = new Date();
        
        admin = new Administrador("Admin Principal", 1000, "admin@parque.com", "admin123");
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
        atracciones = new ArrayList<>();
        admin.getAtracciones().add(atraccionMecanica);
        admin.getAtracciones().add(atraccionCultural);


    }
    
    
//    Esto es para cuando termina todo 
    @After
    public void tearDown( ) throws Exception
    {
    }
    
    
//    Los siguientes 3 métodos son para probar el método cambiarInfoAtraccion
    @Test
    public void testCambiarInfoAtracciones() throws AtraccionException {
        AtraccionCultural atraccionCulturalModificada = new AtraccionCultural(
            "Casa del Terror",
            "Ninguna",
            false,
            null,
            null,
            "Diamante", 
            1,
            "Zona Sur",
            30,
            13
        );
        
        

        admin.cambiarInfoAtraccion(atraccionCulturalModificada);

        Atraccion encontrada = null;
        for (Atraccion a : admin.getAtracciones()) {
            if (a.getNombre().equals("Casa del Terror")) {
                encontrada = a;
                break;
            }
        }

        assertNotNull(encontrada);
        assertEquals("Diamante", ((AtraccionCultural) encontrada).getNivelExclusividad());  
        assertEquals(13, ((AtraccionCultural) encontrada).getEdadMinima());
    }

    
    @Test
    public void testCambiarInfoAtraccionInexistente() {
        Atraccion atraccionInexistente = new AtraccionCultural(
            "Montaña Rusa Inexistente", 
            "Sin restricciones",
            false,
            null,
            null,
            "Familiar",
            1,
            "Zona Oeste",
            40,
            15
        );
        
        try {
            admin.cambiarInfoAtraccion(atraccionInexistente);
            fail("Se esperaba una excepción de tipo AtraccionException");
        } catch (AtraccionException e) {
            assertEquals("La atracción no existe en el sistema", e.getMessage());
        }
    }
    
    @Test
    public void testCambiarInfoAtraccionNull() {
        try {
            admin.cambiarInfoAtraccion(null);
            fail("Se esperaba una excepción de tipo AtraccionException");
        } catch (AtraccionException e) {
            assertEquals("La atracción no puede ser nula", e.getMessage());
        }
    }
    
    
//  Los siguientes 3 métodos son para probar el método cambiarInfoAtraccion
//  @Test
//  public void testCambiarInfoEmpleado() throws AtraccionException, EmpleadoException {
//      Empleado emp1= new Cajero(
//                  "Cajero",
//                  "David Bowie",
//                  3,
//                  false,
//                  "david@.rcarcods.com",
//                  "Changes_deshrek",
//                  false
//              );
//
//      admin.cambiarInfoEmpleado(emp1);
//
//      Empleado encontrada = null;
//      for (Atraccion a : admin.getAtracciones()) {
//          if (a.getNombre().equals("Casa del Terror")) {
//              encontrada = a;
//              break;
//          }
//      }
//
//      assertNotNull(encontrada);
//      assertEquals("Diamante", ((AtraccionCultural) encontrada).getNivelExclusividad());  
//      assertEquals(13, ((AtraccionCultural) encontrada).getEdadMinima());
//  }
//    
    
    

    
    

    
    
//    @Test
//    public void testGestionAtracciones() {
//        assertTrue(admin.getAtracciones().isEmpty());
//        
//        try {
//            admin.agregarAtraccion(atraccionMecanica);
//            admin.agregarAtraccion(atraccionCultural);
//            
//            List<Atraccion> atracciones = admin.getAtracciones();
//            assertEquals(2, atracciones.size());
//            assertTrue(atracciones.contains(atraccionMecanica));
//            assertTrue(atracciones.contains(atraccionCultural));
//            
//            atraccionMecanica.setNivelExclusividad("Diamante");
//            admin.cambiarInfoAtraccion(atraccionMecanica);
//            
//            assertEquals("Diamante", admin.getAtracciones().get(0).getNivelExclusividad());
//            
//            admin.eliminarAtraccion(atraccionCultural);
//            
//            atracciones = admin.getAtracciones();
//            assertEquals(1, atracciones.size());
//            assertFalse(atracciones.contains(atraccionCultural));
//            
//        } catch (AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//        
//        try {
//            admin.agregarAtraccion(null);
//            fail("Debería lanzar excepción");
//        } catch (AtraccionException e) {
//        }
//    }
//    
//    @Test
//    public void testGestionEmpleados() {
//        assertTrue(admin.getEmpleados().isEmpty());
//        
//        try {
//            admin.agregarEmpleado(empleadoAtraccionAlto);
//            admin.agregarEmpleado(empleadoAtraccionMedio);
//            admin.agregarEmpleado(empleadoCajero);
//            admin.agregarEmpleado(empleadoCocinero);
//            
//            List<Empleado> empleados = admin.getEmpleados();
//            assertEquals(4, empleados.size());
//            assertTrue(empleados.contains(empleadoAtraccionAlto));
//            assertTrue(empleados.contains(empleadoCocinero));
//            
//            empleadoCajero.setHorasExtras(true);
//            admin.cambiarInfoEmpleado(empleadoCajero);
//            
//            for (Empleado empleado : admin.getEmpleados()) {
//                if (empleado.equals(empleadoCajero)) {
//                    assertTrue(empleado.isHorasExtras());
//                }
//            }
//            
//            admin.eliminarEmpleado(empleadoCocinero);
//            
//            empleados = admin.getEmpleados();
//            assertEquals(3, empleados.size());
//            assertFalse(empleados.contains(empleadoCocinero));
//            
//        } catch (EmpleadoException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//        
//        try {
//            admin.agregarEmpleado(null);
//            fail("Debería lanzar excepción");
//        } catch (EmpleadoException e) {
//        }
//    }
//    
//    @Test
//    public void testGestionEspectaculos() {
//        assertTrue(admin.getEspectaculos().isEmpty());
//        
//        try {
//            admin.crearEspectaculo(espectaculo);
//            
//            List<Espectaculo> espectaculos = admin.getEspectaculos();
//            assertEquals(1, espectaculos.size());
//            assertEquals(espectaculo, espectaculos.get(0));
//            
//            espectaculo.setCapacidad(150);
//            admin.modificarEspectaculo(espectaculo);
//            
//            assertEquals(150, admin.getEspectaculos().get(0).getCapacidad());
//            
//            admin.eliminarEspectaculo(espectaculo);
//            
//            assertTrue(admin.getEspectaculos().isEmpty());
//            
//        } catch (AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testAsignacionEmpleados() {
//        try {
//            admin.agregarEmpleado(empleadoAtraccionAlto);
//            admin.agregarEmpleado(empleadoAtraccionMedio);
//            admin.agregarEmpleado(empleadoCajero);
//            admin.agregarEmpleado(empleadoCocinero);
//            
//            admin.agregarAtraccion(atraccionMecanica);
//            
//            admin.asignarEmpleadoAtraccion(empleadoAtraccionAlto, atraccionMecanica, fechaActual, Recurrente.APERTURA);
//            
//            assertTrue(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
//            assertEquals(atraccionMecanica, admin.obtenerLugarAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
//            
//            List<Empleado> empleadosAsignados = admin.obtenerEmpleadosAsignadosTurno(fechaActual, Recurrente.APERTURA);
//            assertEquals(1, empleadosAsignados.size());
//            assertTrue(empleadosAsignados.contains(empleadoAtraccionAlto));
//            
//            admin.asignarEmpleadoAtraccion(empleadoAtraccionMedio, atraccionMecanica, fechaActual, Recurrente.APERTURA);
//            
//            assertTrue(admin.verificarPersonalMinimo(atraccionMecanica, fechaActual, Recurrente.APERTURA));
//            
//            admin.liberarAsignacion(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA);
//            
//            assertFalse(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
//            
//            assertFalse(admin.verificarPersonalMinimo(atraccionMecanica, fechaActual, Recurrente.APERTURA));
//            
//        } catch (EmpleadoException | AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testGestionCocinerosCajeros() {
//        try {
//            admin.agregarEmpleado(empleadoCocinero);
//            admin.agregarEmpleado(empleadoCajero);
//            
//            admin.asignarCocineroACafeteria(empleadoCocinero, cafeteria, fechaActual, Recurrente.APERTURA);
//            
//            assertTrue(admin.estaEmpleadoAsignado(empleadoCocinero, fechaActual, Recurrente.APERTURA));
//            assertEquals(cafeteria, admin.obtenerLugarAsignado(empleadoCocinero, fechaActual, Recurrente.APERTURA));
//            
//            admin.asignarCajeroALugarServicio(empleadoCajero, taquilla, fechaActual, Recurrente.APERTURA);
//            
//            assertTrue(admin.estaEmpleadoAsignado(empleadoCajero, fechaActual, Recurrente.APERTURA));
//            assertEquals(taquilla, admin.obtenerLugarAsignado(empleadoCajero, fechaActual, Recurrente.APERTURA));
//            
//        } catch (EmpleadoException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testGestionServicioGeneral() {
//        try {
//            admin.agregarEmpleado(empleadoAtraccionAlto);
//            
//            String[] zonas = {"Zona Norte", "Zona Central"};
//            admin.asignarEmpleadoServicioGeneral(empleadoAtraccionAlto, zonas, fechaActual, Recurrente.APERTURA);
//            
//            assertTrue(admin.estaEmpleadoAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA));
//            Object asignacion = admin.obtenerLugarAsignado(empleadoAtraccionAlto, fechaActual, Recurrente.APERTURA);
//            assertTrue(asignacion instanceof String[]);
//            String[] zonasAsignadas = (String[]) asignacion;
//            assertEquals(2, zonasAsignadas.length);
//            
//        } catch (EmpleadoException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testGestionAtraccionesTemporada() {
//        try {
//            admin.agregarAtraccion(atraccionMecanica);
//            
//            assertFalse(atraccionMecanica.isDeTemporada());
//            
//            Date fechaInicio = fechaActual;
//            Date fechaFin = new Date(fechaActual.getTime() + 90L * 24 * 60 * 60 * 1000); 
//            
//            admin.gestionarAtraccionesTemporada(atraccionMecanica, true, fechaInicio, fechaFin);
//            
//            assertTrue(atraccionMecanica.isDeTemporada());
//            assertEquals(fechaInicio, atraccionMecanica.getFechaInicio());
//            assertEquals(fechaFin, atraccionMecanica.getFechaFin());
//            
//            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));
//            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000)));
//            assertFalse(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaFin.getTime() + 24 * 60 * 60 * 1000)));
//            
//        } catch (AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testGestionMantenimiento() {
//        try {
//            admin.agregarAtraccion(atraccionMecanica);
//            
//            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));
//            
//            Date fechaInicio = fechaActual;
//            Date fechaFin = new Date(fechaActual.getTime() + 7L * 24 * 60 * 60 * 1000); 
//            
//            admin.gestionarMantenimientoAtracciones(atraccionMecanica, fechaInicio, fechaFin);
//            
//            assertFalse(admin.verificarDisponibilidadAtraccion(atraccionMecanica, fechaActual));
//            assertFalse(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaActual.getTime() + 3L * 24 * 60 * 60 * 1000)));
//            
//            assertTrue(admin.verificarDisponibilidadAtraccion(atraccionMecanica, new Date(fechaFin.getTime() + 24 * 60 * 60 * 1000)));
//            
//        } catch (AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testCambiarNivelExclusividad() {
//        try {
//            admin.agregarAtraccion(atraccionMecanica);
//            
//            assertEquals("Oro", atraccionMecanica.getNivelExclusividad());
//            
//            admin.cambiarNivelExclusividadAtraccion(atraccionMecanica, "Diamante");
//            
//            assertEquals("Diamante", atraccionMecanica.getNivelExclusividad());
//            
//            try {
//                admin.cambiarNivelExclusividadAtraccion(atraccionMecanica, "SuperDiamante");
//                fail("Debería lanzar excepción");
//            } catch (AtraccionException e) {
//            }
//            
//        } catch (AtraccionException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
//    
//    @Test
//    public void testDescuentoEmpleado() {
//        try {
//            admin.agregarEmpleado(empleadoCajero);
//            
//            TiqueteBasico tiquete = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
//            
//            assertFalse(tiquete.isDctoEmpleado());
//            
//            admin.gestionarDescuentoEmpleado(tiquete, empleadoCajero);
//            
//            assertTrue(tiquete.isDctoEmpleado());
//            
//        } catch (EmpleadoException e) {
//            fail("No debería lanzar excepción: " + e.getMessage());
//        }
//    }
    
    
}
