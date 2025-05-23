package Principal;

import java.io.IOException;
import java.util.*;

import modelo.empleados.Empleado;
import modelo.atracciones.Atraccion;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;
import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.lugares.Cafeteria;
import modelo.lugares.LugarServicio;
import modelo.lugares.Taquilla;
import modelo.lugares.Tienda;
import persistencia.GuardarCargar;
import persistencia.PersistenciaEmpleados;
import persistencia.PersistenciaLugares;
import excepciones.AtraccionException;
import excepciones.EmpleadoException;
import funcionesrecurrentes.Recurrente;

public class Parque {

	    private List<Empleado> empleados;
	    private List<Atraccion> atracciones;
	    private Map<Date, Map<String, Map<Empleado, Object>>> asignaciones;
	    private List<Espectaculo> espectaculos;
	    private List<Cocinero> cocineros;
	    private List<Cafeteria> cafeterias = new ArrayList<>();
	    private List<LugarServicio> lugares = new ArrayList<>();
	    private List<Taquilla> taquillas = new ArrayList<>();
	    private List<Tienda> tiendas = new ArrayList<>();




	    
	    public Parque()throws IOException  {
	        this.empleados = new ArrayList<>();
	        this.atracciones = new ArrayList<>();
	        this.asignaciones = new HashMap<>();
	        this.espectaculos = new ArrayList<>();
	        this.cocineros = new ArrayList<>();
	        this.lugares = new ArrayList<>();
	        

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
	        
	        if (empleado instanceof Cocinero cocinero) {
	            cocineros.add(cocinero);
	        }
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            asignaciones.put(fecha, new HashMap<>());
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
	        if (!asignacionesFecha.containsKey(turno)) {
	            asignacionesFecha.put(turno, new HashMap<>());
	        }
	        
	        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
	        
	        if (asignacionesTurno.containsKey(empleado)) {
	            throw new EmpleadoException("El empleado ya está asignado en ese turno");
	        }
	        
	        asignacionesTurno.put(empleado, atraccion);
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            asignaciones.put(fecha, new HashMap<>());
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            asignaciones.put(fecha, new HashMap<>());
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            return false;	
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	    
	    public List<Empleado> getEmpleados() {
	        return empleados;
	    }
	    
	    public Map<Date, Map<String, Map<Empleado, Object>>> getAsignaciones() {
	        return asignaciones;
	    }

	    
	    public void setAsignaciones(Map<Date, Map<String, Map<Empleado, Object>>> asignacionesEmpleados) {
	        this.asignaciones = asignacionesEmpleados;
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            return empleadosAsignados;
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            return false;
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            return null;
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
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
	        
	        if (!asignaciones.containsKey(fecha)) {
	            return false;
	        }
	        
	        Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
	        
	        if (!asignacionesFecha.containsKey(turno)) {
	            return false;
	        }
	        
	        Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
	        return asignacionesTurno.remove(empleado) != null;
	    }
	    public List<Cafeteria> obtenerCafeteriasAsignadasAEmpleados() {
	        List<Cafeteria> cafeteriasAsignadas = new ArrayList<>();
	        for (Empleado e : empleados) {
	            if (e.puedeSerCocinero()) {
	                Cafeteria caf = e.getCafeteriaAsignada();
	                if (caf != null) {
	                    cafeteriasAsignadas.add(caf);
	                }
	            }
	        }
	        return cafeteriasAsignadas;
	    }
	    
	    public void cargarCocineros(PersistenciaEmpleados persistencia) throws EmpleadoException {
	        List<Cocinero> cocinerosCargados = persistencia.cargarEmpleadosCocinero(); 
	        for (Cocinero cocinero : cocinerosCargados) {
	            this.agregarEmpleado(cocinero); 
	        }
	    }

	    public Cafeteria buscarCafeteriaPorNombre(String nombre) {
	        for (Cafeteria c : cafeterias) { // donde listaCafeterias es la colección de cafeterías en el parque
	            if (c.getNombre().equalsIgnoreCase(nombre)) {
	                return c;
	            }
	        }
	        return null;
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
		    
		    if (!asignaciones.containsKey(fecha)) {
		        asignaciones.put(fecha, new HashMap<>());
		    }
		    
		    Map<String, Map<Empleado, Object>> asignacionesFecha = asignaciones.get(fecha);
		    
		    if (!asignacionesFecha.containsKey(turno)) {
		        asignacionesFecha.put(turno, new HashMap<>());
		    }
		    
		    Map<Empleado, Object> asignacionesTurno = asignacionesFecha.get(turno);
		    
		    if (asignacionesTurno.containsKey(cocinero)) {
		        throw new EmpleadoException("El cocinero ya está asignado en ese turno");
		    }
		    
		    asignacionesTurno.put(cocinero, cafeteria);
		}

		public Cafeteria crearCafeteria(String id, String nombre, String ubicacion, List<String> menu, int capacidad) {
		    Cafeteria cafeteria = new Cafeteria(id, nombre, ubicacion, menu, capacidad);
		    cafeterias.add(cafeteria);
		    return cafeteria;
		}
		
		public List<Tienda> getTiendas(){
			return tiendas;
		}
		
		
		public List<Taquilla> getTaquillas(){
			return taquillas;
		}
		public List<Cafeteria> getCafeterias(){
			return cafeterias;
		}


		public Taquilla crearTaquilla(String id, String nombre, String ubicacion, String metodoPago) {
		    Taquilla taquilla = new Taquilla(id, nombre, ubicacion, metodoPago);
		    taquillas.add(taquilla);
		    return taquilla;
		}

		public Tienda crearTienda(String id, String nombre, String ubicacion, String tipoProductos, Map<String, Integer> inventario) {
		    Tienda tienda = new Tienda(id, nombre, ubicacion, tipoProductos, inventario);
		    tiendas.add(tienda);
		    return tienda;
		}

		public void cargarLugares(PersistenciaLugares persistencia) throws IOException {
		    this.cafeterias = persistencia.cargarCafeterias();
		    this.taquillas = persistencia.cargarTaquillas();
		    this.tiendas = persistencia.cargarTiendas();
		    
		    this.lugares.clear();
		    this.lugares.addAll(cafeterias);
		    this.lugares.addAll(taquillas);
		    this.lugares.addAll(tiendas);
		}



		
		
	    public void setEmpleados(List<Empleado> empleados) {
	        this.empleados = empleados;
	    }
     
	    

	    
	    
}
