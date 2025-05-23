package modelo.lugares;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cafeteria extends LugarServicio {
    private static final long serialVersionUID = 1L;
    
    private List<String> menu;
    private int capacidad;
    

    public Cafeteria(String id, String nombre, String ubicacion, List<String> menu, int capacidad) {
        super(id, nombre, ubicacion, "Cafeteria", true); 
        this.menu = menu != null ? new ArrayList<>(menu) : new ArrayList<>();
        this.capacidad = capacidad;
    }
    

    public boolean verificarPersonalMinimo(Date fecha, String turno) {
        return tieneCajeroAsignado(fecha, turno) && tieneCocineroAsignado(fecha, turno);
    }
    
    
    public boolean agregarPlato(String plato) {
        if (plato == null || plato.isEmpty()) {
            return false;
        }
        
        if (menu.contains(plato)) {
            return true; 
        }
        
        menu.add(plato);
        return true;
    }
    
  
    public boolean removerPlato(String plato) {
        return menu.remove(plato);
    }
    
    
    public List<String> getMenu() {
        return new ArrayList<>(menu);
    }
    
    
    public void setMenu(List<String> menu) {
        this.menu = menu != null ? new ArrayList<>(menu) : new ArrayList<>();
    }
    
    
    public int getCapacidad() {
        return capacidad;
    }
    
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    @Override
    public String toString() {
        return "Cafeteria [nombre=" + nombre + ", ubicacion=" + ubicacion + ", capacidad=" + capacidad + ", items en menu=" + menu.size() + "]";
    }
}