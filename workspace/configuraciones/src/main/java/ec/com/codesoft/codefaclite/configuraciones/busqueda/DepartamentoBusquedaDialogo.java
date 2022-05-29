package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robert
 */
public class DepartamentoBusquedaDialogo implements InterfaceModelFind<Departamento>
{
    private GeneralEnumEstado filtroEstado;

    public DepartamentoBusquedaDialogo(GeneralEnumEstado filtroEstado) {
        this.filtroEstado = filtroEstado;
    }
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Codigo",100));
        titulo.add(new ColumnaDialogo("Nombre",100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Departamento d;
        //d.setEstado(estado);
        String queryString = "SELECT d FROM Departamento d WHERE ( (LOWER(d.codigo) like ?1) or ";
        queryString+="(LOWER(d.nombre) like ?2) ) ";
        
        if(filtroEstado!=null)
        {
            queryString+=" and d.estado=?3 "; 
        }
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,filter);
        if(filtroEstado!=null)
        {
            queryDialog.agregarParametro(3,filtroEstado.getEstado());
        }
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Departamento t, Vector dato) {
        dato.add(t.getCodigo());
        dato.add(t.getNombre());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
