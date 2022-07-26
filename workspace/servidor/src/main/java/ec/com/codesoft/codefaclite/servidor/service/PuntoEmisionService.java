/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PuntoEmisionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionServiceIf;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class PuntoEmisionService extends ServiceAbstract<PuntoEmision,PuntoEmisionFacade> implements PuntoEmisionServiceIf
{
    public PuntoEmisionService() throws RemoteException {
        super(PuntoEmisionFacade.class);
    }

    @Override
    public void editar(PuntoEmision entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarPuntoEmisionSinTransaccion(entity);
                entityManager.merge(entity);
            }
        });
    }
    
    

    @Override
    public PuntoEmision grabar(PuntoEmision entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public PuntoEmision grabarSinTransaccion(PuntoEmision entity) throws ServicioCodefacException, RemoteException {
        validarPuntoEmisionSinTransaccion(entity);
        entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        entityManager.persist(entity);
        return entity;
    }
    
    /**
     * Valida si existe un punto de emision similar ya ingresado para no ingresar valores duplicados que puedan generar inconsistencias al momento de facturar
     * @param puntoEmision
     * @return
     * @throws ServicioCodefacException
     * @throws RemoteException 
     */
    private void validarPuntoEmisionSinTransaccion(PuntoEmision puntoEmision)throws ServicioCodefacException, RemoteException
    {
        puntoEmision.getSucursal();
        puntoEmision.getPuntoEmision();
        //PuntoEmisionService puntoEmisionService=new PuntoEmisionService();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("sucursal",puntoEmision.getSucursal());
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("puntoEmision",puntoEmision.getPuntoEmision());
        List<PuntoEmision> resultadoPuntoEmision=getFacade().findByMap(mapParametros);
        resultadoPuntoEmision.remove(puntoEmision);
        if(resultadoPuntoEmision.size()>0)
        {
            throw new ServicioCodefacException("Ya existe ingresado un punto de emisión con los mismos datos");
        }
        
    }
    
    @Override
    public List<PuntoEmision> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException, RemoteException {
       // ejecutarTransaccion(new MetodoInterfaceTransaccion() {
       //PuntoEmision pv;
       //pv.getS7
        return (List<PuntoEmision>) ejecutarConsulta(new MetodoInterfaceConsulta() {
           @Override
           public Object consulta() throws ServicioCodefacException, RemoteException {
               Map<String, Object> mapParametros = new HashMap<String, Object>();
               mapParametros.put("sucursal", sucursal);
               mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
               return getFacade().findByMap(mapParametros);
           }
        });
        
            
    }

    @Override
    public void eliminar(PuntoEmision entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PuntoEmisionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public PuntoEmision obtenerPorCodigo(Integer codigo,Sucursal sucursal) throws ServicioCodefacException, RemoteException {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       //PuntoEmision p;
       //p.getSucursal()
       
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("puntoEmision",codigo);
       mapParametros.put("sucursal",sucursal);
       
       List<PuntoEmision> resultados=getFacade().findByMap(mapParametros);
       if(resultados.size()>0)
       {
            return resultados.get(0);
       }
       return null;
    }
    
    @Override
    public List<PuntoEmision> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        /*PuntoEmision p;
        p.getSucursal().getEmpresa();*/
        return (List<PuntoEmision>)ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();

                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("sucursal.empresa", empresa);
                List<PuntoEmision> resultados = getFacade().findByMap(mapParametros);
                //if (resultados.size() > 0) {
                //    return resultados.get(0);
                //}
                return resultados;
            }
        });
        
    }
    
    
}
