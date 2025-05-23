package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Principal.Parque;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.File;
import java.io.IOException;

import modelo.usuarios.Cliente;
import modelo.usuarios.Administrador;
import modelo.empleados.Cajero;
import modelo.empleados.AtraccionAlto;
import modelo.tiquetes.TiqueteBasico;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.AtraccionCultural;
import modelo.lugares.Taquilla;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaTiquetes;
import persistencia.PersistenciaAtracciones;
import excepciones.TiqueteException;
import excepciones.EmpleadoException;
import excepciones.AtraccionException;
import funcionesrecurrentes.Recurrente;

public class CompraTiqueteIntegrationTest {
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaEmpleados persistenciaEmpleados;
    private PersistenciaTiquetes persistenciaTiquetes;
    private PersistenciaAtracciones persistenciaAtracciones;
    private Cliente cliente;
    private Cajero cajero;
    private Administrador admin;
    private AtraccionMecanica montanaRusa;
    private AtraccionCultural casaTerror;
    private AtraccionAlto empleadoAlto;
    private TiqueteBasico tiqueteBasico;
    private Taquilla taquilla;
    private SimpleDateFormat formatoFecha;
    private Date fechaActual;
    private Parque parque;
    @Before
    public void setUp() throws Exception {
        cleanUpDataDirectory();
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaEmpleados = new PersistenciaEmpleados();
        persistenciaTiquetes = new PersistenciaTiquetes();
        persistenciaAtracciones = new PersistenciaAtracciones();
        formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        fechaActual = new Date();
        parque = new Parque();

        admin = new Administrador("Admin Test", 999, "admin@test.com", "admin123", parque);


        cliente = new Cliente("Cliente Test", 998, "cliente@test.com", "pass123", 170, 70, 25);

        montanaRusa = new AtraccionMecanica(
            "Montaña Rusa",
            "No operar durante tormentas eléctricas",
            false,
            null,
            null,
            Recurrente.DIAMANTE,
            1,
            "Zona Norte",
            30,
            120,
            200,
            40,
            120,
            "Problemas cardíacos, embarazo",
            "alto"
        );

        casaTerror = new AtraccionCultural(
            "Casa del Terror",
            "",
            false,
            null,
            null,
            Recurrente.ORO,
            1,
            "Zona Este",
            25,
            12
        );

        empleadoAlto = new AtraccionAlto(
            "Atracción Alto",
            "Juan Perez",
            997,
            false,
            "juan@test.com",
            "pass123",
            false,
            true
        );

        cajero = new Cajero(
            "Cajero",
            "Pedro Gomez",
            996,
            false,
            "pedro@test.com",
            "pass123",
            false
        );

        tiqueteBasico = new TiqueteBasico(
            995,
            "Tiquete Test",
            1,
            Recurrente.FAMILIAR,
            fechaActual,
            "Activo",
            "Taquilla",
            "Adulto",
            false
        );

        taquilla = new Taquilla("TAQ99", "Taquilla Test", "Entrada", "Efectivo");

        parque.agregarAtraccion(montanaRusa);
        parque.agregarAtraccion(casaTerror);
        admin.agregarEmpleado(empleadoAlto);
        admin.agregarEmpleado(cajero); 

        persistenciaAtracciones.guardarAtraccionesMecanicas(List.of(montanaRusa));
        persistenciaAtracciones.guardarAtraccionesCulturales(List.of(casaTerror));
        persistenciaEmpleados.guardarEmpleadosAtraccionAlto(List.of(empleadoAlto));
        persistenciaEmpleados.guardarEmpleadosCajero(List.of(cajero));
        persistenciaTiquetes.guardarTiquetesBasicos(List.of(tiqueteBasico));
        persistenciaUsuarios.guardarClientes(List.of(cliente));
        persistenciaUsuarios.guardarAdministradores(List.of(admin));
    }

    private void cleanUpDataDirectory() {
        File dataDir = new File("data");
        if (dataDir.exists()) {
            File[] files = dataDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            dataDir.delete();
        }
    }

    @Test
    public void testIntegracionVentaTiqueteYAccesoAtraccion() throws TiqueteException, AtraccionException {
        cliente.comprarTiquete(tiqueteBasico);
        assertTrue("El cliente debería tener el tiquete", cliente.getTiquetes().contains(tiqueteBasico));

        assertFalse("El tiquete no debería permitir acceso a la casa del terror", 
            tiqueteBasico.puedeAccederAtraccion(casaTerror));
        assertFalse("El tiquete no debería permitir acceso a la montaña rusa", 
            tiqueteBasico.puedeAccederAtraccion(montanaRusa));

        assertTrue("La casa del terror debería estar disponible", 
            casaTerror.estaDisponible(fechaActual));
        assertTrue("El cliente debería cumplir con la restricción de edad", 
            casaTerror.verificarRestriccionEdad(cliente));

        if (tiqueteBasico.puedeAccederAtraccion(casaTerror) && 
            casaTerror.estaDisponible(fechaActual) && 
            casaTerror.verificarRestriccionEdad(cliente)) {
            tiqueteBasico.marcarComoUsado();
        }
        assertFalse("El tiquete no debería estar usado", tiqueteBasico.isUsado());
    }

    @Test
    public void testIntegracionAsignacionEmpleadoAtraccion() throws EmpleadoException, AtraccionException, IOException {
        // Crear instancias nuevas y limpias para el test
        Parque parqueLocal = new Parque();
        Administrador adminLocal = new Administrador(
        	    "Admin Test",
        	    1000,
        	    "adminLocal@test.com",
        	    "pass123",
        	    parqueLocal
        	);
        // Agregar la atracción al parque local
        parqueLocal.agregarAtraccion(montanaRusa);

        // Agregar empleado al admin local
        adminLocal.agregarEmpleado(empleadoAlto);

        // Asignar empleado a atracción en parque local
        parqueLocal.asignarEmpleadoAtraccion(empleadoAlto, montanaRusa, fechaActual, Recurrente.APERTURA);

        // Verificar asignación y condiciones usando adminLocal y parqueLocal
        assertTrue("El empleado debería estar asignado", 
            adminLocal.estaEmpleadoAsignado(empleadoAlto, fechaActual, Recurrente.APERTURA));

        assertEquals("La atracción asignada debería ser la montaña rusa", 
            montanaRusa, adminLocal.obtenerLugarAsignado(empleadoAlto, fechaActual, Recurrente.APERTURA));

        assertTrue("Debería haber personal mínimo", 
            parqueLocal.verificarPersonalMinimo(montanaRusa, fechaActual, Recurrente.APERTURA));

        // Liberar la asignación
        assertTrue("La liberación de la asignación debería ser exitosa", 
            adminLocal.liberarAsignacion(empleadoAlto, fechaActual, Recurrente.APERTURA));

        assertFalse("El empleado no debería estar asignado después de liberar", 
            adminLocal.estaEmpleadoAsignado(empleadoAlto, fechaActual, Recurrente.APERTURA));
    }



    @Test
    public void testIntegracionGestionMantenimientoAtraccion() throws AtraccionException, IOException {
        montanaRusa = new AtraccionMecanica(
            "Montaña Rusa",
            "No operar durante tormentas eléctricas",
            false,
            null,
            null,
            Recurrente.DIAMANTE,
            1,
            "Zona Norte",
            30,
            120,
            200,
            40,
            120,
            "Problemas cardíacos, embarazo",
            "alto"
        );

        parque = new Parque();
        admin = new Administrador("Admin Test", 999, "admin@test.com", "admin123", parque);

        parque.agregarAtraccion(montanaRusa);
        admin.getAtracciones().clear();
        admin.getAtracciones().add(montanaRusa);

        // Verificación inicial
        assertTrue("La atracción debería estar disponible inicialmente", 
            admin.verificarDisponibilidadAtraccion(montanaRusa, fechaActual));

        // Simular mantenimiento
        Date fechaFinMantenimiento = new Date(fechaActual.getTime() + 2 * 24 * 60 * 60 * 1000);
        admin.gestionarMantenimientoAtracciones(montanaRusa, fechaActual, fechaFinMantenimiento);

        assertFalse("La atracción no debería estar disponible durante el mantenimiento", 
            admin.verificarDisponibilidadAtraccion(montanaRusa, fechaActual));

        Date fechaManana = new Date(fechaActual.getTime() + 1 * 24 * 60 * 60 * 1000);
        assertFalse("La atracción no debería estar disponible al día siguiente", 
            admin.verificarDisponibilidadAtraccion(montanaRusa, fechaManana));

        Date fechaDespuesMantenimiento = new Date(fechaFinMantenimiento.getTime() + 1 * 24 * 60 * 60 * 1000);
        assertTrue("La atracción debería estar disponible después del mantenimiento", 
            admin.verificarDisponibilidadAtraccion(montanaRusa, fechaDespuesMantenimiento));
    }

} 