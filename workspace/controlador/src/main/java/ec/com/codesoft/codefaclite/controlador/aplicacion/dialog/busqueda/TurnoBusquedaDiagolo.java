/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Turno;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.Vector;

/**
 *
 * @author Robert
 */
public class TurnoBusquedaDiagolo implements InterfaceModelFind<Turno>
{

    public TurnoBusquedaDiagolo(SessionCodefacInterface sessionCodefac) {
    }
       
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Nombre", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Hora Inicial", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Hora Final", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT t FROM Turno t WHERE ";
        queryString+=" ((t.nombre) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Turno t, Vector dato) {
        dato.add(t.getNombre().toString());
        dato.add("" + t.getHoraInicial());
        dato.add("" + t.getHoraFinal());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("nombre");
        propiedades.add("horaInicial");
        propiedades.add("horaFinal");
        return propiedades;
    }
    
}
