/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class FacturaBusquedaNotaCredito implements InterfaceModelFind<Factura> ,InterfacesPropertisFindWeb{
    private Empresa empresa;

    public FacturaBusquedaNotaCredito(Empresa empresa) {
        this.empresa = empresa;
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //NotaCredito nota;
        //nota.getTotal();
        //Factura f=new Factura();
        //f.getSecuencial();
        //f.getEstadoNotaCredito()
        
        String queryString = "SELECT u FROM Factura u WHERE u.empresa=?5 and ( u.estado<>?1 and ( u.estadoNotaCredito<>?2 OR u.estadoNotaCredito IS NULL ) and u.estado<>?3 and u.estado<>?6 ) AND ";
        queryString+=" ( LOWER(u.cliente.razonSocial) like ?4 OR CONCAT(u.secuencial,'') like ?4 ) order by CAST(u.secuencial AS BIGINT) desc";
        Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.INFO, null, queryString);
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado());
        queryDialog.agregarParametro(3,ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
        queryDialog.agregarParametro(6,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
        queryDialog.agregarParametro(4,filter);
        queryDialog.agregarParametro(5,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        dato.add(t.getId());
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        ComprobanteEntity.ComprobanteEnumEstado estadoEnum= ComprobanteEntity.ComprobanteEnumEstado.getEnum(t.getEstado());
        dato.add((estadoEnum!=null)?estadoEnum.getNombre():"Sin Estado");
        dato.add(t.getFechaEmision());
        dato.add(t.getTotal());
    }

    /*
    @Override
    public Boolean buscarObjeto(Factura t, Object valor) {
        //if(t.getCliente().getIdentificacion().indexOf(valor.toString())>=0 || t.getPreimpreso().indexOf(valor.toString())>=0)
        String preimpreso=t.getPreimpreso().toLowerCase();
        String valorBuscar=valor.toString().toLowerCase();
        if(preimpreso.indexOf(valorBuscar)>=0)
        {
            return true;
        }   
        else
        {
            return false;
        }  
        //return true;
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        //Factura f;
        //f.getEstado()
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("id");
        propiedades.add("preimpreso");
        propiedades.add("razonSocial");//TODO: Ver como puedo hacer para establecer una propiedad personalizada
        propiedades.add("estado");
        propiedades.add("fechaEmision");
        propiedades.add("total");
        return propiedades;
    }
    
}
