/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @auhor
 */
public interface TipoEstablecimientoServiceIf extends ServiceAbstractIf<TipoEstablecimiento> {
    public List<TipoEstablecimiento> obtenerActivos(OperadorNegocioEnum operadorNegocio) throws ServicioCodefacException, RemoteException;
}
