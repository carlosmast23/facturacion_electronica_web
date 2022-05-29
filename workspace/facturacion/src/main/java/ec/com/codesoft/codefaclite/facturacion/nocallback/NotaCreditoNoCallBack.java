/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.nocallback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteNotaCreditoImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class NotaCreditoNoCallBack extends ComprobanteRespuestaNoCallBack{
    
    private NotaCreditoModel notaCreditoModel;
    
    public NotaCreditoNoCallBack(NotaCredito comprobante, NotaCreditoModel notaCreditoModel) {
        super(comprobante, notaCreditoModel);
        this.notaCreditoModel=notaCreditoModel;
    }
    
    @Override
    public void imprimirComprobante() {
        generarReportePdf(comprobante.getClaveAcceso());
    }
    
    private void generarReportePdf(String clave) {
        try {

            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,comprobante.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            notaCreditoModel.panelPadre.crearReportePantalla(jasperPrint, clave);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteNotaCreditoImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteNotaCreditoImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteNotaCreditoImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
   
}
