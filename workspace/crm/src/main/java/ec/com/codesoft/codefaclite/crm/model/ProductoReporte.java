/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.crm.data.ClienteData;
import ec.com.codesoft.codefaclite.crm.data.ProductoData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProductoReporte extends ControladorCodefacInterface{

    public ProductoReporte() {        
        
    }
    
    private void imprimirReporte()
    {
        try {
            InputStream path = RecursoCodefac.JASPER_CRM.getResourceInputStream("reporteProducto.jrxml");
            Map parameters = new HashMap();
            List<ProductoData> data = new ArrayList<ProductoData>();
            ProductoServiceIf service=ServiceFactory.getFactory().getProductoServiceIf();
            List<Producto> productos=service.obtenerTodosActivos(session.getEmpresa());
            for (Producto producto : productos) {
                ProductoData productoData=new ProductoData();
                productoData.setCodigoPrincipal(producto.getCodigoPersonalizado());
                
                if(producto.getCatalogoProducto()==null || producto.getCatalogoProducto().getIva()==null)
                {
                    System.out.println("Producto no tiene catalogo , código ="+producto.getCodigoPersonalizado());
                }
                else
                {
                    productoData.setImpuestoIva(producto.getCatalogoProducto().getIva().getNombre());
                }
                
                
                productoData.setNombre(producto.getNombre());
                productoData.setTipoProducto(producto.getTipoProductoEnum().getNombre());
                if(producto.getValorUnitario()!=null){
                    Integer decimalesRedondear = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.NUMERO_DECIMALES_RIDE, new ParametroUtilidades.ComparadorInterface() {
                        @Override
                        public Object consultarParametro(String nombreParametro) {
                            return Integer.parseInt(nombreParametro);
                        }
                    });
                    
                    if(decimalesRedondear==null)
                    {
                        decimalesRedondear=ParametrosSistemaCodefac.PresentacionDecimales.DECIMALES_DEFECTO_PRODUCTO;
                    }
                    
                    productoData.setCategoria("");
                    if(producto.getCatalogoProducto().getCategoriaProducto()!=null)
                    {
                        productoData.setCategoria(producto.getCatalogoProducto().getCategoriaProducto().getNombre());
                    }
                    
                    productoData.setValorUnitario(producto.getValorUnitario().setScale(decimalesRedondear, ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO).toString());
                    productoData.setIva(producto.obtenerIvaValorUnitario().setScale(decimalesRedondear, ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO).toString());
                    productoData.setTotal(producto.obtenerValorUnitarioConIva().setScale(decimalesRedondear, ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO).toString());
                }else{
                    productoData.setValorUnitario("0.00");
                    productoData.setIva("0.00");
                    productoData.setTotal("0.00");
                }
                
                
                data.add(productoData);
            }
            
            Collections.sort(data, new Comparator<ProductoData>(){
                public int compare(ProductoData obj1, ProductoData obj2)
                {
                    return obj1.getNombre().compareTo(obj2.getNombre());
                }
            });
            
            DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                @Override
                public void excel() {
                    try {
                        Excel excel = new Excel();
                        String[] nombreCabeceras = {"Codigo", "Tipo", "Nombre","Categoria","Valor Unit","Iva","Total"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    } catch (IOException ex) {
                        Logger.getLogger(ProductoReporte.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ProductoReporte.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ProductoReporte.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Productos");
                }
            });
            setClosable(true);

        } catch (RemoteException ex) {
            Logger.getLogger(ProductoReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Producto Reporte";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite{
        imprimirReporte();
        throw new ExcepcionCodefacLite("Cerrar Ventan");
    }

    @Override
    public void nuevo() {
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
    
}
