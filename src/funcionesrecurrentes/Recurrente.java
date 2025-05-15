package funcionesrecurrentes;


public class Recurrente {
    public static final String APERTURA = "Apertura";
    public static final String CIERRE = "Cierre";
    public static final String FAMILIAR = "Familiar";
    public static final String ORO = "Oro";
    public static final String DIAMANTE = "Diamante";
    

    public static boolean validarTiquete(String nivel) {
        return nivel.equals(FAMILIAR) || nivel.equals(ORO) || nivel.equals(DIAMANTE);
    }
    

    public static boolean tieneAcceso(String nivelTiquete, String nivelAtraccion) {
        if (nivelTiquete.equals(DIAMANTE)) {
            return true; 
        } else if (nivelTiquete.equals(ORO)) {
            return nivelAtraccion.equals(FAMILIAR) || nivelAtraccion.equals(ORO);
        } else if (nivelTiquete.equals(FAMILIAR)) {
            return nivelAtraccion.equals(FAMILIAR);
        }
        return false;
    }
    

    public static boolean esValido(String turno) {
        return turno.equals(APERTURA) || turno.equals(CIERRE);
    }
}