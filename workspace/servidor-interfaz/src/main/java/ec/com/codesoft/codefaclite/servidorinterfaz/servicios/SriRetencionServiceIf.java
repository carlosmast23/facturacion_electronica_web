/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public interface SriRetencionServiceIf extends ServiceAbstractIf<SriRetencion>{
    public SriRetencion consultarPorNombre(String nombre) throws RemoteException;
}
