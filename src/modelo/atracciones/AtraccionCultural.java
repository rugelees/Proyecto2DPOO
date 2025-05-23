package modelo.atracciones;

import java.util.Date;

import modelo.usuarios.Cliente;


public class AtraccionCultural extends Atraccion {
    
    private String ubicacion;
    private int cupoMaximo;
    private int edadMinima;
    
   
    public AtraccionCultural(String nombre, String restriccionClima, boolean deTemporada, Date fechaInicio, Date fechaFin,
            String nivelExclusividad, int empleadosEncargados, String ubicacion, int cupoMaximo, int edadMinima) {
        super(nombre, restriccionClima, deTemporada, fechaInicio, fechaFin, nivelExclusividad, empleadosEncargados);
        this.ubicacion = ubicacion;
        this.cupoMaximo = cupoMaximo;
        this.edadMinima = edadMinima;
    }
    
    
    public boolean verificarRestriccionEdad(Cliente cliente) {
        if (cliente == null) {
            return false;
        }
        
        return cliente.getEdad() >= edadMinima;
    }
    
    @Override
    public String consultarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Atracción Cultural: ").append(nombre).append("\n");
        info.append("Ubicación: ").append(ubicacion).append("\n");
        info.append("Nivel de Exclusividad: ").append(nivelExclusividad).append("\n");
        info.append("Cupo Máximo: ").append(cupoMaximo).append(" personas\n");
        
        if (edadMinima > 0) {
            info.append("Edad Mínima: ").append(edadMinima).append(" años\n");
        }
        
        if (restriccionClima != null && !restriccionClima.isEmpty()) {
            info.append("Restricciones Climáticas: ").append(restriccionClima).append("\n");
        }
        
        if (deTemporada) {
            info.append("Atracción de Temporada\n");
            info.append("Disponible desde: ").append(fechaInicio).append(" hasta: ").append(fechaFin).append("\n");
        }
        
        return info.toString();
    }
    
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    
    public int getCupoMaximo() {
        return cupoMaximo;
    }
    
    
    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
    
    
    public int getEdadMinima() {
        return edadMinima;
    }
    
    
    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }
}