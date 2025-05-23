package modelo.lugares;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Tienda extends LugarServicio {
    private static final long serialVersionUID = 1L;
    
    private String tipoProductos;
    private Map<String, Integer> inventario;
    

    public Tienda(String id, String nombre, String ubicacion, String tipoProductos, Map<String, Integer> inventario) {
        super(id, nombre, ubicacion, "Tienda", false); 
        this.tipoProductos = tipoProductos;
        this.inventario = inventario != null ? new HashMap<>(inventario) : new HashMap<>();
    }
    
    
    public boolean verificarDisponibilidadProducto(String producto) {
        if (producto == null || producto.isEmpty()) {
            return false;
        }
        
        return inventario.containsKey(producto) && inventario.get(producto) > 0;
    }
     

    public boolean agregarProducto(String producto, int cantidad) {
        if (producto == null || producto.isEmpty() || cantidad <= 0) {
            return false;
        }
        
        if (inventario.containsKey(producto)) {
            int cantidadActual = inventario.get(producto);
            inventario.put(producto, cantidadActual + cantidad);
        } else {
            inventario.put(producto, cantidad);
        }
        
        return true;
    }
    
    
    public boolean removerProducto(String producto, int cantidad) {
        if (producto == null || producto.isEmpty() || cantidad <= 0) {
            return false;
        }
        
        if (!inventario.containsKey(producto) || inventario.get(producto) < cantidad) {
            return false;
        }
        
        int cantidadActual = inventario.get(producto);
        int nuevaCantidad = cantidadActual - cantidad;
        
        if (nuevaCantidad > 0) {
            inventario.put(producto, nuevaCantidad);
        } else {
            inventario.remove(producto);
        }
        
        return true;
    }
    
    
    public boolean verificarPersonalMinimo(Date fecha, String turno) {
        return tieneCajeroAsignado(fecha, turno);
    }
    
    
    public String getTipoProductos() {
        return tipoProductos;
    }
    
   
    public void setTipoProductos(String tipoProductos) {
        this.tipoProductos = tipoProductos;
    }
    
    
    public Map<String, Integer> getInventario() {
        return new HashMap<>(inventario);
    }
    
   
    public void setInventario(Map<String, Integer> inventario) {
        this.inventario = inventario != null ? new HashMap<>(inventario) : new HashMap<>();
    }
    
    @Override
    public String toString() {
        return "Tienda [nombre=" + nombre + ", ubicacion=" + ubicacion + ", tipoProductos=" + tipoProductos + ", items en inventario=" + inventario.size() + "]";
    }
}