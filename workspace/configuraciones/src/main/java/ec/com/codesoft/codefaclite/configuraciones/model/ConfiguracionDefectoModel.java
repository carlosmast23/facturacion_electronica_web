/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.ConfiguracionDefectoPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.FormatoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ConfiguracionDefectoModel extends ConfiguracionDefectoPanel {

    private Map<String, ParametroCodefac> parametrosTodos;
    private List<ParametroCodefac> parametrosEditar;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarVariables();
        listenerComponentes();
        cargarDatos();
        
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida = false;
        super.validacionDatosIngresados = false;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearVariable();
            ParametroCodefacServiceIf service = ServiceFactory.getFactory().getParametroCodefacServiceIf();
            service.editarParametros(parametrosEditar);
            DialogoCodefac.mensaje("Actualizado datos", "Los parametros fueron actualizados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            //Cargar nuevamente lo datos de la base para tener persistente la informacion
            cargarDatos();

        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error", "No se pueden grabar los parametros", DialogoCodefac.MENSAJE_INCORRECTO);
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Configuraciones por Defecto";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarVariables() {
        
        UtilidadesComboBox.llenarComboBox(getCmbHabilitarRetencionesEnCompras(),EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbCreditoDefectoFacturas(), EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbNotaVentaInternaIva(), EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbProformaFacturarVariasVeces(), EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbFiltroRapidoBusqueda(), EnumSiNo.values());

        //Agregar los tipos de documentos disponibles
        UtilidadesComboBox.llenarComboBox(getCmbTipoDocumento(), TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION));
       
        //Agregar los tipos de documentos disponibles para las compras
        getCmbTipoDocumentoCompra().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentosCompra = TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.COMPRA);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentosCompra) {
            getCmbTipoDocumentoCompra().addItem(tipoDocumento);
        }
        
        //Cargar las formas de pago vigentes en el SRI
        try {
            List<SriFormaPago> formasPago = ServiceFactory.getFactory().getSriServiceIf().obtenerFormasPagoActivo();
            getCmbFormaPagoDefecto().removeAllItems();
            for (SriFormaPago formaPago : formasPago) {
                getCmbFormaPagoDefecto().addItem(formaPago);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        //getCmbConfiguracionImpresora().removeAllItems();
        UtilidadesComboBox.llenarComboBox(getCmbConfiguracionImpresora(),ConfiguracionImpresoraEnum.values());
        getCmbConfiguracionImpresora().setSelectedItem(ConfiguracionImpresoraEnum.NINGUNA);

        //Agregar los datos del combo de tipo formato de hoja de las ordenes de trabajo
        FormatoHojaEnum[] formatos = FormatoHojaEnum.values();
        for (FormatoHojaEnum formato : formatos) {
            getCmbFormatoHojas().addItem(formato);
        }

        //Agregar las opcion para esocger si o no en activar modulo cartera
        getCmbActivarModuloCartera().removeAllItems();
        getCmbActivarModuloCartera().addItem(EnumSiNo.NO);
        getCmbActivarModuloCartera().addItem(EnumSiNo.SI);
        
        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbActivarNotaVenta().removeAllItems();
        getCmbActivarNotaVenta().addItem(EnumSiNo.NO);
        getCmbActivarNotaVenta().addItem(EnumSiNo.SI);

        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbActivarComprobanteVenta().removeAllItems();
        getCmbActivarComprobanteVenta().addItem(EnumSiNo.NO);
        getCmbActivarComprobanteVenta().addItem(EnumSiNo.SI);

        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbActivarReporteSimpleGuiaRemision().removeAllItems();
        getCmbActivarReporteSimpleGuiaRemision().addItem(EnumSiNo.NO);
        getCmbActivarReporteSimpleGuiaRemision().addItem(EnumSiNo.SI);

        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbCargarProductoIvaFactura().removeAllItems();
        getCmbCargarProductoIvaFactura().addItem(EnumSiNo.NO);
        getCmbCargarProductoIvaFactura().addItem(EnumSiNo.SI);
        
        getTxtDiasAlertaPago().setValue(7);//POR DEFECTO LOS DIAS PARA MOSTRAR LAS ALERTAS SON 7        
        
        
        UtilidadesComboBox.llenarComboBox(getCmbFacturarInventarioNegativo(),EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbConstruirEnsamblesFacturar(),EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRideDireccionEmpledo(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRidePuntoEmisionEmpleado(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRideRazoSocialEmpledo(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRideRucEmpledo(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRideReferenciaDireccion(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbDatoAdicionalRideCodigoPersonalizado(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbImprimirCodigoInternoProducto(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbMostrarTituloFactFisica(),EnumSiNo.values());
        
        
        //Cargar las opciones en las configuraciones
        UtilidadesComboBox.llenarComboBox(getCmbEditarDescripcionFactura(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbEditarDescuentoFactura(),EnumSiNo.values());
        UtilidadesComboBox.llenarComboBox(getCmbEditarPrecioUnitFactura(),EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbDatosCompartidosEmpresas(),EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbDocumentoDefectoVistaFactura(),new DocumentoEnum[]{DocumentoEnum.FACTURA,DocumentoEnum.NOTA_VENTA_INTERNA,DocumentoEnum.NOTA_VENTA});

        //Cargar los valores de las retenciones de la renta       
        try {
            getCmbRetencionRenta().removeAllItems();
            SriRetencionRentaServiceIf sriRetencionRentaService = ServiceFactory.getFactory().getSriRetencionRentaServiceIf();
            List<SriRetencionRenta> tipoRetencionesRenta;
            tipoRetencionesRenta = sriRetencionRentaService.obtenerTodosOrdenadoPorCodigo();
            for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) {
                getCmbRetencionRenta().addItem(sriRetencionRenta);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Cargar los valores de las retenciones del iva
        try {
            getCmbRetencionIva().removeAllItems();
            SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
            List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodosOrdenadoPorCodigo();
            for (SriRetencionIva tipoRetencione : tipoRetencionesIva)
            {
                getCmbRetencionIva().addItem(tipoRetencione);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
        * Filtrar factura por usuario
        */
        UtilidadesComboBox.llenarComboBox(getjComboFiltrarFacturaPorUsuario(), EnumSiNo.values());
        
        UtilidadesComboBox.llenarComboBox(getCmbReporteDefectoVenta(),FormatoReporteEnum.values());
        
        //Llenar modo de facturas paras las guias de remision
        getCmbModoFacturasGuiaRemision().removeAllItems();
        getCmbModoFacturasGuiaRemision().addItem(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
        getCmbModoFacturasGuiaRemision().addItem(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
        
        getTxtDiasAlertaPago().setValue(2);
        
        //Cargar Documentos de guias de remision por defecto
        getCmbDocumentoGuiaRemisionDefecto().addItem(DocumentoEnum.GUIA_REMISION);
        getCmbDocumentoGuiaRemisionDefecto().addItem(DocumentoEnum.GUIA_REMISION_INTERNA);
    }

    private void cargarDatos() {
        try {
            ParametroCodefacServiceIf service = ServiceFactory.getFactory().getParametroCodefacServiceIf();
            parametrosTodos = service.getParametrosMap(session.getEmpresa());

            ParametroCodefac parametroTipoDocumento = parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);
            if(parametroTipoDocumento!=null)
            {
                TipoDocumentoEnum tipoDocumentoEnum = TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumento.getValor());
                getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnum);
            }

            //Cargar el documento de la compra
            ParametroCodefac parametroTipoDocumentoCompra = parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
            if(parametroTipoDocumento!=null)
            {
                TipoDocumentoEnum tipoDocumentoCompraEnum = TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumentoCompra.getValor());
                getCmbTipoDocumentoCompra().setSelectedItem(tipoDocumentoCompraEnum);
            }
            
            ParametroCodefac parametroGuiaRemisionDefecto = parametrosTodos.get(ParametroCodefac.DOCUMENTO_GUIA_REMISION_DEFECTO);
            if(parametroGuiaRemisionDefecto!=null)
            {
                DocumentoEnum documentoEnum=DocumentoEnum.obtenerDocumentoPorCodigo(parametroGuiaRemisionDefecto.getValor());
                getCmbDocumentoGuiaRemisionDefecto().setSelectedItem(documentoEnum);
            }
            
            ParametroCodefac parametroConfiguracionImpresora = parametrosTodos.get(ParametroCodefac.CONFIGURACION_IMPRESORA_FACTURA);
            if(parametroConfiguracionImpresora!=null )
            {
                ConfiguracionImpresoraEnum configuracionImpresoraEnum=ConfiguracionImpresoraEnum.buscarPorLetra(parametroConfiguracionImpresora.getValor());
                getCmbConfiguracionImpresora().setSelectedItem(configuracionImpresoraEnum);
                
            }

            //Cargar el documento de la compra
            ParametroCodefac parametroOrdenCompraObservacion = parametrosTodos.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            getTxtOrdenTrabajoReporte().setText((parametroOrdenCompraObservacion != null) ? parametroOrdenCompraObservacion.getValor() : "");

            //Cargar datos del tipo de reporte de las ordenes de trabajo
            ParametroCodefac parametroFormtaOrdenTrabajo = parametrosTodos.get(ParametroCodefac.FORMATO_ORDEN_TRABAJO);
            getCmbFormatoHojas().setSelectedItem((parametroFormtaOrdenTrabajo != null) ? parametroFormtaOrdenTrabajo.getValor() : null);
            
            ParametroCodefac parametroReporteDefectoVenta = parametrosTodos.get(ParametroCodefac.REPORTE_DEFECTO_VENTA);
            FormatoReporteEnum reporteEnum=FormatoReporteEnum.findByName((parametroReporteDefectoVenta != null) ? parametroReporteDefectoVenta.getValor() : null);
            getCmbReporteDefectoVenta().setSelectedItem(reporteEnum);

            ParametroCodefac parametroActivarCarteras = parametrosTodos.get(ParametroCodefac.ACTIVAR_CARTERA);
            EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra((parametroActivarCarteras != null) ? parametroActivarCarteras.getValor() : null);
            getCmbActivarModuloCartera().setSelectedItem(enumSiNo);
            
            ParametroCodefac parametroFiltroBusquedaRapido = parametrosTodos.get(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroFiltroBusquedaRapido != null) ? parametroFiltroBusquedaRapido.getValor() : null);
            getCmbFiltroRapidoBusqueda().setSelectedItem(enumSiNo);
            
            ParametroCodefac parametroMostrarTituloFactFisica = parametrosTodos.get(ParametroCodefac.MOSTRAR_TITULO_FACT_FISICA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroMostrarTituloFactFisica != null) ? parametroMostrarTituloFactFisica.getValor() : null);
                        
            //Si el valor es null entonces por defecto queda seleccionado en no
            if(enumSiNo==null)
            {
                enumSiNo=EnumSiNo.NO;
            }
            getCmbMostrarTituloFactFisica().setSelectedItem(enumSiNo);
            
            ParametroCodefac notaVentaInternaIva = parametrosTodos.get(ParametroCodefac.NOTA_VENTA_INTERNA_IVA);
            enumSiNo = EnumSiNo.getEnumByLetra((notaVentaInternaIva != null) ? notaVentaInternaIva.getValor() : null);
            getCmbNotaVentaInternaIva().setSelectedItem(enumSiNo);
            
            
            //getTxtOrdenTrabajoReporte().setText((parametroFormtaOrdenTrabajo!=null)?parametroFormtaOrdenTrabajo.getValor():"");
            
            ParametroCodefac parametroCreditoDefectoVentas = parametrosTodos.get(ParametroCodefac.CREDITO_DEFECTO_VENTAS);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroCreditoDefectoVentas != null) ? parametroCreditoDefectoVentas.getValor() : null);
            getCmbCreditoDefectoFacturas().setSelectedItem(enumSiNo);

            ParametroCodefac parametroComprobanteVenta = parametrosTodos.get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroComprobanteVenta != null) ? parametroComprobanteVenta.getValor() : null);
            if (parametroComprobanteVenta != null) {
                getCmbActivarComprobanteVenta().setSelectedItem(enumSiNo);
            } else {
                getCmbActivarComprobanteVenta().setSelectedItem(EnumSiNo.NO);
            }

            ParametroCodefac parametroComprobanteGuiaRemision = parametrosTodos.get(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroComprobanteGuiaRemision != null) ? parametroComprobanteGuiaRemision.getValor() : null);
            if (parametroComprobanteGuiaRemision != null) {
                getCmbActivarReporteSimpleGuiaRemision().setSelectedItem(enumSiNo);
            } else {
                getCmbActivarReporteSimpleGuiaRemision().setSelectedItem(EnumSiNo.NO);
            }

            ParametroCodefac parametroCargarProductoIvaFactura = parametrosTodos.get(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroCargarProductoIvaFactura != null) ? parametroCargarProductoIvaFactura.getValor() : null);
            if (parametroCargarProductoIvaFactura != null) {
                getCmbCargarProductoIvaFactura().setSelectedItem(enumSiNo);
            } else {
                getCmbCargarProductoIvaFactura().setSelectedItem(EnumSiNo.NO);
            }

            ParametroCodefac parametroMotivoGuiaRemisions = parametrosTodos.get(ParametroCodefac.MOTIVO_TRASLADO_GUIA_REMISION);
            String motivoGuiaRemision = (parametroMotivoGuiaRemisions != null) ? parametroMotivoGuiaRemisions.getValor() : "";
            getTxtMotivoTrasladoGuiaRemision().setText(motivoGuiaRemision);
            
            ParametroCodefac parametroNumMaxDetalleFactura = parametrosTodos.get(ParametroCodefac.NUMERO_MAXIMO_DETALLES_FACTURA);
            String numMaxDetalleFactura = (parametroNumMaxDetalleFactura != null) ? parametroNumMaxDetalleFactura.getValor() : "0";
            getTxtNumeroMaximoDetalleFactura().setText(numMaxDetalleFactura);
            
                       
            //==========> CARGAR VALOR DE LAS RETENCIONES
            ParametroCodefac parametro = parametrosTodos.get(ParametroCodefac.VALOR_DEFECTO_RETENCION_IVA);
            if(parametro!=null && parametro.getValor()!=null && !parametro.getValor().isEmpty())
            {
                SriRetencionIva retencion=ServiceFactory.getFactory().getSriRetencionIvaServiceIf().buscarPorId(Long.parseLong(parametro.getValor()));
                getCmbRetencionIva().setSelectedItem(retencion);
            }
            else
            {
                getCmbRetencionIva().setSelectedItem(null);
            }
            
            
            parametro = parametrosTodos.get(ParametroCodefac.VALOR_DEFECTO_RETENCION_RENTA);
            if(parametro!=null && parametro.getValor()!=null && !parametro.getValor().isEmpty())
            {
                SriRetencionRenta retencion=ServiceFactory.getFactory().getSriRetencionRentaServiceIf().buscarPorId(Long.parseLong(parametro.getValor()));
                getCmbRetencionRenta().setSelectedItem(retencion);
            }
            else
            {
                getCmbRetencionRenta().setSelectedItem(null);
            }
            
            //Nuevo parametro para poder imprimir con los tickets de la impresora
            ParametroCodefac parametroImpresoraTicketVenta = parametrosTodos.get(ParametroCodefac.IMPRESORA_TICKETS_VENTAS);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroImpresoraTicketVenta != null) ? parametroImpresoraTicketVenta.getValor() : null);
            getChkImpresoraTickets().setSelected((enumSiNo!=null)?enumSiNo.getBool():false);
            
            
            parametro = parametrosTodos.get(ParametroCodefac.VARIABLES_GENERAL_COMPROBANTES_ELECTRONICOS);
            getTxtVariableGeneralComprobantes().setText((parametro != null) ? parametro.getValor() : "");
            
            
            parametro = parametrosTodos.get(ParametroCodefac.DIAS_ALERTA_PAGO);
            getTxtDiasAlertaPago().setValue((parametro != null) ? Integer.parseInt(parametro.getValor()): 7);
            
            parametro = parametrosTodos.get(ParametroCodefac.NUMERO_DECIMALES_RIDE);
            getTxtNumeroDecimalesRide().setValue((parametro != null) ? Integer.parseInt(parametro.getValor()): 2);
            //getTxtMotivoTrasladoGuiaRemision().setText(motivoGuiaRemision);
            
            parametro = parametrosTodos.get(ParametroCodefac.NUMERO_DECIMAL_PRODUCTO);
            getTxtNumeroDecimalesProducto().setValue((parametro != null) ? Integer.parseInt(parametro.getValor()) : 2);
            
            parametro = parametrosTodos.get(ParametroCodefac.FORMATO_MENSAJE_COMPROBANTE_ELECTRONICO);
            getTxtCodigoHtml().setText((parametro != null) ? parametro.getValor() : "");    
            getjEditorPanelVistaPrevia().setText(getTxtCodigoHtml().getText());
            
            parametro = parametrosTodos.get(ParametroCodefac.LEYENDA_FIRMA_FACTURA_1);
            getTxtLeyendaFirmaFactura1().setText((parametro != null) ? parametro.getValor():"");
            
            parametro = parametrosTodos.get(ParametroCodefac.LEYENDA_FIRMA_FACTURA_2);
            getTxtLeyendaFirmaFactura2().setText((parametro != null) ? parametro.getValor():"");
            
            parametro = parametrosTodos.get(ParametroCodefac.LEYENDA_FIRMA_FACTURA_3);
            getTxtLeyendaFirmaFactura3().setText((parametro != null) ? parametro.getValor():"");
            
            parametro=parametrosTodos.get(ParametroCodefac.AliasNombresDocumentos.NOTA_VENTA_INTERNA_ALIAS);
            getTxtNotaVentaInternaAlias().setText((parametro != null) ? parametro.getValor():"");
            
            
            ParametroCodefac parametroEditarDescripcionFactura = parametrosTodos.get(ParametroCodefac.EDITAR_DESCRIPCION_FACTURA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroEditarDescripcionFactura != null) ? parametroEditarDescripcionFactura.getValor() : null);
            getCmbEditarDescripcionFactura().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            ParametroCodefac parametroEditarPrecioFactura = parametrosTodos.get(ParametroCodefac.EDITAR_PRECIO_UNIT_FACTURA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroEditarPrecioFactura != null) ? parametroEditarPrecioFactura.getValor() : null);
            getCmbEditarPrecioUnitFactura().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            ParametroCodefac parametroEditarDescuentoFactura = parametrosTodos.get(ParametroCodefac.EDITAR_DESCUENTO_FACTURA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroEditarDescuentoFactura != null) ? parametroEditarDescuentoFactura.getValor() : null);
            getCmbEditarDescuentoFactura().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            ParametroCodefac parametroFacturarInventarioNegativo = parametrosTodos.get(ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroFacturarInventarioNegativo != null) ? parametroFacturarInventarioNegativo.getValor() : null);
            getCmbFacturarInventarioNegativo().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            ParametroCodefac parametroConstruirEnsambleFacturar= parametrosTodos.get(ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroConstruirEnsambleFacturar != null) ? parametroConstruirEnsambleFacturar.getValor() : null);
            getCmbConstruirEnsamblesFacturar().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            //Parametros para agregar el ride
            ParametroCodefac parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_DIRECCION_EMPLEADO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRideDireccionEmpledo().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_PUNTO_EMISION_EMPLEADO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRidePuntoEmisionEmpleado().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_REFERENCIA_DIRECCION);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRideReferenciaDireccion().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_CODIGO_PERSONALIZADO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRideCodigoPersonalizado().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.IMPRIMIR_CODIGO_INTERNO_PRODUCTO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbImprimirCodigoInternoProducto().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_RAZON_SOCIAL_EMPLEADO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRideRazoSocialEmpledo().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.FACTURACION_RIDE_RUC_EMPLEADO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatoAdicionalRideRucEmpledo().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDatoAdicionalRide= parametrosTodos.get(ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDatoAdicionalRide != null) ? parametroDatoAdicionalRide.getValor() : null);
            getCmbDatosCompartidosEmpresas().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            ParametroCodefac parametroDato= parametrosTodos.get(ParametroCodefac.ACTIVAR_NOTA_VENTA);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDato != null) ? parametroDato.getValor() : null);
            getCmbActivarNotaVenta().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDato= parametrosTodos.get(ParametroCodefac.HABILITAR_RETENCION_COMPRAS);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDato != null) ? parametroDato.getValor() : null);
            getCmbHabilitarRetencionesEnCompras().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDato= parametrosTodos.get(ParametroCodefac.PROFORMA_FACTURAR_VARIAS_VECES);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroDato != null) ? parametroDato.getValor() : null);
            getCmbProformaFacturarVariasVeces().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            
            parametroDato= parametrosTodos.get(ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA);
            DocumentoEnum documentoEnumVistaFactura = DocumentoEnum.obtenerDocumentoPorCodigo((parametroDato != null) ? parametroDato.getValor() : null);
            getCmbDocumentoDefectoVistaFactura().setSelectedItem((documentoEnumVistaFactura!=null)?documentoEnumVistaFactura:null);
            
            parametroDato= parametrosTodos.get(ParametroCodefac.MODO_FACTURACION_GUIA_REMISION);
            if(parametroDato!=null)
            {
                ComprobanteEntity.ComprobanteEnumEstado modoFacturacionGuiaRemision= ComprobanteEntity.ComprobanteEnumEstado.getEnum(parametroDato.valor);
                getCmbModoFacturasGuiaRemision().setSelectedItem(modoFacturacionGuiaRemision);
            }
            else
            {
                getCmbModoFacturasGuiaRemision().setSelectedItem(null);
            }
                        
            ParametroCodefac parametroFormaPago= parametrosTodos.get(ParametroCodefac.FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE);
            
            //enumSiNo = EnumSiNo.getEnumByLetra((parametroFormaPago != null) ? parametroFormaPago.getValor() : null);
            //getCmbActivarNotaVenta().setSelectedItem((enumSiNo!=null)?enumSiNo:null);
            if(parametroFormaPago!=null && parametroFormaPago.getValor()!=null && !parametroFormaPago.getValor().isEmpty())
            {
                SriFormaPago sriFormaPago=ServiceFactory.getFactory().getSriFormaPagoServiceIf().buscarPorId(Long.parseLong(parametroFormaPago.getValor()));
                getCmbFormaPagoDefecto().setSelectedItem(sriFormaPago);
                //getCmbRetencionRenta().setSelectedItem(retencion);
            }
            else
            {
                getCmbFormaPagoDefecto().setSelectedItem(null);
            }
            
            /**
             * Filtrar factura por usuario
             */
            ParametroCodefac parametroFiltrarFacturaPorUsuario = parametrosTodos.get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
            enumSiNo = EnumSiNo.getEnumByLetra((parametroFiltrarFacturaPorUsuario != null) ? parametroFiltrarFacturaPorUsuario.getValor() : null);
            getjComboFiltrarFacturaPorUsuario().setSelectedItem((enumSiNo != null) ? enumSiNo:null);
            

        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearVariable() {
        parametrosEditar = new ArrayList<ParametroCodefac>();

        TipoDocumentoEnum tipoDocumento = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        agregarParametro(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA,tipoDocumento.getCodigo());
        agregarParametroEditar(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);

        TipoDocumentoEnum tipoDocumentoCompra = (TipoDocumentoEnum) getCmbTipoDocumentoCompra().getSelectedItem();
        agregarParametro(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA,tipoDocumentoCompra.getCodigo());        
        agregarParametroEditar(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
        
        DocumentoEnum documentoGuiaRemision = (DocumentoEnum) getCmbDocumentoGuiaRemisionDefecto().getSelectedItem();
        agregarParametro(ParametroCodefac.DOCUMENTO_GUIA_REMISION_DEFECTO,documentoGuiaRemision.getCodigo());        
        agregarParametroEditar(ParametroCodefac.DOCUMENTO_GUIA_REMISION_DEFECTO);
        
        ConfiguracionImpresoraEnum configuracionImpresoraEnum =(ConfiguracionImpresoraEnum) getCmbConfiguracionImpresora().getSelectedItem();
        agregarParametro(ParametroCodefac.CONFIGURACION_IMPRESORA_FACTURA,configuracionImpresoraEnum.getLetra());
        agregarParametroEditar(ParametroCodefac.CONFIGURACION_IMPRESORA_FACTURA);
        
        DocumentoEnum documentoDefectoVistaFactura =(DocumentoEnum) getCmbDocumentoDefectoVistaFactura().getSelectedItem();
        if(documentoDefectoVistaFactura!=null)
        {
            agregarParametro(ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA,documentoDefectoVistaFactura.getCodigo());
            agregarParametroEditar(ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA);
        }
        
        ComprobanteEntity.ComprobanteEnumEstado modoFacturasGuiaRemision =(ComprobanteEntity.ComprobanteEnumEstado) getCmbModoFacturasGuiaRemision().getSelectedItem();
        if(modoFacturasGuiaRemision!=null)
        {
            agregarParametro(ParametroCodefac.MODO_FACTURACION_GUIA_REMISION,modoFacturasGuiaRemision.getEstado());
            agregarParametroEditar(ParametroCodefac.MODO_FACTURACION_GUIA_REMISION);
        }
        
        FormatoReporteEnum reporteDefectoVenta =(FormatoReporteEnum) getCmbReporteDefectoVenta().getSelectedItem();
        agregarParametro(ParametroCodefac.REPORTE_DEFECTO_VENTA,(reporteDefectoVenta!=null)?reporteDefectoVenta.getNombre():null);
        agregarParametroEditar(ParametroCodefac.REPORTE_DEFECTO_VENTA);

        //Agregar detalle para la orden de trabajo
        agregarParametro(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES, getTxtOrdenTrabajoReporte().getText());
        agregarParametroEditar(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);

        EnumSiNo enumSiNo = (EnumSiNo) getCmbActivarModuloCartera().getSelectedItem();
        agregarParametro(ParametroCodefac.ACTIVAR_CARTERA,(enumSiNo!=null)?enumSiNo.getLetra():EnumSiNo.NO.getLetra()); //Por defecto va a guardar no activar cartera
        agregarParametroEditar(ParametroCodefac.ACTIVAR_CARTERA);
        
        enumSiNo = (EnumSiNo) getCmbCreditoDefectoFacturas().getSelectedItem();
        agregarParametro(ParametroCodefac.CREDITO_DEFECTO_VENTAS,(enumSiNo!=null)?enumSiNo.getLetra():EnumSiNo.NO.getLetra()); //Por defecto va a guardar no activar cartera
        agregarParametroEditar(ParametroCodefac.CREDITO_DEFECTO_VENTAS);

        enumSiNo = (EnumSiNo) getCmbActivarComprobanteVenta().getSelectedItem();
        agregarParametro(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);

        enumSiNo = (EnumSiNo) getCmbActivarReporteSimpleGuiaRemision().getSelectedItem();
        agregarParametro(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR);

        enumSiNo = (EnumSiNo) getCmbCargarProductoIvaFactura().getSelectedItem();
        agregarParametro(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA);
        
        enumSiNo = (EnumSiNo) getCmbEditarDescripcionFactura().getSelectedItem();
        agregarParametro(ParametroCodefac.EDITAR_DESCRIPCION_FACTURA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.EDITAR_DESCRIPCION_FACTURA);
        
        enumSiNo = (EnumSiNo) getCmbEditarDescuentoFactura().getSelectedItem();
        agregarParametro(ParametroCodefac.EDITAR_DESCUENTO_FACTURA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.EDITAR_DESCUENTO_FACTURA);
        
        enumSiNo = (EnumSiNo) getCmbEditarPrecioUnitFactura().getSelectedItem();
        agregarParametro(ParametroCodefac.EDITAR_PRECIO_UNIT_FACTURA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.EDITAR_PRECIO_UNIT_FACTURA);
        
        enumSiNo = (EnumSiNo) getCmbActivarNotaVenta().getSelectedItem();
        agregarParametro(ParametroCodefac.ACTIVAR_NOTA_VENTA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.ACTIVAR_NOTA_VENTA);
        
        enumSiNo = (EnumSiNo) getCmbFacturarInventarioNegativo().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO);
        
        enumSiNo = (EnumSiNo) getCmbConstruirEnsamblesFacturar().getSelectedItem();
        agregarParametro(ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR);
        
        enumSiNo = (EnumSiNo) getCmbHabilitarRetencionesEnCompras().getSelectedItem();
        agregarParametro(ParametroCodefac.HABILITAR_RETENCION_COMPRAS, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.HABILITAR_RETENCION_COMPRAS);
        
        enumSiNo = (EnumSiNo) getCmbNotaVentaInternaIva().getSelectedItem();
        agregarParametro(ParametroCodefac.NOTA_VENTA_INTERNA_IVA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.NOTA_VENTA_INTERNA_IVA);
        
        enumSiNo = (EnumSiNo) getCmbProformaFacturarVariasVeces().getSelectedItem();
        agregarParametro(ParametroCodefac.PROFORMA_FACTURAR_VARIAS_VECES, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.PROFORMA_FACTURAR_VARIAS_VECES);
        
        enumSiNo = (EnumSiNo) getCmbFiltroRapidoBusqueda().getSelectedItem();
        agregarParametro(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA);
        /*ParametroCodefac parametroCodefac=parametrosTodos.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
        if(parametroCodefac==null)
        {
            parametroCodefac=new ParametroCodefac();
            parametroCodefac.setNombre(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            parametroCodefac.setValor(getTxtOrdenTrabajoReporte().getText());
            parametrosTodos.put(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES, parametroCodefac);

        }
        else
        {
            parametroCodefac.setValor(getTxtOrdenTrabajoReporte().getText());
        }*/

        //Agregar tipo de hoja para el reporte de la orden de trabajo
        FormatoHojaEnum formatoHojaEnum = (FormatoHojaEnum) getCmbFormatoHojas().getSelectedItem();
        if (formatoHojaEnum == null) {
            formatoHojaEnum = FormatoHojaEnum.A4;
        }

        agregarParametro(ParametroCodefac.FORMATO_ORDEN_TRABAJO, formatoHojaEnum.getLetra());
        agregarParametroEditar(ParametroCodefac.FORMATO_ORDEN_TRABAJO);
        
        agregarParametro(ParametroCodefac.LEYENDA_FIRMA_FACTURA_1,getTxtLeyendaFirmaFactura1().getText());
        agregarParametroEditar(ParametroCodefac.LEYENDA_FIRMA_FACTURA_1);
        
        agregarParametro(ParametroCodefac.LEYENDA_FIRMA_FACTURA_2,getTxtLeyendaFirmaFactura2().getText());
        agregarParametroEditar(ParametroCodefac.LEYENDA_FIRMA_FACTURA_2);
        
        agregarParametro(ParametroCodefac.LEYENDA_FIRMA_FACTURA_3,getTxtLeyendaFirmaFactura3().getText());
        agregarParametroEditar(ParametroCodefac.LEYENDA_FIRMA_FACTURA_3);

        agregarParametro(ParametroCodefac.MOTIVO_TRASLADO_GUIA_REMISION, getTxtMotivoTrasladoGuiaRemision().getText());
        agregarParametroEditar(ParametroCodefac.MOTIVO_TRASLADO_GUIA_REMISION);
        
        agregarParametro(ParametroCodefac.NUMERO_MAXIMO_DETALLES_FACTURA, getTxtNumeroMaximoDetalleFactura().getText());
        agregarParametroEditar(ParametroCodefac.NUMERO_MAXIMO_DETALLES_FACTURA);        
        
        SriRetencionIva sriRetencionIva=(SriRetencionIva) getCmbRetencionIva().getSelectedItem();
        agregarParametro(ParametroCodefac.VALOR_DEFECTO_RETENCION_IVA,(sriRetencionIva!=null)?sriRetencionIva.getId().toString():"");
        agregarParametroEditar(ParametroCodefac.VALOR_DEFECTO_RETENCION_IVA);
        
        
        SriRetencionRenta sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
        agregarParametro(ParametroCodefac.VALOR_DEFECTO_RETENCION_RENTA, (sriRetencionRenta != null) ? sriRetencionRenta.getId().toString(): "");
        agregarParametroEditar(ParametroCodefac.VALOR_DEFECTO_RETENCION_RENTA);
        
        SriFormaPago sriFormaPagoDefecto = (SriFormaPago) getCmbFormaPagoDefecto().getSelectedItem();
        agregarParametro(ParametroCodefac.FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE, (sriFormaPagoDefecto != null) ? sriFormaPagoDefecto.getId().toString(): "");
        agregarParametroEditar(ParametroCodefac.FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE);
        
        
        enumSiNo = (EnumSiNo) EnumSiNo.getEnumByBoolean(getChkImpresoraTickets().isSelected());
        agregarParametro(ParametroCodefac.IMPRESORA_TICKETS_VENTAS, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.IMPRESORA_TICKETS_VENTAS);
        
        
        agregarParametro(ParametroCodefac.VARIABLES_GENERAL_COMPROBANTES_ELECTRONICOS, getTxtVariableGeneralComprobantes().getText());
        agregarParametroEditar(ParametroCodefac.VARIABLES_GENERAL_COMPROBANTES_ELECTRONICOS);
        
        agregarParametro(ParametroCodefac.FORMATO_MENSAJE_COMPROBANTE_ELECTRONICO,getTxtCodigoHtml().getText());
        agregarParametroEditar(ParametroCodefac.FORMATO_MENSAJE_COMPROBANTE_ELECTRONICO);
        
        agregarParametro(ParametroCodefac.DIAS_ALERTA_PAGO, getTxtDiasAlertaPago().getValue().toString());
        agregarParametroEditar(ParametroCodefac.DIAS_ALERTA_PAGO);   
        
        agregarParametro(ParametroCodefac.NUMERO_DECIMALES_RIDE, getTxtNumeroDecimalesRide().getValue().toString());
        agregarParametroEditar(ParametroCodefac.NUMERO_DECIMALES_RIDE);    
        
        agregarParametro(ParametroCodefac.NUMERO_DECIMAL_PRODUCTO, getTxtNumeroDecimalesProducto().getValue().toString());
        agregarParametroEditar(ParametroCodefac.NUMERO_DECIMAL_PRODUCTO);  
        
        agregarParametro(ParametroCodefac.AliasNombresDocumentos.NOTA_VENTA_INTERNA_ALIAS, getTxtNotaVentaInternaAlias().getText());
        agregarParametroEditar(ParametroCodefac.AliasNombresDocumentos.NOTA_VENTA_INTERNA_ALIAS);    

        /**
         * @auhor
         */        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRideDireccionEmpledo().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_DIRECCION_EMPLEADO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_DIRECCION_EMPLEADO);
        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRidePuntoEmisionEmpleado().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_PUNTO_EMISION_EMPLEADO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_PUNTO_EMISION_EMPLEADO);
        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRideReferenciaDireccion().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_REFERENCIA_DIRECCION, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_REFERENCIA_DIRECCION);
        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRideCodigoPersonalizado().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_CODIGO_PERSONALIZADO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_CODIGO_PERSONALIZADO);
        
        enumSiNo = (EnumSiNo) getCmbImprimirCodigoInternoProducto().getSelectedItem();
        agregarParametro(ParametroCodefac.IMPRIMIR_CODIGO_INTERNO_PRODUCTO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.IMPRIMIR_CODIGO_INTERNO_PRODUCTO);
        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRideRazoSocialEmpledo().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_RAZON_SOCIAL_EMPLEADO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_RAZON_SOCIAL_EMPLEADO);
        
        enumSiNo = (EnumSiNo) getCmbDatoAdicionalRideRucEmpledo().getSelectedItem();
        agregarParametro(ParametroCodefac.FACTURACION_RIDE_RUC_EMPLEADO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FACTURACION_RIDE_RUC_EMPLEADO);
        
        enumSiNo = (EnumSiNo) getCmbDatosCompartidosEmpresas().getSelectedItem();
        agregarParametro(ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA);
        
        /**
         * Filtrar factura por usuario
         */
        enumSiNo = (EnumSiNo) getjComboFiltrarFacturaPorUsuario().getSelectedItem();
        agregarParametro(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
        
        /**
         * Mostrar titulo facturacion fisica
         */
        enumSiNo = (EnumSiNo) getCmbMostrarTituloFactFisica().getSelectedItem();
        agregarParametro(ParametroCodefac.MOSTRAR_TITULO_FACT_FISICA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        agregarParametroEditar(ParametroCodefac.MOSTRAR_TITULO_FACT_FISICA);

    }
    
    private void agregarParametroEditar(String parametro)
    {
        ParametroCodefac parametroCodefac=parametrosTodos.get(parametro);
        if(parametroCodefac!=null)
        {
            parametrosEditar.add(parametroCodefac);
        }
    }

    private void agregarParametro(String nombreParametro, String valor) {
        ParametroCodefac parametroCodefac = parametrosTodos.get(nombreParametro);
        if (parametroCodefac == null) {
            parametroCodefac = new ParametroCodefac();
            parametroCodefac.setNombre(nombreParametro);
            parametroCodefac.setValor(valor);
            parametrosTodos.put(nombreParametro, parametroCodefac);

        } else {
            parametroCodefac.setValor(valor);
        }
        parametroCodefac.setEmpresa(session.getEmpresa());
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerComponentes() {
        getjEditorPanelVistaPrevia().setContentType("text/html");
        getTxtCodigoHtml().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                getjEditorPanelVistaPrevia().setText(getTxtCodigoHtml().getText());
            }
        });
    }
    
    

}
