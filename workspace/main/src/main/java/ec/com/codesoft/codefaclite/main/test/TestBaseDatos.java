/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author
 */
public class TestBaseDatos {
    public static void main(String[] args) {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "1234";
            AbstractFacade.cargarEntityManager();
            
            EntityManager em= AbstractFacade.entityManager;
            EntityTransaction et= em.getTransaction();
            
            
//            ComprobanteFisicoDisenioService servicio = new ComprobanteFisicoDisenioService();
//            ComprobanteFisicoDisenio c2=servicio.obtenerTodos().get(0);
//            AbstractFacade.detachRecursive(c2);
            
//            PersonaService servicio = new PersonaService();
//            List <Persona> personas = servicio.obtenerTodos();
//            for (Persona persona : personas) {
//                System.out.println(" - " + persona);
//            }
            OrdenTrabajoService service = new OrdenTrabajoService();
            OrdenTrabajo ordenTrabajo = (OrdenTrabajo) service.obtenerTodos().get(0);
            System.out.println("Número de ordenes de trabajo: " + ordenTrabajo.getDetalles().size()); 
            PresupuestoService servicio1 = new PresupuestoService();
            List <OrdenTrabajoDetalle> ordenesTrabajoDetalles = servicio1.listarOrdenesTrabajo(ordenTrabajo);
            for (OrdenTrabajoDetalle ordenTrabajoDetalle : ordenesTrabajoDetalles) {
                System.out.println("-> " + ordenTrabajo.toString());
            }
                    
            
            /*
            //et.begin();
            //Usuario usuario=em.find(Usuario.class,"admin");
            //usuario.setClave("12345");
            //System.out.println(usuario);em.detach(et);            
            ComprobanteFisicoDisenioService servicio = new ComprobanteFisicoDisenioService();
            List<ComprobanteFisicoDisenio> documentos = servicio.obtenerTodos();
            ComprobanteFisicoDisenio comprobante=documentos.get(0);
            comprobante.setNombre("vvvvv");
            ServiceAbstract.desasociarEntidadPersistencia(comprobante);
            //em.detach(comprobante);
            
            ComprobanteFisicoDisenio c2=servicio.obtenerTodos().get(0);
            System.out.println(c2.getNombre());
            //em.merge(comprobante);
            //et.commit();
            
            et= em.getTransaction();

            //et.commit();
            et.begin();
            Usuario usuario=em.find(Usuario.class,"admin");
            usuario.setClave("12345");
            et.commit();
            //System.out.println(usuario);*/
            em.close();
            System.exit(0);
            
        } catch (PersistenceException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
