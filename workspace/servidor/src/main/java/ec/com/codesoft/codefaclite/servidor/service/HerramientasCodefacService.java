/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.HerramientasCodefacFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.HerramientasCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

/**
 *
 * @auhor
 */
public class HerramientasCodefacService extends ServiceAbstract<Object,HerramientasCodefacFacade> implements HerramientasCodefacServiceIf{

    public HerramientasCodefacService() throws RemoteException {
        super(HerramientasCodefacFacade.class);
    }
    
    public List buscarClavesRepetidasBaseDatosLista(String nombreTabla,String nombrePk) throws RemoteException,ServicioCodefacException    
    {
        List resultados = AbstractFacade.buscarClavesRepetidasBaseDatosLista(nombreTabla, nombrePk);
        return resultados;
    }
    
    public void corregirDatosDuplicadosPk(List<Factura> facturasProcesarList) throws RemoteException,ServicioCodefacException    
    {        
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                for (Factura factura : facturasProcesarList) 
                {
                    FacturacionService facturacionService=new FacturacionService();
                    Factura facturaOriginal=facturacionService.buscarPorId(factura.getId());
                    
                    Logger.getLogger(HerramientasCodefacService.class.getName()).log(Level.WARNING,"Corriendo factura: "+facturaOriginal.getPreimpreso()+" ,ID="+facturaOriginal.getId());
                    //Grabando la nueva factura 
                    factura.setId(null);                    
                    entityManager.persist(factura);
                    entityManager.flush();
                    Logger.getLogger(HerramientasCodefacService.class.getName()).log(Level.WARNING,"Factura nueva generada: "+factura.getId());
                    
                    //Eliminar la factura antigua que estaba con errores
                    entityManager.remove(facturaOriginal);
                    entityManager.flush();                    
                    
                }
            }
        });
        
        
    }
    
}
