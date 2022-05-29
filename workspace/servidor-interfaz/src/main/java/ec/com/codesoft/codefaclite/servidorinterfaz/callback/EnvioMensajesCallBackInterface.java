/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.callback;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public interface EnvioMensajesCallBackInterface extends Remote {
    public void procesando(Integer porcentaje) throws RemoteException;
}
