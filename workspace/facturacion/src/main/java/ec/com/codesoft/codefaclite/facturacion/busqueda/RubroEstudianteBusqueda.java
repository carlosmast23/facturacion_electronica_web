/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author
 */
public class RubroEstudianteBusqueda implements InterfaceModelFind<RubroEstudiante>{

    /**
     * Variable para filtrar por el estudiante
     */
    private Estudiante estudiante;

    public RubroEstudianteBusqueda(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Precio", 0.3d));
        titulo.add(new ColumnaDialogo("Saldo", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estado=?5 and u.estudianteInscrito.estudiante=?1 and ( u.estadoFactura=?2 OR  u.estadoFactura=?3) and ";
        queryString += " ( LOWER(u.rubroNivel.nombre) like ?4 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, estudiante);
        queryDialog.agregarParametro(2,RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
        queryDialog.agregarParametro(3,RubroEstudiante.FacturacionEstadoEnum.FACTURA_PARCIAL.getLetra());
        queryDialog.agregarParametro(4,filter);
        queryDialog.agregarParametro(5,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(RubroEstudiante t, Vector dato) {
        dato.add(t.getRubroNivel().getNombre());
        dato.add(t.getValor());
        dato.add(t.getSaldo());
    }

    /*
    @Override
    public Boolean buscarObjeto(RubroEstudiante t, Object valor) {
        return t.getRubroNivel().getNombre().equals(t);   
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
