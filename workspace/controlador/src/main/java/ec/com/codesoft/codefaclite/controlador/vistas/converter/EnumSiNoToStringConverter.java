/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.converter;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;

/**
 *
 * @auhor
 */
public class EnumSiNoToStringConverter extends ConverterSwingMvvc<String,EnumSiNo>{
    
    @Override
    public EnumSiNo castPropertyToComponente(String value) {
        //if (value == null || value.isEmpty()) {
        //    value = null;
        //}
        
        return EnumSiNo.getEnumByLetra(value); 
    }

    @Override
    public String castComponentToProperty(EnumSiNo value) {
        if(value!=null)
        {
            return value.getLetra();
        }
        return null;
    }
    
}
