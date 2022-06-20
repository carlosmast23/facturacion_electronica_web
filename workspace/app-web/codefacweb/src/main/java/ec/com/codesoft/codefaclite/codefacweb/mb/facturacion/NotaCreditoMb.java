/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.codefacweb.core.DialogoWeb;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.ParametrosWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesDialogo;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesReporteWeb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.NotaCreditoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.NotaCreditoModelControlador.NotaCreditoModelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.InformacionAdicionalData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_EDITAR;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusquedaNotaCredito;
import ec.com.codesoft.codefaclite.facturacion.busqueda.NotaCreditoBusqueda;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoDocumento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.math.RoundingMode; 
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 *
 * @author Robert
 */
@ManagedBean
@ViewScoped
public class NotaCreditoMb  extends GeneralAbstractMb implements Serializable,NotaCreditoModelInterface {
    private static final String ID_COMPONENTE_MONITOR="monitor";  
    
    private NotaCredito notaCredito;        
    
    private NotaCreditoDetalle notaCreditoDetale;
        
    private Factura facturaSeleccionada;   

    //private FacturaDetalle facturaDetalle; 

    private List<DocumentoEnum> documentos;  
    private List<PuntoEmision> puntosEmision;     
    //private List<SriFormaPago> sriFormaPagosList;

    private Producto productoSeleccionado;  
    private DocumentoEnum documentoSeleccionado;
    private PuntoEmision puntoEmisionSeleccionado;
    private FacturaAdicional facturaAdicionalSeleccionada; 
    private SriFormaPago sriFormaPagoSeleccionado;
    
    //private TipoPaginaEnum tipoPaginaEnum;
    
    /**
     * Variable para saber si se tiene que mostrar o no el boton de cargar desde factura
     */
    private Boolean visualizarCargarProforma;           
    //private String tipoPagina;
    //private String tituloPagina;
    /**
     * TODO:Por el momento seteo con una variable adicional de la fecha porque  
     * en el modelo esta con sql y fuciona correctamente para las consultas pero
     * cuando hago ese cambio en el modelo tengo problemas con otras
     * funcionalidades
     */
    private java.util.Date fechaEmision;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;

    @ManagedProperty(value = "#{parametrosWeb}")
    private ParametrosWeb parametrosWeb;
    
    //private FacturaModelControlador controlador;
    
    private NotaCreditoModelControlador controlador;
    
    private BarraProgreso<NotaCredito> barraProgreso;

    @PostConstruct
    public void init() {
        System.out.println("Creando controlador");
        facturaSeleccionada=new Factura(); //TODO: Da error cuando existe una propiedad vacia
        controlador=new NotaCreditoModelControlador(this,sessionMb.getSession(),MensajeMb.intefaceMensaje);
        String tipoPagina = UtilidadesWeb.buscarParametroPeticion(parametrosWeb.getCampoTipoFacturaOProforma());
        //tipoPaginaEnum = TipoPaginaEnum.getByNombreParametro(tipoPagina);
        
        visualizarCargarProforma=true;
        //if(tipoPaginaEnum.equals(TipoPaginaEnum.PROFORMA))
        //{
            visualizarCargarProforma=false;
        //}
        
        limpiar();

    }

    public void limpiar() {
        notaCredito = new NotaCredito();
        notaCredito.setCliente(new Persona());//Esto solo hago para evitar advertencias
        productoSeleccionado = new Producto();
        notaCreditoDetale = new NotaCreditoDetalle();

        cargarDatosPorDefecto();
        cargarDatosLista();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        try {
            if (!validar()) //Si no valida mando una excepcion para cancelar el ciclo de vida
            {
                throw new ExcepcionCodefacLite("Error grabando el producto");
            }

            setearDatosAdicionales();
            ServiceFactory.getFactory().getFacturacionServiceIf().editar(null);            mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            MensajeMb.mensaje(new CodefacMsj("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO));
            Logger.getLogger(NotaCreditoMb.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
            
        }
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
        //if(true)
        //{
        //    MensajeMb.mensaje("Error al grabar", "Formulario en construcción", FacesMessage.SEVERITY_WARN);
        //    throw new ExcepcionCodefacLite("Error grabando el producto");
        //}
        
        controlador.grabar();
        UtilidadesWeb.ejecutarJavascript("mostrarComprobantesRC();");
        /*try {
            System.out.println("===========>INICIANDO PROCESO GRABAR <==============");     
            if (!validar()) //Si no valida mando una excepcion para cancelar el ciclo de vida 
            {
                throw new ExcepcionCodefacLite("Error grabando el producto");
            }

            setearDatosAdicionales();

            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();

            servicio.grabarProforma(null);
            
            
            //mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            

            //MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            //imprimir();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mostrarMensaje("Error al grabar", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        

    }

    private ComprobanteDataFactura obtenerComprobanteDataFactura() {
        ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(null);
        //comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
        return comprobanteData;
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException 
    {
        try {
            
            ServiceFactory.getFactory().getFacturacionServiceIf().eliminarProforma(null);
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            //mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            nuevo();
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mensaje("Error al grabar", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
    }

    @Override
    public void buscar() {

    }


    
    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new NotaCreditoBusqueda(sessionMb.getSession().getEmpresa());
        
    }
    
    public void abrirDialogoBuscarFactura()
    {
        System.out.println("Abriendo dialogo buscar factura");
        FacturaBusquedaNotaCredito facturaBusqueda = new FacturaBusquedaNotaCredito(sessionMb.getSession().getEmpresa());
        //abrirDialogoBusqueda(facturaBusqueda);
        UtilidadesDialogo.abrirDialogoBusqueda(facturaBusqueda);
        System.out.println("Abriendo dialogo buscar factura");
    }
    
    public void seleccionarProforma(SelectEvent event) {
        Factura proforma = (Factura) event.getObject();
        proforma.setId(null); 
        //cargarDatosCliente(clienteOficina);
        //cargarDatosAdicionalesCliente();
        cargarDatosPantalla(proforma);
        
    }

    public void saludo() {
        System.out.println("Hola todos");
    }

    /*public void abrirDialogoBuscarFactura() {
        
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession().getEmpresa());
        abrirDialogoBusqueda(clienteBusquedaDialogo);
        System.out.println("Abriendo dialogo fin");
    }*/

    /*public void abrirDialogoCrearCliente() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", false);
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 800);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "cliente";
        //PrimeFaces.current().dialog()

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("isDialog", Arrays.asList("true")); //TODO: Parametrizar esta variable
        params.put("tipo", Arrays.asList("cliente"));

        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, params);

    }*/

    public void abrirDialogoBusquedaProducto() {
        ProductoBusquedaDialogo dialogModel = new ProductoBusquedaDialogo(sessionMb.getSession().getEmpresa());
        UtilidadesDialogo.abrirDialogoBusqueda(dialogModel);
        //abrirDialogoBusqueda(dialogModel);
    }

    /*public void abrirDialogoBusqueda(InterfaceModelFind modeloBusqueda) {
        //find();
        System.out.println("Abriendo dialogo busqueda");

        //Establecer objeto de la clase que tiene la implemetacion del dialogo de busqueda que necesito para construir el dialogo web
        //TODO: Solucion temporal porque es una gasto innesario de memoria , buscar otra forma
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("busquedaClase", modeloBusqueda);

        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);           
    } */

    public void seleccionarFactura(SelectEvent event) {
        facturaSeleccionada = (Factura) event.getObject();        
        cargarDatosCliente(facturaSeleccionada.getCliente().getEstablecimientos().get(0));
        
        //cargarDatosAdicionalesCliente();
        controlador.setearDatosFacturaEnNotaCredito(facturaSeleccionada, notaCredito);
        
        //cargarDetalleFactura(facturaSeleccionada);
        
    }

    public void seleccionarClienteCreado(SelectEvent event) {
        Persona cliente = (Persona) event.getObject();
        cargarDatosCliente(cliente.getEstablecimientos().get(0));
        cargarDatosAdicionalesCliente();   
    }

    public void seleccionarProducto(SelectEvent event) {
        productoSeleccionado = (Producto) event.getObject();
        cargarDetalleFacturaAgregar(productoSeleccionado); 
    }
    
    public void seleccionarDatoAdicional(SelectEvent event) {
        ComprobanteAdicional comprobanteAdicional = (ComprobanteAdicional) event.getObject();
        System.out.println("Obteniendo comprobanteAdicional: "+comprobanteAdicional);
        notaCredito.addDatoAdicional(new NotaCreditoAdicional(comprobanteAdicional));
        
        //factura.addDatoAdicional(ESTADO_EDITAR, ESTADO_EDITAR);
        
    }

    private void cargarDetalleFacturaAgregar(Producto productoSeleccionado) {    
        notaCreditoDetale = new NotaCreditoDetalle();
        notaCreditoDetale.setCantidad(BigDecimal.ONE);
        notaCreditoDetale.setDescripcion(productoSeleccionado.getNombre());
        notaCreditoDetale.setPrecioUnitario(productoSeleccionado.getValorUnitario());   
        notaCreditoDetale.setDescuento(BigDecimal.ZERO); 
        notaCreditoDetale.setIvaPorcentaje(productoSeleccionado.getCatalogoProducto().getIva().getTarifa()); //TODO: Revisar este valor porque parece que esta mal seteado y se refiere al iva del producto      
        if(productoSeleccionado.getCatalogoProducto().getIce()!=null)
        {
            //notaCreditoDetale.setIcePorcentaje(productoSeleccionado.getCatalogoProducto().getIce().getPorcentaje());
        }
        //notaCreditoDetale.setTipoDocumentoEnum(TipoDocumentoEnum.LIBRE);//TODO: Por el momento solo dejo como documento por defecto libre
        notaCreditoDetale.setReferenciaId(productoSeleccionado.getIdProducto());
        notaCreditoDetale.setCatalogoProducto(productoSeleccionado.getCatalogoProducto());

    }

    public void agregarProducto() {

        if (notaCreditoDetale.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            MensajeMb.mostrarMensajeDialogo("Error", "Por favor ingrese un valor valido", FacesMessage.SEVERITY_WARN);
            return;
        }
        //facturaDetalle.        
        /*
        notaCreditoDetale.calcularTotalDetalle();
        notaCreditoDetale.calculaIva();
        
        notaCreditoDetale.calcularValorIce();

        notaCreditoDetale.addDetalle(facturaDetalle);
        notaCreditoDetale.calcularTotalesDesdeDetalles();
        notaCreditoDetale = new NotaCreditoDetalle();
        productoSeleccionado = new Producto();*/

    }

    public void eliminarFilaProducto(NotaCreditoDetalle detalle) {
        System.out.println("verificar si se ejecuta el parametro");
        notaCredito.getDetalles().remove(detalle);
        notaCredito.calcularTotalesDesdeDetalles();

    }

    public void cargarDatosCliente(PersonaEstablecimiento establecimiento) {
        if (establecimiento != null) {
            notaCredito.setCliente(establecimiento.getPersona());
            //notaCredito.setSucursal(establecimiento); 
        }
    }

    
    /**
     * Todo: Ver si esta validacion se puede unificar con la primera pantalla
     *
     * @return
     */
    private boolean validar() {
        if (notaCredito.getCliente() != null) {
            if (notaCredito.getCliente().getIdCliente() == null) {
                MensajeMb.mostrarMensajeDialogo("Error Validación", "Seleccione un cliente", FacesMessage.SEVERITY_WARN);
                return false;
            }
        }

        if (notaCredito.getDetalles() == null || notaCredito.getDetalles().size() == 0) {
            MensajeMb.mostrarMensajeDialogo("Error Validación", "No se puede grabar sin detalles", FacesMessage.SEVERITY_WARN);
            return false;
        }

        return true;
    }

    private void setearDatosAdicionales() {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum = notaCredito.getCliente().getTipoIdentificacionEnum();
        String codigoSri = tipoIdentificacionEnum.getCodigoSriVenta();
        //notaCredito.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor

        notaCredito.setEmpresa(sessionMb.getSession().getEmpresa());
        notaCredito.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        notaCredito.setFechaEmision(new java.sql.Date(fechaEmision.getTime()));

        notaCredito.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());

        notaCredito.setObligadoLlevarContabilidad(sessionMb.getSession().getEmpresa().getObligadoLlevarContabilidad());
        notaCredito.setDireccionEstablecimiento(sessionMb.getSession().getSucursal().getDirecccion());
        notaCredito.setDireccionMatriz(sessionMb.getSession().getMatriz().getDirecccion());
        notaCredito.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
        notaCredito.setPuntoEmisionId(puntoEmisionSeleccionado.getId());
        notaCredito.setPuntoEstablecimiento(new BigDecimal(sessionMb.getSession().getSucursal().getCodigoSucursal().toString()));
        notaCredito.setUsuario(sessionMb.getSession().getUsuario());
        notaCredito.setSucursalEmpresa(sessionMb.getSession().getSucursal());
        
        notaCredito.setCodigoDocumento(documentoSeleccionado.getCodigo());
        
        /**
         * Redondeo los valores de los precios unitario de los detalles de la factura
         * Nota: este proceso lo hago al final porque para los totales necesitaba tener los valores exactos de los precios unitarios, pero como ya va a generar la factura puedo redondeal los valores unitario
         */
        for (NotaCreditoDetalle notaCreditoDetalle : notaCredito.getDetalles()) {
            notaCreditoDetalle.setPrecioUnitario(notaCreditoDetalle.getPrecioUnitario().setScale(2,RoundingMode.HALF_UP));
        }

    }

    

    @Override
    public void imprimir() {
        imprimirNotaCredito(notaCredito);
    }
    
      
    
    /**
     * TODO: Ese codigo esta repetido el codigo para imprimir el comprobante
     * @param factura 
     */
    private void imprimirNotaCredito(NotaCredito notaCredito)
    {
        
         //if (this.factura != null && estadoFormulario.equals(ESTADO_EDITAR)) {
        if (notaCredito != null) {
            try {
                String claveAcceso = notaCredito.getClaveAcceso();
                byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(notaCredito.getClaveAcceso(),notaCredito.getEmpresa());
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                
                UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint,notaCredito.getPreimpreso());
                
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando) {
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();

        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {

            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().toString());
            data.setCodigo((detalle.getId()!=null)?detalle.getId().toString():""); //TODO: Solucion temporal para imprimir los nuevos items
            data.setNombre(detalle.getDescripcion().toString());
            data.setPrecioUnitario(detalle.getPrecioUnitario().toString());
            data.setTotal(detalle.getTotal().toString());

            //Datos adicionales para las proformas
            data.setDescuento(detalle.getDescuento().toString());
            data.setDescripcion(detalle.getDescripcion());

            dataReporte.add(data);
        }
        return dataReporte;
    }

    /**
     * TODO: Ver alguna forma de unir con el metodo de factura de escritorio
     * porque se repite los metodos
     *
     * @param facturaProcesando
     * @return
     */
    public Map<String, Object> getMapParametrosReporte(Factura facturaProcesando) {
        Map<String, Object> mapParametros = getMapParametrosReporteCommun(facturaProcesando); //To change body of generated methods, choose Tools | Templates.
        mapParametros.put("estado", notaCredito.getEstadoEnum().getNombre());
        //subtotal_cero
        //Datos adicionales para las proformas
        mapParametros.put("secuencial", facturaProcesando.getSecuencial().toString());
        mapParametros.put("cliente_nombres", facturaProcesando.getRazonSocial());
        mapParametros.put("cliente_identificacion", facturaProcesando.getIdentificacion());
        mapParametros.put("fecha_emision", facturaProcesando.getFechaEmision().toString());
        mapParametros.put("subtotal_cero", facturaProcesando.getSubtotalSinImpuestos().toString());
        mapParametros.put("descuento", facturaProcesando.getDescuentoImpuestos().add(facturaProcesando.getDescuentoSinImpuestos()).toString());
        mapParametros.put("iva_porcentaje", sessionMb.getSession().obtenerIvaActual().toString());
        mapParametros.put("informacionAdicionalList", obtenerDatosAdicionales());

        try {
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionalesA4.jrxml"));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_OTRO",reportDatosAdicionales);
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionales.jrxml"));
            reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_ADICIONAL",reportDatosAdicionales);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

        //SUBREPORT_INFO_ADICIONAL
        // mapParametros.put("estado",facturaProcesando.getEstadoEnum());
        return mapParametros;
    }

    public Map<String, Object> getMapParametrosReporteCommun(Factura facturaProcesando) {
        //map de los parametros faltantes
        Map<String, Object> mapParametros = new HashMap<String, Object>();
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

        return mapParametros;

    }

    private List<InformacionAdicionalData> obtenerDatosAdicionales() {
        List<InformacionAdicionalData> datosAdicionalesData = new ArrayList<InformacionAdicionalData>();
        if (notaCredito.getDatosAdicionales() != null) {
            for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) {
                InformacionAdicionalData data = new InformacionAdicionalData();
                data.setNombre(datoAdicional.getCampo());
                data.setValor(datoAdicional.getValor());
                datosAdicionalesData.add(data);
            }
        }
        return datosAdicionalesData;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public FacturaAdicional getFacturaAdicionalSeleccionada() {
        return facturaAdicionalSeleccionada;
    }

    public void setFacturaAdicionalSeleccionada(FacturaAdicional facturaAdicionalSeleccionada) {
        this.facturaAdicionalSeleccionada = facturaAdicionalSeleccionada;
    }
    
    

    @Override
    public String titulo() {
        return "Nota de Crédito";
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        limpiar(); //Llamo a este metodo para que se seteen en blanco las variables
        System.err.println("Ejecutando metodo de nuevo");
    }

    public void filaEditaTablaEvent(RowEditEvent event) {
        FacturaDetalle detalleEditado = (FacturaDetalle) event.getObject();
        detalleEditado.calcularTotalDetalle();
        detalleEditado.calculaIva();
        notaCredito.calcularTotalesDesdeDetalles();
        //FacesMessage msg = new FacesMessage("Car Edited", ((FacturaDeta) event.getObject()).getId());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        System.err.println("Evento editar la fila ");
        System.err.println("cantidad editada: " + notaCredito.getDetalles().get(0).getCantidad());
        System.err.println("cantidad editada: " + notaCredito.getDetalles().get(0).getTotal());
        System.err.println("total final: " + notaCredito.getTotal());

        //PrimeFaces.current().ajax().update(":formulario:tblProductoDetalles");
    }

    public void verificarPagina() {
        //this=new FacturaMb()
        //System.out.println(">>>> Tipo Pagina >>" + tipoPagina);
    }

    public ParametrosWeb getParametrosWeb() {
        return parametrosWeb;
    }

    public void setParametrosWeb(ParametrosWeb parametrosWeb) {
        this.parametrosWeb = parametrosWeb;
    }


    private void cargarDatosPorDefecto() {
        fechaEmision = UtilidadesFecha.getFechaHoy();
        documentos = new ArrayList<DocumentoEnum>();

        //TODO: Mejorar este metodo para cargar otras formas de pago de forma dinamica , pensar en la solucion de permisos de documentos
        documentos.add(DocumentoEnum.FACTURA);
       
    }
    
    public void abrirDialogoDatosAdicionales()
    {
        UtilidadesWeb.abrirDialogo("datos_adicionales_dialogo",250); 
    }
    
    public void eliminarDatoAdicional()
    {
        notaCredito.getDatosAdicionales().remove(facturaAdicionalSeleccionada); 
    }

    private void cargarDatosAdicionalesCliente() {
        notaCredito.eliminarTodosDatosAdicionales(); //TODO: Por el momento solo elimino todos los datos adicionales para no hacerme problema
        if(notaCredito.getCliente().getCorreoElectronico()!=null && !notaCredito.getCliente().getCorreoElectronico().isEmpty())
        {
            //new FacturaAdicional(ESTADO_EDITAR, ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO)
            notaCredito.addDatoAdicional(new NotaCreditoAdicional(notaCredito.getCliente().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO));
        }
    }

    public NotaCredito obtenerNotaCredito() {
        return notaCredito;
    }

    public java.sql.Date obtenerCmbFechaCompra() {
        return UtilidadesFecha.castDateUtilToSql(facturaSeleccionada.getFechaEmision());
    }

    public java.sql.Date obtenerDateFechaEmision() {
        return UtilidadesFecha.castDateUtilToSql(fechaEmision);
    }

    public TipoDocumentoEnum obtenerCmbTipoDocumento() {
        return TipoDocumentoEnum.VENTA;
    }

    public String obtenerTxtMotivoAnulacion() {
        return notaCredito.getRazonModificado();
    }

    public String obtenerTxtPreimpresoProveedor() {
        return facturaSeleccionada.getPreimpreso();
    }

    public PuntoEmision obtenerPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public ClienteInterfaceComprobante obtenerClienteInterfaceComprobante(NotaCredito notaCredito) {
        try {
            barraProgreso = new BarraProgreso<NotaCredito>(notaCredito, new BarraProgreso.InterfazBoton<NotaCredito>() {
                public void alertaListener(String mensajeAlerta) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                public void imprimirListener(NotaCredito dato) {
                    
                    try {
                        dato = (NotaCredito) ServiceFactory.getFactory().getNotaCreditoServiceIf().buscarPorId(dato.getId()); //Vuelvo a consultar porque el antigua dato no tenia la clave de acceso
                        imprimirNotaCredito(dato);
                        
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                public String tituloBarra(NotaCredito dato) {
                    return dato.getPreimpreso();
                }
            });
            
            ProformaMb.InterfazCallBack interfazCallBack = new ProformaMb.InterfazCallBack(barraProgreso, sessionMb);
            
            //new ProformaMb.InterfazCallBack(barraProgreso, sessionMb);
            //return new ProformaMb.InterfazCallBack(barraProgreso, sessionMb);
            return interfazCallBack;
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void procesarMonitor(NotaCredito notaCredito) {
        
        try {
            sessionMb.getBarraProgresoList().add(barraProgreso);
            sessionMb.setActualizarMonitor(true);
            nuevo();
            UtilidadesWeb.ejecutarJavascript("PF('poll').start();"); //iniciar el comenten de actualizar monitor
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(NotaCreditoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosPantalla(Object entidad) {
        notaCredito = (NotaCredito) entidad;
        fechaEmision = new java.sql.Date(notaCredito.getFechaEmision().getTime());
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public enum TipoPaginaEnum {  
        FACTURA("Factura", "factura"),
        PROFORMA("Proforma", "proforma");

        private String nombre;
        private String nombreParametro;

        private TipoPaginaEnum(String nombre, String nombreParametro) {
            this.nombre = nombre;
            this.nombreParametro = nombreParametro;
        }

        public String getNombre() {
            return nombre;
        }

        public String getNombreParametro() {
            return nombreParametro;
        }

        public static TipoPaginaEnum getByNombre(String nombre) {
            for (TipoPaginaEnum value : TipoPaginaEnum.values()) {
                if (value.getNombre().equals(nombre)) {
                    return value;
                }
            }
            return null;
        }

        public static TipoPaginaEnum getByNombreParametro(String nombre) {
            for (TipoPaginaEnum value : TipoPaginaEnum.values()) {
                if (value.getNombreParametro().equals(nombre)) {
                    return value;
                }
            }
            return null;
        }
    }

    public class InterfazCallBack extends UnicastRemoteObject implements ClienteInterfaceComprobante {

        private BarraProgreso barraProgreso;
        
        public InterfazCallBack(BarraProgreso barraProgreso) throws RemoteException {
            this.barraProgreso=barraProgreso;
        }
        

        public void termino(byte[] byteJasperPrint, List<AlertaComprobanteElectronico> alertas) throws RemoteException {
            barraProgreso.setPorcentaje(100);
            sessionMb.setActualizarMonitor(false);
        }

        public void iniciado() throws RemoteException {
            barraProgreso.setPorcentaje(0);
            sessionMb.setActualizarMonitor(true);
        }

        public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
            if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
                barraProgreso.setPorcentaje(20);                
                //monitorData.getBarraProgreso().setValue(20);

            }

            if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
                barraProgreso.setPorcentaje(30);
                //monitorData.getBarraProgreso().setValue(30);
            }

            if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
                barraProgreso.setPorcentaje(50);
                //monitorData.getBarraProgreso().setValue(50);
            }

            if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
                barraProgreso.setPorcentaje(65);
                //monitorData.getBarraProgreso().setValue(65);

            }

            if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
                barraProgreso.setPorcentaje(80);
                //monitorData.getBarraProgreso().setValue(80);

            }

            if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
                barraProgreso.setPorcentaje(90);
                //monitorData.getBarraProgreso().setValue(90);
            }

            if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
                barraProgreso.setPorcentaje(100);
                sessionMb.setActualizarMonitor(false);
                //monitorData.getBarraProgreso().setValue(100);

            }
            
            //UtilidadesWeb.actualizaComponente(ID_COMPONENTE_MONITOR);
        }

        public void error(ComprobanteElectronicoException cee, String claveAcceso) throws RemoteException {
            System.out.println("error ...");
            sessionMb.setActualizarMonitor(false);
        }
    };



    public SriFormaPago getSriFormaPagoSeleccionado() {
        return sriFormaPagoSeleccionado;
    }

    public void setSriFormaPagoSeleccionado(SriFormaPago sriFormaPagoSeleccionado) {
        this.sriFormaPagoSeleccionado = sriFormaPagoSeleccionado;
    }

    public List<DocumentoEnum> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoEnum> documentos) {
        this.documentos = documentos;
    }

    public DocumentoEnum getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(DocumentoEnum documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    private void cargarDatosLista() {
        
        try {
            List<PuntoEmisionUsuario> puntosEmisionUsuario=ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivoPorUsuario(sessionMb.getSession().getUsuario(),sessionMb.getSession().getSucursal());
            List<PuntoEmision> puntosEmision=new ArrayList<PuntoEmision>();
            for (PuntoEmisionUsuario puntoEmisionUsuario : puntosEmisionUsuario) {
                puntosEmision.add(puntoEmisionUsuario.getPuntoEmision());
            }
            //puntosEmision = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sessionMb.getSession().getSucursal());
            this.puntosEmision=puntosEmision;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<PuntoEmision> getPuntosEmision() {
        return puntosEmision;
    }

    public void setPuntosEmision(List<PuntoEmision> puntosEmision) {
        this.puntosEmision = puntosEmision;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }

   
    

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public NotaCreditoDetalle getNotaCreditoDetale() {
        return notaCreditoDetale;
    }

    /**
     * ==========================> METODOS GET AND SET
     * <=============================
     */
    public void setNotaCreditoDetale(NotaCreditoDetalle notaCreditoDetale) {
        this.notaCreditoDetale = notaCreditoDetale;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

    public Boolean getVisualizarCargarProforma() {
        return visualizarCargarProforma;
    }

    public void setVisualizarCargarProforma(Boolean visualizarCargarProforma) {
        this.visualizarCargarProforma = visualizarCargarProforma;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ///                        METODOS PERSONALIZADOS
    ////////////////////////////////////////////////////////////////////////////
    public void imprimirEjemplo()
    {
        System.out.println("Ejemplo imprimir ............. ");   
        System.out.println(notaCredito);
        System.out.println(notaCredito.getCliente().getRazonSocial());
        System.out.println(notaCredito.getRazonSocial()); 
        System.out.println(notaCredito.getDireccion());
        
    }
    
    
}
