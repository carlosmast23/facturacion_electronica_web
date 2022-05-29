/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Carlos
 */
public class UtilidadIva {
    
    public static BigDecimal calcularValorConIvaIncluido(BigDecimal ivaPorcentaje,BigDecimal icePorcentaje,BigDecimal valor)
    {       
        if(icePorcentaje!=null)
        {            
            BigDecimal icePorcentajeTmp=icePorcentaje.
                    divide(UtilidadBigDecimal.CIEN,5,BigDecimal.ROUND_HALF_UP).
                    add(BigDecimal.ONE);
            valor=valor.multiply(icePorcentajeTmp); //Finalmente calculo solo con 2 decimales porque no tiene sentido mas decimal porque se supoene que es el valor final de lafctura y al final solo pueden 2 facuras
        }
        return ivaPorcentaje.add(BigDecimal.ONE).multiply(valor).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * TODO: Ver si puedo unir con el metodo de arriba
     * @param icePorcentaje
     * @param valor
     * @return 
     */
    public static BigDecimal calcularValorConIce(BigDecimal icePorcentaje,BigDecimal valor)
    {       
        if(icePorcentaje!=null)
        {            
            BigDecimal icePorcentajeTmp=icePorcentaje.
                    divide(UtilidadBigDecimal.CIEN,5,BigDecimal.ROUND_HALF_UP).
                    add(BigDecimal.ONE);
            valor=valor.multiply(icePorcentajeTmp); //Finalmente calculo solo con 2 decimales porque no tiene sentido mas decimal porque se supoene que es el valor final de lafctura y al final solo pueden 2 facuras
            return valor;
        }
        return null;
        
    }
    
    public static BigDecimal calcularValorUnitario(BigDecimal ivaPorcentaje,BigDecimal icePorcentaje,BigDecimal total)
    {
        BigDecimal ivaTmp=ivaPorcentaje.add(BigDecimal.ONE);
        if(icePorcentaje!=null)
        {            
            BigDecimal iceTmp=icePorcentaje.divide(UtilidadBigDecimal.CIEN,5,BigDecimal.ROUND_HALF_UP).
                    add(BigDecimal.ONE);
            return total.divide(ivaTmp.multiply(iceTmp),5,BigDecimal.ROUND_HALF_UP);
        }
        else
        {
            return total.divide(ivaTmp,5,BigDecimal.ROUND_HALF_UP);
        }
    }
}
