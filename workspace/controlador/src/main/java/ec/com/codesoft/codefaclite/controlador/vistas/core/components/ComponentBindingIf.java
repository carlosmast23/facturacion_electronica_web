/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;

/**
 *
 * @auhor
 */
public interface ComponentBindingIf<T,C> {
    /**
     * Este metodo me permite establecer los valor que van de la VISTA al CONTROLADOR
     */
    public void getAccion(String nombrePropiedadControlador,ConverterSwingMvvc converter);
    /**
     * Este metodo me permite establecer elos valores que van del CONTROLADOR a la VISTA
     */
    public void setAccion(T value,String nombrePropiedadControlador,ConverterSwingMvvc converter);

    /**
     * Metodo que debe devuelve el nombre de la propiedad de la vista
     * @return 
     */
    public String getNombrePropiedadControlador(C componente);
    
    public Class getConverterClass(C anotacion);
}
