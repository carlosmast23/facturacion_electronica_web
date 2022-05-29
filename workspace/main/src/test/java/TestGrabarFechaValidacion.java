
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestGrabarFechaValidacion {

    private static final Logger LOG = Logger.getLogger(TestGrabarFechaValidacion.class.getName());
    
    public static void main(String[] args)
    {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "1234";
            String fecha="2019-12-01";
           AbstractFacade.cargarEntityManager();
            
            EntityManager em = AbstractFacade.entityManager;
            EntityTransaction et = em.getTransaction();
            
            EmpresaService empresaService=new EmpresaService();
            List<Empresa> empresasResultado=empresaService.obtenerTodos();
            //Empresa empresa=empresaService.buscarPorIdentificacion("1724218951001");
            Empresa empresa=empresasResultado.get(0);
            
            ParametroCodefacService servicioParametro=new ParametroCodefacService();
            ParametroCodefac parametro=servicioParametro.getParametroByNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION, empresa);
            if(parametro==null)
            {
                parametro=new ParametroCodefac();
                parametro.setEmpresa(empresa);
                parametro.setNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
                parametro.setValor(UtilidadesEncriptar.encriptar(fecha,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
                servicioParametro.grabar(parametro);
            }else
            {
                parametro.setEmpresa(empresa);
                parametro.setNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
                parametro.setValor(UtilidadesEncriptar.encriptar(fecha,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
                servicioParametro.editar(parametro);
            }
            
            LOG.log(Level.INFO,"Fecha Actualizada correctamente");
            em.close();
            System.exit(0);
            
        } catch (PersistenceException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
