/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.ProformaModel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.main.panel.WidgetVentasDiarias;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class VentasDiariasModel extends WidgetVentasDiarias
{
    private Factura factura;
    private DefaultTableModel modeloTablaDetallesProductos;
    private Producto productoSeleccionado;
    private int fila;
    private boolean banderaProducto;
    //private Empresa empresa;
    private SessionCodefac session;
    
    public VentasDiariasModel(JDesktopPane parentPanel,SessionCodefac session) {
        super(parentPanel);
        this.session=session;
        iniciarValores();
        agregarListenerBotones();
        agregarListenerTextField();
        agregarListenerMovimiento();
    }

    @Override
    public JPanel getPanelMovimiento() {
         JPanel panel=getPanelTitulo();
        return panel;
    }
    
    private void agregarListenerTextField()
    {
        getTxtCodigoProducto().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    
                    Producto producto=FacturaModelControlador.listenerBuscarProducto(getTxtCodigoProducto().getText(), (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem(), session.getEmpresa());

                    if (producto == null) {
                        DialogoCodefac.mensaje(new CodefacMsj("No existe el producto buscado", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    } else {
                        agregarProductoVista(producto);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        getTxtCantidadProducto().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {                
                    listenerBotonAgregarProducto();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    /**
     * TODO: Ver si se une esta funcion con la logica de la factura
     * @param manejaInventario 
     */
    private void buscarProductoDialogo(TipoDocumentoEnum tipoDocumentoEnum )
    {
        EnumSiNo manejaInventario=EnumSiNo.NO;
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.INVENTARIO))
        {
            manejaInventario=EnumSiNo.SI;
        }
        
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(manejaInventario,session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        agregarProductoVista((Producto) buscarDialogoModel.getResultado());
    }
    
    private void agregarProductoVista(Producto productoAgregar)
    {
        productoSeleccionado = productoAgregar;
        //productoSeleccionado.setManejarInventarioEnum(manejaInventario);
        banderaProducto = true;

        if (productoSeleccionado == null) {
            return;
        }

        setearValoresProducto();
    }
    
    private void agregarListenerBotones() {
        
        getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                
                switch(tipoDocumentoEnum)
                {
                    case ACADEMICO:
                        //TODO: Falta implementar
                        break;
                    case PRESUPUESTOS:
                        //TODO: Falta implementar
                        break;
                    case INVENTARIO: 
                        buscarProductoDialogo(tipoDocumentoEnum);
                        break;
                    case LIBRE:
                        buscarProductoDialogo(tipoDocumentoEnum);
                        break;
                
                } 
                
               
            }
        });
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBotonAgregarProducto();
            }
        });
        
        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               fila = getTblDetalleFactura().getSelectedRow();
            }
        });
        
        getBtnEliminarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(!factura.getDetalles().isEmpty() ){
                        if( fila != -1){
                            factura.getDetalles().remove(fila);
                            fila = -1;
                            procesarTotales();
                            cargarDatosDetalles();
                            actualizarVentaDiariaComoPedido();
                        }
                        else{
                            DialogoCodefac.mensaje("Detalles Producto", "Seleccione el objeto a eliminar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Detalles Producto", "No existe registro detalles productos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        System.out.println("Factura Detalles ->> " + factura.getDetalles());
                    }
                }
                catch(Exception exc)
                {
                    DialogoCodefac.mensaje("Detalles Producto", "No existe registro detalles productos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        getBtnFacturar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean permitirFacturar = false;
                Boolean respuesta = null;
                if (factura.getDetalles() == null) 
                {
                    DialogoCodefac.mensaje("Alerta", "No se puede facturar sin detalles", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    try {
                        throw new ExcepcionCodefacLite("Necesita seleccionar detalles ");
                    } catch (ExcepcionCodefacLite ex) {
                        Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    permitirFacturar = true;
                    respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea continuar con la facturación?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if (!respuesta) {
                        try {
                            throw new ExcepcionCodefacLite("Cancelacion usuario");
                        } catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(permitirFacturar && respuesta)
                {
                        cargarCliente();
                        definirFechaFacturacion();
                        FacturacionModel facturacionModel = new FacturacionModel();                    
                        panelPadre.crearVentanaCodefac(facturacionModel, true);
                        try {
                            facturacionModel.iniciar();
                            facturacionModel.cargarFacturaDesdeProforma(factura);
                            //facturacionModel.setFactura(factura);
                            //facturacionModel.cargarSecuencial();
                            
                        } catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setarValoresVariables();
                        facturacionModel.revalidate();
                        facturacionModel.repaint();
                }
                    
            }
        });
    }            
    
    private void listenerBotonAgregarProducto()
    {
        if (banderaProducto) {
            if (validarCamposIngresar()) {
                agregarDetallesFactura(null);
                limpiarValoresCamposTextos();
                cargarDatosDetalles();
                procesarTotales();
                banderaProducto = false;
                getjTabbedPanel().setSelectedIndex(1);
            } else {
                DialogoCodefac.mensaje("Validacion", "Verificar los campos ingresados", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
        } else {
            DialogoCodefac.mensaje("Producto", "Se debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
    public void iniciarValores()
    {
        fila = -1;
        banderaProducto = false;
        //Agregar los 2 tipos de documentos disponibles para ventas diarias
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= new ArrayList<>();
        tipoDocumentos.add(TipoDocumentoEnum.LIBRE);
        tipoDocumentos.add(TipoDocumentoEnum.INVENTARIO);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
        crearOCargarPedidoVentaDiaria(true);
    }
    
    private void crearOCargarPedidoVentaDiaria(Boolean iniciar)
    {
        try {
            //Buscar si previamente existe un pedido de venta diaria pendiente para cargar
            Factura facturaTemp=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerPedidoVentaDiariaActivo(session.getSucursal());
            if(facturaTemp!=null)
            {
                factura=facturaTemp;
                //TODO: Agrupar estos tres metodos que sirven para actualizar la vista
                if(iniciar)
                {
                    limpiarValoresCamposTextos();
                    cargarDatosDetalles();
                    procesarTotales();
                }
                
            }
            else
            {
                factura = new Factura();
                factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean agregarDetallesFactura (FacturaDetalle facturaDetalle)
    {
        //Primero verifica o crea si existe o no una venta diaria pendiente
        crearOCargarPedidoVentaDiaria(false);
        
        try {
            boolean agregar = true;
            if (facturaDetalle != null) {
                agregar = false;
            } else {
                facturaDetalle = new FacturaDetalle();
            }
            CatalogoProducto catalogoProducto=null;
            //Seleccionar la referencia dependiendo del tipo de documento
            TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
            facturaDetalle.setTipoDocumento(tipoDocumentoEnum.getCodigo());
            
            switch (tipoDocumentoEnum) 
            {
                case INVENTARIO:
                case LIBRE:
                    facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
                    catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                    facturaDetalle.setCatalogoProducto(catalogoProducto);
                    break;
            }
            if(catalogoProducto==null)
            {
                DialogoCodefac.mensaje("Advertencia","No esta definido el Catalogo Producto ,donde se especifica los impuestos para facturar ",DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
            //Cargo la informacion del producto 
            facturaDetalle.setCantidad(new BigDecimal(getTxtCantidadProducto().getText()));
            facturaDetalle.setDescripcion(getTxtDescripcionProducto().getText());
            BigDecimal valorTotalUnitario = new BigDecimal(getTxtValorUnitarioProducto().getText());
            facturaDetalle.setPrecioUnitario(valorTotalUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
            //facturaDetalle.setProducto(productoSeleccionado);
            facturaDetalle.setValorIce(BigDecimal.ZERO);
            facturaDetalle.setDescuento(BigDecimal.ZERO);

            //Calular el total despues del descuento porque necesito ese valor para grabar
            BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).subtract(facturaDetalle.getDescuento());
            facturaDetalle.setTotal(setTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
            facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
            if (catalogoProducto.getIva().getTarifa().equals(0)) {
                    facturaDetalle.setIva(BigDecimal.ZERO);
                } else {
                    BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    facturaDetalle.setIva(iva);
                }
            //Solo agregar si se enviar un dato vacio
            if (agregar) {
                factura.addDetalle(facturaDetalle);
                limpiarValoresCamposTextos();
                cargarDatosDetalles();
                procesarTotales();
            } 
            
            //Agregar o actualizar el nuevo pedido;
            actualizarVentaDiariaComoPedido();
        } catch (RemoteException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return true;
    }
    
    private void actualizarVentaDiariaComoPedido()
    {        
        try {
            
            //Cuando es una factura le grabo 
            if(factura.getId()==null)
            {
                PersonaServiceIf cliente = ServiceFactory.getFactory().getPersonaServiceIf();
                Persona persona = cliente.buscarConsumidorFinal(session.getEmpresa());
                if (persona == null) {
                    DialogoCodefac.mensaje("Advertencia", "No existe creado un consumidor final para facturar", DialogoCodefac.MENSAJE_INCORRECTO);
                }
                
                //Obtener un punto de emision del usuario iniciado la session
                List<PuntoEmision> puntoEmisionList= ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivosPorSucursalCastPuntoEmision(session.getUsuario(),session.getSucursal());
                if(puntoEmisionList.size()>0)
                {
                    this.factura.setPuntoEmision(puntoEmisionList.get(0).getPuntoEmision());
                    this.factura.setPuntoEmisionId(puntoEmisionList.get(0).getId());
                }

                this.factura.setCliente(persona);
                this.factura.setSucursalEmpresa(this.session.getSucursal());
                this.factura.setSucursal(persona.getEstablecimientos().get(0));//Se refiere a la sucursal del cliente
                //Le grabo como una proforma
                factura.setCodigoDocumentoEnum(DocumentoEnum.PROFORMA);
                factura.setEmpresa(this.session.getEmpresa());
                factura.setUsuario(session.getUsuario());
                factura.setCodigoOrigenTransaccionEnum(Factura.OrigenTransaccionEnum.WIDGETS_VENTA_DIARIA);
                factura=ServiceFactory.getFactory().getFacturacionServiceIf().grabarProforma(factura);
            }
            else
            {
                factura=ServiceFactory.getFactory().getFacturacionServiceIf().editarProforma(factura);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    

    public void limpiarValoresCamposTextos()
    {
        getTxtCantidadProducto().setText("");
        getTxtDescripcionProducto().setText("");
        getTxtValorUnitarioProducto().setText("");
        getTxtCodigoProducto().setText("");
    }
    
    public void setearValoresProducto()
    {
        getTxtCodigoProducto().setText(productoSeleccionado.getCodigoPersonalizado());
        getTxtDescripcionProducto().setText(productoSeleccionado.getNombre());
        getTxtValorUnitarioProducto().setText(""+productoSeleccionado.getValorUnitario());
        getTxtCantidadProducto().requestFocus();
    }
    
    public void setarValoresVariables()
    {
        this.factura = new Factura();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setIva(BigDecimal.ZERO);
        this.factura.setTotal(BigDecimal.ZERO);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //Limpiar detalles
        initModelTablaDetalleFactura();
        //Limpiar valores totales
        getLblSubtotal12().setText("");
        getLblSubtotal0().setText("");
        getLblIva12().setText("");
        getLblValorTotal().setText("");
    }
    
    
    
    public void cargarCliente()
    {
        try {
            PersonaServiceIf cliente = ServiceFactory.getFactory().getPersonaServiceIf();
            //Map<String,Object> clienteMap = new HashMap<String, Object>();
            //clienteMap.put("razonSocial", "Consumidor Final");
            //this.factura.setCliente(cliente.buscarPorRazonSocial("Consumidor Final",empresa));
            Persona persona=cliente.buscarConsumidorFinal(session.getEmpresa());
            if(persona==null)
            {
                DialogoCodefac.mensaje("Advertencia","No existe creado un consumidor final para facturar",DialogoCodefac.MENSAJE_INCORRECTO);
            }
            
            this.factura.setCliente(persona);
            this.factura.setSucursal(persona.getEstablecimientos().get(0));//Se refiere a la sucursal del cliente
            
        } catch (RemoteException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarFecha()
    {
        factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        factura.setFechaEmision((Date) UtilidadesFecha.hoy());
    }
    
    private void cargarDatosDetalles() {
        
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("#");
        titulo.add("Valor");
        titulo.add("Total");

        List<FacturaDetalle> detalles = factura.getDetalles();

        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        for (FacturaDetalle detalle : detalles) {
            Vector<String> fila = new Vector<String>();
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getCantidad().toString());
            fila.add(detalle.getPrecioUnitario().toString());
            fila.add(detalle.getTotal().toString());
            modeloTablaDetallesProductos.addRow(fila);
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
    }
    
    public void calcularSubtotal12y0(List<FacturaDetalle> facturaDetalles) {
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);        
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        
        
        for (FacturaDetalle facturaDetalle : facturaDetalles) {
                if (!facturaDetalle.getIva().equals(BigDecimal.ZERO)) {
                    this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));      
                }
                this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));
           
        }
        
        this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
        this.factura.setSubtotalSinImpuestos(factura.getSubtotalSinImpuestos().setScale(2,BigDecimal.ROUND_HALF_UP));
    }
    
     public void calcularSubtotales() {
     
        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().subtract(this.factura.getSubtotalImpuestos()));
        this.factura.setDescuentoSinImpuestos(new BigDecimal(0));
    }

    public void calcularIva12() {
        this.factura.setIva(this.factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).multiply(obtenerValorIva()));
        this.factura.setIva(this.factura.getIva().setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void calcularValorTotal() {
        this.factura.setTotal(
                factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).
                        add(factura.getSubtotalSinImpuestos().subtract(factura.getDescuentoSinImpuestos())).
                        add(factura.getIva()));
    }
    
    public void procesarTotales()
    {
        calcularSubtotal12y0(factura.getDetalles());
        calcularSubtotales();
        calcularIva12();
        calcularValorTotal();
        mostrarTotales();
    }
    
    public void mostrarTotales()
    {
        this.getLblSubtotal12().setText(""+this.factura.getSubtotalImpuestos());
        this.getLblSubtotal0().setText(""+this.factura.getSubtotalSinImpuestos());
        this.getLblIva12().setText(""+this.factura.getIva());
        this.getLblValorTotal().setText(""+this.factura.getTotal());
    }
    
    public BigDecimal obtenerValorIva() {
        try {
            Map<String, Object> map = new HashMap<>();
            ImpuestoDetalleServiceIf impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            map.put("tarifa", 12); //TODO Parametrizar el iva con la variable del sistema
            List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
            listaImpuestoDetalles.forEach((iD) -> {
                BigDecimal iva = iD.getPorcentaje();
            });
            return new BigDecimal(0.120);
        } catch (RemoteException ex) {
            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void definirFechaFacturacion()
    {   
        factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
    }
    
    private void initModelTablaDetalleFactura() 
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("#");
        titulo.add("Valor");
        titulo.add("Total");
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        getTblDetalleFactura().setModel(modeloTablaDetallesProductos);
    }
    
    private boolean validarCamposIngresar()
    {
        String cantidadProducto = getTxtCantidadProducto().getText();
        String valorTotal = getTxtValorUnitarioProducto().getText();
        
        if(verificarNumeroEntero(cantidadProducto) && verificarNumeroDoble(valorTotal))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean verificarNumeroEntero(String Numero)
    {
        try{
            int numero = Integer.parseInt(Numero);
        }
        catch(NumberFormatException e){
            return false;
        }

        return true;
    }
    
    private boolean verificarNumeroDoble(String Numero)
    {
        try{
            float numero = Float.parseFloat(Numero);
        }
        catch(NumberFormatException e){
            return false;
        }

        return true;
    }

            
}
