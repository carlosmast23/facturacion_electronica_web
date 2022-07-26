/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;

/**
 *
 * @author
 */
public class FacturaBusqueda implements InterfaceModelFind<Factura>,InterfacesPropertisFindWeb {   
    private Persona cliente;
    private PersonaEstablecimiento establecimiento;
    private EnumSiNo estadoEnviadoGuiaRemision;
    private Sucursal sucursal;
    private Usuario usuario;
    

    public FacturaBusqueda(Persona cliente,Sucursal sucursal) {
        this.cliente = cliente;
        this.sucursal=sucursal;
    }
    
    public FacturaBusqueda(PersonaEstablecimiento establecimiento,Sucursal sucursal) {
        this.establecimiento = establecimiento;    
        this.sucursal=sucursal;
    }

    public FacturaBusqueda(Sucursal sucursal) {
        this.sucursal=sucursal;
    }
    
    public FacturaBusqueda(Sucursal sucursal, Usuario usuario) {
        this.sucursal = sucursal;
        this.usuario = usuario;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        //titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("documento", 0.15d));
        //titulo.add(new ColumnaDialogo("tipo", 0.15d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        Factura f;
        //f.getSucursalEmpresa().gete
        //f.getEstadoEnviadoGuiaRemision()
        //f.getSecuencial();
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 ";
        
        if(sucursal!=null)
        {
            queryString+=" AND u.sucursalEmpresa=?13 ";
        }
        
        if(cliente!=null)
        {
            queryString+=" AND u.cliente=?10 ";
        }
        
        if(establecimiento!=null)
        {
            queryString+=" AND u.sucursal=?12 ";
        }
        
        if(estadoEnviadoGuiaRemision!=null)
        {
            queryString+=" AND u.estadoEnviadoGuiaRemision=?11 ";
        }
        
        if(usuario != null)
            if(usuario.getFiltrarFacturaEnum().compareTo(EnumSiNo.SI) == 0 )
                queryString += " AND u.usuario=?14 ";
        
        
        queryString+=getQueryDocumentos();
        
        queryString+="AND ( LOWER(u.cliente.razonSocial) like ?2 OR CONCAT(u.secuencial, '') like ?2 )";
        queryString+=" ORDER BY u.secuencial+0 DESC ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,filter);
        //queryDialog.agregarParametro(3,DocumentoEnum.FACTURA.getCodigo());
        //queryDialog.agregarParametro(4,DocumentoEnum.NOTA_VENTA_INTERNA.getCodigo());
        setParameterQuery(queryDialog);
        
        
        if (sucursal != null) {
            queryDialog.agregarParametro(13,sucursal);
        }
        
        if (cliente != null) {
            queryDialog.agregarParametro(10,cliente);
        }
        
        if (establecimiento != null) {
            queryDialog.agregarParametro(12, establecimiento);
        }
        
        if(estadoEnviadoGuiaRemision!=null)
        {
            queryDialog.agregarParametro(11,estadoEnviadoGuiaRemision.getLetra());
        }
        
        if(usuario != null)
            if(usuario.getFiltrarFacturaEnum().compareTo(EnumSiNo.SI) == 0 )
                queryDialog.agregarParametro(14, usuario);
        
        
        //queryDialog.agregarParametro(3,FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
        return queryDialog;
    }
    
    public String getQueryDocumentos()
    {
        return " AND (u.codigoDocumento=?3 OR  u.codigoDocumento=?4 OR  u.codigoDocumento=?5 ) ";
    }
    
    public void setParameterQuery(QueryDialog queryDialog)
    {
        queryDialog.agregarParametro(3,DocumentoEnum.FACTURA.getCodigo());
        queryDialog.agregarParametro(4,DocumentoEnum.NOTA_VENTA_INTERNA.getCodigo());
        queryDialog.agregarParametro(5,DocumentoEnum.NOTA_VENTA.getCodigo());
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        //dato.add(t.getId());
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        DocumentoEnum documentoEnum= DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());        
        dato.add(documentoEnum.getNombre()); //TODO: Veri si para cosnultar por documento sea una propiedad intrinsica de la factura
        
        //ComprobanteEntity.TipoEmisionEnum tipoFactura=ComprobanteEntity.TipoEmisionEnum.getEnumByEstado(t.getTipoFacturacion());
        //dato.add(tipoFactura.getNombre());
        
        dato.add((t.getEstadoEnum()!=null)?t.getEstadoEnum().getNombre():"Sin estado");
        
        dato.add(t.getFechaEmisionFormat());
        dato.add(t.getTotal());
    }

    public void setEstadoEnviadoGuiaRemision(EnumSiNo estadoEnviadoGuiaRemision) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemision;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Factura f;
        //f.getd
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("Preimpreso");
        propiedades.add("cliente.razonSocial");
        propiedades.add("");//TODO: Ver como puedo hacer para establecer una propiedad personalizada
        propiedades.add("estado");
        propiedades.add("fechaEmision"); //TODO: Probar cambiando por el metodo fechaEmisionFormat
        propiedades.add("total");
        return propiedades;
    }
    
    
    
}
