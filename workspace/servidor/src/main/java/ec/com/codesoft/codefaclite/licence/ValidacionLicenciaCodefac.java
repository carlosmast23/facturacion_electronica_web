/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.licence;

import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;
import ec.com.codesoft.codefaclite.utilidades.varios.InterfazRed;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.mail.Session;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.jfree.util.Log;


/**
 *
 * @author
 */
public class ValidacionLicenciaCodefac{

    private static final Logger LOG = Logger.getLogger(ValidacionLicenciaCodefac.class.getName());
    
    
    
    private static final String NOMBRE_LICENCIA="/licencia/licence.codefac";
    /*public static final String USUARIO="usuario";
    public static final String LICENCIA="licencia";
    public static final String TIPO_LICENCIA="tipo";
    
    /**
     * Campo que identifica la cantidad de usuarios de clientes permitidas
     */
    //public static final String CANTIDAD_USUARIOS="clientes";
    
    private Licencia licencia;
    private String path;

    public ValidacionLicenciaCodefac(String path) {
        //PropertiesC
        //licencia.getPropiedades
        //licencia=new Licencia(new Properties)
        this.path=path;        
        PropertiesConfiguration  p = obtenerLicencia();
        licencia=new Licencia();
        licencia.cargarPropiedadesLicenciaFisica(p);
    }

    public ValidacionLicenciaCodefac() {
    }

    public boolean validar() throws ValidacionLicenciaExcepcion,NoExisteLicenciaException{

        if (verificarExisteLicencia()) {
            PropertiesConfiguration p = obtenerLicencia();//Obtiene todas las propiedades del archivo de licencia
            licencia = new Licencia(p);

            if (licencia.validarLicencia()) {
                return true;
            } else {
                return false;
            }

        } else {
            throw new NoExisteLicenciaException("No existe licencia");
        }

    }

    /**
     * Verificar si existe el usuario propietario en codesoft
     *
     * @return
     */
    public boolean verificarExisteUsuario() {
        //sesion.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO)
        return true;
    }

    public boolean verificarExisteLicencia() {
        File af = new File(path+NOMBRE_LICENCIA);
        if(af.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public PropertiesConfiguration obtenerLicencia() 
    {
        try {
            PropertiesConfiguration p = new PropertiesConfiguration(path+NOMBRE_LICENCIA);
            //p.load(new FileReader(path+NOMBRE_LICENCIA));
            return p;
        } catch (ConfigurationException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void aprobacionUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean verificarConexionInternet() {
        try {
            //Pagina Web de la pagina de Codefac
            URL ruta = new URL("http://www.cf.codesoft-ec.com/");
            URLConnection rutaC = ruta.openConnection();
            rutaC.connect();
            return true;
        } catch (Exception e) {

            return false;
        }
    }
    
    //TODO: VER SI PUEDO UNIR EN UN SOLO METODO CON EL DE ABAJA
    public Properties crearLicenciaMaquina(Licencia licencia,String mac)
    {
        FileOutputStream fr=null;
        try {

            //String licencia=usuario+":"+UtilidadVarios.obtenerMac()+":"+tipoLicencia+":"+cantidadUsuarios;            
            String modulosStr=licencia.getModulosStr();
            String licenciaStr=licencia.getUsuario()+":"+mac+":"+licencia.getTipoLicenciaEnum().getLetra()+":"+licencia.getCantidadClientes()+":"+modulosStr;            
            LOG.log(Level.INFO,"creando="+licenciaStr);
            licenciaStr=UtilidadesHash.generarHashBcrypt(licenciaStr);
            Properties prop = new Properties();
            prop.setProperty(Licencia.PROPIEDAD_USUARIO,licencia.getUsuario());
            prop.setProperty(Licencia.PROPIEDAD_LICENCIA,licenciaStr);
            prop.setProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES,licencia.getCantidadClientes().toString());
                        
            //setearPropiedadesModulos(prop,licen); //Setea los modulos activos
            
            TipoLicenciaEnum enumTipoLicencia=licencia.getTipoLicenciaEnum();
            prop.setProperty(Licencia.PROPIEDAD_TIPO_LICENCIA,enumTipoLicencia.getNombre());
            
            //Llea en el properties los datos adicionales del modulo
            licencia.llenarPropertiesModulo(prop);
            
            File file=new File(path+NOMBRE_LICENCIA);
            
             //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            fr = new FileOutputStream(file);
            prop.store(fr,"Properties");
            fr.close();
            return prop;
            //saveProperties(prop);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    /**
     * Metodo que crea la licencia
     * @return 
     */
    public Properties crearLicenciaMaquina(Licencia licencia)
    {
        //FileOutputStream fr=null;
        //String licencia=usuario+":"+UtilidadVarios.obtenerMac()+":"+tipoLicencia+":"+cantidadUsuarios;
        String modulosStr=licencia.getModulosStr();
        InterfazRed interfazRed=UtilidadVarios.obtenerMacSinInternet(licencia.getNombreInterfazRed()).get(0);
        String mac=interfazRed.mac;
        String licenciaStr=licencia.getUsuario()+":"+mac+":"+licencia.getTipoLicenciaEnum().getLetra()+":"+licencia.getCantidadClientes()+":"+modulosStr;
        LOG.log(Level.INFO,"creando="+licenciaStr);
        licenciaStr=UtilidadesHash.generarHashBcrypt(licenciaStr);
        Properties prop = new Properties();
        prop.setProperty(Licencia.PROPIEDAD_USUARIO,licencia.getUsuario());
        prop.setProperty(Licencia.PROPIEDAD_LICENCIA,licenciaStr);
        prop.setProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES,licencia.getCantidadClientes().toString());
        prop.setProperty(Licencia.PROPIEDAD_INTERFAZ_RED,interfazRed.nombre);
        //setearPropiedadesModulos(prop,licen); //Setea los modulos activos
        
        TipoLicenciaEnum enumTipoLicencia=licencia.getTipoLicenciaEnum();
        prop.setProperty(Licencia.PROPIEDAD_TIPO_LICENCIA,enumTipoLicencia.getNombre());
        //Llea en el properties los datos adicionales del modulo
        licencia.llenarPropertiesModulo(prop);
        UtilidadesArchivos.grabarArchivoPropiedadesFile(path+NOMBRE_LICENCIA, prop,"Properties");
        //File file=new File(path+NOMBRE_LICENCIA);
        //crear toda la ruta si no existe
        //if (!file.exists()) {
        //    file.getParentFile().mkdirs();
        //file.mkdir();
        //}
        
        //fr = new FileOutputStream(file);
        //prop.store(fr,"Properties");
        //fr.close();
        return prop;
        //saveProperties(prop);
        //try {
        //    fr.close();
        //} catch (IOException ex) {
        //    Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //return null;
    }
    
    
    public Properties crearLicenciaDescargada(Licencia licencia)
    {
        FileOutputStream fr=null;
        try {
            Properties prop = new Properties();
            prop.setProperty(Licencia.PROPIEDAD_USUARIO,licencia.getUsuario());
            prop.setProperty(Licencia.PROPIEDAD_LICENCIA,licencia.getLicencia());
            prop.setProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES,licencia.getCantidadClientes().toString());            
            prop.setProperty(Licencia.PROPIEDAD_TIPO_LICENCIA,licencia.getTipoLicenciaEnum().getNombre());
            
            licencia.llenarPropertiesModulo(prop);
            File file=new File(path+NOMBRE_LICENCIA);
            
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            fr = new FileOutputStream(file);
            prop.store(fr,"Properties");
            fr.close();
            return prop;
            //saveProperties(prop);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Licencia getLicencia() {
        return licencia;
    }
    
    
    
}
