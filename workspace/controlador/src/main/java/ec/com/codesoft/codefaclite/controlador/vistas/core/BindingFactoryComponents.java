/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import java.util.ArrayList;
import java.util.List;

/**
 *Metodo que me permite establecer variables generales de configuracion y
 * la lista de todos los binding que se pueden usar
 * @auhor
 */
public abstract class BindingFactoryComponents {
    
    public static List<Class> listaAnotaciones;
    static
    {
        listaAnotaciones=new ArrayList<Class>();
        listaAnotaciones.add(TextFieldBinding.class);        
    }
}
