/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobante;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantesOffline;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantesOfflineService;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import autorizacion.ws.sri.gob.ec.RespuestaLote;
import com.thoughtworks.xstream.XStream;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_AUTORIZADOS;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import static ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil.archivoToByte;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.XStreamUtil;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOffline;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOfflineService;
import ec.com.codesoft.codefaclite.ws.recepcion.RespuestaSolicitud;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import org.jfree.util.Log;

/**
 *
 * @author Carlos
 */
public class ServicioSri {
 
    private static final String namespaceURI="http://ec.gob.sri.ws.recepcion";
    private static final String namespaceAutorizarURI="http://ec.gob.sri.ws.autorizacion";
    private static final String localPort="RecepcionComprobantesOfflineService";
    private static final String localPortAutorizacion="AutorizacionComprobantesOfflineService";
    
    public static final String AUTORIZADO="AUTORIZADO";
    
    private static final Long TIEMPO_ESPERA_AUTORIZACION =1000L;
    /**
     * Numeros de intento para esperar que el sri me devuelva la consulta de autorizacion de un documentos
     */
    private static final Long INTENTOS_AUTORIZACION =10L; //Esperar maximo 10 segundos 
    
    private static final Long INTENTOS_AUTORIZACION_LOTE = 30L;
    
    //private String uri="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    private String uri_recepcion;
    private String uri_autorizacion;
    
    private RecepcionComprobantesOfflineService servicio;
    private AutorizacionComprobantesOfflineService servicioAutorizacion;
    
    private List<Mensaje> mensajes;
    private List<Autorizacion> autorizaciones ;
    
    /**
     * En esta lista se van a grabar los comprobantes que tuvieron problemas al enviar al SRI para que les autoricen
     */
    private List<Comprobante> comprobantesNoRecibidos;
    /**
     * El modo con el que va a trabajar el web service
     */
    private String modo;
    
    /**
     * Path donde se encuentra el archivo para enviar o recibir
     */
    private String urlFile;

    public ServicioSri() {
    }
    
    

    public ServicioSri(String uri_recepcion,String modo) {
        this.uri_recepcion = uri_recepcion;
        this.modo=modo;
    }
    
    public ServicioSri(String uri_recepcion) {
        this.uri_recepcion = uri_recepcion;
    }

    public String getUri_recepcion() {
        return uri_recepcion;
    }

    public void setUri_recepcion(String uri_recepcion) {
        this.uri_recepcion = uri_recepcion;
    }

    public String getUri_autorizacion() {
        return uri_autorizacion;
    }

    public void setUri_autorizacion(String uri_autorizacion) {
        this.uri_autorizacion = uri_autorizacion;
    }
    
    
    
            
    public boolean verificarConexionRecepcion() throws ComprobanteElectronicoException{
        try {
            URL url = new URL(uri_recepcion);
            QName qname = new QName(namespaceURI,localPort);
            servicio = new RecepcionComprobantesOfflineService(url, qname);
            System.out.println("si existe servicio con sri");
            return true;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no existe servicio");
            throw new ComprobanteElectronicoException("Error de formato de la url"+"\n"+ex.getMessage(),"Verificar conexion recibir documentos SRI",ComprobanteElectronicoException.ERROR_COMPROBANTE);
            
        } catch(javax.xml.ws.WebServiceException wse)
        {
            wse.printStackTrace();
            System.out.println("No se puede acceder al web servicio");
            throw new ComprobanteElectronicoException("No existe conexion con el SRI para enviar documentos"+"\n"+wse.getMessage(),"Verificar conexion recibir documentos SRI",ComprobanteElectronicoException.ERROR_COMPROBANTE);
            
        }
    }

    public boolean verificarConexionAutorizar()
    {
        try {
            URL url = new URL(uri_autorizacion);
            QName qname = new QName(namespaceAutorizarURI,localPortAutorizacion);
            servicioAutorizacion=new AutorizacionComprobantesOfflineService(url,qname);
            System.out.println("si existe servicio con sri autorizacion");
            return true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no existe servicio");
            return false;
        } catch(javax.xml.ws.WebServiceException ex)
        {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error con el web service");
            return false;
        }
    }
    
    /**
     * Todo : Ver si en un futuro puedo usar el mismo metodo que enviar por lote
     * @return 
     */
    public Boolean enviar() throws ComprobanteElectronicoException
    {
        //TODO: Solucion temporal que aveces sale un error de conexion con el web service para intentar unas 3 veces antes de terminar
        final int INTENTO_MAXIMO=10;
        for (int i = 0; ;) {
            try {
                //File archivoXMLFirmado = new File("C:\\CodefacRecursos\\comprobantes\\pruebas\\firmados\\0103201801172421895100110010010000000010000000011.xml");
                File archivoXMLFirmado = new File(urlFile);
                RecepcionComprobantesOffline port = servicio.getRecepcionComprobantesOfflinePort();
                RespuestaSolicitud respuestaSolicitud = port.validarComprobante(archivoToByte(archivoXMLFirmado));
                //System.out.println(respuestaSolicitud.getEstado()); //RECIBIDA DEVUELTA           
                if (respuestaSolicitud.getComprobantes().getComprobante().size() == 0) {
                    return true;
                } else {
                    mensajes = respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje();
                    return false;
                }

            } catch (IOException ex) {
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                if(i==INTENTO_MAXIMO)
                {
                    throw new ComprobanteElectronicoException(ex.getMessage(), "Enviando Sri", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
                else
                {
                    i++;
                }
            }
        }
        
        //return false;
    } 
    
    
    public void enviarLote() throws ComprobanteElectronicoException
    {
        final int INTENTO_MAXIMO=10;
        for (int i = 0; ; ) {
            try {
                comprobantesNoRecibidos=new ArrayList<Comprobante>();
                //File archivoXMLFirmado = new File("C:\\CodefacRecursos\\comprobantes\\pruebas\\firmados\\0103201801172421895100110010010000000010000000011.xml");
                File archivoXMLFirmado = new File(urlFile);
                RecepcionComprobantesOffline port= servicio.getRecepcionComprobantesOfflinePort();
                RespuestaSolicitud respuestaSolicitud = port.validarComprobante(archivoToByte(archivoXMLFirmado));
                //System.out.println(respuestaSolicitud.getEstado()); //RECIBIDA DEVUELTA         
                comprobantesNoRecibidos=respuestaSolicitud.getComprobantes().getComprobante();
                for (Comprobante comprobantesNoRecibido : comprobantesNoRecibidos) {
                    
                    //Si en los comprobantes no recibidos viene el signo n/a significa que es un error global no del comprobante
                    if(comprobantesNoRecibido.getClaveAcceso().equals("N/A"))
                    {                        
                        for (Mensaje mensaje : comprobantesNoRecibido.getMensajes().getMensaje()) {
                            /*String mensajeError=
                                    "Identificador:"+mensaje.getIdentificador()+"\n"+
                                    "Info Adicional:"+mensaje.getInformacionAdicional()+"\n"+
                                    "Mensaje:"+mensaje.getMensaje()+"\n"+
                                    "Tipo:"+mensaje.getTipo();                            */
                            String mensajeError=UtilidadesComprobantes.castMensajeToString(mensaje);
                            throw new ComprobanteElectronicoException(mensajeError, "Enviando Sri Lote", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                        }
                    }
                }
                
                break; //Si termina el proceso normal sale del ciclo repetitivo
                /*if(respuestaSolicitud.getComprobantes().getComprobante().size()==0)
                {
                    return true;
                }
                else
                {
                    mensajes=respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje();
                    return false;
                }*/

            } catch (IOException ex) {
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch(Exception ex)
            {
                ex.printStackTrace();
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);            
                if(i==INTENTO_MAXIMO)
                {
                    throw new ComprobanteElectronicoException(ex.getMessage(), "Enviando Sri", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
                else
                {
                    i++;
                }
                //throw new ComprobanteElectronicoException(ex.getMessage(),"Enviando Sri",ComprobanteElectronicoException.ERROR_COMPROBANTE);
            }
        }
       //comprobantesNoRecibidos return new ArrayList<Comprobante>();
    }    
   
    public boolean autorizar(String claveAcceso) throws ComprobanteElectronicoException
    {
       if(verificarConexionAutorizar())
       {
           
           for(int i=0;i<INTENTOS_AUTORIZACION;i++)
           {
               try {
                   AutorizacionComprobantesOffline port= servicioAutorizacion.getAutorizacionComprobantesOfflinePort();
                   RespuestaComprobante respuesta=port.autorizacionComprobante(claveAcceso);
                   autorizaciones=respuesta.getAutorizaciones().getAutorizacion();
                   if(autorizaciones.size()==0)
                   {
                        Thread.sleep(TIEMPO_ESPERA_AUTORIZACION);                        
                   }
                   else
                   {
                       if(autorizaciones.get(0).getEstado().equals(AUTORIZADO))
                       {
                            return true;
                       }if(autorizaciones.get(0).getEstado().equals("EN PROCESO"))
                       {
                           //TODO: Si el mensaje es en proceso sigue esperando hasta que devuelva autorizado o error
                       }
                       else
                       {
                           Autorizacion.Mensajes mensajes=autorizaciones.get(0).getMensajes();
                           String mensajeError="";
                           for (autorizacion.ws.sri.gob.ec.Mensaje mensaje : mensajes.getMensaje()) {
                               System.out.println(mensaje.getIdentificador());
                               System.out.println(mensaje.getInformacionAdicional());
                               System.out.println(mensaje.getMensaje());
                               System.out.println(mensaje.getTipo());
                               mensajeError+=mensaje.getMensaje()+"\n"+mensaje.getInformacionAdicional();
                           }
                           throw new ComprobanteElectronicoException(mensajeError," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                           //for
                       }
                   }
               } catch (InterruptedException ex) {
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                   throw new ComprobanteElectronicoException(ex.getMessage()," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
               } catch (Exception ex)
               {
                   ex.printStackTrace();
                   throw new ComprobanteElectronicoException(ex.getMessage()," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
               }
           }
           
           //Si sale del bucle sin retornar asumo que excedio el tiempo de espera
           throw new ComprobanteElectronicoException("Se excedio el tiempo de espera para autorizar el documento , Por favor inténtelo mas tarde","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       else
       {
           throw new ComprobanteElectronicoException("El servicio para autorizar el comprobante no esta disponible","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       //return false;
       
    }
    
    public boolean autorizarLote(String claveAcceso) throws ComprobanteElectronicoException
    {
       if(verificarConexionAutorizar())
       {
           
           for(int i=0;i<INTENTOS_AUTORIZACION_LOTE;i++)
           {
               try {
                   AutorizacionComprobantesOffline port= servicioAutorizacion.getAutorizacionComprobantesOfflinePort();
                   RespuestaLote respuesta=port.autorizacionComprobanteLote(claveAcceso);
                   autorizaciones=respuesta.getAutorizaciones().getAutorizacion();
                   if(autorizaciones.size()==0)
                   {
                        Thread.sleep(TIEMPO_ESPERA_AUTORIZACION);                        
                   }
                   else
                   {
                       Boolean esperarAutorizacion=false;
                       for (Autorizacion autorizacion : autorizaciones) {
                           if (autorizacion.getEstado().equals("AUTORIZADO") || autorizacion.getEstado().equals("NO AUTORIZADO")) 
                           {
                               //Si solo existe un unico registro y no se autoriza lanzo el error
                               if(autorizacion.getEstado().equals("NO AUTORIZADO") && autorizaciones.size()==1)
                               {
                                   String mensajeError=UtilidadesComprobantes.castMensajeAutorizadoToString(autorizacion.getMensajes());
                                   throw new ComprobanteElectronicoException(mensajeError,"Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                               }
                               break;
                           }
                           else if(autorizacion.getEstado().equals("EN PROCESO"))
                           {
                               esperarAutorizacion=true;
                           }
                       }
                       
                       
                       if(!esperarAutorizacion)
                       {
                            return true;
                       }
                       
 
                   }
               } catch (InterruptedException ex) {
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           //Si sale del bucle sin retornar asumo que excedio el tiempo de espera
           throw new ComprobanteElectronicoException("Se excedio el tiempo de espera para autorizar el documento , Por favor inténtelo mas tarde","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
           
       }
       else
       {
           throw new ComprobanteElectronicoException("No existe comunicación con el Sri para autorizar el comprobante","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       //return false;
       
    }
    
    /**
     * Busca entre los documentos autorizados alguno en especifico por la clave de acceso
     * @return 
     */
    public Autorizacion buscarAutorizacion(String claveAcceso)
    {
        for (Autorizacion autorizacion : autorizaciones) {
            if(autorizacion.getNumeroAutorizacion()!=null && autorizacion.getNumeroAutorizacion().equals(claveAcceso))
            {
                return autorizacion;
            }
        }
        return null;
    }
    
    /**
     * Obtiene la respuesta del comprobante individual y lo transforma en texto
     * @return
     * @throws ComprobanteElectronicoException 
     */
    public String obtenerRespuestaAutorizacion() throws ComprobanteElectronicoException
    {
        try {
            Autorizacion item=autorizaciones.get(0);
            //System.out.println(item.getFechaAutorizacion());
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");            
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            
            /**
             * Solucion temporal para obtener la fecha con el tiempo 
             */
            int posicionInicial=xmlAutorizacion.indexOf("<fechaAutorizacion class=\"fechaAutorizacion\">");
            int posicionFinal=xmlAutorizacion.indexOf("</fechaAutorizacion>");
            xmlAutorizacion=xmlAutorizacion.substring(0,posicionInicial)+"<fechaAutorizacion>"+item.getFechaAutorizacion()+xmlAutorizacion.substring(posicionFinal,xmlAutorizacion.length());
            
            //System.out.println(xmlAutorizacion);
            
            if (item.getEstado().equals("AUTORIZADO")) {
                return xmlAutorizacion;
            }
            else
            {
                throw  new ComprobanteElectronicoException("Documeto no autorizado","Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                //implementar cuando esta autorizado
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    public ComprobanteElectronico castComprobanteToAutorizacion(Autorizacion autorizacion)
    {
        //Si no existe nada grabado en el campo de autorizacion entonces devuelvo nulo
        if(autorizacion.getComprobante()==null)
            return null;
        
        if(autorizacion.getNumeroAutorizacion()==null || autorizacion.getNumeroAutorizacion().isEmpty())
        {
            return null;
        }
        
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(autorizacion.getNumeroAutorizacion());
            //JAXBContext jaxbContext = JAXBContext.newInstance(ComprobanteEnum.FACTURA.getClase());
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getTipoComprobante().getClase());
            String comprobanteStr=autorizacion.getComprobante();
            //Quitar etiquetas de cdata
            comprobanteStr=comprobanteStr.replaceAll("<!\\[CDATA\\[","");
            comprobanteStr=comprobanteStr.replaceAll("\\]\\]>","");
                    
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();      
            StringReader reader = new StringReader(comprobanteStr);
            
            ComprobanteElectronico comprobanteElectronico=(ComprobanteElectronico)jaxbUnmarshaller.unmarshal(reader);
            return comprobanteElectronico;
            
        } catch (JAXBException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Cambiar el formato de autorizacion  a String
     * @param item
     * @return
     * @throws ComprobanteElectronicoException 
     */
    public String castAutorizacionToString(Autorizacion item) throws ComprobanteElectronicoException
    {
          try {
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");            
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            
            /**
             * Solucion temporal para obtener la fecha con el tiempo 
             */
            int posicionInicial=xmlAutorizacion.indexOf("<fechaAutorizacion class=\"fechaAutorizacion\">");
            int posicionFinal=xmlAutorizacion.indexOf("</fechaAutorizacion>");
            xmlAutorizacion=xmlAutorizacion.substring(0,posicionInicial)+"<fechaAutorizacion>"+item.getFechaAutorizacion()+xmlAutorizacion.substring(posicionFinal,xmlAutorizacion.length());
            
            //System.out.println(xmlAutorizacion);
            
            if (item.getEstado().equals("AUTORIZADO")) {
                return xmlAutorizacion;
            }
            else
            {
                throw  new ComprobanteElectronicoException("Documeto no autorizado","Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                //implementar cuando esta autorizado
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
         
        
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public List<Autorizacion> getAutorizacion() {
        return autorizaciones;
    }

    public List<Comprobante> getComprobantesNoRecibidos() {
        return comprobantesNoRecibidos;
    }
    
    
    
    
    
}
