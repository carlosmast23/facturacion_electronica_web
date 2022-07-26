/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.DetalleProductoGuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DetalleProductoGuiaRemisionServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public class DetalleProductoGuiaRemisionService extends ServiceAbstract<DetalleProductoGuiaRemision,DetalleProductoGuiaRemisionFacade> implements DetalleProductoGuiaRemisionServiceIf{

    public DetalleProductoGuiaRemisionService() throws RemoteException {
        super(DetalleProductoGuiaRemisionFacade.class);
    }
    
}
