/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ZonaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ZonaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ZonaService extends ServiceAbstract<Zona,ZonaFacade> implements ZonaServiceIf {

    public ZonaService() throws RemoteException {
        super(ZonaFacade.class);
    }   

    @Override
    public Zona grabar(Zona entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                validar(entity);
                
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
                
            }
        });
        return entity;
    }

    @Override
    public void editar(Zona entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validar(entity);
                
                //entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.merge(entity);
            }
        });
    }

    @Override
    public void eliminar(Zona entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
        
    }
    
        
    
    private void validar(Zona zona)throws ServicioCodefacException, RemoteException
    {
        if(zona.getNombre().isEmpty())
        {
            throw new ServicioCodefacException("No se puede grabar sin nombre");
        }
        
        if(zona.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una empresa");
        }       
    }
    
    public List<Zona> obtenerActivos() throws ServicioCodefacException, RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
        
    }
    
}
