/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ProductoServiceIf extends ServiceAbstractIf<Producto> {

       
    public Producto grabar(Producto p,Boolean generarCodigo) throws RemoteException, ServicioCodefacException;
    
    public void editarProducto(Producto p) throws java.rmi.RemoteException,ServicioCodefacException;
    
    //public void eliminar(Producto p) throws java.rmi.RemoteException;
    
    public List<Producto> buscar(Empresa empresa) throws java.rmi.RemoteException;
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum,Empresa empresa) throws RemoteException;
    
    public Producto buscarProductoActivoPorCodigo(String codigo,Empresa empresa) throws ServicioCodefacException,RemoteException;
    
    public List<Producto> obtenerTodosActivos(Empresa empresa) throws java.rmi.RemoteException;
    
    public Producto buscarGenerarCodigoBarras(EnumSiNo enumSiNo,Empresa empresa ) throws ServicioCodefacException,RemoteException;
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public void eliminarProducto(Producto p) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public List<Producto> buscarProductoActivo(Producto producto,Empresa empresa) throws ServicioCodefacException,RemoteException;
}
