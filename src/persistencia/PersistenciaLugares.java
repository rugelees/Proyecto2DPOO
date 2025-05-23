package persistencia;

import modelo.lugares.*;
import java.io.*;
import java.util.*;

public class PersistenciaLugares {

    private static final String RUTA_CAFETERIAS = "./data/cafeterias.txt";
    private static final String RUTA_TAQUILLAS = "./data/ctaquillas.txt";
    private static final String RUTA_TIENDAS = "./data/ctiendas.txt";

    public static void guardarCafeterias(List<Cafeteria> cafeteriasNuevas) throws IOException {
        List<Cafeteria> cafeteriasActuales = cargarCafeterias();
        Map<String, Cafeteria> mapCafeterias = new LinkedHashMap<>();
        
        for (Cafeteria c : cafeteriasActuales) {
            mapCafeterias.put(c.getId(), c);
        }
        for (Cafeteria c : cafeteriasNuevas) {
            mapCafeterias.putIfAbsent(c.getId(), c);
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_CAFETERIAS, false))) { 
            for (Cafeteria c : mapCafeterias.values()) {
                String menuStr = String.join(",", c.getMenu());
                String linea = String.format("%s;%s;%s;%d;%s",
                        c.getId(), c.getNombre(), c.getUbicacion(), c.getCapacidad(), menuStr);
                writer.write(linea);
                writer.newLine();
            }
        }
    }


    public static List<Cafeteria> cargarCafeterias() throws IOException {
        List<Cafeteria> cafeterias = new ArrayList<>();
        File file = new File(RUTA_CAFETERIAS);
        if (!file.exists()) return cafeterias;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 5) {
                    String id = partes[0];
                    String nombre = partes[1];
                    String ubicacion = partes[2];
                    int capacidad = Integer.parseInt(partes[3]);
                    List<String> menu = Arrays.asList(partes[4].split(","));
                    cafeterias.add(new Cafeteria(id, nombre, ubicacion, menu, capacidad));
                }
            }
        }
        return cafeterias;
    }


    public static void guardarTaquillas(List<Taquilla> taquillasNuevas) throws IOException {
        List<Taquilla> taquillasActuales = cargarTaquillas();
        Map<String, Taquilla> mapTaquillas = new LinkedHashMap<>();

        for (Taquilla t : taquillasActuales) {
            mapTaquillas.put(t.getId(), t);
        }
        for (Taquilla t : taquillasNuevas) {
            mapTaquillas.putIfAbsent(t.getId(), t);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_TAQUILLAS, false))) {
            for (Taquilla t : mapTaquillas.values()) {
                String linea = String.format("%s;%s;%s;%s",
                        t.getId(), t.getNombre(), t.getUbicacion(), t.getMetodoPago());
                writer.write(linea);
                writer.newLine();
            }
        }
    }


    public static List<Taquilla> cargarTaquillas() throws IOException {
        List<Taquilla> taquillas = new ArrayList<>();
        File file = new File(RUTA_TAQUILLAS);
        if (!file.exists()) return taquillas;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    String id = partes[0];
                    String nombre = partes[1];
                    String ubicacion = partes[2];
                    String metodoPago = partes[3];
                    taquillas.add(new Taquilla(id, nombre, ubicacion, metodoPago));
                }
            }
        }
        return taquillas;
    }


    public static void guardarTiendas(List<Tienda> tiendasNuevas) throws IOException {
        List<Tienda> tiendasActuales = cargarTiendas();
        Map<String, Tienda> mapTiendas = new LinkedHashMap<>();

        for (Tienda ti : tiendasActuales) {
            mapTiendas.put(ti.getId(), ti);
        }
        for (Tienda ti : tiendasNuevas) {
            mapTiendas.putIfAbsent(ti.getId(), ti);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_TIENDAS, false))) {
            for (Tienda ti : mapTiendas.values()) {
                StringBuilder inventarioStr = new StringBuilder();
                Map<String, Integer> inventario = ti.getInventario();
                for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                    inventarioStr.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
                }
                if (inventarioStr.length() > 0) inventarioStr.deleteCharAt(inventarioStr.length() - 1);

                String linea = String.format("%s;%s;%s;%s;%s",
                        ti.getId(), ti.getNombre(), ti.getUbicacion(),
                        ti.getTipoProductos(), inventarioStr.toString());
                writer.write(linea);
                writer.newLine();
            }
        }
    }
    


    public static List<Tienda> cargarTiendas() throws IOException {
        List<Tienda> tiendas = new ArrayList<>();
        File file = new File(RUTA_TIENDAS);
        if (!file.exists()) return tiendas;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 5) {
                    String id = partes[0];
                    String nombre = partes[1];
                    String ubicacion = partes[2];
                    String tipoProductos = partes[3];
                    Map<String, Integer> inventario = new HashMap<>();
                    String[] items = partes[4].split(",");
                    for (String item : items) {
                        String[] kv = item.split("=");
                        if (kv.length == 2) {
                            inventario.put(kv[0], Integer.parseInt(kv[1]));
                        }
                    }
                    tiendas.add(new Tienda(id, nombre, ubicacion, tipoProductos, inventario));
                }
            }
        }
        return tiendas;
    }
    
    
//    Esto sólo es para probar :p
    public static void main(String[] args) {
        try {

            List<Cafeteria> cafeterias = new ArrayList<>();
            cafeterias.add(new Cafeteria("c1", "Cafeteria Central", "Zona Norte", Arrays.asList("Cafe", "Té", "Pasteles"), 50));
            cafeterias.add(new Cafeteria("c2", "Snack Bar", "Zona Sur", Arrays.asList("Jugos", "Sándwiches"), 30));

            List<Taquilla> taquillas = new ArrayList<>();
            taquillas.add(new Taquilla("t1", "Taquilla Principal", "Entrada", "Tarjeta"));
            taquillas.add(new Taquilla("t2", "Taquilla Secundaria", "Zona Sur", "Efectivo"));

            Map<String, Integer> inventario1 = new HashMap<>();
            inventario1.put("Camisas", 20);
            inventario1.put("Gorras", 15);

            Map<String, Integer> inventario2 = new HashMap<>();
            inventario2.put("Juguetes", 40);
            inventario2.put("Souvenirs", 60);

            List<Tienda> tiendas = new ArrayList<>();
            tiendas.add(new Tienda("ti1", "Tienda Regalos", "Zona Norte", "Ropa", inventario1));
            tiendas.add(new Tienda("ti2", "Tienda Juegos", "Zona Central", "Juguetería", inventario2));
            tiendas.add(new Tienda("ti3", "Tienda Juegos", "Zona Sur", "Juguetería", inventario2));
            tiendas.add(new Tienda("ti2", "Tienda Juegos", "Zona Sur", "Juguetería", inventario2));



            PersistenciaLugares.guardarCafeterias(cafeterias);
            PersistenciaLugares.guardarTaquillas(taquillas);
            PersistenciaLugares.guardarTiendas(tiendas);

            System.out.println("Datos guardados correctamente.");

            List<Cafeteria> cafeteriasCargadas = PersistenciaLugares.cargarCafeterias();
            List<Taquilla> taquillasCargadas = PersistenciaLugares.cargarTaquillas();
            List<Tienda> tiendasCargadas = PersistenciaLugares.cargarTiendas();

            System.out.println("\nCafeterías cargadas:");
            for (Cafeteria c : cafeteriasCargadas) {
                System.out.println(c);
            }

            System.out.println("\nTaquillas cargadas:");
            for (Taquilla t : taquillasCargadas) {
                System.out.println(t);
            }

            System.out.println("\nTiendas cargadas:");
            for (Tienda ti : tiendasCargadas) {
                System.out.println(ti);
            }

        } catch (IOException e) {
            System.err.println("Error de IO: " + e.getMessage());
        }
    }
}
