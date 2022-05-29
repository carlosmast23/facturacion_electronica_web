/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.IngresoCajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.IngresoCajaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class IngresoCajaService extends ServiceAbstract<IngresoCaja, IngresoCajaFacade> implements IngresoCajaServiceIf{

    public IngresoCajaService() throws RemoteException {
        super(IngresoCajaFacade.class);
    }
    
}
