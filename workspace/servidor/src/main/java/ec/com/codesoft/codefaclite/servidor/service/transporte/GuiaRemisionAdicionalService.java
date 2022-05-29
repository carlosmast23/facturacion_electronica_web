/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionAdicionalFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemisionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionAdicionalServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionAdicionalService extends ServiceAbstract<GuiaRemisionAdicional,GuiaRemisionAdicionalFacade> implements GuiaRemisionAdicionalServiceIf{

    public GuiaRemisionAdicionalService() throws RemoteException {
        super(GuiaRemisionAdicionalFacade.class);
    }
    
}
