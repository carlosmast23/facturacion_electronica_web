/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoProveedorFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProductoProveedorService extends ServiceAbstract<ProductoProveedor, ProductoProveedorFacade> implements ProductoProveedorServiceIf {

    public ProductoProveedorService() throws RemoteException {
        super(ProductoProveedorFacade.class);
    }

    @Override
    public List<ProductoProveedor> buscarProductoCompraActivo(Producto producto, Compra compra) throws ServicioCodefacException, java.rmi.RemoteException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("proveedor", compra.getProveedor());
        return getFacade().findByMap(mapParametros);
    }

    @Override
    public List<ProductoProveedor> buscarProductoProveedorActivo(Producto producto, Persona proveedor) throws ServicioCodefacException, java.rmi.RemoteException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }

    public List<ProductoProveedor> buscarPorProveedorActivo(Persona proveedor) throws ServicioCodefacException, java.rmi.RemoteException {
        //ProductoProveedor pp;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        //mapParametros.put("producto", producto);
        mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }

    public List<ProductoProveedor> buscarPorProductoActivo(Producto producto) throws ServicioCodefacException, java.rmi.RemoteException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        //mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }

    public ProductoProveedor buscarActivoPorCodigoProveedor(String codigoProveedor, Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("codigoProveedor", codigoProveedor);
        mapParametros.put("producto.empresa", empresa);
        //mapParametros.put("proveedor", proveedor);
        List<ProductoProveedor> resultadoList = getFacade().findByMap(mapParametros);

        if (resultadoList.size() > 0) {
            return resultadoList.get(0);
        }

        return null;
    }

    public ProductoProveedor construirSinTransaccion(Producto productoSeleccionado, Persona proveedor) throws ServicioCodefacException, java.rmi.RemoteException {
        ProductoProveedor productoProveedor = null;
        try {

            ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();

            List<ProductoProveedor> resultados = serviceProductoProveedor.buscarProductoProveedorActivo(productoSeleccionado, proveedor);

            if (resultados != null && resultados.size() > 0) {
                productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale

            } else {//Cuando no existe crea un nuevo producto proveedor
                productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                productoProveedor.setDescripcion("");
                productoProveedor.setEstado("a");
                productoProveedor.setProducto(productoSeleccionado);
                productoProveedor.setProveedor(proveedor);

            }

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoProveedorService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoProveedorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoProveedor;

    }

}
