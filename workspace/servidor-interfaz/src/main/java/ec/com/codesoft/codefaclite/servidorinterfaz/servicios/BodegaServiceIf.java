/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface BodegaServiceIf extends ServiceAbstractIf<Bodega>
{
    public Bodega grabar(Bodega b) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editar(Bodega b) throws ServicioCodefacException,java.rmi.RemoteException;
    
    
    public Bodega buscarPorNombre(String nombre) throws ServicioCodefacException,RemoteException;
    
    public List<Bodega> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException,RemoteException;
    public List<Bodega> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException,RemoteException;
    public Bodega obtenerBodegaVenta(Sucursal sucursal) throws ServicioCodefacException,RemoteException;
    public Bodega obtenerUnicaBodegaPorSucursal(Sucursal sucursal) throws ServicioCodefacException,RemoteException;
    public List<Bodega> obtenerTodosActivos() throws ServicioCodefacException,RemoteException;
}
