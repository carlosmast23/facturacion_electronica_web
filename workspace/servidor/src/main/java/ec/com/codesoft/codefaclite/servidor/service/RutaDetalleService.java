/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RutaDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RutaDetalleServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @auhor
 */
public class RutaDetalleService extends ServiceAbstract<RutaDetalle,RutaDetalleFacade> implements RutaDetalleServiceIf{

    public RutaDetalleService() throws RemoteException  {
        super(RutaDetalleFacade.class);
    }
    
}
