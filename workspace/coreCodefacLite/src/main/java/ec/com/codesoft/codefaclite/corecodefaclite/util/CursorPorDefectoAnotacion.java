/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion que me sirve para que puedan especificar cual es el campo que por defecto debe tener seteado el cursor, despues de limpiar la pantalla
 * @author Carlos
 */
@Target(value={ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface CursorPorDefectoAnotacion {
    
}
