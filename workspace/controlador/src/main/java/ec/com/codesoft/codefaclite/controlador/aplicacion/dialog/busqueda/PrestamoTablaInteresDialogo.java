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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoTablaInteres;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.Vector;

/**
 *
 * @author
 */
public class PrestamoTablaInteresDialogo implements InterfaceModelFind<PrestamoTablaInteres>,InterfacesPropertisFindWeb {
    
    private Empresa empresa;

    public PrestamoTablaInteresDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Meses", 0.5d));
        titulo.add(new ColumnaDialogo("Porcentaje", 0.3d));
        titulo.add(new ColumnaDialogo("Estado", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //PrestamoTablaInteres p;
        //p.getSucursal().getEmpresa()
        String queryString = "SELECT u FROM PrestamoTablaInteres u WHERE ";
        queryString += " u.estado=?1 and u.sucursal.empresa=?2 ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(PrestamoTablaInteres t, Vector dato) {
        dato.add(t.getMeses());
        dato.add(t.getPorcentaje());
        dato.add(t.getEstadoEnum().getEstado());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
