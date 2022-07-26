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
public enum FormatoReporteEnum {
    PDF("pdf","pdf"),
    EXCEL("excel","xlsx");
    
    private String nombre;
    private String extension;

    private FormatoReporteEnum(String nombre,String extension) {
        this.nombre = nombre;
        this.extension=extension;
    }

    public String getNombre() {
        return nombre;
    }

    public String getExtension() {
        return extension;
    }
    
    

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
    
    
}
