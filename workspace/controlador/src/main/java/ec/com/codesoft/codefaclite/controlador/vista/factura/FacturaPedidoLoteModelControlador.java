/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RutaBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TableBindingImp;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FacturaPedidoLoteModelControlador extends ModelControladorAbstract<FacturaPedidoLoteModelControlador.CommonIf,FacturaPedidoLoteModelControlador.SwingIf,FacturaPedidoLoteModelControlador.WebIf> implements VistaCodefacIf {

    private java.util.Date fechaInicial;
    private java.util.Date fechaFin;
    
    
    private List<FacturaDataTable> ventasList;
    private List<FacturaDataTable> ventasSeleccionadasList;
    private List<PuntoEmision> puntoEmisionList;
    
    private FacturaDataTable facturaSeleccionada;
    
    private Empleado vendedorSeleccionado;
    private Ruta rutaSeleccionada;
    private PuntoEmision puntoEmisionSeleccionado;
    
    private Boolean seleccionTodosVendedor;
    private Boolean seleccionTodosRuta;
    
    private TableBindingImp tableBindingControlador;
    
    public FacturaPedidoLoteModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);                
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        try {
            puntoEmisionList= ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivosPorSucursalCastPuntoEmision(session.getUsuario(),session.getSucursal());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaPedidoLoteModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try 
        {                        
            validar();
            
            if(!dialogoPregunta(new CodefacMsj("Está seguro que quiere procesar los pedidos en facturas ?", CodefacMsj.TipoMensajeEnum.CORRECTO)))
            {               
                throw new ExcepcionCodefacLite("Cancelado grabar");
            }
            
            //List<Factura> facturasProcesar=construirFacturas();
            
            FacturaLoteRespuesta respuesta=ServiceFactory.getFactory().getFacturacionServiceIf().grabarLote(construirFacturas());
            
            mostrarMensaje(new CodefacMsj("El proceso finalizo correctamente", CodefacMsj.TipoMensajeEnum.CORRECTO));
            
            //Si existen DATOS NO PROCEDOS muestro un mensaje
            if(respuesta.noProcesadosList.size()>0)
            {
                mostrarMensaje(new CodefacMsj(respuesta.costruirMensajeError(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
            if(respuesta.procesadosList.size()>0)
            {
                List<ComprobanteDataInterface> comprobantes=obtenerComprobantesParaProcesarElectronicamente(respuesta);
                //Verifica si existen comprobantes electrónicos para procesar primero
                if(comprobantes.size()>0)
                {
                    //List<List> datosProcesar=UtilidadesLista.dividirLista(ParametrosSistemaCodefac.MAX_COMPROBANTES_ELECTRONICOS_LOTE, comprobantes);
                    //for (List<ComprobanteDataInterface> comprobanteList : datosProcesar) {
                        ///Procesar las facturas de forma electronica
                        ClienteInterfaceComprobanteLote cic = getInterazEscritorio().getInterfaceCallBack();
                        
                        ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobantesLotePendiente(
                                ComprobanteElectronicoService.ETAPA_GENERAR,
                                ComprobanteElectronicoService.ETAPA_AUTORIZAR,
                                null,
                                comprobantes,
                                session.getEmpresa().getIdentificacion(),
                                cic,
                                true,
                                session.getEmpresa(),
                                false
                        );
                    //}
                }
                
            }
            
            //IMPRIMIR las NOTAS DE VENTA INTERNA que no requieren procesar ELECTRONICAMENTE
            List<Factura> notaVentaList=obtenerNotasVentaProcesadas(respuesta);
            if(notaVentaList.size()>0)
            {
                getInterazEscritorio().generarNotasVentaInterna(notaVentaList);
            }
            
            //volver a consultar los datos con la ultima consulta
            listenerConsultarGuiasRemision();
            
        } catch (ServicioCodefacException ex) {            
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Cancelado grabar");
        }
        
        
    }
    
    private List<Factura> obtenerNotasVentaProcesadas(FacturaLoteRespuesta respuesta)
    {
        List<Factura> notaVentaList=new ArrayList<Factura>();
        for (Factura factura : respuesta.procesadosList) {
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
            {
                notaVentaList.add(factura);
            }
        }
        return notaVentaList;
    }
    
    private List<ComprobanteDataInterface> obtenerComprobantesParaProcesarElectronicamente(FacturaLoteRespuesta respuesta)
    {
        List<ComprobanteDataInterface> comprobantesProcesarList=new ArrayList<ComprobanteDataInterface>();
        
        for (Factura factura : respuesta.procesadosList) {
            //Solo procesar los documentos que son de tipo facturas
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
            {
                ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
                comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
                comprobantesProcesarList.add(comprobanteData);
            }
        }
                
        return comprobantesProcesarList;
    }
    

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        fechaInicial=new Date();
        fechaFin=new Date();
        ventasList=new ArrayList<FacturaDataTable>();
        ventasSeleccionadasList=new ArrayList<FacturaDataTable>();
        seleccionTodosRuta=true;
        seleccionTodosVendedor=true;
                
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void listenerConsultarGuiasRemision()
    {        
        try {
            List<Factura> ventasList=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerFacturasReporte(
                    null,
                    new java.sql.Date(fechaInicial.getTime()),
                    new java.sql.Date(fechaFin.getTime()),
                    ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO,
                    Boolean.FALSE,
                    null,
                    Boolean.FALSE,
                    null,
                    session.getEmpresa(),
                    DocumentoEnum.PROFORMA,
                    null,
                    null,
                    vendedorSeleccionado,
                    null,
                    true
                    );
            
            castListDataTable(ventasList);
            
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void castListDataTable(List<Factura> ventasListTmp)
    {
        ventasList=new ArrayList<FacturaDataTable>();
        for (Factura factura : ventasListTmp) {
            ventasList.add(new FacturaDataTable(factura,true, 0,DocumentoEnum.FACTURA));
        }
    }
    
    private boolean validarDatosIngresados()
    {        
        
        if(ventasSeleccionadasList==null || ventasSeleccionadasList.size()==0)
        {
            mostrarMensaje(new CodefacMsj("Seleccione al menos una venta para continuar",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        //Verificar si todos las ventas seleccionadas tiene una direccion importante para procesar la guia de remision
        
        for (FacturaDataTable ventaTmp : ventasSeleccionadasList) {
            Factura venta=ventaTmp.factura;
            String direccion=venta.getSucursal().getDireccion();
            if(direccion==null || direccion.trim().isEmpty())
            {
                mostrarMensaje(new CodefacMsj("El cliente "+venta.getSucursal().getPersona().getNombresCompletos()+" , con Identificación "+venta.getSucursal().getPersona().getIdentificacion()+" es necesario definir una dirección ",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                return false;
            }
        }
        
        return true;
    }
    
        
    public void listenerBuscarVendedor()
    {
        EmpleadoBusquedaDialogo busqueda=new EmpleadoBusquedaDialogo(Departamento.TipoEnum.Ventas);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            vendedorSeleccionado=((Empleado) buscarDialogoModel.getResultado());
            seleccionTodosVendedor=false;
        }
    }
    
    public void listenerBuscarRuta()
    {
        RutaBusquedaDialogo busqueda=new RutaBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            rutaSeleccionada=(Ruta) buscarDialogoModel.getResultado();
            vendedorSeleccionado=rutaSeleccionada.getVendedor();
            seleccionTodosRuta=false;
            seleccionTodosVendedor=false;
        }
    }
    
    public void listenerCheckVendedores()
    {
        if(seleccionTodosVendedor)
        {
            vendedorSeleccionado=null;
        }
    }
    
    public void listenerCheckRutas()
    {
        if(seleccionTodosRuta)
        {
            rutaSeleccionada=null;
        }
    }
    
    public void editarDocumentoPopUp(DocumentoEnum documentoEnum)
    {
        if(facturaSeleccionada!=null)
        {
            facturaSeleccionada.documentoEnum=documentoEnum;
            tableBindingControlador.actualizarBindingVista();
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //                      METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<FacturaDataTable> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<FacturaDataTable> ventasList) {
        this.ventasList = ventasList;
    }

    public List<FacturaDataTable> getVentasSeleccionadasList() {
        return ventasSeleccionadasList;
    }

    public void setVentasSeleccionadasList(List<FacturaDataTable> ventasSeleccionadasList) {
        this.ventasSeleccionadasList = ventasSeleccionadasList;
    }

    public FacturaDataTable getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(FacturaDataTable facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }


    public Empleado getVendedorSeleccionado() {
        return vendedorSeleccionado;
    }

    public void setVendedorSeleccionado(Empleado vendedorSeleccionado) {
        this.vendedorSeleccionado = vendedorSeleccionado;
    }

    public Ruta getRutaSeleccionada() {
        return rutaSeleccionada;
    }

    public void setRutaSeleccionada(Ruta rutaSeleccionada) {
        this.rutaSeleccionada = rutaSeleccionada;
    }

    public Boolean getSeleccionTodosVendedor() {
        return seleccionTodosVendedor;
    }

    public void setSeleccionTodosVendedor(Boolean seleccionTodosVendedor) {
        this.seleccionTodosVendedor = seleccionTodosVendedor;
    }

    public Boolean getSeleccionTodosRuta() {
        return seleccionTodosRuta;
    }

    public void setSeleccionTodosRuta(Boolean seleccionTodosRuta) {
        this.seleccionTodosRuta = seleccionTodosRuta;
    }

    public List<PuntoEmision> getPuntoEmisionList() {
        return puntoEmisionList;
    }

    public void setPuntoEmisionList(List<PuntoEmision> puntoEmisionList) {
        this.puntoEmisionList = puntoEmisionList;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }

    public TableBindingImp getTableBindingControlador() {
        return tableBindingControlador;
    }

    public void setTableBindingControlador(TableBindingImp tableBindingControlador) {
        this.tableBindingControlador = tableBindingControlador;
    }
    
    
    

    private List<FacturaParametro> construirFacturas() {
        List<FacturaParametro> facturasProcesar=new ArrayList<FacturaParametro>();
        for (FacturaDataTable proformaTmp : ventasSeleccionadasList) {
            try {                
                Factura proforma=proformaTmp.factura;
                Factura facturaNueva = (Factura) proforma.clone();
                facturaNueva.setId(null);
                facturaNueva.setCodigoDocumentoEnum(proformaTmp.documentoEnum);
                facturaNueva.setProforma(proforma);
                facturaNueva.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
                facturaNueva.setPuntoEmisionId(puntoEmisionSeleccionado.getId());
                facturaNueva.setPuntoEstablecimiento(new BigDecimal(session.getSucursal().getCodigoSucursal().toString()));
                
                FacturaParametro facturaParametro=new FacturaParametro(facturaNueva, new CarteraParametro(proformaTmp.credito, proformaTmp.dias),null);
                facturasProcesar.add(facturaParametro);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(FacturaPedidoLoteModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return facturasProcesar;
    }

    private void validar() throws ServicioCodefacException 
    {
        if(ventasSeleccionadasList==null || ventasSeleccionadasList.size()==0)
        {
            throw new ServicioCodefacException("No se puede procesar sin seleccionar alguna factura");
        }
        
        if(puntoEmisionSeleccionado==null)
        {
            throw new ServicioCodefacException("Seleccione un punto de emisión para continuar");
        }
        
        if(ventasSeleccionadasList.size()>ParametrosSistemaCodefac.MAX_COMPROBANTES_ELECTRONICOS_LOTE)
        {
            throw new ServicioCodefacException("Por el momento no se puede procesar en lote más de "+ParametrosSistemaCodefac.MAX_COMPROBANTES_ELECTRONICOS_LOTE+" Comprobantes");
        }
    }

    
    
        
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {
        //TODO: Implementacion de todas las interfaces comunes
        public void cerrarPantalla();
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
        public void abrirGuiaRemision(GuiaRemision guiaRemision);
        public ClienteInterfaceComprobanteLote getInterfaceCallBack();
        public void generarNotasVentaInterna(List<Factura> facturas);
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///                 CLASES ADICIONALES
    ///////////////////////////////////////////////////////////////////////////
    public class FacturaDataTable
    {

        public FacturaDataTable(Factura factura, Boolean credito, Integer dias,DocumentoEnum documentoEnum) {
            this.factura = factura;
            this.credito = credito;
            this.dias = dias;
            this.documentoEnum=documentoEnum;
        }
        
        public Factura factura;
        public Boolean credito;
        public Integer dias; 
        public DocumentoEnum documentoEnum;
    }
    
    
}
