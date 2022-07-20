/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @auhor
 */
public class ArqueoCajaBusquedaDialogo implements InterfaceModelFind<ArqueoCaja>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Tiempo", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Valor Teorico", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Valor Fisico", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT ac FROM ArqueoCaja ac WHERE";
        queryString+=" ((ac.estado) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(ArqueoCaja t, Vector dato) {
        dato.add(t.getFechaHora().toString());
        dato.add(t.getValorFisico().toString());
        dato.add(t.getValorTeorico().toString());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("fechaHora");
        propiedades.add("valorTeorico");
        propiedades.add("valorFisico");
        return propiedades;
    }
    
}
