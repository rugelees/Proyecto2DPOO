package excepciones;


public class AtraccionException extends Exception {
    private static final long serialVersionUID = 1L;
    

    public AtraccionException(String mensaje) {
        super(mensaje);
    }
    

    public AtraccionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}