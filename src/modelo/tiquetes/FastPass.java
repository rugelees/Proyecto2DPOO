package modelo.tiquetes;

import java.io.Serializable;
import java.util.Date;


public class FastPass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Tiquete tiqueteAsociado;
    private Date fechaValida;
    private boolean usado;
    

    public FastPass(Tiquete tiqueteAsociado, Date fechaValida) {
        this.tiqueteAsociado = tiqueteAsociado;
        this.fechaValida = fechaValida;
        this.usado = false;
    }
    
    
    public boolean esValido(Date fecha) {
        if (fecha == null || fechaValida == null) {
            return false;
        }
        
        if (usado) {
            return false;
        }
        
        return mismodia(fecha, fechaValida);
    }
    
   
    public void marcarComoUsado() {
        this.usado = true;
    }
    
    
    private boolean mismodia(Date fecha1, Date fecha2) {
        long milisEnDia = 24 * 60 * 60 * 1000;
        long dia1 = fecha1.getTime() / milisEnDia;
        long dia2 = fecha2.getTime() / milisEnDia;
        return dia1 == dia2;
    }
    
 
    public Tiquete getTiqueteAsociado() {
        return tiqueteAsociado;
    }
    
    
    public void setTiqueteAsociado(Tiquete tiqueteAsociado) {
        this.tiqueteAsociado = tiqueteAsociado;
    }
    

    public Date getFechaValida() {
        return fechaValida != null ? new Date(fechaValida.getTime()) : null;
    }
    

    public void setFechaValida(Date fechaValida) {
        this.fechaValida = fechaValida != null ? new Date(fechaValida.getTime()) : null;
    }
    

    public boolean isUsado() {
        return usado;
    }
    

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
    
    @Override
    public String toString() {
        return "FastPass [tiqueteAsociado=" + (tiqueteAsociado != null ? tiqueteAsociado.getId() : "N/A") 
                + ", fechaValida=" + fechaValida + ", usado=" + usado + "]";
    }
}