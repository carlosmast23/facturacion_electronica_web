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
 *
 * @auhor
 */
@Target(value={ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface TableBinding {
    /**
     * Origen de los datos
     * @return 
     */
    public String source();
    //public String modelTable();
    /**
     * Implementacion para poder agregar los datos en la tabla
     * @return 
     */
    public String tableAddDataInterface();
    /**
     * Campo donde se va a grabar el valor seleccionado por la fila
     * @return 
     */
    public String selectValue();
    
    /**
     * Este modo permite crear una tabla con campos de seleccion con checks
     */
    public boolean modeCheck() default false;
    
    /**
     * En este campo se tiene que setear la propiedad que voy a llenar los datos cuando tengo check habilitados
     * @return 
     */
    public String listSelected() default ""; 
    
    /**
     * Componente que esta vinculado a un checkBox que permite terminar de completar la logica de selecciona con las tablas
     * @return 
     */
    public String componenteCheckSelect() default "";
    
    /**
     * Metodo que me permite establecer la referencia del controlador del mvc con la vista para tener mayor control de las acciones
     * @return 
     */
    public String controlador() default "";
}
