/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class MesaBusquedaDialogo implements InterfaceModelFind<Mesa>{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Número",0.2d));
        titulo.add(new ColumnaDialogo("Nombre",0.2d));
        titulo.add(new ColumnaDialogo("Capacidad",0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString=" SELECT u FROM Mesa u where u.estado<>?1 and ( u.nombre like ?3) ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,Mesa.MesaEstadoEnum.ELIMINADO.getLetra());
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Mesa t, Vector dato) {
        dato.add(t.getNumero());
        dato.add(t.getNombre());
        dato.add(t.getCapacidad());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
