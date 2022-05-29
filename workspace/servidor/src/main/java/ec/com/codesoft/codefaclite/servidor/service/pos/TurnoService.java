/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.TurnoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Turno;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.TurnoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class TurnoService extends ServiceAbstract<Turno, TurnoFacade> implements TurnoServiceIf{

    public TurnoService() throws RemoteException {
        super(TurnoFacade.class);
    }
    
}
