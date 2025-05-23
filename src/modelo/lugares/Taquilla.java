package modelo.lugares;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import excepciones.TiqueteException;
import modelo.tiquetes.Tiquete;
import modelo.usuarios.Cliente;


public class Taquilla extends LugarServicio {
    private static final long serialVersionUID = 1L;
    
    private String metodoPago;
    private List<Tiquete> tiquetesDisponibles;
    
    
    public Taquilla(String id, String nombre, String ubicacion, String metodoPago) {
        super(id, nombre, ubicacion, "Taquilla", false);
        this.metodoPago = metodoPago;
        this.tiquetesDisponibles = new ArrayList<>();
    }
    
   
    public Tiquete venderTiquete(String tipo, Cliente cliente) throws TiqueteException {
        if (tipo == null || tipo.isEmpty() || cliente == null) {
            throw new TiqueteException("El tipo de tiquete y el cliente no pueden ser nulos");
        }
        
        Tiquete tiqueteVendido = null;
        for (Tiquete tiquete : tiquetesDisponibles) {
            if (tiquete.getNombre().equals(tipo) && !tiquete.isUsado()) {
                tiqueteVendido = tiquete;
                break;
            }
        }
        
        if (tiqueteVendido == null) {
            throw new TiqueteException("No hay tiquetes disponibles del tipo solicitado");
        }
        
        if (cliente.comprarTiquete(tiqueteVendido)) {
            tiquetesDisponibles.remove(tiqueteVendido);
            return tiqueteVendido;
        } else {
            throw new TiqueteException("Error al asignar el tiquete al cliente");
        }
    }
    

    public boolean agregarTiquete(Tiquete tiquete) {
        if (tiquete == null) {
            return false;
        }
        
        tiquetesDisponibles.add(tiquete);
        return true;
    }
    
    
    public boolean verificarPersonalMinimo(Date fecha, String turno) {
        return tieneCajeroAsignado(fecha, turno);
    }
    
   
    public String getMetodoPago() {
        return metodoPago;
    }
    
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    
    public List<Tiquete> getTiquetesDisponibles() {
        return new ArrayList<>(tiquetesDisponibles);
    }
    
    
    public void setTiquetesDisponibles(List<Tiquete> tiquetesDisponibles) {
        this.tiquetesDisponibles = tiquetesDisponibles != null ? new ArrayList<>(tiquetesDisponibles) : new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Taquilla [nombre=" + nombre + ", ubicacion=" + ubicacion + ", metodoPago=" + metodoPago + ", tiquetes disponibles=" + tiquetesDisponibles.size() + "]";
    }
}