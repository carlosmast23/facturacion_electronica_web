/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ImpuestoDetalleServiceIf extends ServiceAbstractIf<ImpuestoDetalle>
{
    //public void grabar(ImpuestoDetalle i) throws ServicioCodefacException,java.rmi.RemoteException;
    //public void editar(ImpuestoDetalle i) throws java.rmi.RemoteException;
    public void eliminar(ImpuestoDetalle i) throws java.rmi.RemoteException;
    public List<ImpuestoDetalle> buscarImpuestoDetallePorMap(Map<String,Object> map) throws java.rmi.RemoteException;
    public List<ImpuestoDetalle> obtenerIvaVigente()throws java.rmi.RemoteException;
    public ImpuestoDetalle buscarPorTarifa(Integer tarifa) throws ServicioCodefacException,java.rmi.RemoteException;
    public Map<Integer,ImpuestoDetalle> obtenerTodosMap() throws java.rmi.RemoteException,ServicioCodefacException;
    public ImpuestoDetalle buscarPorCodigo(Integer codigo) throws ServicioCodefacException,java.rmi.RemoteException;
}
