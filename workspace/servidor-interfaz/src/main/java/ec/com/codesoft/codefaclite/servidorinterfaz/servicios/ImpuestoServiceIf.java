/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;
/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ImpuestoServiceIf extends ServiceAbstractIf<Impuesto>
{
    public Impuesto grabar(Impuesto i) throws ServicioCodefacException,java.rmi.RemoteException;;
    public void editar(Impuesto i) throws java.rmi.RemoteException;;
    public void eliminar(Impuesto i) throws java.rmi.RemoteException;;
    public Impuesto obtenerImpuestoPorCodigo(String nombre) throws java.rmi.RemoteException;;
    public Impuesto obtenerImpuestoPorVigencia(String nombre) throws java.rmi.RemoteException;;
    public List<Impuesto> obtenerTodos() throws java.rmi.RemoteException;;
    public List<ImpuestoDetalle> obtenerDetalle() throws java.rmi.RemoteException;;
}
