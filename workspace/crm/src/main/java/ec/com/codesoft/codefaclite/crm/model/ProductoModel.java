/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador.IvaOpcionEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.crm.panel.ProductoForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ProductoModel extends ProductoForm implements DialogInterfacePanel<Producto> , InterfazPostConstructPanel,ProductoModelControlador.SwingIf ,ControladorVistaIf {
    
    private static final Integer COLUMNA_ENSAMBLE_OBJECTO=0;

    //private EnumSiNo imprimirBarrasSeleccionado;
        
    /////////////////////////////////////////////    
    private Impuesto impuesto;
    private CategoriaProducto catProducto;

    private ProductoServiceIf productoService;
    private ImpuestoServiceIf impuestoService;
    private ImpuestoDetalleServiceIf impuestoDetalleService;
    private CategoriaProductoServiceIf catProdService;
    //private BigDecimal d;

    /*
    Referencia sobre el producto seleccionado para el ensamble
     */
    private Producto productoEnsamble;
    private ProductoEnsamble productoEnsambleEditar;
    
    private ProductoModelControlador controlador;

    public ProductoModel() {
                
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setearValoresProducto(Producto producto) {
        producto.setCodigoEAN(getTxtCodigoEAN().getText());
        producto.setCodigoUPC(getTxtCodigoUPC().getText());
        //producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);

          

        /**
         * Setear valores adicionales
         */
        producto.setUbicacion(getTxtUbicacion().getText());
        //producto.setGarantia(((EnumSiNo) getCmbGarantia().getSelectedItem()).getLetra());
        producto.setCantidadMinima(Integer.parseInt(getTxtCantidadMinima().getText()));
        producto.setPrecioDistribuidor(new BigDecimal(getTxtPrecioDistribuidor().getText()));
        producto.setPrecioTarjeta(new BigDecimal(getTxtPrecioTarjeta().getText()));
        producto.setPrecioSinSubsidio(new BigDecimal(getTxtPrecio1SinSubsidio().getText()));
        
        producto.setPvp4(new BigDecimal(getTxtPV4().getText()));
        producto.setPvp5(new BigDecimal(getTxtPV5().getText()));
        producto.setPvp6(new BigDecimal(getTxtPV6().getText()));
        
        //producto.setStockInicial(Long.parseLong(getTxtStockInicial().getText()));
        //producto.setMarca(getTxtMarca().getText());
        producto.setImagen(getTxtImagenProducto().getText());

        //catProducto= (CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        //producto.setCategoriaProducto(catProducto);
        producto.setCaracteristicas(getTxtCaracteristica().getText());
        producto.setObservaciones(getTxtObservaciones().getText());
        producto.setAplicacionProducto(getTxtAplicacionProducto().getText());
        
        EnumSiNo enumSiNo=EnumSiNo.getEnumByBoolean(getChkTransportarGuiaRemision().isSelected());        
        producto.setTransportarEnGuiaRemisionEnum(enumSiNo);
        
        enumSiNo=enumSiNo.getEnumByBoolean(getChkOcultarDetalleVenta().isSelected());        
        producto.setOcultarDetalleVentaEnum(enumSiNo);
        
        producto.setEmpresa(session.getEmpresa());
        

    }
    
    

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        //
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    @Override
    public void limpiar() {

        initModelTablaDatosEnsamble();
        setearValoresIniciales();


        getChkTransportarGuiaRemision().setEnabled(true);
        getChkTransportarGuiaRemision().setSelected(true);
        
        getChkOcultarDetalleVenta().setEnabled(true);
        getChkGenerarCodigoAutomatico().setSelected(false);
        
        


    }

//    @Override
    public String getNombre() {
        return "Producto";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.4y1m2n6myaod";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public Producto getResult() throws ExcepcionCodefacLite {
        try {
            controlador.grabar();
            return controlador.producto;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void iniciar() {
        controlador=new ProductoModelControlador(DialogoCodefac.intefaceMensaje, session,this,ModelControladorAbstract.TipoVista.ESCRITORIO);
        
        //controlador.producto=new Producto();
        //controlador.producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        //Iniciar cmbLleva inventario
        
        listenerComboBox();
        listenerBotones();
        listenerTablas();
        
        //mapDatosIngresadosDefault.put(getTextValorUnitario(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        
        mapDatosIngresadosDefault.put(getTxtCantidadMinima(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioDistribuidor(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioTarjeta(),"0");
        mapDatosIngresadosDefault.put(getTxtPV4(),"0");
        mapDatosIngresadosDefault.put(getTxtPV5(),"0");
        mapDatosIngresadosDefault.put(getTxtPV6(),"0");
        //mapDatosIngresadosDefault.put(getTxtStockInicial(),"0");
        
        productoService = ServiceFactory.getFactory().getProductoServiceIf();
        impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
        impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
        iniciarCombosBox();
        
        /*getBtnTemporal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoCodefac.mensaje(new CodefacMsj("Enum:"+controlador.getProducto().getManejarInventarioEnum(), CodefacMsj.TipoMensajeEnum.CORRECTO));
            }
        });*/
    }

    @Override
    public void nuevo() {
        
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarCombosBox() {
            
        controlador.iniciarCombosBox();
    }

    private void listenerComboBox() {
       
        //TODO: Mejorar esta parte para que funcione con el controlador
        getCmbGenerarCodigoBarras().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnumSiNo enumSiNo=(EnumSiNo) getCmbGenerarCodigoBarras().getSelectedItem();
                if(enumSiNo!=null && enumSiNo.equals(EnumSiNo.SI))
                {
                    getChkGenerarCodigoAutomatico().setSelected(true);
                    getTxtCodigoPersonalizado().setText("");
                    //getTxtCodigoPersonalizado().setEnabled(false);
                    controlador.setGenerarCodigoAutomatico(true);
                }
            }
        });
        
        getCmbGenerarCodigoBarras().getSelectedItem();
         

    }

    private void seleccionarTipoProducto(TipoProductoEnum tipoProducto) {
        if (tipoProducto!=null && tipoProducto.equals(TipoProductoEnum.PRODUCTO)) {
            getTxtCodigoEAN().setEnabled(true);
            getTxtCodigoUPC().setEnabled(true);
        } else {
            getTxtCodigoEAN().setEnabled(false);
            getTxtCodigoUPC().setEnabled(false);

        }
    }
    
    private void listenerTablas()
    {
        getTblDatosEnsamble().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblDatosEnsamble().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    productoEnsambleEditar=(ProductoEnsamble) getTblDatosEnsamble().getValueAt(filaSeleccionada,COLUMNA_ENSAMBLE_OBJECTO);
                    productoEnsamble=productoEnsambleEditar.getComponenteEnsamble();                   
                    cargarComponenteProductoEnsambleVista(productoEnsambleEditar.getComponenteEnsamble(),productoEnsambleEditar.getCantidad());
                    //actualizarTablaEnsamble();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    private void cargarComponenteProductoEnsambleVista(Producto producto,BigDecimal cantidad)
    {        
        if(producto!=null)
        {
            getTxtProductoEnsamble().setText(productoEnsamble.getNombre());
            getTxtCantidadEnsamble().setText(cantidad+"");
        }
        else
        {
            getTxtProductoEnsamble().setText("");
            getTxtCantidadEnsamble().setText("");
        }
        
    }

    private void listenerBotones() {
        
        getBtnBuscarProductoEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoEnsamble = (Producto) buscarDialogoModel.getResultado();

                if (productoEnsamble != null) {
                    cargarComponenteProductoEnsambleVista(productoEnsamble, BigDecimal.ONE);
                    //getTxtProductoEnsamble().setText(productoEnsamble.getNombre());
                }
            }
        });
        
        getBtnAgregarEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoEnsamble componenteEnsamble = new ProductoEnsamble();
                componenteEnsamble.setCantidad(new BigDecimal(getTxtCantidadEnsamble().getText()));
                componenteEnsamble.setComponenteEnsamble(productoEnsamble);
                controlador.producto.addProductoEnsamble(componenteEnsamble);
                actualizarTablaEnsamble();
                //DialogoCodefac.mensaje(new CodefacMs);
            }
        });
        
        getBtnEditarEnsamble().addActionListener(listenerEditarEnsamble);
        getBtnEliminarEnsamble().addActionListener(listenerEliminarEnsamble);

    }
    
    private ActionListener listenerEliminarEnsamble=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(productoEnsambleEditar!=null)
            {
                controlador.producto.quitarProductoEnsamble(productoEnsambleEditar);
                cargarComponenteProductoEnsambleVista(null, BigDecimal.ONE);
                actualizarTablaEnsamble();
            }
        }
    };
    
    private ActionListener listenerEditarEnsamble=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //int filaSeleccionada=getTblDatosEnsamble().getSelectedRowCount();
            if(productoEnsambleEditar!=null)
            {
                //ProductoEnsamble productoEnsamble=(ProductoEnsamble) getTblDatosEnsamble().getValueAt(filaSeleccionada,COLUMNA_ENSAMBLE_OBJECTO);                
                productoEnsambleEditar.setCantidad(new BigDecimal(getTxtCantidadEnsamble().getText()));
                actualizarTablaEnsamble();
                productoEnsambleEditar=null;
                cargarComponenteProductoEnsambleVista(null, BigDecimal.ONE);
            }
            else
            {
                
                //DialogoCodefac.mensaje(new CodefacMs);
            }
       }
    };

    private void actualizarTablaEnsamble() {
        String[] titulo = {"","Cantidad", "Nombre", "Precio Venta"};
        UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Object.class,String.class,String.class,String.class});
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);

        if(controlador.producto.getDetallesEnsamble()!=null)
        {
            for (ProductoEnsamble productoEnsamble : controlador.producto.getDetallesEnsamble()) {
                Vector<Object> fila = new Vector<Object>();
                fila.add(productoEnsamble);
                fila.add(productoEnsamble.getCantidad() + "");
                if(productoEnsamble.getComponenteEnsamble()!=null)
                {
                    fila.add(productoEnsamble.getComponenteEnsamble().getNombre());
                    fila.add(productoEnsamble.getComponenteEnsamble().getValorUnitario() + "");
                }
                else
                {
                    fila.add("");
                    fila.add("");
                }
                tableModel.addRow(fila);
            }
        }
        getTblDatosEnsamble().setModel(tableModel);
        UtilidadesTablas.definirTamanioColumnas(getTblDatosEnsamble(), new Integer[]{0});
    }
    
    private void setearValoresIniciales()
    {
        getTxtAplicacionProducto().setText("");
        getTxtCantidadEnsamble().setText("0");
        getTxtCantidadMinima().setText("0");
        getTxtPrecioDistribuidor().setText("0");
        getTxtPrecioTarjeta().setText("0");
        getTxtPrecio1SinSubsidio().setText("0");
        getTxtPV4().setText("0");
        getTxtPV5().setText("0");
        getTxtPV6().setText("0");
        //getTxtStockInicial().setText("0");
    }
    
    private void initModelTablaDatosEnsamble()
    {
        String[] titulo = {"Cantidad","Nombre","Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);
        getTblDatosEnsamble().setModel(tableModel);
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        controlador.producto=(Producto) entidad;
        getTxtCodigoEAN().setText(controlador.producto.getCodigoEAN());
        getTxtCodigoUPC().setText(controlador.producto.getCodigoUPC());


        /**
         * Cargar datos adicionales
         */
        getTxtUbicacion().setText((controlador.producto.getUbicacion() != null) ? controlador.producto.getUbicacion() : "");
        //getCmbGarantia().setSelectedItem(EnumSiNo.getEnumByLetra(controlador.producto.getGarantia()));
        //get().setText((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        getTxtCantidadMinima().setText((controlador.producto.getCantidadMinima() != null) ? controlador.producto.getCantidadMinima() + "" : "");
        getTxtPrecioDistribuidor().setText((controlador.producto.getPrecioDistribuidor() != null) ? controlador.producto.getPrecioDistribuidor() + "" : "");
        getTxtPrecioTarjeta().setText((controlador.producto.getPrecioTarjeta() != null) ? controlador.producto.getPrecioTarjeta() + "" : "");
        //getTxtStockInicial().setText((producto.getStockInicial() != null) ? producto.getStockInicial() + "" : "");
        //getTxtMarca().setText((controlador.producto.getMarca() != null) ? controlador.producto.getMarca() + "" : "");
        getTxtImagenProducto().setText((controlador.producto.getImagen() != null) ? controlador.producto.getImagen() + "" : "");
        //getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getTxtCaracteristica().setText((controlador.producto.getCaracteristicas() != null) ? controlador.producto.getCaracteristicas() + "" : "");
        getTxtObservaciones().setText((controlador.producto.getObservaciones() != null) ? controlador.producto.getObservaciones() + "" : "");
        //getCmbTipoProducto().setSelectedItem(controlador.producto.getTipoProductoEnum());
        getTxtPV4().setText((controlador.producto.getPvp4()!= null) ? controlador.producto.getPvp4() + "" : "0");
        getTxtPV5().setText((controlador.producto.getPvp5()!= null) ? controlador.producto.getPvp5() + "" : "0");
        getTxtPV6().setText((controlador.producto.getPvp6()!= null) ? controlador.producto.getPvp6() + "" : "0");
        getTxtAplicacionProducto().setText(controlador.producto.getAplicacionProducto());
        getTxtPrecio1SinSubsidio().setText((controlador.producto.getPrecioSinSubsidio()!=null)?controlador.producto.getPrecioSinSubsidio().toString():"0");
        
        
        getChkTransportarGuiaRemision().setSelected(controlador.producto.getTransportarEnGuiaRemisionEnum().getBool());
        getChkOcultarDetalleVenta().setSelected(controlador.producto.getOcultarDetalleVentaEnum().getBool());
        
        actualizarTablaEnsamble();
    }

    /**
     * LOS datos del constructor externo son
     * [0] EnumSiNO
     * [1] Codigo producto
     * [2] Nombre producto
     * [3] Pvp sin iva
     * [4] Impuesto del Iva
     * @param parametros 
     */
    @Override
    public void postConstructorExterno(Object[] parametros) {
      
        //EnumSiNo enumSiNo=(EnumSiNo) parametros[0];
        //getCmbManejaInventario().setSelectedItem(enumSiNo);
        
        //TODO: Mejorar esta parte porque solo va a funcionar para el codigo personalizado y si quieren manejar algunos codigos no funciona
        if(parametros[1]!=null)
        {
            String codigoProducto=(String) parametros[1];
            getTxtCodigoPersonalizado().setText(codigoProducto);
        }
        
        if(parametros[2]!=null)
        {
            String nombreProducto=(String) parametros[2];
            getTextNombre().setText(nombreProducto);
        }
        
        if(parametros[3]!=null)
        {
            BigDecimal pvpPrecio=(BigDecimal) parametros[3];
            getTextValorUnitario().setText(pvpPrecio+"");
        }
        
        if(parametros[4]!=null)
        {
            ImpuestoDetalle impuestoDetalleIva=(ImpuestoDetalle) parametros[4];
            controlador.setIvaSeleccionado(impuestoDetalleIva);
        }
        actualizarBindingCompontValues();
    }

  
    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public ProductoModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(ProductoModelControlador controlador) {
        this.controlador = controlador;
    }
    
    
    
    
}
