/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;

/**
 *
 * @author DellWin10
 */
public interface TipoProductoServiceIf extends ServiceAbstractIf<TipoProducto> {
    
    public TipoProducto grabar(TipoProducto entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public TipoProducto editar(TipoProducto entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public void eliminar(TipoProducto entity) throws ServicioCodefacException, RemoteException ;
    
}
