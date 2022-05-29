/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ReferidoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura.TipoReporteEnum;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaReportePanel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.*;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sun.nio.cs.ext.Big5;

/**
 *
 * @author CodesoftDesarrollo
 */
public class FacturaReporteModel extends FacturaReportePanel {

    protected Boolean filtrarReferidos;
    //protected Persona referido;
    
    private PersonaEstablecimiento persona;
    protected Persona referido;
    //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private List<ReporteFacturaData> data;
    
    //private Map<String,BigDecimal> mapTotales;
    private ControladorReporteFactura controladorReporte;



    public FacturaReporteModel() {        
        initListener();
        super.validacionDatosIngresados=false;
    }

    private void initListener() {
        
        listenerBotones();
        listenerCombos();
        listenerChecks();
        listenerTablas();
    }
    
    public ControladorReporteFactura crearControlador()
    {
        return new ControladorReporteFactura(session.getEmpresa());
    }
    
    public ControladorReporteFactura crearControladorPorPuntoEmision(){
        return new ControladorReporteFactura(session.getEmpresa(), session.getUsuario());
    }
    
    protected void generarReporteConLecturaParametros(Date fechaInicio,Date fechaFin)
    {
        
        //Date fechaInicio = null;
        //Date fechaFin = null;

        //mapTotales=new HashMap<String,BigDecimal>();
        //igDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
        ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
        String estadoStr = estadoFactura.getEstado();

        //if (getDateFechaInicio().getDate() != null) {
        //    fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
        //}

        //if (getDateFechaFin().getDate() != null) {
        //    fechaFin = new Date(getDateFechaFin().getDate().getTime());
        //}

        Sucursal sucursal = null;
        if (!getChkSucursalTodos().isSelected()) {
            sucursal = (Sucursal) getCmbSucursal().getSelectedItem();
        }

        DocumentoEnum documentoConsultaEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();

        ParametroCodefac siNofiltrarFacturaPorUsuario = session.getParametrosCodefac().get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
        EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra((siNofiltrarFacturaPorUsuario != null) ? siNofiltrarFacturaPorUsuario.getValor() : null);
        //session.getParametrosCodefac().get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO).compararEnumSiNo(EnumSiNo.SI))
        if (enumSiNo != null && enumSiNo.equals(EnumSiNo.SI)) {
            controladorReporte = crearControladorPorPuntoEmision();
        } else {
            controladorReporte = crearControlador();
        }
        //Seteando datos para el controlador         
        //controladorReporte =crearControlador();
        controladorReporte.setPersona(persona);
        controladorReporte.setFechaInicio(fechaInicio);
        controladorReporte.setFechaFin(fechaFin);
        controladorReporte.setEstadoFactura(estadoFactura);
        controladorReporte.setSucursal(sucursal);
        controladorReporte.setFiltrarReferidos(filtrarReferidos);
        controladorReporte.setReferido(referido);
        controladorReporte.setReporteAgrupado(getChkReporteAgrupadoReferido().isSelected());
        controladorReporte.setAfectarNotaCredito(getChkAfectaNotaCredito().isSelected());
        controladorReporte.setDocumentoConsultaEnum(documentoConsultaEnum);
        PuntoEmision puntoEmisionReporte = ((getChkPuntoEmisionTodos().isSelected()) ? null : (PuntoEmision) getCmbPuntoEmision().getSelectedItem());

        controladorReporte.setPuntoEmision(puntoEmisionReporte);
        controladorReporte.setAgregarCostos(getChkAgregarCostos().isSelected());

        //Cuando se quiere agrupar por produto activo la opcion de Agrupado por Producto
        TipoReporteEnum tipoReporteEnum = (TipoReporteEnum) getCmbTipoReporte().getSelectedItem();
        if (tipoReporteEnum.equals(TipoReporteEnum.AGRUPADO_POR_PRODUCTO)
                || tipoReporteEnum.equals(TipoReporteEnum.AGRUPADO_POR_CATEGORIA)
                || tipoReporteEnum.equals(TipoReporteEnum.AGRUPADO_POR_VALOR)) {
            controladorReporte.setReporteConDetallesFactura(true);
        }

        Long tiempoInicial = System.nanoTime();
        controladorReporte.generarReporte();
        long endTime = System.nanoTime() - tiempoInicial;
        long segundosDemora = TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS);
        System.out.println("El tiempo en generar el reporte es: " + segundosDemora);

        data = controladorReporte.getData();

        imprimirTabla();

    }
    
    protected void generarReporte()
    {
        //try {
            
            Date fechaInicio=null;
            Date fechaFin =null;
            
            
            if (getDateFechaInicio().getDate() != null) 
            {
                fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            }
            
            if (getDateFechaFin().getDate() != null) 
            {
                fechaFin = new Date(getDateFechaFin().getDate().getTime());
            }
            
            generarReporteConLecturaParametros(fechaInicio, fechaFin);
            
    }
    
    //protected InputStream getReporte()
    //{
    //    return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
    //}
    
    protected void imprimirReporte()
    {
        TipoReporteEnum tipoReporteEnum=(TipoReporteEnum) getCmbTipoReporte().getSelectedItem();
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {

            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    Vector<String> titulosVector = crearCabezeraExcel(tipoReporteEnum);
                    String nombreCabeceras[] = titulosVector.toArray(new String[titulosVector.size()]); //Convertir en array
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                
                if(tipoReporteEnum.equals(TipoReporteEnum.NORMAL))
                {
                    controladorReporte.obtenerReportePdf(panelPadre);                    
                }else
                {
                    controladorReporte.obtenerReporteAgrupado(panelPadre,tipoReporteEnum);
                }
                
                /*switch(tipoReporteEnum)
                {
                    case AGRUPADO_POR_PUNTO_EMISION:
                        controladorReporte.obtenerReportePdfAgrupadoPorPuntosEstablecimiento(panelPadre);
                        break;  
                    
                    case NORMAL:
                        controladorReporte.obtenerReportePdf(panelPadre);
                        break;
                        
                    case AGRUPADO_POR_PRODUCTO:
                        controladorReporte.obtenerReporteAgrupadoPorProducto(panelPadre);
                        break;
                        
                    case AGRUPADO_POR_VENDEDOR:
                        controladorReporte.obtenerReporteAgrupadoPorVendedor(panelPadre);
                        break;
                    case AGRUPADO_POR_CATEGORIA:
                        controladorReporte.obtenerReporteAgrupadoPorProductoCategoria(panelPadre);
                        break;
                    case AGRUPADO_POR_VALOR:
                        //controladorReporte.obtenerReporteAgrupadoPorPrecio(panelPadre);
                        controladorReporte.obtenerReporteAgrupado(panelPadre,tipoReporteEnum);
                        break;
                    case AGRUPADO_POR_FORMA_PAGO:
                        controladorReporte.obtenerReporteAgrupadoPorPrecio(panelPadre);
                        break;
                    
                }*/
                
                //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, titulo, OrientacionReporteEnum.HORIZONTAL);
            }
        });
    }
    
    protected void imprimirTabla()
    {
        //Si genera en la tabla creo los datos de las filas
        DefaultTableModel modeloTablaFacturas = construirModelTabla();
        for (ReporteFacturaData reporteFacturaData : data) {
            Vector<String> fila = new Vector<String>();
            fila.add(reporteFacturaData.getNumeroFactura());
            fila.add(reporteFacturaData.getReferencia());
            fila.add(reporteFacturaData.getFechaFactura());
            fila.add(reporteFacturaData.getFechaAutorizacion());
            fila.add(reporteFacturaData.getIdentificacionCliente());
            fila.add(reporteFacturaData.getRazonSocialCliente());
            
            //Imprimir segun el tipo de consulta que estoy haciendo
            if(filtrarReferidos)
                fila.add(reporteFacturaData.getReferido());
            else                
                fila.add(reporteFacturaData.getNombreLegalCliente());
            
            fila.add(reporteFacturaData.getRuta());
            fila.add(reporteFacturaData.getZona());
            
            fila.add(reporteFacturaData.getDocumento()); //Aqui debe ir el tipo de documento
            fila.add(reporteFacturaData.getEstadoFactura());
            fila.add(reporteFacturaData.getTipoEmision()); //Falta implementar
            fila.add(reporteFacturaData.getSubtotalDoceFactura());
            fila.add(reporteFacturaData.getSubtotalCeroFactura());
            fila.add(reporteFacturaData.getDescFactura());
            fila.add(reporteFacturaData.getIvaDoceFactura());
            fila.add(reporteFacturaData.getValorAfecta());
            fila.add(reporteFacturaData.getTotalFinal());
            modeloTablaFacturas.addRow(fila);
        }

        getTblDocumentos().setModel(modeloTablaFacturas);
        Map<Integer, Integer> mapTamanios = new HashMap<Integer, Integer>();
        mapTamanios.put(0, 130);
        UtilidadesTablas.definirTamanioColumnasPorMap(getTblDocumentos(), mapTamanios);
        
        ControladorReporteFactura.TotalSumatoria total=controladorReporte.totalSinNotaCredito();
        getLblSubtotal0().setText(total.getSubtotalSinImpuestoMenosDescuento().toString());
        getLblSubtotal12().setText(total.getSubtotalConImpuestoMenosDescuento().toString());
        getLblSubtotalSinImpuesto().setText(total.obtenerSubtotal().toString());
        getLblTotalDescuento().setText(total.obtenerTotalDescuentos().toString());
        getLblIva12().setText(total.getValorImpuesto().toString());
        getTxtValorTotal().setText(total.obtenerTotal().toString());

       
    }

    private NotaCredito verificarPorFactura(Factura factura,List<NotaCredito> notasCredito) {
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura()!=null && notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
    }
    
    protected Vector<String>  crearCabezeraTabla()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Preimpreso");
        titulo.add("Referencia");
        titulo.add("Fecha");
        titulo.add("Fecha Autorización");
        titulo.add("Identificación");
        titulo.add("Razón social");
        
        //Si esta activo esta opcion envez del nombre legal pongo el nombre del referido TODO: Buscar alguna solucion mas elegante
        if(filtrarReferidos)
            titulo.add("Referido");
        else
            titulo.add("Nombre legal");
        
        titulo.add("Ruta");
        titulo.add("Zona");
        titulo.add("Documento");
        titulo.add("Estado");
        titulo.add("Tipo");
        titulo.add("Subtotal 12%");
        titulo.add("Subtotal 0% ");
        titulo.add("Descuentos");
        titulo.add("IVA 12%");
        titulo.add("Valor Afecta");
        titulo.add("Total");
        return titulo;
    }
    
    protected Vector<String>  crearCabezeraExcel(TipoReporteEnum tipoReporteEnum)
    {
        Vector<String> titulos=crearCabezeraTabla();
        titulos.add(titulos.size(),"Costo");
        titulos.add(0,"Clave de Acceso");
        titulos.add(1,"Fecha Max Pago");
        titulos.add(2,"Vendedor");
        
        //if(tipoReporteEnum.equals(TipoReporteEnum.AGRUPADO_POR_PRODUCTO))
        //{
        titulos.add(3,"Producto");
        //}
        
        return titulos;
    }
    
    
    
    private DefaultTableModel construirModelTabla() {
        Vector<String> titulo = crearCabezeraTabla();        
        DefaultTableModel modeloTablaFacturas = new DefaultTableModel(titulo, 0);
        return modeloTablaFacturas;
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        //try {
        //    List<UtilidadResult> datos= ServiceFactory.getFactory().getFacturacionServiceIf().consultaUtilidadVentas();
            
        imprimirReporte();
        /*} catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void actualizar() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
    }

//    @Override
    public String getNombre() {
        return "Reporte Facturación";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    private void setearValoresCliente() {

        getTxtCliente().setText(persona.getPersona().getIdentificacion());
        //getLblNombreCliente().setText(persona.getRazonSocial());

        //getLblDireccionCliente().setText(persona.getDireccion());
        //getLblTelefonoCliente().setText(persona.getTelefonoConvencional());  
        //datosAdicionales.put("email",persona.getCorreoElectronico());
        //factura.setCliente(persona);
        //Actualiza la tabla de los datos adicionales
        //cargarDatosAdicionales();
    }

    @Override
    public void iniciar() {
        valoresIniciales();
    }

    @Override
    public void nuevo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void valoresIniciales() {
        
        filtrarReferidos=false;
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getCmbEstado().removeAllItems();
        for (ComprobanteEntity.ComprobanteEnumEstado comprobanteEstado : ComprobanteEntity.ComprobanteEnumEstado.values()) {
            getCmbEstado().addItem(comprobanteEstado);
        }

        
        //////////////CARGAR LOS PUNTOS DE EMISION ///////////////////        
        try {
            getCmbPuntoEmision().removeAllItems();
            List<PuntoEmision> puntosEmisionList=ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorEmpresa(session.getEmpresa()); //TODO: Analizar si los puntos de venta deberia filtrar por sucursales
            UtilidadesComboBox.llenarComboBox(getCmbPuntoEmision(),puntosEmisionList);
            
            List<Sucursal> sucursales= ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbSucursal(),sucursales);
            getCmbSucursal().setSelectedItem(session.getSucursal());
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            persona = null;
            getTxtCliente().setText("...");
            //getLblNombreCliente().setText("..");
            getBtnBuscarCliente().setEnabled(false);
        }
        cargarDocumentosCombo();
        
        getChkTodosReferidos().setSelected(true);
        getBtnBuscarReferido().setEnabled(false);
        referido=null;
        
        getChkPuntoEmisionTodos().setSelected(true);
        getCmbPuntoEmision().setEnabled(false);
        
        /***
         * Cargar los tipos de reporte
         */
        UtilidadesComboBox.llenarComboBox(getCmbTipoReporte(),TipoReporteEnum.values());
        //getCmbTipoReporte().
        
        ///Para el reporte de facturacion no me importa que sean visibles estos campos del referido
        getLblReferido().setVisible(false);
        getTxtReferido().setVisible(false);
        getBtnBuscarReferido().setVisible(false);
        getChkTodosReferidos().setVisible(false);
        getChkReporteAgrupadoReferido().setVisible(false);
    }
    
    public void cargarDocumentosCombo()
    {
        //Agregar la lista de los elementos disponibles para buscar
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.FACTURA);        
        getCmbDocumento().addItem(DocumentoEnum.NOTA_VENTA_INTERNA);
        getCmbDocumento().addItem(DocumentoEnum.NOTA_VENTA);
        getCmbDocumento().addItem(DocumentoEnum.NOTA_CREDITO);
        
        /**
         * Seleccionar el documento por defecto configurado para facturar
         */
        try {
            DocumentoEnum documentoEnum=ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(),ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA,DocumentoEnum.FACTURA);
            if(documentoEnum!=null)
            {
                getCmbDocumento().setSelectedItem(documentoEnum);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    protected void listenerBotones() {
        
       
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona = ((PersonaEstablecimiento) buscarDialogoModel.getResultado());
                if (persona != null) {
                    setearValoresCliente();
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte(); //codigo que genera el reporte
            }
        });
        getBtnLimpiarFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });

        getBtnLimpiarFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });
    }

    private void listenerCombos() {
        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                
                //Si el documento es nota de credito desabilito el panel de control
                if(documentoEnum.equals(DocumentoEnum.NOTA_CREDITO))
                {
                    getPanelOpciones().setEnabled(false);
                    getChkAfectaNotaCredito().setEnabled(false);
                    getChkAfectaNotaDebito().setEnabled(false);
                }
                else
                {
                    getPanelOpciones().setEnabled(true);
                    getChkAfectaNotaCredito().setEnabled(true);
                    getChkAfectaNotaDebito().setEnabled(true);
                }
            }
        });
    }

    protected void listenerChecks() {
        
        
        //String texto= session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
        getChkTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    persona = null;
                    getTxtCliente().setText("...");
                    //getLblNombreCliente().setText("..");
                    getBtnBuscarCliente().setEnabled(false);
                } else {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
        
        getChkPuntoEmisionTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    getCmbPuntoEmision().setEnabled(false);
                } else {
                    getCmbPuntoEmision().setEnabled(true);
                }
            }
        });
        
        getChkSucursalTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    getCmbSucursal().setEnabled(false);
                } else {
                    getCmbSucursal().setEnabled(true);
                }
            }
        });
    }

    private void listenerTablas() {
        JPopupMenu jpopMenuItem=new JPopupMenu();
        JMenuItem itemRide= new JMenuItem("RIDE");
        itemRide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filasSeleccionada[]=getTblDocumentos().getSelectedRows();
                if(filasSeleccionada.length>=0)
                {
                    /*if(filasSeleccionada.length==1)
                    {
                        int filaSeleccionada=filasSeleccionada[0];
                        panelPadre.cambiarCursorEspera();
                        
                        try {
                            //controladorReporte.getData().get
                            String claveAcceso=controladorReporte.getData().get(filaSeleccionada).getClaveAcceso();//                    String claveAcceso = this.factura.getClaveAcceso();
                            //byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,session.getEmpresa()); //TODO: Revisar si es correcto buscar con el nombre de la empresa
                            //JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                            JasperPrint jasperPrint =obtenerJasperPrint(claveAcceso);
                            panelPadre.crearReportePantalla(jasperPrint,controladorReporte.getData().get(filaSeleccionada).getNumeroFactura());
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        panelPadre.cambiarCursorNormal();
                    }
                    else
                    {*/
                    DocumentoEnum documentoSeleccionado=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                    if(documentoSeleccionado.equals(DocumentoEnum.FACTURA))
                    {
                        List<String> clavesAcceso=new ArrayList<String>();
                        for (int fila : filasSeleccionada) 
                        {
                            clavesAcceso.add(controladorReporte.getData().get(fila).getClaveAcceso());//  
                        }
                        generarReporteUnificado(clavesAcceso);
                    } else if(documentoSeleccionado.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
                    {
                        List<Factura> facturas=new ArrayList<Factura>();
                        for (int fila : filasSeleccionada) 
                        {
                            Factura factura= controladorReporte.getDatafact().get(fila);
                            facturas.add(factura);
                        }
                        
                        FacturaModelControlador.imprimirComprobanteVentaLote(facturas, "Notas de venta",true, session, panelPadre);
                        
                    }
                    //}
                }
            }
        });
        jpopMenuItem.add(itemRide);
                
        getTblDocumentos().setComponentPopupMenu(jpopMenuItem);
    }
    
    private void generarReporteUnificado(List<String> comprobantes)
    {
        JasperPrint reporteUnido=null;
        if(comprobantes!=null)
        {
        for (String claveAcceso : comprobantes) {
            
            if(claveAcceso==null || claveAcceso.isEmpty())
            {
                continue;
            }
            
            try {
                    byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,session.getEmpresa());
                    JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);

                    if(reporteUnido==null)
                    {
                        reporteUnido=jasperPrint;
                    }else
                    {
                        List pages = jasperPrint.getPages();
                        for (int j = 0; j < pages.size(); j++) {
                            JRPrintPage nuevasPaginas = (JRPrintPage) pages.get(j);
                            reporteUnido.addPage(nuevasPaginas);
                        }
                        //reporteUnido.addPage(page);
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            JasperViewer.viewReport(reporteUnido,false);
        }
    }
    
    
    
    private JasperPrint obtenerJasperPrint(String claveAcceso) throws RemoteException, IOException, ClassNotFoundException
    {
        byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso, session.getEmpresa()); //TODO: Revisar si es correcto buscar con el nombre de la empresa
        JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
        return jasperPrint;
    }
            
            
    
    
}
