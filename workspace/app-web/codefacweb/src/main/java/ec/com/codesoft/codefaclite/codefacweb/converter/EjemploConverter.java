/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author
 */
@FacesConverter("ejemploConverter")
public class EjemploConverter implements Converter{

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Empresa empresa=null;
        if(!value.isEmpty())
        {
            try {
                empresa= ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorId(Long.parseLong(value));
            } catch (RemoteException ex) {
                Logger.getLogger(EjemploConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Empresa consultada: "+empresa);
        return empresa;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("oBTENER VALOR"+value);
        return value.toString();
    }
    
}
