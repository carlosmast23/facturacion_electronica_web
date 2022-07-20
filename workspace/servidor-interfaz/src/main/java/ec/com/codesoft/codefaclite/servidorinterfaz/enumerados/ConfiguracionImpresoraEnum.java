/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;

/**
 *
 * @auhor
 */
public enum ConfiguracionImpresoraEnum implements ParametroUtilidades.ComparadorInterface<ConfiguracionImpresoraEnum>{
    NINGUNA("n","Ninguna"),
    IMPRESORA_POR_DEFECTO("d","Impresora por Defecto"),
    SELECCIONAR_IMPRESORA("s","Seleccionar Impresora");
    
    private String letra;
    private String descripcion;

    private ConfiguracionImpresoraEnum() {
    }
        

    private ConfiguracionImpresoraEnum(String letra, String descripcion) {
        this.letra = letra;
        this.descripcion = descripcion;
    }
    
    

    public String getLetra() {
        return letra;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public static ConfiguracionImpresoraEnum buscarPorLetra(String letra)
    {
        for (ConfiguracionImpresoraEnum value : ConfiguracionImpresoraEnum.values()) {
            if(value.getLetra().equals(letra))
            {
                return value;
            }
        }
        return null;
    }

    @Override
    public ConfiguracionImpresoraEnum consultarParametro(String nombreParametro) {
        return buscarPorLetra(nombreParametro);
    }
    
}
