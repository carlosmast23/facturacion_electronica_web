/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.VentaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Venta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.VentaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @auhor
 */
public class VentaService extends ServiceAbstract<Venta, VentaFacade> implements VentaServiceIf{

    public VentaService() throws RemoteException {
        super(VentaFacade.class);
    }
    
}
