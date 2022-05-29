/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class GestionRetencionRentaDialogo implements InterfaceModelFind<SriRetencionRenta>{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Código",0.2d));
        titulo.add(new ColumnaDialogo("Nombre",0.2d));
        titulo.add(new ColumnaDialogo("Porcentaje",0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //SriRetencionRenta rente;
        //rente.get
        String queryString=" SELECT u FROM SriRetencionRenta u where u.nombre like ?3 or u.codigo like ?3 ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        //queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        //queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(SriRetencionRenta t, Vector dato) {
        dato.add(t.getCodigo());
        dato.add(t.getNombre());
        dato.add(t.getPorcentaje());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
