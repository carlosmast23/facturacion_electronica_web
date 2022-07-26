/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.archivos;

import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * TODO: Optimizar el problema que esta clase no deberia interactuar directamente con el atributo de las propiedades
 * por que se supone que esta clase funciona como una clase de envoltorio y debe comunciarse por metodos de put y set
 * por que si algun rato se cambia de implementacion solo se modifica en esta clase y no en todos los lugares que usen el atributo property
 * @author
 */
public class ArchivoConfiguracionesCodefac {

    private static final Logger LOG = Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName());
    
    
       /**
     * Nombre del archivo de configuraciones iniciales
     */
    private static final String NOMBRE_ARCHIVO_CONFIGURACION = "codefac.ini";

    public static final String CAMPO_MODO_APLICATIVO = "modo";
    
    /***
     * Este campo es para los clientes para que tengan grabado la ip del servidor
     */
    public static final String CAMPO_IP_ULTIMO_ACCESO_SERVIDOR="servidorip";
    
    /**
     * Este campo es para saber el ultimo tipo de acceso del cliente si fue remoto o local
     */
    public static final String CAMPO_TIPO_CLIENTE="tipo_cliente";
    
    /**
     * Este campo es paro el servidor para que inicie el aplicativo en la direccion de red ingresada
     */
    public static final String CAMPO_IP_SERVIDOR="ip";
    
    /**
     * Este campo sirve para especificar cuando un servidor acepta conexiones desde una ip publica 
     */
    public static final String CAMPO_IP_PUBLICA_SERVIDOR="ip_publica";
    
    public static final String CAMPO_VERSION="version";
    
    public static final String CAMPO_TEMA="tema";
    
    public static final String CAMPO_PUERTO_SISTEMA="puerto_sistema";
    public static final String CAMPO_PUERTO_SMS="puerto_sms";
    
    /**
     * Este campo especifica si se desea activar o no el servicio web al iniciar la aplicacion
     * Valores validos (si o no)
     */
    public static final String CAMPO_ACTIVAR_SERVICIO_WEB="activar_servicio_web";
    
    /**
     * Campo para especificar de que interfaz en particular esta generando la licencia por sis tiene varias y luego tiene problemas con la licencia
     */
    public static final String CAMPO_INTERFAZ_RED_LICENCIA="interfaz_red_licencia";
    
    /**
     * Establece el tipo de modo que se puede actualizar la aplicacion
     * Opiones esta en el enum ModoActualizacionEnum
     */
    public static final String CAMPO_MODO_ACTUALIZACION="modo_actualizar";
    
    /**
     * Campo que me sirve para guardar y almacenar cual es la sucursal seleccionada 
     * para que aparesca por defecto la siguiente vez que ingrese al sistema
     */
    public static final String CAMPO_SUCURSAL_INICIAL_ID="sucursal_inicial_id";
    
    /**
     * Este campo me va a permitir actualizar de forma forzada cuando por ejemplo tenga un error en la actualizacion por temas de conectividad y tenga que actualziar de nuevo
     */
    public static final String CAMPO_FORZAR_ACTUALIZACION="forzar_actualizar";
    
    private PropertiesConfiguration propiedadesIniciales;
    
    private static ArchivoConfiguracionesCodefac instance;
    
    public static ArchivoConfiguracionesCodefac getInstance()
    {
        if(instance==null)
            instance=new ArchivoConfiguracionesCodefac();
        
        return instance;
    }
    

    public ArchivoConfiguracionesCodefac() {
    }
    
    public void agregarCampo(String nombreCampo,String valor)
    {        
        propiedadesIniciales.setProperty(nombreCampo, valor);
        
    }
    
    public String obtenerValor(String nombreCampo)
    {
        return (propiedadesIniciales.getString(nombreCampo)!=null)?propiedadesIniciales.getString(nombreCampo).toString():null;
        //return (propiedadesIniciales.get(nombreCampo)!=null)?propiedadesIniciales.get(nombreCampo).toString():null;
    }
    
    public void cargarConfiguracionesIniciales()
    {
    
         try {
            propiedadesIniciales = new PropertiesConfiguration(NOMBRE_ARCHIVO_CONFIGURACION);
            //propiedadesIniciales.load(new FileReader(NOMBRE_ARCHIVO_CONFIGURACION));
            //propiedadesIniciales.load(new FileReader(NOMBRE_ARCHIVO_CONFIGURACION));

        } catch (ConfigurationException ex) {
            Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardar() throws IOException
    {
        try {
            propiedadesIniciales.save();
            //propiedadesIniciales.store(new FileWriter(ArchivoConfiguracionesCodefac.NOMBRE_ARCHIVO_CONFIGURACION), "");
        } catch (ConfigurationException ex) {
            Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PropertiesConfiguration getPropiedadesIniciales() {
        return propiedadesIniciales;
    }
    
    public Sucursal getSucursalPorDefecto()
    {
        String sucursalId=obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_SUCURSAL_INICIAL_ID);
        if(sucursalId!=null && !sucursalId.trim().isEmpty())
        {
            try {
                Sucursal sucursal=ServiceFactory.getFactory().getSucursalServiceIf().buscarPorId(Long.parseLong(sucursalId));
                return sucursal;                
            } catch (RemoteException ex) {
                Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public void setSucursalPorDefecto(Sucursal sucursal)
    {
        if(sucursal!=null)
        {
            agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_SUCURSAL_INICIAL_ID,sucursal.getId().toString());
            try {
                guardar();
            } catch (IOException ex) {
                Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ///                          CLASES E INTEFACES
    ////////////////////////////////////////////////////////////////////////////
    
    public enum ModoActualizacionEnum
    {
        ESTABLE("estable"),
        DESARROLLO("desarrollo");

        private ModoActualizacionEnum(String nombre) {
            this.nombre = nombre;
        }
        
        private String nombre;

        public String getNombre() {
            return nombre;
        }
        
    }
}
