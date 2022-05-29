/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.inventario;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CatalogoProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.StockMinimoData;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.RutaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ReporteInventarioStockControlador extends ModelControladorAbstract<ReporteInventarioStockControlador.CommonIf, ReporteInventarioStockControlador.SwingIf, ReporteInventarioStockControlador.WebIf> implements VistaCodefacIf
{
    private List<Object[]> listaStock;
    /**
     * Lista que va a contener todos los datos del reporte
     */
    private List<StockMinimoData> listaData;
    
    private List<Bodega> bodegasList;
    
    private Bodega bodegaSeleccionada;
    
    private List<CategoriaProducto> categoriaList;
    
    private CategoriaProducto categoriaSeleccionada;
    
    
  
    public ReporteInventarioStockControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ReporteInventarioStockControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {       
           
        try {
            bodegasList = ServiceFactory.getFactory().getBodegaServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            
            //Cargar la categoria
            categoriaList= ServiceFactory.getFactory().getCategoriaProductoServiceIf().obtenerTodosPorEmpresa(session.getEmpresa());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteInventarioStockControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        this.iniciar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    /**
     * TODO: Unir esta funcionalidad con la pantalla de swing
     */
    public void listenerConsultar()
    {
        try {
            //Bodega bodegaSeleccionada=(Bodega) getCmbBodega().getSelectedItem();
            
            listaStock = ServiceFactory.getFactory().getKardexServiceIf().consultarStock(bodegaSeleccionada, categoriaSeleccionada,session.getEmpresa());
            
            listaData = new ArrayList<StockMinimoData>();
            
            for (Object[] objeto : listaStock) {
                Producto producto = (Producto) objeto[0];
                Integer cantidad = (Integer) objeto[1];
                BigDecimal costoPromedio = (BigDecimal) objeto[2];
                //Kardex kardexTemp = (Kardex) objeto[2];
                
                /*if(producto==null)
                {
                System.err.println("Error con un producto nulo en kardeId="+kardexTemp.getId());
                continue;
                }*/
                StockMinimoData data = new StockMinimoData();
                
                String codigoPersonalizado = "Sin Código";
                if (producto.getCodigoPersonalizado() != null) {
                    codigoPersonalizado = producto.getCodigoPersonalizado();
                }
                
                data.setCodigo(codigoPersonalizado);
                data.setProducto(producto.getNombre());
                data.setStock(cantidad.toString());
                data.setCategoria((producto.getCatalogoProducto().getCategoriaProducto() != null) ? producto.getCatalogoProducto().getCategoriaProducto().getNombre() : "");
                data.setUbicacion(producto.getUbicacion());
                data.setCantidadMinima(producto.getCantidadMinima().toString());
                data.setCosto(costoPromedio.toString());
                
                listaData.add(data);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteInventarioStockControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public List<Bodega> getBodegasList() {
        return bodegasList;
    }

    public void setBodegasList(List<Bodega> bodegasList) {
        this.bodegasList = bodegasList;
    }

    public Bodega getBodegaSeleccionada() {
        return bodegaSeleccionada;
    }

    public void setBodegaSeleccionada(Bodega bodegaSeleccionada) {
        this.bodegaSeleccionada = bodegaSeleccionada;
    }

    public List<CategoriaProducto> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<CategoriaProducto> categoriaList) {
        this.categoriaList = categoriaList;
    }

    public CategoriaProducto getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaProducto categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public List<Object[]> getListaStock() {
        return listaStock;
    }

    public void setListaStock(List<Object[]> listaStock) {
        this.listaStock = listaStock;
    }

    public List<StockMinimoData> getListaData() {
        return listaData;
    }

    public void setListaData(List<StockMinimoData> listaData) {
        this.listaData = listaData;
    }

    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    //                      INTERFACES Y CLASES
    ////////////////////////////////////////////////////////////////////////////
    public interface CommonIf
    {
        
    }
    
    public interface SwingIf extends CommonIf
    {   
        //void addCheckListener();
    }
    
    public interface WebIf extends CommonIf
    {   
    }
    
}
