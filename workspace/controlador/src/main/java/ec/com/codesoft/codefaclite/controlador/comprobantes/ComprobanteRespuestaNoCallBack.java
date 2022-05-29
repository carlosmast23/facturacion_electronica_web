/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
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
 * @author Carlos
 */
public abstract class ComprobanteRespuestaNoCallBack implements Runnable{

    private static final Logger LOG = Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName());
    
    //public abstract Long getClavePrimariaComprobante();
    public abstract void imprimirComprobante();
    
    //Esta variable identifica cuanto tiempo esperar la autorizacion del documento expresado en segundos
    private static final Integer TIEMPO_ESPERA=40;
    
    protected ComprobanteEntity comprobante;
    
    protected MonitorComprobanteData monitorData;
    
    protected ControladorCodefacInterface panel;
    
    /**
     * Variable de control que me permite saber si ya se genero el evento de imprimir el Ride
     */
    private Boolean verificarEventGeneradoRide=false;
    
    /**
     * Variable para saber si el proceso debe esperar a que se autorize el documento o que solo se genere el Rid
     * true=esperar hasta autorizar el documento
     * false=esperar solo hasta que se genere el ride
     */
    protected Boolean procesoCompleto=true;

    public ComprobanteRespuestaNoCallBack(ComprobanteEntity comprobante, ControladorCodefacInterface panel) {
        this.comprobante = comprobante;
        this.panel = panel;
    }
    
    
    public void iniciar()
    {
        new Thread(this).start();
    }
    
    private ComprobanteEntity actualizarComprobante() throws RemoteException
    {
        switch(comprobante.getCodigoDocumentoEnum())
        {
            case LIQUIDACION_COMPRA:
            case NOTA_VENTA:
            case FACTURA:
                Factura factura=(Factura) comprobante;
                return ServiceFactory.getFactory().getFacturacionServiceIf().buscarPorId(factura.getId());
                            
            case NOTA_CREDITO:
                NotaCredito notaCredito=(NotaCredito) comprobante;
                return ServiceFactory.getFactory().getNotaCreditoServiceIf().buscarPorId(notaCredito.getId());
            
            case GUIA_REMISION:
                GuiaRemision guiaRemision=(GuiaRemision)comprobante;
                return ServiceFactory.getFactory().getGuiaRemisionServiceIf().buscarPorId(guiaRemision.getId());
            
            case RETENCIONES:
                Retencion retencion=(Retencion) comprobante;
                return ServiceFactory.getFactory().getRetencionServiceIf().buscarPorId(retencion.getId());            
             
        }
        return null;
    }
    
    @Override
    public void run() {
        
        LOG.log(Level.INFO,"Iniciado nocallback :"+comprobante.getPreimpreso());
        iniciado(); //Estado inicial que esta procesando la factura
        for (int i = 0; i < TIEMPO_ESPERA; i++) 
        {            
            try {
                Thread.sleep(2000);
                comprobante = actualizarComprobante();

                if (comprobante.getFechaAutorizacionSri() != null) {
                    terminado();
                    LOG.log(Level.INFO,"Factura Autorizada :"+comprobante.getPreimpreso());
                    return;
                } else if (comprobante.getClaveAcceso() != null) {
                    generadoRide();
                    LOG.log(Level.INFO,"Factura generado Ride :"+comprobante.getPreimpreso());
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LOG.log(Level.INFO,"Tiempo de espera superado para factura :"+comprobante.getPreimpreso());
    }
    
    private void iniciado() {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(comprobante.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(comprobante.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
    }
    
    private void generadoRide()
    {
        //Si ya esta generando este evento no lo vuelvo a generar
        if(verificarEventGeneradoRide)
        {
            return ;
        }
        imprimirComprobante(); 
        
        monitorData.getBarraProgreso().setForeground(Color.YELLOW);
        monitorData.getBarraProgreso().setValue(75);
        
        monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    imprimirComprobante();                    
                }
            });
        verificarEventGeneradoRide=true; //Esto sirve para identificar que ya agregue un actionListener
        
    }
    
    private void terminado()
    {
        try {
            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(comprobante.getClaveAcceso(),comprobante.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            monitorData.getBarraProgreso().setForeground(Color.GREEN);            
            monitorData.getBarraProgreso().setValue(100);
            monitorData.getBtnCerrar().setEnabled(true);
            
            //Solo activar si aun no se a activa el evento de imprimir
            if (verificarEventGeneradoRide) {
                return;
            }
            
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    imprimirComprobante();
                }
            });
                       
            
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /*private void generarReportePdf(String clave) {
        try {
            
            if(verificarImprimirComprobanteVenta())
            {
                //facturacionModel.imprimirComprobanteVenta(factura); //TODO:Verificar si este metodo no funciona
                imprimirComprobante();
            }
            else
            {            
                byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
                panel.panelPadre.crearReportePantalla(jasperPrint, clave);
            }
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComprobanteRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
    
    /*private boolean verificarImprimirComprobanteVenta()
    {
        ParametroCodefac parametroCodefac = panel.session.getParametrosCodefac().get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);
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
    }*/
    
}
