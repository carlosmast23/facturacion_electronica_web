/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface TransportistaServiceIf extends ServiceAbstractIf<Transportista>
{
    public Transportista grabar(Transportista t) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editar(Transportista t) throws java.rmi.RemoteException;
    public void eliminar(Transportista t) throws java.rmi.RemoteException;
}
