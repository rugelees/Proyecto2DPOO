package persistencia;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import excepciones.AtraccionException;
import modelo.atracciones.Atraccion;
import modelo.atracciones.AtraccionCultural;
import modelo.atracciones.AtraccionMecanica;
import modelo.atracciones.Espectaculo;


public class PersistenciaAtracciones {
    private static final String ARCHIVO_ATRACCIONES_MECANICAS = "atracciones_mecanicas.txt";
    private static final String ARCHIVO_ATRACCIONES_CULTURALES = "atracciones_culturales.txt";
    private static final String ARCHIVO_ESPECTACULOS = "espectaculos.txt";
    
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");
    

    public void guardarAtraccionesMecanicas(List<AtraccionMecanica> atraccionesMecanicas) throws AtraccionException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (AtraccionMecanica atraccion : atraccionesMecanicas) {
                StringBuilder sb = new StringBuilder();
                sb.append(atraccion.getNombre()).append("|");
                sb.append(atraccion.getRestriccionClima()).append("|");
                sb.append(atraccion.isDeTemporada()).append("|");
                sb.append(atraccion.getFechaInicio() != null ? FORMATO_FECHA.format(atraccion.getFechaInicio()) : "null").append("|");
                sb.append(atraccion.getFechaFin() != null ? FORMATO_FECHA.format(atraccion.getFechaFin()) : "null").append("|");
                sb.append(atraccion.getNivelExclusividad()).append("|");
                sb.append(atraccion.getEmpleadosEncargados()).append("|");
                sb.append(atraccion.getUbicacion()).append("|");
                sb.append(atraccion.getCupoMaximo()).append("|");
                sb.append(atraccion.getAlturaMinima()).append("|");
                sb.append(atraccion.getAlturaMaxima()).append("|");
                sb.append(atraccion.getPesoMinimo()).append("|");
                sb.append(atraccion.getPesoMaximo()).append("|");
                sb.append(atraccion.getRestriccionesSalud()).append("|");
                sb.append(atraccion.getNivelRiesgo());
                 
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_ATRACCIONES_MECANICAS, true);
            
        } catch (IOException e) {
            throw new AtraccionException("Error al guardar las atracciones mecánicas", e);
        }
    }
    
   
    public void guardarAtraccionesCulturales(List<AtraccionCultural> atraccionesCulturales) throws AtraccionException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (AtraccionCultural atraccion : atraccionesCulturales) {
                StringBuilder sb = new StringBuilder();
                sb.append(atraccion.getNombre()).append("|");
                sb.append(atraccion.getRestriccionClima()).append("|");
                sb.append(atraccion.isDeTemporada()).append("|");
                sb.append(atraccion.getFechaInicio() != null ? FORMATO_FECHA.format(atraccion.getFechaInicio()) : "null").append("|");
                sb.append(atraccion.getFechaFin() != null ? FORMATO_FECHA.format(atraccion.getFechaFin()) : "null").append("|");
                sb.append(atraccion.getNivelExclusividad()).append("|");
                sb.append(atraccion.getEmpleadosEncargados()).append("|");
                sb.append(atraccion.getUbicacion()).append("|");
                sb.append(atraccion.getCupoMaximo()).append("|");
                sb.append(atraccion.getEdadMinima());
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_ATRACCIONES_CULTURALES, true);
            
        } catch (IOException e) {
            throw new AtraccionException("Error al guardar las atracciones culturales", e);
        }
    }
    
    
    public void guardarEspectaculos(List<Espectaculo> espectaculos) throws AtraccionException {
        try {
            List<String> lineas = new ArrayList<>();
            
            for (Espectaculo espectaculo : espectaculos) {
                StringBuilder sb = new StringBuilder();
                sb.append(espectaculo.getNombre()).append("|");
                sb.append(espectaculo.getRestriccionClima()).append("|");
                sb.append(espectaculo.isDeTemporada()).append("|");
                sb.append(espectaculo.getFechaInicio() != null ? FORMATO_FECHA.format(espectaculo.getFechaInicio()) : "null").append("|");
                sb.append(espectaculo.getFechaFin() != null ? FORMATO_FECHA.format(espectaculo.getFechaFin()) : "null").append("|");
                sb.append(espectaculo.getDuracion()).append("|");
                sb.append(espectaculo.getHorario()).append("|");
                sb.append(espectaculo.getCapacidad()).append("|");
                
                List<Date> funciones = espectaculo.getFunciones();
                if (funciones.isEmpty()) {
                    sb.append("null");
                } else {
                    for (int i = 0; i < funciones.size(); i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(FORMATO_FECHA.format(funciones.get(i)));
                    }
                }
                
                lineas.add(sb.toString());
            }
            
            GuardarCargar.guardarLineas(lineas, ARCHIVO_ESPECTACULOS, true);
            
        } catch (IOException e) {
            throw new AtraccionException("Error al guardar los espectáculos", e);
        }
    }
    
   
    public List<AtraccionMecanica> cargarAtraccionesMecanicas() throws AtraccionException {
        List<AtraccionMecanica> atracciones = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_ATRACCIONES_MECANICAS)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_ATRACCIONES_MECANICAS);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 15) {
                        String nombre = partes[0];
                        String restriccionClima = partes[1];
                        boolean deTemporada = Boolean.parseBoolean(partes[2]);
                        
                        Date fechaInicio = null;
                        if (!partes[3].equals("null")) {
                            fechaInicio = FORMATO_FECHA.parse(partes[3]);
                        }
                        
                        Date fechaFin = null;
                        if (!partes[4].equals("null")) {
                            fechaFin = FORMATO_FECHA.parse(partes[4]);
                        }
                        
                        String nivelExclusividad = partes[5];
                        int empleadosEncargados = Integer.parseInt(partes[6]);
                        String ubicacion = partes[7];
                        int cupoMaximo = Integer.parseInt(partes[8]);
                        float alturaMinima = Float.parseFloat(partes[9]);
                        float alturaMaxima = Float.parseFloat(partes[10]);
                        float pesoMinimo = Float.parseFloat(partes[11]);
                        float pesoMaximo = Float.parseFloat(partes[12]);
                        String restriccionesSalud = partes[13];
                        String nivelRiesgo = partes[14];
                        
                        AtraccionMecanica atraccion = new AtraccionMecanica(nombre, restriccionClima, deTemporada, 
                                fechaInicio, fechaFin, nivelExclusividad, empleadosEncargados, ubicacion, 
                                cupoMaximo, alturaMinima, alturaMaxima, pesoMinimo, pesoMaximo, 
                                restriccionesSalud, nivelRiesgo);
                        
                        atracciones.add(atraccion);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new AtraccionException("Error al cargar las atracciones mecánicas", e);
        }
        
        return atracciones;
    }
    
    
    public List<AtraccionCultural> cargarAtraccionesCulturales() throws AtraccionException {
        List<AtraccionCultural> atracciones = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_ATRACCIONES_CULTURALES)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_ATRACCIONES_CULTURALES);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 10) {
                        String nombre = partes[0];
                        String restriccionClima = partes[1];
                        boolean deTemporada = Boolean.parseBoolean(partes[2]);
                        
                        Date fechaInicio = null;
                        if (!partes[3].equals("null")) {
                            fechaInicio = FORMATO_FECHA.parse(partes[3]);
                        }
                        
                        Date fechaFin = null;
                        if (!partes[4].equals("null")) {
                            fechaFin = FORMATO_FECHA.parse(partes[4]);
                        }
                        
                        String nivelExclusividad = partes[5];
                        int empleadosEncargados = Integer.parseInt(partes[6]);
                        String ubicacion = partes[7];
                        int cupoMaximo = Integer.parseInt(partes[8]);
                        int edadMinima = Integer.parseInt(partes[9]);
                        
                        AtraccionCultural atraccion = new AtraccionCultural(nombre, restriccionClima, deTemporada, 
                                fechaInicio, fechaFin, nivelExclusividad, empleadosEncargados, ubicacion, 
                                cupoMaximo, edadMinima);
                        
                        atracciones.add(atraccion);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new AtraccionException("Error al cargar las atracciones culturales", e);
        }
        
        return atracciones;
    }
    
    
    public List<Espectaculo> cargarEspectaculos() throws AtraccionException {
        List<Espectaculo> espectaculos = new ArrayList<>();
        
        try {
            if (GuardarCargar.existeArchivo(ARCHIVO_ESPECTACULOS)) {
                List<String> lineas = GuardarCargar.cargarLineas(ARCHIVO_ESPECTACULOS);
                
                for (String linea : lineas) {
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length >= 9) {
                        String nombre = partes[0];
                        String restriccionClima = partes[1];
                        boolean deTemporada = Boolean.parseBoolean(partes[2]);
                        
                        Date fechaInicio = null;
                        if (!partes[3].equals("null")) {
                            fechaInicio = FORMATO_FECHA.parse(partes[3]);
                        }
                        
                        Date fechaFin = null;
                        if (!partes[4].equals("null")) {
                            fechaFin = FORMATO_FECHA.parse(partes[4]);
                        }
                        
                        String duracion = partes[5];
                        String horario = partes[6];
                        int capacidad = Integer.parseInt(partes[7]);
                        
                        Espectaculo espectaculo = new Espectaculo(nombre, restriccionClima, deTemporada, 
                                fechaInicio, fechaFin, duracion, horario, capacidad);
                        
                        if (!partes[8].equals("null")) {
                            String[] funcionesStr = partes[8].split(",");
                            for (String funcionStr : funcionesStr) {
                                Date fechaFuncion = FORMATO_FECHA.parse(funcionStr);
                                espectaculo.agregarFuncion(fechaFuncion);
                            }
                        }
                        
                        espectaculos.add(espectaculo); 	
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new AtraccionException("Error al cargar los espectáculos", e);
        }
        
        return espectaculos;
    }
    
    
    public List<Atraccion> cargarTodasAtracciones() throws AtraccionException {
        List<Atraccion> atracciones = new ArrayList<>();
        atracciones.addAll(cargarAtraccionesMecanicas());
        atracciones.addAll(cargarAtraccionesCulturales());
        return atracciones;
    }
}