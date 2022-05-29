/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SucursalFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SucursalServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class SucursalService extends ServiceAbstract<Sucursal, SucursalFacade> implements SucursalServiceIf{

    public SucursalService() throws RemoteException {
        super(SucursalFacade.class);
    }

    @Override
    public Sucursal grabar(Sucursal entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public Sucursal grabarSinTransaccion(Sucursal entity) throws ServicioCodefacException, RemoteException {
        entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        entityManager.persist(entity);
        return entity;
    }
    
    /*public Sucursal consultarMatrizPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        //Sucursal sucursal;
        //sucursal.getEstadoEnum()        
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("tipo",Sucursal.TipoSucursalEnum.MATRIZ.getCodigo());
        mapParametros.put("empresa",empresa);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        List<Sucursal> sucursalList=getFacade().findByMap(mapParametros);
        if(sucursalList.size()>0)
        {
            return sucursalList.get(0);
        }
        
        return null;
        
    }*/
    
    @Override
    public List<Sucursal> consultarActivosPorEmpresa(Empresa empresa)  throws ServicioCodefacException, RemoteException
    {
        //Sucursal sucursal;
        //sucursal.getEmpresa();
        //sucursal.getEstado();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("empresa", empresa);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);        
    }

    @Override
    public void eliminar(Sucursal entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Sucursal obtenerPorCodigo(Integer codigo) throws ServicioCodefacException, RemoteException {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       //Sucursal sucursal;
       //sucursal.getCodigoSucursal()
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("codigoSucursal",codigo);
       List<Sucursal> sucursales=getFacade().findByMap(mapParametros);
       if(sucursales.size()>0)
       {
            return sucursales.get(0);
       }
       return null;
    }
    
    //TODO: Cambiar nombre por busca por empresa no por sucursal
    public Sucursal obtenerMatrizPorSucursal(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       //Sucursal sucursal;
       //sucursal.getT
       mapParametros.put("empresa",empresa);
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("tipo",Sucursal.TipoSucursalEnum.MATRIZ.getCodigo());
       List<Sucursal> sucursales=getFacade().findByMap(mapParametros);
       
       if(sucursales.size()>0)
       {
           return sucursales.get(0);
       }
       return null;
    }
    
    
}
