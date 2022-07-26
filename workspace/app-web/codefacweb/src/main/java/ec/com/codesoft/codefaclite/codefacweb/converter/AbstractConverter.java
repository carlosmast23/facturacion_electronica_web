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
public abstract class AbstractConverter {

    public abstract Object buscarObjetoPorId(String valor) throws RemoteException ;
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        if(!value.isEmpty())
        {
            try {
                return buscarObjetoPorId(value);
                //empresa= ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorId(Long.parseLong(value));
            } catch (RemoteException ex) {
                Logger.getLogger(AbstractConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
