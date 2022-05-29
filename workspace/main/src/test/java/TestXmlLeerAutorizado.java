
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestXmlLeerAutorizado {
    public static void main(String args[])
    {        
        try {
            XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar("2019-01-02T16:56:13-05:00");
            System.out.println(result);
            
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TestXmlLeerAutorizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
