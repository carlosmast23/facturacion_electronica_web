/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public interface MetodoInterfaceTransaccion {
    public void transaccion() throws ServicioCodefacException,RemoteException;
}
