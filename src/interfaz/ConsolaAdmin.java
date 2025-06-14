package interfaz;

import modelo.usuarios.*;
import modelo.empleados.*;
import modelo.lugares.Cafeteria;
import modelo.lugares.Taquilla;
import modelo.lugares.Tienda;
import modelo.atracciones.*;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaLugares;
import excepciones.UsuarioException;
import excepciones.EmpleadoException;
import excepciones.AtraccionException;
import java.util.Scanner;

import Principal.Parque;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ConsolaAdmin { 
    private Administrador admin;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaAtracciones persistenciaAtracciones;
    private PersistenciaEmpleados persistenciaEmpleados;
    private Scanner scanner;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private Parque parque;
    private PersistenciaLugares persistenciaLugares;

    public ConsolaAdmin() throws IOException {
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaAtracciones = new PersistenciaAtracciones();
        persistenciaEmpleados = new PersistenciaEmpleados();
        persistenciaLugares = new PersistenciaLugares();

        scanner = new Scanner(System.in);
        parque = new Parque();
        
    }

    public void iniciar() throws EmpleadoException, IOException {
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
            admin.setParque(parque);
            return admin != null;
        } catch (UsuarioException e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            return false;
        }
    }

    private void mostrarMenu() throws EmpleadoException, IOException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Gestionar Empleados");
            System.out.println("2. Gestionar Atracciones");
            System.out.println("3. Gestionar Turnos");
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
                    gestionarTurnos();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    } 

    private void gestionarEmpleados() throws EmpleadoException, IOException {
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
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Tipo (Cajero/Cocinero/EmpleadoTienda): ");
        String tipo = scanner.nextLine();

        Empleado empleado = null;
        switch (tipo.toLowerCase()) {
            case "cajero":
                empleado = new Cajero("Cajero", nombre, id, false, usuario, password, false);
                break;
            case "cocinero":
                empleado = new Cocinero(true, "Cocinero", nombre, id, false, usuario, password, false);
                break;
            case "empleadotienda":
                empleado = new Regular(true, "EmpleadoTienda", nombre, id, false, usuario, password, false);
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


    
    private void asignarLugarTrabajo() throws EmpleadoException, IOException {
        List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();

        System.out.println("Seleccione tipo de lugar para asignar:");
        System.out.println("1. Cafetería");
        System.out.println("2. Taquilla");
        System.out.println("3. Tienda");
        System.out.println("4. Servicio general");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                List<Cocinero> cocineros = new ArrayList<>();
                List<Cafeteria> cafeterias = persistenciaLugares.cargarCafeterias();


                System.out.println("\n=== Cocineros Disponibles ===");
                System.out.println(cafeterias);

                
                for (Empleado emp : empleados) {
                    if (emp instanceof Cocinero) {
                        Cocinero c = (Cocinero) emp;
                        cocineros.add(c);
                        System.out.println("Nombre: " + c.getNombre() + " | ID: " + c.getId());
                    }
                }
                if (cocineros.isEmpty()) {
                    System.out.println("No hay cocineros disponibles.");
                    return;
                }
                System.out.print("Ingrese el ID del cocinero a asignar: ");
                int idCocinero = Integer.parseInt(scanner.nextLine());
                Cocinero cocineroSeleccionado = null;
                for (Cocinero c : cocineros) {
                    if (c.getId() == idCocinero) {
                        cocineroSeleccionado = c;
                        break;
                    }
                }
                if (cocineroSeleccionado == null) {
                    System.out.println("ID no válido.");
                    return;
                }

                System.out.println("\n=== Cafeterías disponibles ===");
                for (int i = 0; i < cafeterias.size(); i++) {
                    System.out.println((i + 1) + ". " + cafeterias.get(i).getNombre());
                }
                System.out.print("Seleccione el número de la cafetería: ");
                int indiceCafeteria = Integer.parseInt(scanner.nextLine()) - 1;
                if (indiceCafeteria < 0 || indiceCafeteria >= cafeterias.size()) {
                    System.out.println("Opción no válida.");
                    return;
                }
                Cafeteria cafeteriaSeleccionada = cafeterias.get(indiceCafeteria);
                cocineroSeleccionado.setCafeteriaAsignada(cafeteriaSeleccionada);
                persistenciaEmpleados.guardarEmpleadosCocinero(cocineros);
                System.out.println("¡Cocinero asignado correctamente a " + cafeteriaSeleccionada.getNombre() + "!");
                break;
                
                
    
                
                
            case "2":
                List<Cajero> cajeros = new ArrayList<>();
                List<Taquilla>taquillas = persistenciaLugares.cargarTaquillas();


                System.out.println("\n=== Cajeros Disponibles ===");

                
                for (Empleado emp : empleados) {
                    if (emp instanceof Cajero) {
                        Cajero c = (Cajero) emp;
                        cajeros.add(c);
                        System.out.println("Nombre: " + c.getNombre() + " | ID: " + c.getId());
                    }
                }
                if (cajeros.isEmpty()) {
                    System.out.println("No hay cajeros disponibles.");
                    return;
                }
                System.out.print("Ingrese el ID del cajero a asignar: ");
                int idCajero = Integer.parseInt(scanner.nextLine());
                Cajero cajeroSeleccionado = null;
                for (Cajero c : cajeros) {
                    if (c.getId() == idCajero) {
                        cajeroSeleccionado = c;
                        break;
                    }
                }
                if (cajeroSeleccionado == null) {
                    System.out.println("ID no válido.");
                    return;
                }

                System.out.println("\n=== Taquillas disponibles ===");
                for (int i = 0; i < taquillas.size(); i++) {
                    System.out.println((i + 1) + ". " + taquillas.get(i).getNombre());
                }
                System.out.print("Seleccione el número de la tienda: ");
                int iTaquilla = Integer.parseInt(scanner.nextLine()) - 1;
                if (iTaquilla < 0 || iTaquilla >= taquillas.size()) {
                    System.out.println("Opción no válida.");
                    return;
                }
                Taquilla taquillaSeleccionada = taquillas.get(iTaquilla);
                cajeroSeleccionado.setLugarAsignado(taquillaSeleccionada);
                persistenciaEmpleados.guardarEmpleadosCajero(cajeros);
                System.out.println("¡Cajero asignado correctamente a " + taquillaSeleccionada.getNombre() + "!");
                break;
                
            case "3":
                List<Regular> regulares = new ArrayList<>();
                List<Tienda> tiendas = persistenciaLugares.cargarTiendas();


                System.out.println("\n=== Empleados regulares Disponibles ===");

                
                for (Empleado emp : empleados) {
                    if (emp instanceof Regular) {
                        Regular c = (Regular) emp;
                        regulares.add(c);
                        System.out.println("Nombre: " + c.getNombre() + " | ID: " + c.getId());
                    }
                }
                if (regulares.isEmpty()) {
                    System.out.println("No hay empleados disponibles.");
                    return;
                }
                System.out.print("Ingrese el ID del empleado a asignar: ");
                int idRegular = Integer.parseInt(scanner.nextLine());
                Regular regularSeleccionado = null;
                for (Regular c : regulares) {
                    if (c.getId() == idRegular) {
                        regularSeleccionado = c;
                        break;
                    }
                }
                if (regularSeleccionado == null) {
                    System.out.println("ID no válido.");
                    return;
                }

                System.out.println("\n=== Tiendas disponibles ===");
                for (int i = 0; i < tiendas.size(); i++) {
                    System.out.println((i + 1) + ". " + tiendas.get(i).getNombre());
                }
                System.out.print("Seleccione el número de la tienda: ");
                int iTienda = Integer.parseInt(scanner.nextLine()) - 1;
                if (iTienda < 0 || iTienda >= tiendas.size()) {
                    System.out.println("Opción no válida.");
                    return;
                }
                Tienda tiendaSeleccionada = tiendas.get(iTienda);
                regularSeleccionado.setLugarAsignado(tiendaSeleccionada);
                persistenciaEmpleados.guardarEmpleadosRegular(regulares);
                System.out.println("¡Empleado asignado correctamente a " + tiendaSeleccionada.getNombre() + "!");
                break;


            default:
                System.out.println("Opción no reconocida.");
                break;
        }
    

    }




    private void verEmpleados() {
        System.out.println("\n=== Lista de Empleados ===");
        try {
            List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();
            for (Empleado emp : empleados) {
                System.out.println("Nombre: " + emp.getNombre());
                System.out.println("Usuario: " + emp.getEmail());
                System.out.println("Tipo: " + emp.getClass().getSimpleName());
                System.out.println("Lugar de trabajo: " + 
                    (emp instanceof Regular ? ((Regular)emp).getLugarAsignado() != null ? ((Regular)emp).getLugarAsignado().getNombre() : "No asignado" :
                     emp instanceof Cajero ? ((Cajero)emp).getLugarAsignado() != null ? ((Cajero)emp).getLugarAsignado().getNombre() : "No asignado" :
                     "No asignado"));
                System.out.println("-------------------");
            }
        } catch (EmpleadoException e) {
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
	          admin.asignarEmpleadoServicioGeneral(empleado, null, null, usuario);
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

    private void gestionarTurnos() {
        System.out.println("\n=== Gestión de Turnos ===");
        System.out.println("1. Asignar Turno");
        System.out.println("2. Ver Turnos Asignados");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                asignarTurno();
                break;
            case 2:
                verTurnosAsignados();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void asignarTurno() {
        System.out.println("\nEmpleados disponibles:");
        try {
            List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();
            admin.setEmpleados(empleados); 

            if (admin.getParque() == null) {
                Parque parque = new Parque();
                parque.setEmpleados(empleados);
                admin.setParque(parque);
            } else {
                admin.getParque().setEmpleados(empleados);
            }

            for (int i = 0; i < empleados.size(); i++) {
                System.out.println((i + 1) + ". " + empleados.get(i).getNombre());
            }

            System.out.print("Seleccione el empleado: ");
            int indiceEmpleado = Integer.parseInt(scanner.nextLine()) - 1;
            Empleado empleado = empleados.get(indiceEmpleado);

            System.out.print("Fecha del turno (dd/MM/yyyy): ");
            String fechaStr = scanner.nextLine();
            Date fecha = formatoFecha.parse(fechaStr);

            System.out.println("Tipo de turno:");
            System.out.println("1. Apertura");
            System.out.println("2. Cierre");
            System.out.print("Seleccione el tipo: ");
            int tipoTurno = Integer.parseInt(scanner.nextLine());
            String turno = tipoTurno == 1 ? "Apertura" : "Cierre";

            System.out.print("¿Es horas extras? (S/N): ");
            boolean horasExtras = scanner.nextLine().equalsIgnoreCase("S");

            admin.asignarTurno(empleado, fecha, turno, horasExtras);
            System.out.println("Turno asignado exitosamente");

        } catch (Exception e) {
            System.out.println("Error al asignar turno: " + e.getMessage());
        }
    }
    

    private void verTurnosAsignados() {
        System.out.println("\nTurnos asignados:");
        try {
            List<Empleado> empleados = persistenciaEmpleados.cargarTodosEmpleados();
            for (Empleado empleado : empleados) {
                System.out.println("\nEmpleado: " + empleado.getNombre());
                Map<Date, Map<String, Map<Empleado, Object>>> asignaciones = admin.getAsignacionesEmpleados();
                boolean tieneTurnos = false;
                
                for (Map.Entry<Date, Map<String, Map<Empleado, Object>>> entry : asignaciones.entrySet()) {
                    Date fecha = entry.getKey();
                    Map<String, Map<Empleado, Object>> asignacionesFecha = entry.getValue();
                    
                    for (Map.Entry<String, Map<Empleado, Object>> turnoEntry : asignacionesFecha.entrySet()) {
                        String turno = turnoEntry.getKey();
                        Map<Empleado, Object> asignacionesTurno = turnoEntry.getValue();
                        
                        if (asignacionesTurno.containsKey(empleado)) {
                            tieneTurnos = true;
                            System.out.println("- Fecha: " + formatoFecha.format(fecha));
                            System.out.println("  Tipo: " + turno);
                            System.out.println("  Horas extras: " + asignacionesTurno.get(empleado));
                        }
                    }
                }
                
                if (!tieneTurnos) {
                    System.out.println("No tiene turnos asignados");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar turnos: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws IOException, EmpleadoException {
        ConsolaAdmin consola = new ConsolaAdmin();
        consola.iniciar();
    }
}