/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoAutorizado {
    
    private static final String TAG_ESTADO = "estado";
    private static final String NUMERO_AUTORIZACION = "numeroAutorizacion";
    private static final String FECHA_AUTORIZACION = "fechaAutorizacion";
    private static final String AMBIENTE = "ambiente";
    private static final String COMPROBANTE = "comprobante";
    
    
    private String estado;
    private String numeroAutorizacion;
    private String fechaAutorizacion;
    private String ambiente;
    private String comprobante;

    public ComprobanteElectronicoAutorizado() {
    }
    
    /**
     * Devuelve el objeto construido si no se puede construir devuelve false
     * @param path
     * @return 
     */
    public boolean construirDesdeArchivo(String path)
    {
        File file = new File(path);
        if (file.exists()) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                
                NodeList listaNodos = document.getElementsByTagName("*");
                
                for (int i = 0; i < listaNodos.getLength(); i++) {
                    Element elemento = (Element) listaNodos.item(i);
                    if (elemento.getNodeName().equals(TAG_ESTADO)) {
                        this.estado = elemento.getTextContent();
                    } else if (elemento.getNodeName().equals(TAG_ESTADO)) {
                        this.estado = elemento.getTextContent();
                        
                    } else if (elemento.getNodeName().equals(NUMERO_AUTORIZACION)) {
                        this.numeroAutorizacion = elemento.getTextContent();
                        
                    } else if (elemento.getNodeName().equals(FECHA_AUTORIZACION)) {
                        this.fechaAutorizacion = elemento.getTextContent();
                        
                    } else if (elemento.getNodeName().equals(AMBIENTE)) {
                        this.ambiente = elemento.getTextContent();
                        
                    } else if (elemento.getNodeName().equals(COMPROBANTE)) {
                        this.comprobante = elemento.getTextContent();
                        
                    }
                }
                return true;
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ComprobanteElectronicoAutorizado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(ComprobanteElectronicoAutorizado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ComprobanteElectronicoAutorizado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }
    
    public java.sql.Date obtenerFecha()
    {
        return UtilidadesFecha.stringFormatXMLGregorianCalendarToDate(fechaAutorizacion);
    }

    public String getAmbiente() {
        return ambiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
    
    

    
}
