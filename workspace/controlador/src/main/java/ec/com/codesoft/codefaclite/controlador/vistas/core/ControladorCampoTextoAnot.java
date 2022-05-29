/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion que permite especificar que estos datos van a estar
 * asociados con jTextField
 * @author CARLOS_CODESOFT
 */
@Target(value={ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface ControladorCampoTextoAnot {
    String nombre() ;
}
