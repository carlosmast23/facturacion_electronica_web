/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import java.rmi.RemoteException;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter("formaPagoConverter")
public class FormaPagoConverter extends AbstractConverter implements Converter {

    @Override
    public Object buscarObjetoPorId(String valor) throws RemoteException {
        //System.out.println("Valor Converter:"+valor);
        SriFormaPago formaPago= ServiceFactory.getFactory().getSriFormaPagoServiceIf().buscarPorId(Long.parseLong(valor));
        //System.out.println("Valor Sri Forma Pago:"+formaPago);
        return formaPago;
    }
    
}
