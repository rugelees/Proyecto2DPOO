package modelo.usuarios;

import java.io.Serializable;


public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String email;
    protected String password;
    

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
    

    public boolean verificarCredenciales(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getPassword() {
        return password;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }
}