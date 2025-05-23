package modelo.empleados;

import java.util.List;

import excepciones.EmpleadoException;
import modelo.lugares.Cafeteria;


public class Cocinero extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private boolean capacitado;
    private Cafeteria cafeteriaAsignada;
    private int horasExtra;
    

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
    @Override
    public void validarLugarTrabajo(Object lugarTrabajo) throws EmpleadoException {
        if (!(lugarTrabajo instanceof Cafeteria)) {
            throw new EmpleadoException("Un cocinero sólo puede ser asignado a una Cafetería.");
        }
    }

    @Override
    public boolean requiereCapacitacion() {
        return true;
    }

    @Override
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
    
    @Override
    public boolean puedeSerCocinero() {
        return capacitado;
    }
    @Override
    public String getTipo() {
        return "Cocinero";
    }



}