/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
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
@FacesConverter("empresaConverter")
public class EmpresaConverter extends AbstractConverter implements Converter{

    @Override
    public Object buscarObjetoPorId(String id) throws RemoteException {
        return ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorId(Long.parseLong(id));
    }
    
}
