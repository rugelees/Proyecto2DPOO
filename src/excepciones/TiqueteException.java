package excepciones;


public class TiqueteException extends Exception {
    private static final long serialVersionUID = 1L;
    

    public TiqueteException(String mensaje) {
        super(mensaje);
    }
    
    public TiqueteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}