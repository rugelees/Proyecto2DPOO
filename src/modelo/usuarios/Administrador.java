package modelo.usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Principal.Parque;
import excepciones.AtraccionException;
import excepciones.EmpleadoException;
import modelo.atracciones.Atraccion;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;
import modelo.empleados.AtraccionAlto;
import modelo.empleados.AtraccionMedio;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Empleado;
import modelo.lugares.Cafeteria;
import modelo.lugares.LugarServicio;
import modelo.lugares.Taquilla;
import modelo.lugares.Tienda;
import modelo.tiquetes.Tiquete;
import funcionesrecurrentes.Recurrente;


public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private int id;
    private List<Atraccion> atracciones;
    private List<Empleado> empleados;
    private List<Espectaculo> espectaculos;
    private Map<Date, Map<String, Map<Empleado, Object>>> asignacionesEmpleados;
    private List<Cafeteria> cafeterias = new ArrayList<>();
    private Parque parque;


    
    
    public Administrador(String nombre, int id, String email, String password, Parque parque) {
        super(email, password);
        this.nombre = nombre;
        this.id = id;
        this.atracciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.espectaculos = new ArrayList<>();
        this.asignacionesEmpleados = new HashMap<>();
        this.cafeterias = new ArrayList<>();
        this.parque = parque;

        
    }
    
    
    public void actualizarAtraccion(Atraccion atraccion) throws AtraccionException {
		parque.cambiarInfoAtraccion(atraccion);
    }

    
    
    public void actualizarEmpleado(Empleado empleado) throws EmpleadoException {
        parque.cambiarInfoEmpleado(empleado);
    }

    
   
    public void crearAtraccion(Atraccion atraccion) throws AtraccionException {
        parque.agregarAtraccion(atraccion);
    }

    public Cafeteria crearCafeteria(String id, String nombre, String ubicacion, List<String> menu, int capacidad) {
        return parque.crearCafeteria(id, nombre, ubicacion, menu, capacidad);
    }

    public Taquilla crearTaquilla(String id, String nombre, String ubicacion, String metodoPago) {
        return parque.crearTaquilla(id, nombre, ubicacion, metodoPago);
    }

    public Tienda crearTienda(String id, String nombre, String ubicacion, String tipoProductos, Map<String, Integer> inventario) {
        return parque.crearTienda(id, nombre, ubicacion, tipoProductos, inventario);
    }

    
    
    public void eliminarAtraccion(Atraccion atraccion) throws AtraccionException {
        parque.eliminarAtraccion(atraccion);
    }

    
   
    public void agregarEmpleado(Empleado empleado) throws EmpleadoException {
        parque.agregarEmpleado(empleado);
    }
    
    public void eliminarEmpleado(Empleado empleado) throws EmpleadoException {
        parque.eliminarEmpleado(empleado);
    }

    
    public void asignarEmpleadoAtraccion(Empleado empleado, AtraccionMecanica atraccion, Date fecha, String turno) 
            throws EmpleadoException {
        parque.asignarEmpleadoAtraccion(empleado, atraccion, fecha, turno);
    }

    public void asignarCocineroCafeteria(Cocinero cocinero, Cafeteria cafeteria, Date fecha, String turno) 
            throws EmpleadoException {
        parque.asignarCocineroACafeteria(cocinero, cafeteria, fecha, turno);
    }

    public void asignarCajeroLugarServicio(Cajero cajero, LugarServicio lugarServicio, Date fecha, String turno) 
            throws EmpleadoException {
        parque.asignarCajeroALugarServicio(cajero, lugarServicio, fecha, turno);
    }

    public void asignarEmpleadoServicioGeneral(Empleado empleado, String[] zonas, Date fecha, String turno) 
            throws EmpleadoException {
        parque.asignarEmpleadoServicioGeneral(empleado, zonas, fecha, turno);
    }

    
    
    public boolean verificarPersonalMinimoEnAtraccion(Atraccion atraccion, Date fecha, String turno) {
        return parque.verificarPersonalMinimo(atraccion, fecha, turno);
    }
    
    
    public void gestionarMantenimientoAtracciones(AtraccionMecanica atraccion, Date fechaInicio, Date fechaFin) 
            throws AtraccionException {
        parque.gestionarMantenimientoAtracciones(atraccion, fechaInicio, fechaFin);
    }
    
    public void crearEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        parque.crearEspectaculo(espectaculo);
    }
    
    
    public void modificarEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        parque.modificarEspectaculo(espectaculo);
    }

    
     
    public void eliminarEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        parque.eliminarEspectaculo(espectaculo);
    }

    
    
    public void gestionarAtraccionesTemporada(Atraccion atraccion, boolean deTemporada, Date fechaInicio, Date fechaFin) 
            throws AtraccionException {
        parque.gestionarAtraccionesTemporada(atraccion, deTemporada, fechaInicio, fechaFin);
    }

    
    
    public Map<Atraccion, Double> generarReporteOcupacionAtracciones() {
        Map<Atraccion, Double> reporte = new HashMap<>();
        
        for (Atraccion atraccion : atracciones) {
            double ocupacion = 0.0; 
            reporte.put(atraccion, ocupacion);
        }
        
        return reporte;
    }
    
   
    public void cambiarNivelExclusividadAtraccion(Atraccion atraccion, String nivelExclusividad) 
            throws AtraccionException {
        if (atraccion == null || nivelExclusividad == null || nivelExclusividad.isEmpty()) {
            throw new AtraccionException("La atracción y el nivel de exclusividad no pueden ser nulos o vacíos");
        }
        
        if (!atracciones.contains(atraccion)) {
            throw new AtraccionException("La atracción no existe en el sistema");
        }
        
        if (!Recurrente.validarTiquete(nivelExclusividad)) {
            throw new AtraccionException("El nivel de exclusividad no es válido");
        }
        
        atraccion.setNivelExclusividad(nivelExclusividad);
    }
    

    public boolean verificarDisponibilidadAtraccion(Atraccion atraccion, Date fecha) {
        if (atraccion == null || fecha == null) {
            return false;
        }
        
        if (!atracciones.contains(atraccion)) {
            return false;
        }
        
        return atraccion.estaDisponible(fecha);
    }
    
    public List<Empleado> consultarEmpleadosCapacitados(String tipoCapacitacion) {
        List<Empleado> empleadosCapacitados = new ArrayList<>();
        if (parque.getEmpleados() == null) {
             System.err.println("Error: La lista de empleados no ha sido inicializada.");
             return empleadosCapacitados;
        }

        for (Empleado empleado : parque.getEmpleados()) {
            try {
                if ("Medio".equalsIgnoreCase(tipoCapacitacion)) {
                    if (empleado instanceof AtraccionMedio) {
                        AtraccionMedio empMedio = (AtraccionMedio) empleado;
                        if (empMedio.puedeOperarAtraccionRiesgoMedio()) {
                            empleadosCapacitados.add(empleado);
                        }
                    }
                } else if ("Alto".equalsIgnoreCase(tipoCapacitacion)) {
                    if (empleado instanceof AtraccionAlto) {
                        AtraccionAlto empAlto = (AtraccionAlto) empleado;
                        if (empAlto.puedeOperarAtraccionRiesgoAlto()) {
                            empleadosCapacitados.add(empleado);
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("Error procesando empleado ID " +
                                   (empleado != null ? empleado.getId() : "null") +
                                   ": " + e.getMessage());
            }
        }

        return empleadosCapacitados;
    }
    
    public void asignarTurno(Empleado empleado, Date fecha, String turno, boolean horasExtras) throws EmpleadoException {
        if (empleado == null || fecha == null || turno == null) {
            throw new EmpleadoException("El empleado, la fecha y el turno no pueden ser nulos");
        }

        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }

        if (!parque.getEmpleados().contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        if (!parque.getAsignaciones().containsKey(fecha)) {
            parque.getAsignaciones().put(fecha, new HashMap<>());
        }

        Map<String, Map<Empleado, Object>> asignacionesFecha = parque.getAsignaciones().get(fecha);

        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }

        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        asignacionesTurno.put(empleado, horasExtras);
    }

    
    
    public Map<Atraccion, List<Date>> consultarCalendarioAtracciones() {
        return parque.consultarCalendarioAtracciones();
    }
   
    public List<Empleado> obtenerEmpleadosAsignadosTurno(Date fecha, String turno) {
        return parque.obtenerEmpleadosAsignadosTurno(fecha, turno);
    }
    
    
    public boolean estaEmpleadoAsignado(Empleado empleado, Date fecha, String turno) {
        return parque.estaEmpleadoAsignado(empleado, fecha, turno);
    }
    
    
    public Object obtenerLugarAsignado(Empleado empleado, Date fecha, String turno) {
        return parque.obtenerLugarAsignado(empleado, fecha, turno);
    }
    
    public boolean liberarAsignacion(Empleado empleado, Date fecha, String turno) throws EmpleadoException {
        return parque.liberarAsignacion(empleado, fecha, turno);
    }
    
    public String getNombre() {
        return nombre;
    }
    
   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public int getId() {
        return id;
    }
    
    
    public List<Atraccion> getAtracciones() {
        return atracciones;}
    
    
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    
    
    public List<Espectaculo> getEspectaculos() {
        return espectaculos;
    }

    
   
    public void repartirTurnos(Empleado empleado) throws EmpleadoException {
        if (empleado == null) {
            throw new EmpleadoException("El empleado no puede ser nulo");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
    }
    
    
    public void gestionarDescuentoEmpleado(Tiquete tiquete, Empleado empleado) throws EmpleadoException {
        if (tiquete == null || empleado == null) {
            throw new EmpleadoException("El tiquete y el empleado no pueden ser nulos");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
        tiquete.setDctoEmpleado(true);
    }
    
   
    public void gestionarCapacitacionEmpleado(Empleado empleado, String tipoCapacitacion) throws EmpleadoException {
        if (empleado == null || tipoCapacitacion == null || tipoCapacitacion.isEmpty()) {
            throw new EmpleadoException("El empleado y el tipo de capacitación no pueden ser nulos o vacíos");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
    }
    
    
    public Map<Date, Integer> generarReporteVentasPorPeriodo(Date fechaInicio, Date fechaFin) {
        Map<Date, Integer> reporteVentas = new HashMap<>();
        
        if (fechaInicio == null || fechaFin == null || fechaInicio.after(fechaFin)) {
            return reporteVentas;
        }
        
        
        return reporteVentas;
    }
    
    
    public Map<Atraccion, Integer> generarReporteAfluenciaPorAtraccion(Date fecha) {
        Map<Atraccion, Integer> reporteAfluencia = new HashMap<>();
        
        if (fecha == null) {
            return reporteAfluencia;
        }
        
        
        return reporteAfluencia;
    }
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Administrador other = (Administrador) obj;
        return id == other.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Administrador [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
    }

    public Map<Date, Map<String, Map<Empleado, Object>>> getAsignacionesEmpleados() {
        return asignacionesEmpleados;
    }
    
    public List<Cafeteria> getCafeterias() {
        return cafeterias;
    }

    public void agregarCafeteria(Cafeteria cafeteria) {
        cafeterias.add(cafeteria);
    }
    
    public void setParque(Parque parque) {
        this.parque = parque;
    }
    
    public Parque getParque() {
        return parque;
    }




}