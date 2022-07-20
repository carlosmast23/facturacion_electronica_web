/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.math.BigDecimal;

/**
 *
 * @auhor
 */
public abstract class UtilidadesNumeros {
    
    public static int numeroDecimales(String numeroStr)
    {
        BigDecimal numero=new BigDecimal(numeroStr);
        String[] decimales= numero.toString().split("\\.");
        if(decimales.length>1)
        {
            int cantidadDecimales=decimales[1].length();
            return cantidadDecimales;
        }
        
        return 0;
    }
    
    public static Integer castStringToInteger(String numeroStr)
    {
        if(numeroStr!=null && !numeroStr.trim().isEmpty())
        {
            try
            {
                return Integer.parseInt(numeroStr);
            }
            catch(Exception e)
            {
                System.out.println("Error casting Integer: "+e.getMessage());
                return null;
            }
        }
        return null;
    }
    
    public static BigDecimal castStringToBigDecimal(String numeroStr)
    {
        if(numeroStr!=null && !numeroStr.trim().isEmpty())
        {
            try
            {
                return new BigDecimal(numeroStr);
            }
            catch(Exception e)
            {
                System.out.println("Error casting Integer: "+e.getMessage());
                return null;
            }
        }
        return null;
    }
    
    
}
