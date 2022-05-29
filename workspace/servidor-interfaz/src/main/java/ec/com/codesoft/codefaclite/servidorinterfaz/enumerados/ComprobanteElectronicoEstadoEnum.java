/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum ComprobanteElectronicoEstadoEnum {
    AUTORIZADO("a","Autorizado"),
    NO_AUTORIZADO("n","Sin Autorizado");

    private ComprobanteElectronicoEstadoEnum(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }
    
    private String letra;
    private String nombre;

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
    public static ComprobanteElectronicoEstadoEnum obtenerPorLetra(String letra)
    {
        for (ComprobanteElectronicoEstadoEnum enumerador : ComprobanteElectronicoEstadoEnum.values()) {
            if(enumerador.getLetra().equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    
    
}
