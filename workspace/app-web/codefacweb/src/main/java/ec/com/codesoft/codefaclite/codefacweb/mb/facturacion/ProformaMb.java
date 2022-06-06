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
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.FacturaModelInterface;
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
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.math.RoundingMode;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped 
public class ProformaMb extends GeneralAbstractMb implements FacturaModelInterface, Serializable {

    private static final String ID_COMPONENTE_MONITOR="monitor";  
    
        
    private Factura factura;    

    /**
     * Esta referencia me permite saber cual factura esta seleccionada para editar 
     */
    private FacturaDetalle facturaDetalle; 

    private List<Producto.PrecioVenta> precioVentaList;
    private List<DocumentoEnum> documentos;     
    private List<PuntoEmision> puntosEmision;    
    private EnumSiNo[] enumSiNoList;
    //private List<SriFormaPago> sriFormaPagosList;

    private Producto productoSeleccionado;  
    private DocumentoEnum documentoSeleccionado;
    private PuntoEmision puntoEmisionSeleccionado;
    private FacturaAdicional facturaAdicionalSeleccionada;
    private SriFormaPago sriFormaPagoSeleccionado;
    private Producto.PrecioVenta precioVentaSeleccionado;
    
    private TipoPaginaEnum tipoPaginaEnum;
    
    /**
     * Variable para saber si al momento de agregar un producto en la vista este ya incluye iva
     */
    private EnumSiNo incluyeIvaDetalleEnum;
    
    private Boolean cmbIvaDetalleEnable;
    /**
     * Variable para saber si se tiene que mostrar o no el boton de cargar desde factura
     */
    private Boolean visualizarCargarProforma;
    
    private Boolean descuentoPorcentaje;
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
    
    private FacturaModelControlador controlador;
    
        /**
     * Referencia para saber si estan en modo de editar un detalle o no 
     */
    private Boolean modoEdicionDetalle;

    @PostConstruct
    public void init() {
        System.out.println("Creando controlador");
        controlador=new FacturaModelControlador(sessionMb.getSession(),this,MensajeMb.intefaceMensaje);
        String tipoPagina = UtilidadesWeb.buscarParametroPeticion(parametrosWeb.getCampoTipoFacturaOProforma());
        tipoPaginaEnum = TipoPaginaEnum.getByNombreParametro(tipoPagina);
        
        visualizarCargarProforma=true;
        
        //Si no puede encontrar el tipo de la pantala por defecto abre como factura
        if(tipoPaginaEnum==null)
        {
            tipoPaginaEnum=TipoPaginaEnum.FACTURA;
        }
        
        if(tipoPaginaEnum.equals(TipoPaginaEnum.PROFORMA))
        {
            visualizarCargarProforma=false;
        }
        
        limpiar();

    }

    public void limpiar() {
        factura = new Factura();
        factura.setCliente(new Persona());//Esto solo hago para evitar advertencias
        productoSeleccionado = new Producto();
        facturaDetalle = new FacturaDetalle();
        incluyeIvaDetalleEnum=EnumSiNo.SI;
        cmbIvaDetalleEnable=false;
        modoEdicionDetalle=false;
        
        cargarDatosPorDefecto();
        cargarDatosLista();
        cargarCombosBox();
    }
    
    private void cargarCombosBox()
    {
        enumSiNoList=EnumSiNo.values();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        try {
            if (!validar()) //Si no valida mando una excepcion para cancelar el ciclo de vida
            {
                throw new ExcepcionCodefacLite("Error grabando el producto");
            }

            setearDatosAdicionales();
            factura=ServiceFactory.getFactory().getFacturacionServiceIf().editarProforma(factura);
            mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
        //Por el momento descativo estos datos        
        /*if(true)
        {
             MensajeMb.mensaje("Alerta","Pantalla en contrucción",FacesMessage.SEVERITY_WARN);
            throw new ExcepcionCodefacLite("Pantalla en contrucción");
        }*/
        
        try {
            System.out.println("===========>INICIANDO PROCESO GRABAR <==============");     
            if (!validar()) //Si no valida mando una excepcion para cancelar el ciclo de vida 
            {
                throw new ExcepcionCodefacLite("Error grabando el producto");
            }

            setearDatosAdicionales();
            correcionesAdicionales();

            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();

            if (tipoPaginaEnum.equals(TipoPaginaEnum.PROFORMA)) {
                factura = servicio.grabarProforma(factura);
                mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.GUARDADO);

            }
            else if (tipoPaginaEnum.equals(TipoPaginaEnum.FACTURA)) {
                factura = servicio.grabar(factura);
                
                //TODO:Toca validar que solo sea para electronica unir con el controlador para la factura
                if (documentoSeleccionado.equals(DocumentoEnum.FACTURA)) 
                {
                    BarraProgreso<Factura> barraProgreso = new BarraProgreso<Factura>(factura, new BarraProgreso.InterfazBoton<Factura>() {
                        
                        public void alertaListener(String mensajeAlerta) {
                            MensajeMb.mensaje("Alerta",mensajeAlerta,FacesMessage.SEVERITY_WARN);
                        }

                        public void imprimirListener(Factura dato) {

                            try {
                                dato = (Factura) ServiceFactory.getFactory().getFacturacionServiceIf().buscarPorId(dato.getId()); //Vuelvo a consultar porque el antigua dato no tenia la clave de acceso
                                if (ParametroUtilidades.comparar(sessionMb.getSession().getEmpresa(), ParametroCodefac.IMPRESORA_TICKETS_VENTAS, EnumSiNo.SI)) {
                                    imprimirTicket(dato);
                                } else {
                                    imprimirFactura(dato); 
                                }

                            } catch (RemoteException ex) {
                                Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        public String tituloBarra(Factura dato) {
                            return dato.getPreimpreso();  
                        }
                    });
 
                    ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                    comprobanteServiceIf.procesarComprobante(
                            obtenerComprobanteDataFactura(),
                            factura,
                            sessionMb.getSession().getUsuario(),
                            new InterfazCallBack(barraProgreso,sessionMb));

                    sessionMb.getBarraProgresoList().add(barraProgreso);
                    sessionMb.setActualizarMonitor(true); //Variable para indicar que no esta actualiado el monitor
                    nuevo();
                    UtilidadesWeb.ejecutarJavascript("PF('poll').start();"); //iniciar el actualizador en la pantalla 
                    UtilidadesWeb.ejecutarJavascript("mostrarComprobantesRC();");
                    //UtilidadesWeb.actualizaComponente(":formulario:panelSecundario:barMonitor");       
                    MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                } else if(documentoSeleccionado.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
                {
                    mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                }

                

            } 

            //MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            //imprimir();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mensaje("Error al grabar", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex); 
        }

    }

    private ComprobanteDataFactura obtenerComprobanteDataFactura() {
        ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
        comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
        return comprobanteData;
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException 
    {
        try {
            
            ServiceFactory.getFactory().getFacturacionServiceIf().eliminarProforma(factura);
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
        if (tipoPaginaEnum.equals(tipoPaginaEnum.PROFORMA)) {
            return new ProformaBusqueda(sessionMb.getSession().getEmpresa(),true);
        } else if (tipoPaginaEnum.equals(tipoPaginaEnum.FACTURA)) {
            return new FacturaBusqueda(sessionMb.getSession().getSucursal(),sessionMb.getSession().getUsuario());
        }
        return null;
    }
    
    public void abrirDialogoBuscarProforma()
    {
        System.out.println("Abriendo dialogo proforma init");
        ProformaBusqueda proformaBusqueda = new ProformaBusqueda(sessionMb.getSession().getEmpresa());
        //abrirDialogoBusqueda(proformaBusqueda);
        UtilidadesDialogo.abrirDialogoBusqueda(proformaBusqueda);
        System.out.println("Abriendo dialogo proforma fin");
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

    public void abrirDialogoBuscarCliente() {
        System.out.println("Abriendo dialogo init");
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession());
        clienteBusquedaDialogo.setPrimeraColumnaNombre(true);
        //abrirDialogoBusqueda(clienteBusquedaDialogo);
        UtilidadesDialogo.abrirDialogoBusqueda(clienteBusquedaDialogo);
        System.out.println("Abriendo dialogo fin");
    }

    public void abrirDialogoCrearCliente() {
        /*Map<String, Object> options = new HashMap<String, Object>();
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

        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, params);*/
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("tipo", Arrays.asList("cliente"));

        UtilidadesDialogo.abrirDialogoFormulario("cliente", params);

    }

    public void abrirDialogoBusquedaProducto() {
        ProductoBusquedaDialogo dialogModel = new ProductoBusquedaDialogo(sessionMb.getSession().getEmpresa());
        //abrirDialogoBusqueda(dialogModel);
        UtilidadesDialogo.abrirDialogoBusqueda(dialogModel);
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
        options.put("resizable", true);
        options.put("draggable", false);
        options.put("modal", true);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);           
    } */

    public void seleccionarCliente(SelectEvent event) {
        PersonaEstablecimiento clienteOficina = (PersonaEstablecimiento) event.getObject();
        cargarDatosCliente(clienteOficina);
        cargarDatosAdicionalesCliente();
        
    }

    public void seleccionarClienteCreado(SelectEvent event) {
        Persona cliente = (Persona) event.getObject();
        cargarDatosCliente(cliente.getEstablecimientos().get(0));
        cargarDatosAdicionalesCliente();   
    }

    public void seleccionarProducto(SelectEvent event) {
        System.out.println("Metodo ejecutando seleccionar producto");
        System.out.println("Documento seleccionado : "+documentoSeleccionado.getNombre());
        productoSeleccionado = (Producto) event.getObject(); 
        controlador.agregarProductoVista(productoSeleccionado,null);
        //cargarDetalleFacturaAgregar(productoSeleccionado); 
    }
    
    public void seleccionarDatoAdicional(SelectEvent event) {
        ComprobanteAdicional comprobanteAdicional = (ComprobanteAdicional) event.getObject();
        System.out.println("Obteniendo comprobanteAdicional: "+comprobanteAdicional);
        factura.addDatoAdicional(new FacturaAdicional(comprobanteAdicional));
        
        //factura.addDatoAdicional(ESTADO_EDITAR, ESTADO_EDITAR);
        
    }


    public void agregarProducto() {

        try {
            /*if (facturaDetalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            MensajeMb.mostrarMensajeDialogo("Error", "Por favor ingrese un valor valido", FacesMessage.SEVERITY_WARN);
            return;
            }
            //facturaDetalle.
            facturaDetalle.calcularTotalDetalle();
            facturaDetalle.calculaIva();
            
            facturaDetalle.calcularValorIce();
            
            factura.addDetalle(facturaDetalle);
            factura.calcularTotalesDesdeDetalles();
            facturaDetalle = new FacturaDetalle();
            productoSeleccionado = new Producto();*/
            controlador.agregarDetallesFactura(facturaDetalle,documentoSeleccionado,null);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * TODO: Unificar este metodo con el de FActuracionModel para tener una unica logica
     * @param detalle 
     */
    public void eliminarFilaProducto(FacturaDetalle detalle) {
        System.out.println("verificar si se ejecuta el parametro");
        factura.getDetalles().remove(detalle);
        factura.calcularTotalesDesdeDetalles();
        //Actualizar las forma de pago
        controlador.cargarFormaPago();

    }

    public void cargarDatosCliente(PersonaEstablecimiento establecimiento) {
        if (establecimiento != null) {
            //TODO: La parte de la identificación solo dejo seteando de forma temporal esto debe estar en el servidor, pero por algun motivo luego se quita ese dato de identificación
            factura.setIdentificacion(establecimiento.getPersona().getIdentificacion());
            
            factura.setCliente(establecimiento.getPersona());
            factura.setSucursal(establecimiento); 
        }
    }

    
    /**
     * Todo: Ver si esta validacion se puede unificar con la primera pantalla
     *
     * @return
     */
    private boolean validar() {
        if (factura.getCliente() != null) {
            if (factura.getCliente().getIdCliente() == null) {
                MensajeMb.mostrarMensajeDialogo("Error Validación", "Seleccione un cliente", FacesMessage.SEVERITY_WARN);
                return false;
            }
        }

        if (factura.getDetalles() == null || factura.getDetalles().size() == 0) {
            MensajeMb.mostrarMensajeDialogo("Error Validación", "No se puede grabar sin detalles", FacesMessage.SEVERITY_WARN);
            return false;
        }
        
        if(!UtilidadesFecha.validarfechaDentroDeRango(UtilidadesFecha.castDateToTimeStamp(fechaEmision),ParametrosSistemaCodefac.MAX_DIAS_FACURAR))
        {
            MensajeMb.mostrarMensajeDialogo("Error Validación", "La fecha de emisión es superior a la fecha de autorización del Sri", FacesMessage.SEVERITY_WARN);
            return false;
        }
        
        return true;
    }

    private void setearDatosAdicionales() {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum = factura.getCliente().getTipoIdentificacionEnum();
        String codigoSri = tipoIdentificacionEnum.getCodigoSriVenta();
        factura.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor

        factura.setEmpresa(sessionMb.getSession().getEmpresa());
        factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        factura.setFechaEmision(new java.sql.Date(fechaEmision.getTime()));

        factura.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());

        factura.setObligadoLlevarContabilidad(sessionMb.getSession().getEmpresa().getObligadoLlevarContabilidad());
        factura.setDireccionEstablecimiento(sessionMb.getSession().getSucursal().getDirecccion());
        factura.setDireccionMatriz(sessionMb.getSession().getMatriz().getDirecccion());
        factura.setPuntoEmision((puntoEmisionSeleccionado!=null)?puntoEmisionSeleccionado.getPuntoEmision():null);
        factura.setPuntoEmisionId((puntoEmisionSeleccionado!=null)?puntoEmisionSeleccionado.getId():null);
        factura.setPuntoEstablecimiento(new BigDecimal(sessionMb.getSession().getSucursal().getCodigoSucursal().toString()));
        factura.setUsuario(sessionMb.getSession().getUsuario());
        factura.setSucursalEmpresa(sessionMb.getSession().getSucursal());
        factura.setCodigoOrigenTransaccionEnum(Factura.OrigenTransaccionEnum.APLICACION_WEB);
        
        if(tipoPaginaEnum.equals(TipoPaginaEnum.PROFORMA))
        {
            factura.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
        }else
        {
            factura.setCodigoDocumento(documentoSeleccionado.getCodigo());
        }
        
        //todo: Solucion temporal
        factura.getCliente().setIdentificacion(factura.getIdentificacion());
        
        
        /**
         * Redondeo los valores de los precios unitario de los detalles de la factura
         * Nota: este proceso lo hago al final porque para los totales necesitaba tener los valores exactos de los precios unitarios, pero como ya va a generar la factura puedo redondeal los valores unitario
         */
        //for (FacturaDetalle facturaDetalle : factura.getDetalles()) {
        //    facturaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario().setScale(2,RoundingMode.HALF_UP));
        //}

    }

    

    @Override
    public void imprimir() {
        if(tipoPaginaEnum.equals(tipoPaginaEnum.PROFORMA))
        {
            imprimirProforma();
        }else if(tipoPaginaEnum.equals(tipoPaginaEnum.FACTURA))
        {
            try {
                if (ParametroUtilidades.comparar(sessionMb.getSession().getEmpresa(), ParametroCodefac.IMPRESORA_TICKETS_VENTAS, EnumSiNo.SI)) {
                    imprimirTicket(factura);
                } else {
                    imprimirFactura(factura);
                }
                //imprimirFactura(factura);s
            } catch (RemoteException ex) {
                Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void imprimirProforma()
    {
        System.out.println("Imprimir ...");
        List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);
        Map<String, Object> mapParametros = getMapParametrosReporte(factura);
        InputStream path = RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("proforma.jrxml");
        JasperPrint jasperPrint = ReporteCodefac.construirReporte(path, mapParametros, dataReporte, sessionMb.getSession(), "Proforma", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4);
        //JasperPrint jasperPrint = JasperFillManager.fillReport(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
        UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint,"Presupuesto "+factura.getSecuencial()+".pdf");
        /*mapParametros = ReporteCodefac.mapReportePlantilla(OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4, sessionMb.getSession());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        //byte[] bytes = JasperRunManager.runReportToPdf(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.setHeader("Content-disposition", "inline; filename=proforma");
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        
        ServletOutputStream outStream = response.getOutputStream();
        baos.writeTo(outStream);
        //outStream.write(baos, 0, baos.size());
        
        outStream.flush();
        outStream.close();
        
        FacesContext.getCurrentInstance().responseComplete();*/
    }
    
    
    /**
     * TODO: Ese codigo esta repetido el codigo para imprimir el comprobante
     * @param factura 
     */
    private void imprimirFactura(Factura factura)
    {
        try {
            String claveAcceso = factura.getClaveAcceso();
            byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(factura.getClaveAcceso(),factura.getEmpresa());
            JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint,factura.getPreimpreso()+".pdf");
            
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }
    
        /**
     * TODO: Se debe unir de alguna forma con la de la pantalla de factura para no tener codigo duplicado
     * Este metodo es para imprimir el comprobante pequeño
     * @param factura 
     */
    private void imprimirTicket(Factura factura)
    {
        //MOnitorCom
        Map<String, Object> mapParametros = controlador.getMapParametrosReporte(factura);
        List<ComprobanteVentaData> dataReporte = controlador.getDetalleDataReporte(factura);
        InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta_ticket.jrxml");
        String nombreReporte = factura.getCodigoDocumentoEnum().getNombre();
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
        {
            nombreReporte="Nota de Venta";
        }
        JasperPrint jasperPrint = ReporteCodefac.construirReporte(path, mapParametros, dataReporte, sessionMb.getSession(),nombreReporte, OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.TICKET);
        UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint,factura.getPreimpreso()+".pdf");
        
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
    
    private JasperReport buscarReporteDatosAdicionales(RecursoCodefac recursoCodefac,String nombreReporte)
    {
        
        InputStream inputStream= null;
        try {
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(recursoCodefac,nombreReporte));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            //mapParametros.put("SUBREPORT_INFO_OTRO",reportDatosAdicionales);
            return reportDatosAdicionales;
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
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
        mapParametros.put("estado", factura.getEnumEstadoProforma().getNombre());
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
        
        JasperReport reportDatosAdicionales =buscarReporteDatosAdicionales(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS, "datos_adicionalesA4.jrxml");
        mapParametros.put("SUBREPORT_INFO_OTRO",reportDatosAdicionales);
        
        reportDatosAdicionales=buscarReporteDatosAdicionales(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS, "datos_adicionales.jrxml");
        mapParametros.put("SUBREPORT_INFO_ADICIONAL",reportDatosAdicionales);
        
        //reportDatosAdicionales=buscarReporteDatosAdicionales(RecursoCodefac.JASPER, "pl_firmas_factura.jrxml");
        //mapParametros.put("pl_firmas_factura",reportDatosAdicionales);
        

        /*try {
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
        }*/

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
        if (factura.getDatosAdicionales() != null) {
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
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
        return tipoPaginaEnum.getNombre();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        limpiar(); //Llamo a este metodo para que se seteen en blanco las variables
        System.err.println("Ejecutando metodo de nuevo");
    }

    /**
     * TODO: Unificar este metodo con el de FacturacionModel para tener una sola logica 
     * @param event 
     */
    public void filaEditaTablaEvent(RowEditEvent event) {
        FacturaDetalle detalleEditado = (FacturaDetalle) event.getObject();
        /*detalleEditado.calcularTotalDetalle();
        detalleEditado.calculaIva();
        factura.calcularTotalesDesdeDetalles();
        //FacesMessage msg = new FacesMessage("Car Edited", ((FacturaDeta) event.getObject()).getId());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        System.err.println("Evento editar la fila ");
        System.err.println("cantidad editada: " + factura.getDetalles().get(0).getCantidad());
        System.err.println("cantidad editada: " + factura.getDetalles().get(0).getTotal());
        System.err.println("total final: " + factura.getTotal());*/
        //controlador.agregarDetallesFactura(detalleEditado);
        /**
         * TODO: Lo correcto sera en vez de usar la logica de abajo unir con  controlador.agregarDetallesFactura(detalleEditado)
         * que tiene logica adicional de validaciones y otras cosas mas
         */
        detalleEditado.calcularTotalesDetallesFactura();
        //controlador.calcularTotalesDetalles(detalleEditado);
        controlador.cargarTotales();
        controlador.cargarFormaPago();
        
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

    public TipoPaginaEnum getTipoPaginaEnum() {
        return tipoPaginaEnum;
    }

    public void setTipoPaginaEnum(TipoPaginaEnum tipoPaginaEnum) {
        this.tipoPaginaEnum = tipoPaginaEnum;
    }

    private void cargarDatosPorDefecto() {
        fechaEmision = UtilidadesFecha.getFechaHoy();
        documentos = new ArrayList<DocumentoEnum>();

        //TODO: Mejorar este metodo para cargar otras formas de pago de forma dinamica , pensar en la solucion de permisos de documentos
        if (tipoPaginaEnum.equals(TipoPaginaEnum.PROFORMA)) 
        {
            documentos.add(DocumentoEnum.PROFORMA);
        } 
        else if (tipoPaginaEnum.equals(TipoPaginaEnum.FACTURA)) 
        {
            //System.out.println("Obteniendo lista de documentos");
            documentos=controlador.buscarDocumentosFactura();
            //System.out.println(documentos);
            //UtilidadesComboBox.
            //documentos.add(DocumentoEnum.FACTURA);
        }
    }
    
    public void abrirDialogoDatosAdicionales()
    {
        UtilidadesWeb.abrirDialogo("datos_adicionales_dialogo",250); 
    }
    
    public void eliminarDatoAdicional()
    {
        factura.getDatosAdicionales().remove(facturaAdicionalSeleccionada); 
    }

    private void cargarDatosAdicionalesCliente() {
        factura.eliminarTodosDatosAdicionales(); //TODO: Por el momento solo elimino todos los datos adicionales para no hacerme problema
        if(factura.getCliente().getCorreoElectronico()!=null && !factura.getCliente().getCorreoElectronico().isEmpty())
        {
            factura.addDatoAdicional(new FacturaAdicional(factura.getCliente().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO));
        }
    }

    public DocumentoEnum obtenerDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void cargarPrecios(Producto producto) {
        //TODO: Metodo donde se deben cargar los diferentes precios 
        precioVentaList=new ArrayList<Producto.PrecioVenta>(); 
        for (Producto.PrecioVenta precioVenta : producto.obtenerPreciosVenta()) {
            precioVentaList.add(precioVenta);
        }
    }

    public void setearValoresProducto(BigDecimal valorUnitario, String descripcion, String codigo, CatalogoProducto catologoProducto) {
        //Metodo para setear los valores en la pantalla
    }

    public void cargarDatosDetalleVista(BigDecimal valorUnitario, String descripcion, String codigo, CatalogoProducto catologoProducto) {
        /*facturaDetalle = new FacturaDetalle();
        facturaDetalle.setCantidad(BigDecimal.ONE);
        facturaDetalle.setDescripcion(productoSeleccionado.getNombre());
        facturaDetalle.setPrecioUnitario(productoSeleccionado.getValorUnitario());
        facturaDetalle.setDescuento(BigDecimal.ZERO);
        facturaDetalle.setIvaPorcentaje(productoSeleccionado.getCatalogoProducto().getIva().getTarifa()); //TODO: Revisar este valor porque parece que esta mal seteado y se refiere al iva del producto      
        if (productoSeleccionado.getCatalogoProducto().getIce() != null) {
            facturaDetalle.setIcePorcentaje(productoSeleccionado.getCatalogoProducto().getIce().getPorcentaje());
        }
        facturaDetalle.setTipoDocumentoEnum(TipoDocumentoEnum.LIBRE);//TODO: Por el momento solo dejo como documento por defecto libre
        facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());*/
        facturaDetalle = new FacturaDetalle();
        facturaDetalle.setCantidad(BigDecimal.ONE);
        facturaDetalle.setDescripcion(descripcion);
        facturaDetalle.setPrecioUnitario(valorUnitario);
        facturaDetalle.setDescuento(BigDecimal.ZERO);
        facturaDetalle.setIvaPorcentaje(catologoProducto.getIva().getTarifa());
        if (productoSeleccionado.getCatalogoProducto().getIce() != null) {
            facturaDetalle.setIcePorcentaje(productoSeleccionado.getCatalogoProducto().getIce().getPorcentaje());
        }
        facturaDetalle.setTipoDocumentoEnum(TipoDocumentoEnum.LIBRE);
        facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
        facturaDetalle.setCatalogoProducto(catologoProducto);
    }

    public void habilitarComboIva(Boolean opcion) {
        cmbIvaDetalleEnable=opcion;
    }

    public void setComboIva(EnumSiNo enumSiNo) {
        this.incluyeIvaDetalleEnum=enumSiNo;
    }

    public void setTxtValorUnitario(String valorUnitario) {
        facturaDetalle.setPrecioUnitario(new BigDecimal(valorUnitario));
    }

    public TipoDocumentoEnum obtenerTipoDocumentoSeleccionado() {
        return TipoDocumentoEnum.LIBRE; //TODO: por el momento dejo este documento como libre
    }

    public Producto obtenerProductoSeleccionado() {
        return productoSeleccionado;
    }

    public Presupuesto obtenerPresupuestoSeleccionado() {
        return null;
    }

    public RubroEstudiante obtenerRubroSeleccionado() {
        return null;
    }

    public String obtenerTxtDescuento() {
        return facturaDetalle.getDescuento().toString();
    }

    public String obtenerTxtCantidad() {
        return facturaDetalle.getCantidad().toString();
    }

    public String obtenerTxtDescripcion() {
        return facturaDetalle.getDescripcion();
    }

    public String obtenerTxtValorUnitario() {
        return facturaDetalle.getPrecioUnitario().toString();
    }

    public EnumSiNo obtenerComboIva() {
        return incluyeIvaDetalleEnum;
    }

    public Factura obtenerFactura() {
        return factura;
    }

    public Boolean obtenerCheckPorcentajeSeleccion() {
        return descuentoPorcentaje; 
    }

    public void limpiarComboPrecioVenta() {
        //TODO: FALTA IMPLEMNTAR
    }

    public void focoTxtCodigoDetalle() {
        //TODO: FALTA IMPLEMNTAR
    }

    public void setearCantidadTxt(String cantidad) {
        facturaDetalle.setCantidad((cantidad==null || cantidad.isEmpty())?null:new BigDecimal(cantidad));
    }

    public void setearDescripcionTxt(String descripcion) {
        facturaDetalle.setDescripcion(descripcion);
    }

    public void setearValorUnitarioTxt(String valorUnitario) {
        facturaDetalle.setPrecioUnitario((valorUnitario==null || valorUnitario.isEmpty() )?null:new BigDecimal(valorUnitario));
    }

    public void setearDescuentoTxt(String descuento) {
        facturaDetalle.setDescuento((descuento==null || descuento.isEmpty() )?null:new BigDecimal(descuento));
    }

    public void setearCodigoDetalleTxt(String codigoDetalle) {
        //facturaDetalle.set
        codigoDetalle=codigoDetalle;
    }

    public void cargarTotalesVista() {
        
    }

    public void cargarFormasPagoTabla() {
        
    }

    public void cargarDatosDetalles() {
        
    }

    public Boolean validarIngresoDetalle() {
        return true; //Falta implementar
    }

    public Integer filaSeleccionadaTablaDetalle() {
        return 0;
    }

    public void seleccionarFilaTablaDetalle(int filaSeleccionada) {
        
    }

    public void cargarDatosDetalleVista(BigDecimal valorUnitario, BigDecimal descuentos,String descripcion, String codigo) {
        
    }

    public void setFacturaDetalleSeleccionado(FacturaDetalle facturaDetalle) {
        this.facturaDetalle=facturaDetalle;
    }

    public Boolean getModoEdicionDetalle() {
        return modoEdicionDetalle;
    }

    public void setModoEdicionDetalle(Boolean modoEdicionDetalle) {
        this.modoEdicionDetalle=modoEdicionDetalle;
    }

    public void limpiarIngresoDetalleVista() {
        productoSeleccionado = new Producto();
        this.facturaDetalle=new FacturaDetalle();
        this.facturaDetalle.setCantidad(BigDecimal.ONE);
    }

    public void agregarFormaPagoConCartera() {
        
    }

    public Boolean isPagoConCartera() {
        return false; //TODO: Falta aumentar una opcion en la vista para habilitar es nueva funcion
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.iniciar();
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
        factura = (Factura) entidad;
        fechaEmision = new java.sql.Date(factura.getFechaEmision().getTime());
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void correcionesAdicionales() {
        //Correcion temporal que cuando borran un descuento se graba con null si pasa ese caso toca setear con cero
        //TODO: Toca ver como hacer estas correciones desde la propia vista
        
        for (FacturaDetalle detalle : factura.getDetalles()) {
            if(detalle.getDescuento()==null)
            {
                detalle.setDescuento(BigDecimal.ZERO);
            }
            
            if(detalle.getCantidad()==null)
            {
                detalle.setCantidad(BigDecimal.ZERO);
            }
            
            if(detalle.getPrecioUnitario()==null)
            {
                detalle.setPrecioUnitario(BigDecimal.ZERO);
            }
        }
        
    }

    public Estudiante getEStudiante() {
        return null;
    }

    /**
     * =============================================================
     *              TIPO PAGINA ENUMA
     * ==============================================================
     */
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

    public static class InterfazCallBack extends UnicastRemoteObject implements ClienteInterfaceComprobante {

        private BarraProgreso barraProgreso;
        private SessionMb sessionMb;
        
        public InterfazCallBack(BarraProgreso barraProgreso,SessionMb sessionMb) throws RemoteException {
            this.barraProgreso=barraProgreso;
            this.sessionMb=sessionMb;
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
            String mensaje=cee.obtenerErrorFormato();            
            barraProgreso.setMensajeAlerta(mensaje);
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
    
    ///////////////////////////////////////////////////////////////////////////
    //                       METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////
    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public FacturaDetalle getFacturaDetalle() {
        return facturaDetalle;
    }

    public void setFacturaDetalle(FacturaDetalle facturaDetalle) {
        this.facturaDetalle = facturaDetalle;
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

    public EnumSiNo getIncluyeIvaDetalleEnum() {
        return incluyeIvaDetalleEnum;
    }

    public void setIncluyeIvaDetalleEnum(EnumSiNo incluyeIvaDetalleEnum) {
        this.incluyeIvaDetalleEnum = incluyeIvaDetalleEnum;
    }

    

    public EnumSiNo[] getEnumSiNoList() {
        return enumSiNoList;
    }

    public void setEnumSiNoList(EnumSiNo[] enumSiNoList) {
        this.enumSiNoList = enumSiNoList;
    }

    
    public Boolean getCmbIvaDetalleEnable() { 
        return cmbIvaDetalleEnable;
    }

    public void setCmbIvaDetalleEnable(Boolean cmbIvaDetalleEnable) {
        this.cmbIvaDetalleEnable = cmbIvaDetalleEnable;
    }

    public Boolean getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(Boolean descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public List<Producto.PrecioVenta> getPrecioVentaList() {
        return precioVentaList;  
    }

    public void setPrecioVentaList(List<Producto.PrecioVenta> precioVentaList) {
        this.precioVentaList = precioVentaList;
    }

    public Producto.PrecioVenta getPrecioVentaSeleccionado() {
        return precioVentaSeleccionado;
    }

    public void setPrecioVentaSeleccionado(Producto.PrecioVenta precioVentaSeleccionado) {
        this.precioVentaSeleccionado = precioVentaSeleccionado;
    }

    public FacturaModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(FacturaModelControlador controlador) {
        this.controlador = controlador;
    }
    
    
    
         
}
