/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

/**
 *  Clase abstracta que ejecuta los metodos necesarios para convertir de un origen de datos
 *  al tipo de dato correcto entre el componente y la propiedad
 *  Los Nombres para el Template son los siguientes
 *  C= tipode de dato del componente de swing que esta enlazado 
 *  P= tipo de dato de la propiedad a la cual esta enlaza
 *  
 * @author CARLOS_CODESOFT
 */
public abstract class ConverterSwingMvvc<C,P> {
    
    public abstract P castPropertyToComponente(C value);
    public abstract C castComponentToProperty(P value);
    
    /**
     * Clase auxiliar solo para saber en las anotaciones si no existe asignado un Class converter
     */
    public static class Error
    {
        
    }
}
