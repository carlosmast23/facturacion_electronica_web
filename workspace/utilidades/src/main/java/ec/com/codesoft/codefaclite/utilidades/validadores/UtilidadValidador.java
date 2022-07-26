/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

/**
 *
 * @author
 */
public abstract class UtilidadValidador {
    
    public static String normalizarTextoFacturacionElectronica(String s) {
        
        //Si la variable es nula devolver en blanco
        if(s==null)
            return "";
        
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii =    "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC()()";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }

        output = output.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " ");
        //String regex = "[^0-9A-Za-z.,;:_()&= ]";
        return output;
    }
    
    public static String normalizarTexto(String s) {
        
        //Si la variable es nula devolver en blanco
        if(s==null)
            return "";
        
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}^\\-¿";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC()()    ";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }

        output = output.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " ");
        String regex = "[^0-9A-Za-z.,;:_()&= ]";
        return output.replaceAll(regex, "");
    }
    
    /**
     * Funcion que me permite quitar caracteres especiales de una cadenas obiando simbolos como @ y .
     * @param s
     * @return 
     */
    public static String normalizarTextoCorreo(String s) {

        //()<>@,;:"[]ç%&
        // Cadena de caracteres original a sustituir.
        //String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}^\\-¿";
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
    
       
}
