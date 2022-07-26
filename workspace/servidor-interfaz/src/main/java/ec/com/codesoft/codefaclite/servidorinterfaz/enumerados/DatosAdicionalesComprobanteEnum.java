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
public enum DatosAdicionalesComprobanteEnum {    
    NOMBRE_ESTUDIANTE("Estudiante"),
    CODIGO_ESTUDIANTE("CodEstudiante"),
    FECHA_VENCIMIENTO("FechaVencimiento"),
    CURSO_ESTUDIANTE("Curso");
    
    private DatosAdicionalesComprobanteEnum(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;

    public String getNombre() {
        return nombre;
    }
    
    
    
}
