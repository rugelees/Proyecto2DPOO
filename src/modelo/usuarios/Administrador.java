package modelo.usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    
    public Administrador(String nombre, int id, String email, String password) {
        super(email, password);
        this.nombre = nombre;
        this.id = id;
        this.atracciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.espectaculos = new ArrayList<>();
        this.asignacionesEmpleados = new HashMap<>();
    }
    
    
    public void cambiarInfoAtraccion(Atraccion atraccion) throws AtraccionException {
        if (atraccion == null) {
            throw new AtraccionException("La atracción no puede ser nula");
        }
        
        for (int i = 0; i < atracciones.size(); i++) {
            if (atracciones.get(i).getNombre().equals(atraccion.getNombre())) {
                atracciones.set(i, atraccion);
                return;
            }
        }
        
        throw new AtraccionException("La atracción no existe en el sistema");
    }
     
    
    public void cambiarInfoEmpleado(Empleado empleado) throws EmpleadoException {
        if (empleado == null) {
            throw new EmpleadoException("El empleado no puede ser nulo");
        }
        
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == empleado.getId()) {
                empleados.set(i, empleado);
                return;
            }
        }
        
        throw new EmpleadoException("El empleado no existe en el sistema");
    }
    
   
    public void agregarAtraccion(Atraccion atraccion) throws AtraccionException {
        if (atraccion == null) {
            throw new AtraccionException("La atracción no puede ser nula");
        }
        
        for (Atraccion a : atracciones) {
            if (a.getNombre().equals(atraccion.getNombre())) {
                throw new AtraccionException("Ya existe una atracción con ese nombre");
            }
        }
        
        atracciones.add(atraccion);
    }
    
    
    public void eliminarAtraccion(Atraccion atraccion) throws AtraccionException {
        if (atraccion == null) {
            throw new AtraccionException("La atracción no puede ser nula");
        }
        
        if (!atracciones.remove(atraccion)) {
            throw new AtraccionException("La atracción no existe en el sistema");
        }
    }
    
   
    public void agregarEmpleado(Empleado empleado) throws EmpleadoException {
        if (empleado == null) {
            throw new EmpleadoException("El empleado no puede ser nulo");
        }
        
        for (Empleado e : empleados) {
            if (e.getId() == empleado.getId()) {
                throw new EmpleadoException("Ya existe un empleado con ese ID");
            }
        }
        
        empleados.add(empleado);
    }
    
    
    public void eliminarEmpleado(Empleado empleado) throws EmpleadoException {
        if (empleado == null) {
            throw new EmpleadoException("El empleado no puede ser nulo");
        }
        
        if (!empleados.remove(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
    }
    
    
    public void asignarEmpleadoAtraccion(Empleado empleado, AtraccionMecanica atraccion, Date fecha, String turno) 
            throws EmpleadoException {
        if (empleado == null || atraccion == null || fecha == null || turno == null) {
            throw new EmpleadoException("Los parámetros no pueden ser nulos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
        if (!atracciones.contains(atraccion)) {
            throw new EmpleadoException("La atracción no existe en el sistema");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            asignacionesEmpleados.put(fecha, new HashMap<>());
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        
        if (asignacionesTurno.containsKey(empleado)) {
            throw new EmpleadoException("El empleado ya está asignado en ese turno");
        }
        
        asignacionesTurno.put(empleado, atraccion);
    }
    
    
    public void asignarCocineroACafeteria(Cocinero cocinero, Cafeteria cafeteria, Date fecha, String turno) 
            throws EmpleadoException {
        if (cocinero == null || cafeteria == null || fecha == null || turno == null) {
            throw new EmpleadoException("Los parámetros no pueden ser nulos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!empleados.contains(cocinero)) {
            throw new EmpleadoException("El cocinero no existe en el sistema");
        }
        
        if (!cocinero.isCapacitado()) {
            throw new EmpleadoException("El cocinero no está capacitado");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            asignacionesEmpleados.put(fecha, new HashMap<>());
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        
        if (asignacionesTurno.containsKey(cocinero)) {
            throw new EmpleadoException("El cocinero ya está asignado en ese turno");
        }
        
        asignacionesTurno.put(cocinero, cafeteria);
    }
    
  
    public void asignarCajeroALugarServicio(Cajero cajero, LugarServicio lugarServicio, Date fecha, String turno) 
            throws EmpleadoException {
        if (cajero == null || lugarServicio == null || fecha == null || turno == null) {
            throw new EmpleadoException("Los parámetros no pueden ser nulos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!empleados.contains(cajero)) {
            throw new EmpleadoException("El cajero no existe en el sistema");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            asignacionesEmpleados.put(fecha, new HashMap<>());
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        
        if (asignacionesTurno.containsKey(cajero)) {
            throw new EmpleadoException("El cajero ya está asignado en ese turno");
        }
        
        asignacionesTurno.put(cajero, lugarServicio);
    }
    
   
    public void asignarEmpleadoServicioGeneral(Empleado empleado, String[] zonas, Date fecha, String turno) 
            throws EmpleadoException {
        if (empleado == null || zonas == null || zonas.length == 0 || fecha == null || turno == null) {
            throw new EmpleadoException("Los parámetros no pueden ser nulos o vacíos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            asignacionesEmpleados.put(fecha, new HashMap<>());
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        
        if (asignacionesTurno.containsKey(empleado)) {
            throw new EmpleadoException("El empleado ya está asignado en ese turno");
        }
        
        asignacionesTurno.put(empleado, zonas);
    }
    
    
    public boolean verificarPersonalMinimo(Atraccion atraccion, Date fecha, String turno) {
        if (atraccion == null || fecha == null || !Recurrente.esValido(turno)) {
            return false;
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            return false;
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return false;
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        
        int empleadosAsignados = 0;
        for (Map.Entry<Empleado, Object> entry : asignacionesTurno.entrySet()) {
            if (entry.getValue() == atraccion) {
                empleadosAsignados++;
            }
        }
        
        return empleadosAsignados >= atraccion.getEmpleadosEncargados();
    }
    
    
    public void gestionarMantenimientoAtracciones(AtraccionMecanica atraccion, Date fechaInicio, Date fechaFin) 
            throws AtraccionException {
        if (atraccion == null || fechaInicio == null || fechaFin == null) {
            throw new AtraccionException("La atracción y las fechas no pueden ser nulas");
        }
        
        if (fechaInicio.after(fechaFin)) {
            throw new AtraccionException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        
        if (!atracciones.contains(atraccion)) {
            throw new AtraccionException("La atracción no existe en el sistema");
        }
        
        atraccion.programarMantenimiento(fechaInicio, fechaFin);
    }
    
    
    public void crearEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        if (espectaculo == null) {
            throw new AtraccionException("El espectáculo no puede ser nulo");
        }
        
        for (Espectaculo e : espectaculos) {
            if (e.getNombre().equals(espectaculo.getNombre())) {
                throw new AtraccionException("Ya existe un espectáculo con ese nombre");
            }
        }
        
        espectaculos.add(espectaculo);
    }
    
    
    public void modificarEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        if (espectaculo == null) {
            throw new AtraccionException("El espectáculo no puede ser nulo");
        }
        
        for (int i = 0; i < espectaculos.size(); i++) {
            if (espectaculos.get(i).getNombre().equals(espectaculo.getNombre())) {
                espectaculos.set(i, espectaculo);
                return;
            }
        }
        
        throw new AtraccionException("El espectáculo no existe en el sistema");
    }
    
    
    public void eliminarEspectaculo(Espectaculo espectaculo) throws AtraccionException {
        if (espectaculo == null) {
            throw new AtraccionException("El espectáculo no puede ser nulo");
        }
        
        if (!espectaculos.remove(espectaculo)) {
            throw new AtraccionException("El espectáculo no existe en el sistema");
        }
    }
    
    
    public void gestionarAtraccionesTemporada(Atraccion atraccion, boolean deTemporada, Date fechaInicio, Date fechaFin) 
            throws AtraccionException {
        if (atraccion == null) {
            throw new AtraccionException("La atracción no puede ser nula");
        }
        
        if (deTemporada && (fechaInicio == null || fechaFin == null)) {
            throw new AtraccionException("Si la atracción es de temporada, las fechas no pueden ser nulas");
        }
        
        if (deTemporada && fechaInicio.after(fechaFin)) {
            throw new AtraccionException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        
        if (!atracciones.contains(atraccion)) {
            throw new AtraccionException("La atracción no existe en el sistema");
        }
        
        atraccion.setDeTemporada(deTemporada);
        
        if (deTemporada) {
            atraccion.setFechaInicio(fechaInicio);
            atraccion.setFechaFin(fechaFin);
        }
    }
    
    
    public Map<String, Integer> consultarEstadisticasVentas() {
        Map<String, Integer> estadisticas = new HashMap<>();
        
        
        return estadisticas;
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
        if (this.empleados == null) {
             System.err.println("Error: La lista de empleados no ha sido inicializada.");
             return empleadosCapacitados;
        }

        for (Empleado empleado : this.empleados) {
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
    
    
    public void asignarTurno(Empleado empleado, Date fecha, String turno) throws EmpleadoException {
        if (empleado == null || fecha == null || turno == null) {
            throw new EmpleadoException("El empleado, la fecha y el turno no pueden ser nulos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!empleados.contains(empleado)) {
            throw new EmpleadoException("El empleado no existe en el sistema");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            asignacionesEmpleados.put(fecha, new HashMap<>());
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new HashMap<>());
        }
        
    }
    
    
    public Map<Atraccion, List<Date>> consultarCalendarioAtracciones() {
        Map<Atraccion, List<Date>> calendario = new HashMap<>();
        
        for (Atraccion atraccion : atracciones) {
            List<Date> fechasDisponibles = new ArrayList<>();
            calendario.put(atraccion, fechasDisponibles);
        }
        
        return calendario;
    }
    
   
    public List<Empleado> obtenerEmpleadosAsignadosTurno(Date fecha, String turno) {
        List<Empleado> empleadosAsignados = new ArrayList<>();
        
        if (fecha == null || turno == null || !Recurrente.esValido(turno)) {
            return empleadosAsignados;
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            return empleadosAsignados;
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return empleadosAsignados;
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        empleadosAsignados.addAll(asignacionesTurno.keySet());
        
        return empleadosAsignados;
    }
    
    
    public boolean estaEmpleadoAsignado(Empleado empleado, Date fecha, String turno) {
        if (empleado == null || fecha == null || turno == null || !Recurrente.esValido(turno)) {
            return false;
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            return false;
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return false;
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        return asignacionesTurno.containsKey(empleado);
    }
    
     
    public Object obtenerLugarAsignado(Empleado empleado, Date fecha, String turno) {
        if (empleado == null || fecha == null || turno == null || !Recurrente.esValido(turno)) {
            return null;
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            return null;
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return null;
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        return asignacionesTurno.get(empleado);
    }
    
    
    public boolean liberarAsignacion(Empleado empleado, Date fecha, String turno) throws EmpleadoException {
        if (empleado == null || fecha == null || turno == null) {
            throw new EmpleadoException("El empleado, la fecha y el turno no pueden ser nulos");
        }
        
        if (!Recurrente.esValido(turno)) {
            throw new EmpleadoException("El turno no es válido");
        }
        
        if (!asignacionesEmpleados.containsKey(fecha)) {
            return false;
        }
        
        Map<String, Map<Empleado, Object>> asignacionesFecha = asignacionesEmpleados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return false;
        }
        
        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
        return asignacionesTurno.remove(empleado) != null;
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
}