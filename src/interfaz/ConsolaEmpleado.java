package interfaz;

import modelo.usuarios.Usuario;
import modelo.empleados.Empleado;
import modelo.empleados.Regular;
import modelo.empleados.Cajero;
import persistencia.PersistenciaUsuarios;
import java.util.Scanner;

public class ConsolaEmpleado {
    private Empleado empleado;
    private PersistenciaUsuarios persistencia;
    private Scanner scanner;

    public ConsolaEmpleado() {
        persistencia = new PersistenciaUsuarios();
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
        System.out.println("=== Autenticación de Empleado ===");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            Usuario user = persistencia.autenticarUsuario(usuario, password);
            if (user instanceof Empleado) {
                empleado = (Empleado) user;
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
        }
        return false;
    }

    private void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Empleado ===");
            System.out.println("1. Ver Información Personal");
            System.out.println("2. Actualizar Inventario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    verInformacionPersonal();
                    break;
                case 2:
                    actualizarInventario();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void actualizarInventario() {
        if (empleado instanceof Regular && ((Regular)empleado).getLugarAsignado() != null) {
            System.out.println("\n=== Actualizar Inventario ===");
            System.out.print("Nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            //lógica para actualizar el inventario
            System.out.println("Inventario actualizado exitosamente");
        } else {
            System.out.println("No tiene permiso para actualizar inventario");
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

    public static void main(String[] args) {
        ConsolaEmpleado consola = new ConsolaEmpleado();
        consola.iniciar();
    }
} 