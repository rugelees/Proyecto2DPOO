package excepciones;


public class UsuarioException extends Exception {
    private static final long serialVersionUID = 1L;
    

    public UsuarioException(String mensaje) {
        super(mensaje);
    }

    public UsuarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}