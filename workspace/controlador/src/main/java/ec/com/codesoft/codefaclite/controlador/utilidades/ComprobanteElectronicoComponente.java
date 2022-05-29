/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoComponente {

    public static void eliminarComprobante(GeneralPanelInterface panel, ComprobanteEntity comprobante, JLabel labelEstado) throws ExcepcionCodefacLite {
        //TODO: Mejorar esta parte porque puede ser que se quiera cambiar entre estos tipos anulaciones pero por ahora no
        //Validacion para no eliminar facturas que estan anuladas en el Sri o eliminadas
        if (comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado()) || comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado())) {
            DialogoCodefac.mensaje("Advertencia", "No se puede eliminar el comprobante porque ya esta eliminado o anulado", DialogoCodefac.MENSAJE_INCORRECTO);
            return;

        }

        //Varible 
        boolean respuesta = false;

        //Variable temporal cuando aun no se autoriza y el usuario quiere eliminar como si fuera desde el SRI
        //TODO: Ver como se puede modificar para mejorar esta parte
        boolean eliminarComoAutorizado = false;

        //Eliminar solo si esta en modo editar
        if (panel.estadoFormulario.equals(panel.ESTADO_EDITAR)) {
            if (comprobante != null) {

                //Eliminar solo si el estado esta en sin autorizar, o esta en el modo de facturacion normal y esta con estado facturado
                if (comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado())
                        || (comprobante.getTipoFacturacion().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()) && comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado()))) {
                    String[] opciones = {"Eliminar internamente", "Eliminado desde el Sri"};
                    int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Advertencia", "Selecciona la opción para eliminar el comprobante : ", DialogoCodefac.MENSAJE_ADVERTENCIA, opciones);

                    if (opcionSeleccionada == 1) {
                        eliminarComoAutorizado = true;
                    }

                    respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar el comprobante electrónico? ", DialogoCodefac.MENSAJE_ADVERTENCIA);
                } else {
                    respuesta = DialogoCodefac.dialogoPregunta("Alerta", "El comprobante electrónico se encuentra autorizada en el SRI , \nPor favor elimine  solo si tambien esta anulado en el SRI\nDesea eliminar de todos modos?", DialogoCodefac.MENSAJE_INCORRECTO);
                }

                //Eliminar la factura si eligen la respuesta si
                if (respuesta) {
                    try {

                        switch (comprobante.getCodigoDocumentoEnum()) {
                            case NOTA_VENTA:
                            case FACTURA:
                                ServiceFactory.getFactory().getFacturacionServiceIf().eliminarFactura((Factura) comprobante);
                                break;

                            case RETENCIONES:
                                ServiceFactory.getFactory().getRetencionServiceIf().eliminar((Retencion) comprobante);
                                break;

                            case GUIA_REMISION:
                                ServiceFactory.getFactory().getGuiaRemisionServiceIf().eliminar((GuiaRemision) comprobante);
                                break;

                            case NOTA_CREDITO:
                                ServiceFactory.getFactory().getNotaCreditoServiceIf().eliminar((NotaCredito) comprobante);
                                break;
                                
                        }

                        //ServiceFactory.getFactory().getComprobanteServiceIf().eliminarComprobante(comprobante);
                        DialogoCodefac.mensaje("Exitoso", "El comprobante electrónico se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);

                        /// Opcion para tambien eliminar el comprobante fisico
                        if (comprobante.getEstado().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado())) {
                            Boolean respuestaEliminar = DialogoCodefac.dialogoPregunta("Exitoso", "Desea eliminar también el comprobante físico?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                            if (respuestaEliminar) {
                                ServiceFactory.getFactory().getComprobanteServiceIf().eliminarComprobanteFisico(comprobante.getClaveAcceso());
                                DialogoCodefac.mensaje("Exitoso", "El comprobante electrónico se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                                panel.panelPadre.actualizarNotificacionesCodefac();
                            }
                        }

                        //TODO: Mejorar esta parte
                        if (eliminarComoAutorizado) {
                            comprobante.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
                            ServiceFactory.getFactory().getComprobanteServiceIf().editar(comprobante);
                        }

                        if (labelEstado != null) {
                            labelEstado.setText(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getNombre());
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje("Error al eliminar", ex.getMessage(), DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                }
            }
        } else {
            throw new ExcepcionCodefacLite("Cancelar evento eliminar porque no esta en modo editar");
        }

    }

    public static void cargarSecuencialConsulta(ComprobanteEntity comprobante, JComboBox<PuntoEmision> cmbPuntoEmision, JLabel lblEstablecimiento, JLabel lblSecuencial) {

        try {
            PuntoEmision puntoEmision=null;
            if(comprobante.getPuntoEmision()!=null)
            {
                puntoEmision = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerPorCodigo(Integer.valueOf(comprobante.getPuntoEmision()), comprobante.getSucursalEmpresa());
            }

            //cmbPuntoEmision.setSelectedItem((PuntoEmision) puntoEmision); //TODO: Analizar para todos los casos porque aveces no me va a permitir cargagar cuando pertenece a otra sucursal
            DefaultComboBoxModel modelCombo=(DefaultComboBoxModel)cmbPuntoEmision.getModel();
            //Si no tiene permisos seleciono con null para que no pueda editar y no genere inconsistencias
            if(modelCombo.getIndexOf(puntoEmision)<0)
            {
                cmbPuntoEmision.addItem(puntoEmision);
                cmbPuntoEmision.setSelectedItem(puntoEmision);                
            }
            else
            {
                cmbPuntoEmision.setSelectedItem(puntoEmision);
            }
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
            PuntoEmision puntoEmisionTmp = new PuntoEmision();
            puntoEmisionTmp.setPuntoEmision(Integer.valueOf(comprobante.getPuntoEmision()));
            cmbPuntoEmision.addItem(puntoEmisionTmp); //TODO: Revisar que salga bien
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(comprobante.getPuntoEstablecimiento()!=null)
        {
            lblEstablecimiento.setText(ComprobantesUtilidades.formatoEstablecimiento(comprobante.getPuntoEstablecimiento().toString()));
        }
        
        if(comprobante.getSecuencial()!=null)
        {
            lblSecuencial.setText(ComprobantesUtilidades.formatoSecuencial(comprobante.getSecuencial().toString()));
        }

    }

    public static void cargarSecuencial(Usuario usuario, DocumentoEnum documentoEnum, Sucursal sucursal, JComboBox<PuntoEmision> cmbPuntoEmision, JLabel lblEstablecimiento, JLabel lblSecuencial) {
        int indiceSeleccionado = cmbPuntoEmision.getSelectedIndex();
        //Cargar Puntos de Venta disponibles para la sucursal

        try {
            List<PuntoEmisionUsuario> puntosEmisionUsuario = ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivoPorUsuario(usuario, sucursal);
            //List<PuntoEmision> puntosVenta = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sucursal);
            cmbPuntoEmision.removeAllItems();
            //Canfigurar un cell render para las sucursales
            //getCmbPuntoEmision().setRenderer(new RenderPersonalizadoCombo());

            if (puntosEmisionUsuario != null) {
                for (PuntoEmisionUsuario puntoUsuario : puntosEmisionUsuario) {
                    //Cargar solo los puntos de emision que estan relacionados con la sucursal
                    if(puntoUsuario.getPuntoEmision()!=null)
                    {
                        if (puntoUsuario.getPuntoEmision().getSucursal() != null) {
                            if (puntoUsuario.getPuntoEmision().getSucursal().equals(sucursal)) {
                                cmbPuntoEmision.addItem(puntoUsuario.getPuntoEmision());
                            }
                        }
                    }
                }
            }

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteElectronicoComponente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (indiceSeleccionado < 0 && cmbPuntoEmision.getModel().getSize() > 0) {
            cmbPuntoEmision.setSelectedIndex(0); // Seleccionar el primero registro la primera vez
        } else {
            System.out.println("Cantidad Items: " + cmbPuntoEmision.getModel().getSize());
            System.out.println("Item a seleccionar: " + indiceSeleccionado);

            if (cmbPuntoEmision.getModel().getSize() > indiceSeleccionado) //Solo seleccionar un indice cuanto tiene contenido
            {
                cmbPuntoEmision.setSelectedIndex(indiceSeleccionado);
            }
        }

        lblEstablecimiento.setText(sucursal.getCodigoSucursalFormatoTexto() + "-");
        PuntoEmision puntoEmision = (PuntoEmision) cmbPuntoEmision.getSelectedItem();
        if (puntoEmision != null) {
            Integer secuencial = -1;
            switch (documentoEnum) {
                case RETENCIONES:
                    secuencial = puntoEmision.getSecuencialRetenciones();
                    break;

                case FACTURA:
                    secuencial = puntoEmision.getSecuencialFactura();
                    break;

                case NOTA_VENTA_INTERNA:
                    secuencial = puntoEmision.getSecuencialNotaVentaInterna();
                    break;

                case GUIA_REMISION:
                    secuencial = puntoEmision.getSecuencialGuiaRemision();
                    break;

                case NOTA_CREDITO:
                    secuencial = puntoEmision.getSecuencialNotaCredito();
                    break;

                case LIQUIDACION_COMPRA:
                    secuencial = puntoEmision.getSecuencialLiquidacionCompra();
                    break;

                case NOTA_VENTA:
                    secuencial = puntoEmision.getSecuencialNotaVenta();
                    break;
                /*case NOTA_DEBITO:
                    secuencial=puntoEmision.getSecuencialNotaDebito();
                    break;*/
            }
            lblSecuencial.setText("-" + UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0"));
        }
    }
}
