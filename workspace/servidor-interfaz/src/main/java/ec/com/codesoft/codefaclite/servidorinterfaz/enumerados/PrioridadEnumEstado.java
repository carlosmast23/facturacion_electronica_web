/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public enum PrioridadEnumEstado 
{
    ALTA("A","Alta"),
    MEDIA("M","Media"),
    BAJA("B","Baja");
    
    private String letra;
    private String nombre;
    
    private PrioridadEnumEstado(String letra, String nombre)
    {
        this.letra = letra;
        this.nombre = nombre;
    }

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static PrioridadEnumEstado getEnum(String letra)
    {
        for(PrioridadEnumEstado prioridadEnumEstado : PrioridadEnumEstado.values())
        {
            if(prioridadEnumEstado.letra.equals(letra))
            {
                return prioridadEnumEstado;
            }
        }
        
        return null;
    }
    
    
    
}
