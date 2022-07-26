/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import java.awt.Image;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;

/**
 * Clase que contiene los parametros generales del sistema 
 * @author
 */
public abstract class ParametrosSistemaCodefac {
    /**
     * Version actual del sistema
     */
    public static final String VERSION="1.2.9.1.2";
    /**
     * El modo de configuracion del sistema
     */
    public static final ModoSistemaEnum MODO=ModoSistemaEnum.PRODUCCION;
    
    
    public static final String DIRECTORIO_RECURSOS_DEFECTO=System.getProperty("user.home") + "/codefacRecursos";

    /**
     * Puerto por defecto para configurar las conexiones en red no es final porque puedo modificar al momento de leer el archivo de configuraciones
     */
    public static  int PUERTO_COMUNICACION_RED=1099;
    /**2.8.
     * Nombre de la base de datosv1
     */
    public static final String NOMBRE_BASE_DATOS="Derby2.DB";
    
    /**
     * Icono por defecto para usar en todo el sistema
     */
    public static final Image iconoSistema=new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("logoCodefac-ico.png")).getImage();

    /**
     * Cadena que contiene la clave para encriptar o desencriptar los datos en codefac
     */
    public static final String LLAVE_ENCRIPTAR="codesoft23codefac01"; //Todo: Analizar para obtener esta clave de un ddl en c++ para que no puedan ver con ingeniera inversa, Advertencia: no cambiar la clave porque datos encriptados con este clave ya no se podrian desencriptar
    
    /**
     * Pagina que se carga por defecto cuando carga las ayudas
     */
    public static final String PAGINA_DEFECTO_AYUDA="http://www.cf.codesoft-ec.com/ayuda";
    
    /**
     * Puerto por el que van a aceptar conexiones para el app movil de Codefac
     */
    public static  Integer PUERTO_APP_MOVIL_SMS=9999;
    
    public static final String CODIGO_TELEFONO_ECUADOR="+593";
    
    public static final Integer LIMITE_CARACTERES_SMS=160;
    
    public static final String BODEGA_NOMBRE_DEFECTO="Bodega principal";
    
    public static final String CARPETA_DATOS_TEMPORALES="tmp";
    
    
    public static final String LINK_CONSULTAS_SRI="https://declaraciones.sri.gob.ec/tuportal-internet/accederAplicacion.jspa?redireccion=60&idGrupo=58";
     
    public static final String LINK_ANULACIONES_SRI="https://declaraciones.sri.gob.ec/tuportal-internet/accederAplicacion.jspa?redireccion=61&idGrupo=58";
    
    /**
     * Variable que sirve para establecer el redondeo por defecto de todo el sistam
     */
    public static final RoundingMode REDONDEO_POR_DEFECTO=RoundingMode.HALF_UP;
        
    
    /**
     * Enlaces de los Web Service del Sri
     */
    public static final String SRI_WS_RECEPCION="https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    public static final String SRI_WS_RECEPCION_PRUEBA="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    public static final String SRI_WS_AUTORIZACION="https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
    public static final String SRI_WS_AUTORIZACION_PRUEBA="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
    
    /**
     * Nombre de usuario por defecto para poder configurar los datos de la empresa por primera vez
     */
    public static final String CREDENCIALES_USUARIO_CONFIGURACION="admin";
    
    /**
     * Variable que establece el iva actual que esta manejando el sistema
     * TODO: Analizar este tema porque actualmente lo controla como parametro en la base de datos pero parece ser una variable del sistema mas que de l base
     */
    public static final String IVA_DEFECTO="12";
    
    /**
     * Formato Estandar de las fechas , ver si este tema puede ser seleccionado si algun rato algun cliente tan personalizado que ese dato sea parametrizable
     */
    public static final SimpleDateFormat FORMATO_ESTANDAR_FECHA = new SimpleDateFormat("dd/MM/yyyy");  
    
    public static final SimpleDateFormat FORMATO_ESTANDAR_FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    
    /**
     * Variable para configurar los intentos maximos para verificar conexion con el Sri
     */
    public static final Integer INTENTOS_MAXIMO_VERICAR_CONEXION_SRI=5;
    
    /**
     * Etiqueta oculta detalle factura
     */
    public static String ETIQUETA_OCULTAR_DETALLE_FACTURA="[Hidden]";
    
    /**
     * Etiqueta que me permite controlar el tamanio maximo para generar los codigos de los documentos 
     */
    public static Integer TAMANIO_CODIGOS=8;
    /**
     * Caracter para realizar la separacion al generar los codigos
     */
    public static String CARACTER_SEPARACION_CODIGO="-";
    
    public static final String LINK_PUBLICIDAD_CODEDAC="http://www.cf.codesoft-ec.com/index.php/general/publicidad2";
    
    public static final String LINK_REGISTRO_CODEFAC="http://www.cf.codesoft-ec.com/index.php/general/registro";
    
    public static final String LINK_PERMISOS_CORREO_CODEFAC="https://docs.google.com/document/d/1AJGbwPOj4rw0uiSMUv0FJQRMlNoYNRbl2TcoE_24ivk/edit#heading=h.qyz6sq2ltuij";
    public static final String LINK_FIMA_ELECTRONICA_CODEFAC="https://docs.google.com/document/d/1AJGbwPOj4rw0uiSMUv0FJQRMlNoYNRbl2TcoE_24ivk/edit#heading=h.vckvdakikwdv";
    public static final String LINK_REQUISITOS_PREVIOS="https://docs.google.com/document/d/1AJGbwPOj4rw0uiSMUv0FJQRMlNoYNRbl2TcoE_24ivk/edit#heading=h.birn2dqy78e";
    
    public static String IP_SERVIDOR_PRUEBA="codesoft-ec.com";
    
    /**
     * Correo para enviar correos y verificar que los usuarios no tenga problemas con las credenciales de sus correso configurados
     * en el sistema codefac
     */
    public static String CORREO_COMPROBACION_CORREOS="codefac.test@gmail.com";
    
    public static String REPOSITORIO_ACTUALIZACION_ESTABLE="http://www.cf.codesoft-ec.com/uploads/versiones/";
    public static String REPOSITORIO_ACTUALIZACION_DESARROLLO="http://www.cf.codesoft-ec.com/uploads/version_desarrollo/";
    
    public static final String NOMBRE_SISTEMA="Codefac";
    
    public static final String CODIGO_NO_APLICA_RETENCIONES="000";
    
    /***
     * Variable que establece la cantidad maxima de días previos que se pueden emitir una factura
     */
    public static final Integer MAX_DIAS_FACURAR=30;
    
    public static final Integer MAX_COMPROBANTES_ELECTRONICOS_LOTE=35;
    
    public static final Boolean PROFORMA_MODO_PRUEBA=false;
    
    public static final String CORREO_DEFECTO_USUARIO="codefac.test@gmail.com";
    public static final String CORREO_DEFECTO_CLAVE="26hhdTtckLvAO/VRy7q+dQ==";
    public static final String CORREO_DEFECTO_HOST="smtp.gmail.com";
    public static final String CORREO_DEFECTO_PUERTO="587";
    
    public static final String CORREO_SOPORTE="info@codesoft-ec.com";
    
    public static final EstiloCodefacEnum TEMA_DEFECTO_CODEFAC=EstiloCodefacEnum.MCWIN;
    
    public static final BigDecimal MONTO_MAXIMO_VENTAS_CONSUMIDOR_FINAL=new BigDecimal("200");
    
    public abstract class ComprobantesElectronicos
    {
        public static final String LOGO_SIN_FOTO="sin_imagen.jpg";
    }
    
    public abstract class MensajesSistemaCodefac
    {
        public static final String MENSAJE_PIE_PAGINA_GRATIS="Reporte generado con Codefac versión gratuita, descargada en www.cf.codesoft-ec.com";
    }
    
    public abstract class PresentacionDecimales
    {
        public static final int DECIMALES_DEFECTO_PRODUCTO=4;
    }
    
    
}
