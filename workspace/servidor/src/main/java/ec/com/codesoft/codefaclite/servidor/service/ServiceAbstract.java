/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.EntityAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author
 */
public abstract class ServiceAbstract<Entity,Facade> extends UnicastRemoteObject implements Serializable
{
    private static final Integer TIEMPO_ESPERA_TRANSACCION=1000;
    private static final Integer INTENTOS_ESPERA_TRANSACCION=5;

    //private static final Logger LOG = Logger.getLogger(ServiceAbstract.class.getName());
    
    
    protected AbstractFacade<Entity> facade;
    protected EntityManager entityManager;
    
    /**
     * TODO: Variable temporal solo para hacer aritificios para las clases internas
     */
    public Object tmp;

    public ServiceAbstract() throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        this.entityManager=AbstractFacade.entityManager;
    }
    
    /**
     * Obtiene un transaccion para trabar con el entity manager
     */
    public EntityTransaction getTransaccion()
    {
        return entityManager.getTransaction();
    }
 
    public ServiceAbstract(Class<Facade> clase) throws java.rmi.RemoteException
    {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        try {
            this.facade =(AbstractFacade<Entity>) clase.newInstance();
            this.entityManager=AbstractFacade.entityManager;
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //private T t;
    public List<Entity> obtenerTodos()
    {
        try {
            return (List<Entity>) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    return facade.findAll();
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Entity grabar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.persist(entity);
            }
        });
        return entity;
    }
   
    public Entity buscarPorId(Object primaryKey) throws java.rmi.RemoteException
    {
        try {
            return (Entity) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    return facade.find(primaryKey);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public void editar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(entity);
            }
        });
    }
    
    public void eliminar(Entity entity) throws ServicioCodefacException, java.rmi.RemoteException 
    {
        this.facade.remove(entity);
    }
    
    public List<Entity> obtenerPorMap(Map<String,Object> parametros) throws ServicioCodefacException, java.rmi.RemoteException 
    {
        return this.facade.findByMap(parametros);
    }
    
    protected Object ejecutarConsulta(MetodoInterfaceConsulta interfaz) throws ServicioCodefacException
    {
        try {
            return interfaz.consulta();
        } catch (RemoteException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new ServicioCodefacException("Error de conexión con el servidor");
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
            throw ex;
        }catch (PersistenceException ex) { //Hacer un RoolBack cuando es un error relacionado con la persistencia
            //ex.printStackTrace();
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            //Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                String mensaje="sin mensaje";
                if(ex!=null)
                   mensaje=ex.getMessage();
                    
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje()+"\n Causa: "+ mensaje);
            }  
        }catch(Exception e)
        {
            //e.printStackTrace();
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, e);
            //LOG.log(Level.SEVERE,e.getMessage()); //Todo: Mejorar esta parte porque deberia imprimir toda la pila de error y ademas deberia poder comunicar el error a la capa superior
            throw new ServicioCodefacException(e.getMessage());
        }
        
    }
    
    protected Object ejecutarTransaccionConResultado(MetodoInterfaceTransaccionResultado interfaz) throws ServicioCodefacException
    {
        return ejecutarTransaccionGenerico(new TransaccionIf() {
            @Override
            public Object transaccionGenerica() throws ServicioCodefacException, RemoteException {
                return interfaz.transaccion();
            }
        });
    }
    
    
    /**
     * Metodo Que me permite ejecutar un conjunto de procesos en el jpa como un proceso
     * @param interfaz
     * @throws PersistenceException 
     */
    protected void ejecutarTransaccion(MetodoInterfaceTransaccion interfaz) throws ServicioCodefacException
    {
        ejecutarTransaccionGenerico(new TransaccionIf() {
            @Override
            public Object transaccionGenerica() throws ServicioCodefacException, RemoteException {
                interfaz.transaccion();
                return null;
            }
        });
    }
    
    private Object ejecutarTransaccionGenerico(TransaccionIf interfaz) throws ServicioCodefacException
    {
        
        EntityTransaction transaccion = entityManager.getTransaction();
        Object resultadoTransaccion=null;
        
        //No se puede iniciar una nueva transaccion si previamente existe otra
       //TODO: En teoria deberia ver una forma de tener varios entity manager sincronizados y no como en este caso que solo permite una transacción a la vez
       //TODO: Se puede optimizar un tiempo de espera antes de lanzar el mensaje previamente escrito
       
       //Si existe una transaccion procesando esperar un tiempo para ver si se libera y procesar la siguiente
       for (int i = 0; i < INTENTOS_ESPERA_TRANSACCION; i++) 
       {
            if(!transaccion.isActive())
            {
                break;
            }
           
            try {
                Thread.sleep(TIEMPO_ESPERA_TRANSACCION);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       }
       
       
       if (transaccion.isActive()) 
       {
           //TODO: Solución de prueba temporal
           transaccion.commit();//Si por algun motivo queda una transaccion activa , lo que primero que intento es grabar la transacción
           
           if(transaccion.isActive())
           {           
                throw new ServicioCodefacException("Previamente existe una transacción activa , por favor intente nuevamente");
           }
       }
       
       
        
        try {        
            
            transaccion.begin();
            resultadoTransaccion=interfaz.transaccionGenerica();
            transaccion.commit();
            //transaccion.setRollbackOnly();
            //entityManager.clear(); //Supuestamente esto no es neceario y solo se estaba usando supuestamente para errores de cache
        }catch (RemoteException ex) { //Hacer un RoolBack cuando no exista comunicacion con el servidor
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            //ex.printStackTrace();
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error de conexión con el servidor");
        }catch (ServicioCodefacException ex) { //Hacer un RoolBack cuando sea un error personalizado
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            //ex.printStackTrace();
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }catch (PersistenceException ex) { //Hacer un RoolBack cuando es un error relacionado con la persistencia
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
            //verifica que la transaccion esta activa para hacer un rollback
            //Nota: Algunas veces el commit automaticamente hace un rollback es decir no es necesario hacer rollback y la sesion ya no esta activa
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            //Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                String mensaje="sin mensaje";
                if(ex!=null)
                   mensaje=ex.getMessage();
                    
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje()+"\n Causa: "+ mensaje);
            }  
            
            
        }catch(NullPointerException e)
        {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
            throw new ServicioCodefacException("Problema con variable nula \n\n"+e.getMessage());
        }catch(Exception e) //Hacer un RollBack si se produce cualquier error
        {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            //e.printStackTrace();
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, e);
            //LOG.log(Level.SEVERE,e.getMessage()); //Todo: Mejorar esta parte porque deberia imprimir toda la pila de error y ademas deberia poder comunicar el error a la capa superior
            throw new ServicioCodefacException(e.getMessage());
            //throw e;
        }
        return resultadoTransaccion;
        
    }

    protected Facade getFacade()
    {
        return (Facade) this.facade;
    }

    /**
     * Metodo que se encarga de desasoriar una entidad gestionada para poder hacer acciones
     * sobre el objecto pero que no se reflejen en la persistencia con la base de datoss
     */    
    public static void desasociarEntidadPersistencia(Object entidad)
    {
        AbstractFacade.detachEntity(entidad);
    }
    
    /**
     * Metodo recursivo que se encarga de desasoriar una entidad gestionada para poder
     * hacer acciones sobre el objecto pero que no se reflejen en la
     * persistencia con la base de datoss
     */   
    public static void desasociarEntidadRecursivo(Object entidad)
    {
        AbstractFacade.detachRecursive(entidad);
    }    
    
    public interface  TransaccionIf
    {
        public Object transaccionGenerica() throws ServicioCodefacException,RemoteException;
    }
    
    
    public void setDatosAuditoria(EntityAbstract entidad,Usuario usuario,CrudEnum crudEnum) throws ServicioCodefacException
    {
        entidad.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
        entidad.setFechaUltimaEdicion(UtilidadesFecha.getFechaHoyTimeStamp());        
                
        if (entidad.getEstado() == null) 
        {
            throw new ServicioCodefacException("No se puede grabar un registro sin Estado");
        }
        
        if(crudEnum==CrudEnum.CREAR)
        {
            entidad.setUsuarioCreacion(usuario);
            if(entidad.getUsuarioCreacion()==null)
            {
                throw new ServicioCodefacException("No se puede grabar un registro sin USUARIO DE CREACION");
            }            
        }
        
        if(crudEnum==CrudEnum.EDITAR)
        {
            entidad.setUsuarioUltimaEdicion(usuario);
            if(entidad.getUsuarioUltimaEdicion()==null)
            {
                throw new ServicioCodefacException("No se puede editar un registro sin USUARIO DE EDICION");
            }
        }
    }
}
