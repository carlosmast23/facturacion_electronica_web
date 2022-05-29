/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel;
import ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteAvanzadoModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class ClienteUtilidadImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobante {

    private UtilidadComprobanteAvanzadoModel panel;


    public ClienteUtilidadImplComprobante(UtilidadComprobanteAvanzadoModel panel) throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        this.panel=panel;
    }

    @Override
    public void termino(byte[] byteJasperPrint,List<AlertaComprobanteElectronico> alertas) throws RemoteException {
        panel.estadoNormal(); //TODO:Ver si se puede mejorar este metodo porque desabilita la pantalla cuando termina el primer comprobante pero pueden ser varios
        //DialogoCodefac.mensaje("Dialogo", "Proceso Terminado", 1);
        panel.getCmbCarpetaComprobante().setSelectedIndex(panel.getCmbCarpetaComprobante().getSelectedIndex()); //Volver a cargar los comprobantes para actualizar y que no aparesca los que ya fueron enviados
        panel.panelPadre.actualizarNotificacionesCodefac();
    }

    @Override
    public void iniciado() {

    }

    @Override
    public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) //Si ya cumple la etapa de autorizar cambio el estado de los comprobantes
        {/*
            try {
                ComprobanteEnum comprobante = ComprobanteEnum.getEnumByCodigo(clave.tipoComprobante);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("claveAcceso", clave.clave);
                switch (comprobante) {
                    case FACTURA:
                        FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
                        List<Factura> facturas = servicio.obtenerPorMap(map);
                        for (Factura factura : facturas) {
                            try {
                                factura.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                                servicio.editar(factura);
                            } catch (RemoteException ex) {
                                Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    case NOTA_CREDITO:
                        NotaCreditoServiceIf servicioNotaCredito = ServiceFactory.getFactory().getNotaCreditoServiceIf();
                        List<NotaCredito> notasCredito = servicioNotaCredito.obtenerPorMap(map);
                        for (NotaCredito notaCredito : notasCredito) {
                            try {
                                notaCredito.setClaveAcceso(NotaCreditoEnumEstado.TERMINADO.getEstado());
                                servicioNotaCredito.editar(notaCredito);
                            } catch (RemoteException ex) {
                                Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(UtilidadComprobanteModel.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }

    @Override
    public void error(ComprobanteElectronicoException cee, String claveAcceso) throws RemoteException {
        panel.estadoNormal();
        DialogoCodefac.mensaje("Dialogo", cee.getMessage(), 1);
    }

}
