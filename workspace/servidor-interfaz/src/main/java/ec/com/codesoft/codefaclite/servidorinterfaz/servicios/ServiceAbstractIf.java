/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Carlos
 * @param <Entity>
 */
public interface ServiceAbstractIf<Entity> extends Remote {
        //private T t;
    public List<Entity> obtenerTodos() throws java.rmi.RemoteException;
    
    public Entity grabar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public void editar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public void eliminar(Entity entity) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public Entity buscarPorId(Object primaryKey) throws java.rmi.RemoteException;
    
    //public List<Entity> obtenerPorMap(Map<String,Object> parametros) throws java.rmi.RemoteException,ServicioCodefacException;
}
