package modelo.empleados;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.atracciones.AtraccionMecanica;


public class AtraccionMedio extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private boolean capacitado;
    private Date fechaCapacitacion;
    private Date fechaVencimientoCapacitacion;
    private List<AtraccionMecanica> atraccionesAsignadas;
    
   
    public AtraccionMedio(Date fechaCapacitacion, Date fechaVencimientoCapacitacion, String tipo, String nombre, 
            int id, boolean servicioGeneral, String email, String password, boolean horasExtras) {
        super(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
        this.fechaCapacitacion = fechaCapacitacion;
        this.fechaVencimientoCapacitacion = fechaVencimientoCapacitacion;
        this.capacitado = true; 
        this.atraccionesAsignadas = new ArrayList<>();
    }
    
   
    public boolean puedeOperarAtraccionRiesgoMedio() {
        if (!capacitado) {
            return false;
        }
        
        Date ahora = new Date();
        return !ahora.after(fechaVencimientoCapacitacion);
    }
    
   
    public boolean asignarAtraccionMedia(AtraccionMecanica atraccion) {
        if (atraccion == null || !atraccion.esRiesgoMedio()) {
            return false;
        }
        
        if (!puedeOperarAtraccionRiesgoMedio()) {
            return false;
        }
        
        if (atraccionesAsignadas.contains(atraccion)) {
            return true; 
        }
        
        atraccionesAsignadas.add(atraccion);
        return true;
    }
    
   
    public boolean isCapacitado() {
        return capacitado;
    }
    
    
    public void setCapacitado(boolean capacitado) {
        this.capacitado = capacitado;
    }
    
        public Date getFechaCapacitacion() {
        return fechaCapacitacion != null ? new Date(fechaCapacitacion.getTime()) : null;
    }
    
    
    public void setFechaCapacitacion(Date fechaCapacitacion) {
        this.fechaCapacitacion = fechaCapacitacion != null ? new Date(fechaCapacitacion.getTime()) : null;
    }
    
   
    public Date getFechaVencimientoCapacitacion() {
        return fechaVencimientoCapacitacion != null ? new Date(fechaVencimientoCapacitacion.getTime()) : null;
    }
    
    
    public void setFechaVencimientoCapacitacion(Date fechaVencimientoCapacitacion) {
        this.fechaVencimientoCapacitacion = fechaVencimientoCapacitacion != null ? 
                new Date(fechaVencimientoCapacitacion.getTime()) : null;
    }
    
    
    public List<AtraccionMecanica> getAtraccionesAsignadas() {
        return new ArrayList<>(atraccionesAsignadas);
    }
}