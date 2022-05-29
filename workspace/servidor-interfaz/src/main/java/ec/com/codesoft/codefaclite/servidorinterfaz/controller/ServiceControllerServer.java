/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.controller;


import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class ServiceControllerServer {

    private static final Logger LOG = Logger.getLogger(ServiceControllerServer.class.getName());    
    
       /**
     * Permite cargar todos los recursos que van a usar en el sistema mediante el protocolo RMI
     * @param mapRecursos envia un map con la clase de la clase de la interfaz y la que implementa
     */
    public static void cargarRecursos(Map<Class,Class> mapRecursos,String host)
    {
        try {
            Registry registro=LocateRegistry.createRegistry(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
            //String host=InetAddress.getLocalHost().getHostAddress();
            
            for (Map.Entry<Class, Class> entry : mapRecursos.entrySet()) {
                Class claseImplementacion=entry.getKey();
                LOG.log(Level.INFO,"Load RMI server: "+host+":"+ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED+":"+claseImplementacion.getName());
                UnicastRemoteObject remoteObject=(UnicastRemoteObject) claseImplementacion.newInstance();
                
                Class claseInterfaz=entry.getValue();
                
                String nombreRecurso=claseInterfaz.getName();
                
                //Lanza el recurso para que este disponible por la red
                //registro.rebind("rmi://"+host+":"+ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED+"/"+nombreRecurso,remoteObject);               
                registro.rebind(nombreRecurso,remoteObject);               
                
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
        } /*catch (UnknownHostException ex) {
            Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
}
