/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter("documentoEnumConverter")
public class DocumentoEnumConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return DocumentoEnum.obtenerDocumentoPorCodigo(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value==null)
            return "";
        else
            return value.toString();
    }
    
}
