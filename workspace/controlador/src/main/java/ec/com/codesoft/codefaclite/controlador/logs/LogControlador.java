/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.logs;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author
 */
public class LogControlador {
    //Paquete Raiz de todas las clases para gestionar los Logs
    private final static Logger LOG_RAIZ = Logger.getLogger("ec.com.codesoft.codefaclite");
    // Equvalencia de los bytes
    private final static int MEGABYTE=1024*1024;
    
    /**
     * Es el tamanio maximo del archivo de los Logs
     */
    private int tamanioArchivo;
    
    /**
     * Nombre del archivo del log
     */
    private String nombreArchivo;
    
    /**
     * Nombre de la carpeta de los logs
     */
    private String nombreCarpeta;
    
    /**
     * Es el numero maximo de partes que se van a dividir los logs cuando sobrepasen el tamanio de los logs
     */
    private int numeroPartes;

    public LogControlador() {
        this.tamanioArchivo= MEGABYTE*10; // Los archivos van a tener maximo 5 mbs
        this.nombreArchivo="codefac.log"; 
        this.nombreCarpeta="log";
        this.numeroPartes=10;
        iniciar();
    }
    
    /**
     * Inicializa y configura todos los valores para iniciar los logs
     */
    private void iniciar()
    {
        //Crear la ruta si no existe
        File file = new File(nombreCarpeta);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.mkdir();
            LOG_RAIZ.log(Level.INFO,"Creado directorio de los logs");
        }
        
        try {
            Handler consoleHandler = new ConsoleHandler();
            
            Handler fileHandler = new FileHandler(nombreCarpeta+"/"+nombreArchivo,tamanioArchivo,numeroPartes,true);
            
            // El formateador indica como presentar los datos, en este caso usaremos el formaro sencillo
            // el cual es mas facil de leer, si no usamos esto el log estara en formato xml por defecto
            fileHandler.setFormatter(new SimpleFormatter());
            
            //Permite mostrar los logs en la consola
            //LOG_RAIZ.addHandler(consoleHandler);
            //Permite grabar los logs en archivos
            LOG_RAIZ.addHandler(fileHandler);
            
            // Indicamos a partir de que nivel deseamos mostrar los logs, podemos especificar un nivel en especifico
            // para ignorar informacion que no necesitemos
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            
        } catch (IOException ex) {
            Logger.getLogger(LogControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LogControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
        /**
     * Esta funcion nos permite convertir el stackTrace en un String, necesario
     * para poder imprimirlos al log debido a cambios en como Java los maneja
     * internamente
     *
     * @param e Excepcion de la que queremos el StackTrace
     * @return StackTrace de la excepcion en forma de String
     */
    public static String getStackTrace(Exception e) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        return sWriter.toString();
    }
    
    
}
