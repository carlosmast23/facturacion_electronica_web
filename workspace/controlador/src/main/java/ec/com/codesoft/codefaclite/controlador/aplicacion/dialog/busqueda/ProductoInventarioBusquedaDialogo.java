/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo implements InterfaceModelFind<Kardex> , InterfacesPropertisFindWeb {
    
    private Empresa empresa;
    //private EnumSiNo isManejoInvetario;
    private Bodega bodega;
    
    public ProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega) 
    {
        //this.isManejoInvetario = isManejoInvetario;
        this.empresa = empresa;
        this.bodega = bodega;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Codigo Aux", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Lote", 0.3d));
        titulo.add(new ColumnaDialogo("Ubicación", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.1d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("Stock", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String whereManejaInventario="";
        
        String whereBodega="";
        
        //if(isManejoInvetario!=null)
        //{
        //    whereManejaInventario=" and u.manejarInventario=?98 ";
        //}
        
        String queryFiltroEmpresa=" and u.empresa=?4";
        Boolean datosCompartidosEmpresas=false;
        try {
            datosCompartidosEmpresas=ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI);           
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteEstablecimientoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (datosCompartidosEmpresas) 
        {
            //Si los datos son compratidos entre empresas entoces no hago ningun filtro
            queryFiltroEmpresa = "";
        }
        
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?5 ";
        }
        
        
        String queryString = "SELECT k FROM Kardex k JOIN k.producto u  WHERE 1=1 "+queryFiltroEmpresa+" and (u.estado=?1)"+whereManejaInventario+whereBodega;      
        
        queryString+=" and ( LOWER(u.nombre) like ?2 OR LOWER(u.codigoPersonalizado) like ?2 OR LOWER(u.codigoUPC) like ?2 ) ORDER BY u.codigoPersonalizado";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        
        if (!datosCompartidosEmpresas) 
        {
           queryDialog.agregarParametro(4,empresa);
        }
        
        if(bodega!=null)
        {
           queryDialog.agregarParametro(5,bodega);
        }
        
        /*if(isManejoInvetario!=null)
        {
            queryDialog.agregarParametro(98,isManejoInvetario.getLetra());
        }*/
       
        return queryDialog;
    }
    
    
    @Override
    public void agregarObjeto(Kardex kardex, Vector vector) {
        //KardexServiceIf servicio = ServiceFactory.getFactory().getKardexServiceIf();
        //Kardex kardex = servicio.buscarKardexPorProductoyBodega(this.bodega, producto);
        Producto producto=kardex.getProducto();
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getCodigoUPC());
        vector.add(producto.getNombre());
        vector.add((kardex.getLote()!=null)?kardex.getLote().getCodigo():"");
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        vector.add(producto.getValorUnitario());
        vector.add((producto.getCatalogoProducto()!=null && producto.getCatalogoProducto().getIva()!=null)?producto.getCatalogoProducto().getIva().toString():"Sin Especificar");
        vector.add(kardex.getStock());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }
    
}
