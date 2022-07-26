/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 *
 * @author
 */
public class TestCorreo {
    public static void main(String[] args) {

        /*try {
            List<String> correos=new ArrayList<String>();
            correos.add("carlosmast2301@hotmail.es");
            CorreoElectronico correoElectronico=new CorreoElectronico("carlosmast2301@hotmail.es","Empresa ABC","codesoft2302","hola",correos, "Prueba",null);
            //correoElectronico.sendMail();
        } catch (AuthenticationFailedException ex) {
            System.out.println("Fallo al autentificar el usuario");
            Logger.getLogger(TestCorreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(TestCorreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SmtpNoExisteException ex) {
            Logger.getLogger(TestCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
    
}
