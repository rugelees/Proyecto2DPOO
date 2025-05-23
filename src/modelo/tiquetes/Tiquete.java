package modelo.tiquetes;

import java.io.Serializable;
import java.util.Date;

import modelo.atracciones.Atraccion;


public abstract class Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected String nombre;
    protected int numTiquetes;
    protected String exclusividad;
    protected Date fecha;
    protected String estado;
    protected boolean dctoEmpleado;
    protected String portalCompra;
    protected boolean usado;
    

    public Tiquete(int id, String nombre, int numTiquetes, String exclusividad, Date fecha, String estado, 
            String portalCompra, boolean usado) {
        this.id = id;
        this.nombre = nombre;
        this.numTiquetes = numTiquetes;
        this.exclusividad = exclusividad;
        this.fecha = fecha;
        this.estado = estado;
        this.dctoEmpleado = false; 
        this.portalCompra = portalCompra;
        this.usado = usado;
    }
    

    public boolean isUsado() {
        return usado;
    }
    
    
    public void marcarComoUsado() {
        this.usado = true;
    }
    
    
    public abstract boolean puedeAccederAtraccion(Atraccion atraccion);
    
    
    public int getId() {
        return id;
    }
    
   
    public String getNombre() {
        return nombre;
    }
    
  
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public int getNumTiquetes() {
        return numTiquetes;
    }
    
    
    public void setNumTiquetes(int numTiquetes) {
        this.numTiquetes = numTiquetes;
    }
    
    
    public String getExclusividad() {
        return exclusividad;
    }
    
   
    public void setExclusividad(String exclusividad) {
        this.exclusividad = exclusividad;
    }
    
   
    public Date getFecha() {
        return fecha != null ? new Date(fecha.getTime()) : null;
    }
    
    
    public void setFecha(Date fecha) {
        this.fecha = fecha != null ? new Date(fecha.getTime()) : null;
    }
    
    
    public String getEstado() {
        return estado;
    }
    
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    public boolean isDctoEmpleado() {
        return dctoEmpleado;
    }
    
   
    public void setDctoEmpleado(boolean dctoEmpleado) {
        this.dctoEmpleado = dctoEmpleado;
    }
    
   
    public String getPortalCompra() {
        return portalCompra;
    }
    
   
    public void setPortalCompra(String portalCompra) {
        this.portalCompra = portalCompra;
    }
    
    
    public void setUsado(boolean usado) {
        this.usado = usado;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tiquete other = (Tiquete) obj;
        return id == other.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Tiquete [id=" + id + ", nombre=" + nombre + ", exclusividad=" + exclusividad + ", estado=" + estado + ", usado=" + usado + "]";
    }
}