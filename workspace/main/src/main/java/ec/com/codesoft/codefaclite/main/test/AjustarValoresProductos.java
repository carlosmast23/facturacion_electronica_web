/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AjustarValoresProductos {

    public static void main(String[] args) {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "1234";
            AbstractFacade.cargarEntityManager();

            ProductoService service=new ProductoService();
            List<Producto> productos=service.obtenerTodos();
            for (Producto producto : productos) {
                BigDecimal valorRedondeado=producto.getValorUnitario().multiply(new BigDecimal("1.12")).setScale(2,RoundingMode.HALF_UP);
                System.out.println("Valor redondeado: "+valorRedondeado);
                
                BigDecimal nuevoValor=valorRedondeado.divide(new BigDecimal("1.12"),4,BigDecimal.ROUND_HALF_UP);
                System.out.println("Valor decimal: "+nuevoValor);
                
                producto.setValorUnitario(nuevoValor);
                service.editar(producto);
                
            }
            System.exit(0);

        } catch (PersistenceException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(AjustarValoresProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
