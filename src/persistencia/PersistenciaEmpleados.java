package persistencia;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import excepciones.EmpleadoException;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.AtraccionMedio;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Empleado;
import modelo.empleados.Regular;
import modelo.empleados.ServicioGeneral;


public class PersistenciaEmpleados {
    private static final String ARCHIVO_EMPLEADOS_ATRACCION_ALTO = "empleados_atraccion_alto.txt";
    private static final String ARCHIVO_EMPLEADOS_ATRACCION_MEDIO = "empleados_atraccion_medio.txt";
    private static final String ARCHIVO_EMPLEADOS_CAJERO = "empleados_cajero.txt";
    private static final String ARCHIVO_EMPLEADOS_COCINERO = "empleados_cocinero.txt";
    private static final String ARCHIVO_EMPLEADOS_REGULAR = "empleados_regular.txt";
    private static final String ARCHIVO_EMPLEADOS_SERVICIO_GENERAL = "empleados_servicio_general.txt";
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd-MM-yyyy");
    

    public void guardarEmpleadosAtraccionAlto(List<AtraccionAlto> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (AtraccionAlto empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                sb.append(empleado.isCapacitado()).append("|");
                
                List<modelo.atracciones.AtraccionMecanica> atracciones = empleado.getAtraccionesEspecificas();
                if (atracciones.isEmpty()) {
                    sb.append("null");
                } else {
                    for (int i = 0; i < atracciones.size(); i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(atracciones.get(i).getNombre());
                    }
                }
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_ATRACCION_ALTO);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los empleados de atracción de alto riesgo", e);
        }
    }
    
   
    public void guardarEmpleadosAtraccionMedio(List<AtraccionMedio> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (AtraccionMedio empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                sb.append(empleado.isCapacitado()).append("|");
                sb.append(empleado.getFechaCapacitacion() != null ? FORMATO_FECHA.format(empleado.getFechaCapacitacion()) : "null").append("|");
                sb.append(empleado.getFechaVencimientoCapacitacion() != null ? FORMATO_FECHA.format(empleado.getFechaVencimientoCapacitacion()) : "null").append("|");
                
                List<modelo.atracciones.AtraccionMecanica> atracciones = empleado.getAtraccionesAsignadas();
                if (atracciones.isEmpty()) {
                    sb.append("null");
                } else {
                    for (int i = 0; i < atracciones.size(); i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(atracciones.get(i).getNombre());
                    }
                }
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_ATRACCION_MEDIO);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los empleados de atracción de riesgo medio", e);
        }
    }
    
    
    public void guardarEmpleadosCajero(List<Cajero> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Cajero empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                
                modelo.lugares.LugarServicio lugar = empleado.getLugarAsignado();
                sb.append(lugar != null ? lugar.getId() : "null");
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_CAJERO);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los cajeros", e);
        }
    }
    
    
    public void guardarEmpleadosCocinero(List<Cocinero> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Cocinero empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                sb.append(empleado.isCapacitado()).append("|");
                
                modelo.lugares.Cafeteria cafeteria = empleado.getCafeteriaAsignada();
                sb.append(cafeteria != null ? cafeteria.getId() : "null");
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_COCINERO);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los cocineros", e);
        }
    }
    
    
    public void guardarEmpleadosRegular(List<Regular> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Regular empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                sb.append(empleado.isPuedeSerCajero()).append("|");
                
                modelo.lugares.LugarServicio lugar = empleado.getLugarAsignado();
                sb.append(lugar != null ? lugar.getId() : "null");
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_REGULAR);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los empleados regulares", e);
        }
    }
    
   
    public void guardarEmpleadosServicioGeneral(List<ServicioGeneral> empleados) throws EmpleadoException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (ServicioGeneral empleado : empleados) {
                StringBuilder sb = new StringBuilder();
                sb.append(empleado.getTipo()).append("|");
                sb.append(empleado.getNombre()).append("|");
                sb.append(empleado.getId()).append("|");
                sb.append(empleado.esServicioGeneral()).append("|");
                sb.append(empleado.getEmail()).append("|");
                sb.append(empleado.getPassword()).append("|");
                sb.append(empleado.isHorasExtras()).append("|");
                
                List<String> zonas = empleado.getZonasAsignadas();
                if (zonas.isEmpty()) {
                    sb.append("null");
                } else {
                    for (int i = 0; i < zonas.size(); i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(zonas.get(i));
                    }
                }
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_EMPLEADOS_SERVICIO_GENERAL);
            
        } catch (IOException e) {
            throw new EmpleadoException("Error al guardar los empleados de servicio general", e);
        }
    }
    
    
    public List<AtraccionAlto> cargarEmpleadosAtraccionAlto() throws EmpleadoException {
        List<AtraccionAlto> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_ATRACCION_ALTO)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_ATRACCION_ALTO);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 8) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        boolean capacitado = Boolean.parseBoolean(partes[7]);
                        
                        AtraccionAlto empleado = new AtraccionAlto(tipo, nombre, id, servicioGeneral, email, password, horasExtras, capacitado);
                        
                      
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            throw new EmpleadoException("Error al cargar los empleados de atracción de alto riesgo", e);
        }
        
        return empleados;
    }
    
 
    public List<AtraccionMedio> cargarEmpleadosAtraccionMedio() throws EmpleadoException {
        List<AtraccionMedio> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_ATRACCION_MEDIO)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_ATRACCION_MEDIO);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 10) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        
                        Date fechaCapacitacion = null;
                        if (!partes[8].equals("null")) {
                            fechaCapacitacion = FORMATO_FECHA.parse(partes[8]);
                        }
                        
                        Date fechaVencimiento = null;
                        if (!partes[9].equals("null")) {
                            fechaVencimiento = FORMATO_FECHA.parse(partes[9]);
                        }
                        
                        AtraccionMedio empleado = new AtraccionMedio(fechaCapacitacion, fechaVencimiento, tipo, nombre, id, servicioGeneral, email, password, horasExtras);
                       
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new EmpleadoException("Error al cargar los empleados de atracción de riesgo medio", e);
        }
        
        return empleados;
    }
    
   
    public List<Cajero> cargarEmpleadosCajero() throws EmpleadoException {
        List<Cajero> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_CAJERO)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_CAJERO);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 7) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        
                        Cajero empleado = new Cajero(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
                        
                       
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            throw new EmpleadoException("Error al cargar los cajeros", e);
        }
        
        return empleados;
    }
    
   
    public List<Cocinero> cargarEmpleadosCocinero() throws EmpleadoException {
        List<Cocinero> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_COCINERO)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_COCINERO);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 8) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        boolean capacitado = Boolean.parseBoolean(partes[7]);
                        
                        Cocinero empleado = new Cocinero(capacitado, tipo, nombre, id, servicioGeneral, email, password, horasExtras);
                        
                        
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            throw new EmpleadoException("Error al cargar los cocineros", e);
        }
        
        return empleados;
    }
    

    public List<Regular> cargarEmpleadosRegular() throws EmpleadoException {
        List<Regular> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_REGULAR)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_REGULAR);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 8) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        boolean puedeSerCajero = Boolean.parseBoolean(partes[7]);
                        
                        Regular empleado = new Regular(puedeSerCajero, tipo, nombre, id, servicioGeneral, email, password, horasExtras);
                        
                        
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            throw new EmpleadoException("Error al cargar los empleados regulares", e);
        }
        
        return empleados;
    }
    

    public List<ServicioGeneral> cargarEmpleadosServicioGeneral() throws EmpleadoException {
        List<ServicioGeneral> empleados = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_EMPLEADOS_SERVICIO_GENERAL)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_EMPLEADOS_SERVICIO_GENERAL);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 8) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        int id = Integer.parseInt(partes[2]);
                        boolean servicioGeneral = Boolean.parseBoolean(partes[3]);
                        String email = partes[4];
                        String password = partes[5];
                        boolean horasExtras = Boolean.parseBoolean(partes[6]);
                        
                        List<String> zonas = new ArrayList<>();
                        if (!partes[7].equals("null")) {
                            String[] zonasStr = partes[7].split(",");
                            for (String zona : zonasStr) {
                                zonas.add(zona);
                            }
                        }
                        
                        ServicioGeneral empleado = new ServicioGeneral(zonas, tipo, nombre, id, email, password, horasExtras);
                        
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            throw new EmpleadoException("Error al cargar los empleados de servicio general", e);
        }
        
        return empleados;
    }
    
    
    public List<Empleado> cargarTodosEmpleados() throws EmpleadoException {
        List<Empleado> empleados = new ArrayList<>();
        empleados.addAll(cargarEmpleadosAtraccionAlto());
        empleados.addAll(cargarEmpleadosAtraccionMedio());
        empleados.addAll(cargarEmpleadosCajero());
        empleados.addAll(cargarEmpleadosCocinero());
        empleados.addAll(cargarEmpleadosRegular());
        empleados.addAll(cargarEmpleadosServicioGeneral());
        return empleados;
    }
}