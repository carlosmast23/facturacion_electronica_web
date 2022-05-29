/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface NacionalidadServiceIf extends ServiceAbstractIf<Nacionalidad>{
     public Nacionalidad obtenerDefaultEcuador() throws ServicioCodefacException,java.rmi.RemoteException;
}
