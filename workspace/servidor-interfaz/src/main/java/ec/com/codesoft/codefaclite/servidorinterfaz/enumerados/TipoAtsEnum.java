/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum TipoAtsEnum {
    MENSUAL("Mensual"),
    PRIMER_SEMESTRE("Primer Semestre"),
    SEGUNDO_SEMESTRE("Segundo Semestre");

    private TipoAtsEnum(String nombre) {
        this.nombre = nombre;
    }
    
    
    private String nombre;

    public String getNombre() 
    {
        return nombre;
    }

    @Override
    public String toString() 
    {
        return nombre;
    }
    
    
}
