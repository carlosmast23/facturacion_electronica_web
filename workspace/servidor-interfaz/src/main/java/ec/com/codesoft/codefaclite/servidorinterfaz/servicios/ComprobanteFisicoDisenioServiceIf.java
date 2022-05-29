/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ComprobanteFisicoDisenioServiceIf extends ServiceAbstractIf<ComprobanteFisicoDisenio>
{
    public ComprobanteFisicoDisenio buscarPorCodigoDocumento(String codigo) throws ServicioCodefacException,java.rmi.RemoteException;
}
