package interfaz;

import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import modelo.atracciones.Atraccion;
import modelo.tiquetes.Tiquete;
import modelo.tiquetes.TiqueteBasico;
import modelo.tiquetes.FastPass;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaTiquetes;
import java.util.Scanner;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ConsolaCliente {
    private Cliente cliente;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaAtracciones persistenciaAtracciones;
    private PersistenciaTiquetes persistenciaTiquetes;
    private Scanner scanner;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

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
        System.out.println("1. Tiquete Regular");
        System.out.println("2. FastPass");
        System.out.print("Seleccione una opción: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                comprarTiqueteRegular();
                break;
            case 2:
                comprarFastPass();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void comprarTiqueteRegular() {
        System.out.println("\nTipos de tiquete disponibles:");
        System.out.println("1. Básico - $50");
        System.out.println("2. Familiar - $100");
        System.out.println("3. Oro - $150");
        System.out.println("4. Diamante - $200");
        System.out.print("Seleccione el tipo: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        double precio = 0;
        String categoria = "";
        
        switch (tipo) {
            case 1:
                precio = 50;
                categoria = "BRONCE";
                break;
            case 2:
                precio = 100;
                categoria = "FAMILIAR";
                break;
            case 3:
                precio = 150;
                categoria = "ORO";
                break;
            case 4:
                precio = 200;
                categoria = "DIAMANTE";
                break;
            default:
                System.out.println("Tipo no válido");
                return;
        }

        if (cliente.isEmpleado()) {
            precio = precio * 0.8; 
            System.out.println("Descuento de empleado aplicado (20%)");
        }

        System.out.println("Precio final: $" + precio);
        System.out.print("¿Confirmar compra? (S/N): ");
        
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            try {
                TiqueteBasico tiquete = new TiqueteBasico(
                    generarIdTiquete(),
                    "Tiquete " + categoria,
                    1,
                    categoria,
                    new Date(),
                    "Activo",
                    "Online",
                    "Adulto",
                    false
                );
                
                cliente.comprarTiquete(tiquete);
                System.out.println("Tiquete comprado exitosamente");
            } catch (Exception e) {
                System.out.println("Error al comprar tiquete: " + e.getMessage());
            }
        }
    }

    private void comprarFastPass() {
        if (cliente.getTiquetes().isEmpty()) {
            System.out.println("Debe tener al menos un tiquete para comprar FastPass");
            return;
        }

        System.out.println("\nTiquetes disponibles para FastPass:");
        List<Tiquete> tiquetes = cliente.getTiquetes();
        for (int i = 0; i < tiquetes.size(); i++) {
            System.out.println((i + 1) + ". " + tiquetes.get(i).getNombre());
        }

        System.out.print("Seleccione el tiquete: ");
        int indice = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (indice < 0 || indice >= tiquetes.size()) {
            System.out.println("Tiquete no válido");
            return;
        }

        System.out.print("Fecha para el FastPass (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        
        try {
            Date fecha = formatoFecha.parse(fechaStr);
            double precio = 30; 

            if (cliente.isEmpleado()) {
                precio = precio * 0.8; 
                System.out.println("Descuento de empleado aplicado (20%)");
            }

            System.out.println("Precio del FastPass: $" + precio);
            System.out.print("¿Confirmar compra? (S/N): ");

            if (scanner.nextLine().equalsIgnoreCase("S")) {
                FastPass fastPass = new FastPass(tiquetes.get(indice), fecha);
                cliente.agregarFastPass(fastPass);
                System.out.println("FastPass comprado exitosamente");
            }
        } catch (Exception e) {
            System.out.println("Error al comprar FastPass: " + e.getMessage());
        }
    }

    private int generarIdTiquete() {
        try {
            List<TiqueteBasico> tiquetes = persistenciaTiquetes.cargarTiquetesBasicos();
            return tiquetes.size() + 1;
        } catch (Exception e) {
            return 1;
        }
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