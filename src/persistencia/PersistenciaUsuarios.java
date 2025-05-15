package persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import excepciones.UsuarioException;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;

public class PersistenciaUsuarios {
    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_ADMINISTRADORES = "administradores.txt";
    

    public void guardarClientes(List<Cliente> clientes) throws UsuarioException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Cliente cliente : clientes) {
                StringBuilder sb = new StringBuilder();
                sb.append(cliente.getNombre()).append("|");
                sb.append(cliente.getId()).append("|");
                sb.append(cliente.getEmail()).append("|");
                sb.append(cliente.getPassword()).append("|");
                sb.append(cliente.getAltura()).append("|");
                sb.append(cliente.getPeso()).append("|");
                sb.append(cliente.getEdad()).append("|");
                
                List<String> condiciones = cliente.getCondicionesSalud();
                if (condiciones.isEmpty()) {
                    sb.append("null");
                } else {
                    for (int i = 0; i < condiciones.size(); i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(condiciones.get(i));
                    }
                }
                
                
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_CLIENTES);
            
        } catch (IOException e) {
            throw new UsuarioException("Error al guardar los clientes", e);
        }
    }
    
    
    public void guardarAdministradores(List<Administrador> administradores) throws UsuarioException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Administrador admin : administradores) {
                
                StringBuilder sb = new StringBuilder();
                sb.append(admin.getNombre()).append("|");
                sb.append(admin.getId()).append("|");
                sb.append(admin.getEmail()).append("|");
                sb.append(admin.getPassword());
                
                
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_ADMINISTRADORES);
            
        } catch (IOException e) {
            throw new UsuarioException("Error al guardar los administradores", e);
        }
    }
    
   
    public List<Cliente> cargarClientes() throws UsuarioException {
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_CLIENTES)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_CLIENTES);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 8) {
                        String nombre = partes[0];
                        int id = Integer.parseInt(partes[1]);
                        String email = partes[2];
                        String password = partes[3];
                        
                        Cliente cliente = new Cliente(nombre, id, email, password);
                        
                        float altura = Float.parseFloat(partes[4]);
                        float peso = Float.parseFloat(partes[5]);
                        int edad = Integer.parseInt(partes[6]);
                        
                        cliente.setAltura(altura);
                        cliente.setPeso(peso);
                        cliente.setEdad(edad);
                        
                        if (!partes[7].equals("null")) {
                            String[] condiciones = partes[7].split(",");
                            for (String condicion : condiciones) {
                                cliente.agregarCondicionSalud(condicion);
                            }
                        }
                        
                        clientes.add(cliente);
                    }
                }
            }
        } catch (IOException e) {
            throw new UsuarioException("Error al cargar los clientes", e);
        }
        
        return clientes;
    }
    
   
    public List<Administrador> cargarAdministradores() throws UsuarioException {
        List<Administrador> administradores = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_ADMINISTRADORES)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_ADMINISTRADORES);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 4) {
                        String nombre = partes[0];
                        int id = Integer.parseInt(partes[1]);
                        String email = partes[2];
                        String password = partes[3];
                        
                        Administrador admin = new Administrador(nombre, id, email, password);
                        
                        administradores.add(admin);
                    }
                }
            }
        } catch (IOException e) {
            throw new UsuarioException("Error al cargar los administradores", e);
        }
        
        return administradores;
    }
    
   
    public List<Usuario> cargarTodosUsuarios() throws UsuarioException {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(cargarClientes());
        usuarios.addAll(cargarAdministradores());
        return usuarios;
    }
    
    
    public Usuario buscarUsuarioPorEmail(String email) throws UsuarioException {
        if (email == null || email.isEmpty()) {
            return null;
        }
        
        List<Usuario> usuarios = cargarTodosUsuarios();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        
        return null;
    }
    
    
    public Usuario autenticarUsuario(String email, String password) throws UsuarioException {
        if (email == null || email.isEmpty() || password == null) {
            return null;
        }
        
        Usuario usuario = buscarUsuarioPorEmail(email);
        
        if (usuario != null && usuario.verificarCredenciales(email, password)) {
            return usuario;
        }
        
        return null;
    }
}