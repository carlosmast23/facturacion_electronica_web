
import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestServidoSms {
    public static void main(String[] args) {
        try {
            ServidorSMS servidorsms=ServidorSMS.getInstance();
            servidorsms.iniciarServidor();
            Thread.sleep(3600*3600);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestServidoSms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
