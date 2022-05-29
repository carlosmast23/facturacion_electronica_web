/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface SriIdentificacionServiceIf extends ServiceAbstractIf<SriIdentificacion>
{
    public SriIdentificacion obtenerPorTransaccionEIdentificacion(Persona.TipoIdentificacionEnum tipoIdentificacion,SriIdentificacion.tipoTransaccionEnum tipoTransaccion) throws java.rmi.RemoteException;
    public SriIdentificacion buscarPorCodigo(String codigo) throws ServicioCodefacException,java.rmi.RemoteException;
}
