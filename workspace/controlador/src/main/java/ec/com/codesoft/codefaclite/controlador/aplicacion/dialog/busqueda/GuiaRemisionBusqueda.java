/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionBusqueda implements InterfaceModelFind<GuiaRemision>
{
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        //titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("Identificación", 0.15d));
        titulo.add(new ColumnaDialogo("Razón Social", 0.3d));
        titulo.add(new ColumnaDialogo("Documento", 0.2d));
        titulo.add(new ColumnaDialogo("Estado", 0.15d));
        //titulo.add(new ColumnaDialogo("fecha", 0.15d));
        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        
        String queryString = "SELECT u FROM GuiaRemision u WHERE u.estado<>?1 ";
        queryString+="AND ( LOWER(u.razonSocial) like ?2 OR CONCAT(u.secuencial, '') like ?2 )";
        queryString+=" ORDER BY u.secuencial+0 DESC ";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,filter);
        
        return queryDialog;
        
    }

    @Override
    public void agregarObjeto(GuiaRemision t, Vector dato) {
        dato.add(t.getPreimpreso());
        dato.add((t.getIdentificacion()!=null)?t.getIdentificacion():"");
        dato.add(t.getRazonSocial().toString());
        dato.add(t.getCodigoDocumentoEnum().getNombre());
        dato.add(t.getEstadoEnum().getNombre());
        //dato.add(t.getFechaEmision());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
