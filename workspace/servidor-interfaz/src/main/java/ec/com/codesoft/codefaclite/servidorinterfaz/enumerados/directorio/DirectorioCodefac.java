/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.mail.Session;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author
 */
public enum DirectorioCodefac {
    CONFIGURACION("configuracion"),
    IMAGENES("imagenes"),
    LICENCIA("licencia"),
    COMPROBANTES_PRUEBAS("comprobantes/pruebas"),
    COMPROBANTES_PRODUCCION("comprobantes/producción"),
    FIRMA("firma"),
    TEMP("temporal");
    
    private String nombre;
    /**
     * Path de la raiz de los directorio a usar en codefac
     */
    //public static String path_raiz;
    
    
    private DirectorioCodefac(String nombre) {
        this.nombre = nombre;
    }
    
    /*
    public String getDirectorio() throws RemoteException
    {
        ParametroCodefacServiceIf service=ServiceFactory.getFactory().getParametroCodefacServiceIf();
        Map<String,ParametroCodefac> parametrosMap= service.getParametrosMap();
        String path_raiz=parametrosMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
        return path_raiz+"/"+nombre;
    }
    
    public String getArchivo(String archivo) throws RemoteException
    {
        ParametroCodefacServiceIf service = ServiceFactory.getFactory().getParametroCodefacServiceIf();
        Map<String, ParametroCodefac> parametrosMap = service.getParametrosMap();
        String path_raiz=parametrosMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
        return path_raiz+"/"+nombre+"/"+archivo;
    }
    
    public BufferedImage getArchivoStream(String archivo) throws RemoteException
    {
        ParametroCodefacServiceIf service = ServiceFactory.getFactory().getParametroCodefacServiceIf();
        Map<String, ParametroCodefac> parametrosMap = service.getParametrosMap();
        InputStream input=null;
        try {
            String path_raiz=parametrosMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
            File file=new File(path_raiz+"/"+nombre+"/"+archivo);
            input = new FileInputStream(file);
            BufferedImage image = ImageIO.read(file ); 
            return image;
            //BufferedInputStream bufferedIn = new BufferedInputStream(input);
            //ObjectInputStream ois=new ObjectInputStream(input);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }*/

    public String getNombre() {
        return nombre;
    }
    
    
    
    
    
}
