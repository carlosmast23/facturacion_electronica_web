/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.PrestamoTablaInteresFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoTablaInteres;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoTablaInteresServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 *
 * @author
 */
public class PrestamoTablaInteresService extends ServiceAbstract<PrestamoTablaInteres,PrestamoTablaInteresFacade> implements PrestamoTablaInteresServiceIf
{

    public PrestamoTablaInteresService() throws RemoteException {
        super(PrestamoTablaInteresFacade.class);
    }

    @Override
    public PrestamoTablaInteres grabar(PrestamoTablaInteres entity) throws ServicioCodefacException, RemoteException {
        
        validar(entity);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entity.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    @Override
    public void editar(PrestamoTablaInteres entity) throws ServicioCodefacException, RemoteException {
        validar(entity);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //entity.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                entityManager.merge(entity);
            }
        });
        
    }

    @Override
    public void eliminar(PrestamoTablaInteres entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    
    
    
    /**
     * TODO: Ver si se puede hacer una funcion global en los metodos para validar cuando sean nulos
     * @param entity
     * @throws ServicioCodefacException
     * @throws RemoteException 
     */
    private void validar(PrestamoTablaInteres entity) throws ServicioCodefacException, RemoteException
    {
        if(entity.getMeses()==null)
        {
            throw new ServicioCodefacException("El campo de los meses es obligatorio");
        }
        
        if(entity.getMeses()<=0)
        {
            throw new ServicioCodefacException("El campo de los meses tiene que ser un valor positivo");
        }
        
        if(entity.getPorcentaje()==null)
        {
            throw new ServicioCodefacException("El campo del porcentaje es obligatorio");
        }
        
        if(entity.getPorcentaje().compareTo(BigDecimal.ZERO)<=0)
        {
            throw new ServicioCodefacException("El campo del porcentaje tiene que ser positivo");
        }
        
        if(entity.getSucursal()==null)
        {
            throw new ServicioCodefacException("Ingrese un establecimiento es obligatorio");
        }
        
        
        
    }
    
    
}
