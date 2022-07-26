/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;

/**
 *
 * @author
 */
public interface FacturaDetalleServiceIf extends ServiceAbstractIf<FacturaDetalle> {
    public Object getReferenciaDetalle(FacturaDetalle facturaDetalle) throws ServicioCodefacException,java.rmi.RemoteException;
}
