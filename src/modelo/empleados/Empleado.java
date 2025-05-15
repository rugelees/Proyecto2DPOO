package modelo.empleados;

import java.io.Serializable;

import modelo.usuarios.Usuario;


public abstract class Empleado extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String tipo;
    protected String nombre;
    protected int id;
    protected boolean servicioGeneral;
    protected boolean horasExtras;
    

    public Empleado(String tipo, String nombre, int id, boolean servicioGeneral, String email, String password, boolean horasExtras) {
        super(email, password);
        this.tipo = tipo;
        this.nombre = nombre;
        this.id = id;
        this.servicioGeneral = servicioGeneral;
        this.horasExtras = horasExtras;
    }
    
  
    public boolean esServicioGeneral() {
        return servicioGeneral;
    }
    
  
    public String getTipo() {
        return tipo;
    }
    
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
   
    public String getNombre() {
        return nombre;
    }
    
   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public int getId() {
        return id;
    }
    
   
    public void setServicioGeneral(boolean servicioGeneral) {
        this.servicioGeneral = servicioGeneral;
    }
    
    
    public boolean isHorasExtras() {
        return horasExtras;
    }
    
    
    public void setHorasExtras(boolean horasExtras) {
        this.horasExtras = horasExtras;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Empleado other = (Empleado) obj;
        return id == other.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Empleado [tipo=" + tipo + ", nombre=" + nombre + ", id=" + id + "]";
    }
}