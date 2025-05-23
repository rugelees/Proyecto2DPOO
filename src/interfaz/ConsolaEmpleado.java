package interfaz;

import modelo.usuarios.Cliente;
import modelo.empleados.Empleado;
import modelo.empleados.Regular;
import modelo.empleados.Cajero;
import modelo.tiquetes.TiqueteBasico;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaTiquetes;
import excepciones.EmpleadoException;
import excepciones.UsuarioException;
import excepciones.TiqueteException;
import java.util.Scanner;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import modelo.atracciones.AtraccionMecanica;
import persistencia.PersistenciaAtracciones;

public class ConsolaEmpleado {
    private Empleado empleado;
    private PersistenciaEmpleados persistenciaEmpleados;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaTiquetes persistenciaTiquetes;
    private PersistenciaAtracciones persistenciaAtracciones;
    private Scanner scanner;
    private SimpleDateFormat formatoFecha;

    public ConsolaEmpleado() {
        persistenciaEmpleados = new PersistenciaEmpleados();
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaTiquetes = new PersistenciaTiquetes();
        persistenciaAtracciones = new PersistenciaAtracciones();
        scanner = new Scanner(System.in);
        formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    }

    public void iniciar() {
        if (autenticar()) {
            mostrarMenu();
        } else {
            System.out.println("Autenticación fallida");
        }
    }

    private boolean autenticar() {
        System.out.println("=== Autenticación de Empleado ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();
            for (Empleado emp : empleados) {
                if (emp.getEmail().equals(usuario) && emp.verificarCredenciales(usuario, password)) {
                    empleado = emp;
                    return true;
                }
            }
        } catch (EmpleadoException e) {
            System.out.println("Error de autenticación: " + e.getMessage());
        }
        return false;
    }

    private void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Empleado ===");
            System.out.println("1. Ver Información Personal");
            if (empleado instanceof Cajero) {
                System.out.println("2. Vender Tiquete");
            }
            System.out.println("3. Ver Tiquetes Vendidos");
            System.out.println("4. Gestionar Atracciones de Temporada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    verInformacionPersonal();
                    break;
                case 2:
                    if (empleado instanceof Cajero) {
                        venderTiquete();
                    }
                    break;
                case 3:
                    verTiquetesVendidos();
                    break;
                case 4:
                    gestionarAtraccionesTemporada();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void venderTiquete() {
        if (!(empleado instanceof Cajero)) {
            System.out.println("Solo los cajeros pueden vender tiquetes");
            return;
        }

        System.out.println("\n=== Venta de Tiquete ===");
        System.out.print("Email del cliente: ");
        String emailCliente = scanner.nextLine();

        try {
            Cliente cliente = (Cliente) persistenciaUsuarios.buscarUsuarioPorEmail(emailCliente);
            if (cliente == null) {
                System.out.println("Cliente no encontrado");
                return;
            }

            System.out.println("\nTipos de tiquetes disponibles:");
            System.out.println("1. Tiquete Básico - $50");
            System.out.println("2. Tiquete Premium - $100");
            System.out.println("3. Tiquete VIP - $200");
            System.out.print("Seleccione el tipo de tiquete: ");
            int tipoTiquete = Integer.parseInt(scanner.nextLine());

            String nombreTiquete;
            String categoria;
            String exclusividad;
            switch (tipoTiquete) {
                case 1:
                    nombreTiquete = "Básico";
                    categoria = "BRONCE";
                    exclusividad = "BRONCE";
                    break;
                case 2:
                    nombreTiquete = "Premium";
                    categoria = "PLATA";
                    exclusividad = "PLATA";
                    break;
                case 3:
                    nombreTiquete = "VIP";
                    categoria = "ORO";
                    exclusividad = "ORO";
                    break;
                default:
                    System.out.println("Tipo de tiquete no válido");
                    return;
            }

            System.out.print("Fecha del tiquete (dd-MM-yyyy): ");
            String fechaStr = scanner.nextLine();
            Date fecha = formatoFecha.parse(fechaStr);

            List<TiqueteBasico> tiquetesBasicos = persistenciaTiquetes.cargarTiquetesBasicos();
            int nuevoId = tiquetesBasicos.size() + 1;

            TiqueteBasico tiquete = new TiqueteBasico(
                nuevoId,
                nombreTiquete,
                1,
                exclusividad,
                fecha,
                "Activo",
                "Cajero",
                categoria,
                false
            );

            tiquetesBasicos.add(tiquete);
            persistenciaTiquetes.guardarTiquetesBasicos(tiquetesBasicos);

            System.out.println("Tiquete vendido exitosamente");
            System.out.println("ID del tiquete: " + tiquete.getId());
            System.out.println("Categoría: " + tiquete.getCategoria());

        } catch (UsuarioException | TiqueteException e) {
            System.out.println("Error al vender el tiquete: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void verTiquetesVendidos() {
        System.out.println("\n=== Tiquetes Vendidos ===");
        try {
            List<TiqueteBasico> tiquetes = persistenciaTiquetes.cargarTiquetesBasicos();
            for (TiqueteBasico tiquete : tiquetes) {
                System.out.println("ID: " + tiquete.getId());
                System.out.println("Nombre: " + tiquete.getNombre());
                System.out.println("Fecha: " + formatoFecha.format(tiquete.getFecha()));
                System.out.println("Categoría: " + tiquete.getCategoria());
                System.out.println("Estado: " + tiquete.getEstado());
                System.out.println("-------------------");
            }
        } catch (TiqueteException e) {
            System.out.println("Error al cargar los tiquetes: " + e.getMessage());
        }
    }

    private void verInformacionPersonal() {
        System.out.println("\n=== Información Personal ===");
        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Usuario: " + empleado.getEmail());
        System.out.println("Tipo: " + empleado.getClass().getSimpleName());
        System.out.println("Lugar de trabajo: " + 
            (empleado instanceof Regular ? ((Regular)empleado).getLugarAsignado() != null ? ((Regular)empleado).getLugarAsignado().getNombre() : "No asignado" :
             empleado instanceof Cajero ? ((Cajero)empleado).getLugarAsignado() != null ? ((Cajero)empleado).getLugarAsignado().getNombre() : "No asignado" :
             "No asignado"));
    }

    private void gestionarAtraccionesTemporada() {
        System.out.println("\n=== Gestión de Atracciones de Temporada ===");
        System.out.println("1. Configurar Atracción de Temporada");
        System.out.println("2. Ver Atracciones de Temporada");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                configurarAtraccionTemporada();
                break;
            case 2:
                verAtraccionesTemporada();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void configurarAtraccionTemporada() {
        System.out.println("\nAtracciones mecánicas disponibles:");
        try {
            List<AtraccionMecanica> atracciones = persistenciaAtracciones.cargarAtraccionesMecanicas();
            for (int i = 0; i < atracciones.size(); i++) {
                System.out.println((i + 1) + ". " + atracciones.get(i).getNombre());
            }

            System.out.print("Seleccione la atracción: ");
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            AtraccionMecanica atraccion = atracciones.get(indice);

            System.out.print("¿Hacer de temporada? (S/N): ");
            boolean deTemporada = scanner.nextLine().equalsIgnoreCase("S");

            if (deTemporada) {
                System.out.print("Fecha de inicio (dd/MM/yyyy): ");
                String fechaInicioStr = scanner.nextLine();
                Date fechaInicio = formatoFecha.parse(fechaInicioStr);

                System.out.print("Fecha de fin (dd/MM/yyyy): ");
                String fechaFinStr = scanner.nextLine();
                Date fechaFin = formatoFecha.parse(fechaFinStr);

                atraccion.setDeTemporada(true);
                atraccion.setFechaInicioTemporada(fechaInicio);
                atraccion.setFechaFinTemporada(fechaFin);
            } else {
                atraccion.setDeTemporada(false);
                atraccion.setFechaInicioTemporada(null);
                atraccion.setFechaFinTemporada(null);
            }

            persistenciaAtracciones.guardarAtraccionesMecanicas(atracciones);
            System.out.println("Configuración guardada exitosamente");

        } catch (Exception e) {
            System.out.println("Error al configurar atracción: " + e.getMessage());
        }
    }

    private void verAtraccionesTemporada() {
        System.out.println("\nAtracciones de temporada:");
        try {
            List<AtraccionMecanica> atracciones = persistenciaAtracciones.cargarAtraccionesMecanicas();
            boolean hayAtraccionesTemporada = false;

            for (AtraccionMecanica atraccion : atracciones) {
                if (atraccion.estaDisponible(new Date())) {
                    hayAtraccionesTemporada = true;
                    System.out.println("\nAtracción: " + atraccion.getNombre());
                    System.out.println("Fecha inicio: " + formatoFecha.format(atraccion.getFechaInicioTemporada()));
                    System.out.println("Fecha fin: " + formatoFecha.format(atraccion.getFechaFinTemporada()));
                }

            }

            if (!hayAtraccionesTemporada) {
                System.out.println("No hay atracciones de temporada activas");
            }

        } catch (Exception e) {
            System.out.println("Error al cargar atracciones: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ConsolaEmpleado consola = new ConsolaEmpleado();
        consola.iniciar();
    }
}