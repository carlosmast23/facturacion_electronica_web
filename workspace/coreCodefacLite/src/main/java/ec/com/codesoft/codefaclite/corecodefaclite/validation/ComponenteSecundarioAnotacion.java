/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author
 */
@Target(value={ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponenteSecundarioAnotacion {
     String nombreCategoria() default "Herramientas"; 
     boolean visible() default true;
}
