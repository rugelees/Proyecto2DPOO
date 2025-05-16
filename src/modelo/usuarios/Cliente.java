package modelo.usuarios;
import java.util.ArrayList;
import java.util.List;
import excepciones.TiqueteException;
import modelo.tiquetes.Tiquete;
import modelo.tiquetes.FastPass;


public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private List<Tiquete> tiquetes;
    private String nombre;
    private int id;
    private float altura;
    private float peso;
    private int edad;
    private List<String> condicionesSalud;
    private boolean esEmpleado;
    
   
    public Cliente(String nombre, int id, String email, String password) {
        super(email, password);
        this.nombre = nombre;
        this.id = id;
        this.tiquetes = new ArrayList<>();
        this.condicionesSalud = new ArrayList<>();
        this.esEmpleado = false;
    }
    
    
    public Cliente(String nombre, int id, String email, String password, float altura, float peso, int edad) {
        this(nombre, id, email, password);
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
    }
    
   
    public boolean comprarTiquete(Tiquete tiquete) throws TiqueteException {
        if (tiquete == null) {
            throw new TiqueteException("El tiquete no puede ser nulo");
        }
        
        tiquetes.add(tiquete);
        return true;
    }
    
   
    public void agregarCondicionSalud(String condicion) {
        if (condicion != null && !condicion.isEmpty()) {
            condicionesSalud.add(condicion);
        }
    }
    
    
    public List<Tiquete> getTiquetes() {
        return new ArrayList<>(tiquetes);
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
    
   
    public float getAltura() {
        return altura;
    }
    
  
    public void setAltura(float altura) {
        this.altura = altura;
    }
    
    
    public float getPeso() {
        return peso;
    }
    
    
    public void setPeso(float peso) {
        this.peso = peso;
    }
    
   
    public int getEdad() {
        return edad;
    }
    
  
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    
    public List<String> getCondicionesSalud() {
        return new ArrayList<>(condicionesSalud);
    }
    
   
    public boolean tieneCondicionSalud(String condicion) {
        return condicionesSalud.contains(condicion);
    }
    
    public void agregarFastPass(FastPass fastPass) {
        tiquetes.add(fastPass.getTiqueteAsociado());
    }
    
    public boolean isEmpleado() {
        return esEmpleado;
    }
    
    public void setEmpleado(boolean esEmpleado) {
        this.esEmpleado = esEmpleado;
    }
    
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
    }
}
