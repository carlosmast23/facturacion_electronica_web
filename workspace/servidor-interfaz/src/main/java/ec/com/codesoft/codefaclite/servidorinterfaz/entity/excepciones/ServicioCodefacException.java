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
public class ServicioCodefacException extends Exception {
    
    //Variable que indica si se puede volver a procesar pero en modo forzado
    private Boolean procesarModoForzado;
    
    public ServicioCodefacException(String message) {
        super(message);
        this.procesarModoForzado=false;
    }

    public ServicioCodefacException(String string,Boolean procesarModoForzado) {
        super(string);
        this.procesarModoForzado = procesarModoForzado;
    }
    
    

    public Boolean getProcesarModoForzado() {
        return procesarModoForzado;
    }

    public void setProcesarModoForzado(Boolean procesarModoForzado) {
        this.procesarModoForzado = procesarModoForzado;
    }
    
    
}
