/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.vistas.converter;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.util.Date;

/**
 *
 * @author DellWin10
 */
public class DateUtilToDateSqlConverter extends  ConverterSwingMvvc<java.sql.Date, java.util.Date>{

    @Override
    public Date castPropertyToComponente(java.sql.Date value) {
        if (value == null) 
        {
                return null;
        }
        
        return UtilidadesFecha.castDateSqlToUtil(value);
        
    }

    @Override
    public java.sql.Date castComponentToProperty(Date value) {
        if(value!=null)
        {
            return UtilidadesFecha.castDateUtilToSql(value);
        }
        return null;
    }
    
    
}
