/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.other;

import static ec.com.codesoft.codefaclite.main.init.Main.modoAplicativo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class BaseDatosCredenciales {
    private static BaseDatosCredenciales entity;
    private static final String CAMPO_USUARIO_DB="usuario_db";
    private static final String CAMPO_CLAVE_DB="clave_db";
    
    private static final String NOMBRE_ARCHIVO="key.codefac";
        
    /**
     * Variable para almacenar los datos de las propiedaes
     */
    private Properties propiedades;
    
    public static BaseDatosCredenciales getInstance()
    {
        if(entity==null)
        {
            entity=new BaseDatosCredenciales();
        }
        return entity;
    }
   

    public BaseDatosCredenciales() {
        propiedades=new Properties();
    }
    
    /**
     * Carga los datos si ya estan creados
     * @return true si el archivo se puede crear
     */
    public boolean cargarDatos()
    {
        try {
            propiedades.load(new FileReader(NOMBRE_ARCHIVO));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BaseDatosCredenciales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    
    public String getUsuario()
    {
        return propiedades.getProperty(CAMPO_USUARIO_DB);
    }
    
    public String getClave()
    {
        String claveEncriptada=propiedades.getProperty(CAMPO_CLAVE_DB);
        if(claveEncriptada!=null)
        {
            try {
                String claveSinEncriptar=UtilidadesEncriptar.desencriptar(claveEncriptada,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
                return claveSinEncriptar;
            } catch (Exception ex) {
                Logger.getLogger(BaseDatosCredenciales.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
    }
    
    public void setUsuario(String usuario)
    {
        try {
            propiedades.put(CAMPO_USUARIO_DB,usuario);
            propiedades.store(new FileWriter(NOMBRE_ARCHIVO), "");
        } catch (IOException ex) {
            Logger.getLogger(BaseDatosCredenciales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void setClave(String claveSinEncriptar)
    {
        try {
            String claveEncriptada= UtilidadesEncriptar.encriptar(claveSinEncriptar, ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            propiedades.put(CAMPO_CLAVE_DB, claveEncriptada);
            propiedades.store(new FileWriter(NOMBRE_ARCHIVO), "");
        } catch (IOException ex) {
            Logger.getLogger(BaseDatosCredenciales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
