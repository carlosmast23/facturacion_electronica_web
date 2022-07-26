/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.nocallback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import static ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel.NOMBRE_REPORTE_FACTURA_ELECTRONICA;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author
 */
public class FacturaRespuestaNoCallBack extends ComprobanteRespuestaNoCallBack
{
    private FacturacionModel facturacionModel;

    public FacturaRespuestaNoCallBack(Factura factura, FacturacionModel panel,Boolean procesoCompleto) {
        super(factura, panel);
        this.facturacionModel=panel;
        this.procesoCompleto=procesoCompleto;
    }
    

    @Override
    public void imprimirComprobante() {
        generarReportePdf(comprobante.getClaveAcceso());
    }
    
        
    private void generarReportePdf(String clave) {
        try {
            
            if(verificarImprimirComprobanteVenta())
            {
                FacturaModelControlador.imprimirComprobanteVenta((Factura) comprobante, NOMBRE_REPORTE_FACTURA_ELECTRONICA, true, facturacionModel.session,facturacionModel.panelPadre);
                //facturacionModel.imprimirComprobanteVenta((Factura) comprobante,NOMBRE_REPORTE_FACTURA_ELECTRONICA,true); //TODO:Verificar si este metodo no funciona
            }
            else
            {            
                byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,comprobante.getEmpresa());
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
                facturacionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            }
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private boolean verificarImprimirComprobanteVenta()
    {
        ParametroCodefac parametroCodefac = facturacionModel.session.getParametrosCodefac().get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);
        if (parametroCodefac == null) {
            //Si no esta tiene ningun dato por defecto no habilito la opcion de comprobante de venta
            return false;
        } else {
            if (EnumSiNo.getEnumByLetra(parametroCodefac.getValor()).equals(EnumSiNo.NO)) {
                //Si esta marcado la opcion no entonce no genero la opcion de imprimir el comprobante de venta
                return false;
            }
        }
        return true;
    }

}
