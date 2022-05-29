/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PuntoEmisionService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestClienteRMILimpio {

    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("13.65.185.133", ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
            System.out.println("conectando ...");
            Remote remote=registro.lookup("spirit/EmpresaSessionEJB/remote");
            //PersonaServiceIf servicio=(PersonaServiceIf) remote;
            //System.out.println(respuesta);
            //for (Persona persona : servicio.obtenerTodos()) {
            //    System.out.println(persona.getRazonSocial());
            //}
            
            System.out.println("conectado ok...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("terminado ...");
    }

}
