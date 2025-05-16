package modelo.atracciones;

import java.util.Date;

import modelo.usuarios.Cliente;

public class AtraccionMecanica extends Atraccion {
    
    private String ubicacion;
    private int cupoMaximo;
    private float alturaMinima;
    private float alturaMaxima;
    private float pesoMinimo;
    private float pesoMaximo;
    private String restriccionesSalud;
    private String nivelRiesgo;
    private Date fechaInicioTemporada;
    private Date fechaFinTemporada;
    
    
    public AtraccionMecanica(String nombre, String restriccionClima, boolean deTemporada, Date fechaInicio, Date fechaFin,
            String nivelExclusividad, int empleadosEncargados, String ubicacion, int cupoMaximo, float alturaMinima,
            float alturaMaxima, float pesoMinimo, float pesoMaximo, String restriccionesSalud, String nivelRiesgo) {
        super(nombre, restriccionClima, deTemporada, fechaInicio, fechaFin, nivelExclusividad, empleadosEncargados);
        this.ubicacion = ubicacion;
        this.cupoMaximo = cupoMaximo;
        this.alturaMinima = alturaMinima;
        this.alturaMaxima = alturaMaxima;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.restriccionesSalud = restriccionesSalud;
        this.nivelRiesgo = nivelRiesgo;
    }
   
    public boolean verificarRestriccionesFisicas(Cliente cliente) {
        if (cliente == null) {
            return false;
        }
        
        float altura = cliente.getAltura();
        float peso = cliente.getPeso();
        
        if (altura < alturaMinima || altura > alturaMaxima) {
            return false;
        }
        
        if (peso < pesoMinimo || peso > pesoMaximo) {
            return false;
        }
        
        return true;
    }
    
    
    public boolean verificarContraindicaciones(Cliente cliente) {
        if (cliente == null || restriccionesSalud == null || restriccionesSalud.isEmpty()) {
            return true; 
        }
        
        String[] restricciones = restriccionesSalud.split(",");
        
        for (String restriccion : restricciones) {
            if (cliente.tieneCondicionSalud(restriccion.trim())) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String consultarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Atracción Mecánica: ").append(nombre).append("\n");
        info.append("Ubicación: ").append(ubicacion).append("\n");
        info.append("Nivel de Exclusividad: ").append(nivelExclusividad).append("\n");
        info.append("Cupo Máximo: ").append(cupoMaximo).append(" personas\n");
        info.append("Restricciones de Altura: ").append(alturaMinima).append(" cm - ").append(alturaMaxima).append(" cm\n");
        info.append("Restricciones de Peso: ").append(pesoMinimo).append(" kg - ").append(pesoMaximo).append(" kg\n");
        info.append("Nivel de Riesgo: ").append(nivelRiesgo).append("\n");
        
        if (restriccionesSalud != null && !restriccionesSalud.isEmpty()) {
            info.append("Contraindicaciones: ").append(restriccionesSalud).append("\n");
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
    
   
    public float getAlturaMinima() {
        return alturaMinima;
    }
    
   
    public void setAlturaMinima(float alturaMinima) {
        this.alturaMinima = alturaMinima;
    }
    
    
    public float getAlturaMaxima() {
        return alturaMaxima;
    }
    
    
    public void setAlturaMaxima(float alturaMaxima) {
        this.alturaMaxima = alturaMaxima;
    }
    
    
    public float getPesoMinimo() {
        return pesoMinimo;
    }
    
    
    public void setPesoMinimo(float pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }
    
    
    public float getPesoMaximo() {
        return pesoMaximo;
    }
    
    
    public void setPesoMaximo(float pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }
    
    
    public String getRestriccionesSalud() {
        return restriccionesSalud;
    }
    
    
    public void setRestriccionesSalud(String restriccionesSalud) {
        this.restriccionesSalud = restriccionesSalud;
    }
    
    
    public String getNivelRiesgo() {
        return nivelRiesgo;
    }
    
    
    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }
    
   
    public boolean esRiesgoAlto() {
        return "alto".equalsIgnoreCase(nivelRiesgo);
    }
    
    
    public boolean esRiesgoMedio() {
        return "medio".equalsIgnoreCase(nivelRiesgo);
    }

    public Date getFechaInicioTemporada() {
        return fechaInicioTemporada;
    }

    public Date getFechaFinTemporada() {
        return fechaFinTemporada;
    }

    public void setFechaInicioTemporada(Date fechaInicioTemporada) {
        this.fechaInicioTemporada = fechaInicioTemporada;
    }

    public void setFechaFinTemporada(Date fechaFinTemporada) {
        this.fechaFinTemporada = fechaFinTemporada;
    }
}