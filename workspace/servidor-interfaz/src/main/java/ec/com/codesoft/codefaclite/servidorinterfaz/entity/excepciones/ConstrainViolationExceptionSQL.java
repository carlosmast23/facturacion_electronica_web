/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones;

/**
 *
 * @author
 */
public class ConstrainViolationExceptionSQL extends Exception {

    public ConstrainViolationExceptionSQL(String message) {
        super(message);
    }
    
}
