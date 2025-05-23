package modelo.tiquetes;

import java.util.Date;

import modelo.atracciones.Atraccion;
import funcionesrecurrentes.Recurrente;


public class TiqueteBasico extends Tiquete {
    private static final long serialVersionUID = 1L;
    
    private String categoria;

    public TiqueteBasico(int id, String nombre, int numTiquetes, String exclusividad, Date fecha, String estado, 
            String portalCompra, String categoria, boolean usado) {
        super(id, nombre, numTiquetes, exclusividad, fecha, estado, portalCompra, usado);
        this.categoria = categoria;
    }
    
    @Override
    public boolean puedeAccederAtraccion(Atraccion atraccion) {
        if (atraccion == null) {
            return false;
        }
        
        return Recurrente.tieneAcceso(exclusividad, atraccion.getNivelExclusividad());
    }
    
    
    public String getCategoria() {
        return categoria;
    }
    
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "TiqueteBasico [id=" + id + ", nombre=" + nombre + ", exclusividad=" + exclusividad + ", categoria=" + categoria + ", estado=" + estado + ", usado=" + usado + "]";
    }
}