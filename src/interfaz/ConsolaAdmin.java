package interfaz;

import modelo.usuarios.*;
import modelo.empleados.*;
import modelo.atracciones.*;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaEmpleados;
import excepciones.UsuarioException;
import excepciones.EmpleadoException;
import excepciones.AtraccionException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ConsolaAdmin {
    private Administrador admin;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaAtracciones persistenciaAtracciones;
    private PersistenciaEmpleados persistenciaEmpleados;
    private Scanner scanner;

    public ConsolaAdmin() {
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaAtracciones = new PersistenciaAtracciones();
        persistenciaEmpleados = new PersistenciaEmpleados();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        if (autenticar()) {
            mostrarMenu();
        } else {
            System.out.println("Autenticación fallida");
        }
    }

    private boolean autenticar() {
        System.out.println("=== Autenticación de Administrador ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            admin = (Administrador) persistenciaUsuarios.autenticarUsuario(usuario, password);
            return admin != null;
        } catch (UsuarioException e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            return false;
        }
    }

    private void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Gestionar Empleados");
            System.out.println("2. Gestionar Atracciones");
            System.out.println("3. Ver Reportes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    gestionarEmpleados();
                    break;
                case 2:
                    gestionarAtracciones();
                    break;
                case 3:
                    verReportes();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void gestionarEmpleados() {
        System.out.println("\n=== Gestión de Empleados ===");
        System.out.println("1. Agregar Empleado");
        System.out.println("2. Asignar Lugar de Trabajo");
        System.out.println("3. Ver Empleados");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                agregarEmpleado();
                break;
            case 2:
                asignarLugarTrabajo();
                break;
            case 3:
                verEmpleados();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void agregarEmpleado() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Tipo (Cajero/Cocinero/EmpleadoTienda): ");
        String tipo = scanner.nextLine();

        Empleado empleado = null;
        switch (tipo.toLowerCase()) {
            case "cajero":
                empleado = new Cajero("Cajero", nombre, 0, false, usuario, password, false);
                break;
            case "cocinero":
                empleado = new Cocinero(true, "Cocinero", nombre, 0, false, usuario, password, false);
                break;
            case "empleadotienda":
                empleado = new Regular(true, "EmpleadoTienda", nombre, 0, false, usuario, password, false);
                break;
        }

        if (empleado != null) {
            try {
                if (empleado instanceof Cajero) {
                    List<Cajero> cajeros = new ArrayList<>();
                    cajeros.add((Cajero) empleado);
                    persistenciaEmpleados.guardarEmpleadosCajero(cajeros);
                } else if (empleado instanceof Cocinero) {
                    List<Cocinero> cocineros = new ArrayList<>();
                    cocineros.add((Cocinero) empleado);
                    persistenciaEmpleados.guardarEmpleadosCocinero(cocineros);
                } else if (empleado instanceof Regular) {
                    List<Regular> regulares = new ArrayList<>();
                    regulares.add((Regular) empleado);
                    persistenciaEmpleados.guardarEmpleadosRegular(regulares);
                }
                System.out.println("Empleado agregado exitosamente");
            } catch (EmpleadoException e) {
                System.out.println("Error al guardar el empleado: " + e.getMessage());
            }
        }
    }

    private void asignarLugarTrabajo() {
        System.out.print("Usuario del empleado: ");
        String usuario = scanner.nextLine();
        System.out.print("Nombre del lugar de trabajo: ");
        String lugar = scanner.nextLine();

        try {
            Empleado empleado = (Empleado) persistenciaUsuarios.buscarUsuarioPorEmail(usuario);
            if (empleado != null) {
                admin.asignarEmpleadoAtraccion(empleado, null, new Date(), lugar);
                System.out.println("Lugar de trabajo asignado exitosamente");
            } else {
                System.out.println("Empleado no encontrado");
            }
        } catch (UsuarioException | EmpleadoException e) {
            System.out.println("Error al asignar lugar de trabajo: " + e.getMessage());
        }
    }

    private void verEmpleados() {
        System.out.println("\n=== Lista de Empleados ===");
        try {
            for (Usuario usuario : persistenciaUsuarios.cargarTodosUsuarios()) {
                if (usuario instanceof Empleado) {
                    Empleado emp = (Empleado) usuario;
                    System.out.println("Nombre: " + emp.getNombre());
                    System.out.println("Usuario: " + emp.getEmail());
                    System.out.println("Tipo: " + emp.getClass().getSimpleName());
                    System.out.println("Lugar de trabajo: " + 
                        (emp instanceof Regular ? ((Regular)emp).getLugarAsignado() != null ? ((Regular)emp).getLugarAsignado().getNombre() : "No asignado" :
                         emp instanceof Cajero ? ((Cajero)emp).getLugarAsignado() != null ? ((Cajero)emp).getLugarAsignado().getNombre() : "No asignado" :
                         "No asignado"));
                    System.out.println("-------------------");
                }
            }
        } catch (UsuarioException e) {
            System.out.println("Error al cargar los empleados: " + e.getMessage());
        }
    }

    private void gestionarAtracciones() {
        System.out.println("\n=== Gestión de Atracciones ===");
        System.out.println("1. Agregar Atracción");
        System.out.println("2. Asignar Empleado");
        System.out.println("3. Ver Atracciones");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                agregarAtraccion();
                break;
            case 2:
                asignarEmpleadoAtraccion();
                break;
            case 3:
                verAtracciones();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void agregarAtraccion() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo (Mecanica/Cultural): ");
        String tipo = scanner.nextLine();
        System.out.print("Capacidad: ");
        int capacidad = Integer.parseInt(scanner.nextLine());

        try {
            if (tipo.equalsIgnoreCase("mecanica")) {
                AtraccionMecanica atraccion = new AtraccionMecanica(
                    nombre, "Sin restricciones", false, null, null,
                    "DIAMANTE", 3, "Zona Norte", capacidad, 120.0f, 200.0f, 40.0f, 120.0f,
                    "Sin restricciones", "alto");
                List<AtraccionMecanica> atracciones = new ArrayList<>();
                atracciones.add(atraccion);
                persistenciaAtracciones.guardarAtraccionesMecanicas(atracciones);
            } else if (tipo.equalsIgnoreCase("cultural")) {
                AtraccionCultural atraccion = new AtraccionCultural(
                    nombre, "Sin restricciones", false, null, null,
                    "ORO", 2, "Zona Este", capacidad, 12);
                List<AtraccionCultural> atracciones = new ArrayList<>();
                atracciones.add(atraccion);
                persistenciaAtracciones.guardarAtraccionesCulturales(atracciones);
            }
            System.out.println("Atracción agregada exitosamente");
        } catch (AtraccionException e) {
            System.out.println("Error al agregar atracción: " + e.getMessage());
        }
    }

    private void asignarEmpleadoAtraccion() {
        System.out.print("Nombre de la atracción: ");
        String nombreAtraccion = scanner.nextLine();
        System.out.print("Usuario del empleado: ");
        String usuario = scanner.nextLine();

        try {
            List<AtraccionMecanica> atraccionesMecanicas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            AtraccionMecanica atraccion = null;
            for (AtraccionMecanica a : atraccionesMecanicas) {
                if (a.getNombre().equals(nombreAtraccion)) {
                    atraccion = a;
                    break;
                }
            }
            Empleado empleado = (Empleado) persistenciaUsuarios.buscarUsuarioPorEmail(usuario);

            if (atraccion != null && empleado != null) {
                admin.asignarEmpleadoAtraccion(empleado, atraccion, new Date(), "mañana");
                System.out.println("Empleado asignado exitosamente");
            } else {
                System.out.println("Atracción o empleado no encontrado");
            }
        } catch (UsuarioException | EmpleadoException | AtraccionException e) {
            System.out.println("Error al asignar empleado: " + e.getMessage());
        }
    }

    private void verAtracciones() {
        System.out.println("\n=== Lista de Atracciones ===");
        try {
            List<AtraccionMecanica> atraccionesMecanicas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            List<AtraccionCultural> atraccionesCulturales = persistenciaAtracciones.cargarAtraccionesCulturales();
            
            for (AtraccionMecanica atraccion : atraccionesMecanicas) {
                System.out.println("Nombre: " + atraccion.getNombre());
                System.out.println("Tipo: " + atraccion.getClass().getSimpleName());
                System.out.println("Capacidad: " + atraccion.getCupoMaximo());
                System.out.println("Empleado asignado: " + 
                    (atraccion.getEmpleadosEncargados() > 0 ? "Asignado" : "No asignado"));
                System.out.println("-------------------");
            }
            
            for (AtraccionCultural atraccion : atraccionesCulturales) {
                System.out.println("Nombre: " + atraccion.getNombre());
                System.out.println("Tipo: " + atraccion.getClass().getSimpleName());
                System.out.println("Capacidad: " + atraccion.getCupoMaximo());
                System.out.println("Empleado asignado: " + 
                    (atraccion.getEmpleadosEncargados() > 0 ? "Asignado" : "No asignado"));
                System.out.println("-------------------");
            }
        } catch (AtraccionException e) {
            System.out.println("Error al cargar atracciones: " + e.getMessage());
        }
    }

    private void verReportes() {
        System.out.println("\n=== Reportes ===");
        System.out.println("1. Ventas por día");
        System.out.println("2. Empleados por lugar");
        System.out.println("3. Atracciones más populares");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                // Implementar reporte de ventas
                break;
            case 2:
                // Implementar reporte de empleados
                break;
            case 3:
                // Implementar reporte de atracciones
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    public static void main(String[] args) {
        ConsolaAdmin consola = new ConsolaAdmin();
        consola.iniciar();
    }
} 