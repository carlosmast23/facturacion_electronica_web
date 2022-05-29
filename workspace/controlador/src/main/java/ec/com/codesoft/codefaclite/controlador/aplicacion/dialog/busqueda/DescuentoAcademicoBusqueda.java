/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoAcademicoBusqueda implements InterfaceModelFind<DescuentoAcademico>, InterfacesPropertisFindWeb  {

    private Periodo periodoActual;

    public DescuentoAcademicoBusqueda(Periodo periodoActual) {
        this.periodoActual = periodoActual;
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Porcentaje", 0.3d));
        titulo.add(new ColumnaDialogo("Tipo", 0.3d));
        titulo.add(new ColumnaDialogo("Estado", 0.15d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //DescuentoAcademico descuentoAcademico;
        //descuentoAcademico.getEstado();
        
        String queryStr="SELECT d FROM DescuentoAcademico d where d.estado<>?1 and d.periodo=?2 ";
        QueryDialog queryDialog=new QueryDialog(queryStr);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,periodoActual);
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(DescuentoAcademico t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getPorcentaje());
        dato.add(t.getTipoEnum().getNombre());
        dato.add(t.getEstadoEnum().getNombre());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
