/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author
 */
public enum FormatoHojaEnum {
    
    A5("A5"),
    A4("A4"),
    A3("A3"),
    TICKET("TICKET");
    
    private String letra;    

    private FormatoHojaEnum(String letra) {
        this.letra = letra;
    }

    public String getLetra() {
        return letra;
    }
    
    public static FormatoHojaEnum buscarPorLetra(String letra)
    {
        for (FormatoHojaEnum formato : FormatoHojaEnum.values()) {
            if(formato.getLetra().equals(letra))
            {
                return formato;
            }
        }
        
        return null;
    }
    
}
