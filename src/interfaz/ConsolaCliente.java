package interfaz;

import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import modelo.atracciones.Atraccion;
import modelo.tiquetes.Tiquete;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaTiquetes;
import java.util.Scanner;
import java.util.List;

public class ConsolaCliente {
    private Cliente cliente;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaAtracciones persistenciaAtracciones;
    private PersistenciaTiquetes persistenciaTiquetes;
    private Scanner scanner;

    public ConsolaCliente() {
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaAtracciones = new PersistenciaAtracciones();
        persistenciaTiquetes = new PersistenciaTiquetes();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== Bienvenido al Parque de Atracciones ===");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                if (autenticar()) {
                    mostrarMenu();
                } else {
                    System.out.println("Autenticación fallida");
                }
                break;
            case 2:
                if (registrar()) {
                    mostrarMenu();
                } else {
                    System.out.println("Registro fallido");
                }
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private boolean autenticar() {
        System.out.println("\n=== Iniciar Sesión ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            Usuario user = persistenciaUsuarios.autenticarUsuario(usuario, password);
            if (user instanceof Cliente) {
                cliente = (Cliente) user;
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
        }
        return false;
    }

    private boolean registrar() {
        System.out.println("\n=== Registro de Usuario ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        cliente = new Cliente(nombre, id, email, password);
        try {
            List<Cliente> clientes = persistenciaUsuarios.cargarClientes();
            clientes.add(cliente);
            persistenciaUsuarios.guardarClientes(clientes);
        } catch (Exception e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Cliente ===");
            System.out.println("1. Comprar Tiquete");
            System.out.println("2. Ver Mis Tiquetes");
            System.out.println("3. Ver Atracciones");
            System.out.println("4. Ver Información Personal");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    comprarTiquete();
                    break;
                case 2:
                    verTiquetes();
                    break;
                case 3:
                    verAtracciones();
                    break;
                case 4:
                    verInformacionPersonal();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void comprarTiquete() {
        System.out.println("\n=== Compra de Tiquete ===");
        System.out.print("Nombre del tiquete: ");
        String nombreTiquete = scanner.nextLine();
        // implementar la lógica para buscar y crear un Tiquete válido
        System.out.println("Funcionalidad de compra de tiquete no implementada completamente.");
    }

    private void verTiquetes() {
        System.out.println("\n=== Mis Tiquetes ===");
        for (Tiquete tiquete : cliente.getTiquetes()) {
            System.out.println("ID: " + tiquete.getId());
            System.out.println("Nombre: " + tiquete.getNombre());
            System.out.println("Fecha: " + tiquete.getFecha());
            System.out.println("Estado: " + tiquete.getEstado());
            System.out.println("-------------------");
        }
    }

    private void verAtracciones() {
        System.out.println("\n=== Atracciones Disponibles ===");
        try {
            for (Atraccion atraccion : persistenciaAtracciones.cargarTodasAtracciones()) {
                System.out.println("Nombre: " + atraccion.getNombre());
                System.out.println("Tipo: " + atraccion.getClass().getSimpleName());
                if (atraccion instanceof modelo.atracciones.AtraccionMecanica) {
                    System.out.println("Capacidad: " + ((modelo.atracciones.AtraccionMecanica)atraccion).getCupoMaximo());
                } else if (atraccion instanceof modelo.atracciones.AtraccionCultural) {
                    System.out.println("Capacidad: " + ((modelo.atracciones.AtraccionCultural)atraccion).getCupoMaximo());
                }
                System.out.println("-------------------");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar atracciones: " + e.getMessage());
        }
    }

    private void verInformacionPersonal() {
        System.out.println("\n=== Información Personal ===");
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Usuario: " + cliente.getEmail());
        System.out.println("Tiquetes activos: " + cliente.getTiquetes().size());
    }

    public static void main(String[] args) {
        ConsolaCliente consola = new ConsolaCliente();
        consola.iniciar();
    }
} 