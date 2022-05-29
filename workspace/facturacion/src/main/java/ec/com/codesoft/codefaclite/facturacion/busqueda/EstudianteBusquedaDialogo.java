/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author CodesoftDesarrollo
 */
public class EstudianteBusquedaDialogo implements InterfaceModelFind<Estudiante> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Cedula", 0.2d));
        titulo.add(new ColumnaDialogo("Nombres", 0.3d));
        titulo.add(new ColumnaDialogo("Apellidos", 0.3d));
        titulo.add(new ColumnaDialogo("Correo", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        /*Estudiante estudiante=new Estudiante();
        estudiante.getNombres();
        estudiante.getApellidos();
        estudiante.getCedula();*/
        
        String queryString = "SELECT u FROM Estudiante u WHERE (u.estado=?1) and";
        queryString += " u.cedula LIKE ?3 OR ( CONCAT(LOWER(u.nombres),' ',LOWER(u.apellidos)) LIKE ?2 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Estudiante e, Vector dato) {
       dato.add(e.getCedula());
       dato.add(e.getNombres());
       dato.add(e.getApellidos());
       dato.add(e.getEmail());
    }

    /*
    @Override
    public Boolean buscarObjeto(Estudiante e, Object valor) {
        return e.getApellidos().equals(valor.toString());
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
