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
public enum OperadorNegocioEnum {
    /**
     * Identificador para clientes
     */
    CLIENTE("C","Cliente"),
    /**
     * Identificador para proveedores
     */
    PROVEEDOR("P","Proveedor"),
    /**
     * Identificador para cuando son clientes y proveedores
     */
    AMBOS("A","Cliente/Proveedor");

    private OperadorNegocioEnum(String letra, String nombre) {
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
    
    
    public static OperadorNegocioEnum getEnum(String letra)
    {

        for (OperadorNegocioEnum enumerador : OperadorNegocioEnum.values())
        {
            if(enumerador.letra.equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    
    
}
