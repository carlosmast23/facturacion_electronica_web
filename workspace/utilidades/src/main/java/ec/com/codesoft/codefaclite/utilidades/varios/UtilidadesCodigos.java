/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.util.UUID;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesCodigos {
    
    /**
     * Generar formato de los codigos
     * @param prefijo
     * @param numerador
     * @return 
     */
    public static String generarFormatoCodigo(String prefijo,Integer numerador,Integer tamanioCodigo)
    {
        String numeroConFormato=UtilidadesTextos.llenarCarateresIzquierda(numerador.toString(),tamanioCodigo,"0");
        return prefijo+numeroConFormato;
        //return prefijo
    }
    
    public static String generarPrefijo(String codigoEmpresa,String codigoSucursal,String codigoDocumento,String separador)
    {
        return codigoEmpresa+separador+codigoSucursal+separador+codigoDocumento+separador;
    }
    
    public static String generarCodigoUnicoUUID()
    {
        return UUID.randomUUID().toString().replace("-","");
    }
}
