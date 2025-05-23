package modelo.atracciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Espectaculo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String restriccionClima;
    private boolean deTemporada;
    private Date fechaInicio;
    private Date fechaFin;
    private String duracion;
    private String horario; 
    private int capacidad;
    private List<Date> funciones;
    
   
    public Espectaculo(String nombre, String restriccionClima, boolean deTemporada, Date fechaInicio, Date fechaFin,
            String duracion, String horario, int capacidad) {
        this.nombre = nombre;
        this.restriccionClima = restriccionClima;
        this.deTemporada = deTemporada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracion = duracion;
        this.horario = horario;
        this.capacidad = capacidad;
        this.funciones = new ArrayList<>();
    }
    
    
    public void agregarFuncion(Date fechaFuncion) {
        if (fechaFuncion != null) {
            for (Date fecha : funciones) {
                if (mismodia(fecha, fechaFuncion)) {
                    return; 
                }
            }
            
            funciones.add(new Date(fechaFuncion.getTime()));
        }
    }
    
   
    public boolean cancelarFuncion(Date fechaFuncion) {
        if (fechaFuncion != null) {
            for (int i = 0; i < funciones.size(); i++) {
                if (mismodia(funciones.get(i), fechaFuncion)) {
                    funciones.remove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
   
    public boolean estaDisponible(Date fecha) {
        if (fecha == null) {
            return false;
        }
        
        if (deTemporada && (fecha.before(fechaInicio) || fecha.after(fechaFin))) {
            return false;
        }
        
        for (Date fechaFuncion : funciones) {
            if (mismodia(fechaFuncion, fecha)) {
                return true;
            }
        }
        
        return false;
    }
    
    
    private boolean mismodia(Date fecha1, Date fecha2) {
        
        long milisEnDia = 24 * 60 * 60 * 1000;
        long dia1 = fecha1.getTime() / milisEnDia;
        long dia2 = fecha2.getTime() / milisEnDia;
        return dia1 == dia2;
    }
    

    public String consultarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Espectáculo: ").append(nombre).append("\n");
        info.append("Duración: ").append(duracion).append("\n");
        info.append("Horario: ").append(horario).append("\n");
        info.append("Capacidad: ").append(capacidad).append(" personas\n");
        
        if (restriccionClima != null && !restriccionClima.isEmpty()) {
            info.append("Restricciones Climáticas: ").append(restriccionClima).append("\n");
        }
        
        if (deTemporada) {
            info.append("Espectáculo de Temporada\n");
            info.append("Disponible desde: ").append(fechaInicio).append(" hasta: ").append(fechaFin).append("\n");
        }
        
        if (!funciones.isEmpty()) {
            info.append("Próximas funciones:\n");
            for (Date funcion : funciones) {
                info.append("- ").append(funcion).append("\n");
            }
        }
        
        return info.toString();
    }
    
   
    public String getNombre() {
        return nombre;
    }
   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getRestriccionClima() {
        return restriccionClima;
    }
    
    
    public void setRestriccionClima(String restriccionClima) {
        this.restriccionClima = restriccionClima;
    }
    
    
    public boolean isDeTemporada() {
        return deTemporada;
    }
    
    
    public void setDeTemporada(boolean deTemporada) {
        this.deTemporada = deTemporada;
    }
    
   
    public Date getFechaInicio() {
        return fechaInicio != null ? new Date(fechaInicio.getTime()) : null;
    }
    
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? new Date(fechaInicio.getTime()) : null;
    }
    
    
    public Date getFechaFin() {
        return fechaFin != null ? new Date(fechaFin.getTime()) : null;
    }
    
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? new Date(fechaFin.getTime()) : null;
    }
    
    
    public String getDuracion() {
        return duracion;
    }
    
    
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    
    public String getHorario() {
        return horario;
    }
    
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
   
    public int getCapacidad() {
        return capacidad;
    }
    
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    
    public List<Date> getFunciones() {
        List<Date> copiaFunciones = new ArrayList<>();
        for (Date fecha : funciones) {
            copiaFunciones.add(new Date(fecha.getTime()));
        }
        return copiaFunciones;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Espectaculo other = (Espectaculo) obj;
        return nombre != null && nombre.equals(other.nombre);
    }
    
    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Espectaculo [nombre=" + nombre + ", horario=" + horario + "]";
    }
}