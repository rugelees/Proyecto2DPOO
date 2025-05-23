package modelo.lugares;

import java.util.Date;
import java.util.List;

import modelo.empleados.Cajero;
import modelo.empleados.Cocinero;
import modelo.empleados.Empleado;

public class LugarServicio extends LugarTrabajo {
    private static final long serialVersionUID = 1L;
    
    private String tipoServicio;
    private boolean requiereCocinero;
    private boolean requiereCajero;
    

    public LugarServicio(String id, String nombre, String ubicacion, String tipoServicio, boolean requiereCocinero) {
        super(id, nombre, ubicacion);
        this.tipoServicio = tipoServicio;
        this.requiereCocinero = requiereCocinero;
    }
    

    public boolean tieneCajeroAsignado(Date fecha, String turno) {
        if (!requiereCajero) {
            return true; 
        }
        
        List<Empleado> empleados = getEmpleadosAsignados(fecha, turno);
        
        for (Empleado empleado : empleados) {
            if (empleado.puedeSerCocinero()) {
                return true;
            }
        }

        
        return false;
    
    }
    

    public boolean tieneCocineroAsignado(Date fecha, String turno) {
        if (!requiereCocinero) {
            return true; 
        }
        
        List<Empleado> empleados = getEmpleadosAsignados(fecha, turno);
        
        for (Empleado empleado : empleados) {
            if (empleado.puedeSerCocinero()) {
                return true;
            }
        }

        
        return false;
    }
    
    
    
    public boolean asignarCajero(Cajero cajero, Date fecha, String turno) {
        return asignarEmpleado(cajero, fecha, turno);
    }
    
    
    public boolean asignarCocinero(Cocinero cocinero, Date fecha, String turno) {
        if (!requiereCocinero) {
            return false; 
        }
        
        return asignarEmpleado(cocinero, fecha, turno);
    }
    

    public String getTipoServicio() {
        return tipoServicio;
    }
    
    
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    
   
    public boolean isRequiereCocinero() {
        return requiereCocinero;
    }
    
   
    public void setRequiereCocinero(boolean requiereCocinero) {
        this.requiereCocinero = requiereCocinero;
    }
    
    @Override
    public String toString() {
        return "LugarServicio [id=" + id + ", nombre=" + nombre + ", tipoServicio=" + tipoServicio + "]";
    }
}