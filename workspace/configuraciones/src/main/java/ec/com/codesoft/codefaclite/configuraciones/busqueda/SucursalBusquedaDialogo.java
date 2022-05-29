/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;

/**
 *
 * @author Carlos
 */
public class SucursalBusquedaDialogo implements InterfaceModelFind<Sucursal> {

    private Empresa empresa;

    public SucursalBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 100));
        titulo.add(new ColumnaDialogo("Codigo", 100));
        titulo.add(new ColumnaDialogo("Direccion", 100));
        titulo.add(new ColumnaDialogo("Telefono", 100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Sucursal s;
        //s.getEmpresa().
        //s.getNombre();
        //pv.getDescripcion()
        String queryString = "SELECT s FROM Sucursal s WHERE s.estado=?1 and s.empresa=?3 and ";
        queryString += "(LOWER(s.nombre) like ?2) ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);
        queryDialog.agregarParametro(3,empresa);
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Sucursal t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getCodigoSucursal());
        dato.add(t.getDirecccion());
        dato.add(t.getTelefono());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
