package modelo.atracciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Atraccion {
  
    
    protected String nombre;
    protected String restriccionClima;
    protected boolean deTemporada;
    protected Date fechaInicio;
    protected Date fechaFin;
    protected String nivelExclusividad;
    protected int empleadosEncargados;
    protected List<Date> fechasMantenimiento;
    
 
    public Atraccion(String nombre, String restriccionClima, boolean deTemporada, Date fechaInicio, Date fechaFin, 
            String nivelExclusividad, int empleadosEncargados) {
        this.nombre = nombre;
        this.restriccionClima = restriccionClima;
        this.deTemporada = deTemporada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nivelExclusividad = nivelExclusividad;
        this.empleadosEncargados = empleadosEncargados;
        this.fechasMantenimiento = new ArrayList<>();
    }
    
    public abstract String consultarInformacion();
   
    public void programarMantenimiento(Date fechaInicio, Date fechaFin) {
        Date fecha = new Date(fechaInicio.getTime());
        while (!fecha.after(fechaFin)) {
            fechasMantenimiento.add(new Date(fecha.getTime()));
            fecha.setTime(fecha.getTime() + 24 * 60 * 60 * 1000);
        }
    }
    
    public boolean estaDisponible(Date fecha) {
        for (Date fechaMantenimiento : fechasMantenimiento) {
            if (mismodia(fechaMantenimiento, fecha)) {
                return false;
            }
        }
        
        if (deTemporada) {
            return !fecha.before(fechaInicio) && !fecha.after(fechaFin);
        }
        
        return true;
    }
    
   
    private boolean mismodia(Date fecha1, Date fecha2) {

        long milisEnDia = 24 * 60 * 60 * 1000;
        long dia1 = fecha1.getTime() / milisEnDia;
        long dia2 = fecha2.getTime() / milisEnDia;
        return dia1 == dia2;
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
    
    
    public String getNivelExclusividad() {
        return nivelExclusividad;
    }
    
   
    public void setNivelExclusividad(String nivelExclusividad) {
        this.nivelExclusividad = nivelExclusividad;
    }
    
    
    public int getEmpleadosEncargados() {
        return empleadosEncargados;
    }
    
    
    public void setEmpleadosEncargados(int empleadosEncargados) {
        this.empleadosEncargados = empleadosEncargados;
    }
    
    
    public List<Date> getFechasMantenimiento() {
        List<Date> copiaFechas = new ArrayList<>();
        for (Date fecha : fechasMantenimiento) {
            copiaFechas.add(new Date(fecha.getTime()));
        }
        return copiaFechas;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Atraccion other = (Atraccion) obj;
        return nombre != null && nombre.equals(other.nombre);
    }

    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

    public String toString() {
        return "Atraccion [nombre=" + nombre + ", nivelExclusividad=" + nivelExclusividad + "]";
    }
    }
