package modelo.empleados;

import java.util.ArrayList;
import java.util.List;

import modelo.atracciones.AtraccionMecanica;


public class AtraccionAlto extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private boolean capacitado;
    private List<AtraccionMecanica> atraccionesEspecificas;
    
    
    public AtraccionAlto(String tipo, String nombre, int id, boolean servicioGeneral, String email, 
            String password, boolean horasExtras, boolean capacitado) {
        super(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
        this.capacitado = capacitado;
        this.atraccionesEspecificas = new ArrayList<>();
    }
    
   
    public boolean puedeOperarAtraccionRiesgoAlto() {
        return capacitado;
    }
    
    
    public boolean asignarAtraccionAlta(AtraccionMecanica atraccion) {
        if (atraccion == null || !atraccion.esRiesgoAlto()) {
            return false;
        }
        
        if (!capacitado) {
            return false;
        }
        
        if (atraccionesEspecificas.contains(atraccion)) {
            return true; 
        }
        
        atraccionesEspecificas.add(atraccion);
        return true;
    }
    

    public boolean estaCapacitadoParaAtraccion(AtraccionMecanica atraccion) {
        if (atraccion == null) {
            return false;
        }
        
        return atraccionesEspecificas.contains(atraccion);
    }
    
   
    public boolean isCapacitado() {
        return capacitado;
    }
    
   
    public void setCapacitado(boolean capacitado) {
        this.capacitado = capacitado;
    }
    
    
    public List<AtraccionMecanica> getAtraccionesEspecificas() {
        return new ArrayList<>(atraccionesEspecificas);
    }
    
    
    public boolean removerAtraccionEspecifica(AtraccionMecanica atraccion) {
        return atraccionesEspecificas.remove(atraccion);
    }


	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
}