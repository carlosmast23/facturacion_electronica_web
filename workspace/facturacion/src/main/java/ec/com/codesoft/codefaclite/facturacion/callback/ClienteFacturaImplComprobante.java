/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import static ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel.NOMBRE_REPORTE_FACTURA_ELECTRONICA;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author
 */
public class ClienteFacturaImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobante {

    private FacturacionModel facturacionModel;
    private MonitorComprobanteData monitorData;
    //private FacturacionServiceIf servicio;
    private Factura facturaProcesando;
    
    /**
     * Opcion para saber si se se va a mandar a autorizar o solo se va a firmar el comprobante electronica
     */
    private Boolean facturacionOffline;

    public ClienteFacturaImplComprobante(FacturacionModel facturacionModel, Factura facturaProcesando,Boolean facturacionOffline) throws RemoteException {
        super(ParametrosClienteEscritorio.puertoCallBack);
        
        this.facturacionModel = facturacionModel;
        //this.servicio = servicio;
        this.facturaProcesando = facturaProcesando;
        this.facturacionOffline=facturacionOffline;
    }

    @Override
    public void termino(byte[] byteJasperPrint,List<AlertaComprobanteElectronico> alertas) throws RemoteException {

        try {
            
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteJasperPrint);
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            
            if(!monitorData.getBtnReporte().isEnabled() && alertas.size()>0)
            {
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String mensajeCompleto = AlertaComprobanteElectronico.unirTodasAlertas(alertas);

                        DialogoCodefac.mensaje("Alertas",mensajeCompleto,DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                });
            }
            /*monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                }
            });*/
            
            // Agregado mensaje de advertencia cuando se factura en el modo offline
            if(facturacionOffline)
            {
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DialogoCodefac.mensaje("Advertencia","Recuerde Autorizar el comprobante en el SRI, \n Si esta sin internet mandar por correo el RIDE", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                });
                facturacionModel.panelPadre.actualizarNotificacionesCodefac(); //todo: Ver alguna otra forma mejor para actualizar la pantalla de comprobantes                
                
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    @Override
    public void iniciado() {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(facturaProcesando.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(facturaProcesando.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
        
    }

    @Override
    public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
            facturaProcesando.setClaveAcceso(clave.clave);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            monitorData.getBarraProgreso().setValue(50);
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            
            //solo cuando este en el proceso normal seteo el 65 % porque para el proceso autorizado se supone que ya esta con el 100%
            //TODO: Ver un forma estar con las demas pantallas que hacen lo mismo
            if(ParametroUtilidades.comparar(facturacionModel.getEmpresa(),ParametroCodefac.TIPO_ENVIO_COMPROBANTE, ParametroCodefac.TipoEnvioComprobanteEnum.ENVIAR_FIRMADO))
            {
                monitorData.getBarraProgreso().setValue(65);
            }
            
            //facturaProcesando.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                        
            //En esta etapa ya se habilita la opcion de imprimir el ride porque ya esta generado
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eventoImprimirComprobanteEvento(clave);
                }
            });
            eventoImprimirComprobanteEvento(clave);
            
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            monitorData.getBarraProgreso().setValue(80);
            //facturaProcesando.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());            
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            monitorData.getBarraProgreso().setValue(100);
            //facturaProcesando.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        }

    }
    
    private void eventoImprimirComprobanteEvento(ClaveAcceso clave)
    {
        //TODO: Parche temporal para imprimir y utilizar la misma metodologia para las liquidaciones de compra
        if(clave.getTipoComprobante().equals(ComprobanteEnum.LIQUIDACION_COMPRA))
        {
            imprimirComprobanteRide(clave.clave);
            return;
        }
        
        if (verificarImprimirComprobanteVenta()) {
            FacturaModelControlador.imprimirComprobanteVenta(facturaProcesando, NOMBRE_REPORTE_FACTURA_ELECTRONICA, true, facturacionModel.session, facturacionModel.panelPadre);
            //facturacionModel.imprimirComprobanteVenta(facturaProcesando, NOMBRE_REPORTE_FACTURA_ELECTRONICA,true);
            //imprimirComprobanteVenta();
        } else {
            generarReportePdf(clave.clave);
        }
    }
        
    private void generarReportePdf(String clave) {
        if(verificarImprimirComprobanteVenta())
        {
            FacturaModelControlador.imprimirComprobanteVenta(facturaProcesando, NOMBRE_REPORTE_FACTURA_ELECTRONICA, true, facturacionModel.session, facturacionModel.panelPadre);
            //facturacionModel.imprimirComprobanteVenta(facturaProcesando,NOMBRE_REPORTE_FACTURA_ELECTRONICA,true); //TODO:Verificar si este metodo no funciona
        }
        else
        {
            //byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,facturaProcesando.getEmpresa());
            //JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            imprimirComprobanteRide(clave);
        }
        //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());

    }
    
    private void imprimirComprobanteRide(String clave)
    {
        try {
            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave, facturaProcesando.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            facturacionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void error(ComprobanteElectronicoException cee,String claveAcceso) throws RemoteException {
        try {
            byte[] resporteSerializado = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,facturaProcesando.getEmpresa());
                        
            monitorData.getBtnReporte().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(resporteSerializado);
            
            monitorData.getBtnReporte().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    JOptionPane.showMessageDialog(null,cee.obtenerErrorFormato());
                    
                    monitorData.getBtnAbrir().addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(verificarImprimirComprobanteVenta())
                            {
                                FacturaModelControlador.imprimirComprobanteVenta(facturaProcesando, NOMBRE_REPORTE_FACTURA_ELECTRONICA, true, facturacionModel.session, facturacionModel.panelPadre);
                                //facturacionModel.imprimirComprobanteVenta(facturaProcesando,NOMBRE_REPORTE_FACTURA_ELECTRONICA,true); //TODO:Verificar si este metodo no funciona
                            }
                            else
                            {
                                facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                            }

                        }
                    });
                }
            });

            if (cee.getTipoError().equals(ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE)) {
                monitorData.getBtnAbrir().setEnabled(true);
                monitorData.getBarraProgreso().setForeground(Color.YELLOW);
            } else {
                monitorData.getBarraProgreso().setForeground(Color.ORANGE);
            }

            //servicio.editar(facturaProcesando);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }
        facturacionModel.panelPadre.actualizarNotificacionesCodefac();
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
    /*
    private  void imprimirComprobanteVenta()
    {

        //if(DialogoCodefac.dialogoPregunta("Pregunta","Desea imprimir un comprobante de la venta",DialogoCodefac.MENSAJE_CORRECTO))
        //{
            List<ComprobanteVentaData> dataReporte=new ArrayList<ComprobanteVentaData>();
            
            
            for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {
                
                ComprobanteVentaData data=new ComprobanteVentaData();
                data.setCantidad(detalle.getCantidad().toString());
                data.setCodigo(detalle.getId().toString());
                data.setNombre(detalle.getDescripcion().toString());
                data.setPrecioUnitario(detalle.getPrecioUnitario().toString());
                data.setTotal(detalle.getTotal().toString());
                
                dataReporte.add(data);                
            }
            //map de los parametros faltantes
            Map<String,Object> mapParametros=new HashMap<String, Object>();
            mapParametros.put("codigo", facturaProcesando.getPreimpreso());
            mapParametros.put("cedula", facturaProcesando.getIdentificacion());
            mapParametros.put("cliente", facturaProcesando.getRazonSocial());
            mapParametros.put("direccion", facturaProcesando.getDireccion());
            mapParametros.put("telefonos", facturaProcesando.getTelefono());
            mapParametros.put("fechaIngreso", facturaProcesando.getFechaEmision().toString());
            mapParametros.put("subtotal", facturaProcesando.getSubtotalImpuestos().add(facturaProcesando.getSubtotalSinImpuestos()).toString());
            mapParametros.put("iva", facturaProcesando.getIva().toString());
            mapParametros.put("total", facturaProcesando.getTotal().toString());
            mapParametros.put("autorizacion", facturaProcesando.getClaveAcceso());
            
            
            InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta.jrxml");
            
           ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, dataReporte, facturacionModel.panelPadre, "Comprobante de Venta ",OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A5);
            
        //}
        
        
    }
    */

}
