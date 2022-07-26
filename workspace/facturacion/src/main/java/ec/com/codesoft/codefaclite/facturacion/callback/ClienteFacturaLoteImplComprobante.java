/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacion.interfaz.InterfaceCallbakClient;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.ResultadoLoteAcademicoModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteMensaje;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author
 */
public class ClienteFacturaLoteImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobanteLote {

    private InterfaceCallbakClient listener;
    private MonitorComprobanteData monitorData;
    private ControladorCodefacInterface controlador;

    public ClienteFacturaLoteImplComprobante(ControladorCodefacInterface controlador) throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        this.controlador=controlador;
    }
    
    public ClienteFacturaLoteImplComprobante(ControladorCodefacInterface controlador,InterfaceCallbakClient listener) throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        this.controlador = controlador;
        this.listener=listener;
    }
    
    
    @Override
    public void iniciado() {        
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText("Factura Lote");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString("Factura Lote");
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
        
    }

    @Override
    public void procesando(int etapa) {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            monitorData.getBarraProgreso().setValue(50);
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            monitorData.getBarraProgreso().setValue(65);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            monitorData.getBarraProgreso().setValue(80);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            monitorData.getBarraProgreso().setValue(100);
        }


    }

    @Override
    public void error(ComprobanteElectronicoException cee) {
        monitorData.getBarraProgreso().setForeground(Color.ORANGE);
        monitorData.getBarraProgreso().setValue(100);
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(true);
        monitorData.getBtnReporte().setEnabled(true);
        //Ejecutar listener para informar a la pantalla prinicipal que termino el proceso
        if(listener!=null)
            listener.terminoProceso(null);
        
        monitorData.getBtnReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Etapa: " + cee.getEtapa() + "\n" + cee.getMessage());
            }
        });
        controlador.panelPadre.actualizarNotificacionesCodefac();

    }

    @Override
    public void termino(List<ComprobanteData> comprobantes) {
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            
            //Ejecutar listener para informar a la pantalla prinicipal que termino el proceso
            if(listener!=null)
                listener.terminoProceso(comprobantes);
            
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ResultadoLoteAcademicoModel resultadoLote=new ResultadoLoteAcademicoModel(comprobantes);
                    controlador.panelPadre.crearVentanaCodefac(resultadoLote, true);
                    
                    //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                }
            });
            
            ////=========> VERIFICAR SI TENGO QUE INFORMAR DE ALGÚN MENSAJE EN PANTALLA <=============== /////
            Boolean mostrarMensaje=false;
            String mensaje="";
            for (ComprobanteData comprobante : comprobantes) {
                if(comprobante.getMensajes().size()>0)
                {
                    mostrarMensaje=true;
                }
                
                mensaje+=castMensajeAutorizadoToString(comprobante.getMensajes());
            }
            ///=========> MOSTRAR LOS MENSAJE SI EXISTE MENSAJE <=====================//
            if(mostrarMensaje)
            {
                final String mensajeMostrar=mensaje;
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DialogoCodefac.mensaje("Mensajes",mensajeMostrar,DialogoCodefac.MENSAJE_ADVERTENCIA);
                        //JOptionPane.showMessageDialog(null, mensaje);
                    }
                });
            }
            
            
            
            
            //Actualiza la pantalla principal cuando se termina de procesar los comprobantes
            controlador.panelPadre.actualizarNotificacionesCodefac();
    }
    
    public void addListener(InterfaceCallbakClient listener)
    {
        this.listener=listener;
    }
    
    /**
     *TODO: Ver si este metodo se lo puede hacer mas general para tener acceso desde otras pantallas 
     * Posible Solución: Que el formato del mensaje ya venga listo desde el servidor solo para mostrar en el cliente.
     * @param mensajes
     * @return 
     */
    public static String castMensajeAutorizadoToString(List<ComprobanteMensaje> mensajes)
    {
        String mensajeError="";
        for (ComprobanteMensaje mensajeTmp : mensajes) {
            mensajeError+= 
                    "Identificador:" + mensajeTmp.getIdentificador() + "\n"
                + "Info Adicional:" + mensajeTmp.getInformacionAdicional() + "\n"
                + "Mensaje:" + mensajeTmp.getMensaje() + "\n"
                + "Tipo:" + mensajeTmp.getTipo()+"\n\n";
        }
        
        return mensajeError;
    }


}
