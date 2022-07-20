/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.texto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @auhor
 */
public abstract class UtilidadesExpresionesRegulares 
{
    public static Boolean validar(String cadena, String regex)
    {
        //Validacion inicial
        if(cadena==null)
        {
            return false;
        }
        
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
        
}
