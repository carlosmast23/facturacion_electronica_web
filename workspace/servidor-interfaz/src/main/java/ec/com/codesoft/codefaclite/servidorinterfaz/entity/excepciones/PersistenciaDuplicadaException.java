/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones;

/**
 *
 * @author Carlos
 */
public class PersistenciaDuplicadaException extends Exception {
    public PersistenciaDuplicadaException(String message) {
        super(message);
    }
}
