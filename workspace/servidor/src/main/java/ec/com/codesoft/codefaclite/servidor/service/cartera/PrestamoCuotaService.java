/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.PrestamoCuotaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuota;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoCuotaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public class PrestamoCuotaService extends ServiceAbstract<PrestamoCuota,PrestamoCuotaFacade> implements PrestamoCuotaServiceIf {

    public PrestamoCuotaService() throws RemoteException {
        super(PrestamoCuotaFacade.class);
    }
    
}
