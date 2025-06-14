package tests;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Principal.Parque;
import excepciones.AtraccionException;
import excepciones.EmpleadoException;
import excepciones.TiqueteException;
import excepciones.UsuarioException;
import modelo.atracciones.AtraccionCultural;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.Cajero;
import modelo.lugares.Tienda;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import persistencia.GuardarCargar;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaTiquetes;
import persistencia.PersistenciaUsuarios;

public class TestPersistencia {
    
    private PersistenciaAtracciones persistenciaAtracciones;
    private PersistenciaEmpleados persistenciaEmpleados;
    private PersistenciaTiquetes persistenciaTiquetes;
    private PersistenciaUsuarios persistenciaUsuarios;
    
    private AtraccionMecanica atraccionMecanica;
    private AtraccionCultural atraccionCultural;
    private Espectaculo espectaculo;
    
    private AtraccionAlto empleadoAtraccionAlto;
    private Cajero empleadoCajero;
    
    private TiqueteBasico tiqueteBasico;
    
    private Cliente cliente;
    private Administrador admin;
    private Parque parque;

    
    @Before
    public void setUp() throws IOException {
        persistenciaAtracciones = new PersistenciaAtracciones();
        persistenciaEmpleados = new PersistenciaEmpleados();
        persistenciaTiquetes = new PersistenciaTiquetes();
        persistenciaUsuarios = new PersistenciaUsuarios();
        parque = new Parque();

        admin = new Administrador("Admin Test", 999, "admin@test.com", "admin123", parque);
        
        Date fechaActual = new Date();
        
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
        
        empleadoCajero = new Cajero(
            "Cajero",
            "Pedro Gómez",
            3,
            false,
            "pedro@ejemplo.com",
            "clave123",
            false
        );
        
        tiqueteBasico = new TiqueteBasico(1, "Tiquete Básico", 1, "Familiar", fechaActual, "Activo", "Taquilla", "Adulto", false);
        
        cliente = new Cliente("Juan Cliente", 101, "cliente@ejemplo.com", "clave123", 170.0f, 70.0f, 25);
        admin = new Administrador("Admin Principal", 1000, "admin@parque.com", "admin123", parque);
    }
    
    @After
    public void tearDown() {
    	GuardarCargar.eliminarArchivo("atracciones_mecanicas.txt");
    	GuardarCargar.eliminarArchivo("atracciones_culturales.txt");
    	GuardarCargar.eliminarArchivo("espectaculos.txt");
    	GuardarCargar.eliminarArchivo("empleados_atraccion_alto.txt");
    	GuardarCargar.eliminarArchivo("empleados_cajero.txt");
    	GuardarCargar.eliminarArchivo("tiquetes_basicos.txt");
    	GuardarCargar.eliminarArchivo("clientes.txt");
    	GuardarCargar.eliminarArchivo("administradores.txt");
    }
    
    @Test
    public void testPersistenciaAtracciones() {
        try {
            List<AtraccionMecanica> atraccionesMecanicas = new ArrayList<>();
            atraccionesMecanicas.add(atraccionMecanica);
            persistenciaAtracciones.guardarAtraccionesMecanicas(atraccionesMecanicas);
            
            List<AtraccionCultural> atraccionesCulturales = new ArrayList<>();
            atraccionesCulturales.add(atraccionCultural);
            persistenciaAtracciones.guardarAtraccionesCulturales(atraccionesCulturales);
            
            List<Espectaculo> espectaculos = new ArrayList<>();
            espectaculos.add(espectaculo);
            persistenciaAtracciones.guardarEspectaculos(espectaculos);
            
            assertTrue(GuardarCargar.existeArchivo("atracciones_mecanicas.txt"));
            assertTrue(GuardarCargar.existeArchivo("atracciones_culturales.txt"));
            assertTrue(GuardarCargar.existeArchivo("espectaculos.txt"));
            
            List<AtraccionMecanica> atraccionesMecanicasCargadas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            List<AtraccionCultural> atraccionesCulturalesCargadas = persistenciaAtracciones.cargarAtraccionesCulturales();
            List<Espectaculo> espectaculosCargados = persistenciaAtracciones.cargarEspectaculos();
            
            assertEquals(1, atraccionesMecanicasCargadas.size());
            assertEquals(1, atraccionesCulturalesCargadas.size());
            assertEquals(1, espectaculosCargados.size());
            
            AtraccionMecanica atraccionCargada = atraccionesMecanicasCargadas.get(0);
            assertEquals(atraccionMecanica.getNombre(), atraccionCargada.getNombre());
            assertEquals(atraccionMecanica.getNivelExclusividad(), atraccionCargada.getNivelExclusividad());
            assertEquals(atraccionMecanica.getNivelRiesgo(), atraccionCargada.getNivelRiesgo());
            
            AtraccionCultural culturalCargada = atraccionesCulturalesCargadas.get(0);
            assertEquals(atraccionCultural.getNombre(), culturalCargada.getNombre());
            assertEquals(atraccionCultural.getEdadMinima(), culturalCargada.getEdadMinima());
            
            Espectaculo espectaculoCargado = espectaculosCargados.get(0);
            assertEquals(espectaculo.getNombre(), espectaculoCargado.getNombre());
            assertEquals(espectaculo.getDuracion(), espectaculoCargado.getDuracion());
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaEmpleados() {
        try {
            List<AtraccionAlto> empleadosAtraccionAlto = new ArrayList<>();
            empleadosAtraccionAlto.add(empleadoAtraccionAlto);
            persistenciaEmpleados.guardarEmpleadosAtraccionAlto(empleadosAtraccionAlto);
            
            List<Cajero> empleadosCajero = new ArrayList<>();
            empleadosCajero.add(empleadoCajero);
            persistenciaEmpleados.guardarEmpleadosCajero(empleadosCajero);
            
            assertTrue(GuardarCargar.existeArchivo("empleados_atraccion_alto.txt"));
            assertTrue(GuardarCargar.existeArchivo("empleados_cajero.txt"));
            
            List<AtraccionAlto> empleadosAtraccionAltoCargados = persistenciaEmpleados.cargarEmpleadosAtraccionAlto();
            List<Cajero> empleadosCajeroCargados = persistenciaEmpleados.cargarEmpleadosCajero();
            
            assertEquals(1, empleadosAtraccionAltoCargados.size());
            assertEquals(1, empleadosCajeroCargados.size());
            
            AtraccionAlto empleadoAltoCargado = empleadosAtraccionAltoCargados.get(0);
            assertEquals(empleadoAtraccionAlto.getNombre(), empleadoAltoCargado.getNombre());
            assertEquals(empleadoAtraccionAlto.getId(), empleadoAltoCargado.getId());
            assertEquals(empleadoAtraccionAlto.getEmail(), empleadoAltoCargado.getEmail());
            assertEquals(empleadoAtraccionAlto.isCapacitado(), empleadoAltoCargado.isCapacitado());
            
            Cajero empleadoCajeroCargado = empleadosCajeroCargados.get(0);
            assertEquals(empleadoCajero.getNombre(), empleadoCajeroCargado.getNombre());
            assertEquals(empleadoCajero.getId(), empleadoCajeroCargado.getId());
            assertEquals(empleadoCajero.getEmail(), empleadoCajeroCargado.getEmail());
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaEmpleadosMultiples() {
        try {
            List<AtraccionAlto> empleadosAtraccionAlto = new ArrayList<>();
            empleadosAtraccionAlto.add(empleadoAtraccionAlto);
            empleadosAtraccionAlto.add(new AtraccionAlto(
                "Operador de atracción alta",
                "María López",
                2,
                false,
                "maria@ejemplo.com",
                "clave456",
                true,
                true
            ));
            persistenciaEmpleados.guardarEmpleadosAtraccionAlto(empleadosAtraccionAlto);
            
            List<Cajero> empleadosCajero = new ArrayList<>();
            empleadosCajero.add(empleadoCajero);
            empleadosCajero.add(new Cajero(
                "Cajero",
                "Ana Martínez",
                4,
                false,
                "ana@ejemplo.com",
                "clave789",
                true
            ));
            persistenciaEmpleados.guardarEmpleadosCajero(empleadosCajero);
            
            List<AtraccionAlto> empleadosAtraccionAltoCargados = persistenciaEmpleados.cargarEmpleadosAtraccionAlto();
            List<Cajero> empleadosCajeroCargados = persistenciaEmpleados.cargarEmpleadosCajero();
            
            assertEquals(2, empleadosAtraccionAltoCargados.size());
            assertEquals(2, empleadosCajeroCargados.size());
            
            assertEquals("Juan Pérez", empleadosAtraccionAltoCargados.get(0).getNombre());
            assertEquals("María López", empleadosAtraccionAltoCargados.get(1).getNombre());
            assertEquals("Pedro Gómez", empleadosCajeroCargados.get(0).getNombre());
            assertEquals("Ana Martínez", empleadosCajeroCargados.get(1).getNombre());
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaEmpleadosActualizar() {
        try {
            
            List<AtraccionAlto> empleadosIniciales = new ArrayList<>();
            empleadosIniciales.add(empleadoAtraccionAlto);
            persistenciaEmpleados.guardarEmpleadosAtraccionAlto(empleadosIniciales);
            empleadoAtraccionAlto.setCapacitado(true);
            persistenciaEmpleados.guardarEmpleadosAtraccionAlto(empleadosIniciales);
            List<AtraccionAlto> empleadosCargados = persistenciaEmpleados.cargarEmpleadosAtraccionAlto();
            assertEquals(1, empleadosCargados.size());
            assertTrue(empleadosCargados.get(0).isCapacitado());
            
        } catch (EmpleadoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaUsuarios() {
        try {
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente);
            persistenciaUsuarios.guardarClientes(clientes);
            
            List<Administrador> administradores = new ArrayList<>();
            administradores.add(admin);
            persistenciaUsuarios.guardarAdministradores(administradores);
            
            assertTrue(GuardarCargar.existeArchivo("clientes.txt"));
            assertTrue(GuardarCargar.existeArchivo("administradores.txt"));
            
            List<Cliente> clientesCargados = persistenciaUsuarios.cargarClientes();
            List<Administrador> administradoresCargados = persistenciaUsuarios.cargarAdministradores();
            
            assertEquals(1, clientesCargados.size());
            assertEquals(1, administradoresCargados.size());
            
            Cliente clienteCargado = clientesCargados.get(0);
            assertEquals(cliente.getNombre(), clienteCargado.getNombre());
            assertEquals(cliente.getId(), clienteCargado.getId());
            assertEquals(cliente.getEmail(), clienteCargado.getEmail());
            assertEquals(cliente.getEdad(), clienteCargado.getEdad());
            
            Administrador adminCargado = administradoresCargados.get(0);
            assertEquals(admin.getNombre(), adminCargado.getNombre());
            assertEquals(admin.getId(), adminCargado.getId());
            assertEquals(admin.getEmail(), adminCargado.getEmail());
            
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testBuscarUsuarioPorEmail() {
        try {
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente);
            persistenciaUsuarios.guardarClientes(clientes);
            
            List<Administrador> administradores = new ArrayList<>();
            administradores.add(admin);
            persistenciaUsuarios.guardarAdministradores(administradores);
            
            assertEquals(cliente.getEmail(), persistenciaUsuarios.buscarUsuarioPorEmail("cliente@ejemplo.com").getEmail());
            assertEquals(admin.getEmail(), persistenciaUsuarios.buscarUsuarioPorEmail("admin@parque.com").getEmail());
            assertNull(persistenciaUsuarios.buscarUsuarioPorEmail("otro@ejemplo.com"));
            
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testAutenticarUsuario() {
        try {
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente);
            persistenciaUsuarios.guardarClientes(clientes);
            
            assertNotNull(persistenciaUsuarios.autenticarUsuario("cliente@ejemplo.com", "clave123"));
            
            assertNull(persistenciaUsuarios.autenticarUsuario("cliente@ejemplo.com", "claveIncorrecta"));
            assertNull(persistenciaUsuarios.autenticarUsuario("otro@ejemplo.com", "clave123"));
            
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaCargarTodasAtracciones() {
        try {
            List<AtraccionMecanica> atraccionesMecanicas = new ArrayList<>();
            atraccionesMecanicas.add(atraccionMecanica);
            persistenciaAtracciones.guardarAtraccionesMecanicas(atraccionesMecanicas);
            
            List<AtraccionCultural> atraccionesCulturales = new ArrayList<>();
            atraccionesCulturales.add(atraccionCultural);
            persistenciaAtracciones.guardarAtraccionesCulturales(atraccionesCulturales);
            
            List<modelo.atracciones.Atraccion> todasAtracciones = persistenciaAtracciones.cargarTodasAtracciones();
            
            assertEquals(2, todasAtracciones.size());
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testArchivoUtil() {
        try {
            String contenido = "Este es un contenido de prueba";
            GuardarCargar.guardarTexto(contenido, "prueba.txt");
            
            assertTrue(GuardarCargar.existeArchivo("prueba.txt"));
            
            String contenidoCargado = GuardarCargar.cargarTexto("prueba.txt");
            assertEquals(contenido + "\n", contenidoCargado);
            
            List<String> lineas = new ArrayList<>();
            lineas.add("Línea 1");
            lineas.add("Línea 2");
            GuardarCargar.guardarLineas(lineas, "prueba_lineas.txt", true);
            
            List<String> lineasCargadas = GuardarCargar.cargarLineas("prueba_lineas.txt");
            assertEquals(2, lineasCargadas.size());
            assertEquals("Línea 1", lineasCargadas.get(0));
            assertEquals("Línea 2", lineasCargadas.get(1));
            
            assertTrue(GuardarCargar.eliminarArchivo("prueba.txt"));
            assertTrue(GuardarCargar.eliminarArchivo("prueba_lineas.txt"));
            
            assertFalse(GuardarCargar.existeArchivo("prueba.txt"));
            assertFalse(GuardarCargar.existeArchivo("prueba_lineas.txt"));
            
        } catch (IOException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testCargarAtraccionesArchivoVacio() throws AtraccionException {
        try {
            GuardarCargar.guardarLineas(new ArrayList<>(), "atracciones_mecanicas.txt", true);
            GuardarCargar.guardarLineas(new ArrayList<>(), "atracciones_culturales.txt", true);
            GuardarCargar.guardarLineas(new ArrayList<>(), "espectaculos.txt", true);
        } catch (IOException e) {
            fail("No debería lanzar excepción al crear archivos vacíos");
        }
        List<AtraccionMecanica> atraccionesMecanicas = persistenciaAtracciones.cargarAtraccionesMecanicas();
        List<AtraccionCultural> atraccionesCulturales = persistenciaAtracciones.cargarAtraccionesCulturales();
        List<Espectaculo> espectaculos = persistenciaAtracciones.cargarEspectaculos();
        
        assertTrue(atraccionesMecanicas.isEmpty());
        assertTrue(atraccionesCulturales.isEmpty());
        assertTrue(espectaculos.isEmpty());
    }
    
    @Test
    public void testCargarAtraccionesArchivoNoExiste() throws AtraccionException {
        List<AtraccionMecanica> atraccionesMecanicas = persistenciaAtracciones.cargarAtraccionesMecanicas();
        List<AtraccionCultural> atraccionesCulturales = persistenciaAtracciones.cargarAtraccionesCulturales();
        List<Espectaculo> espectaculos = persistenciaAtracciones.cargarEspectaculos();
        
        assertTrue(atraccionesMecanicas.isEmpty());
        assertTrue(atraccionesCulturales.isEmpty());
        assertTrue(espectaculos.isEmpty());
    }

    @Test
    public void testGuardarAtraccionesConFechasInvalidas() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa",
            "No operar en caso de tormenta eléctrica",
            true,
            new Date(System.currentTimeMillis() + 86400000), 
            new Date(System.currentTimeMillis() - 86400000), 
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
        
        List<AtraccionMecanica> atracciones = new ArrayList<>();
        atracciones.add(atraccion);
        
        try {
            persistenciaAtracciones.guardarAtraccionesMecanicas(atracciones);
            List<AtraccionMecanica> atraccionesCargadas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            assertEquals(1, atraccionesCargadas.size());
            assertEquals(atraccion.getNombre(), atraccionesCargadas.get(0).getNombre());
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción con fechas inválidas");
        }
    }
    
    @Test
    public void testGuardarCargarEspectaculoConFunciones() {
        Date fechaActual = new Date();
        Espectaculo espectaculo = new Espectaculo(
            "Show de Delfines",
            "No realizar en caso de lluvia",
            true,
            fechaActual,
            new Date(fechaActual.getTime() + 30L * 24 * 60 * 60 * 1000),
            "45 minutos",
            "15:00",
            100
        );
        
        Date funcion1 = new Date(fechaActual.getTime());
        Date funcion2 = new Date(fechaActual.getTime() + 86400000);
        espectaculo.agregarFuncion(funcion1);
        espectaculo.agregarFuncion(funcion2);
        
        List<Espectaculo> espectaculos = new ArrayList<>();
        espectaculos.add(espectaculo);
        
        try {
            persistenciaAtracciones.guardarEspectaculos(espectaculos);
            List<Espectaculo> espectaculosCargados = persistenciaAtracciones.cargarEspectaculos();
            
            assertEquals(1, espectaculosCargados.size());
            Espectaculo espectaculoCargado = espectaculosCargados.get(0);
            assertEquals(2, espectaculoCargado.getFunciones().size());
            List<Date> funcionesCargadas = espectaculoCargado.getFunciones();
            assertTrue(espectaculoCargado.estaDisponible(funcion1));
            assertFalse(espectaculoCargado.estaDisponible(funcion2));
            Date fechaNoDisponible = new Date(fechaActual.getTime() + 172800000); 
            assertFalse(espectaculoCargado.estaDisponible(fechaNoDisponible));
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción al guardar/cargar espectáculo con funciones: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaTiquetes() {
        try {
            List<TiqueteBasico> tiquetesBasicos = new ArrayList<>();
            tiquetesBasicos.add(tiqueteBasico);
            persistenciaTiquetes.guardarTiquetesBasicos(tiquetesBasicos);
            
            assertTrue(GuardarCargar.existeArchivo("tiquetes_basicos.txt"));
            
            List<TiqueteBasico> tiquetesBasicosCargados = persistenciaTiquetes.cargarTiquetesBasicos();
            
            assertEquals(1, tiquetesBasicosCargados.size());
            
            TiqueteBasico tiqueteCargado = tiquetesBasicosCargados.get(0);
            assertEquals(tiqueteBasico.getId(), tiqueteCargado.getId());
            assertEquals(tiqueteBasico.getNombre(), tiqueteCargado.getNombre());
            assertEquals(tiqueteBasico.getExclusividad(), tiqueteCargado.getExclusividad());
            assertEquals(tiqueteBasico.getCategoria(), tiqueteCargado.getCategoria());
            assertEquals(tiqueteBasico.getEstado(), tiqueteCargado.getEstado());
            assertEquals(tiqueteBasico.isUsado(), tiqueteCargado.isUsado());
            
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testPersistenciaTiquetesActualizar() {
        try {
            List<TiqueteBasico> tiquetesIniciales = new ArrayList<>();
            tiquetesIniciales.add(tiqueteBasico);
            persistenciaTiquetes.guardarTiquetesBasicos(tiquetesIniciales);
            tiqueteBasico.setEstado("Usado");
            tiqueteBasico.setUsado(true);
            persistenciaTiquetes.guardarTiquetesBasicos(tiquetesIniciales);
            List<TiqueteBasico> tiquetesCargados = persistenciaTiquetes.cargarTiquetesBasicos();
            assertEquals(1, tiquetesCargados.size());
            assertEquals("Usado", tiquetesCargados.get(0).getEstado());
            assertTrue(tiquetesCargados.get(0).isUsado());
            
        } catch (TiqueteException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
    
    @Test
    public void testGuardarCargarClientes() {
        Cliente cliente = new Cliente(
            "Juan Cliente",
            101,
            "cliente@ejemplo.com",
            "clave123",
            170.0f,
            70.0f,
            25
        );
        
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        
        try {
            persistenciaUsuarios.guardarClientes(clientes);
            List<Cliente> clientesCargados = persistenciaUsuarios.cargarClientes();
            
            assertEquals(1, clientesCargados.size());
            assertEquals(cliente.getNombre(), clientesCargados.get(0).getNombre());
            assertEquals(cliente.getId(), clientesCargados.get(0).getId());
            assertEquals(cliente.getEmail(), clientesCargados.get(0).getEmail());
            assertEquals(cliente.getEdad(), clientesCargados.get(0).getEdad());
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción al guardar/cargar cliente");
        }
    }
    
    @Test
    public void testGuardarCargarAdministradores() {
        Administrador admin = new Administrador(
            "Admin Principal",
            1000,
            "admin@parque.com",
            "admin123", parque
        );
        
        List<Administrador> administradores = new ArrayList<>();
        administradores.add(admin);
        
        try {
            persistenciaUsuarios.guardarAdministradores(administradores);
            List<Administrador> administradoresCargados = persistenciaUsuarios.cargarAdministradores();
            
            assertEquals(1, administradoresCargados.size());
            assertEquals(admin.getNombre(), administradoresCargados.get(0).getNombre());
            assertEquals(admin.getId(), administradoresCargados.get(0).getId());
            assertEquals(admin.getEmail(), administradoresCargados.get(0).getEmail());
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción al guardar/cargar administrador");
        }
    }
    
    @Test
    public void testCargarUsuariosArchivoVacio() throws UsuarioException {
        try {
            GuardarCargar.guardarLineas(new ArrayList<>(), "clientes.txt", true);
            GuardarCargar.guardarLineas(new ArrayList<>(), "administradores.txt", true);
        } catch (IOException e) {
            fail("No debería lanzar excepción al crear archivos vacíos");
        }
        List<Cliente> clientes = persistenciaUsuarios.cargarClientes();
        List<Administrador> administradores = persistenciaUsuarios.cargarAdministradores();
        
        assertTrue(clientes.isEmpty());
        assertTrue(administradores.isEmpty());
    }
    
    @Test
    public void testGuardarUsuariosConDatosInvalidos() {
        Cliente cliente = new Cliente(
            "", 
            -1, 
            "email_invalido", 
            "", 
            -1.0f,
            -1.0f, 
            -1 
        );
        
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        
        try {
            persistenciaUsuarios.guardarClientes(clientes);
            List<Cliente> clientesCargados = persistenciaUsuarios.cargarClientes();
            
            assertEquals(1, clientesCargados.size());
            assertEquals(cliente.getNombre(), clientesCargados.get(0).getNombre());
            assertEquals(cliente.getId(), clientesCargados.get(0).getId());
            assertEquals(cliente.getEmail(), clientesCargados.get(0).getEmail());
            assertEquals(cliente.getEdad(), clientesCargados.get(0).getEdad());
        } catch (UsuarioException e) {
            fail("No debería lanzar excepción con datos inválidos");
        }
    }
    
    @Test
    public void testBuscarUsuarioPorEmailNoExiste() throws UsuarioException {
        assertNull(persistenciaUsuarios.buscarUsuarioPorEmail("no_existe@ejemplo.com"));
    }
    
    @Test
    public void testAutenticarUsuarioNoExiste() throws UsuarioException {
        assertNull(persistenciaUsuarios.autenticarUsuario("no_existe@ejemplo.com", "clave123"));
    }
    
    @Test
    public void testAutenticarUsuarioClaveIncorrecta() throws UsuarioException {
        Cliente cliente = new Cliente(
            "Juan Cliente",
            101,
            "cliente@ejemplo.com",
            "clave123",
            170.0f,
            70.0f,
            25
        );
        
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        
        persistenciaUsuarios.guardarClientes(clientes);
        
        assertNull(persistenciaUsuarios.autenticarUsuario("cliente@ejemplo.com", "clave_incorrecta"));
    }

    @Test
    public void testEstaDisponible() {
        try {
            Date fechaActual = new Date();
            Date fechaInicio = new Date(fechaActual.getTime() - 86400000);
            Date fechaFin = new Date(fechaActual.getTime() + 86400000); 
            
            AtraccionMecanica atraccion = new AtraccionMecanica(
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
            
            List<AtraccionMecanica> atracciones = new ArrayList<>();
            atracciones.add(atraccion);
            persistenciaAtracciones.guardarAtraccionesMecanicas(atracciones);
            
            List<AtraccionMecanica> atraccionesCargadas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            assertEquals(1, atraccionesCargadas.size());
            
            AtraccionMecanica atraccionCargada = atraccionesCargadas.get(0);
            assertTrue(atraccionCargada.estaDisponible(fechaActual));
            atraccion.setDeTemporada(true);
            atraccion.setFechaInicio(new Date(fechaActual.getTime() + 86400000)); 
            atraccion.setFechaFin(new Date(fechaActual.getTime() + 172800000)); 
            persistenciaAtracciones.guardarAtraccionesMecanicas(atracciones);
            
            atraccionesCargadas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            atraccionCargada = atraccionesCargadas.get(0);
            assertFalse(atraccionCargada.estaDisponible(fechaActual));
            
        } catch (AtraccionException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
