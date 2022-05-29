/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import com.sun.xml.bind.marshaller.DataWriter;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.GuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra.LiquidacionCompraComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobanteCData;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.JaxbCharacterEscapeHandler;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.ComprobanteElectronicoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.DetalleReporteData;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.FacturaElectronicaReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.GuiaRemisionReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.LiquidacionCompraReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.NotaCreditoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.RetencionElectronicaReporte;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.xml.UtilidadesXml;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoService implements Runnable {
    
    public static final String CARPETA_GENERADOS = "generados";
    public static final String CARPETA_FIRMADOS = "firmados";
    public static final String CARPETA_FIRMADOS_SIN_ENVIAR = "firmados_sin_enviar";
    public static final String CARPETA_ENVIADOS_SIN_RESPUESTA = "enviados";
    public static final String CARPETA_AUTORIZADOS = "autorizados";
    public static final String CARPETA_NO_AUTORIZADOS = "no_autorizados";
    public static final String CARPETA_RIDE = "ride";
    public static final String CARPETA_LOTE = "lote";
    
    public static final String CARPETA_CONFIGURACION = "configuracion";


    public static final String MODO_PRODUCCION = "producción";
    public static final String MODO_PRUEBAS = "pruebas";

    //TODO: Ver mejor si estos estados se convierten a un enum fuera de esta clase para tener mejor organizado
    public static final Integer ETAPA_GENERAR = 1;
    public static final Integer ETAPA_PRE_VALIDAR = 2;
    public static final Integer ETAPA_FIRMAR = 3;
    public static final Integer ETAPA_RIDE = 4;
    public static final Integer ETAPA_ENVIO_COMPROBANTE =5;
    public static final Integer ETAPA_ENVIAR = 6;
    public static final Integer ETAPA_AUTORIZAR = 7;
    public static final Integer ETAPA_ENVIO_COMPROBANTE_AUTORIZADO =8; //Etapa que deberia funcionar cuando quiere enviar el xml autorizado


    public static final Integer CODIGO_SRI_MODO_PRUEBAS = 1;
    public static final Integer CODIGO_SRI_MODO_PRODUCCION = 2;
    
    public static final Long TAMANIO_MAX_LOTE_KB=512l;

    /**
     * Etapa en la que se encuentra la facturacion
     */
    public Integer etapaActual;

    /**
     * Path base de los archivos de configuracion
     */
    private String pathBase;
    /**
     * Nombre del archivo del archivo de la firma electronica
     */
    private String nombreFirma;
    /**
     * Clave para la firma electronica
     */
    private String claveFirma;
    /**
     * Modo facturacion
     */
    private String modoFacturacion;

    /**
     * Comprobante electronica que se desea mandar a autorizar en el sri
     */
    private ComprobanteElectronico comprobante;
    
    /**
     * Conjunto de comprobante cuando se van a procesar por Lotes
     */
    private List<ComprobanteElectronico> comprobantesLote;
    
    /**
     * Ruc de la empresa variable necesaria cuando se trabaja en lote
     */
    private String ruc;
    
    /**
     * Lista de las claves de acceso para trabajar en las siguientes etapas despues de generar el comprobante
     */
    private List<String> clavesAccesoLote;

    /**
     * Clave de acceso del comprobante
     */
    private String claveAcceso;

    private String uriRecepcion;
    private String uriAutorizacion;

    private ServicioSri servicioSri;

    private URL pathFacturaJasper;

    private URL pathNotaCreditoJasper;
    
    private URL pathRetencionJasper;
    
    private URL pathGuiaRemisionJasper;
    
    private JasperReport jasperSubReporteGuiaRemision;
        
    private String pathParentJasper;
    public Image pathLogoImagen;

    private Map<String, Object> mapAdicionalReporte;
    private ListenerComprobanteElectronico escucha;
    private ListenerComprobanteElectronicoLote escuchaLote;
    
    private MetodosEnvioInterface metodoEnvioInterface;
    private MetodoEnvioSmsInterface metodoEnvioSmsInterface;
    private List<String> correosElectronicos;

    private String footerMensajeCorreo;
    private Integer etapaLimiteProcesar;
    
    private JasperReport reporteInfoOtroAdicional;
    private JasperReport reporteInfoAdicional;
    private JasperReport reporteFormaPago;
    
    private boolean enviarPorLotes;
    
    private Integer secuencialLote;
    
    /**
     * Map que establece el diccionario de los codigos
     * y los nombres de las formas de pago para poder generar el ride
     * con los valores proporicinados.
     * @return 
     */
    private Map<String,String> mapCodeAndNameFormaPago;
    
    private Map<String,String> mapCodeAndNameTipoRetecion;
        
    private Map<String,String> mapCodeAndNameTipoDocumento;
    
    private List<AlertaComprobanteElectronico> alertas;
    
    /**
     * Variable que sirve para saber si envio los correos adjuntos en el comprobante o tambien los adicionales
     */
    public boolean enviarSoloCorreosAdjuntos;
    
    /**
     * Variable que me permite  saber si quiere enviar los correos con los documentos firmados o autorizados
     */
    private Boolean enviarCorreoComprobanteAutorizado ;
    /**
     * Variables para saber si se deben enviar correos por defecto pongo verdadero
     */
    private Boolean enviarCorreos;
    
    /**
     * Map que me permite establecer nombres alternos para documentos internos, especialmente util cuando no quiere que se llame nota de venta interna
     */
    private Map<ComprobanteEnum,String> aliasNombreDocumentosMap;

    public ComprobanteElectronicoService() {
        this.etapaLimiteProcesar = 100;
        this.etapaActual = ETAPA_GENERAR;
        this.alertas=new ArrayList<AlertaComprobanteElectronico>();
        this.enviarSoloCorreosAdjuntos=false;
        this.enviarCorreoComprobanteAutorizado=false;
        this.enviarCorreos=true;
        this.aliasNombreDocumentosMap=new HashMap<ComprobanteEnum,String>();
    }

    public ComprobanteElectronicoService(String pathBase, String nombreFirma, String claveFirma, String modoFacturacion, ComprobanteElectronico comprobante) {
        this.pathBase = pathBase;
        this.nombreFirma = nombreFirma;
        this.claveFirma = claveFirma;
        this.modoFacturacion = modoFacturacion;
        this.comprobante = comprobante;
        this.etapaActual = ETAPA_GENERAR;
        this.etapaLimiteProcesar = 100;
        this.alertas=new ArrayList<AlertaComprobanteElectronico>();
        enviarSoloCorreosAdjuntos=false;
        this.enviarCorreoComprobanteAutorizado=false;
        this.enviarCorreos=true;
        this.aliasNombreDocumentosMap=new HashMap<ComprobanteEnum,String>();
    }

    public void procesar(Boolean enviarPorLotes) {
        this.enviarPorLotes=enviarPorLotes;
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void procesarSincronico(Boolean enviarPorLotes) {
        this.enviarPorLotes=enviarPorLotes;
        run();
    }
    

    public void procesarComprobante() {
        alertas.clear(); //Limpiar todoas las alertas anteriores para empezar el nuevo proceso
        try {
            if(escucha!=null)escucha.iniciado(comprobante);
            
            if (etapaActual.equals(ETAPA_GENERAR)){
                generar();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("generar()");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }

                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_PRE_VALIDAR)) {
                preValidacion();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("preValidacion()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_FIRMAR)) {
                firmar();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("firmar()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }
            
            if (etapaActual.equals(ETAPA_RIDE)) {
                if(enviarCorreoComprobanteAutorizado==false)//Valida si tengo que generar en esta etapa o despues de autorizar el ride
                {
                    generarRide(CARPETA_FIRMADOS);
                    if (escucha != null) {
                        escucha.procesando(etapaActual, new ClaveAcceso(claveAcceso));
                    }
                    //generarRide();
                    System.out.println("generarRide()");
                }
                
                if (etapaLimiteProcesar <= ETAPA_RIDE) {
                    if (escucha != null) {
                        escucha.termino();
                    }
                    return;
                }
                etapaActual++;
            }
            
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE))
            {
                if(enviarCorreoComprobanteAutorizado==false && enviarCorreos==true)
                {
                    enviarComprobante(CARPETA_FIRMADOS);
                    System.out.println("enviarCorreo() y SMS()");
                }

                if (escucha != null) {
                    escucha.procesando(etapaActual, new ClaveAcceso(claveAcceso));
                }
                if (etapaLimiteProcesar <= ETAPA_ENVIO_COMPROBANTE) {
                    if (escucha != null) {
                        escucha.termino();
                    }
                    return;
                }
                etapaActual++;
            }
            
            if (etapaActual.equals(ETAPA_ENVIAR)) {
                enviarSri();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("enviarSri()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_AUTORIZAR)) {
                //throw new ComprobanteElectronicoException("error prueba","autorizado", 1);
                Autorizacion autorizacion=autorizarSri();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                if(escucha!=null)escucha.autorizado(autorizacion); //Informo al servidor para enviar los datos de la autorizacion
                
                System.out.println("autorizarSri()");
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }
            
            /**
             * Nueva etapa cuando se utiliza el esquema online para enviar el comprobante del Ride Autorizado
             */
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE_AUTORIZADO)) 
            {
                if(enviarCorreoComprobanteAutorizado)
                {
                    generarRide(CARPETA_AUTORIZADOS); //Todo: Como el ride recien se genera en esta etapa aviso de esta forma
                    if (escucha != null) {
                        escucha.procesando(ETAPA_RIDE, new ClaveAcceso(claveAcceso));
                    }
                }
                
                if (enviarCorreoComprobanteAutorizado == true && enviarCorreos == true) 
                {                    
                    enviarComprobante(CARPETA_AUTORIZADOS);
                    System.out.println("enviarCorreoAutorizado() y SMS()");
                }

                if (escucha != null) {
                    escucha.procesando(etapaActual, new ClaveAcceso(claveAcceso));
                }
                if (etapaLimiteProcesar <= ETAPA_ENVIO_COMPROBANTE_AUTORIZADO) {
                    if (escucha != null) {
                        escucha.termino();
                    }
                    return;
                }
                etapaActual++;
            }


            if(escucha!=null)escucha.termino();
        } catch (ComprobanteElectronicoException cee) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE,cee.getMessage());
            if(escucha!=null)escucha.error(cee);
        }

    }
    
    private void procesarComprobanteLote()
    {
        try {
            
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService iniciando lote  ... ");
            if(escuchaLote!=null)escuchaLote.iniciado();            
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService posiniciando lote  ... ");
            
            if (etapaActual.equals(ETAPA_GENERAR)){
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_GENERAR  ... ");
                List<ClaveAcceso> listaClaves=generarLote();                
                
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService PRE escuchaLote.clavesGeneradas  ... ");
                if(escuchaLote!=null)escuchaLote.clavesGeneradas(listaClaves);
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService POST escuchaLote.clavesGeneradas  ... ");
                
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService PRE escuchaLote.procesando ... ");
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService POST escuchaLote.procesando ... ");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }

                etapaActual++;
            }
            
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_PRE_VALIDAR PRE ... ");

            if (etapaActual.equals(ETAPA_PRE_VALIDAR)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_PRE_VALIDAR  ... ");
                preValidacion();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("preValidacion lote()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }

            
            
            if (etapaActual.equals(ETAPA_FIRMAR)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_FIRMAR  ... ");
                claveAcceso = obtenerClaveAccesoLote();
                firmarLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("firmar lote()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }
            else//Si no tiene que firmar los datos entonces tiene que obtener segun las claves de acceso
            {
                claveAcceso = obtenerClaveAccesoLote();
                List<String> comprobantesFirmados = new ArrayList<String>();
                for (String claveAcceso : clavesAccesoLote) {
                    String path = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, claveAcceso);
                    String firmaStr;
                    try {
                        firmaStr = UtilidadesXml.convertirDocumentToString(path);
                    } catch (IOException ex) {                        
                        Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                        throw new ComprobanteElectronicoException(ex.getMessage(), "Error con el archivo", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                    }
                    //System.out.println(firmaStr);
                    firmaStr="<![CDATA["+firmaStr+"]]>";
                    comprobantesFirmados.add(firmaStr);
                }

                StringWriter stringWriter = generarXmlLote(comprobantesFirmados, ruc);
                ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_LOTE));
                
            }
            
            
            
            if (etapaActual.equals(ETAPA_RIDE)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_FIRMAR  ... ");
                if (enviarCorreoComprobanteAutorizado == false)
                {
                    generarRideLote(CARPETA_FIRMADOS);
                    if (escuchaLote != null) {
                        escuchaLote.procesando(etapaActual);
                    }
                    //generarRide();
                    System.out.println("generarRide()");
                }
                
                if (etapaLimiteProcesar <= ETAPA_RIDE) {
                    if (escuchaLote != null) {
                        escuchaLote.termino(servicioSri.getAutorizacion());
                    }
                    return;
                }
                etapaActual++;
            }
            
            
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_ENVIO_COMPROBANTE  ... ");
                if(enviarCorreoComprobanteAutorizado==false && enviarCorreos==true)
                {
                    enviarComprobanteLoteCorreo(CARPETA_FIRMADOS);
                    System.out.println("enviarCorreo()");
                }
                
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                    if(etapaLimiteProcesar<=ETAPA_ENVIO_COMPROBANTE) {
                        if(escuchaLote!=null)escuchaLote.termino(servicioSri.getAutorizacion());
                        return;
                    }
                etapaActual++;
            }
            
            
            
            List<Comprobante> comprobanteConProblemasEnviar=new ArrayList<Comprobante>(); //Nota: Esta variable la pongo fuera por si el usuario solo manda a verificar desde la etapa final
            if (etapaActual.equals(ETAPA_ENVIAR)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_ENVIAR  ... ");
                comprobanteConProblemasEnviar= enviarSriLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("enviarSri lote()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }

            
            if (etapaActual.equals(ETAPA_AUTORIZAR)) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_AUTORIZAR  ... ");
                autorizarSriLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                if(escuchaLote!=null)escuchaLote.datosAutorizados(listaLotesAutorizados(servicioSri.getAutorizacion(),comprobanteConProblemasEnviar));
                System.out.println("autorizarSri lote()");
                
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    if(escuchaLote!=null)escuchaLote.termino(listaLotesAutorizados(servicioSri.getAutorizacion(),comprobanteConProblemasEnviar));
                    return;
                }
                etapaActual++;
            }
            
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE_AUTORIZADO)) {

                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_ENVIO_COMPROBANTE_AUTORIZADO  ... ");
                if(enviarCorreoComprobanteAutorizado)
                {
                    generarRideLote(CARPETA_AUTORIZADOS);
                    if (escuchaLote != null) { //TODO: Aviso que estamos en la etapa lote generando el ride
                        escuchaLote.procesando(ETAPA_RIDE);
                    }
                }
                
                if (enviarCorreoComprobanteAutorizado == true && enviarCorreos == true) {
                    enviarComprobanteLoteCorreo(CARPETA_AUTORIZADOS);
                    //generarRide();                    
                    System.out.println("enviarCorreo()");
                }

                if (escuchaLote != null) {
                    escuchaLote.procesando(etapaActual);
                }
                if (etapaLimiteProcesar <= ETAPA_ENVIO_COMPROBANTE_AUTORIZADO) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.INFO,"ComprobanteElectronicoService ETAPA_ENVIO_COMPROBANTE_AUTORIZADO  ... ");
                    if (escuchaLote != null) {
                        escuchaLote.termino(servicioSri.getAutorizacion());
                    }
                    return;
                }
                etapaActual++;
            }
            

            if(escuchaLote!=null)escuchaLote.termino(listaLotesAutorizados(servicioSri.getAutorizacion(),comprobanteConProblemasEnviar));
        } catch (ComprobanteElectronicoException cee) {
            if(escuchaLote!=null)escuchaLote.error(cee);
        }
    
     }
    
    /**
     * Metodo que me permite agregar tambien los mensajes de los no enviados a la lista de autorizados para informar al cliente
     * @param autorizados
     * @param ComprobantesNoEnviados
     * @return 
     */
    private List<Autorizacion> listaLotesAutorizados(List<Autorizacion> autorizados,List<Comprobante> ComprobantesNoEnviados)
    {
        List<Autorizacion> autorizadosConNoEnviados=new ArrayList<Autorizacion>(autorizados);
        for (Comprobante comprobantesNoEnviado : ComprobantesNoEnviados) {
            Autorizacion autorizacion=new Autorizacion();
            autorizacion.setEstado("NO_ENVIADO");
            autorizacion.setNumeroAutorizacion(comprobantesNoEnviado.getClaveAcceso());
            //autorizacion.setMensajes(value);
            autorizadosConNoEnviados.add(autorizacion);
        }
        return autorizadosConNoEnviados;
    }

    /**
     * TODO: UNIR CON EL METODO DE ENVIO CORREO EN LOTE
     * @param carpetaComprobante
     * @throws ComprobanteElectronicoException 
     */
    private void enviarComprobante(String carpetaComprobante) throws ComprobanteElectronicoException {
        //enviarComprobanteCorreo(this.claveAcceso);
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            String pathComprobanteFirmado=getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, this.claveAcceso);
            File file=new File(pathComprobanteFirmado);

            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante)); //Si utilizo dos modos para enviar al correo de veria 
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            //archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobante(CARPETA_FIRMADOS));
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobante(carpetaComprobante));
            
            
            try {
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                
                List<String> correosElectronicosTemp=new ArrayList<String>();
                List<String> numerosSmsTemp=new ArrayList<String>();
                
                if(correosElectronicos!=null)
                {
                    //Agregado correos electronicos adicionales
                    correosElectronicosTemp.addAll(correosElectronicos);
                }
                
                if(comprobante.getInformacionAdicional()!=null)
                {
                    for (InformacionAdicional infoAdicional : comprobante.getInformacionAdicional()) {
                        
                        if(!enviarSoloCorreosAdjuntos)
                        {
                            if (infoAdicional.getNombre().indexOf("correo") >= 0) { //TODO: Setear a una variable global para poder modificar
                                correosElectronicosTemp.add(infoAdicional.getValor());
                            }
                        }
                        
                        //Buscar numeros de celular para enviar el correo
                        if (infoAdicional.getNombre().indexOf("celular") >= 0) { //TODO: Setear a una variable global para poder modificar
                            numerosSmsTemp.add(infoAdicional.getValor());
                        }
                    }
                }
                
                if(correosElectronicosTemp.size()>0)
                {                    
                    metodoEnvioInterface.enviarCorreo(mensajeGenerado, claveAcceso.getTipoComprobante().getNombre()+":" + comprobante.getInformacionTributaria().getPreimpreso(), correosElectronicosTemp, archivosPath);
                    metodoEnvioInterface.cerrarSesion();
                }
                
                /**
                 * ENVIAR SMS SI EL NUMERO ESTA EN LOS CAMPOS ADICIONALES
                 */
                if(numerosSmsTemp.size()>0)
                {   
                    String mensajeGeneradoSms =getMensajeSms(claveAcceso.getTipoComprobante(),comprobante);
                    metodoEnvioSmsInterface.enviarMensaje(numerosSmsTemp, mensajeGeneradoSms);
                    System.out.println("Enviado SMS a los numeros ingresados");
                }
                
                
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                //throw new ComprobanteElectronicoException("El comprobante se genero correctamente pero no se envio al cliente,\nRevise el correo y envie manualmente el RIDE \n\nProblema :\n "+ex.getMessage(), "Enviado correo", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
                alertas.add(new AlertaComprobanteElectronico("El comprobante se genero correctamente pero no se envio al cliente,\nRevise el correo y envie manualmente el RIDE \n\nProblema :\n "+ex.getMessage(),
                        ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE, 
                        AlertaComprobanteElectronico.TipoMensajeEnum.GRAVE));
            }

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            //throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
            alertas.add(new AlertaComprobanteElectronico("Error al enviar por correo el comprobante \n\nProblema :\n " + ex.getMessage(),
            ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE,
            AlertaComprobanteElectronico.TipoMensajeEnum.GRAVE));
                            
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            //throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
            alertas.add(new AlertaComprobanteElectronico("Error al enviar por correo el comprobante \n\nProblema :\n " + ex.getMessage(),
            ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE,
            AlertaComprobanteElectronico.TipoMensajeEnum.GRAVE));
        }

    }
    
    private void enviarComprobanteLoteCorreo(String carpetaEnviarComprobante)
    {
        for (String claveAccesoComprobante : clavesAccesoLote) {
             //Autorizacion autorizacion = servicioSri.buscarAutorizacion(claveAccesoComprobante);

             //Enviar toso los comprobantes que existen porque segun el SRI ya son validos
            //if (autorizacion != null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO)) {
                 try {
                enviarComprobanteCorreo(claveAccesoComprobante,carpetaEnviarComprobante);
            } catch (ComprobanteElectronicoException ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            //}
        }
        //Una vez que termina de enviar todos los correos termino la session que estaba abierta para los correos
        metodoEnvioInterface.cerrarSesion();
    }
    
    private void enviarComprobanteCorreo(String claveAccesoTemp,String carpetaEnviarComprobante) throws ComprobanteElectronicoException {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp), null, null);
            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            String pathComprobanteFirmado = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp);
            File file = new File(pathComprobanteFirmado);
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobanteConClaveAcceso(carpetaEnviarComprobante,claveAccesoTemp));
            
            try {
                
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                
                List<String> correosElectronicosTemp=comprobante.getCorreos();
                
                //Si la lista de correos adicionales  
                if(correosElectronicosTemp==null)correosElectronicosTemp=new ArrayList<String>();
                //Agregar correos adjuntos en el comprobante electronico
                if(comprobante.getInformacionAdicional()!=null)
                {
                    for (InformacionAdicional infoAdicional : comprobante.getInformacionAdicional()) {
                        if(infoAdicional.getNombre().equals("correo"))
                            correosElectronicosTemp.add(infoAdicional.getValor());                                           
                    }
                }
                
                //Si no existe ingresado correos electronicos entonces no se manda nada
                if(correosElectronicosTemp.size()==0)
                {
                    return ;
                }
                
                metodoEnvioInterface.enviarCorreo(mensajeGenerado, claveAcceso.getTipoComprobante().getNombre()+":" + comprobante.getInformacionTributaria().getPreimpreso(), correosElectronicosTemp, archivosPath);
                //metodoEnvioInterface.cerrarSesion();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                throw new ComprobanteElectronicoException("El comprobante se genero correctamente pero no se envio al cliente,\n Revise el correo y envie manualmente el RIDE", "Enviado correo", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        }

    }
    
    private String getMensajeSms(ComprobanteEnum clase,ComprobanteElectronico comprobante)
    {
        String mensajeGenerado = "Estimado/a , ";
        mensajeGenerado+=comprobante.getInformacionTributaria().getNombreComercial()+" "; //Nombre legal
        mensajeGenerado+="le informa que su ";
        if(clase.equals(ComprobanteEnum.FACTURA))
        {
            mensajeGenerado+=" Factura ";
        }
        else
        {
            if(clase.equals(ComprobanteEnum.NOTA_CREDITO))
            {
                mensajeGenerado+=" Nota Credito ";
            }
            else
            {
                mensajeGenerado+=" Comprobante ";
            }
        }
        
        mensajeGenerado+=" electronico ";
        mensajeGenerado+=comprobante.getInformacionTributaria().getSecuencial();
        mensajeGenerado+=" fue emitida correctamente";
        return mensajeGenerado;
        
    }
    
    private String getMensajeCorreo(ComprobanteEnum clase,ComprobanteElectronico comprobante)
    {
        String mensajeGenerado = "Estimado/a ";
        String nombreEmpresa="Codefac";
        
        if(comprobante.getInformacionTributaria().getNombreComercial()!=null && !comprobante.getInformacionTributaria().getNombreComercial().isEmpty())
        {
            nombreEmpresa=comprobante.getInformacionTributaria().getNombreComercial();
        }
        else
        {
            nombreEmpresa=comprobante.getInformacionTributaria().getRazonSocial();
        }
        
        if(clase.equals(ComprobanteEnum.FACTURA))
        {
            mensajeGenerado+= " "
                    + "<b>" + comprobante.getRazonSocialComprador() + "</b> ,<br><br>"
                    + "<b>" + nombreEmpresa + "</b>"
                    + " le informa que su factura  electrónica " + comprobante.getInformacionTributaria().getPreimpreso() + " se generó correctamente. <br><br>";
            mensajeGenerado = "<p>" + mensajeGenerado + "</p>" + footerMensajeCorreo;
        }
        else
        {
            if(clase.equals(ComprobanteEnum.NOTA_CREDITO))
            {
                mensajeGenerado += " "
                        + "<b>" + comprobante.getRazonSocialComprador() + "</b> ,<br><br>"
                        + "<b>" + nombreEmpresa + "</b>"
                        + " le informa que su nota de crédito " + comprobante.getInformacionTributaria().getPreimpreso() + " se generó correctamente. <br><br>";
                mensajeGenerado = "<p>" + mensajeGenerado + "</p>" + footerMensajeCorreo;
                
            }
            else
            {
                //Mensaje generico cuando no es ningun de los comprobantes registtados
                mensajeGenerado=" Puede revisar el comprobante electronico como archivo adjunto";
            }
        }
        return mensajeGenerado;
        
    }

    private void generarRide(String carpetaOrigenXml) throws ComprobanteElectronicoException {
        generarRideIndividual(this.claveAcceso,carpetaOrigenXml);

    }
    
    private void generarRideLote(String carpetaOrigenXml) throws ComprobanteElectronicoException {
        for (String claveAccesoComprobante : clavesAccesoLote) {
            generarRideIndividual(claveAccesoComprobante,carpetaOrigenXml);
        }
    }
    
    public void generarRideManual()
    {
        comprobante.getInformacionTributaria().setClaveAcceso(comprobante.getInformacionTributaria().getPreimpreso());
        generarRideIndividual(comprobante, null,null);
    }
    
    public  void generarRideIndividual( ComprobanteElectronico comprobante,String carpetaOrigenXml,ClaveAcceso claveAcceso)
    {
        //ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            ComprobanteElectronicoReporte reporte =getComprobanteReporte(comprobante);
            
            String fechaHoraAutorizacion="";
            String estado="";
            
            //Si la carpeta que se quiere obtene es desde la autorizada consulto los otros datos que faltan
            if(carpetaOrigenXml!=null && carpetaOrigenXml.equals(CARPETA_AUTORIZADOS))
            {
                ComprobanteElectronicoAutorizado comprobanteAutorizado=new ComprobanteElectronicoAutorizado();
                comprobanteAutorizado.construirDesdeArchivo(getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS, claveAcceso.clave));
                fechaHoraAutorizacion=comprobanteAutorizado.getFechaAutorizacion();
                estado=comprobanteAutorizado.getEstado();
            }
            
            List<Object> informacionAdiciona = reporte.getDetalles();
            Map<String, Object> datosMap = reporte.getMapReporte(aliasNombreDocumentosMap);
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_INFO_OTRO", reporteInfoOtroAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("fecha_hora_autorizacion",fechaHoraAutorizacion);
            datosMap.put("estado",estado);
            
            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            //datosMap.putAll(mapAdicionalReporte);
            datosMap.putAll(getMapCopyAdicionalReporte());

            datosMap.put("imagen_logo",pathLogoImagen);
            
            UtilidadesComprobantes.generarReporteJasper(getPathJasper(comprobante), datosMap, informacionAdiciona, getPathComprobante(CARPETA_RIDE, getNameRide(comprobante)));            
    }
    
    //TODO: Tratar de unificar con el metodo generarRideIndividual por que son muy similares
    public static ComprobanteElectronico obtenerComprobanteDataDesdeXml(File archivoXml)
    {
        try {
            
            //VERIFICAR si existe un XML AUTORIZADO primero obtengo el formato normal del XML FIRMADO
            String xmlStr=new String(Files.readAllBytes(archivoXml.toPath()),StandardCharsets.UTF_8);
            
            final String etiquetaAperturaFirmado="[CDATA[";
            final String etiquetaCierreFirmado="]]>";
            if(xmlStr.indexOf(etiquetaAperturaFirmado)>=0)
            {
                int etiquetaPosicionInicialFirmado=xmlStr.indexOf(etiquetaAperturaFirmado)+etiquetaAperturaFirmado.length();
                int etiquetaPosicionFinalFirmado=xmlStr.indexOf(etiquetaCierreFirmado);
                xmlStr=xmlStr.substring(etiquetaPosicionInicialFirmado,etiquetaPosicionFinalFirmado);                
            }
            //Obtener la CLAVE DE ACCESO directamente desde el ARCHIVO XML            
            int etiquetaPosicionInicial=xmlStr.indexOf("<claveAcceso>");
            int etiquetaPosicionFinal=xmlStr.indexOf("</claveAcceso>");
            
            etiquetaPosicionInicial=etiquetaPosicionInicial+13;//Me posiciono en la parte final de la etiqueta para poder cortar
            String claveAccesoStr=xmlStr.substring(etiquetaPosicionInicial, etiquetaPosicionFinal);
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoStr);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(archivoXml);
            Reader reader=new StringReader(xmlStr);
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
            
            return comprobante;
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void generarRideIndividual(String claveAccesoTemp,String carpetaOrigenXml) throws ComprobanteElectronicoException
    {
        try {            
            
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            
            String pathComprobanteFirmado=getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, claveAccesoTemp);
            File file=new File(pathComprobanteFirmado);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);
            generarRideIndividual(comprobante, carpetaOrigenXml, claveAcceso);
                        
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
    }
    

    private Map<String, Object> getMapCopyAdicionalReporte()
    {
        Map<String,Object> mapCopy=new HashMap<String,Object>();
        for (Map.Entry<String, Object> entry : mapAdicionalReporte.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            //Si tiene un valor null no lo tomo en cuenta y continuo con el siguiente
            if(value==null)
            {
                continue;
            }
            
            if(value.getClass().equals(URL.class))
            {
                try {
                    URL url=(URL) value;
                    mapCopy.put(key,url.openStream());
                } catch (IOException ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                mapCopy.put(key, value);
            }
        }
        return mapCopy;
    }
    
    private ComprobanteElectronicoReporte getComprobanteReporte(ComprobanteElectronico comprobante)
    {
        //TODO: Por el momento utilizo el mismo reporte para factura y liquidacion de compras porque son iguales
        if(comprobante.getClass().equals(FacturaComprobante.class))
        {
            FacturaElectronicaReporte factElectReport=new FacturaElectronicaReporte((FacturaComprobante) comprobante);
            factElectReport.setMapCodeAndNameFormaPago(mapCodeAndNameFormaPago);
            return factElectReport;
        }
        else
            if(comprobante.getClass().equals(NotaCreditoComprobante.class))
            {
                return new NotaCreditoReporte(comprobante);
            }
            else
            {
                if(comprobante.getClass().equals(RetencionComprobante.class))
                {
                    RetencionElectronicaReporte retencionReporte=new RetencionElectronicaReporte(comprobante);
                    retencionReporte.setMapCodeAndNameTipoDocumento(mapCodeAndNameTipoDocumento);
                    retencionReporte.setMapCodeAndNameTipoRetecion(mapCodeAndNameTipoRetecion);
                    return retencionReporte;
                }
                else
                {
                    if(comprobante.getClass().equals(GuiaRemisionComprobante.class))
                    {
                        GuiaRemisionReporte guiaRemisionReporte=new GuiaRemisionReporte(comprobante,jasperSubReporteGuiaRemision);
                        return guiaRemisionReporte;
                    }
                    else
                    {
                        if(comprobante.getClass().equals(LiquidacionCompraComprobante.class))
                        {
                            LiquidacionCompraReporte liquidacionCompraReporte = new LiquidacionCompraReporte((LiquidacionCompraComprobante) comprobante);
                            liquidacionCompraReporte.setMapCodeAndNameFormaPago(mapCodeAndNameFormaPago);
                            return liquidacionCompraReporte;
                        }
                        else
                        {                    
                            System.out.println("no esta comparando clases");
                            return null;
                        }
                    }
                    
                }
            }
    }

    private void preValidacion() {

    }
    
    public JasperPrint getPrintJasperComprobante(ComprobanteElectronico comprobante,ClaveAcceso claveAcceso)
    {
        String clave=(claveAcceso!=null)?claveAcceso.clave:null;
        return getPrintJasperComprobante(comprobante, clave,null);
    }   
    
    public JasperPrint getPrintJasperComprobante(ComprobanteElectronico comprobante,String claveAcceso,String fechaAutorizacion)
    {
            //ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);
            ComprobanteElectronicoReporte reporte = getComprobanteReporte(comprobante);

            List<Object> informacionAdicional = reporte.getDetalles();
            
            //InputStream reporteInfoAdicional = this.reporteInfoAdicional.openStream();
            //InputStream reporteFormaPago = this.reporteFormaPago.openStream();
            //InputStream pathLogoImagen = this.pathLogoImagen.openStream();
            
            
            String fechaHoraAutorizacion="";
            //String estado="";
            //Si la carpeta que se quiere obtene es desde la autorizada consulto los otros datos que faltan
            //if(carpetaOrigenXml.equals(CARPETA_AUTORIZADOS))
            //{
            //Intenta verificar si existe el dato de la fecha y hora de autorizacion
            if(claveAcceso!=null)
            {
                //TODO: Verificar que esta parte no genere error cuando la fecha es null y no existe el archivo para leer
                if(fechaAutorizacion==null)
                {
                    ComprobanteElectronicoAutorizado comprobanteAutorizado=new ComprobanteElectronicoAutorizado();
                    comprobanteAutorizado.construirDesdeArchivo(getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS, claveAcceso));
                    fechaHoraAutorizacion=comprobanteAutorizado.getFechaAutorizacion();
                }
                else
                {
                    fechaHoraAutorizacion=fechaAutorizacion;
                }
            }
                //estado=comprobanteAutorizado.getEstado();
            //}

            Map<String, Object> datosMap = reporte.getMapReporte(aliasNombreDocumentosMap);
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("fecha_hora_autorizacion",fechaHoraAutorizacion);
            datosMap.put("estado","");
            
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_INFO_OTRO", reporteInfoOtroAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("imagen_logo", pathLogoImagen);

            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            //datosMap.putAll(mapAdicionalReporte);
            datosMap.putAll(getMapCopyAdicionalReporte());

            //datosMap.put("imagen_logo",is);
            //datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));
            //datosMap.put("imagen_logo",pathLogoImagen.openStream());

            return UtilidadesComprobantes.generarReporteJasperPrint(getPathJasper(comprobante), datosMap, informacionAdicional);
    }

    /**
     * TODO: Unir este codigo con el de generarRideIndividual que son similares
     * @return 
     */
    public JasperPrint getPrintJasper() {
        try {
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            String pathComprobanteFirmado = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, this.claveAcceso);
            File file = new File(pathComprobanteFirmado);
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);
            return getPrintJasperComprobante(comprobante, claveAcceso);
            /*ComprobanteElectronicoReporte reporte = getComprobanteReporte(comprobante);

            List<Object> informacionAdicional = reporte.getDetalles();
            
            //InputStream reporteInfoAdicional = this.reporteInfoAdicional.openStream();
            //InputStream reporteFormaPago = this.reporteFormaPago.openStream();
            //InputStream pathLogoImagen = this.pathLogoImagen.openStream();
            
            
            String fechaHoraAutorizacion="";
            String estado="";
            //Si la carpeta que se quiere obtene es desde la autorizada consulto los otros datos que faltan
            //if(carpetaOrigenXml.equals(CARPETA_AUTORIZADOS))
            //{
            //Intenta verificar si existe el dato de la fecha y hora de autorizacion
                ComprobanteElectronicoAutorizado comprobanteAutorizado=new ComprobanteElectronicoAutorizado();
                comprobanteAutorizado.construirDesdeArchivo(getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS, claveAcceso.clave));
                fechaHoraAutorizacion=comprobanteAutorizado.getFechaAutorizacion();
                estado=comprobanteAutorizado.getEstado();
            //}

            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("fecha_hora_autorizacion",fechaHoraAutorizacion);
            datosMap.put("estado","");
            
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_INFO_OTRO", reporteInfoOtroAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("imagen_logo", pathLogoImagen);

            //
            // Agregar datos adicionales como por ejemplo los datos del pide de
            // pagina
            //
            //datosMap.putAll(mapAdicionalReporte);
            datosMap.putAll(getMapCopyAdicionalReporte());

            //datosMap.put("imagen_logo",is);
            //datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));
            //datosMap.put("imagen_logo",pathLogoImagen.openStream());

            return UtilidadesComprobantes.generarReporteJasperPrint(getPathJasper(comprobante), datosMap, informacionAdicional);*/

        } catch (JAXBException ex) {
            ex.printStackTrace();
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void generar() throws ComprobanteElectronicoException {
        try {
            claveAcceso = obtenerClaveAcceso();
            comprobante.getInformacionTributaria().setClaveAcceso(claveAcceso);
            StringWriter stringWriter = generarXml(comprobante,claveAcceso);
            ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_GENERADOS));
            System.out.println("generando "+claveAcceso);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    /**
     * Genera todos los comprobantes y devuelve un map con las claves de acceso
     * @throws ComprobanteElectronicoException 
     */
    
    private List<ClaveAcceso> generarLote() throws ComprobanteElectronicoException{
         
        List<ClaveAcceso> listaClaves=new ArrayList<ClaveAcceso>();                    
        try {
                    
            //claveAcceso = obtenerClaveAccesoLote();            

            //Generar los xml individuales de los comprobantes para procesar en lote
            clavesAccesoLote=new ArrayList<String>();
            for (ComprobanteElectronico comprobante : comprobantesLote) {
                String claveAccesoComprobante=obtenerClaveAcceso(comprobante);
                listaClaves.add(new ClaveAcceso(claveAccesoComprobante));
                        
                comprobante.getInformacionTributaria().setClaveAcceso(claveAccesoComprobante);
                clavesAccesoLote.add(claveAccesoComprobante);
                //ruc=comprobante.getInformacionTributaria().getRuc();
                StringWriter stringWriter = generarXml(comprobante,claveAccesoComprobante);
                ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoComprobante));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
        return listaClaves;
  
    }

    private void firmar() throws ComprobanteElectronicoException {
        /**
         * Firmando el documento
         */
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        Document documentoFirmado = firmaElectronica.firmar(getPathComprobante(CARPETA_GENERADOS));
        if (documentoFirmado != null) {
            try {
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS_SIN_ENVIAR));
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS));
                ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_GENERADOS));
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private void obtenerFirmasPorLote()
    {
        for (String claveAccesoComprobante : clavesAccesoLote) {
            
        }
    }
    
    
    private void firmarLote() throws ComprobanteElectronicoException {
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        
        //String ruc="";
        List<String> comprobantesFirmados=new ArrayList<String>();
        
        for (String claveAccesoComprobante : clavesAccesoLote) {
            //ruc=comprobanteElectronico.getInformacionTributaria().getRuc();
            String claveAccesoTemp=claveAccesoComprobante;
            Document documentoFirmado = firmaElectronica.firmar(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));
            
            if (documentoFirmado != null) {
                try {
                    ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS_SIN_ENVIAR,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));

                    String loteComprobanteData=("<![CDATA["+UtilidadesTextos.documentToString(documentoFirmado)+"]]>");
                    
                    comprobantesFirmados.add(loteComprobanteData);
                } catch (Exception ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        StringWriter stringWriter = generarXmlLote(comprobantesFirmados, ruc);
        
        /**
         * ====================================================================
         * Verificar que el tamaño no exeda el permitido por el Sri
         * ====================================================================
         */
        File archivoLoteGenerado=ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_LOTE));
        Long tamanioArchivo= UtilidadesArchivos.obtenerTamanioArchivoEnKb(archivoLoteGenerado);
        if(tamanioArchivo>TAMANIO_MAX_LOTE_KB)
        {
            throw new ComprobanteElectronicoException("No se puede enviar un archivo superior a "+TAMANIO_MAX_LOTE_KB+"kb", "Error proceso Lote", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }        
       
    }
    
    /**
     * Agregado intentos porque muchas veces el Sri esta funcionando de manera iregular porque va y viene la conexion
     * @author Carlos 
     * @param intentos
     * @return 
     */
    public boolean disponibilidadServidorSri(Integer intentos)
    {
        servicioSri = new ServicioSri();
        servicioSri.setUri_autorizacion(uriAutorizacion);
        servicioSri.setUri_recepcion(uriRecepcion);
        try {
            for (int i = 0; i < intentos; i++) {
                if (!servicioSri.verificarConexionRecepcion() || !servicioSri.verificarConexionAutorizar())
                {
                    return false;
                }
            }
            
        } catch (ComprobanteElectronicoException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private void enviarSri() throws ComprobanteElectronicoException {
        try {
            servicioSri = new ServicioSri();
            servicioSri.setUri_autorizacion(uriAutorizacion);
            servicioSri.setUri_recepcion(uriRecepcion);
            servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));

            if (servicioSri.verificarConexionRecepcion()) {
                System.out.println("Existe conexion");
                if (servicioSri.enviar()) {
                    
                    System.out.println("Documento enviados");
                    
                    //Copiar el archivo al siguiente nivel firmado a los enviados
                    ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS_SIN_RESPUESTA));
                    //Elimina la carpeta de los firmados para saber que ya fue enviado
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS_SIN_ENVIAR));
                    
                } else {
                    String mensajeError = "";
                    for (Mensaje mensaje : servicioSri.getMensajes()) {
                        System.out.println(mensaje.getIdentificador() + "-" + mensaje.getMensaje() + "-" + mensaje.getInformacionAdicional());
                        mensajeError += mensaje.getMensaje() + "\n" + mensaje.getInformacionAdicional();
                    }
                    throw new ComprobanteElectronicoException(mensajeError, "Enviar comprobante", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
            }
        } catch (ComprobanteElectronicoException cee) {
            cee.printStackTrace();
            throw new ComprobanteElectronicoException(cee);
        }
    }
    
    private Comprobante buscarComprobantesPorClaveAcceso(List<Comprobante> comprobantesConProblemas, String claveAcceso)
    {
        for (Comprobante comprobantesConProblema : comprobantesConProblemas) {
            if(comprobantesConProblema.getClaveAcceso().equals(claveAcceso))
            {
                return comprobantesConProblema;
            }
        }
        return null;
    }
    
    private  List<Comprobante> enviarSriLote() throws ComprobanteElectronicoException {
        try {
            servicioSri = new ServicioSri();
            servicioSri.setUri_autorizacion(uriAutorizacion);
            servicioSri.setUri_recepcion(uriRecepcion);
            servicioSri.setUrlFile(getPathComprobante(CARPETA_LOTE));

            if (servicioSri.verificarConexionRecepcion()) {
                System.out.println("Existe conexion");
                servicioSri.enviarLote();
                List<Comprobante> comprobantesConProblemas=servicioSri.getComprobantesNoRecibidos();
                
                //Si la cantidad de comprobantes con problemas es igual al total procesado genero un error
                if(comprobantesConProblemas.size()==clavesAccesoLote.size())
                {
                    List<String> mensajes=new ArrayList<String>();
                    for (Comprobante comprobantesConProblema : comprobantesConProblemas) {                        
                        for (Mensaje mensaje : comprobantesConProblema.getMensajes().getMensaje()) 
                        {
                            mensajes.add(UtilidadesComprobantes.castMensajeToString(mensaje));
                        }
                    }
                    throw new ComprobanteElectronicoException(UtilidadesLista.castListToString(mensajes,"\n"), "Mensaje enviando Lote", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
                
                //if (servicioSri.enviar()) {
                    System.out.println("Documento enviados");
                    for (String claveAccesoComprobante : clavesAccesoLote) {
                        
                        Comprobante comprobanteEncontrado=buscarComprobantesPorClaveAcceso(comprobantesConProblemas,claveAccesoComprobante);
                        if(comprobanteEncontrado==null) //Si no encuentra en la lista significa porque no tiene problemas
                        {
                            //Mover todos los archivos individuales
                            String claveAccesoTemp = claveAccesoComprobante;
                            ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, claveAccesoTemp), getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS_SIN_RESPUESTA, claveAccesoTemp));
                            //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS_SIN_ENVIAR, claveAccesoTemp));
                        }                        
                        
                    }                    
                    
                    //Mover el archivo que contiene todos los archivos en lote a la carpeta de enviados
                    //ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS));
                    //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS));
                    
                //} else {
                    //String mensajeError = "";
                    //for (Mensaje mensaje : servicioSri.getMensajes()) {
                    //    System.out.println(mensaje.getIdentificador() + "-" + mensaje.getMensaje() + "-" + mensaje.getInformacionAdicional());
                    //    mensajeError += mensaje.getMensaje() + "\n" + mensaje.getInformacionAdicional();
                    //}
                    //throw new ComprobanteElectronicoException(mensajeError, "Enviar comprobante", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                //}
                return comprobantesConProblemas;
            }
        } catch (ComprobanteElectronicoException cee) {
            cee.printStackTrace();
            throw new ComprobanteElectronicoException(cee);
        }
        return new ArrayList<Comprobante>();
    }
    

    private Autorizacion autorizarSri() throws ComprobanteElectronicoException {
        servicioSri = new ServicioSri();
        servicioSri.setUri_autorizacion(uriAutorizacion);
        servicioSri.setUri_recepcion(uriRecepcion);
        servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));
        /**
         * Recogiendo autorizacion SRI
         */
        if (servicioSri.autorizar(claveAcceso)) {
            String xmlAutorizado = servicioSri.obtenerRespuestaAutorizacion();
            ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobante(CARPETA_AUTORIZADOS));
            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_ENVIADOS_SIN_RESPUESTA));
            return servicioSri.getAutorizacion().get(0); //Obtengo solo el primer valor porque solo esta mandando a autorizar un documento
        }
        return null;

    }
    
    private void autorizarSriLote() throws ComprobanteElectronicoException {
        servicioSri = new ServicioSri();
        servicioSri.setUri_autorizacion(uriAutorizacion);
        servicioSri.setUri_recepcion(uriRecepcion);
        servicioSri.setUrlFile(getPathComprobante(CARPETA_LOTE));
        
        /**
         * Recogiendo autorizacion SRI
         */
        if (servicioSri.autorizarLote(claveAcceso)) {
            
            for (String claveAccesoComprobante : clavesAccesoLote) {
                //Generar los archivos de cada comprobante autorizado
                Autorizacion autorizacion=servicioSri.buscarAutorizacion(claveAccesoComprobante);
                
                if(autorizacion!=null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO))
                {
                    String claveAccesoTemp=autorizacion.getNumeroAutorizacion();
                    String xmlAutorizado = servicioSri.castAutorizacionToString(autorizacion);
                    ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS_SIN_RESPUESTA,claveAccesoTemp));
                }
                
            }
            
            //Si el archivo se autoriza correctamente se elimina el archivo del lote
            //ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_ENVIADOS), getPathComprobante(CARPETA_FIRMADOS));
            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_LOTE));

            //String xmlAutorizado = servicioSri.obtenerRespuestaAutorizacion();
            //ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAcceso));
            //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAcceso));
        }

    }

    private KeyStore obtenerAlmacenFirma(String rutaAlmacenCertificado, String passwordAlmacenCertificado) {
        try {
            KeyStore clave = null;
            clave = KeyStore.getInstance("PKCS12");
            FileInputStream file = new FileInputStream(rutaAlmacenCertificado);
            clave.load(new FileInputStream(rutaAlmacenCertificado),
                    passwordAlmacenCertificado.toCharArray());
            return clave;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String obtenerClaveAccesoLote()
    {
        Vector<String> claveAcceso = new Vector<>();
        
        //Fecha cuando se va a enviar los archivos por lote
        SimpleDateFormat formateador = new SimpleDateFormat("ddMMyyyy");
        String fechaFormat = formateador.format(new Date());
        claveAcceso.add(fechaFormat);
        
        //Tipo de comprobante (por defecto le dejo 01 que corresponde a factura)
        claveAcceso.add("01"); //Todo: Verificar que se pone en tipo de comprobante porque creo que se pueden enviar archivos de cualquier tipo
        
        //Identificacion de ruc mia supongo
        claveAcceso.add(ruc); 
        
        //Tipo de ambiente de la facturacion
        claveAcceso.add(getTipoCodigoAmbiente()); //por defecto en pruebas
        
        //Numero de serie , asumo que es un valor que se genera automaticamente 
        claveAcceso.add("001001");
        
        //Numero de secuencial que supongo que es el secuencial inicial desde el que sea va a empezar a facturar
        claveAcceso.add(UtilidadesTextos.llenarCarateresIzquierda(secuencialLote+"",9,"0"));
        
        
        //Código Numérico que por defecto mando este valor
        claveAcceso.add("00000000");
        
        //Tipo de emision
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        
        //Digito verificador
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);
        
        
        return UtilidadesTextos.castVectorToString(claveAcceso);
    }

    public String obtenerClaveAcceso() 
    {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        //Tipo de documento a enviar ejemplo:factura
        claveAcceso.add(comprobante.getTipoDocumento()); 

        //identificacion de el ruc de la empresa
        claveAcceso.add(comprobante.getIdentificacion());

        //Tipo del ambiente que esta configurado pruebas o produccion
        claveAcceso.add(getTipoCodigoAmbiente());

        /**
         * Establecimiento y punto de emision
         */
        String establecimiento= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getEstablecimiento(),3,"0");
        String puntoEmision= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getPuntoEmision(),3,"0");
        claveAcceso.add(establecimiento+puntoEmision);

        /**
         * Secuendial del comprobante
         */
        String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * lote y por defecto se pone con 0
         */
        claveAcceso.add("00000000");

        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo
         * 1 que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);

        /**
         * Digito verificador
         */
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);

        return UtilidadesTextos.castVectorToString(claveAcceso);
    }
    
    public String obtenerClaveAcceso(ComprobanteElectronico comprobante) 
    {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        claveAcceso.add(getTipoComprobante(comprobante));

        //String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),12, "0");
        claveAcceso.add(comprobante.getIdentificacion());

        claveAcceso.add(getTipoCodigoAmbiente());

        /**
         * Establecimiento y punto de emision
         */
        String establecimiento= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getEstablecimiento(),3,"0");
        String puntoEmision= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getPuntoEmision(),3,"0");
        claveAcceso.add(establecimiento+puntoEmision);

        /**
         * Secuendial del comprobante
         */
        String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * lote y por defecto se pone con 0
         */
        claveAcceso.add("00000000");

        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo
         * 1 que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);

        /**
         * Digito verificador
         */
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);

        return UtilidadesTextos.castVectorToString(claveAcceso);
    }

    private String getTipoCodigoAmbiente() {
        switch (this.modoFacturacion) {
            case ComprobanteElectronicoService.MODO_PRODUCCION:
                return "2";

            case ComprobanteElectronicoService.MODO_PRUEBAS:
                return "1";

            default:
                return "00";
        }
    }

    private String getTipoComprobante() {
        System.out.println(comprobante.getTipoDocumento());
        switch (comprobante.getTipoDocumento()) {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";
                
            case ComprobanteElectronico.RETENCION:
                return ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo();

            default:
                return "00";
        }
    }
    
    //TODO: Revisar si usa este metodo y unificar con el de enum
    private String getTipoComprobante(ComprobanteElectronico comprobante) {
        
        String tipoDocumento=comprobante.getTipoDocumento();
        
        /*switch (tipoDocumento) 
        {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";
                
            case ComprobanteElectronico.RETENCION:
                return "07";
                
            case ComprobanteElectronico.GUIA_REMISION:
                return "06";                

            default:
                return "00";
        }*/
        return tipoDocumento;
    }

    private StringWriter generarXml(ComprobanteElectronico comprobante,String claveAccesoStr) {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoStr);
            JAXBContext contexto = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "libro.xsd");
            StringWriter sw = new StringWriter();
            //marshaller.marshal(libro, System.out);
            marshaller.marshal(comprobante, sw);
            return sw;
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private StringWriter generarXmlLote(List<String> comprobantesFirmados,String ruc) {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext contexto = JAXBContext.newInstance(LoteComprobante.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
          
             LoteComprobante loteComprobante=new LoteComprobante();
             loteComprobante.setRuc(ruc);
             loteComprobante.setClaveAcceso(this.claveAcceso);
             loteComprobante.setComprobantes(comprobantesFirmados);
             
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());
            
            
            marshaller.marshal(loteComprobante, dataWriter);
            return sw;
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getPathComprobante(String carpeta) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + claveAcceso + ".xml";
    }
    
    public String getPathComprobanteConClaveAcceso(String carpeta,String claveAcceso) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + claveAcceso + ".xml";
    }

    private String getPathComprobante(String carpeta, String archivo) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + archivo;
    }

    private String getPathFirma() {
        return pathBase + "/" + CARPETA_CONFIGURACION + "/" + nombreFirma;
    }

    private String getNameRide(ComprobanteElectronico comprobante) {
        String prefijo = "";
        if (ComprobanteEnum.FACTURA.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
            prefijo = ComprobanteEnum.FACTURA.getPrefijo();
        }
        else
        {
            if (ComprobanteEnum.NOTA_CREDITO.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
                prefijo = ComprobanteEnum.NOTA_CREDITO.getPrefijo();
            }
            else
            {
                if(ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento()))
                {
                    prefijo=ComprobanteEnum.COMPROBANTE_RETENCION.getPrefijo();
                }
                else
                {
                    if(ComprobanteEnum.GUIA_REMISION.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento()))
                    {
                        prefijo=ComprobanteEnum.GUIA_REMISION.getPrefijo();
                    }
                    else
                    {
                        if(ComprobanteEnum.NOTA_VENTA_INTERNA.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
                            prefijo=ComprobanteEnum.NOTA_VENTA_INTERNA.getPrefijo();
                        }
                        else
                        {
                            if(ComprobanteEnum.GUIA_REMISION_INTERNA.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) 
                            {
                                prefijo=ComprobanteEnum.GUIA_REMISION_INTERNA.getPrefijo();
                            }
                            else
                            {
                                //TODO: Falta implentar para el ultimo documento que son las notas de debito si algun rato alguien nos pide
                            }
                            
                        }
                    }
                }
            }
        }
        comprobante.getTipoDocumento();
        return prefijo + "-" + comprobante.getInformacionTributaria().getPreimpreso() +"_"+comprobante.getInformacionTributaria().getClaveAcceso()+ ".pdf";
    }

    public void setUriRecepcion(String uriRecepcion) {
        this.uriRecepcion = uriRecepcion;
    }

    public void setUriAutorizacion(String uriAutorizacion) {
        this.uriAutorizacion = uriAutorizacion;
    }

    public String getModoFacturacion() {
        return modoFacturacion;
    }

    public void setModoFacturacion(String modoFacturacion) {
        this.modoFacturacion = modoFacturacion;
    }

    public URL getPathFacturaJasper() {
        return pathFacturaJasper;
    }

    public void setPathFacturaJasper(URL pathFacturaJasper) {
        this.pathFacturaJasper = pathFacturaJasper;
    }

    public String getPathParentJasper() {
        return pathParentJasper;
    }

    public void setPathParentJasper(String pathParentJasper) {
        this.pathParentJasper = pathParentJasper;
    }

    
    public Map<String, Object> getMapAdicionalReporte() {
        return mapAdicionalReporte;
    }

    public void setMapAdicionalReporte(Map<String, Object> mapAdicionalReporte) {
        this.mapAdicionalReporte = mapAdicionalReporte;
    }

    public void addActionListerComprobanteElectronico(ListenerComprobanteElectronico e) {
        this.escucha = e;
    }
    
    public void addActionListerComprobanteElectronicoLote(ListenerComprobanteElectronicoLote e) {
        this.escuchaLote = e;
    }
    

    public Integer getEtapaActual() {
        return etapaActual;
    }

    public String getPathBase() {
        return pathBase;
    }

    public String getNombreFirma() {
        return nombreFirma;
    }

    public String getClaveFirma() {
        return claveFirma;
    }

    public ComprobanteElectronico getComprobante() {
        return comprobante;
    }

    public void setEtapaActual(Integer etapaActual) {
        this.etapaActual = etapaActual;
    }

    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }

    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    public void setClaveFirma(String claveFirma) {
        this.claveFirma = claveFirma;
    }

    public void setComprobante(ComprobanteElectronico comprobante) {
        this.comprobante = comprobante;
    }

    public MetodosEnvioInterface getMetodoEnvioInterface() {
        return metodoEnvioInterface;
    }

    public void setMetodoEnvioInterface(MetodosEnvioInterface metodoEnvioInterface) {
        this.metodoEnvioInterface = metodoEnvioInterface;
    }

    public void setCorreosElectronicos(List<String> correosElectronicos) {
        this.correosElectronicos = correosElectronicos;
    }

    public String getFooterMensajeCorreo() {
        return footerMensajeCorreo;
    }

    public void setFooterMensajeCorreo(String footerMensajeCorreo) {
        this.footerMensajeCorreo = footerMensajeCorreo;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Integer getEtapaLimiteProcesar() {
        return etapaLimiteProcesar;
    }

    public void setEtapaLimiteProcesar(Integer etapaLimiteProcesar) {
        this.etapaLimiteProcesar = etapaLimiteProcesar;
    }

    public URL getPathNotaCreditoJasper() {
        return pathNotaCreditoJasper;
    }

    public void setPathNotaCreditoJasper(URL pathNotaCreditoJasper) {
        this.pathNotaCreditoJasper = pathNotaCreditoJasper;
    }

    public URL getPathRetencionJasper() {
        return pathRetencionJasper;
    }

    public void setPathRetencionJasper(URL pathRetencionJasper) {
        this.pathRetencionJasper = pathRetencionJasper;
    }
    
    

    public void setMapCodeAndNameFormaPago(Map<String, String> mapCodeAndNameFormaPago) {
        this.mapCodeAndNameFormaPago = mapCodeAndNameFormaPago;
    }

    public void setReporteInfoAdicional(JasperReport reporteInfoAdicional) {
        this.reporteInfoAdicional = reporteInfoAdicional;
    }

    public void setReporteFormaPago(JasperReport reporteFormaPago) {
        this.reporteFormaPago = reporteFormaPago;
    }

    public List<ComprobanteElectronico> getComprobantesLote() {
        return comprobantesLote;
    }

    public void setComprobantesLote(List<ComprobanteElectronico> comprobantesLote) {
        this.comprobantesLote = comprobantesLote;
    }
    
    private ComprobanteElectronico buscarComprobanteLote(String clave)
    {
        if(comprobantesLote!=null)
        {
            for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
                if(comprobanteElectronico.getInformacionTributaria().getClaveAcceso().equals(clave))
                {
                    return comprobanteElectronico;
                }
            }
        }
        return null;
    }

    public URL getPathJasper(ComprobanteElectronico comprobanteElectronico)
    {
        URL path = null;
        
        //Primero intenta obtener los datos del comprobante en memoria y si no encuentra busca con la clave de acceso
        String tipoComprobanteCodigo="";
        if(comprobanteElectronico!=null && comprobanteElectronico.getInformacionTributaria()!=null)
        {
            tipoComprobanteCodigo=comprobanteElectronico.getInformacionTributaria().getCodigoDocumento();
        }
        else
        {
            ClaveAcceso clave = new ClaveAcceso(claveAcceso);
            tipoComprobanteCodigo=clave.tipoComprobante;
        }
        
        if (ComprobanteEnum.FACTURA.getCodigo().equals(tipoComprobanteCodigo)) {
            //path = pathFacturaJasper.openStream();
            path = pathFacturaJasper;
            
        } else if (ComprobanteEnum.NOTA_CREDITO.getCodigo().equals(tipoComprobanteCodigo)) {
            //path = pathNotaCreditoJasper.openStream();
            path = pathNotaCreditoJasper;
        } else if (ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo().equals(tipoComprobanteCodigo))
        {
            //path=pathRetencionJasper.openStream();
            path=pathRetencionJasper;
        }
        else if (ComprobanteEnum.GUIA_REMISION.getCodigo().equals(tipoComprobanteCodigo))
        {
            //path=pathGuiaRemisionJasper.openStream();
            path=pathGuiaRemisionJasper;
        }else if(ComprobanteEnum.LIQUIDACION_COMPRA.getCodigo().equals(tipoComprobanteCodigo))
        {
            path = pathFacturaJasper; //TODO: Por el momento utilio el mismo comprobante que de la factura
        }else if(ComprobanteEnum.NOTA_VENTA_INTERNA.getCodigo().equals(tipoComprobanteCodigo)) 
        {
            path=pathFacturaJasper;
        }else if(ComprobanteEnum.GUIA_REMISION_INTERNA.getCodigo().equals(tipoComprobanteCodigo)) 
        {
            path=pathGuiaRemisionJasper;
        }
        //comprobante.getTipoDocumento();
        //return path + "-" + comprobante.getInformacionTributaria().getPreimpreso() +"_"+claveAcceso+ ".pdf";
        return path;
    }
    
    public void copiarComprobantesElectronicos(String claveAcceso,String carpetaOrigen,String carpetaDestino)
    {
        String pathOrigen=getPathComprobanteConClaveAcceso(carpetaOrigen,claveAcceso);
        String pathDestino=getPathComprobanteConClaveAcceso(carpetaDestino,claveAcceso);        
        ComprobantesElectronicosUtil.copiarArchivoXml(pathOrigen, pathDestino);
        
    }
    
    public void eliminarComprobanteElectronico(String claveAcceso,String carpeta)
    {
        String pathEliminar=getPathComprobanteConClaveAcceso(carpeta,claveAcceso);
        ComprobantesElectronicosUtil.eliminarArchivo(pathEliminar);
    }
    
    public boolean verificarExisteArchivo(String claveAcceso,String carpeta)
    {
        String pathEliminar=getPathComprobanteConClaveAcceso(carpeta,claveAcceso);
        return new File(pathEliminar).exists();
    }

    public void setSecuencialLote(Integer secuencialLote) {
        this.secuencialLote = secuencialLote;
    }

    public ServicioSri getServicioSri() {
        return servicioSri;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<String> getClavesAccesoLote() {
        return clavesAccesoLote;
    }

    public void setClavesAccesoLote(List<String> clavesAccesoLote) {
        this.clavesAccesoLote = clavesAccesoLote;
    }

    public Map<String, String> getMapCodeAndNameTipoRetecion() {
        return mapCodeAndNameTipoRetecion;
    }

    public void setMapCodeAndNameTipoRetecion(Map<String, String> mapCodeAndNameTipoRetecion) {
        this.mapCodeAndNameTipoRetecion = mapCodeAndNameTipoRetecion;
    }

    public Map<String, String> getMapCodeAndNameTipoDocumento() {
        return mapCodeAndNameTipoDocumento;
    }

    public void setMapCodeAndNameTipoDocumento(Map<String, String> mapCodeAndNameTipoDocumento) {
        this.mapCodeAndNameTipoDocumento = mapCodeAndNameTipoDocumento;
    }

    public void setPathGuiaRemisionJasper(URL pathGuiaRemisionJasper) {
        this.pathGuiaRemisionJasper = pathGuiaRemisionJasper;
    }

    public void setJasperSubReporteGuiaRemision(JasperReport jasperSubReporteGuiaRemision) {
        this.jasperSubReporteGuiaRemision = jasperSubReporteGuiaRemision;
    }

    public void setMetodoEnvioSmsInterface(MetodoEnvioSmsInterface metodoEnvioSmsInterface) {
        this.metodoEnvioSmsInterface = metodoEnvioSmsInterface;
    }

    public List<AlertaComprobanteElectronico> getAlertas() {
        return alertas;
    }

    public void setEnviarCorreoComprobanteAutorizado(Boolean enviarCorreoComprobanteAutorizado) {
        this.enviarCorreoComprobanteAutorizado = enviarCorreoComprobanteAutorizado;
    }

    public void setEnviarCorreos(Boolean enviarCorreos) {
        this.enviarCorreos = enviarCorreos;
    }

    public JasperReport getReporteInfoOtroAdicional() {
        return reporteInfoOtroAdicional;
    }

    public void setReporteInfoOtroAdicional(JasperReport reporteInfoOtroAdicional) {
        this.reporteInfoOtroAdicional = reporteInfoOtroAdicional;
    }

    public Map<ComprobanteEnum, String> getAliasNombreDocumentosMap() {
        return aliasNombreDocumentosMap;
    }

    public void setAliasNombreDocumentosMap(Map<ComprobanteEnum, String> aliasNombreDocumentosMap) {
        this.aliasNombreDocumentosMap = aliasNombreDocumentosMap;
    }
    
    
    
    
    

    @Override
    public void run() {
        if(enviarPorLotes)
        {
            procesarComprobanteLote();
        }
        else
        {        
            procesarComprobante();
        }
    }

}
