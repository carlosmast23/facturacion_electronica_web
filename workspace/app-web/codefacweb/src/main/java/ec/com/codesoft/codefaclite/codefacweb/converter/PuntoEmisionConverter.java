/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.rmi.RemoteException;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author
 */

@FacesConverter("puntoEmisionConverter")
public class PuntoEmisionConverter extends AbstractConverter implements Converter{

    @Override
    public Object buscarObjetoPorId(String id) throws RemoteException {
        return ServiceFactory.getFactory().getPuntoVentaServiceIf().buscarPorId(Long.parseLong(id));
    }
    
}
