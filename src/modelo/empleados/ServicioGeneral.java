package modelo.empleados;

import java.util.ArrayList;
import java.util.List;


public class ServicioGeneral extends Empleado {
    private static final long serialVersionUID = 1L;
    
    private List<String> zonasAsignadas;
    

    public ServicioGeneral(List<String> zonasAsignadas, String tipo, String nombre, int id,
            String email, String password, boolean horasExtras) {
        super(tipo, nombre, id, true, email, password, horasExtras);
        this.zonasAsignadas = zonasAsignadas != null ? new ArrayList<>(zonasAsignadas) : new ArrayList<>();
    }
    
    
    public boolean asignarZona(String zona) {
        if (zona == null || zona.isEmpty()) {
            return false;
        }
        
        if (zonasAsignadas.contains(zona)) {
            return true; 
        }
        
        zonasAsignadas.add(zona);
        return true;
    }
    
   
    public boolean removerZona(String zona) {
        return zonasAsignadas.remove(zona);
    }
    
    
    public List<String> getZonasAsignadas() {
        return new ArrayList<>(zonasAsignadas);
    }
    
    
    public void setZonasAsignadas(List<String> zonasAsignadas) {
        this.zonasAsignadas = zonasAsignadas != null ? new ArrayList<>(zonasAsignadas) : new ArrayList<>();
    }
    

    public boolean tieneZonaAsignada(String zona) {
        return zonasAsignadas.contains(zona);
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Zonas: " + zonasAsignadas;
    }


	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
}