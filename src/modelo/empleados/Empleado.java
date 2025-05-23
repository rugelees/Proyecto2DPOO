package modelo.empleados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import excepciones.EmpleadoException;
import modelo.lugares.Cafeteria;
import modelo.usuarios.Usuario;


public abstract class Empleado extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String tipo;
    protected String nombre;
    protected int id;
    protected boolean servicioGeneral;
    protected boolean horasExtras;
    private List<Turno> turnos;
    

    public Empleado(String tipo, String nombre, int id, boolean servicioGeneral, String email, String password, boolean horasExtras) {
        super(email, password);
        this.tipo = tipo;
        this.nombre = nombre;
        this.id = id;
        this.servicioGeneral = servicioGeneral;
        this.horasExtras = horasExtras;
        this.turnos = new ArrayList<>();
    }
    
  
    public boolean esServicioGeneral() {
        return servicioGeneral;
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
    
    public int getHorasExtra() {
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

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }
    
    public void validarLugarTrabajo(Object lugarTrabajo) throws EmpleadoException {
        throw new EmpleadoException("No se permite asignar lugar de trabajo genérico para empleado");
    }
    public boolean requiereCapacitacion() {
        return false;
    }
    public boolean isCapacitado() {
        return true;
    }
    public boolean puedeSerCajero() {
        return false; 
    }
    public boolean puedeSerCocinero() {
        return false; 
    }
    public Cafeteria getCafeteriaAsignada() {
        return null;
    }
    public abstract String getTipo();

    
}