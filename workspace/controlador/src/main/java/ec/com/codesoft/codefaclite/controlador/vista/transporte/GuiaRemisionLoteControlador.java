/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.transporte;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RutaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity.ComprobanteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
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
public class GuiaRemisionLoteControlador extends ModelControladorAbstract<GuiaRemisionLoteControlador.CommonIf,GuiaRemisionLoteControlador.SwingIf,GuiaRemisionLoteControlador.WebIf> implements VistaCodefacIf {

    private java.util.Date fechaInicial;
    private java.util.Date fechaFin;
    
    
    private List<Factura> ventasList;
    private List<Factura> ventasSeleccionadasList;
    
    private List<DocumentoEnum> documentosFiltroList;
    private DocumentoEnum documentoFiltro;
    
    private Factura facturaSeleccionada;
    private String motivoTraslado;
    
    private Empleado vendedorSeleccionado;
    private Ruta rutaSeleccionada;
    
    private Boolean seleccionTodosVendedor;
    private Boolean seleccionTodosRuta;
    
    public GuiaRemisionLoteControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.documentosFiltroList=new ArrayList<DocumentoEnum>();
        this.documentosFiltroList.add(DocumentoEnum.FACTURA);
        this.documentosFiltroList.add(DocumentoEnum.NOTA_VENTA_INTERNA);
        
        //Cargar el tipo de documento segun la guia de remision tenga seleccionado el tipo de documento
        this.documentoFiltro=DocumentoEnum.FACTURA;
        
        DocumentoEnum documentoConfigurado=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.DOCUMENTO_GUIA_REMISION_DEFECTO, DocumentoEnum.GUIA_REMISION);
        if(documentoConfigurado!=null)
        {
            if(documentoConfigurado.equals(DocumentoEnum.GUIA_REMISION))
            {
                this.documentoFiltro=DocumentoEnum.FACTURA;
            }else if(documentoConfigurado.equals(DocumentoEnum.GUIA_REMISION_INTERNA))
            {
                this.documentoFiltro=DocumentoEnum.NOTA_VENTA_INTERNA;
            }
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            if(!validarDatosIngresados())
            {
                throw new ExcepcionCodefacLite("Cancelado por validación grabar");
            }
            
            GuiaRemision guiaRemisionContruida=constuirGuiaRemision();
            if(tipoVista.equals(TipoVista.ESCRITORIO))
            {
                getInterazEscritorio().abrirGuiaRemision(guiaRemisionContruida);
                getInterfaz().cerrarPantalla();            
            }
            //TODO: Falta terminar de implementar para el caso de otra interfaz
            
            
        } catch (ServicioCodefacException ex) {            
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Cancelado grabar");
        }
        
        
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
        ventasList=new ArrayList<Factura>();
        ventasSeleccionadasList=new ArrayList<Factura>();
        seleccionTodosRuta=true;
        seleccionTodosVendedor=true;
        motivoTraslado="";
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
            
            //Si el estado de los documento es nota de venta interna entonce filtro por el estado sin autorizar
            ComprobanteEnumEstado comprobanteEnum=ComprobanteEnumEstado.AUTORIZADO;
            if(documentoFiltro.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
            {
                comprobanteEnum=ComprobanteEnumEstado.SIN_AUTORIZAR;
            }
            
            ventasList=consultarFacturas(fechaInicial, fechaFin, comprobanteEnum, documentoFiltro, vendedorSeleccionado);
            
            //Consultar facturas sin autorizar cuando esta activo el modo en las configuraciones
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.MODO_FACTURACION_GUIA_REMISION,ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
            {
                ventasList.addAll(consultarFacturas(fechaInicial, fechaFin, ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR, DocumentoEnum.FACTURA, vendedorSeleccionado));
            }            

            
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Factura> consultarFacturas(Date fechaInicial,Date fechaFin,ComprobanteEntity.ComprobanteEnumEstado comprobanteEstado,DocumentoEnum documentoEnum,Empleado vendedorSeleccionado)
    {
        try {
            return ServiceFactory.getFactory().getFacturacionServiceIf().obtenerFacturasReporte(
                    null,
                    new java.sql.Date(fechaInicial.getTime()),
                    new java.sql.Date(fechaFin.getTime()),
                    comprobanteEstado,
                    Boolean.FALSE,
                    null,
                    Boolean.FALSE,
                    null,
                    session.getEmpresa(),
                    documentoEnum,
                    null,
                    null,
                    vendedorSeleccionado,
                    EnumSiNo.NO,
                    true);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Factura>();
    }
    
    private boolean validarDatosIngresados()
    {
        if(motivoTraslado==null || motivoTraslado.isEmpty())
        {
            mostrarMensaje(new CodefacMsj("Ingrese un motivo para procesar la guía de remisión",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        if(ventasSeleccionadasList==null || ventasSeleccionadasList.size()==0)
        {
            mostrarMensaje(new CodefacMsj("Seleccione al menos una venta para continuar",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        //Verificar si todos las ventas seleccionadas tiene una direccion importante para procesar la guia de remision
        
        for (Factura venta : ventasSeleccionadasList) {
            String direccion=venta.getSucursal().getDireccion();
            if(direccion==null || direccion.trim().isEmpty())
            {
                mostrarMensaje(new CodefacMsj("El cliente "+venta.getSucursal().getPersona().getNombresCompletos()+" , con Identificación "+venta.getSucursal().getPersona().getIdentificacion()+" es necesario definir una dirección ",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                return false;
            }
        }
        
        return true;
    }
    
    private GuiaRemision constuirGuiaRemision() throws ServicioCodefacException
    {
        GuiaRemision guiaRemision=new GuiaRemision();
        //Todos los demas datos genero en la siguiente pantala
        
        for (Factura ventas : ventasSeleccionadasList) 
        {
            
            try {
                //Buscar el codigo de establecimiento por defecto selecciono 1
                String codigoSucursalStr=ventas.getSucursal().getCodigoSucursal();
                Integer codigoSucursal=(codigoSucursalStr==null || codigoSucursalStr.trim().isEmpty())?1:new Integer(codigoSucursalStr);
                //Crear destinatario desde un metodo estandar
                DestinatarioGuiaRemision destinatario=DestinatarioGuiaRemision.crearDestinatario(
                        guiaRemision,
                        ventas,
                        ventas.getClaveAcceso(),
                        ventas.getSucursal().getPersona(),
                        ventas.getSucursal().getDireccion(),
                        ventas.getFechaEmision(),
                        motivoTraslado,
                        "", //Todo: Completar este dato de la ruta
                        ventas.getPreimpreso(),
                        codigoSucursal);
                
                guiaRemision.addDestinario(destinatario);
                
            } catch (ServicioCodefacException ex) {
                //DialogoCodefac.mensaje(new CodefacMsj("Error factura:"+ventas.getPreimpreso()+" \nMotivo: "+ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServicioCodefacException("Error factura:"+ventas.getPreimpreso()+" \nMotivo: "+ex.getMessage());
            } catch (RemoteException ex) {
                Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return guiaRemision;
        
        
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

    public List<Factura> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Factura> ventasList) {
        this.ventasList = ventasList;
    }

    public List<Factura> getVentasSeleccionadasList() {
        return ventasSeleccionadasList;
    }

    public void setVentasSeleccionadasList(List<Factura> ventasSeleccionadasList) {
        this.ventasSeleccionadasList = ventasSeleccionadasList;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
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

    public List<DocumentoEnum> getDocumentosFiltroList() {
        return documentosFiltroList;
    }

    public void setDocumentosFiltroList(List<DocumentoEnum> documentosFiltroList) {
        this.documentosFiltroList = documentosFiltroList;
    }

    public DocumentoEnum getDocumentoFiltro() {
        return documentoFiltro;
    }

    public void setDocumentoFiltro(DocumentoEnum documentoFiltro) {
        this.documentoFiltro = documentoFiltro;
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
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
}
