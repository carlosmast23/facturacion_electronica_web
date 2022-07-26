/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;
   
//import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOfflineService;
import ec.com.codesoft.codefaclite.ws.test.AutorizacionTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

/**
 *
 * @author
 */
public class Test {
    public static void main(String[] args) {
         String uri = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
        try {
            URL url = new URL(uri);
             QName qname = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");
             //RecepcionComprobantesOfflineService servicio=new RecepcionComprobantesOfflineService(url,qname);
             System.out.println("si existe servicio con sri");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AutorizacionTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no existe servicio");
        }
    }
}
