/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ProductoBusquedaDialogo implements InterfaceModelFind<Producto> , InterfacesPropertisFindWeb
{
    private Empresa empresa;
    /**
     * Variable para hacer ese filtro cuando lo requiera
     */
    private EnumSiNo generarCodigoBarrasEnum; 
    
    private TipoProductoEnum tipoProductoEnum;
    
    private EnumSiNo isManejoInvetario;

    public ProductoBusquedaDialogo(Empresa empresa) 
    {
        this.generarCodigoBarrasEnum = null; //Le pongo en null para que filtre todo
        this.empresa=empresa;
        this.isManejoInvetario=null;
    }
    
    public ProductoBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa) {
        this.isManejoInvetario = isManejoInvetario;
        this.empresa = empresa;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Código", 0.2d));
        titulo.add(new ColumnaDialogo("Código Aux", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.1d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("ICE", 0.1d));        
        return titulo;
    }

    @Override
    public void agregarObjeto(Producto t, Vector dato) 
    {
        dato.add(t.getCodigoPersonalizado());
        dato.add(t.getCodigoUPC());
        dato.add(t.getNombre());
        dato.add(t.getValorUnitario());
        
        if(t.getCatalogoProducto().getIva()!=null)
        {
            dato.add(t.getCatalogoProducto().getIva().toString());
        }
        else
        {
            dato.add("");
        }
        
        if(t.getCatalogoProducto().getIce() != null){
            dato.add(t.getCatalogoProducto().getIce().toString());
        }
        else
        {
            dato.add("");
        }
    }

    /*
    @Override
    public Boolean buscarObjeto(Producto t, Object valor) 
    {
        return t.getNombre().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {
        //Producto p;
        //p.getGenerarCodigoBarras()
        String queryExtra="";
        if(tipoProductoEnum!=null)
        {
            queryExtra=" and u.tipoProductoCodigo=?99 ";
        }
        
        String whereManejaInventario="";
        if(isManejoInvetario!=null)
        {
            whereManejaInventario=" and u.manejarInventario=?98 ";
        }
        
        
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
        
        
        String queryString = "SELECT u FROM Producto u WHERE 1=1 "+queryFiltroEmpresa+" and (u.estado=?1) "+queryExtra+whereManejaInventario;        
        
        if (generarCodigoBarrasEnum != null) {
            queryString+=" and  u.generarCodigoBarras=?3 ";
        }
        
       
        queryString+=" and ( LOWER(u.nombre) like ?2 OR LOWER(u.codigoPersonalizado) like ?2 OR LOWER(u.codigoUPC) like ?2 ) ORDER BY u.nombre";

        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        
        if (generarCodigoBarrasEnum != null)
        {
            queryDialog.agregarParametro(3,generarCodigoBarrasEnum.getLetra());
        }
        
        if(tipoProductoEnum!=null)
        {
            queryDialog.agregarParametro(99,tipoProductoEnum.getLetra());
        }
        
        if(isManejoInvetario!=null)
        {
            queryDialog.agregarParametro(98,isManejoInvetario.getLetra());
        }
        
        if (!datosCompartidosEmpresas) 
        {
            queryDialog.agregarParametro(4,empresa);
        }
        return queryDialog;
    }

    public EnumSiNo getGenerarCodigoBarrasEnum() {
        return generarCodigoBarrasEnum;
    }

    public void setGenerarCodigoBarrasEnum(EnumSiNo generarCodigoBarrasEnum) {
        this.generarCodigoBarrasEnum = generarCodigoBarrasEnum;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("codigoPersonalizado");
        propiedades.add("nombre");
        propiedades.add("valorUnitario");
        propiedades.add("catalogoProducto.iva");
        propiedades.add("catalogoProducto.ice");
        return propiedades;
    }

    public TipoProductoEnum getTipoProductoEnum() {
        return tipoProductoEnum;
    }

    public void setTipoProductoEnum(TipoProductoEnum tipoProductoEnum) {
        this.tipoProductoEnum = tipoProductoEnum;
    }
    
    
    
    
}
