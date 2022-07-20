/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @auhor
 */
public interface CajaServiceIf extends ServiceAbstractIf<Caja>{
    public List<Caja> buscarCajasAutorizadasPorUsuario(Usuario usuario) throws RemoteException;
    public List<Caja> buscarCajasPorSucursal(SessionCodefacInterface session) throws RemoteException;
}
