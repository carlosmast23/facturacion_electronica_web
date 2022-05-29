/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.facade.OrdenTrabajoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoService extends ServiceAbstract<OrdenTrabajo, OrdenTrabajoFacade> implements OrdenTrabajoServiceIf {

    OrdenTrabajoFacade ordenTrabajoFacade;

    public OrdenTrabajoService() throws RemoteException {
        super(OrdenTrabajoFacade.class);
        this.ordenTrabajoFacade = new OrdenTrabajoFacade();
    }

    @Override
    public List<OrdenTrabajo> consultaDialogo(String param, int limiteMinimo, int limiteMaximo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabarOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, RemoteException {
        try {
            entityManager.getTransaction().begin(); //Inicio de la transaccion
            entityManager.persist(ordenTrabajo);
            entityManager.getTransaction().commit();
        } catch (Exception exc) {
            exc.addSuppressed(exc);
        }
    }

    @Override
    public List<OrdenTrabajo> obtenerTodos() {
        return ordenTrabajoFacade.findAll();
    }

    @Override
    public void editar(OrdenTrabajo ordenTrabajo) {
        ordenTrabajoFacade.edit(ordenTrabajo);
    }

    @Override
    public void eliminar(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.ELIMINADO);
                entityManager.merge(ordenTrabajo);
            }
        });

    }

    @Override
    public List<OrdenTrabajoDetalle> consultarReporte(Date fechaInicial, Date fechaFinal, Departamento departamento, Empleado empleado, OrdenTrabajoDetalle.EstadoEnum estado) throws RemoteException {
        return getFacade().consultaReporteFacade(fechaInicial, fechaFinal, departamento, empleado, estado);
    }

    @Override
    public OrdenTrabajo grabar(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, java.rmi.RemoteException {

        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                OrdenTrabajo.EstadoEnum estadoEnum = OrdenTrabajo.EstadoEnum.GENERADO;
                ordenTrabajo.setEstadoEnum(estadoEnum);

                for (OrdenTrabajoDetalle ordenTrabajoDetalle : ordenTrabajo.getDetalles()) {
                    /**
                     * Agregar estado por defecto a detalle orden trabajo
                     */
                    ordenTrabajoDetalle.setEstadoEnum(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO);
                }
                entityManager.persist(ordenTrabajo);

                SmsService smsService = new SmsService();
                for (OrdenTrabajoDetalle detalle : ordenTrabajo.getDetalles()) {
                    if (detalle.getEmpleado() != null) {
                        if (detalle.getEmpleado().getTelefonoCelular() != null && !detalle.getEmpleado().getTelefonoCelular().equals("")) {
                            try
                            {
                                smsService.enviarMensaje(detalle.getEmpleado().getTelefonoCelular(), "Nueva orden " + ordenTrabajo.getId() + "," + detalle.getTitulo() + ", Cliente:" + ordenTrabajo.getCliente().getNombreSimple());
                            }catch(ServicioCodefacException se)
                            {
                                se.printStackTrace();
                            }
                        }

                    }
                }

            }
           
        });
        return ordenTrabajo;
    }
    
    /**
     * Metodo que me permite establecer el estado de la orden dea trabajo segun el estado de los detalles
     * @param ordenTrabajo 
     */
    public void actualizarEstadoSinTransaccion(OrdenTrabajo ordenTrabajo)
    {
        //OrdenTrabajo.EstadoEnum estadoEnum=OrdenTrabajo.EstadoEnum.GENERADO;
        
        for (OrdenTrabajoDetalle detalleOrdenTrabajo : ordenTrabajo.getDetalles()) 
        {
            if(detalleOrdenTrabajo.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO) || detalleOrdenTrabajo.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO))
            {
                ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.GENERADO);
                entityManager.merge(ordenTrabajo);
                return ;
            }
        }
        ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.FINALIZADO);
        entityManager.merge(ordenTrabajo);
        
    }
}
