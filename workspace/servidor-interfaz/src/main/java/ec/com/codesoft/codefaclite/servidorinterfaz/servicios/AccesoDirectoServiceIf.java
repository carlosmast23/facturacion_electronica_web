/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.AccesoDirecto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface AccesoDirectoServiceIf extends ServiceAbstractIf<AccesoDirecto>
{
    public AccesoDirecto buscarPorNombre(String nombre)  throws RemoteException ,ServicioCodefacException;
}
