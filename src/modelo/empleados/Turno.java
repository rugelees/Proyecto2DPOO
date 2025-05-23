package modelo.empleados;

import java.util.Date;

public class Turno {
    private Date fecha;
    private String tipo;
    private Empleado empleado;
    private boolean horasExtras;

    public Turno(Date fecha, String tipo, Empleado empleado) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.empleado = empleado;
        this.horasExtras = false;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public boolean isHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(boolean horasExtras) {
        this.horasExtras = horasExtras;
    }
} 