/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;

/**
 *
 * @author
 */
public class PerfilBusquedaDialogo implements InterfaceModelFind<Perfil>{

    private Empresa empresa;

    public PerfilBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre",100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Perfil u WHERE u.empresa=?3 and u.estado=?1 and ";
        queryString+=" ( LOWER(u.nombre) like ?2 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Perfil t, Vector dato) {
        dato.add(t.getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(Perfil t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
