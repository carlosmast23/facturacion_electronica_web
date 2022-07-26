/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroPlantillaFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;


/**
 *
 * @author
 */
public class RubroPlantillaService extends ServiceAbstract<RubroPlantilla,RubroPlantillaFacade> implements RubroPlantillaServiceIf{

    public RubroPlantillaService() throws RemoteException {
        super(RubroPlantillaFacade.class);
    }

    @Override
    public RubroPlantilla grabar(RubroPlantilla entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    
    
    public void grabarConDetalles(RubroPlantilla rubroPlantilla) throws RemoteException
    {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        
        rubroPlantilla.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        List<RubroPlantillaEstudiante> detalles = rubroPlantilla.getDetalles();

        for (RubroPlantillaEstudiante detalle : detalles) {
            //Si no tiene id se debe grabar porque no eixste
            if (detalle.getId() == null) {
                entityManager.persist(detalle);
            }
        }
        
        entityManager.persist(rubroPlantilla);
        
        transaccion.commit();

    }
    
    
    public void editarConDetalles(RubroPlantilla entity,List<RubroPlantillaEstudiante> detallesEliminar) throws java.rmi.RemoteException
    {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        
        List<RubroPlantillaEstudiante> detalles=entity.getDetalles();
        
        for (RubroPlantillaEstudiante detalle : detalles) {
            //Si no tiene id se debe grabar porque no eixste
            if(detalle.getId()==null)
            {
                entityManager.persist(detalle);
            }
            else
            {
                entityManager.merge(detalle);
            }
        }
        
        //Eliminar fisicamente los detalles
        for (RubroPlantillaEstudiante detalle : detallesEliminar) {
            
            //Primero se funciona el objeto con la base antes de borrar
            detalle=entityManager.merge(detalle);
            entityManager.remove(detalle);
            detalles.remove(detalle);
            //detalles.get(0).set
            
        }
        entity.setDetalles(detalles);
        
        entityManager.merge(entity);
        
        transaccion.commit();
    }

    @Override
    public void eliminar(RubroPlantilla entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubroPlantillaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
