/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoBusquedaDialogo implements InterfaceModelFind<OrdenTrabajo>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Código",0.2d));
        titulo.add(new ColumnaDialogo("Cliente",0.3d));
        titulo.add(new ColumnaDialogo("Descripción",0.15d));
        titulo.add(new ColumnaDialogo("Fecha Ingreso",0.15d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //OrdenTrabajo ordenTrabajo;
        //ordenTrabajo.getEstadoEnum()
        String queryString = "SELECT ot FROM OrdenTrabajo ot WHERE ";
        queryString+=" ( LOWER(ot.codigo) like ?2 ) or ot.descripcion like lower(?3) and (ot.estado=?1)";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,OrdenTrabajo.EstadoEnum.GENERADO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,filter);
        return queryDialog;   
    }

    @Override
    public void agregarObjeto(OrdenTrabajo t, Vector dato) {
        dato.add(""+t.getId());
        dato.add(""+t.getCliente().getNombresCompletos());
        dato.add(""+t.getDescripcion());
        dato.add(""+t.getFechaIngreso());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
