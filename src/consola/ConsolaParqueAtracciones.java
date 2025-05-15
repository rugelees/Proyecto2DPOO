package consola;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import excepciones.AtraccionException;
import excepciones.EmpleadoException;
import excepciones.TiqueteException;
import excepciones.UsuarioException;
import modelo.atracciones.Atraccion;
import modelo.atracciones.AtraccionCultural;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.AtraccionMedio;
import modelo.empleados.Empleado;
import modelo.tiquetes.EnTemporada;
import modelo.tiquetes.FastPass;
import modelo.tiquetes.Individual;
import modelo.tiquetes.Tiquete;
import modelo.tiquetes.TiqueteBasico;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import funcionesrecurrentes.Recurrente;
import persistencia.PersistenciaAtracciones;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaTiquetes;
import persistencia.PersistenciaUsuarios;

public class ConsolaParqueAtracciones {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    private static List<Atraccion> atracciones = new ArrayList<>();
    private static List<Espectaculo> espectaculos = new ArrayList<>();
    private static List<Empleado> empleados = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Tiquete> tiquetes = new ArrayList<>();
    private static List<FastPass> fastPasses = new ArrayList<>();
    private static Administrador administrador;
    
    private static Map<String, Atraccion> atraccionesPorNombre = new HashMap<>();
    private static Map<Integer, Empleado> empleadosPorId = new HashMap<>();
    private static Map<Integer, Cliente> clientesPorId = new HashMap<>();
    private static Map<Integer, Tiquete> tiquetesPorId = new HashMap<>();
    
    private static PersistenciaAtracciones persistenciaAtracciones = new PersistenciaAtracciones();
    private static PersistenciaEmpleados persistenciaEmpleados = new PersistenciaEmpleados();
    private static PersistenciaTiquetes persistenciaTiquetes = new PersistenciaTiquetes();
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();

    public static void main(String[] args) {
    	cargarDatos();
    	if (administrador != null) {
    	    administrador.setEmpleados(empleados);
    	}

        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DEL PARQUE DE ATRACCIONES ===");
            System.out.println("1. Cargar datos de archivos");
            System.out.println("2. Crear datos de ejemplo");
            System.out.println("3. FUNCIONALIDAD 1: Asignación de empleados a atracciones");
            System.out.println("4. FUNCIONALIDAD 2: Venta de tiquetes y verificación de acceso");
            System.out.println("5. FUNCIONALIDAD 3: Gestión de mantenimiento de atracciones");
            System.out.println("6. Guardar datos en archivos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 0:
                        salir = true;
                        break;
                    case 1:
                        cargarDatos();
                        break;
                    case 2:
                        crearDatosEjemplo();
                        break;
                    case 3:
                        gestionarAsignacionEmpleados();
                        break;
                    case 4:
                        gestionarVentaTiquetes();
                        break;
                    case 5:
                        gestionarMantenimientoAtracciones();
                        break;
                    case 6:
                        guardarDatos();
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("¡Hasta luego!");
        scanner.close();
    }
    private static void cargarDatos() {
        try {
            System.out.println("\nCargando datos desde archivos...");

            File carpetaData = new File("data");
            if (!carpetaData.exists() || !carpetaData.isDirectory()) {
                System.out.println("No se encontró la carpeta 'data'. Los archivos se crearán al guardar.");
                return;
            }

            List<AtraccionMecanica> atraccionesMecanicas = persistenciaAtracciones.cargarAtraccionesMecanicas();
            List<AtraccionCultural> atraccionesCulturales = persistenciaAtracciones.cargarAtraccionesCulturales();
            espectaculos = persistenciaAtracciones.cargarEspectaculos();

            atracciones.clear();
            atracciones.addAll(atraccionesMecanicas);
            atracciones.addAll(atraccionesCulturales);

            atraccionesPorNombre.clear();
            for (Atraccion atraccion : atracciones) {
                atraccionesPorNombre.put(atraccion.getNombre(), atraccion);
            }

            empleados = persistenciaEmpleados.cargarTodosEmpleados();

            empleadosPorId.clear();
            for (Empleado empleado : empleados) {
                empleadosPorId.put(empleado.getId(), empleado);
            }

            clientes = persistenciaUsuarios.cargarClientes();
            List<Administrador> administradores = persistenciaUsuarios.cargarAdministradores();

            if (!administradores.isEmpty()) {
                administrador = administradores.get(0);
            } else {
                System.out.println("No se encontró administrador, creando uno por defecto...");
                administrador = new Administrador("Admin Principal", 1, "admin@parque.com", "admin123");
                administradores.add(administrador);
                persistenciaUsuarios.guardarAdministradores(administradores);
            }

            administrador.getAtracciones().clear();
            administrador.getAtracciones().addAll(atracciones);

            administrador.getEmpleados().clear();
            administrador.getEmpleados().addAll(empleados);

            administrador.getEspectaculos().clear();
            administrador.getEspectaculos().addAll(espectaculos);

            clientesPorId.clear();
            for (Cliente cliente : clientes) {
                clientesPorId.put(cliente.getId(), cliente);
            }

            List<TiqueteBasico> tiquetesBasicos = persistenciaTiquetes.cargarTiquetesBasicos();
            List<EnTemporada> tiquetesTemporada = persistenciaTiquetes.cargarTiquetesTemporada();
            List<Individual> tiquetesIndividuales = persistenciaTiquetes.cargarTiquetesIndividuales();

            tiquetes.clear();
            tiquetes.addAll(tiquetesBasicos);
            tiquetes.addAll(tiquetesTemporada);
            tiquetes.addAll(tiquetesIndividuales);

            persistenciaTiquetes.asociarAtraccionesATiquetes(atraccionesPorNombre);

            fastPasses = persistenciaTiquetes.cargarFastPasses(tiquetes);

            tiquetesPorId.clear();
            for (Tiquete tiquete : tiquetes) {
                tiquetesPorId.put(tiquete.getId(), tiquete);
            }

            System.out.println("Datos cargados exitosamente:");
            System.out.println("- Atracciones: " + atracciones.size());
            System.out.println("- Espectáculos: " + espectaculos.size());
            System.out.println("- Empleados: " + empleados.size());
            System.out.println("- Clientes: " + clientes.size());
            System.out.println("- Tiquetes: " + tiquetes.size());
            System.out.println("- FastPasses: " + fastPasses.size());

        } catch (AtraccionException | EmpleadoException | TiqueteException | UsuarioException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    

    private static void crearDatosEjemplo() {
        try {
            System.out.println("\nCreando datos de ejemplo...");
            
            atracciones.clear();
            espectaculos.clear();
            empleados.clear();
            clientes.clear();
            tiquetes.clear();
            fastPasses.clear();
            
            atraccionesPorNombre.clear();
            empleadosPorId.clear();
            clientesPorId.clear();
            tiquetesPorId.clear();
            
            administrador = new Administrador("Admin Principal", 1, "admin@parque.com", "admin123");
            
            AtraccionMecanica montanaRusa = new AtraccionMecanica(
                    "Montaña Rusa", "No operar durante tormentas eléctricas", false, null, null,
                    Recurrente.DIAMANTE, 3, "Zona Norte", 30, 120, 200, 40, 120,
                    "Problemas cardíacos, embarazo", "alto");
            
            AtraccionMecanica ruedaFortuna = new AtraccionMecanica(
                    "Rueda de la Fortuna", "No operar durante vientos fuertes", false, null, null,
                    Recurrente.FAMILIAR, 2, "Zona Central", 40, 80, 190, 0, 200,
                    "", "medio");
            
            AtraccionMecanica carroschocones = new AtraccionMecanica(
                    "Carros Chocones", "No operar durante lluvias intensas", false, null, null,
                    Recurrente.ORO, 2, "Zona Sur", 20, 120, 180, 40, 100,
                    "Problemas de espalda", "medio");
            
            atracciones.add(montanaRusa);
            atracciones.add(ruedaFortuna);
            atracciones.add(carroschocones);
            
            AtraccionCultural casaTerror = new AtraccionCultural(
                    "Casa del Terror", "", false, null, null,
                    Recurrente.ORO, 2, "Zona Este", 25, 12);
            
            AtraccionCultural teatroInteractivo = new AtraccionCultural(
                    "Teatro Interactivo", "", false, null, null,
                    Recurrente.FAMILIAR, 3, "Zona Oeste", 50, 0);
            
            atracciones.add(casaTerror);
            atracciones.add(teatroInteractivo);
            
            for (Atraccion atraccion : atracciones) {
                atraccionesPorNombre.put(atraccion.getNombre(), atraccion);
            }
            
            Date hoy = new Date();
            Date manana = new Date(hoy.getTime() + 24 * 60 * 60 * 1000);
            Date pasadoManana = new Date(hoy.getTime() + 2 * 24 * 60 * 60 * 1000);
            
            Espectaculo showDelfines = new Espectaculo(
                    "Show de Delfines", "No realizar durante tormentas", false, null, null,
                    "45 minutos", "12:00", 100);
            showDelfines.agregarFuncion(hoy);
            showDelfines.agregarFuncion(manana);
            showDelfines.agregarFuncion(pasadoManana);
            
            Espectaculo circo = new Espectaculo(
                    "Circo Mágico", "", true, hoy, pasadoManana,
                    "1:30 horas", "17:00", 200);
            circo.agregarFuncion(hoy);
            circo.agregarFuncion(manana);
            
            espectaculos.add(showDelfines);
            espectaculos.add(circo);
            
            AtraccionAlto empleadoAlto1 = new AtraccionAlto(
                    "Atracción Alto", "Juan Pérez", 101, false,
                    "juan@parque.com", "pass101", false, true);
            empleadoAlto1.asignarAtraccionAlta(montanaRusa);
            
            AtraccionAlto empleadoAlto2 = new AtraccionAlto(
                    "Atracción Alto", "María García", 102, false,
                    "maria@parque.com", "pass102", true, true);
            empleadoAlto2.asignarAtraccionAlta(montanaRusa);
            
            AtraccionMedio empleadoMedio1 = new AtraccionMedio(
                    hoy, new Date(hoy.getTime() + 365 * 24 * 60 * 60 * 1000),
                    "Atracción Medio", "Pedro Rodríguez", 103, false,
                    "pedro@parque.com", "pass103", false);
            empleadoMedio1.asignarAtraccionMedia(ruedaFortuna);
            empleadoMedio1.asignarAtraccionMedia(carroschocones);
            
            empleados.add(empleadoAlto1);
            empleados.add(empleadoAlto2);
            empleados.add(empleadoMedio1);
            
            for (Empleado empleado : empleados) {
                empleadosPorId.put(empleado.getId(), empleado);
            }
            
            Cliente cliente1 = new Cliente("Ana López", 201, "ana@email.com", "pass201", 165, 60, 25);
            Cliente cliente2 = new Cliente("Carlos Torres", 202, "carlos@email.com", "pass202", 180, 80, 30);
            Cliente cliente3 = new Cliente("Sofía Martínez", 203, "sofia@email.com", "pass203", 155, 50, 9);
            
            cliente1.agregarCondicionSalud("Asma");
            
            clientes.add(cliente1);
            clientes.add(cliente2);
            clientes.add(cliente3);
            
            for (Cliente cliente : clientes) {
                clientesPorId.put(cliente.getId(), cliente);
            }
            
            TiqueteBasico tiquete1 = new TiqueteBasico(
                    301, "Tiquete Familiar", 1, Recurrente.FAMILIAR,
                    hoy, "Activo", "Taquilla", "Adulto", false);
            
            TiqueteBasico tiquete2 = new TiqueteBasico(
                    302, "Tiquete Oro", 1, Recurrente.ORO,
                    hoy, "Activo", "Taquilla", "Adulto", false);
            
            TiqueteBasico tiquete3 = new TiqueteBasico(
                    303, "Tiquete Diamante", 1, Recurrente.DIAMANTE,
                    hoy, "Activo", "Taquilla", "Niño", false);
            
            EnTemporada tiquete4 = new EnTemporada(
                    304, "Temporada Oro", 1, Recurrente.ORO,
                    hoy, "Activo", "Online", hoy, pasadoManana,
                    "Semanal", "Adulto", false);
            
            Individual tiquete5 = new Individual(
                    montanaRusa, 305, "Individual Montaña Rusa", 1,
                    Recurrente.DIAMANTE, hoy, "Activo", "Taquilla", false);
            
            tiquetes.add(tiquete1);
            tiquetes.add(tiquete2);
            tiquetes.add(tiquete3);
            tiquetes.add(tiquete4);
            tiquetes.add(tiquete5);
            
            for (Tiquete tiquete : tiquetes) {
                tiquetesPorId.put(tiquete.getId(), tiquete);
            }
            
            cliente1.comprarTiquete(tiquete1);
            cliente2.comprarTiquete(tiquete2);
            cliente3.comprarTiquete(tiquete3);
            cliente1.comprarTiquete(tiquete5);
            
            FastPass fastPass1 = new FastPass(tiquete2, manana);
            FastPass fastPass2 = new FastPass(tiquete3, hoy);
            
            fastPasses.add(fastPass1);
            fastPasses.add(fastPass2);
            
            administrador.getAtracciones().addAll(atracciones);
            administrador.getEmpleados().addAll(empleados);
            administrador.getEspectaculos().addAll(espectaculos);
            
            System.out.println("Datos de ejemplo creados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear los datos de ejemplo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void gestionarAsignacionEmpleados() throws EmpleadoException {
        if (administrador == null) {
            System.out.println("\nDebe cargar datos o crear datos de ejemplo primero.");
            return;
        }
        
        System.out.println("\n=== GESTIÓN DE ASIGNACIÓN DE EMPLEADOS A ATRACCIONES ===");
        
        administrador.getAtracciones().clear();
        administrador.getAtracciones().addAll(atracciones);
        
        administrador.getEmpleados().clear();
        administrador.getEmpleados().addAll(empleados);
        System.out.println("\nAtracciones mecánicas disponibles:");
        List<AtraccionMecanica> atraccionesMecanicas = new ArrayList<>();
        for (Atraccion atraccion : atracciones) {
            if (atraccion instanceof AtraccionMecanica) {
                AtraccionMecanica atraccionMecanica = (AtraccionMecanica) atraccion;
                atraccionesMecanicas.add(atraccionMecanica);
                System.out.println((atraccionesMecanicas.size()) + ". " + atraccionMecanica.getNombre() + 
                        " - Nivel de riesgo: " + atraccionMecanica.getNivelRiesgo());
            }
        }
        
        if (atraccionesMecanicas.isEmpty()) {
            System.out.println("No hay atracciones mecánicas disponibles.");
            return;
        }
        
        System.out.print("\nSeleccione el número de la atracción a la que desea asignar empleados: ");
        int indiceAtraccion;
        try {
            indiceAtraccion = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Operación cancelada.");
            return;
        }
        
        if (indiceAtraccion < 0 || indiceAtraccion >= atraccionesMecanicas.size()) {
            System.out.println("Número de atracción inválido.");
            return;
        }
        
        AtraccionMecanica atraccionSeleccionada = atraccionesMecanicas.get(indiceAtraccion);
        
        System.out.println("\nEmpleados disponibles para esta atracción:");
        List<Empleado> empleadosDisponibles = new ArrayList<>();
        
        for (Empleado empleado : empleados) {
            boolean disponible = false;
            
            if (atraccionSeleccionada.esRiesgoAlto() && empleado instanceof AtraccionAlto) {
                AtraccionAlto empleadoAlto = (AtraccionAlto) empleado;
                disponible = empleadoAlto.puedeOperarAtraccionRiesgoAlto();
            } else if (atraccionSeleccionada.esRiesgoMedio() && empleado instanceof AtraccionMedio) {
                AtraccionMedio empleadoMedio = (AtraccionMedio) empleado;
                disponible = empleadoMedio.puedeOperarAtraccionRiesgoMedio();
            }
            
            if (disponible) {
                empleadosDisponibles.add(empleado);
                System.out.println((empleadosDisponibles.size()) + ". " + empleado.getNombre() + 
                        " - ID: " + empleado.getId() + " - Tipo: " + empleado.getTipo());
            }
        }
        
        if (empleadosDisponibles.isEmpty()) {
            System.out.println("No hay empleados disponibles para esta atracción.");
            return;
        }
        
        System.out.print("\nSeleccione el número del empleado que desea asignar: ");
        int indiceEmpleado;
        try {
            indiceEmpleado = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Operación cancelada.");
            return;
        }
        
        if (indiceEmpleado < 0 || indiceEmpleado >= empleadosDisponibles.size()) {
            System.out.println("Número de empleado inválido.");
            return;
        }
        
        Empleado empleadoSeleccionado = empleadosDisponibles.get(indiceEmpleado);
        
        System.out.print("\nIngrese la fecha (dd/MM/yyyy) para la asignación: ");
        String fechaStr = scanner.nextLine();
        
        Date fecha;
        try {
            fecha = formatoFecha.parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
            return;
        }
        
        System.out.println("\nTurnos disponibles:");
        System.out.println("1. Apertura");
        System.out.println("2. Cierre");
        System.out.print("Seleccione el turno: ");
        int opcionTurno;
        try {
            opcionTurno = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Operación cancelada.");
            return;
        }
        
        String turno;
        switch (opcionTurno) {
            case 1:
                turno = "Apertura";
                break;
            case 2:
                turno = "Cierre";
                break;
            default:
                System.out.println("Opción de turno inválida.");
                return;
        }
        
        boolean empleadoExiste = false;
        boolean atraccionExiste = false;
        
        for (Empleado emp : administrador.getEmpleados()) {
            if (emp.getId() == empleadoSeleccionado.getId()) {
                empleadoExiste = true;
                empleadoSeleccionado = emp;
                break;
            }
        }
        
        for (Atraccion atr : administrador.getAtracciones()) {
            if (atr.getNombre().equals(atraccionSeleccionada.getNombre()) && atr instanceof AtraccionMecanica) {
                atraccionExiste = true;
                atraccionSeleccionada = (AtraccionMecanica) atr;
                break;
            }
        }
        
        if (!empleadoExiste) {
            System.out.println("Error de consistencia: El empleado no existe en la lista interna del administrador.");
            System.out.println("ID del empleado: " + empleadoSeleccionado.getId());
            System.out.println("Tamaño de la lista de empleados del administrador: " + administrador.getEmpleados().size());
            return;
        }
        
        if (!atraccionExiste) {
            System.out.println("Error de consistencia: La atracción no existe en la lista interna del administrador.");
            return;
        }
        
        try {
            administrador.asignarEmpleadoAtraccion(empleadoSeleccionado, atraccionSeleccionada, fecha, turno);
            System.out.println("\nEmpleado asignado exitosamente a la atracción.");
            if (atraccionSeleccionada.esRiesgoAlto() && empleadoSeleccionado instanceof AtraccionAlto) {
                AtraccionAlto empleadoAlto = (AtraccionAlto) empleadoSeleccionado;
                boolean asignado = empleadoAlto.asignarAtraccionAlta(atraccionSeleccionada);
                
                if (asignado) {
                    System.out.println("El empleado ha sido capacitado específicamente para esta atracción de alto riesgo.");
             
                }
            }
            
        } catch (EmpleadoException e) {
        	System.out.println("DEBUG: ¡Se atrapó una EmpleadoException!");
            System.out.println("Error al asignar el empleado: " + e.getMessage());
            e.printStackTrace(); 
        }
       }
    

    private static void gestionarVentaTiquetes() throws TiqueteException {
        if (clientes.isEmpty() || atracciones.isEmpty()) {
            System.out.println("\nDebe cargar datos o crear datos de ejemplo primero.");
            return;
        }
        
        System.out.println("\n=== GESTIÓN DE VENTA DE TIQUETES Y VERIFICACIÓN DE ACCESO ===");
        
        System.out.println("\n1. Seleccionar cliente existente");
        System.out.println("2. Crear nuevo cliente");
        System.out.print("Seleccione una opción: ");
        int opcionCliente = Integer.parseInt(scanner.nextLine());
        
        Cliente clienteSeleccionado = null;
        
        switch (opcionCliente) {
            case 1:
                System.out.println("\nClientes disponibles:");
                for (int i = 0; i < clientes.size(); i++) {
                    Cliente cliente = clientes.get(i);
                    System.out.println((i + 1) + ". " + cliente.getNombre() + " - ID: " + cliente.getId());
                }
                
                System.out.print("Seleccione el número del cliente: ");
                int indiceCliente = Integer.parseInt(scanner.nextLine()) - 1;
                
                if (indiceCliente >= 0 && indiceCliente < clientes.size()) {
                    clienteSeleccionado = clientes.get(indiceCliente);
                } else {
                    System.out.println("Número de cliente inválido.");
                    return;
                }
                break;
                
            case 2:
                System.out.print("\nIngrese el nombre del cliente: ");
                String nombre = scanner.nextLine();
                
                System.out.print("Ingrese el ID del cliente: ");
                int id = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Ingrese el email del cliente: ");
                String email = scanner.nextLine();
                
                System.out.print("Ingrese la contraseña del cliente: ");
                String password = scanner.nextLine();
                
                System.out.print("Ingrese la altura del cliente (cm): ");
                float altura = Float.parseFloat(scanner.nextLine());
                
                System.out.print("Ingrese el peso del cliente (kg): ");
                float peso = Float.parseFloat(scanner.nextLine());
                
                System.out.print("Ingrese la edad del cliente (años): ");
                int edad = Integer.parseInt(scanner.nextLine());
                
                clienteSeleccionado = new Cliente(nombre, id, email, password, altura, peso, edad);
                
                System.out.print("¿El cliente tiene alguna condición de salud? (S/N): ");
                String tieneCondicion = scanner.nextLine();
                
                if (tieneCondicion.equalsIgnoreCase("S")) {
                    System.out.print("Ingrese la condición de salud: ");
                    String condicion = scanner.nextLine();
                    clienteSeleccionado.agregarCondicionSalud(condicion);
                }
                
                clientes.add(clienteSeleccionado);
                clientesPorId.put(clienteSeleccionado.getId(), clienteSeleccionado);
                
                System.out.println("Cliente creado exitosamente.");
                break;
                
            default:
                System.out.println("Opción inválida.");
                return;
        }
        
        System.out.println("\nTipos de tiquete disponibles:");
        System.out.println("1. Tiquete Básico - Familiar");
        System.out.println("2. Tiquete Básico - Oro");
        System.out.println("3. Tiquete Básico - Diamante");
        System.out.println("4. Tiquete de Temporada");
        System.out.println("5. Tiquete Individual para una atracción");
        System.out.println("6. FastPass");
        System.out.print("Seleccione el tipo de tiquete: ");
        
        int opcionTiquete = Integer.parseInt(scanner.nextLine());
        Tiquete nuevoTiquete = null;
        
        int nuevoId = 1000;
        for (Tiquete t : tiquetes) {
            if (t.getId() >= nuevoId) {
                nuevoId = t.getId() + 1;
            }
        }
        
        Date fechaActual = new Date();
        
        switch (opcionTiquete) {
            case 1: 
                nuevoTiquete = new TiqueteBasico(
                        nuevoId, "Tiquete Familiar", 1, Recurrente.FAMILIAR,
                        fechaActual, "Activo", "Consola", "Adulto", false);
                break;
                
            case 2: 
                nuevoTiquete = new TiqueteBasico(
                        nuevoId, "Tiquete Oro", 1, Recurrente.ORO,
                        fechaActual, "Activo", "Consola", "Adulto", false);
                break;
                
            case 3: 
                nuevoTiquete = new TiqueteBasico(
                        nuevoId, "Tiquete Diamante", 1, Recurrente.DIAMANTE,
                        fechaActual, "Activo", "Consola", "Adulto", false);
                break;
                
            case 4: 
                System.out.print("\nIngrese el nivel de exclusividad (Familiar/Oro/Diamante): ");
                String nivelExclusividad = scanner.nextLine();
                
                System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
                String fechaInicioStr = scanner.nextLine();
                
                System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
                String fechaFinStr = scanner.nextLine();
                
                System.out.print("Ingrese el tipo de temporada (Semanal/Mensual/Estacional/Anual): ");
                String tipoTemporada = scanner.nextLine();
                
                Date fechaInicio, fechaFin;
                try {
                    fechaInicio = formatoFecha.parse(fechaInicioStr);
                    fechaFin = formatoFecha.parse(fechaFinStr);
                } catch (ParseException e) {
                    System.out.println("Error en el formato de fechas: " + e.getMessage());
                    return;
                }
                
                nuevoTiquete = new EnTemporada(
                        nuevoId, "Temporada " + nivelExclusividad, 1, nivelExclusividad,
                        fechaActual, "Activo", "Consola", fechaInicio, fechaFin,
                        tipoTemporada, "Adulto", false);
                break;
                
            case 5: 
                System.out.println("\nAtracciones disponibles:");
                for (int i = 0; i < atracciones.size(); i++) {
                    Atraccion atraccion = atracciones.get(i);
                    System.out.println((i + 1) + ". " + atraccion.getNombre() + 
                            " - Nivel de exclusividad: " + atraccion.getNivelExclusividad());
                }
                
                System.out.print("Seleccione el número de la atracción: ");
                int indiceAtraccion = Integer.parseInt(scanner.nextLine()) - 1;
                
                if (indiceAtraccion < 0 || indiceAtraccion >= atracciones.size()) {
                    System.out.println("Número de atracción inválido.");
                    return;
                }
                
                Atraccion atraccionSeleccionada = atracciones.get(indiceAtraccion);
                
                nuevoTiquete = new Individual(
                        atraccionSeleccionada, nuevoId, "Individual " + atraccionSeleccionada.getNombre(), 1,
                        atraccionSeleccionada.getNivelExclusividad(), fechaActual, "Activo", "Consola", false);
                break;
                
            case 6: 
                if (clienteSeleccionado.getTiquetes().isEmpty()) {
                    System.out.println("El cliente debe tener al menos un tiquete para comprar un FastPass.");
                    return;
                }
                
                System.out.println("\nTiquetes del cliente:");
                List<Tiquete> tiquetesCliente = clienteSeleccionado.getTiquetes();
                for (int i = 0; i < tiquetesCliente.size(); i++) {
                    Tiquete tiquete = tiquetesCliente.get(i);
                    System.out.println((i + 1) + ". " + tiquete.getNombre() + 
                            " - ID: " + tiquete.getId() + " - Exclusividad: " + tiquete.getExclusividad());
                }
                
                System.out.print("Seleccione el número del tiquete al que asociar el FastPass: ");
                int indiceTiquete = Integer.parseInt(scanner.nextLine()) - 1;
                
                if (indiceTiquete < 0 || indiceTiquete >= tiquetesCliente.size()) {
                    System.out.println("Número de tiquete inválido.");
                    return;
                }
                
                Tiquete tiqueteAsociado = tiquetesCliente.get(indiceTiquete);
                
                System.out.print("Ingrese la fecha para el FastPass (dd/MM/yyyy): ");
                String fechaFastPassStr = scanner.nextLine();
                
                Date fechaFastPass;
                try {
                    fechaFastPass = formatoFecha.parse(fechaFastPassStr);
                } catch (ParseException e) {
                    System.out.println("Error en el formato de fecha: " + e.getMessage());
                    return;
                }
                
                FastPass nuevoFastPass = new FastPass(tiqueteAsociado, fechaFastPass);
                fastPasses.add(nuevoFastPass);
                
                System.out.println("FastPass creado exitosamente.");
                break;
                
            default:
                System.out.println("Opción de tiquete inválida.");
                return;
        }
        
        if (nuevoTiquete != null) {
            tiquetes.add(nuevoTiquete);
            tiquetesPorId.put(nuevoTiquete.getId(), nuevoTiquete);
            
            clienteSeleccionado.comprarTiquete(nuevoTiquete);
            
            System.out.println("Tiquete vendido exitosamente al cliente " + clienteSeleccionado.getNombre());
        }
        
        System.out.println("\n=== VERIFICACIÓN DE ACCESO A ATRACCIONES ===");
        System.out.println("\nAtracciones disponibles:");
        for (int i = 0; i < atracciones.size(); i++) {
            Atraccion atraccion = atracciones.get(i);
            System.out.println((i + 1) + ". " + atraccion.getNombre() + 
                    " - Nivel de exclusividad: " + atraccion.getNivelExclusividad());
        }
        
        System.out.print("Seleccione el número de la atracción para verificar acceso: ");
        int indiceAtraccionAcceso = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (indiceAtraccionAcceso < 0 || indiceAtraccionAcceso >= atracciones.size()) {
            System.out.println("Número de atracción inválido.");
            return;
        }
        
        Atraccion atraccionAcceso = atracciones.get(indiceAtraccionAcceso);
        
        System.out.println("\nVerificando acceso del cliente " + clienteSeleccionado.getNombre() + 
                " a la atracción " + atraccionAcceso.getNombre() + ":");
        
        boolean atraccionDisponible = atraccionAcceso.estaDisponible(new Date());
        System.out.println("Atracción disponible hoy: " + (atraccionDisponible ? "Sí" : "No"));
        
        if (!atraccionDisponible) {
            System.out.println("La atracción no está disponible en la fecha actual.");
            return;
        }
        
        boolean cumpleRestricciones = true;
        if (atraccionAcceso instanceof AtraccionMecanica) {
            AtraccionMecanica atraccionMecanica = (AtraccionMecanica) atraccionAcceso;
            
            boolean cumpleRestriccionesFisicas = atraccionMecanica.verificarRestriccionesFisicas(clienteSeleccionado);
            boolean cumpleContraindicaciones = atraccionMecanica.verificarContraindicaciones(clienteSeleccionado);
            
            System.out.println("Cumple restricciones físicas: " + (cumpleRestriccionesFisicas ? "Sí" : "No"));
            System.out.println("No tiene contraindicaciones de salud: " + (cumpleContraindicaciones ? "Sí" : "No"));
            
            cumpleRestricciones = cumpleRestriccionesFisicas && cumpleContraindicaciones;
        } else if (atraccionAcceso instanceof AtraccionCultural) {
            AtraccionCultural atraccionCultural = (AtraccionCultural) atraccionAcceso;
            
            boolean cumpleRestriccionEdad = atraccionCultural.verificarRestriccionEdad(clienteSeleccionado);
            System.out.println("Cumple restricción de edad: " + (cumpleRestriccionEdad ? "Sí" : "No"));
            
            cumpleRestricciones = cumpleRestriccionEdad;
        }
        
        if (!cumpleRestricciones) {
            System.out.println("El cliente no cumple con las restricciones de la atracción.");
            return;
        }
        
        boolean tieneAcceso = false;
        Tiquete tiqueteAcceso = null;
        
        for (Tiquete tiquete : clienteSeleccionado.getTiquetes()) {
            if (!tiquete.isUsado() && tiquete.puedeAccederAtraccion(atraccionAcceso)) {
                tieneAcceso = true;
                tiqueteAcceso = tiquete;
                break;
            }
        }
        
        System.out.println("Tiene tiquete válido para acceder: " + (tieneAcceso ? "Sí" : "No"));
        
        if (!tieneAcceso) {
            System.out.println("El cliente no tiene un tiquete válido para acceder a esta atracción.");
            return;
        }
        
        boolean tieneFastPass = false;
        for (FastPass fastPass : fastPasses) {
            if (!fastPass.isUsado() && 
                fastPass.getTiqueteAsociado() != null && 
                fastPass.getTiqueteAsociado().equals(tiqueteAcceso) &&
                fastPass.esValido(new Date())) {
                tieneFastPass = true;
                break;
            }
        }
        
        System.out.println("Tiene FastPass válido: " + (tieneFastPass ? "Sí" : "No"));
        
        System.out.println("\nRESULTADO: El cliente " + (tieneAcceso ? "SÍ" : "NO") + 
                " puede acceder a la atracción " + atraccionAcceso.getNombre());
        
        if (tieneAcceso) {
            System.out.print("¿Desea marcar el tiquete como usado? (S/N): ");
            String marcarUsado = scanner.nextLine();
            
            if (marcarUsado.equalsIgnoreCase("S")) {
                tiqueteAcceso.marcarComoUsado();
                System.out.println("Tiquete marcado como usado.");
            }
        }
    }
    
    private static void gestionarMantenimientoAtracciones() throws AtraccionException {
        if (administrador == null || atracciones.isEmpty()) {
            System.out.println("\nDebe cargar datos o crear datos de ejemplo primero.");
            return;
        }
        
        System.out.println("\n=== GESTIÓN DE MANTENIMIENTO DE ATRACCIONES ===");
        
        System.out.println("\nAtracciones mecánicas:");
        List<AtraccionMecanica> atraccionesMecanicas = new ArrayList<>();
        
        for (Atraccion atraccion : atracciones) {
            if (atraccion instanceof AtraccionMecanica) {
                AtraccionMecanica atraccionMecanica = (AtraccionMecanica) atraccion;
                atraccionesMecanicas.add(atraccionMecanica);
                System.out.println((atraccionesMecanicas.size()) + ". " + atraccionMecanica.getNombre() + 
                        " - Nivel de riesgo: " + atraccionMecanica.getNivelRiesgo());
            }
        }
        
        if (atraccionesMecanicas.isEmpty()) {
            System.out.println("No hay atracciones mecánicas disponibles.");
            return;
        }
        
        System.out.print("\nSeleccione el número de la atracción para programar mantenimiento: ");
        int indiceAtraccion = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (indiceAtraccion < 0 || indiceAtraccion >= atraccionesMecanicas.size()) {
            System.out.println("Número de atracción inválido.");
            return;
        }
        
        AtraccionMecanica atraccionSeleccionada = atraccionesMecanicas.get(indiceAtraccion);
        
        List<Date> fechasMantenimiento = atraccionSeleccionada.getFechasMantenimiento();
        
        System.out.println("\nFechas de mantenimiento actuales para " + atraccionSeleccionada.getNombre() + ":");
        if (fechasMantenimiento.isEmpty()) {
            System.out.println("No hay fechas de mantenimiento programadas.");
        } else {
            for (Date fecha : fechasMantenimiento) {
                System.out.println("- " + formatoFecha.format(fecha));
            }
        }
        
        System.out.println("\nProgramar nuevo mantenimiento:");
        System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
        String fechaInicioStr = scanner.nextLine();
        
        System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
        String fechaFinStr = scanner.nextLine();
        
        Date fechaInicio, fechaFin;
        try {
            fechaInicio = formatoFecha.parse(fechaInicioStr);
            fechaFin = formatoFecha.parse(fechaFinStr);
        } catch (ParseException e) {
            System.out.println("Error en el formato de fechas: " + e.getMessage());
            return;
        }
        
        boolean disponibleAntes = atraccionSeleccionada.estaDisponible(new Date());
        
        try {
            administrador.gestionarMantenimientoAtracciones(atraccionSeleccionada, fechaInicio, fechaFin);
            System.out.println("\nMantenimiento programado exitosamente para " + 
                    atraccionSeleccionada.getNombre() + " desde " + 
                    formatoFecha.format(fechaInicio) + " hasta " + 
                    formatoFecha.format(fechaFin));
            
            boolean disponibleDespues = atraccionSeleccionada.estaDisponible(new Date());
            System.out.println("La atracción estaba disponible antes: " + (disponibleAntes ? "Sí" : "No"));
            System.out.println("La atracción está disponible ahora: " + (disponibleDespues ? "Sí" : "No"));
            
        } catch (AtraccionException e) {
            System.out.println("Error al programar el mantenimiento: " + e.getMessage());
        }
        
        System.out.println("\n=== VERIFICACIÓN DE DISPONIBILIDAD ===");
        System.out.print("Ingrese una fecha para verificar disponibilidad (dd/MM/yyyy): ");
        String fechaVerificacionStr = scanner.nextLine();
        
        try {
            Date fechaVerificacion = formatoFecha.parse(fechaVerificacionStr);
            boolean disponible = atraccionSeleccionada.estaDisponible(fechaVerificacion);
            
            System.out.println("La atracción " + atraccionSeleccionada.getNombre() + 
                    (disponible ? " ESTÁ disponible " : " NO está disponible ") + 
                    "el día " + formatoFecha.format(fechaVerificacion));
            
            if (!disponible) {
                boolean enMantenimiento = false;
                for (Date fecha : atraccionSeleccionada.getFechasMantenimiento()) {
                    if (mismodia(fecha, fechaVerificacion)) {
                        enMantenimiento = true;
                        break;
                    }
                }
                
                if (enMantenimiento) {
                    System.out.println("La atracción está en mantenimiento en esta fecha.");
                } else if (atraccionSeleccionada.isDeTemporada()) {
                    System.out.println("La atracción es de temporada y la fecha está fuera de temporada.");
                }
            }
            
        } catch (ParseException e) {
            System.out.println("Error en el formato de fecha: " + e.getMessage());
        }
    }
    
    private static void guardarDatos() {
        try {
            System.out.println("\nGuardando datos en archivos...");
            
            File carpetaData = new File("data");
            if (!carpetaData.exists()) {
                carpetaData.mkdirs();
            }
            
            List<AtraccionMecanica> atraccionesMecanicas = new ArrayList<>();
            List<AtraccionCultural> atraccionesCulturales = new ArrayList<>();
            
            for (Atraccion atraccion : atracciones) {
                if (atraccion instanceof AtraccionMecanica) {
                    atraccionesMecanicas.add((AtraccionMecanica) atraccion);
                } else if (atraccion instanceof AtraccionCultural) {
                    atraccionesCulturales.add((AtraccionCultural) atraccion);
                }
            }
            
            persistenciaAtracciones.guardarAtraccionesMecanicas(atraccionesMecanicas);
            persistenciaAtracciones.guardarAtraccionesCulturales(atraccionesCulturales);
            persistenciaAtracciones.guardarEspectaculos(espectaculos);
            
            List<AtraccionAlto> empleadosAlto = new ArrayList<>();
            List<AtraccionMedio> empleadosMedio = new ArrayList<>();
            List<modelo.empleados.Cajero> empleadosCajero = new ArrayList<>();
            List<modelo.empleados.Cocinero> empleadosCocinero = new ArrayList<>();
            List<modelo.empleados.Regular> empleadosRegular = new ArrayList<>();
            List<modelo.empleados.ServicioGeneral> empleadosServicioGeneral = new ArrayList<>();
            
            for (Empleado empleado : empleados) {
                if (empleado instanceof AtraccionAlto) {
                    empleadosAlto.add((AtraccionAlto) empleado);
                } else if (empleado instanceof AtraccionMedio) {
                    empleadosMedio.add((AtraccionMedio) empleado);
                } else if (empleado instanceof modelo.empleados.Cajero) {
                    empleadosCajero.add((modelo.empleados.Cajero) empleado);
                } else if (empleado instanceof modelo.empleados.Cocinero) {
                    empleadosCocinero.add((modelo.empleados.Cocinero) empleado);
                } else if (empleado instanceof modelo.empleados.Regular) {
                    empleadosRegular.add((modelo.empleados.Regular) empleado);
                } else if (empleado instanceof modelo.empleados.ServicioGeneral) {
                    empleadosServicioGeneral.add((modelo.empleados.ServicioGeneral) empleado);
                }
            }
            
            persistenciaEmpleados.guardarEmpleadosAtraccionAlto(empleadosAlto);
            persistenciaEmpleados.guardarEmpleadosAtraccionMedio(empleadosMedio);
            persistenciaEmpleados.guardarEmpleadosCajero(empleadosCajero);
            persistenciaEmpleados.guardarEmpleadosCocinero(empleadosCocinero);
            persistenciaEmpleados.guardarEmpleadosRegular(empleadosRegular);
            persistenciaEmpleados.guardarEmpleadosServicioGeneral(empleadosServicioGeneral);
            
            List<TiqueteBasico> tiquetesBasicos = new ArrayList<>();
            List<EnTemporada> tiquetesTemporada = new ArrayList<>();
            List<Individual> tiquetesIndividuales = new ArrayList<>();
            
            for (Tiquete tiquete : tiquetes) {
                if (tiquete instanceof TiqueteBasico && !(tiquete instanceof EnTemporada)) {
                    tiquetesBasicos.add((TiqueteBasico) tiquete);
                } else if (tiquete instanceof EnTemporada) {
                    tiquetesTemporada.add((EnTemporada) tiquete);
                } else if (tiquete instanceof Individual) {
                    tiquetesIndividuales.add((Individual) tiquete);
                }
            }
            
            persistenciaTiquetes.guardarTiquetesBasicos(tiquetesBasicos);
            persistenciaTiquetes.guardarTiquetesTemporada(tiquetesTemporada);
            persistenciaTiquetes.guardarTiquetesIndividuales(tiquetesIndividuales);
            persistenciaTiquetes.guardarFastPasses(fastPasses);
            
            persistenciaUsuarios.guardarClientes(clientes);
            
            if (administrador != null) {
                List<Administrador> administradores = new ArrayList<>();
                administradores.add(administrador);
                persistenciaUsuarios.guardarAdministradores(administradores);
            }
            
            System.out.println("Datos guardados exitosamente en la carpeta 'data'.");
            
        } catch (AtraccionException | EmpleadoException | TiqueteException | UsuarioException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }
    
   
    private static boolean mismodia(Date fecha1, Date fecha2) {

        long milisEnDia = 24 * 60 * 60 * 1000;
        long dia1 = fecha1.getTime() / milisEnDia;
        long dia2 = fecha2.getTime() / milisEnDia;
        return dia1 == dia2;
    }
}