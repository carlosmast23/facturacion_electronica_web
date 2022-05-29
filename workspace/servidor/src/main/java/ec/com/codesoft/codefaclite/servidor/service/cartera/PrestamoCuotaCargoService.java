/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraFacade;
import ec.com.codesoft.codefaclite.servidor.facade.cartera.PrestamoCuotaCargoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuotaCargo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoCuotaCargoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class PrestamoCuotaCargoService extends ServiceAbstract<PrestamoCuotaCargo,PrestamoCuotaCargoFacade> implements PrestamoCuotaCargoServiceIf {

    public PrestamoCuotaCargoService() throws RemoteException {
        super(PrestamoCuotaCargoFacade.class);
    }
    
}
