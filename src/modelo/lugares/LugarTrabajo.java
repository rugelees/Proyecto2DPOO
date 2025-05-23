package modelo.lugares;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.empleados.Empleado;

public class LugarTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String nombre;
    protected String ubicacion;
    protected Map<Date, Map<String, List<Empleado>>> empleadosAsignados;
    

    public LugarTrabajo(String id, String nombre, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empleadosAsignados = new HashMap<>();
    }
    

    public boolean requiereCapacitacion(Empleado empleado) {
        return false;
    }
    

    public List<Empleado> getEmpleadosAsignados(Date fecha, String turno) {
        if (fecha == null || turno == null) {
            return new ArrayList<>();
        }
        
        if (!empleadosAsignados.containsKey(fecha)) {
            return new ArrayList<>();
        }
        
        Map<String, List<Empleado>> asignacionesFecha = empleadosAsignados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(asignacionesFecha.get(turno));
    }
    
    
    public boolean asignarEmpleado(Empleado empleado, Date fecha, String turno) {
        if (empleado == null || fecha == null || turno == null) {
            return false;
        }
        
        if (!empleadosAsignados.containsKey(fecha)) {
            empleadosAsignados.put(fecha, new HashMap<>());
        }
        
        Map<String, List<Empleado>> asignacionesFecha = empleadosAsignados.get(fecha);
        
        if (!asignacionesFecha.containsKey(turno)) {
            asignacionesFecha.put(turno, new ArrayList<>());
        }
        
        List<Empleado> asignacionesTurno = asignacionesFecha.get(turno);
        
        if (asignacionesTurno.contains(empleado)) {
            return true; 
        }
        
        asignacionesTurno.add(empleado);
        return true;
    }
    
    
    public String getId() {
        return id;
    }
    
    
    public void setId(String id) {
        this.id = id;
    }
    
    
    public String getNombre() {
        return nombre;
    }
    
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getUbicacion() {
        return ubicacion;
    }
    
   
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LugarTrabajo other = (LugarTrabajo) obj;
        return id != null && id.equals(other.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "LugarTrabajo [id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + "]";
    }}