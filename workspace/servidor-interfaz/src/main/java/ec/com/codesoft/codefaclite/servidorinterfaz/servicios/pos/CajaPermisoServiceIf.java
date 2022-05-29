/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Robert
 */
public interface CajaPermisoServiceIf extends ServiceAbstractIf<CajaPermiso>
{
    public List<Usuario> buscarUsuariosPorSucursalYLigadosACaja(Sucursal sucursal, Caja caja) throws RemoteException;
    public List<CajaPermiso> obtenerTodasCajasPorUsuario(Usuario usuario, PuntoEmision puntoEmision) throws RemoteException;
    public List<CajaPermiso> buscarPermisosCajasActivos(Usuario usuario) throws ServicioCodefacException,java.rmi.RemoteException;
}
