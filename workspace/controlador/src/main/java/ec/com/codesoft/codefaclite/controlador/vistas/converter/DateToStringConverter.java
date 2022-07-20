/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.converter;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class DateToStringConverter extends ConverterSwingMvvc<String, Date>{

    private static final String formatoFecha="dd/MM/yy";
    
    @Override
    public Date castPropertyToComponente(String value) {
        try {
            if (value == null || value.isEmpty()) {
                return null;
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(formatoFecha);
            java.util.Date dateFormato=simpleDateFormat.parse(value);
            return dateFormato;
        } catch (ParseException ex) {
            Logger.getLogger(DateToStringConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String castComponentToProperty(Date value) {
        if(value!=null)
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(formatoFecha);
            return simpleDateFormat.format(value);
        }
        return null;
    }
    
    
}
