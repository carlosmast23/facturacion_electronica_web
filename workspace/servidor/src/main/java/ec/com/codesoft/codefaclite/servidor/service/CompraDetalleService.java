/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.CompraDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public class CompraDetalleService extends ServiceAbstract<CompraDetalle, CompraDetalleFacade> implements CompraDetalleServiceIf{
    
    public CompraDetalleService() throws RemoteException {
        super(CompraDetalleFacade.class);
    }
    
}
