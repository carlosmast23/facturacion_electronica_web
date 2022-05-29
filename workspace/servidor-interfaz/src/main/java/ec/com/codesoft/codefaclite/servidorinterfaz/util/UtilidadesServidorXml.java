/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.utilidades.xml.UtilidadesXml;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesServidorXml {

    public static void convertirObjetoXmlEnArchivo(Object objecto, File file) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(AtsJaxb.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "libro.xsd");
            //StringWriter sw = new StringWriter();
            //marshaller.marshal(libro, System.out);
            //File file = new File( "tmp/ejemplo.xml" );
            marshaller.marshal(objecto, file);
        } catch (JAXBException ex) {
            Logger.getLogger(UtilidadesXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
