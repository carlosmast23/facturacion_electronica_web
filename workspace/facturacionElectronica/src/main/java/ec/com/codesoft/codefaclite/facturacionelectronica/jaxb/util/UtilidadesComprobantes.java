/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesComprobantes {

    public static Map<String,String> decodificarArchivoBase64Offline(String nombreArchivo, String numeroAutorizacion, String fechaAutorizacion) throws Exception{
        try {
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document documento = builder.parse(archivo);
                NodeList listaNodos = documento.getElementsByTagName("*");
                String comprobante = null;
                String numeroAutorizacionComprobante = "";
                String fechaAutorizacionComprobante = "";
                String estadoComprobante = "";
                for (int i = 0; i < listaNodos.getLength(); i++) {
                    Element elemento = (Element) listaNodos.item(i);
                    if (elemento.getNodeName().equals("comprobante")) {
                        comprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if (elemento.getNodeName().equals("estado")) {
                        estadoComprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if ((numeroAutorizacion == null)
                            && (fechaAutorizacion == null)) {
                        if (elemento.getNodeName().equals("numeroAutorizacion")) {
                            numeroAutorizacionComprobante = elemento.getChildNodes().item(0).getNodeValue();
                        }
                        if (elemento.getNodeName().equals("fechaAutorizacion")) {
                            fechaAutorizacionComprobante = fechaAutorizacionComprobante + elemento.getChildNodes().item(0).getNodeValue();
                        }
                    }
                }
                if ((fechaAutorizacion == null) && (numeroAutorizacion == null)) {
                    fechaAutorizacion = fechaAutorizacionComprobante;
                    numeroAutorizacion = numeroAutorizacionComprobante;
                }
                if ("AUTORIZADO".equalsIgnoreCase(estadoComprobante)) {
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("comprobante", comprobante);
                    map.put("numeroAutorizacion", numeroAutorizacion);
                    map.put("fechaAutorizacion",UtilidadesComprobantes.getFormatXmlGregorianCalendarToSimpleFormat(fechaAutorizacion));
                    map.put("estado",estadoComprobante);
                    //generarReporteComprobante(comprobante, numeroAutorizacion, fechaAutorizacion);
                    return map;
                }
            }
        } catch (MalformedByteSequenceException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (SAXException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return null;

    }
    
     public static Map<String,String> decodificarArchivoAutorizado(String nombreArchivo) throws Exception{
        try {
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document documento = builder.parse(archivo);
                NodeList listaNodos = documento.getElementsByTagName("*");
                String comprobante = null;
                String numeroAutorizacionComprobante = "";
                String fechaAutorizacionComprobante = "";
                String estadoComprobante = "";
                for (int i = 0; i < listaNodos.getLength(); i++) {
                    Element elemento = (Element) listaNodos.item(i);
                    if (elemento.getNodeName().equals("comprobante")) {
                        comprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if (elemento.getNodeName().equals("estado")) {
                        estadoComprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }

                    if (elemento.getNodeName().equals("numeroAutorizacion")) {
                        numeroAutorizacionComprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if (elemento.getNodeName().equals("fechaAutorizacion")) {
                        fechaAutorizacionComprobante = fechaAutorizacionComprobante + elemento.getChildNodes().item(0).getNodeValue();
                    }
                }
                

                if ("AUTORIZADO".equalsIgnoreCase(estadoComprobante)) {
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("comprobante", comprobante);
                    map.put("numeroAutorizacion", numeroAutorizacionComprobante);
                    map.put("fechaAutorizacion",UtilidadesComprobantes.getFormatXmlGregorianCalendarToSimpleFormat(fechaAutorizacionComprobante));
                    map.put("estado",estadoComprobante);
                    //generarReporteComprobante(comprobante, numeroAutorizacion, fechaAutorizacion);
                    return map;
                }
            }
        } catch (MalformedByteSequenceException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (SAXException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return null;

    }
 
    /*
    private static void generarReporteComprobante(String documento, String numeroAutorizacion, String fechaAutorizacion) {
        if (documento != null) {
            //String rutaReporte = ServerConfigurationManager.getAppSetting("RUTA_REPORTES");
            File comprobanteElectronicoXML = Utilitarios.crearArchivoDesdeCadena(rutaReporte + numeroAutorizacion + ".xml", documento);
            //ComprobanteElectronico comprobante = null;

            try {
                //String tipoComprobante = obtenerTipoComprobante(documento);
                JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                FacturaComprobante facturaComprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(file);

                generarReporte(comprobante, reporteComprobante, numeroAutorizacion, fechaAutorizacion, tipoIva);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
*/

    public static void generarReporteJasper(URL pathReporte, Map parametros, Collection datos, String pathGrabar) {
        try {
            JasperReport report=ReporteComprobantesElectronicosProxy.obtenerReporte(pathReporte);
            if(report==null)
            {
                InputStream inputSream=pathReporte.openStream();
                report = JasperCompileManager.compileReport(inputSream);
                ReporteComprobantesElectronicosProxy.agregar(pathReporte, report);
            
            }
            JRBeanCollectionDataSource dataReport = new JRBeanCollectionDataSource(datos);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataReport);
            
            File file = new File(pathGrabar);
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            //JasperExportManager.exportReportToPdfFile(print, pathGrabar);
            //generando directamente el file stream por que con el otro metodo gena
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(print,fileOutputStream);
            fileOutputStream.close();
            //JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public static JasperPrint generarReporteJasperPrint(URL pathReporte, Map parametros, Collection datos) {
        try {
            JasperReport report = ReporteComprobantesElectronicosProxy.obtenerReporte(pathReporte);
            if(report==null)
            {
                InputStream inputStream=pathReporte.openStream();
                report = JasperCompileManager.compileReport(inputStream);
                ReporteComprobantesElectronicosProxy.agregar(pathReporte, report);
            }
            
            JRBeanCollectionDataSource dataReport = new JRBeanCollectionDataSource(datos);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataReport);
            return print;
            //JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            ex.printStackTrace();
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
    
    public static InputStream getStreamByPath(String pathLogoImagen)
    {
        try {
            System.out.println(pathLogoImagen);
            File file = new File(pathLogoImagen);
            BufferedImage image = ImageIO.read(file); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);            
            //return new ByteArrayInputStream(os.toByteArray());
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        public static InputStream getStreamByPath(InputStream input)
    {
        try {

            BufferedImage image = ImageIO.read(input); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);            
            return new ByteArrayInputStream(os.toByteArray());
            //return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getFormatXmlGregorianCalendarToSimpleFormat(String date)
    {
        String dateString = "";
        try {
            XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            dateString = formatter.format(result.toGregorianCalendar().getTime());

        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateString;
    }
    
    //public static Map<String,Object> obtener
    
    public static String castMensajeToString(Mensaje mensaje)
    {
        String mensajeError
                = "Identificador:" + mensaje.getIdentificador() + "\n"
                + "Info Adicional:" + mensaje.getInformacionAdicional() + "\n"
                + "Mensaje:" + mensaje.getMensaje() + "\n"
                + "Tipo:" + mensaje.getTipo();
        return mensajeError;
    }
    
    public static String castMensajeAutorizadoToString(Autorizacion.Mensajes mensaje)
    {
        String mensajeError="";
        for (autorizacion.ws.sri.gob.ec.Mensaje mensajeTmp : mensaje.getMensaje()) {
            mensajeError+= 
                    "Identificador:" + mensajeTmp.getIdentificador() + "\n"
                + "Info Adicional:" + mensajeTmp.getInformacionAdicional() + "\n"
                + "Mensaje:" + mensajeTmp.getMensaje() + "\n"
                + "Tipo:" + mensajeTmp.getTipo()+"\n\n";
            
        }
        
        return mensajeError;
    }
    

}
