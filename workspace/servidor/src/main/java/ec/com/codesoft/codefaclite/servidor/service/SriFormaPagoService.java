/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriFormaPagoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public class SriFormaPagoService extends ServiceAbstract<SriFormaPago, SriFormaPagoFacade> implements SriFormaPagoServiceIf
{

    public SriFormaPagoService() throws RemoteException {
        super(SriFormaPagoFacade.class);
    }
    
}
