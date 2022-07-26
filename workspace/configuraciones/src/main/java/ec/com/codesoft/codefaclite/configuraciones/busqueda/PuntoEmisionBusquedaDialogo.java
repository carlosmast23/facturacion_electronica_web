/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;

/**
 *
 * @author
 */
public class PuntoEmisionBusquedaDialogo implements InterfaceModelFind<PuntoEmision>{

    private Empresa empresa;

    public PuntoEmisionBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 100));
        titulo.add(new ColumnaDialogo("Secuencial", 100));
        titulo.add(new ColumnaDialogo("Sucursal", 100));
        //titulo.add(new ColumnaDialogo("Empresa", 100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //PuntoEmision pv;
        //pv.getSucursal().getEmpresa();
        //pv.getEstado();
        //pv.getDescripcion();
        //pv.getDescripcion()
        String queryString = "SELECT pv FROM PuntoEmision pv WHERE pv.estado=?1 AND ";
        queryString += " pv.sucursal.empresa=?3 and  (LOWER(pv.descripcion) like ?2) ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);
        queryDialog.agregarParametro(3,empresa);
        
        return queryDialog;
        
    }

    @Override
    public void agregarObjeto(PuntoEmision t, Vector dato) {
        dato.add(t.getDescripcion());
        dato.add(t.getPuntoEmision());
        dato.add(t.getSucursal());
        //dato.add(t.getPuntoEmision());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
