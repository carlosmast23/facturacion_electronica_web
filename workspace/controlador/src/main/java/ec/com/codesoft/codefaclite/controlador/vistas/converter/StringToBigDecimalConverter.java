/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.converter;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class StringToBigDecimalConverter extends ConverterSwingMvvc<BigDecimal,String>{

    @Override
    public String castPropertyToComponente(BigDecimal value) {
        return value.toString();
    }

    @Override
    public BigDecimal castComponentToProperty(String value) {
        if(value==null || value.isEmpty())
        {
            value="0";
        }
        return new BigDecimal(value);
    }
    
}
