/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author Carlos
 */
public class RubroPeriodoBusquedaDialogo implements InterfaceModelFind<RubrosNivel>{

    private Periodo periodoFiltro;

    public RubroPeriodoBusquedaDialogo(Periodo periodoFiltro) {
        this.periodoFiltro = periodoFiltro;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Periodo", 0.3d));
        titulo.add(new ColumnaDialogo("Valor", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //RubrosNivel rn;
        //rn.getEstado();
        String queryString = "SELECT u FROM RubrosNivel u WHERE u.periodo=?2 and u.estado=?3 and ";
        queryString += " ( LOWER(u.nombre) like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,periodoFiltro);
        queryDialog.agregarParametro(3,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(RubrosNivel t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getPeriodo().getNombre());
        dato.add(t.getValor().toString());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
