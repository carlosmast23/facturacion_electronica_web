/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class TestClienteRMI {
    public static void main(String[] args) {
        try {
            ServiceFactory.newController("192.168.1.7");
            //ServiceFactory.newController("186.4.212.15");
            //ServiceFactory.newController("192.168.100.13");
            
            PersonaServiceIf personaServiceIf= ServiceFactory.getFactory().getPersonaServiceIf();
            List<Persona> buscarList= personaServiceIf.buscar();
            
            for (Persona persona : buscarList) {
               System.out.println(persona.getNombres()); 
               System.out.println(persona.getIdentificacion());
            }
            
            /*
            try {
            Registry registro = LocateRegistry.getRegistry("192.168.1.3", 1099);
            PersonaServiceIf interfaz = (PersonaServiceIf) registro.lookup("rmi://192.168.1.3:1099/RMIBD");
            List<Persona> personas= interfaz.buscar();
            for (Persona persona : personas) {
            System.out.println(persona.getIdentificacion());
            }
            
            Thread.sleep(5000);
            
            personas= interfaz.buscar();
            for (Persona persona : personas) {
            System.out.println(persona.getIdentificacion());
            }
            
            } catch (RemoteException ex) {
            Logger.getLogger(TestClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
            Logger.getLogger(TestClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
            Logger.getLogger(TestClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
        } catch (RemoteException ex) {
            Logger.getLogger(TestClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
