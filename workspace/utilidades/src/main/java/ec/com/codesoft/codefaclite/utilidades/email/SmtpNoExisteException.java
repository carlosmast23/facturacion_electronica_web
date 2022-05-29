/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.email;

/**
 *
 * @author Carlos
 */
public class SmtpNoExisteException extends Exception {

    public SmtpNoExisteException(String message) {
        super(message);
    }
    
}
