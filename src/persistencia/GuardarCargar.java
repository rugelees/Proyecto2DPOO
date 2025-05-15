package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class GuardarCargar {
    private static final String RUTA_DATOS = "data/";
    


    public static void guardarTexto(String contenido, String nombreArchivo) throws IOException {
        File carpeta = new File(RUTA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            writer.print(contenido);
        }
    }
    

    public static String cargarTexto(String nombreArchivo) throws IOException {
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        if (!archivo.exists()) {
            return "";
        }
        
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        
        return contenido.toString();
    }
    

    public static void guardarLineas(List<String> lineas, String nombreArchivo) throws IOException {
        File carpeta = new File(RUTA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (String linea : lineas) {
                writer.println(linea);
            }
        }
    }
    

    public static List<String> cargarLineas(String nombreArchivo) throws IOException {
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        List<String> lineas = new ArrayList<>();
        
        if (!archivo.exists()) {
            return lineas;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        }
        
        return lineas;
    }
    

    public static boolean existeArchivo(String nombreArchivo) {
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        return archivo.exists();
    }
    

    public static boolean eliminarArchivo(String nombreArchivo) {
        File archivo = new File(RUTA_DATOS + nombreArchivo);
        return archivo.delete();
    }
}