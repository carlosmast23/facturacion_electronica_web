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
public class StringToIntegerConverter extends ConverterSwingMvvc<Integer,String>{

    @Override
    public String castPropertyToComponente(Integer value) {
        return value.toString();
    }

    @Override
    public Integer castComponentToProperty(String value) {
        if (value == null || value.isEmpty()) {
            value = "0";
        }
        return new Integer(value);
    }
    
}
