/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 * Enum que me permite conocer el filtro para consultar la cartera
 * @author CARLOS_CODESOFT
 */
public enum CarteraEstadoReporteEnum {
    TODO("Todo"),
    VENCIDA("Vencida"),
    SIN_VENCER("Sin Vencer");
    
    private String nombre;

    private CarteraEstadoReporteEnum(String nombre) {
        this.nombre = nombre;
    }
    
    
}
