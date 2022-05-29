/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.formato;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;

/**
 *
 * @author Carlos
 */
public abstract class ComprobantesUtilidades {
    
    public static String formatoEstablecimiento(String establecimiento)
    {
        return UtilidadesTextos.llenarCarateresIzquierda(establecimiento.toString(),3,"0");
    }
    
    public static String formatoPuntoEmision(String puntoEmision) {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision.toString(), 3, "0");
    }        
    
    public static String formatoSecuencial(String secuencial)
    {
        return UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(),8,"0");
    }
}
