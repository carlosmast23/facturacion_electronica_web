/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import com.healthmarketscience.rmiio.RemoteInputStream;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface RecursosServiceIf extends Remote {
    //TODO: Genera problemas con conexiones externas
    @Deprecated
    public RemoteInputStream getResourceInputStream(RecursoCodefac recurso,String file) throws RemoteException;
    
    //TODO: Genera problemas con conexiones externas
    @Deprecated
    public RemoteInputStream getResourceInputStreamByFile(Empresa empresa,DirectorioCodefac directorio,String nameFile) throws RemoteException;
    
    //TODO: Genera problemas con conexiones externas
    @Deprecated
    public RemoteInputStream getDataBaseResources() throws RemoteException;
    
    public void uploadFileServer(DirectorioCodefac directorio,RemoteInputStream recurso,String nombre,Empresa empresa) throws RemoteException;
    public void uploadFileServer(String pathServidor,DirectorioCodefac directorio,RemoteInputStream recurso,String nombre) throws RemoteException;
    
    public byte[] obtenerRecurso(Empresa empresa,DirectorioCodefac directorio,String nameFile) throws RemoteException;
}
