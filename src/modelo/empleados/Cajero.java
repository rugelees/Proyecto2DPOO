package modelo.empleados;

import excepciones.TiqueteException;
import modelo.lugares.LugarServicio;
import modelo.tiquetes.Tiquete;
import modelo.usuarios.Cliente;


public class Cajero extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private LugarServicio lugarAsignado;
    
    
    public Cajero(String tipo, String nombre, int id, boolean servicioGeneral, 
            String email, String password, boolean horasExtras) {
        super(tipo, nombre, id, servicioGeneral, email, password, horasExtras);
    }
    
    
    public boolean venderTiquete(Cliente cliente, Tiquete tiquete) throws TiqueteException {
        if (cliente == null || tiquete == null) {
            throw new TiqueteException("El cliente y el tiquete no pueden ser nulos");
        }
        
        
        return cliente.comprarTiquete(tiquete);
    }
    
    
    public void asignarLugarServicio(LugarServicio lugarServicio) {
        this.lugarAsignado = lugarServicio;
    }
    
    
    public LugarServicio getLugarAsignado() {
        return lugarAsignado;
    }
    
   
    public void setLugarAsignado(LugarServicio lugarAsignado) {
        this.lugarAsignado = lugarAsignado;
    }
}