/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoService extends ServiceAbstract<Presupuesto, PresupuestoFacade> implements PresupuestoServiceIf
{
    private PresupuestoFacade presupuestoFacade;

    public PresupuestoService() throws RemoteException 
    {
        super(PresupuestoFacade.class);
        this.presupuestoFacade = new PresupuestoFacade();
    }
    
    public Presupuesto grabar(Presupuesto entity) throws ServicioCodefacException
    {
        
        /*if(entity.getPresupuestoDetalles()==null || entity.getPresupuestoDetalles().size()==0)
        {
            throw new ServicioCodefacException("Error al grabar, no existen datos en el detalle");
        }*/
        
        if(entity.getTotalVenta()==null || entity.getTotalVenta().compareTo(BigDecimal.ZERO)==0)
        {
            throw new ServicioCodefacException("Error al grabar, el total del presupuesto no puede ser 0");
        }
        
        EntityTransaction transaccion=entityManager.getTransaction();
        try {
            transaccion.begin();
            /**
             * Cambiar el estado del detalle de la Orden de trabajo
             */
            entity.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO.getLetra());
            
            /**
             * Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
             */ 
            if(entity.getPresupuestoDetalles()!=null)
            {
                for (PresupuestoDetalle presupuestoDetalle : entity.getPresupuestoDetalles()) {
                    if (presupuestoDetalle.getProductoProveedor().getId() == null) {
                        entityManager.persist(presupuestoDetalle.getProductoProveedor());
                    } else {
                        entityManager.merge(presupuestoDetalle.getProductoProveedor());
                    }
                }
            }

            entityManager.persist(entity);
            transaccion.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaccion.rollback();
            throw new ServicioCodefacException("Error al grabar la compra");
        }
        return entity;
    }
    
    public void editar(Presupuesto p)
    {
        presupuestoFacade.edit(p);
    }
    
    public void eliminar(Presupuesto p) throws ServicioCodefacException,RemoteException
    {
        if(p.getEstadoEnum().equals(Presupuesto.EstadoEnum.FACTURADO))
        {
            throw new ServicioCodefacException("No se puede eliminar un presupuesto facturado");
        }
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                p.setEstadoEnum(Presupuesto.EstadoEnum.ANULADO);
                entityManager.merge(p);
            }
        });
        
    }
    
    public List<Presupuesto> buscar()
    {
        return presupuestoFacade.findAll();
    }
    
    public List<OrdenTrabajoDetalle> listarOrdenesTrabajo(OrdenTrabajo ordenTrabajo)
    {
        return presupuestoFacade.listarOrdenTrabajo(ordenTrabajo);
    }
    
    public List<Presupuesto> consultarPresupuestos(Date fechaInicial, Date fechaFinal,Persona cliente,Presupuesto.EstadoEnum estadoEnum) throws ServicioCodefacException,java.rmi.RemoteException
    {
        return getFacade().consultarPresupuestos(fechaInicial, fechaFinal, cliente, estadoEnum);
    }
    
    
    
}
