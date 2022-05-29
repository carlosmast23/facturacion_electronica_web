/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface CatalogoProductoServiceIf extends ServiceAbstractIf<CatalogoProducto> 
{
    public List<CatalogoProducto> obtenerPorModulo(ModuloCodefacEnum modulo) throws RemoteException;
    public CatalogoProducto obtenerPorNombre(String nombre) throws RemoteException;
}
