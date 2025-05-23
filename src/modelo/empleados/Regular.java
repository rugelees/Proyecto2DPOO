package modelo.empleados;

import modelo.lugares.LugarServicio;


public class Regular extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private boolean puedeSerCajero;
    private LugarServicio lugarAsignado;
    

    public Regular(boolean puedeSerCajero, String tipo, String nombre, int id, boolean servicioGeneral, 
            String email, String password, boolean horasExtras) {
        super(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
        this.puedeSerCajero = puedeSerCajero;
    }
    

    public boolean asignarServicioGeneral() {
        this.servicioGeneral = true;
        this.lugarAsignado = null; 
        return true;
    }
    
    
    public boolean asignarCajero(LugarServicio lugarServicio) {
        if (lugarServicio == null) {
            return false;
        }
        
        if (!puedeSerCajero) {
            return false;
        }
        
        this.lugarAsignado = lugarServicio;
        this.servicioGeneral = false; 
        return true;
    }
    
   
    public boolean isPuedeSerCajero() {
        return puedeSerCajero;
    }
    
    
    public void setPuedeSerCajero(boolean puedeSerCajero) {
        this.puedeSerCajero = puedeSerCajero;
    }
    
    
    public LugarServicio getLugarAsignado() {
        return lugarAsignado;
    }
    
    
    public void setLugarAsignado(LugarServicio lugarAsignado) {
        this.lugarAsignado = lugarAsignado;
        
        if (lugarAsignado != null) {
            this.servicioGeneral = false;
        }
    }


	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}