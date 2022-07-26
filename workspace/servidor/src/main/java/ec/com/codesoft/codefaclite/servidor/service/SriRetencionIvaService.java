/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionIvaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author
 */
public class SriRetencionIvaService extends ServiceAbstract<SriRetencionIva,SriRetencionIvaFacade> implements SriRetencionIvaServiceIf{

    public SriRetencionIvaService() throws RemoteException {
        super(SriRetencionIvaFacade.class);
    }
    
    public List<SriRetencionIva> obtenerTodosOrdenadoPorCodigo() throws RemoteException
    {
        return getFacade().obtenerTodosOrdenadoPorCodigoFacade();
    }

    @Override
    public SriRetencionIva grabar(SriRetencionIva entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    @Override
    public void editar(SriRetencionIva entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(entity);
            }
        });
    }
    
    
    
    
    
}
