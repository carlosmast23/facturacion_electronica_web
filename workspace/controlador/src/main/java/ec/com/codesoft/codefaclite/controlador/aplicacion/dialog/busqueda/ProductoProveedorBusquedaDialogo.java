/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author PC
 */
public class ProductoProveedorBusquedaDialogo implements InterfaceModelFind<ProductoProveedor>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Costo", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));              
        return titulo;
    }

    @Override
    public void agregarObjeto(ProductoProveedor t, Vector dato) 
    {
        dato.add(t.getProducto().getCodigoPersonalizado());
        dato.add(t.getProducto().getNombre());
        dato.add(t.getCosto()+"");
        
        EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra("no"); //TODO: Setear otra variable mas util
        //dato.add(enumSiNo.getNombre());
                

    }

    /*
    @Override
    public Boolean buscarObjeto(ProductoProveedor t, Object valor) 
    {
        return t.getProducto().getNombre().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {
        /*ProductoProveedor productoProveedor;
        productoProveedor.getProducto().getNombre();*/
        String queryString = "SELECT u FROM ProductoProveedor u WHERE ";
        queryString+=" ( LOWER(u.producto.nombre) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        //queryDialog.agregarParametro(2,ProductoEnumEstado.INACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
