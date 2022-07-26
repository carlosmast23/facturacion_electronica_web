/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.other.session;

//import com.sun.xml.internal.ws.client.ClientTransportException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;
import ec.com.codesoft.codefaclite.utilidades.varios.InterfazRed;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.io.FileWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Licencia implements Serializable{

    private static final Logger LOG = Logger.getLogger(Licencia.class.getName());
    
    
    
    public static final String PROPIEDAD_USUARIO="usuario";
    public static final String PROPIEDAD_LICENCIA="licencia";
    public static final String PROPIEDAD_TIPO_LICENCIA="tipo";
    public static final String PROPIEDAD_CANTIDAD_CLIENTES="clientes";
    /**
     * Metodo que permite guardar con que interfaz se guardo la licencia para que vuelva a generar
     */
    public static final String PROPIEDAD_INTERFAZ_RED="interfaz_red";
    
    private List<InterfazRed> macListValidas;
    private PropertiesConfiguration propiedades;
    private TipoLicenciaEnum tipoLicenciaEnum;
    
    
    private String usuario;
    private String licencia;
    //private EnumTipo tipoLicencia;
    private Integer cantidadClientes;
    /**
     * Todo: Ver si se puede usar el objeto InterfazRed
     */
    private String nombreInterfazRed;
    
    /**
     * Variable para almacenar los modulos activos
     */
    private List<ModuloCodefacEnum> modulosActivos;

    public Licencia() {
    }
    
    

    public Licencia(PropertiesConfiguration propiedades) {
        this.propiedades = propiedades;
        //this.mac=obtenerMac(interfazRed);
        //propiedades.getString(PROPIEDAD_TIPO_LICENCIA);
        this.tipoLicenciaEnum=TipoLicenciaEnum.getEnumByNombre(propiedades.getString(PROPIEDAD_TIPO_LICENCIA));        
    }
    
    
    public List<InterfazRed> obtenerInterfacesRedDisponibles(String interfazRedDefecto)
    {
        //UtilidadVarios.obtenerMac();
        return UtilidadVarios.obtenerMacSinInternet(interfazRedDefecto);
    }
    
    public boolean validarLicencia()
    {
        cargarPropiedadesLicenciaFisica(propiedades);
        this.tipoLicenciaEnum=TipoLicenciaEnum.getEnumByNombre(propiedades.getString(PROPIEDAD_TIPO_LICENCIA));
        
        if(this.tipoLicenciaEnum==null)
        {
            return false; //Si no existe ningun tipo de usuario reporta falso
        }

        
        String modulosStr=getModulosStr(); //Obtiene los valores de los modulos activos con una cadena para validar la licencia
               
        try
        {
            for(InterfazRed interfazRed:macListValidas)
            {
                //Validacion cuando el usuario si esta registrado con licencia gratis 
                String licenciaTxt=usuario + ":" + interfazRed.mac + ":" + tipoLicenciaEnum.getLetra()+":"+cantidadClientes+":"+modulosStr;
                LOG.log(Level.INFO,licenciaTxt);
                Boolean validacionLicencia= UtilidadesHash.verificarHashBcrypt(licenciaTxt, licencia);
                if(validacionLicencia)
                {
                    //Antes de devolver el resulto verifico si la interfaz de red fue modificada para grabar
                    verificarInterfazRedModificada(interfazRed);
                    return true;
                }
            }
            
        }
        catch(java.lang.IllegalArgumentException iae)
        {
            iae.printStackTrace();
        }
        
        return false;

    }
    
    private void verificarInterfazRedModificada(InterfazRed interfazRed )
    {
        if(nombreInterfazRed!=null)
        {
            if(!nombreInterfazRed.equals(interfazRed.nombre))
            {
                try {
                    propiedades.setProperty(Licencia.PROPIEDAD_INTERFAZ_RED, interfazRed.nombre);
                    //Grabar las nuevas modificaciones en el archivo grabar
                    propiedades.save();
                } catch (ConfigurationException ex) {
                    Logger.getLogger(Licencia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Devuelve una cadena construida con los modulos para verificar la licencia
     * @return 
     */
    public String getModulosStr()
    {
        ModuloCodefacEnum[] modulos=ModuloCodefacEnum.values();
        String modulosStr="";
        
        
        for (ModuloCodefacEnum modulo : modulosActivos) {
            modulosStr+=modulo.getLetra();
        }

        return modulosStr;
    }
    

    public void cargarLicenciaOnline(String usuario) throws Exception 
    {
        this.usuario=usuario;
        this.licencia = WebServiceCodefac.getLicencia(usuario);
        this.tipoLicenciaEnum =tipoLicenciaEnum.getEnumByLetra(WebServiceCodefac.getTipoLicencia(usuario));
        this.cantidadClientes = WebServiceCodefac.getCantidadClientes(usuario);
        cargarModulosOnline();
    }
    
    public void cargarModulosOnline() {
        this.modulosActivos=new ArrayList<ModuloCodefacEnum>();
        for (ModuloCodefacEnum modulo : ModuloCodefacEnum.values()) {
            if(castStringBooleanOnline(WebServiceCodefac.getModuloCodefac(usuario,modulo.getLetra())))
            {
                this.modulosActivos.add(modulo);
            }
        }
    }

    public void cargarPropiedadesLicenciaFisica(PropertiesConfiguration propiedades)
    {   
        //Si no existe ningun propiedad cargada omito este paso porque no va a cargar ningun dato
        if(propiedades==null)
        {
            return ;
        }
        
        this.usuario = propiedades.getString(Licencia.PROPIEDAD_USUARIO);
        
        //Nombre de la interfaz de red con la cual se debe buscar para validar la licencia
        this.nombreInterfazRed = propiedades.getString(Licencia.PROPIEDAD_INTERFAZ_RED);
        this.licencia = propiedades.getString(Licencia.PROPIEDAD_LICENCIA);
        this.tipoLicenciaEnum = TipoLicenciaEnum.getEnumByNombre(propiedades.getString(Licencia.PROPIEDAD_TIPO_LICENCIA));
        this.cantidadClientes = Integer.parseInt(propiedades.getString(Licencia.PROPIEDAD_CANTIDAD_CLIENTES));
        
        this.macListValidas=obtenerInterfacesRedDisponibles(nombreInterfazRed);
        
        //Cargar los modulos activos desde el archivo de propiedades
        this.modulosActivos=new ArrayList<ModuloCodefacEnum>();
        for (ModuloCodefacEnum modulo :ModuloCodefacEnum.values()) {
            //Si existe el modulo lo agrega a lista de modulos diponibles
            if(propiedades.getProperty(modulo.getNombrePropiedad())!=null)
            {
                modulosActivos.add(modulo);
            }
            
        }
    }
    
    /**
     * Comparar la licencia con otra
     * @param licencia
     * @return 
     */
    public boolean compararOtraLicencia(Licencia licencia)
    {
        System.out.println("Comparar las licencias");
        System.out.println(this.licencia+" - "+licencia.getLicencia());
        System.out.println(this.tipoLicenciaEnum+" - "+licencia.getTipoLicenciaEnum());
        System.out.println(this.cantidadClientes+" - "+licencia.getCantidadClientes());
        System.out.println(this.getModulosStr()+" - "+licencia.getModulosStr());
        
        if (this.licencia.equals(licencia.getLicencia())
                && this.tipoLicenciaEnum.equals(licencia.getTipoLicenciaEnum())
                && this.cantidadClientes.equals(licencia.getCantidadClientes())
                && this.getModulosStr().equals(licencia.getModulosStr())) {
            
            return true;
        }
        return false;
    }
    
    //Metodo para converitir de String a boolean
    private boolean castStringBooleanOnline(String opcion)
    {
        if(opcion!=null && opcion.equals("success"))
        {
            return true;
        }
        return false;
    }
    
        //Metodo para converitir de String a boolean
    private boolean castStringBooleanProperty(String opcion)
    {
        if(opcion!=null && opcion.equals("si"))
        {
            return true;
        }
        return false;
    }
    
    public List<ModuloCodefacEnum> getModulosSistema()
    {
        /*
        Map<ModuloCodefacEnum,Boolean> modulosMap=new HashMap<ModuloCodefacEnum,Boolean>();
        
        //Si la licencia es gratis no tomo en cuenta ningun modulo
        //if(tipoLicenciaEnum.equals(TipoLicenciaEnum.GRATIS))
        //    return modulosMap;
        
        
        for (ModuloCodefacEnum modulo : ModuloCodefacEnum.values()) {
            //Si el modulo esta dentro de la lista de modulos activos lo pongo true
            if(modulosActivos.contains(modulo))
            {
                modulosMap.put(modulo,true);
            }
            else
            {
                modulosMap.put(modulo,false);
            }
        }
        return modulosMap;*/
        return modulosActivos;
    }
    
    public void llenarPropertiesModulo(Properties propiedades)
    {
        
        ModuloCodefacEnum[] modulos=ModuloCodefacEnum.values();
        
        //Llenar en orden dependiendo que modulos estan activos
        for (ModuloCodefacEnum modulo : modulos) 
        {
            //Si contiene el modulo dentro de la lista significa que esta activo
            if(modulosActivos.contains(modulo))
            {
                propiedades.setProperty(modulo.getNombrePropiedad(),"si");
            }            
        }
        
    }
    
    
    
    ////METODOS GET AND SET //////////////////////
    
    public PropertiesConfiguration getPropiedades() {
        return propiedades;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Integer getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(Integer cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }


    public void setTipoLicenciaEnum(TipoLicenciaEnum tipoLicenciaEnum) {
        this.tipoLicenciaEnum = tipoLicenciaEnum;
    }

    public TipoLicenciaEnum getTipoLicenciaEnum() {
        return tipoLicenciaEnum;
    }

    public List<ModuloCodefacEnum> getModulosActivos() {
        return modulosActivos;
    }

    public void setModulosActivos(List<ModuloCodefacEnum> modulosActivos) {
        this.modulosActivos = modulosActivos;
    }

    public String getNombreInterfazRed() {
        return nombreInterfazRed;
    }

    public void setNombreInterfazRed(String nombreInterfazRed) {
        this.nombreInterfazRed = nombreInterfazRed;
    }
    
    
    

    
}
