/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;

/**
 *
 * @author
 * Funcion que proporciona un interfaz para pasar una referencia de un objeto y permitir
 * utilizar como una pantalla de dialogo relacionada con otra para pasar un valor al
 * formulario principal
 */
public interface DialogInterfacePanel<T> {
    
    public T getResult() throws ExcepcionCodefacLite;
}
