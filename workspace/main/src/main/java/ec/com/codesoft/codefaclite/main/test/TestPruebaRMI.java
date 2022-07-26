/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

//import ec.com.codesoft.codefaclite.servicios.controller.ControllerServiceUtil;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.AccesoDirectoService;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.CategoriaProductoService;
import ec.com.codesoft.codefaclite.servidor.service.CompraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.CompraService;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.KardexDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.KardexItemEspecificoService;
import ec.com.codesoft.codefaclite.servidor.service.NotaCreditoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoEnsambleService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoProveedorService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidor.service.SriIdentificacionService;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
//import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceControllerServer;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import java.util.Scanner;

/**
 *
 * @author
 */
public class TestPruebaRMI {
    public static void main(String[] args) {
        try {
            //try {
            AbstractFacade.usuarioDb="root";
            AbstractFacade.claveDb="1234";
            AbstractFacade.cargarEntityManager();
            //PersonaServiceIf servicioIf;
            /*
            Map<Class,Class> mapRecursos=new HashMap<Class, Class>();
            
            mapRecursos.put(ProductoService.class,ProductoServiceIf.class);
            mapRecursos.put(PersonaService.class,PersonaServiceIf.class);
            mapRecursos.put(AccesoDirectoService.class,AccesoDirectoServiceIf.class);
            mapRecursos.put(BodegaService.class,BodegaServiceIf.class);
            mapRecursos.put(CategoriaProductoService.class,CategoriaProductoServiceIf.class);
            mapRecursos.put(CompraDetalleService.class,CompraDetalleServiceIf.class);
            mapRecursos.put(CompraService.class,CompraServiceIf.class);
            mapRecursos.put(ComprobanteFisicoDisenioService.class,ComprobanteFisicoDisenioServiceIf.class);
            mapRecursos.put(EmpresaService.class,EmpresaServiceIf.class);
            mapRecursos.put(FacturacionService.class,FacturacionServiceIf.class);
            mapRecursos.put(ImpuestoDetalleService.class,ImpuestoDetalleServiceIf.class);
            mapRecursos.put(ImpuestoService.class,ImpuestoServiceIf.class);
            mapRecursos.put(KardexDetalleService.class,KardexDetalleServiceIf.class);
            mapRecursos.put(KardexItemEspecificoService.class,KardexItemEspecificoServiceIf.class);
            mapRecursos.put(KardexService.class,KardexServiceIf.class);
            mapRecursos.put(NotaCreditoService.class,NotaCreditoServiceIf.class);
            mapRecursos.put(ParametroCodefacService.class,ParametroCodefacServiceIf.class);
            mapRecursos.put(PerfilService.class,PerfilServiceIf.class);
            mapRecursos.put(ProductoEnsambleService.class,ProductoEnsambleServiceIf.class);            
            mapRecursos.put(ProductoProveedorService.class,ProductoProveedorServiceIf.class);            
            mapRecursos.put(SriIdentificacionService.class,SriIdentificacionServiceIf.class);
            mapRecursos.put(SriService.class,SriServiceIf.class);
            mapRecursos.put(UsuarioServicio.class, UsuarioServicioIf.class);
            mapRecursos.put(UtilidadesService.class,UtilidadesServiceIf.class);
            */
            System.setProperty("java.rmi.server.hostname","186.4.212.15");
            //ControllerServiceUtil.cargarRecursosServidor("192.168.100.2");
            //ServiceControllerServer.cargarRecursos(mapRecursos,"1099");
            System.out.println("servidor iniciado");
            /*
            String host=InetAddress.getLocalHost().getHostAddress();
            System.out.println(host);
            Registry registro=LocateRegistry.createRegistry(1099);
            registro.rebind("rmi://"+host+":1099/RMIBD",new PersonaService());
            System.out.println("Servidor Activo");
            } catch (PersistenceException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            new Scanner(System.in).next();
        } catch (PersistenceException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
