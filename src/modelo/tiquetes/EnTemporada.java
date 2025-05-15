package modelo.tiquetes;

import java.util.Date;

import modelo.atracciones.Atraccion;
import funcionesrecurrentes.Recurrente;


public class EnTemporada extends Tiquete {
    private static final long serialVersionUID = 1L;
    
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoTemporada;
    private String categoria;
    
    
    public EnTemporada(int id, String nombre, int numTiquetes, String exclusividad, Date fecha, String estado, 
            String portalCompra, Date fechaInicio, Date fechaFin, String tipoTemporada, String categoria, boolean usado) {
        super(id, nombre, numTiquetes, exclusividad, fecha, estado, portalCompra, usado);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoTemporada = tipoTemporada;
        this.categoria = categoria;
    }
    
    
    public boolean estaVigente(Date fecha) {
        if (fecha == null || fechaInicio == null || fechaFin == null) {
            return false;
        }
        
        return !fecha.before(fechaInicio) && !fecha.after(fechaFin);
    }
    
    @Override
    public boolean puedeAccederAtraccion(Atraccion atraccion) {
        if (atraccion == null) {
            return false;
        }
        
        Date ahora = new Date();
        if (!estaVigente(ahora)) {
            return false;
        }
        
        return Recurrente.tieneAcceso(exclusividad, atraccion.getNivelExclusividad());
    }
    

    public Date getFechaInicio() {
        return fechaInicio != null ? new Date(fechaInicio.getTime()) : null;
    }
    
   
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? new Date(fechaInicio.getTime()) : null;
    }
    
    
    public Date getFechaFin() {
        return fechaFin != null ? new Date(fechaFin.getTime()) : null;
    }
    
   
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? new Date(fechaFin.getTime()) : null;
    }
    

    public String getTipoTemporada() {
        return tipoTemporada;
    }
    
    
    public void setTipoTemporada(String tipoTemporada) {
        this.tipoTemporada = tipoTemporada;
    }
    

    public String getCategoria() {
        return categoria;
    }
    
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "EnTemporada [id=" + id + ", nombre=" + nombre + ", exclusividad=" + exclusividad + ", tipoTemporada=" + tipoTemporada 
                + ", vigencia=" + fechaInicio + " a " + fechaFin + ", estado=" + estado + ", usado=" + usado + "]";
    }
}