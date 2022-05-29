/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 *
 * @author Carlos
 */
public class UtilidadBigDecimal {
    
    private static final BigDecimal[] VALORES_DEFECTO = {
        new BigDecimal("100"),
        new BigDecimal("1000"),        
    };
    
    public static final BigDecimal CIEN =VALORES_DEFECTO[0];
    
    /**
     * Me permite calcular el porcetaje desde un valor entero
     * @param porcentaje
     * @param valor
     * @return 
     */
    public static BigDecimal calcularValorPorcentaje(BigDecimal porcentaje,BigDecimal valor,Integer decimales)
    {        
        BigDecimal decimalPorcentaje=porcentaje.divide(UtilidadBigDecimal.CIEN,decimales,RoundingMode.HALF_UP);
        return valor.multiply(decimalPorcentaje);        
        
    }
    
}
