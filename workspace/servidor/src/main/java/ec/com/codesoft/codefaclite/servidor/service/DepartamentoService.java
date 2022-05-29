/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DepartamentoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class DepartamentoService extends ServiceAbstract<Departamento, DepartamentoFacade> implements DepartamentoServiceIf
{
    private DepartamentoFacade departamentoFacade;
    
    public DepartamentoService() throws RemoteException
    {
        super(DepartamentoFacade.class);
        this.departamentoFacade = new DepartamentoFacade();
    }
    
    /*
    @Override
     public Departamento grabar(Departamento d) throws ServicioCodefacException {
        try {
            departamentoFacade.create(d);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(DepartamentoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }*/
    public List<Departamento> obtenerActivos() throws ServicioCodefacException,RemoteException
    {
        //Departamento d;
        //d.getEstado();        
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
        
        
    }

    @Override
    public void editar(Departamento d) throws ServicioCodefacException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(d);
            }
        });
    }

    @Override
    public void eliminar(Departamento d) throws ServicioCodefacException {
  
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                d.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                entityManager.merge(d);
            }
        });
        
    }

    public List<Departamento> buscar()
    {
        return departamentoFacade.findAll();
    } 
}
