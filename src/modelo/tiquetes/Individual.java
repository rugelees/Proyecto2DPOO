package modelo.tiquetes;

import java.util.Date;

import modelo.atracciones.Atraccion;


public class Individual extends Tiquete {
    private static final long serialVersionUID = 1L;
    
    private Atraccion atraccion;

    public Individual(Atraccion atraccion, int id, String nombre, int numTiquetes, String exclusividad, Date fecha, 
            String estado, String portalCompra, boolean usado) {
        super(id, nombre, numTiquetes, exclusividad, fecha, estado, portalCompra, usado);
        this.atraccion = atraccion;
    }
    
    @Override
    public boolean puedeAccederAtraccion(Atraccion atraccion) {
        if (atraccion == null || this.atraccion == null) {
            return false;
        }
        
        return this.atraccion.equals(atraccion) && !usado;
    }
    
   
    public Atraccion getAtraccion() {
        return atraccion;
    }
    
    
    public void setAtraccion(Atraccion atraccion) {
        this.atraccion = atraccion;
    }
    
    @Override
    public String toString() {
        return "Individual [id=" + id + ", nombre=" + nombre + ", atraccion=" + (atraccion != null ? atraccion.getNombre() : "N/A") 
                + ", estado=" + estado + ", usado=" + usado + "]";
    }
}