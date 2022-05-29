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
public enum FormatoArchivoEnum {
       
    PDF("pdf"),
    XML("xml"),
    EXCEL("xls");
    
    private static final String SIMBOLO_SEPARADOR=".";
    
    private String extension;

    private FormatoArchivoEnum(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String agregarExtension(String nombreArchivo)
    {
        return  nombreArchivo.concat(SIMBOLO_SEPARADOR).concat(extension);
    }
}
