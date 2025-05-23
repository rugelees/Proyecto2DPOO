package excepciones;


public class EmpleadoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public EmpleadoException(String mensaje) {
        super(mensaje);
    }
    

    public EmpleadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
