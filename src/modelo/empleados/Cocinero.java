package modelo.empleados;

import modelo.lugares.Cafeteria;


public class Cocinero extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private boolean capacitado;
    private Cafeteria cafeteriaAsignada;
    

    public Cocinero(boolean capacitado, String tipo, String nombre, int id, boolean servicioGeneral, 
            String email, String password, boolean horasExtras) {
        super(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
        this.capacitado = capacitado;
    }
    
  
   
    public boolean puedeTrabajarCaja() {
        return true;
    }
    
    
    public boolean asignarCocina(Cafeteria cafeteria) {
        if (cafeteria == null) {
            return false;
        }
        
        if (!capacitado) {
            return false;
        }
        
        this.cafeteriaAsignada = cafeteria;
        return true;
    }
    
   
    public boolean isCapacitado() {
        return capacitado;
    }
    
    
    public void setCapacitado(boolean capacitado) {
        this.capacitado = capacitado;
    }
    
   
    public Cafeteria getCafeteriaAsignada() {
        return cafeteriaAsignada;
    }
    
    
    public void setCafeteriaAsignada(Cafeteria cafeteriaAsignada) {
        this.cafeteriaAsignada = cafeteriaAsignada;
    }
}