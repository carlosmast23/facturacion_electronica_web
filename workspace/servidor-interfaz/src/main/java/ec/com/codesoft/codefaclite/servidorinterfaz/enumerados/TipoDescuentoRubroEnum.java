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
public enum TipoDescuentoRubroEnum {
    
    NINGUNO("n","Ninguno"),
    BECA("b","Beca"),
    OTRO("o","Otro");

    private TipoDescuentoRubroEnum(String letra, String nombre) {
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
    
    public static TipoDescuentoRubroEnum buscarPorLetra(String letra)
    {
        for (TipoDescuentoRubroEnum enumerador : TipoDescuentoRubroEnum.values()) {
            if(enumerador.getLetra().equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    

    
}
