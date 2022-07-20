/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @auhor
 */
public interface CajaSesionServiceIf extends ServiceAbstractIf<CajaSession>
{
    public boolean buscarSiCajaTieneSessionActiva(Caja caja) throws RemoteException;
    public CajaSession obtenerUltimaCajaSession(Caja caja) throws RemoteException;
    public CajaSession obtenerCajaSessionPorPuntoEmisionYUsuario(Integer puntoEmision, Usuario usuario) throws RemoteException;
    public List<CajaSession> obtenerCajaSessionPorCajaYUsuario(Caja caja, Usuario usuario) throws RemoteException;
    public List<CajaSession> obtenerCajaSessionPorCajaUsuarioYFecha(Caja caja, Usuario usuario, Date fechaInicio,Date fechaFin) throws RemoteException;
    public List<CajaSession> obtenerCajaSessionPorUsuarioYSucursal(Usuario usuario, Sucursal sucursal) throws RemoteException;
    
    public List<CajaSession> obtenerCajaSessionPorUsuario(Usuario usuario) throws RemoteException;
}
