package excepciones;


public class LugarTrabajoException extends Exception {
    private static final long serialVersionUID = 1L;
    

    public LugarTrabajoException(String mensaje) {
        super(mensaje);
    }
    

    public LugarTrabajoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
