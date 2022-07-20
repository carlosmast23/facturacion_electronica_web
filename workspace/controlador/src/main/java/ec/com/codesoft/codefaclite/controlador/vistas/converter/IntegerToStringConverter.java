/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.converter;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;

/**
 *
 * @auhor
 */
public class IntegerToStringConverter extends ConverterSwingMvvc<String,Integer> {

    @Override
    public Integer castPropertyToComponente(String value) {
        if (value == null || value.isEmpty()) {
            value = "0";
        }
        return new Integer(value); 
    }

    @Override
    public String castComponentToProperty(Integer value) {
        return value.toString();
    }
    
}
