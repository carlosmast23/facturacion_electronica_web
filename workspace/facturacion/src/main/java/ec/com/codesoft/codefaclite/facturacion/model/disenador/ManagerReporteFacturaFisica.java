/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.BandaComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComponenteComprobanteFisico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Carlos
 */
public class ManagerReporteFacturaFisica {
    
    private final String NOMBRE_ANCHO_DOCUMENTO="pageWidth";
    private final String NOMBRE_ALTO_DOCUMENTO="pageHeight";
    private final String NOMBRE_ALTO_BANDA="height";
    private final String NOMBRE_ID_COMPONENTE="uuid";
    private final String NOMBRE_X_COMPONENTE="x";
    private final String NOMBRE_Y_COMPONENTE="y";
    private final String NOMBRE_ANCHO_COMPONENTE="width";
    private final String NOMBRE_ALTO_COMPONENTE="height";
    private final String NOMBRE_FONT_COMPONENTE="size";
    private final String NOMBRE_BOLD_COMPONENTE="isBold";
    
    /**
     * Este campo va en funcion del ancho de la pagina y parece que es el mismo valor menos los margenes
     */
    private final String NOMBRE_ANCHO_COLUMNA="columnWidth";
    
    private final String ID_LINEA_INICIO="eb719316-db48-41bc-b826-7e0ef1b2ff21";
    private final String ID_LINEA_FINAL="4ead026f-bfbb-4dbb-9189-f0cb97eb039a";
    
    /**
     * Reporte original sobre el cual se va a trabajar
     */
    InputStream reporteOriginal;
    /**
     * Documento en memoria que contiene la estrucutura del xml
     */
    Document document;
    
    /**
     * Raiz Principal del archivo XML
     */
    Element rootNode;

    public ManagerReporteFacturaFisica(InputStream reporteOriginal) {
        this.reporteOriginal = reporteOriginal;
        cargarDocumento();
    }
    
    private void cargarDocumento()
    {
        try {
            SAXBuilder builder = new SAXBuilder();
            document = (Document) builder.build(reporteOriginal);
            rootNode = document.getRootElement();
        } catch (JDOMException ex) {
            Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private Element buscarEtiquetaPorNombre(Element root, String nombre)
    {
        for (Object element : root.getChildren()) {
            if(((Element)element).getName().equals(nombre))
            {
                return (Element) element;
            }
        }
        return null;
    
    }
    
    private void setearComponente(Element columna,ComponenteComprobanteFisico componente,int altoBanda)
    {
        Element bandaElemento=(Element) columna.getChildren().get(0); //el componente 0 es la banda
        //Va a recorrer todos los componentes de la banda
        for (Object object : bandaElemento.getChildren()) {
            Element tipoElemento=(Element) object;
            Element elementoReporte=buscarEtiquetaPorNombre(tipoElemento, "reportElement");
            String elementoReporteId=elementoReporte.getAttribute(NOMBRE_ID_COMPONENTE).getValue();
            
            //Si coincide con alguna de las 2 lineas les elimino en la parte de visualizacion
            if(elementoReporteId.equals(ID_LINEA_FINAL) )
            {
                elementoReporte.getAttribute(NOMBRE_Y_COMPONENTE).setValue((altoBanda-2)+"");
                return;
            }
            
            //Si encuentra el elemento que busca setea los valores
            if(elementoReporteId.equals(componente.getUuid()))
            {
                if(componente.getOculto().equals("s"))
                {
                    tipoElemento.detach();
                    return; //Si el componente esta oculto lo elimino termino de buscar
                }
                
                elementoReporte.getAttribute(NOMBRE_X_COMPONENTE).setValue(componente.getX()+"");
                elementoReporte.getAttribute(NOMBRE_Y_COMPONENTE).setValue(componente.getY()+"");
                elementoReporte.getAttribute(NOMBRE_ANCHO_COMPONENTE).setValue(componente.getAncho()+"");
                elementoReporte.getAttribute(NOMBRE_ALTO_COMPONENTE).setValue(componente.getAlto()+"");
                
                Element elementoCaracteristica=buscarEtiquetaPorNombre(tipoElemento, "textElement");
                
                if(elementoCaracteristica!=null)
                {
                    Element elementoFont=buscarEtiquetaPorNombre(elementoCaracteristica, "font");
                    if(elementoFont!=null)
                    {
                        elementoFont.getAttribute(NOMBRE_FONT_COMPONENTE).setValue(componente.getTamanioLetra()+"");//TODO: Falta grabar este valor

                        String boldTxt=(componente.getNegrita().equals("s"))?"true":"false";
                        elementoFont.getAttribute(NOMBRE_BOLD_COMPONENTE).setValue(boldTxt);                    
                    }
                }
                
                return;
            }
            
        }
    }
    
    /**
     * Convertir un documento modificado a formato inputStream para que puede compilar
     * y mostrar el reporte
     * @return 
     */
    public InputStream generarNuevoDocumento()
    {
        InputStream stream = null;
        try {
            //Generar un nuevo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            //xmlOutput.output(document, new FileWriter("factura_fisica.jrxml"));
            String documento = xmlOutput.outputString(document);
            stream = new ByteArrayInputStream(documento.getBytes("UTF-8"));
            return stream;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stream;
    
    }
    

    public void setearNuevosValores(ComprobanteFisicoDisenio comprobante)
    {
       //Establecer propiedades de ancho y alto del documento
        rootNode.getAttribute(NOMBRE_ANCHO_DOCUMENTO).setValue(comprobante.getAncho()+"");
        rootNode.getAttribute(NOMBRE_ALTO_DOCUMENTO).setValue(comprobante.getAlto()+"");
        rootNode.getAttribute(NOMBRE_ANCHO_COLUMNA).setValue(comprobante.getAnchoColumna()+"");
        
        //Establecer propiedades de las bandas
        for (BandaComprobante banda : comprobante.getSecciones()) {
            Element columnaElemento=buscarEtiquetaPorNombre(rootNode,banda.getNombre());
            Element bandaElemento=(Element) columnaElemento.getChildren().get(0); //el componente 0 es la banda
            bandaElemento.getAttribute(NOMBRE_ALTO_BANDA).setValue(banda.getAlto()+"");
            
            //Establcer propiedades de los componentes
            for (ComponenteComprobanteFisico componente : banda.getComponentes()) 
            {
                setearComponente(columnaElemento,componente,banda.getAlto());
            }
        }
        
    }
    
    
    
}
