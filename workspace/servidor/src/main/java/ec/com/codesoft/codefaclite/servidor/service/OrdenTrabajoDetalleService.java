/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.OrdenTrabajoDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoDetalleServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoDetalleService extends ServiceAbstract<OrdenTrabajoDetalle, OrdenTrabajoDetalleFacade> implements OrdenTrabajoDetalleServiceIf
{
    public OrdenTrabajoDetalleService() throws RemoteException {
        super(OrdenTrabajoDetalleFacade.class);
    }
   
}
