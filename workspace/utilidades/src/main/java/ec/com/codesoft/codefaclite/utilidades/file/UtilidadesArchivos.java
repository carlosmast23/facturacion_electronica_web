/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.file;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesArchivos {
    public static void crearRuta(String path)
    {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
    }
    
    public static void crearRutaDirectorio(String path)
    {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.mkdirs();
            //file.mkdir();
        }
    }
    
    public static boolean verificarExiteArchivo(String path)
    {
        File fichero = new File(path);
        
        if (fichero.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean renombrarArchivo(File file,String nuevoNombre)
    {
        try {
            File newFile = new File(file.getParent(), nuevoNombre);
            Files.move(file.toPath(), newFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Funcion que lee una archivo plano y trasforma los datos en un String
     * @param path
     * @return 
     */
    public static List<String> leerArchivoPlano(String path) {
        FileReader f = null;
        List<String> resultado = new ArrayList<String>();
        try {
            String cadena;
            f = new FileReader(path);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) 
            {
                resultado.add(cadena);
            }
            b.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } finally 
        {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }
    
    /**
     * Metodo que permite grabar un flujo de datos en un archivo
     * @param inputStream //Datos del stream para grabar en un archivo
     * @param outputStream  //Destino final donde se van a guardar los datos
     */
    public static void grabarInputStreamEnArchivo(InputStream inputStream,OutputStream outputStream) throws IOException
    {
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        inputStream.close();
        outputStream.close();
    }
    
    
    /**
     * TODO:Modificar por que existe una libreria que genera nombres aleatorios para archivos , revisar en proyecto escuela del aire
     * @param nombrePrincipal
     * @param formato
     * @return 
     */
    @Deprecated
    public static String generarNombreArchivoUnico(String nombrePrincipal,String formato)
    {
        String pattern = "MMddyyyyHHmmssSSSSSSSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return nombrePrincipal+"-"+simpleDateFormat.format(UtilidadesFecha.getFechaHoy())+"."+formato;
    }
    
        @Deprecated
    public static String generarNombreArchivoUnico(String nombrePrincipal)
    {
        String pattern = "mmssSSSSSSSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return nombrePrincipal+"-"+simpleDateFormat.format(UtilidadesFecha.getFechaHoy());
    }
    
    public static Long obtenerTamanioArchivoEnKb(File archivo)
    {
        try {
            return Files.size(archivo.toPath())/ 1024;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0l;
    }
    
    public static Boolean grabarArchivoPropiedadesFile(String path,Properties prop,String comentarios)
    {
        try {
            FileOutputStream fr=null;
            File file=new File(path);
            
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            fr = new FileOutputStream(file);
            prop.store(fr,comentarios);
            fr.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
