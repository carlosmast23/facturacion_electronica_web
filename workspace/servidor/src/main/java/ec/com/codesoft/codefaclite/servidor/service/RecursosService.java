/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesZip;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class RecursosService extends UnicastRemoteObject implements RecursosServiceIf
{

    public RecursosService() throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
    }

    @Override
    public RemoteInputStream getResourceInputStream(RecursoCodefac recurso, String file) throws RemoteException {
        try {
            InputStream input= recurso.getResourceInputStream(file);
            RemoteInputStreamServer istream =new SimpleRemoteInputStream(input);
            RemoteInputStream result = istream.export();
            return result;
            //return UtilidadesRmi.serializar(ois);
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //Si falla el servidor devuelve null
    }

    public RemoteInputStream getResourceInputStreamByFile(Empresa empresa,DirectorioCodefac directorio,String nameFile) throws RemoteException
    {
        try {
            File file=new File(UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa+"/"+directorio.getNombre()+"/"+nameFile);
            InputStream input= new  FileInputStream(file);
            RemoteInputStreamServer istream =new SimpleRemoteInputStream(input);
            RemoteInputStream result = istream.export();
            return result;
            //return UtilidadesRmi.serializar(ois);
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //Si falla el servidor devuelve null        
    }
    
    public byte[] obtenerRecurso(Empresa empresa,DirectorioCodefac directorio,String nameFile) throws RemoteException
    {
        try {
            File file=new File(UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa+"/"+directorio.getNombre()+"/"+nameFile);
            InputStream input= new  FileInputStream(file);
            RemoteInputStreamServer istream =new SimpleRemoteInputStream(input);
            RemoteInputStream result = istream.export();
            return UtilidadesRmi.serializar(result);
            //return UtilidadesRmi.serializar(ois);
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //Si falla el servidor devuelve null        
    }
    
    public RemoteInputStream getDataBaseResources() throws RemoteException
    {
        try {
            String nombreBaseDatos=ParametrosSistemaCodefac.NOMBRE_BASE_DATOS;
            
            InputStream input= UtilidadesZip.comprimirToInputStream(nombreBaseDatos);
            RemoteInputStreamServer istream =new SimpleRemoteInputStream(input);
            RemoteInputStream result = istream.export();
            return result;
            //return UtilidadesRmi.serializar(ois);
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //Si falla el servidor devuelve null    
    }
    
    public void uploadFileServer(String pathServidor,DirectorioCodefac directorio,RemoteInputStream recurso,String nombre) throws RemoteException
    {
        try {
            File fileDestino=new File(pathServidor+"/"+directorio.getNombre()+"/"+nombre);
                        //crear toda la ruta si no existe
            if (!fileDestino.exists()) {
                fileDestino.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            OutputStream outputStream = new FileOutputStream(fileDestino);            
                        
            InputStream inputStream = RemoteInputStreamClient.wrap(recurso);
            
            UtilidadesArchivos.grabarInputStreamEnArchivo(inputStream, outputStream);
/*
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
*/
        } catch (IOException ex) {
            Logger.getLogger(RecursosService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Agregar recursos al servidor
     */
    public void uploadFileServer(DirectorioCodefac directorio,RemoteInputStream recurso,String nombre,Empresa empresa) throws RemoteException
    {
        //uploadFileServer(UtilidadesServidor.pathRecursos,directorio,recurso,nombre);
        uploadFileServer(UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa,directorio,recurso,nombre);
          
    }
    

}
