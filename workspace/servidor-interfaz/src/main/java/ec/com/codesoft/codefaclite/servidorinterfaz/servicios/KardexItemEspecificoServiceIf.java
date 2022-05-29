/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface KardexItemEspecificoServiceIf extends ServiceAbstractIf<KardexItemEspecifico>
{
    public int obtenerCantidadItemsEspecificosPorKardex(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<KardexItemEspecifico> obtenerItemsEspecificosPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException;
}
