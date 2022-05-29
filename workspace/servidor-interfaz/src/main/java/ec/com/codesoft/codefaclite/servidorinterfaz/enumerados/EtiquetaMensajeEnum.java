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
public enum EtiquetaMensajeEnum {
    ETIQUETA_NOMBRE_ESTUDIANTE("ESTUDIANTE","[nombre_estudiante]"),
    ETIQUETA_NOMBRE_REPRESENTANTE("REPRESENTANTE","[nombre_representante]");
    
    private String nombre;
    private String etiqueta;
    

    private EtiquetaMensajeEnum(String nombre,String etiqueta) {
        this.nombre=nombre;
        this.etiqueta=etiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
    
    
    
            
}
