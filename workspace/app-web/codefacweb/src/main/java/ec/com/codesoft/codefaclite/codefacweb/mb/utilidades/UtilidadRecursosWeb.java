/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

import java.io.InputStream;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadRecursosWeb {

    /**
     * Metodo que me permite obtener un archivo del directorio web de la carpeta RESOURCES
     * @param path
     * @return 
     */
    public static InputStream obtenerRecurso(String path) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //InputStream input = externalContext.getResourceAsStream("/resources/icons/foo.png");
        InputStream input = externalContext.getResourceAsStream("/resources/"+path);
        return input;
    }
}
