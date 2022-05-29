/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.TipoEstablecimientoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.ZonaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoEstablecimientoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TipoEstablecimientoService extends ServiceAbstract<TipoEstablecimiento,TipoEstablecimientoFacade> implements TipoEstablecimientoServiceIf{

    public TipoEstablecimientoService() throws RemoteException {
        super(TipoEstablecimientoFacade.class);
    }
    
    private void validar(TipoEstablecimiento tipoEstablecimientos)throws ServicioCodefacException, RemoteException
    {
        if(tipoEstablecimientos.getNombre().isEmpty())
        {
            throw new ServicioCodefacException("No se puede grabar sin nombre");
        }
        
        if(tipoEstablecimientos.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una empresa");
        }       
    }

    @Override
    public TipoEstablecimiento grabar(TipoEstablecimiento entity) throws ServicioCodefacException, RemoteException {
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
    public void editar(TipoEstablecimiento entity) throws ServicioCodefacException, RemoteException {
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
    public void eliminar(TipoEstablecimiento entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    public List<TipoEstablecimiento> obtenerActivos(OperadorNegocioEnum operadorNegocio) throws ServicioCodefacException, RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("tipo", operadorNegocio.getLetra());
        return getFacade().findByMap(mapParametros);        
    }
    
    
}
