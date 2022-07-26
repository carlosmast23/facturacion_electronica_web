/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author
 */ 
public interface PuntoEmisionUsuarioServiceIf extends ServiceAbstractIf<PuntoEmisionUsuario> {
    public List<PuntoEmisionUsuario> obtenerActivoPorUsuario(Usuario usuario,Sucursal sucursal) throws ServicioCodefacException, RemoteException;
    public List<PuntoEmisionUsuario> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException, RemoteException;
    public List<PuntoEmision> obtenerActivosPorSucursalCastPuntoEmision(Usuario usuario,Sucursal sucursal) throws ServicioCodefacException, RemoteException;
}
