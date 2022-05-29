/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.compra;

import ec.com.codesoft.codefaclite.servidor.facade.compra.OrdenCompraFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class OrdenCompraService extends ServiceAbstract<OrdenCompra, OrdenCompraFacade> implements OrdenCompraServiceIf{

    public OrdenCompraService() throws RemoteException {
        super(OrdenCompraFacade.class);
    }

    public OrdenCompra grabar(OrdenCompra entity) throws ServicioCodefacException, RemoteException {
        entityManager.getTransaction().begin(); //Inicio de la transaccion

        try {
            //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
            for (OrdenCompraDetalle compraDetalle : entity.getDetalles()) {
                if (compraDetalle.getProductoProveedor().getId() == null) {
                    entityManager.persist(compraDetalle.getProductoProveedor());
                } else {
                    entityManager.merge(compraDetalle.getProductoProveedor());
                }
            }

            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new ServicioCodefacException("Error al grabar la compra");
        }
        return entity;
    }
    
    
    
}
