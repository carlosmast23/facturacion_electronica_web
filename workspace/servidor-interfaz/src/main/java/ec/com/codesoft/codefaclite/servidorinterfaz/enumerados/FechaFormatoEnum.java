/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.text.SimpleDateFormat;

/**
 *
 * @author
 */
public enum FechaFormatoEnum {
    ANIO_MES_DIA("yyyy-MM-dd");

    private FechaFormatoEnum(String formatString) {
        this.formatString = formatString;
    }
    
    private String formatString;
    
    public String getFecha(java.util.Date fecha)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(fecha);
    }
    
    public String getFecha(java.sql.Date fecha)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(fecha);
    }
    
    
}
