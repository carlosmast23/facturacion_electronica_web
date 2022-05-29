/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ProductoEnsambleServiceIf extends ServiceAbstractIf<ProductoEnsamble>
{
    public List<ProductoEnsamble> buscarPorProducto(Producto producto) throws ServicioCodefacException, java.rmi.RemoteException;
}
