package excepciones;


public class ValidacionException extends Exception {
    private static final long serialVersionUID = 1L;
    

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
    
 
    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}