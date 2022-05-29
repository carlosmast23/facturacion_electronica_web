/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralPublicoAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ConsultaFacturasMb extends GeneralPublicoAbstractMb{

    private String identificacion;

    private List<Factura> listaFacturas;

    @PostConstruct
    public void init() {

    }

    public void consultarCedula() {
        try {
            System.out.println("Consultado la factura ...");
            //Factura f;
            //f.getIdentificacion();
            FacturacionServiceIf facturaService = ServiceFactory.getFactory().getFacturacionServiceIf();

            //Map<String, Object> mapParametros = new HashMap<String, Object>();
            //mapParametros.put("identifacion", "1724218951001");

            listaFacturas = facturaService.obtenerFacturasPorIdentificacion(identificacion,getControladorPlantilla().getEmpresaSeleccionada());

            //Usuarios
            //mapParametros = new HashMap<String, Object>();
            //mapParametros.put("nick", "root");
            //UsuarioServicioIf usuarioServiceIf = ServiceFactory.getFactory().getUsuarioServicioIf();
            
            //List<Usuario> usuarios = (List<Usuario>) usuarioServiceIf.consultarUsuarioActivoPorEmpresa("root",null); //Todo: Falta parametrizar para consultar las facturas por empresa
            //for (Usuario usuario : usuarios) {
            //    System.out.println(usuario.getNick());
            //}

        } catch (RemoteException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirFactura(Factura factura) {
        try {           
            System.out.println("consultando RIDE factura..");
            //TODO:Corregir esta parte
            byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(factura.getClaveAcceso(),null); //Todo: revisar este caso porque debe mandar a imprimir con informacion de alguna empresa
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            UtilidadesWeb.exportarPDF(jasperPrint);
            System.out.println("Imprimiendo factura..");
        } catch (RemoteException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ejemplo()
    {
        System.out.println("Metodo de ejemplo .....");
    }

    /**
     * METODOS GET AND SET
     *
     * @return
     */
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    @Override
    public void postAddController() {
        
    }

}
